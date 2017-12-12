package kz.aphion.adverts.web.api.search;

import java.util.List;

import kz.aphion.adverts.persistence.adverts.Advert;
import kz.aphion.adverts.web.api.query.SearchAdvertQuery;

public interface AdvertSearch {

	public List<Advert> search(SearchAdvertQuery query) throws Exception;

	public Long count(SearchAdvertQuery query) throws Exception;
	
}
