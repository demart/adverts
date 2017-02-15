====================================================================================
============================ ADVERS ================================================
====================================================================================

https://ssl.olx.kz/i2/ads/?json=1&

db.getCollection('adverts.realty.flat.sell').find({
    status:"ACTIVE",
    "location.regions" : { $elemMatch: { _id :  ObjectId("5847abffff6ae7e04aa24e04")}}
    }).sort({"publishedAt":-1}).limit(25).skip(0)





- Вынести в common класс с названием всех очередей


3. Реализовать analyse как простой анализатор со старым объявлением
4. Реализовать модуль подписок
5. Реализовать модуль рассылок

	- добавить в анализатор фукнцию сравнения
	- добавить в модуль подписок
		+ добавить поиск объявлений необходимых для уведомления пользователю
		- добавить общих процесс формирования уведомления для рассылки
		- добавить шаблонизатор для Email, SMS, PUSH
		- добавить вызов модуля маркетинга для передачи всех необходимых параметров и получения информации о рекламе
	- добавить модуль подписок уведомлений
		- мнгновенные уведомления
		- поиск по расписанию
		- отправка в очереди + TTL жизни для каждого
	- добавить коммерческие модуль прототип
		- добавить API для обработки запроса от модуля подписок
		- добавить базовую структуру данных
	- подделать краулеры
	- запутить все в месте
	- добавить web проект для клиентов (возможно стянуть с yandex)
	- завершить функцию поиска (добавить типы при помощи рефлексии)

	Notification
		- Добавить изменение статуса уведомления из поступающих колл бэков, добавить упорядоченность статусов. Чтобы вниз не понижался статус
		- Для EMIL добавить процессинг с заменой URL для последующего счетчика переходов + URL base64
		- Добавить web проект или класс который будет принимать URL фиксировать и перенаправлять пользователя
		- Добавить веб проект или класс который будет принимать отметки от мобильных приложений (2 отметки о доставке и о прочтении переходе)

Adverts
	- MQ Что и в каком виде нужно отправлять в очередь обработки

	- Крыша - Получать информацию о пользователях	
	- Проверять нужно ли обновлять существующие объекты (Comparator)
	- Ругаться при ошибках парсинга одного объявления а другие пусть отрабаытваются
	- Разделение логирование на отдельные группы и crawler потоки

	- Описать статистику чтения данных
	- Описать стандартные компоненты для PipeLine
		- AdvertTransformer - конвертация объектов в универсальный вид
		+ MessageQueueProducer - отправка сообщений в очереди


- krisha протестировать

- изменить MQProvide -> к более простому MQ, MNG.DS
- раскладывать crawler по разным файлам (по id crawlers)
- правильно останавливать работу краулеров
	(!!! Добавить флаг проверки перед новой итерацией за списком или за объявлением)


- сделать общий агрегатор не найденных или плохо смапленных объектов на разных краулерах
	Добавить таблицу в БД и считать когда и сколько раз была проблема с нахождением регионов, ЖК и других чувствительных данных
	Основная идея иметь возможность мониторить по БД что происходит, а не перебирать все логи всех экземпляров краулеров

- настроить время краулеров на более соответвующее, напирмер irr не чаще 30 сек или даже 1 мин, так как объявлений не много
- !!!??? irr добавить новые регионы вывод в отдельный файл

=== KN ===
Отсутсвующие ЖК (KN)
	по Байтурсынов
	Жасыл-Астана НС
	Лея-Жасыл
	Лея-Север
	Алтын Орда-3
Requested residental complex with name [Керемет] not found in SELECTED REGION [Есиль].

