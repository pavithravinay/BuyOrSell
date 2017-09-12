package dbutil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import bean.Category;

public class CacheHandler {

	// HashMap<Category_ID, Category_Object>
	private static HashMap<Integer, Category> categories = null;
	
	// HashMap<Parent_Category_ID, Child Categories> for Display Purpose
	private static HashMap<Integer, List<Category>> displayCategories = null;
	
	/* Rohan */
	private static HashMap<Integer, String> condition = new HashMap<>();
	private static String CONDITION_NEW = "NEW";
	private static String CONDITION_OLD = "OLD";
	/* Rohan */
	
	private CacheHandler() {

	}
	
	/*public static void buildCacheFromDatabase() {
		categories = new HashMap<>();
		displayCategories = new HashMap<>();
		
		DataManager dataManager = new DataManager();
		CachedRowSet rs = dataManager.getCategories();
		try {
			if (rs != null) {
				while (rs.next()) {
					Category category = new Category(rs.getInt("id"), rs.getString("name"),
							rs.getInt("parentCategory"));
					categories.put(category.getId(), category);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for(Category c : categories.values()) {
			if (c.getParentCategory() != 0) {
				if (displayCategories.containsKey(c.getParentCategory())) {
					displayCategories.get(c.getParentCategory()).add(c);
				} else {
					List<Category> list = new ArrayList<>();
					list.add(c);
					displayCategories.put(c.getParentCategory(), list);
				}
			} else if (!displayCategories.containsKey(c.getId())) {
				displayCategories.put(c.getId(), new ArrayList<Category>());
			}
		}
		//System.out.println(displayCategories);
	}*/
	
	public static void buildCacheFromDatabase(String xmlFileName) {
		XmlSAXParser parser = new XmlSAXParser();
		categories = parser.getCategoriesFromXml(xmlFileName);
		displayCategories = parser.getDisplayCategories();
	}
	
	public static HashMap<Integer, Category> getCategories() {
		/*if (categories == null) {
			buildCacheFromDatabase();
		}*/
		return categories;
	}
	
	public static HashMap<Integer, List<Category>> getArrangedCategories() {
		/*if (displayCategories == null) {
			buildCacheFromDatabase();
		}*/
		return displayCategories;
	}
	
	public static Category getParentCategory(int categoryId) {
		if (categories != null) {
			int parentId = categories.get(categoryId).getParentCategory();
			if(parentId != 0)
				return categories.get(parentId);
		}
		return null;
	}
	
	public static Category getCategory(int categoryId) {
		if (categories != null) {
			return categories.get(categoryId);
		}
		return null;
	}
	
	/* Rohan */
	public static HashMap<Integer, String> getItemConditions() {
		condition.put(1, CONDITION_NEW);
		condition.put(2, CONDITION_OLD);
		return condition;
	}
	/* Rohan */
}
