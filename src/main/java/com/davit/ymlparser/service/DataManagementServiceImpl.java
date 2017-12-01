package com.davit.ymlparser.service;

import com.davit.ymlparser.dao.DataManagementDao;
import com.davit.ymlparser.model.Shop;
import com.davit.ymlparser.model.YMLCatalog;
import com.davit.ymlparser.parser.Parser;
import com.davit.ymlparser.parser.StaxParser;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * author by davitpetrosyan on 11/30/17.
 */
public class DataManagementServiceImpl implements DataManagementService {

	private DataManagementDao dataManagementDao;

	@Autowired
	public DataManagementServiceImpl(DataManagementDao h2DataManagementDao) {
		this.dataManagementDao = h2DataManagementDao;
	}


	@Override
	public void syncData() {
		Parser parser = new StaxParser();
		YMLCatalog parsedData = parser.parse("");

		Shop shop = parsedData.getShop();
		dataManagementDao.saveOrUpdateCurrencies(shop.getCurrencies());
		dataManagementDao.saveOrUpdateCategories(shop.getCategories());
		dataManagementDao.saveOrUpdateOffers(shop.getOffers());

	}

}