=== OLX ===
Отсутствующие города OLX
Жайык https://www.olx.kz/i2/ajax/selector/locationcity/?json=1&city_id=3819
OLX Region not found by DistrictID:[443] or CityID:[5633]!!! Please check dictionary, maybe it must be updated!
OLX Region not found by DistrictID:[null] or CityID:[4345]!!! Please check dictionary, maybe it must be updated!
OLX Region not found by DistrictID:[417] or CityID:[5633]!!! Please check dictionary, maybe it must be updated!
OLX Region not found by DistrictID:[null] or CityID:[2207]!!! Please check dictionary, maybe it must be updated!
OLX Region not found by DistrictID:[null] or CityID:[3819]!!! Please check dictionary, maybe it must be updated!
OLX Region not found by DistrictID:[null] or CityID:[1309]!!! Please check dictionary, maybe it must be updated!
OLX Region not found by DistrictID:[null] or CityID:[1387]!!! Please check dictionary, maybe it must be updated!
OLX Region not found by DistrictID:[null] or CityID:[3819]!!! Please check dictionary, maybe it must be updated!
OLX Region not found by DistrictID:[null] or CityID:[925]!!! Please check dictionary, maybe it must be updated!
OLX Region not found by DistrictID:[447] or CityID:[5633]!!! Please check dictionary, maybe it must be updated!
OLX Region not found by DistrictID:[null] or CityID:[3843]!!! Please check dictionary, maybe it must be updated!
OLX Region not found by DistrictID:[null] or CityID:[3819]!!! Please check dictionary, maybe it must be updated!
OLX Region not found by DistrictID:[null] or CityID:[3693]!!! Please check dictionary, maybe it must be updated!
OLX Region not found by DistrictID:[null] or CityID:[5689]!!! Please check dictionary, maybe it must be updated!
OLX Region not found by DistrictID:[417] or CityID:[5633]!!! Please check dictionary, maybe it must be updated!
OLX Region not found by DistrictID:[null] or CityID:[5655]!!! Please check dictionary, maybe it must be updated!
OLX Region not found by DistrictID:[null] or CityID:[787]!!! Please check dictionary, maybe it must be updated!
OLX Region not found by DistrictID:[null] or CityID:[2129]!!! Please check dictionary, maybe it must be updated!
OLX Region not found by DistrictID:[417] or CityID:[5633]!!! Please check dictionary, maybe it must be updated!
OLX Region not found by DistrictID:[null] or CityID:[6133]!!! Please check dictionary, maybe it must be updated!

=== KRISHA ===
2016-12-09 18:50:12 [KRISHA:ALL-FLATS-RENT] ERROR k.a.a.c.krisha.KrishaDataManager - Requested region with id [2111] not found.
2016-12-09 18:50:12 [KRISHA:ALL-FLATS-RENT] ERROR k.a.a.c.krisha.KrishaDataManager - Requested region with id [2107] not found.
2016-12-09 18:46:58 [KRISHA:ALL-FLATS-RENT] ERROR k.a.a.c.krisha.KrishaDataManager - Requested region with id [2151] not found.
2016-12-09 18:48:48 [KRISHA:ALL-FLATS-RENT] ERROR k.a.a.c.krisha.KrishaDataManager - Requested region with id [2139] not found.
2016-12-09 18:48:48 [KRISHA:ALL-FLATS-RENT] ERROR k.a.a.c.krisha.KrishaDataManager - Requested region with id [2151] not found.
2016-12-09 18:48:48 [KRISHA:ALL-FLATS-RENT] ERROR k.a.a.c.krisha.KrishaDataManager - Requested region with id [2151] not found.
2016-12-09 18:49:41 [KRISHA:ALL-FLATS-SELL] ERROR k.a.a.c.k.m.flat.FlatDataMapperUtils - KrishaResidentalComplex with name [864] and region.id [5847abffff6ae7e04aa24e04] not found
2016-12-09 18:48:48 [KRISHA:ALL-FLATS-RENT] ERROR k.a.a.c.k.m.flat.FlatRentDataMapper - ATTENTION! Found new data key: estate.type with value: {4=4}
2016-12-09 18:48:48 [KRISHA:ALL-FLATS-RENT] ERROR k.a.a.c.k.m.flat.FlatRentDataMapper - ATTENTION! Found new data key: estate.is_buss with value: 1
2016-12-09 18:48:48 [KRISHA:ALL-FLATS-RENT] ERROR k.a.a.c.k.m.flat.FlatRentDataMapper - ATTENTION! Found new data key: land.type with value: {1=1}
2016-12-09 18:48:48 [KRISHA:ALL-FLATS-RENT] ERROR k.a.a.c.k.m.flat.FlatRentDataMapper - ATTENTION! Found new data key: com.square with value: 1000
2016-12-09 18:48:48 [KRISHA:ALL-FLATS-RENT] ERROR k.a.a.c.k.m.flat.FlatRentDataMapper - ATTENTION! Found new data key: land.square_au with value: 1
2016-12-09 18:48:48 [KRISHA:ALL-FLATS-RENT] ERROR k.a.a.c.k.m.flat.FlatRentDataMapper - ATTENTION! Found new data key: land.square_a with value: 1
2016-12-09 18:48:48 [KRISHA:ALL-FLATS-RENT] ERROR k.a.a.c.k.m.flat.FlatRentDataMapper - ATTENTION! Found new data key: land.square with value: 100
2016-12-09 18:48:48 [KRISHA:ALL-FLATS-RENT] ERROR k.a.a.c.k.m.flat.FlatRentDataMapper - ATTENTION! Found new data key: com.square with value: 40
2016-12-09 18:48:48 [KRISHA:ALL-FLATS-RENT] ERROR k.a.a.c.k.m.flat.FlatRentDataMapper - ATTENTION! Found new data key: land.square_a with value: 1
2016-12-09 18:48:48 [KRISHA:ALL-FLATS-RENT] ERROR k.a.a.c.k.m.flat.FlatRentDataMapper - ATTENTION! Found new data key: land.square_au with value: 2
2016-12-09 18:48:48 [KRISHA:ALL-FLATS-RENT] ERROR k.a.a.c.k.m.flat.FlatRentDataMapper - ATTENTION! Found new data key: land.square with value: 1


