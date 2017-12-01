package com.davit.ymlparser.parser;

import com.davit.ymlparser.model.YMLCatalog;

/**
 * author by davitpetrosyan on 11/29/17.
 */
public interface Parser {

	YMLCatalog parse(String fileName);

}
