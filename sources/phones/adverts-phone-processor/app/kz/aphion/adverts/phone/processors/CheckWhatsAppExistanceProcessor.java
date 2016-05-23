package kz.aphion.adverts.phone.processors;

import kz.aphion.adverts.common.models.mq.phones.RegisterPhoneModel;

import com.google.gson.Gson;

/**
 * Класс процессор проверяет установлено ли приложение WhatsApp по указанному телефону
 * 
 * @author artem.demidovich
 *
 */
public class CheckWhatsAppExistanceProcessor extends AbstractAppExistanceProcessor {

	/**
	 * Обрабатывает запрос на проверку наличия приложения
	 * @param message JSON сообщение
	 * @throws Exception
	 */
	public void processMessage(String message) throws Exception {
		// TODO IMPLEMENT THIS CHECKER
	}
	
	
}
