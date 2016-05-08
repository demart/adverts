package kz.aphion.adverts.persistence.phones;

import kz.aphion.adverts.persistence.BaseEntity;

import org.mongodb.morphia.annotations.Entity;

/**
 * Класс описывающий коллекцию телефонов полученных из разных источников
 * 
 * @author artem.demidovich
 *
 */
@Entity("adverts.phones")
public class Phone extends BaseEntity {

	
}
