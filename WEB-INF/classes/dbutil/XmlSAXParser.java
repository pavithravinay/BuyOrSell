package dbutil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import bean.Category;

public class XmlSAXParser extends DefaultHandler {
	
	private Category parentCategory, childCategory;
	// HashMap<Category_ID, Category_Object>
	private static HashMap<Integer, Category> categories = null;
	// HashMap<Parent_Category_ID, Child Categories> for Display Purpose
	private static HashMap<Integer, List<Category>> displayCategories = null;
	private String productCatalogXmlFileName;
	private String elementValueRead;
	private List<Category> childCategories = null;

	private void parseDocument() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			parser.parse(productCatalogXmlFileName, this);
		} catch (ParserConfigurationException e) {
			System.out.println("ProductCatalogDataLoader :: ParserConfig error");
		} catch (SAXException e) {
			System.out.println("ProductCatalogDataLoader :: SAXException : xml not well formed");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("ProductCatalogDataLoader :: IO error");
		}
	}
	
	public HashMap<Integer, Category> getCategoriesFromXml(String xmlFileName) {
		this.productCatalogXmlFileName = xmlFileName;
		categories = new HashMap<Integer, Category>();
		displayCategories = new HashMap<>();
		parseDocument();
		return categories;
	}
	
	public HashMap<Integer, List<Category>> getDisplayCategories() {
		return displayCategories;
	}

	@Override
	public void startElement(String str1, String str2, String elementName, Attributes attributes) throws SAXException {
		if (elementName.equalsIgnoreCase("ParentCategory")) {
			parentCategory = new Category();
			childCategories = new ArrayList<>();
			parentCategory.setId(Integer.parseInt(attributes.getValue("id")));
			parentCategory.setName(attributes.getValue("name"));
			categories.put(parentCategory.getId(), parentCategory);
			return;
		}
		
		if (elementName.equalsIgnoreCase("ChildCategory")) {
			childCategory = new Category();
			childCategory.setId(Integer.parseInt(attributes.getValue("id")));
		}
	}

	@Override
	public void endElement(String str1, String str2, String element) throws SAXException {

		if (element.equals("ChildCategory")) {
			childCategory.setName(elementValueRead);
			childCategories.add(childCategory);
			categories.put(childCategory.getId(), childCategory);
			return;
		}
		
		if (element.equals("ParentCategory")) {
			displayCategories.put(parentCategory.getId(), childCategories);
			return;
		}
	}

	@Override
	public void characters(char[] content, int begin, int end) throws SAXException {
		elementValueRead = new String(content, begin, end);
	}
}
