package kz.aphion.adverts.analyser.mq;

public class QueueNameConstants {

	/**
	 * Название очереди откуда читать входящие сообщения для обработки модулем analyse
	 */
	public static final String MQ_REALTY_ADVERTS_QUEUE = "adverts.crawler.realty";
	
	/**
	 * Название очереди куда писать исходящие сообщения после обработки модулем analyse
	 * На данным момент это очередь модуля подписок. 
	 */
	public static final String MQ_REALTY_ADVERTS_SUBSCRIPTION_QUEUE = "adverts.realty.subscriptions";
	
}
