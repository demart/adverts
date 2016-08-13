package kz.aphion.adverts.analyser.searcher;

/**
 * Общий интерфейс всех искателей дубликатов
 * @author artem.demidovich
 *
 * Created at Jun 12, 2016
 */
public interface DuplicateSearcher {

	public void searchDuplicates(String advertId);
	
}
