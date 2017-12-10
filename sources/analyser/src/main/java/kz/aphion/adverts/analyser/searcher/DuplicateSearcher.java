package kz.aphion.adverts.analyser.searcher;

import kz.aphion.adverts.persistence.adverts.Advert;

/**
 * Общий интерфейс всех искателей дубликатов
 * @author artem.demidovich
 *
 * Created at Jun 12, 2016
 */
public interface DuplicateSearcher {

	public void searchDuplicates(Advert advert);
	
}
