package kz.aphion.adverts.persistence.adverts;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

/**
 * Изображение объявления
 * @author artem.demidovich
 *
 * Created at Dec 7, 2017
 */
public class AdvertPhoto {

	/**
	 * Тип картинки
	 */
	@Property
	public String mimeType;
	
	/**
	 * Путь к файлу
	 */
	@Property
	public String path;
	
	/**
	 * Ширина
	 */
	@Property
	public Long width;
	
	/**
	 * Высота
	 */
	@Property
	public Long height;
	
	/**
	 * Мини картинки
	 */
	@Embedded
	public List<AdvertPhoto> thumbnails;
	
}
