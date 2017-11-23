package kz.aphion.adverts.web.api.models;


/**
 * Обертка для того чтобы правильно возвращаться коллецию
 * @author artem.demidovich
 *
 * Created at Nov 22, 2017
 */
public class DataListWrapper  {

	/**
	 * Список записей
	 */
	public Object items;
	
	/**
	 * Всего записей
	 */
	public long total;
	
	/**
	 * Страница
	 */
	public long page;
	
	/**
	 * Всего страниц
	 */
	public long pages;

	public static DataListWrapper with(Object items, long total, long page, long pages) {
		DataListWrapper wrapper = new DataListWrapper();
		wrapper.items = items;
		wrapper.total = total;
		wrapper.page = page;
		wrapper.pages = pages;
		return wrapper;
	}
	
	
}
