package kz.aphion.adverts.analyser.comparator;

import kz.aphion.adverts.analyser.mq.AnalyserProcessStatus;

/**
 * Интерфейс для серии классов реализующих сравнение объектов, как новый объект изменился
 * по отношении к старому. А именно в какую сторону лучшую или худшую 
 *  
 * @author artem.demidovich
 *
 * Created at Feb 27, 2017
 */
public interface AdvertComparator {

	/**
	 * Метод выполняет сравнение двух версий объявления и принимает решение лучше или хуже
	 * новая версия объявления
	 * @param newAdvertId
	 * @param oldAdvertId
	 */
	public AnalyserProcessStatus compare(String newAdvertId, String oldAdvertId);
	
}