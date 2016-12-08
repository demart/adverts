package kz.aphion.adverts.phone.processors;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kz.aphion.adverts.common.models.mq.phones.RegisterPhoneModel;
import kz.aphion.adverts.persistence.phones.Phone;
import kz.aphion.adverts.persistence.phones.PhoneChangesHistory;
import kz.aphion.adverts.persistence.phones.PhoneOwner;
import kz.aphion.adverts.persistence.phones.PhoneStatus;
import kz.aphion.adverts.phone.models.PhoneApplicationCheckModel;
import kz.aphion.adverts.phone.mq.QueueNameConstants;
import kz.aphion.adverts.phone.providers.ActiveMqProvider;
import kz.aphion.adverts.phone.providers.MongoDbProvider;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

/**
 * Класс процессор обрабатывает новые запросы на регистрацию телефонов
 * 
 * @author artem.demidovich
 *
 */
public class RegistrationPhoneProcessor {

	private static Logger logger = LoggerFactory.getLogger(RegistrationPhoneProcessor.class);

	public void processRegistrationMessage(String registrationMessage) throws Exception {
		RegisterPhoneModel model = parseModel(registrationMessage);
		if (model == null) {
			throw new JsonParseException("Can't parse registration phone json message");
		}
		
		for (String phoneNumber : model.phone) {
			if (phoneNumber == null) {
				if (logger.isDebugEnabled())
					logger.debug("WARNING Registration Phone Model has null phone in the list");
				continue;
			}
			String phone = normalizePhoneNumber(phoneNumber);
			Phone phoneEntity = getPhoneEntity(phone);
			
			if (phoneEntity == null) {
				// Add new entity
				phoneEntity = createNewPhoneRecord(phone, model);
				logger.info("Phone number [{}] successfully added to db", phone);
			} else {
				// Update existing
				phoneEntity = updateExistingPhoneRecord(phoneEntity, model);
				logger.info("Record with phone number [{}] successfully updated in db", phone);
			}
			
			// Отправляев очереди по проверки наличия приложений
			sendCheckAppRequests(phoneEntity);
		}
		
	}
	
	
	private void sendCheckAppRequests(Phone entity) {
		PhoneApplicationCheckModel checkModel = new PhoneApplicationCheckModel();
		checkModel.objectId = entity.id.toString();
		checkModel.phone = entity.number;
		checkModel.time = Calendar.getInstance();
		
		String message = new GsonBuilder().setPrettyPrinting().create().toJson(checkModel);
		
		try {
			ActiveMqProvider.getInstance().sendTextMessageToQueue(QueueNameConstants.MQ_CHECK_VIBER_QUEUE, message);
		} catch (Exception ex) {
			logger.error("Error during sending phone to check Viber application existance");
		}
		
		try {
			ActiveMqProvider.getInstance().sendTextMessageToQueue(QueueNameConstants.MQ_CHECK_TELEGRAM_QUEUE, message);
		} catch (Exception ex) {
			logger.error("Error during sending phone to check Telegram application existance");
		}
		
		
		try {
			ActiveMqProvider.getInstance().sendTextMessageToQueue(QueueNameConstants.MQ_CHECK_WHATSAPP_QUEUE, message);
		} catch (Exception ex) {
			logger.error("Error during sending phone to check Whatsapp application existance");
		}		
	}
	
	
	/**
	 * Проверяет есть ли измнения которые необходимо сделать в существующей записи.
	 * Если нет, то обновляет дату обновления
	 * Если есть, то записывает новые данные, старые сохраняет в истрии (мало ли)
	 * @param existingPhoneEntity
	 * @param model
	 * @return
	 * @throws Exception
	 */
	private Phone updateExistingPhoneRecord(Phone existingPhoneEntity, RegisterPhoneModel model) throws Exception{
		// Проверяем были ли изменены основные поля
		Boolean wasUpdated = false;	
		if (existingPhoneEntity.category != model.category) wasUpdated = true;
		if (existingPhoneEntity.owner != model.owner) wasUpdated = true;
		if (existingPhoneEntity.region != null && model.region != null)
			if (existingPhoneEntity.region.code != model.region.code) wasUpdated = true;
		if (existingPhoneEntity.source != model.source) wasUpdated = true;
		
		if (wasUpdated == false) {
			logger.info("Record with phone number [{}] was not updated. Skip update process.", existingPhoneEntity.number);
			
			// Update lastUpdate field
			/*
			Datastore ds = MongoDbProvider.getInstance().getDatastore();
			Query updateQuery = ds.createQuery(Phone.class).field(Mapper.ID_KEY).equal(existingPhoneEntity.id);

			existingPhoneEntity.lastUpdate = Calendar.getInstance();
			UpdateOperations<?> ops = ds.createUpdateOperations(Phone.class)
					.set("lastUpdate", existingPhoneEntity.lastUpdate);
			ds.update(updateQuery, ops);
			*/
			return existingPhoneEntity;
		}
			
		// Здесь обработка если всё таки есть изменения в данных
		
		if (existingPhoneEntity.history == null)
			existingPhoneEntity.history = new  ArrayList<PhoneChangesHistory>();
		
		// Сохраняем в истории какие данные были до этого
		// FIXME Почему то в истории изменения телефонов отсуствует последний регион, хотя может это и не важно
		PhoneChangesHistory historyItem = new PhoneChangesHistory();
		historyItem.category = existingPhoneEntity.category;
		historyItem.owner = existingPhoneEntity.owner;
		historyItem.source = existingPhoneEntity.source;
		historyItem.time = existingPhoneEntity.lastUpdate;
		existingPhoneEntity.history.add(historyItem);		
		
		existingPhoneEntity.category = model.category;
		existingPhoneEntity.owner = model.owner;
		existingPhoneEntity.region = model.region;
		existingPhoneEntity.regions = model.regions;
		existingPhoneEntity.source = model.source;
		existingPhoneEntity.status = PhoneStatus.ACTIVE;
		
		existingPhoneEntity.lastUpdate = Calendar.getInstance();
		existingPhoneEntity.updated = Calendar.getInstance(); 
		
		// FIXME Временное решение, так как нагенрил 30 тыс сообщений а это поле забыл...
		if (existingPhoneEntity.owner == null)
			existingPhoneEntity.owner = PhoneOwner.UNDEFINED;
		
		Datastore ds = MongoDbProvider.getInstance().getDatastore();
		Query updateQuery = ds.createQuery(Phone.class).field(Mapper.ID_KEY).equal(existingPhoneEntity.id);
		
		UpdateOperations<?> ops = ds.createUpdateOperations(Phone.class)
				.set("category", existingPhoneEntity.category)
				.set("owner", existingPhoneEntity.owner)
				.set("source", existingPhoneEntity.source)
				.set("region", existingPhoneEntity.region)
				.set("regions", existingPhoneEntity.regions)
				.set("status", existingPhoneEntity.status)
				.set("history", existingPhoneEntity.history)
//				.set("lastUpdate", existingPhoneEntity.lastUpdate)
//				.set("updated", existingPhoneEntity.updated);
				;
		

		
		ds.update(updateQuery, ops);
		
		return existingPhoneEntity;
	}
	
	
	/**
	 * Метод преобразует модель в сущность Телефон и сохраняет его в БД
	 * @param phone
	 * @param model
	 * @return
	 * @throws Exception
	 */
	private Phone createNewPhoneRecord(String phone, RegisterPhoneModel model) throws Exception {
		Phone phoneEntity = new  Phone();
		
		phoneEntity.number = phone;
		phoneEntity.category = model.category;
		phoneEntity.owner = model.owner;
		phoneEntity.region = model.region;
		phoneEntity.regions = model.regions;
		phoneEntity.source = model.source;
		phoneEntity.status = PhoneStatus.ACTIVE;
		
		phoneEntity.created = Calendar.getInstance();
		phoneEntity.updated = phoneEntity.created;
		phoneEntity.lastUpdate = phoneEntity.created;

		MongoDbProvider.getInstance().getDatastore().save(phoneEntity);
		
		return phoneEntity;
	}
	
	
	/**
	 * Извлекает запись из БД по номеру телефона
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	private Phone getPhoneEntity(String phone) throws Exception {
		Datastore ds = MongoDbProvider.getInstance().getDatastore();
		Query q = ds.createQuery(Phone.class);
		q.field("number").equal(phone);
		
		List result = q.asList();
		if (result != null && result.size() > 0)
			return (Phone)result.get(0);
		return null;
	}
	
	
	/**
	 * Удаляет из телефонов лишние скобки, плюсы и др.
	 * В случае если номер телефона больше 10 символов, то удаляет первую цифру (7 или 8)
	 * @param phoneNumber
	 * @return
	 */
	private String normalizePhoneNumber(String phoneNumber) {
		logger.info("Phone number before normalization: {}", phoneNumber);
		String phone = phoneNumber.replaceAll("[^\\d]", "");
		
		if (phone.length() > 10)
			phone = phone.substring(phone.length()-10, phone.length());
		
		logger.info("Phone number after normalization: {}", phone);
		return phone;
	}
	
	
	/**
	 * Разбирает JSON объект в модель для последующей обработки
	 * @param jsonRegistrationModel
	 * @return
	 */
	private RegisterPhoneModel parseModel(String jsonRegistrationModel) {
		if (jsonRegistrationModel == null)
			return null;
		
		Gson gson = new Gson();
		RegisterPhoneModel model = gson.fromJson(jsonRegistrationModel, RegisterPhoneModel.class);
		
		return model;
	}
	
}
