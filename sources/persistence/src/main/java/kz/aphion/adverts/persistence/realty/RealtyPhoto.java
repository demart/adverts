package kz.aphion.adverts.persistence.realty;

import java.util.List;

import kz.aphion.adverts.persistence.BaseEntity;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.NotSaved;
import org.mongodb.morphia.annotations.Property;

/**
 * Фотографии объекта недвижимости
 * 
 * @author artem.demidovich
 *
 */
@Entity
public class RealtyPhoto extends BaseEntity {

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
	public List<RealtyPhoto> thumbnails;
	
	/**
	 * Ссылка на объявление к которому относиться данное фото
	 * 
	 * NotSaved - потому что для Mongo это будет встроенный объект, и обратная ссылка не нужна.
	 * Только в случае реляционного подхода
	 */
	@NotSaved
	public Realty realty;
	
}
