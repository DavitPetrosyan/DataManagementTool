package com.davit.ymlparser.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * author by davitpetrosyan on 11/29/17.
 */
public class Shop {

	private Collection<Currency> currencies;
	private Collection<Category> categories;
	private Map<Integer, List<Offer>> offers;

	public Collection<Currency> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(Collection<Currency> currencies) {
		this.currencies = currencies;
	}

	public Collection<Category> getCategories() {
		return categories;
	}

	public void setCategories(Collection<Category> categories) {
		this.categories = categories;
	}

	public Map<Integer, List<Offer>> getOffers() {
		return offers;
	}

	public void setOffers(Map<Integer, List<Offer>> offers) {
		this.offers = offers;
	}
}
