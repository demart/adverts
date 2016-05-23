package kz.aphion.adverts.crawler.olx;

/**
 * Типы категорий объявлений на сайте OLX
 * 
 * @author artem.demidovich
 *
 * Created at May 19, 2016
 */
public enum OlxAdvertCategoryType {

	/**
	 * Все рубрики
	 */
	ALL_ADVERT(0),
	
	/**
	 * Недвижимость
	 * Всё в рубрике Недвижимость
	 */
	REAL_ESTATE(1),
	
	/**
	 * Аренда комнат
	 */
	REAL_ESTATE_RENT_ROOM(9),
	
	/**
	 * Недвижимость -> Аренда комнат -> Койко-места
	 */
	REAL_ESTATE_RENT_ROOM_BED(328),
	
	/**
	 * Недвижимость -> Аренда комнат -> Комнаты посуточно
	 */
	REAL_ESTATE_RENT_ROOM_DAILY(329),
	
	/**
	 * Недвижимость -> Аренда комнат -> Долгосрочная аренда комнат
	 */
	REAL_ESTATE_RENT_ROOM_LONG_TERM(1149),
	
	/**
	 * Недвижимость -> Продажа земли
	 */
	REAL_ESTATE_SELL_LAND(10),
	
	
	/**
	 * Недвижимость -> Продажа земли -> Продажа земли под индивидуальное строительство
	 */
	REAL_ESTATE_SELL_LAND_INDIVIDUAL_CONSTRACTIONS(25),
	
	/**
	 * Недвижимость -> Продажа земли -> Продажа земли промышленного назначения
	 */
	REAL_ESTATE_SELL_LAND_INDUSTRIAL(26),
	
	/**
	 * Недвижимость -> Продажа земли -> Продажа земли под сад / огород
	 */
	REAL_ESTATE_SELL_LAND_GARDEN(26),

	/**
	 * Недвижимость -> Продажа земли -> Продажа земли сельскохозяйственного назначения
	 */
	REAL_ESTATE_SELL_LAND_AGRO(26),

	/**
	 * Недвижимость -> Аренда помещений
	 */
	REAL_ESTATE_RENT_SPACE(11),

	/**
	 * Недвижимость -> Аренда помещений -> Аренда офисов
	 */
	REAL_ESTATE_RENT_SPACE_OFFICE(27),		

	/**
	 * Недвижимость -> Аренда помещений -> Аренда магазинов / салонов
	 */
	REAL_ESTATE_RENT_SPACE_SHOP_SALOON(211),	

	/**
	 * Недвижимость -> Аренда помещений -> Аренда складов
	 */
	REAL_ESTATE_RENT_SPACE_WAREHOUSE(212),	
	
	/**
	 * Недвижимость -> Аренда помещений -> Прочее
	 */
	REAL_ESTATE_RENT_SPACE_OTHERS(212),

	/**
	 * Недвижимость -> Аренда помещений -> Аренда ресторанов / баров
	 */
	REAL_ESTATE_RENT_SPACE_RESTAURANT(335),

	/**
	 * Недвижимость -> Аренда помещений -> Аренда отдельно стоящих зданий
	 */
	REAL_ESTATE_RENT_SPACE_DEDICATED_BUILDING(336),

	/**
	 * Недвижимость -> Аренда помещений -> Аренда баз отдыха
	 */
	REAL_ESTATE_RENT_SPACE_RECREATION(337),

	/**
	 * Недвижимость -> Аренда помещений -> Аренда помещений промышленного назначения
	 */
	REAL_ESTATE_RENT_SPACE_INDUSTRIAL(338),

	/**
	 * Недвижимость -> Аренда помещений -> Аренда помещений свободного назначения
	 */
	REAL_ESTATE_RENT_SPACE_FREE_DESTINATION(338),				

	/**
	 * Недвижимость -> Продажа квартир
	 */
	REAL_ESTATE_SELL_FLAT(13),				

	/**
	 * Недвижимость -> Продажа квартир -> Вторичный рынок
	 */
	REAL_ESTATE_SELL_FLAT_USED(207),		
	
	/**
	 * Недвижимость -> Продажа квартир -> Новостройки
	 */
	REAL_ESTATE_SELL_FLAT_NEW(345),	
	
	/**
	 * Недвижимость -> Продажа помещений
	 */
	REAL_ESTATE_SELL_SPACE(14),	

	/**
	 * Недвижимость -> Продажа помещений -> Продажа магазинов / салонов
	 */
	REAL_ESTATE_SELL_SPACE_SHOP_SALOON(216),

