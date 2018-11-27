package model;

import java.io.FileWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;

import model.catalog.Category;
import model.catalog.Item;
import model.catalog.Order;
import model.dao.CategoryDAO;
import model.dao.ItemDAO;
import model.dao.OrderDAO;

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
		return this.getFoodBy(by, val, false);
	}
	
	public Item getFoodBy(String by, String val, 
			Boolean like) throws Exception {
		return itemDao.findOneBy(by, val, like);
	}
	
	public List<Item> getFoodsByMultiple(String val) throws Exception {
		return itemDao.getAllByMultiple(val, true, null);
	}

	public List<Item> getFoodsBy(String by, 
			String val) throws Exception {
		return itemDao.getAllBy(by, val, true);
	}
	
	public List<Item> getFoodsBy(String by, 
			String val, Boolean like) throws Exception {
		return getFoodsBy(by, val, like, "0");
	}
	
	public List<Item> getFoodsBy(String by, String val, 
			Boolean like, String limit) throws Exception {
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
	
	public synchronized StringWriter createPO(String filePath, 
			int orderNum, String xslFilename, 
			Order order, Account user) throws Exception 
	{
		OrderDAO orderDao = new OrderDAO(filePath);
		String fileName = orderDao.getOrderFileName(user.getUsername(), orderNum);
		
		JAXBContext jaxbContext = JAXBContext.newInstance(order.getClass());
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

		StringWriter sw = new StringWriter();
		sw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
		sw.write("<?xml-stylesheet type=\"text/xsl\" href=\"" + xslFilename + "\"?>\n");

		marshaller.marshal(order, new StreamResult(sw));
		FileWriter orderFileWrite = orderDao.getFileWriter(fileName);
		orderFileWrite.write(sw.toString());
		orderFileWrite.close();

		return sw;
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
