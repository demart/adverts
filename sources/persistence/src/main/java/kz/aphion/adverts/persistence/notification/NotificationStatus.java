package kz.aphion.adverts.persistence.notification;

/**
 * Статус уведомления, уведомления для определенного канала и так далее
 * 
 * @author artem.demidovich
 *
 * Created at Aug 15, 2016
 */
public enum NotificationStatus {

	/**
	 * Создано, дальнешие процессы будут изменять статус
	 */
	CREATED,
	
	/**
	 * Обработано, может повторятся неоднократно.
	 * Например: 
	 * 	EMAIL 
	 * 		Первый шаг подготовка сообщения и шаблонизация
	 * 		Второй шаг замена всех URL на локальный URL чтобы в последующем счиать переходы
	 */
	PROCESSED,
	
	/**
	 * Сообщение было отправлено (Означает что система транспорт успешно отправила сообщение)
	 */
	SENT,
	
	/**
	 * Отметка об успешной доставке, к сожалению не все каналы поддерживают данный вариант.
	 * Например EMAIL, PUSH не совсем поддерживают, поэтому мы будем использовать дополнительный
	 * API для мобильников чтобы они отправляли отметку и еще для EMAIL URL переходы с редиректом
	 */
	DELIVERED,
	
	/**
	 * Статус сообщает что внутренние процессы системы так и не смогли вовремя отправить сообщение
	 * Как система понимает что было вовремя или нет, то есть TTL для каждого сообщения канала.
	 * Если он превыщен система должно прекратить обработку так как считается что сообщение уже 
	 * не актуально. Например PUSH для объявление пришло только через 4 дня потому что мы ложанулил
	 * В такой ситуации лучше вообще не отправлять сообщения.
	 */
	EXPIRED,
	
	/**
	 * Отметка о прочтении. Очень важно понимать было ли просмотрено сообщение
	 * 1. Для браузеров мы можем это понять
	 * 2. Для емаил сомнительно, так как мы сможем поймат событие в случае перехода по URL
	 * 3. В мобильниках скорее всего нужно отправлять отметку как только чувак нажал на просмотр
	 */
	PREVIEWED,
	
	/**
	 * Ошибка при попытке отправить уведомление на какой либо канал
	 */
	FAILED,
	
}