	/**
	 * Недвижимость -> Продажа помещений -> Продажа офисов
	 */
	REAL_ESTATE_SELL_SPACE_OFFICE(217),	

	/**
	 * Недвижимость -> Продажа помещений -> Продажа складов
	 */
	REAL_ESTATE_SELL_SPACE_WAREHOUSE(218),	

	/**
	 * Недвижимость -> Продажа помещений -> Продажа ресторанов / баров
	 */
	REAL_ESTATE_SELL_SPACE_RESTAURANT(351),

	/**
	 * Недвижимость -> Продажа помещений -> Продажа отдельно стоящих зданий
	 */
	REAL_ESTATE_SELL_SPACE_DEDICATED_BUILDING(352),	

	/**
	 * Недвижимость -> Продажа помещений -> Продажа баз отдыха
	 */
	REAL_ESTATE_SELL_SPACE_RECREATION(353),	

	/**
	 * Недвижимость -> Продажа помещений -> Продажа помещений промышленного назначения
	 */
	REAL_ESTATE_SELL_SPACE_INDUSTRIAL(354),
	
	/**
	 * Недвижимость -> Продажа помещений -> Продажа помещений свободного назначения
	 */
	REAL_ESTATE_SELL_SPACE_FREE_DESTINATION(355),
	
	/**
	 * Недвижимость -> Аренда квартир
	 */
	REAL_ESTATE_RENT_FLAT(15),
	
	/**
	 * Недвижимость -> Аренда квартир -> Квартиры посуточно
	 */
	REAL_ESTATE_RENT_FLAT_DAILY(31),		
	
	/**
	 * Недвижимость -> Аренда квартир -> Квартиры с почасовой оплатой
	 */
	REAL_ESTATE_RENT_FLAT_HOURLY(32),	
	
	/**
	 * Недвижимость -> Аренда квартир -> Долгосрочная аренда квартир
	 */
	REAL_ESTATE_RENT_FLAT_LONG_TERM(1147),	

	/**
	 * Недвижимость -> Аренда квартир -> Аренда квартир без посредников
	 */
	REAL_ESTATE_RENT_FLAT_BY_OWNERS(1155),
	
	/**
	 * Недвижимость -> Обмен недвижимости
	 */
	REAL_ESTATE_EXCHANGE(16),
		
	/**
	 * Недвижимость -> Продажа домов
	 */
	REAL_ESTATE_SELL_HOUSE(18),
		
	/**
	 * Недвижимость -> Продажа домов -> Продажа домов в городе
	 */
	REAL_ESTATE_SELL_HOUSE_IN_CITY(346),	
	
	/**
	 * Недвижимость -> Продажа домов -> Продажа домов за городом
	 */
	REAL_ESTATE_SELL_HOUSE_OUTSIDE_CITY(347),	
	
	/**
	 * Недвижимость -> Продажа домов -> Продажа дач
	 */
	REAL_ESTATE_SELL_HOUSE_DACHA(348),
	
	/**
	 * Недвижимость -> Аренда земли
	 */
	REAL_ESTATE_RENT_LAND(20),
	
	/**
	 * Недвижимость -> Продажа гаражей / стоянок
	 */
	REAL_ESTATE_SELL_GARAGE_PARKING(21),	
	
	/**
	 * Недвижимость -> Ищу компаньона
	 */
	REAL_ESTATE_COMPANION(22),	
	
	/**
	 * Недвижимость -> Аренда гаражей / стоянок
	 */
	REAL_ESTATE_RENT_GARAGE_PARKING(28),
	
	/**
	 * Недвижимость -> Аренда домов
	 */
	REAL_ESTATE_RENT_HOUSE(206),
	
	/**
	 * Недвижимость -> Аренда домов -> Долгосрочная аренда домов
	 */
	REAL_ESTATE_RENT_HOUSE_LONG_TERM(330),	

	/**
	 * Недвижимость -> Аренда домов -> Дома посуточно, почасово
	 */
	REAL_ESTATE_RENT_HOUSE_DAILY_HOURLY(331),

	/**
	 * Недвижимость -> Продажа комнат
	 */
	REAL_ESTATE_SELL_ROOM(1137),	

	/**
	 * Недвижимость -> Прочая недвижимость
	 */
	REAL_ESTATE_OTHER(1423);
	
	private final int value;

	OlxAdvertCategoryType(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }
	
	
}
