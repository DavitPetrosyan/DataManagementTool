package com.davit.ymlparser.dao;

import com.davit.ymlparser.model.Category;
import com.davit.ymlparser.model.Currency;
import com.davit.ymlparser.model.Offer;

import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * author by davitpetrosyan on 11/30/17.
 */
public interface DataManagementDao {

	void saveOrUpdateCurrencies(Collection<Currency> currencies);
	void saveCurrencies(Collection<Currency> currencies);
	void updateCurrencies(Collection<Currency> currencies);
	void removeCurrencies(Collection<Currency> currencies);
	void removeAllCurrencies();
	Map<String, Currency> getCurrenciesByValues(List<String> idsList);


	void saveOrUpdateCategories(Collection<Category> categories);
	void saveCategories(Collection<Category> categories);
	void updateCategories(Collection<Category> categories);
	void removeCategories(Collection<Category> categories);
	Map<Integer, Category> getCategoriesByIds(List<Integer> idsList);


	void saveOrUpdateOffers(Map<Integer, List<Offer>> offers);
}
