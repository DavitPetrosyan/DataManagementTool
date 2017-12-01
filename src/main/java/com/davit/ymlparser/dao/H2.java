package com.davit.ymlparser.dao;

import com.davit.ymlparser.model.Category;
import com.davit.ymlparser.model.Currency;
import com.davit.ymlparser.model.Offer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author david.petrosyan@synisys.com on 11/30/17.
 */
public class H2 implements DataManagementDao {

	private static final String DB_DRIVER = "org.h2.Driver";
	private static final String DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";


	private static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}

	@Override
	public void saveOrUpdateCurrencies(Collection<Currency> newCurrencies) {

		removeAllCurrencies();
		saveCurrencies(newCurrencies);
	}

	@Override
	public void saveCurrencies(Collection<Currency> currencies) {
		
	}

	@Override
	public void updateCurrencies(Collection<Currency> currencies) {

	}

	@Override
	public void removeCurrencies(Collection<Currency> currencies) {

	}

	@Override
	public void removeAllCurrencies() {

	}

	@Override
	public Map<String, Currency> getCurrenciesByValues(List<String> incomeIdsList) {
		return null;
	}

	@Override
	public void saveOrUpdateCategories(Collection<Category> newCategories) {

		List<Integer> newCategoryIdsList = newCategories
				.stream()
				.map(Category::getId)
				.collect(Collectors.toList());

		Map<Integer, Category> categoriesOldData = getCategoriesByIds(newCategoryIdsList);


		List<Category> toBeAdded = new ArrayList<>();
		List<Category> toBeUpdated = new ArrayList<>();
//		List<Category> toBeRemoved = new ArrayList<>();

		newCategories
				.stream()
				.forEach(category -> {
					Integer categoryId = category.getId();
					Category c = categoriesOldData.get(categoryId);
					if(c == null) {
						toBeAdded.add(category);
					} else {
						toBeUpdated.add(category);
					}
					categoriesOldData.remove(categoryId);
				});


		saveCategories(toBeAdded);
		updateCategories(toBeUpdated);
		removeCategories(categoriesOldData.values());


	}

	@Override
	public void saveCategories(Collection<Category> categories) {

	}

	@Override
	public void updateCategories(Collection<Category> categories) {

	}

	@Override
	public void removeCategories(Collection<Category> categories) {

	}

	@Override
	public Map<Integer, Category> getCategoriesByIds(List<Integer> incomeIdsList) {
		return null;
	}

	@Override
	public void saveOrUpdateOffers(Map<Integer, List<Offer>> offers) {
		List<Offer> all = new ArrayList<>();
		offers.values().stream().map(offer -> all.addAll(offer));
		List<String> allIds = all.stream().map(Offer::getId).collect(Collectors.toList());

//		List<Offer> oldOffers = getOffersByIds(allIds);
	}
}
