package com.davit.ymlparser.parser;


import com.davit.ymlparser.model.Category;
import com.davit.ymlparser.model.Currency;
import com.davit.ymlparser.model.DetailedParam;
import com.davit.ymlparser.model.Offer;
import com.davit.ymlparser.model.Shop;
import com.davit.ymlparser.model.YMLCatalog;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * author by davitpetrosyan on 11/29/17.
 */
public class StaxParser implements Parser {


	private static YMLCatalog ymlCatalog;
	private static Shop shop;

	private static List<Category> categories = Collections.EMPTY_LIST;
	private static Category category;

	private static List<Currency> currencies = Collections.EMPTY_LIST;;
	private static Currency currency;

//	private static List<Offer> offers = null;

	private static Map<Integer, List<Offer>> groupOffers = Collections.EMPTY_MAP;;
	private static Offer offer;


	/*@Override
	public void parse() {
		parseXML("/home/david.petrosyan@synisys.com/Downloads/yml_test_new.xml");
	}*/


	public YMLCatalog parse(String fileName) {

		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		try {
			XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(fileName));
			while(xmlEventReader.hasNext()){
				XMLEvent xmlEvent = xmlEventReader.nextEvent();
				if (xmlEvent.isStartElement()){
					StartElement startElement = xmlEvent.asStartElement();

					String localPart = startElement.getName().getLocalPart();
					switch (localPart) {
						case XML.CATALOG:
							ymlCatalog = new YMLCatalog();

							Attribute dateAttr = startElement.getAttributeByName(new QName("date"));
							if (dateAttr != null) {

								DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
								Date startDate;
								try {
									startDate = df.parse(dateAttr.getValue());
									ymlCatalog.setDate(startDate);
								} catch (ParseException e) {
									e.printStackTrace();
								}
							}
							break;
						case XML.SHOP:
							shop = new Shop();
							break;
						case XML.CURRENCIES:
							currencies = new ArrayList<>();
							break;
						case XML.CURRENCY:
							Attribute currencyIdAttr = startElement.getAttributeByName(new QName("id"));
							if (currencyIdAttr != null) {
								currency = new Currency();
								currency.setValue(currencyIdAttr.getValue());
								currencies.add(currency);
							}

							break;
						case XML.CATEGORIES:
							categories = new ArrayList<>();
							break;
						case XML.CATEGORY:
							Attribute categoryIdAttr = startElement.getAttributeByName(new QName("id"));
							category = new Category();
							if (categoryIdAttr != null) {
								category.setId(Integer.parseInt(categoryIdAttr.getValue()));
							}
							xmlEvent = xmlEventReader.nextEvent();
							category.setName(xmlEvent.asCharacters().getData());
							categories.add(category);
							break;
						case XML.OFFERS:
							groupOffers = new HashMap<>();
							break;
						case XML.OFFER:
							offer = new Offer();
							Attribute idAttr = startElement.getAttributeByName(new QName("id"));
							if(idAttr != null){
								offer.setId(idAttr.getValue());
							}
							Attribute availableAttr = startElement.getAttributeByName(new QName("available"));
							if(availableAttr != null){
								offer.setIsAvailable(Boolean.parseBoolean(availableAttr.getValue()));
							}
							Attribute groupIdAttr = startElement.getAttributeByName(new QName("group_id"));
							if(groupIdAttr != null){
								offer.setGroupId(Integer.parseInt(groupIdAttr.getValue()));
							}
							collectOffer(xmlEventReader);
							break;
					}

				}
				else if(xmlEvent.isEndElement()){
					handleEndElement(xmlEvent);
				}

			}

		} catch (FileNotFoundException | XMLStreamException e) {
			e.printStackTrace();
		}
		return ymlCatalog;
	}

	private static void collectOffer(XMLEventReader xmlEventReader) throws XMLStreamException {

		while (xmlEventReader.hasNext()) {
			XMLEvent xmlEvent = xmlEventReader.nextEvent();
			if (xmlEvent.isStartElement()) {
				StartElement startElement = xmlEvent.asStartElement();

				xmlEvent = xmlEventReader.nextEvent();
				if(xmlEvent.isCharacters()) {
					String localPart = startElement.getName().getLocalPart();

					switch (localPart) {
						case XML.OFFER_PRICE:
							offer.setPrice(parsePrice(xmlEvent.asCharacters().getData()));
							break;
						case XML.OFFER_CURRENCY:
							offer.setCurrencyId(xmlEvent.asCharacters().getData());
							break;
						case XML.OFFER_CATEGORY:
							offer.setCategoryId(Integer.parseInt(xmlEvent.asCharacters().getData()));
							break;
						case XML.OFFER_PICTURE_URL:
							List<String> pictureURLs = offer.getPictureURLs();
							if (pictureURLs == null) {
								pictureURLs = new ArrayList<>();
							}
							pictureURLs.add(xmlEvent.asCharacters().getData());
							offer.setPictureURLs(pictureURLs);
							break;
						case XML.OFFER_DESCRIPTION:
							offer.setDescription(xmlEvent.asCharacters().getData());
							break;
						case XML.OFFER_VENDOR:
							offer.setVendor(xmlEvent.asCharacters().getData());
							break;
						case XML.OFFER_VENDOR_CODE:
							offer.setVendorCode(xmlEvent.asCharacters().getData());
							break;
						case XML.OFFER_NAME:
							offer.setName(xmlEvent.asCharacters().getData());
							break;
						case XML.OFFER_PARAM:
							List<DetailedParam> params = offer.getParams();
							if (params == null) {
								params = new ArrayList<>();
							}
							Attribute name = startElement.getAttributeByName(new QName("name"));
							DetailedParam detailedParam = new DetailedParam();
							detailedParam.setName(name.getValue());
							detailedParam.setValue(xmlEvent.asCharacters().getData());
							params.add(detailedParam);
							offer.setParams(params);
							break;
					}
				}

			}
			else if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals("offer")) {

				List<Offer> offers = groupOffers.get(offer.getGroupId());
				if(offers == null) {
					offers = new ArrayList<>();
				}
				offers.add(offer);

				break;
			}
		}
	}

	private static Double parsePrice(String data) {
		data = data.replace(",", ".");
		return Double.parseDouble(data);
	}


	private static void handleEndElement(XMLEvent xmlEvent ) {
		EndElement endElement = xmlEvent.asEndElement();
		String localPart = endElement.getName().getLocalPart();

		switch (localPart) {
			case XML.CATALOG:
				ymlCatalog.setShop(shop);
				break;
			case XML.SHOP:
				shop.setCategories(categories);
				shop.setCurrencies(currencies);
				shop.setOffers(groupOffers);
				break;
		}
	}


	/**
	 * Class for XML tag and attribute constants for loading model
	 *
	 */
	private class XML {
		static final String OFFER_PRICE             = "price";
		static final String OFFER_CURRENCY          = "currencyId";
		static final String OFFER_CATEGORY          = "categoryId";
		static final String OFFER_NAME              = "name";
		static final String OFFER_PICTURE_URL       = "picture";
		static final String OFFER_DESCRIPTION       = "description";
		static final String OFFER_PARAM             = "param";
		static final String OFFER_VENDOR            = "vendor";
		static final String OFFER_VENDOR_CODE       = "vendorCode";


		static final String CATALOG                 = "yml_catalog";
		static final String SHOP                    = "shop";
		static final String CURRENCIES              = "currencies";
		static final String CURRENCY                = "currency";
		static final String CATEGORIES              = "categories";
		static final String CATEGORY                = "category";
		static final String OFFERS                  = "offers";
		static final String OFFER                   = "offer";

		private XML() {
		}
	}

}