Astana region =   ObjectId("5847abffff6ae7e04aa24e00")
 Есильский район = ObjectId("5847abffff6ae7e04aa24e04")

@GET("/ajax/selector/locationregion/?json=1")
public abstract CitiesResponse listCities(@Query("region_id") String paramString);
  
@GET("/ajax/selector/locationcity/?json=1")
public abstract DistrictsResponse listDistrict(@Query("city_id") String paramString);
  
@GET("/ajax/selector/location/?json=1")
public abstract RegionsResponse listRegions(@Query("adding") String paramString);
 

====================================================================================
====================================================================================

Задачи
	- Завершить конвертацию объекта OLX
		- Проверять если изменения касаются только даты объявления то обновлять только дату и всё
		- Выделять категории изменений которые влияют на уведомления (цена, фото, характристики)

		- Добавить фоновую проверку на наличие обновлений загруженных ранее объявлений (должно быть настраиваемое время)
		- Посмотреть алгоритм для выявления повторяющихся слов для последующей возможности добавть анализатор ко тексту
		- Добавить к идеям информацию о том сколько будет стоить месячная квартплата (на основе КСК и тарифов на комуналки + интернет)

	- Выгрузить все варианты описаний квартир для того чтобы упорядочить их по появлению в текстах -> Выработать базу знаний для конвертации в атрибуты
		- Добавить механизм определения разницы во время публикации того же объявления (например 1 неделю не было объявления и оно появилось
			то значит что его опубликовали повторно и клиентам возможно интересно об этом узнать), время должно быть определено в ходе разработки

====================================================================================
===================================== DONE =========================================
====================================================================================

+ Перейти в проектах на MQ DB
+ Погдоготовить проекты других crawlers
+ olx протестировать
+ OLX вынести в опции crawler адрес для проверки телефона (сейчас захардкордено)
+ OLX понизить логирование чтобы видеть реально полезную информацию (какую не понятно пока)
+ OLX Выгружать контент телефона и настроект тоже с учетом пожеланий в краулере (включать proxy, подставлять заголовки)
+ OLX добавить обработку регионов и городов на OLX (они добавили поля)

+ kn протестировать
+ kn - добавить вывод всех новых регионов (в новый файл копию)
+ kn - добавить вывод всех новых ЖК (в новый файл комию) 

+ irr протестировать
+ irr проверить почему ошибки при отправке телефона
+ irr проверить почему offset не увеличивается

+ пофиксить ошибки в проектах
+ Избавиться от grandle
+ Перехеать на tomcat (tomee, jboss)
+ Перенести postgres to mongo
+ Перенести play на tomcat
