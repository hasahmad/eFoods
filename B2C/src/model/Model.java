package model;

import java.util.List;

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
		return itemDao.getAll();
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
		System.out.println(itemDao.getAllBy(by, val, like).toString());
		return itemDao.getAllBy(by, val, like);
	}
	
	public List<Category> getCategories() throws Exception {
		return catDao.getAll();
	}
	
	public Category getCategory(String cat) throws Exception {
		return catDao.findOne(cat);
	}

}
