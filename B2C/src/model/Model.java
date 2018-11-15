package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.catalog.Category;
import model.catalog.Item;
import model.dao.CategoryDAO;
import model.dao.ItemDAO;

public class Model {
	private static Model instance = null;
	private static ItemDAO itemDao;
	private static CategoryDAO catDao;
	
	private Model() {
		try {
			itemDao = new ItemDAO();
			catDao = new CategoryDAO();
		} catch(Exception e) {
			System.out.println("DB ERROR: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static Model getInstance() {
		if (instance == null) {
			instance = new Model();
		}
		return instance;
	}
	
	public List<Item> getFoods() throws Exception {
		return getFoods("0");
	}
	
	public List<Item> getFoods(String limit) throws Exception {
		int l = Integer.parseInt(limit);
		return itemDao.getAll(l);
	}
	
	public Item getFood(String val) throws Exception {
		return this.getFoodBy("id", val);
	}
	
	public Item getFoodBy(String by, String val) throws Exception {
		return itemDao.findOneBy(by, val, false);
	}

	public List<Item> getFoodsBy(String by, String val) throws Exception {
		return itemDao.getAllBy(by, val, true);
	}
	
	public List<Item> getFoodsBy(String by, String val, Boolean like) throws Exception {
		return getFoodsBy(by, val, like, "0");
	}
	
	public List<Item> getFoodsBy(String by, String val, Boolean like, String limit) throws Exception {
		int l = Integer.parseInt(limit);
		return itemDao.getAllBy(by, val, like, l);
	}
	
	public List<Category> getCategories() throws Exception {
		return catDao.getAll();
	}
	
	public Category getCategory(String cat) throws Exception {
		return catDao.findOne(cat);
	}
	
	public Map<String, List<Item>> getCatNameWithFoods() throws Exception {
		Map<String, List<Item>> result = new HashMap<String, List<Item>>();

		for (Category cat: this.getCategories()) {
			List<Item> items = new ArrayList<Item>();
			for (Item item: this.getFoods()) {
				if (cat.getId() == item.getCatID()) {
					items.add(item);
					result.put(cat.getName(), items);
				}
			}
		}

		return result;
	}
	
	public Map<Category, List<Item>> getCatsWithFoods() throws Exception {
		Map<Category, List<Item>> result = new HashMap<Category, List<Item>>();
		
		for (Category cat: this.getCategories()) {
			List<Item> items = new ArrayList<Item>();
			for (Item item: this.getFoods()) {
				if (cat.getId() == item.getCatID()) {
					items.add(item);
					result.put(cat, items);
				}
			}
		}

		return result;
	}

	
	public static void main(String[] args) {
		Model model = Model.getInstance();
		try {
			System.out.println(model.getCategories().toString());
			System.out.println(model.getFoods().toString());
		} catch(Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
}
