package model.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.catalog.Item;

public class ItemDAO extends DAO<Item> {
	private static Map<String, String[]> COLUMNS;
	private static List<String[]> searchColumns;
	
	public ItemDAO() throws Exception {
		super("ITEM");
		COLUMNS = new HashMap<String, String[]>();
		searchColumns = new ArrayList<String[]>();
		
		COLUMNS.put("qty", new String[]{"QTY", "num"});
		COLUMNS.put("name", new String[]{"NAME", "str"});
		COLUMNS.put("price", new String[]{"PRICE", "num"});
		COLUMNS.put("id", new String[]{"NUMBER", "str"});
		COLUMNS.put("num", new String[]{"NUMBER", "str"});
		COLUMNS.put("cid", new String[]{"CATID", "num"});
		
		searchColumns.add(COLUMNS.get("name"));
		searchColumns.add(COLUMNS.get("num"));
	}

	@Override
	public String convertToColumnName(String by) {
		String result = COLUMNS.get("cid")[0];

		if (COLUMNS.containsKey(by) || COLUMNS.containsKey(by.toLowerCase()))
		{
			result = COLUMNS.get(by)[0];
		} 
		else if (by.equals(COLUMNS.get("name")[0])) 
		{
			result = COLUMNS.get("name")[0];
		} 
		else if (by.toLowerCase().equals("price") || by.equals(COLUMNS.get("price")[0])) 
		{
			result = COLUMNS.get("price")[0];
		} 
		else if (by.toLowerCase().equals("quantity") || by.equals(COLUMNS.get("qty")[0])) 
		{
			result = COLUMNS.get("qty")[0];
		} 
		else if (by.toLowerCase().equals("number") || by.toLowerCase().equals("num") || by.equals(COLUMNS.get("id")[0])) 
		{
			result = COLUMNS.get("id")[0];
		} 
		else if (by.toLowerCase().equals(COLUMNS.get("cid")[0])) 
		{
			result = COLUMNS.get("cid")[0];
		} 
		return result;
	}
	
	@Override
	public List<String[]> getSearchColumns() {
		return searchColumns;	
	}
	
	@Override
	public Item createBean(ResultSet r) throws Exception {
		Item item = new Item();
		item.setName(getName(r));
		item.setQuantity(getQuantity(r));
		item.setPrice(getPrice(r));
		item.setNumber(getNumber(r));
		item.setCatID(getCatID(r));
		return item;
	}
	
	@Override
	public Map<String, String[]> getColumns() {
		return COLUMNS;
	}
	
	public String getId(ResultSet r) throws Exception {
		return r.getString(COLUMNS.get("cid")[0]);
	}
	
	public String getName(ResultSet r) throws Exception {
		return r.getString(COLUMNS.get("name")[0]);
	}

	public double getPrice(ResultSet r) throws Exception {
		return r.getDouble(COLUMNS.get("price")[0]);
	}

	public int getQuantity(ResultSet r) throws Exception {
		return r.getInt(COLUMNS.get("qty")[0]);
	}

	public String getNumber(ResultSet r) throws Exception {
		return r.getString(COLUMNS.get("id")[0]);
	}

	public int getCatID(ResultSet r) throws Exception {
		return r.getInt(COLUMNS.get("cid")[0]);
	}

	public static void main(String[] args) throws Exception {
		ItemDAO itemDao = new ItemDAO();
		System.out.println(itemDao.getAll().toString());
	}
}
