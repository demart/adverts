package kz.aphion.adverts.notification.sender.caches;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Кэш почты, для того чтобы хранить список недавно использованных ключей почты во избежания частых отправок.
 * Необходимо заменить на внешний кэш если будет работать в кластере.
 * 
 * @author artem.demidovich
 *
 * Created at Nov 13, 2017
 */
public class EmailCache {

	private final static int EXPIRATION_EMAIL_TIME = 1;
	
	private static HashMap<String, Calendar> emails;
	
	static {
		emails = new HashMap<String, Calendar>();
	}
	
	/**
	 * Проверяет истекло ли время ожидания для отправки следующего сообщения
	 * @param email
	 */
	public static boolean isEmailReadyForSend(String email) {
		if (emails.containsKey(email)) {
			if (emails.get(email) != null) {
				return Calendar.getInstance().compareTo(emails.get(email)) > 0 ? true : false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}
	
	/**
	 * Обновляем переменную кэша для того чтобы новые сообщения не отправлялись
	 * @param email
	 */
	public static void emailSuccessfullySent(String email) {
		Calendar expirationTime = Calendar.getInstance();
		expirationTime.add(Calendar.MINUTE, EXPIRATION_EMAIL_TIME);
		emails.put(email, expirationTime);
	}
	
	
}
