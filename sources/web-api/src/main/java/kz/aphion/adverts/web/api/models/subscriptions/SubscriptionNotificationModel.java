package kz.aphion.adverts.web.api.models.subscriptions;

import java.util.Calendar;
import java.util.List;

import kz.aphion.adverts.persistence.subscription.notification.SubscriptionNotification;
import kz.aphion.adverts.persistence.subscription.notification.SubscriptionNotificationChannelType;
import kz.aphion.adverts.persistence.subscription.notification.SubscriptionNotificationScheduledType;
import kz.aphion.adverts.persistence.subscription.notification.SubscriptionNotificationType;

/**
 * Модель параметров уведомлений по подписке
 * @author artem.demidovich
 *
 * Created at Nov 22, 2017
 */
public class SubscriptionNotificationModel {

	/**
	 * Тип уведолмений для пользователя
	 */
	public SubscriptionNotificationType type;
	
	/**
	 * Не уведолмять ночью с 23:00 по 09:00 например)
	 */
	public Boolean doNotDisturbAtNight;
	
	/**
	 * Отслеживает время подследнего уведомления отправленного пользователю
	 */
	public Calendar lastNotifiedTime;
	
	/**
	 * Время следующего запуска не ранее указанного времени.
	 * 
	 * Так как у нас есть критрий запуска по времени, сложно вычислять каждый раз
	 * разницу между текущим временем > время последнего запуска + заданный интервал.
	 * Проще пересчитывать время один раз и выполнять простой поиск. Даже если запуск
	 * будет не точный, ничего страшного. так как погрешность будет равна интервалу
	 * запуска job процесса по поиску подписок, что априори должно быть не реже 1 раза 
	 * в минуту. 
	 * 
	 * Поле должно каждый раз обновляться как только подписка попадает под критерии
	 * поиска для рассылки объявлений. 
	 */
	public Calendar nextSchedulerScanTime;
	
	/**
	 * Кол-во новых объявлений о которые еще не видел пользователь.
	 * Счетчик должно безопасно увеличиваться или уменьшаться, так как 
	 * есть риск потенциальных проблем при работе с многопоточностью.
	 * 
	 * Основная идея этого поля, вести учет кол-ва объявлений, чтобы потом быстро понять
	 * Нужно ли уже уведомлять пользователя или нет при выборе режимов уведомлений по
	 * 1. Кол-ву записей
	 * 2. Кол-ву записей и/или минимальному интревалу перед уведомлениями
	 * 
	 */
	public Integer newAdvertsCount = 0;
	
	/**
	 * Поле показывает превышено ли минимальное кол-во записей
	 * после которых можно уведомлять пользователя. Этот подход
	 * выглядит немного не красиво, но на текущий момент сойдет.
	 * 
	 * Процесс следующий:
	 * 	1. Получаем новое объявлени
	 * 	2. Добавляем в результаты
	 * 	3. Увеличиваем счетчик newAdvertsCount (если режим с кол-вом записей)
	 * 	4. Проверяем true или false и сохраняем
	 * 	5. Если true тогда searcher находит обрабатывает и обнуляет значения.
	 * 	6. Процесс повторяется.
	 * 
	 * Есть только проблема с многопоточностью, но на данный момент всё будет
	 * работать в одном потоке, по крайней мере разные типы объявлений.
	 */
	public Boolean isMinimumAmountAchieved = false;
	
	/**
	 * Подвиды отложенных уведомлений
	 */
	public SubscriptionNotificationScheduledType scheduledType;
	
	/**
	 * В случае если выбран режим уведомлений по расписанию то 
	 * необходимо запускать уведомления каждые интервалы времени<br>
	 * Время указывается в минутах, поэтому если необходимо указать раз в час<br>
	 * Every 60 mins<br>
	 * Every 720 mins - 12 hours<br>
	 * etc.<br>
	 * Если < 1 то не учитывается при просмотре, по умолчанию берется в расчет scheduledRunAfter
	 */
	public Integer scheduledRunEvery;
	
	/**
	 * В случае если выбран режим уведомлений по расписанию то
	 * опиционально можно указать сколько необходимо записей чтобы отправить уведомление
	 * заранее.
	 * Например:
	 * 	Пользователь указан раз в 1 час уведомлять, но при этом указал что если будет
	 * 10 новых записей в подписке, то уведомить заранее.
	 * Если меньше 1 то не учитывается
	 */
	public Integer scheduledRunAfter;
	
	/**
	 * Разрешенные каналы рассылки уведолмений, если ниуказан ни один тип или указан UNSPECIFIED
	 * то будут учитываьтся все каналы которые есть у пользователя.
	 * Если указаны другие каналы, то будут рассматриваться только они.
	 * Если указан UNSPECIFIED и MOBILE, по умолчанию считается что пользователь выбрал ТОЛЬКО
	 * канал MOBILE (в дальнейшем логику можно изменить)
	 */
	public List<SubscriptionNotificationChannelType> channels;

	public static SubscriptionNotificationModel convertToModel(SubscriptionNotification notification) {
		if (notification == null)
			return null;
		
		SubscriptionNotificationModel model = new SubscriptionNotificationModel();
		
		model.channels = notification.channels;
		model.doNotDisturbAtNight = notification.doNotDisturbAtNight;
		model.isMinimumAmountAchieved = notification.isMinimumAmountAchieved;
		model.lastNotifiedTime = notification.lastNotifiedTime;
		model.newAdvertsCount = notification.newAdvertsCount;
		model.nextSchedulerScanTime = notification.nextSchedulerScanTime;
		model.scheduledRunAfter = notification.scheduledRunAfter;
		model.scheduledRunEvery = notification.scheduledRunEvery;
		model.scheduledType = notification.scheduledType;
		model.type = notification.type;
		
		return model;
		
	}
	
}
