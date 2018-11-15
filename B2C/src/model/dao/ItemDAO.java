package model.dao;

import java.sql.ResultSet;
import model.catalog.Item;

public class ItemDAO extends DAO<Item> {	
	public ItemDAO() throws Exception {
		super("ITEM");
		
		COLUMNS.put("name", "NAME");
		COLUMNS.put("price", "PRICE");
		COLUMNS.put("qty", "QTY");
		COLUMNS.put("num", "NUMBER");
		COLUMNS.put("id", "NUMBER");
		COLUMNS.put("cid", "CATID");
	}

	@Override
	public String convertToTableName(String by) {
		String result = COLUMNS.get("cid");

		if (COLUMNS.containsKey(by) || COLUMNS.containsKey(by.toLowerCase()))
		{
			result = COLUMNS.get(by);
		} 
		else if (by.equals(COLUMNS.get("name"))) 
		{
			result = COLUMNS.get("name");
		} 
		else if (by.toLowerCase().equals("price") || by.equals(COLUMNS.get("price"))) 
		{
			result = COLUMNS.get("price");
		} 
		else if (by.toLowerCase().equals("quantity") || by.equals(COLUMNS.get("qty"))) 
		{
			result = COLUMNS.get("qty");
		} 
		else if (by.toLowerCase().equals("number") || by.equals(COLUMNS.get("num"))) 
		{
			result = COLUMNS.get("num");
		} 
		else if (by.equals(COLUMNS.get("cid"))) 
		{
			result = COLUMNS.get("cid");
		} 
		return result;
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
	
	public String getId(ResultSet r) throws Exception {
		return r.getString(COLUMNS.get("cid"));
	}
	
	public String getName(ResultSet r) throws Exception {
		return r.getString(COLUMNS.get("name"));
	}

	public double getPrice(ResultSet r) throws Exception {
		return r.getDouble(COLUMNS.get("price"));
	}

	public int getQuantity(ResultSet r) throws Exception {
		return r.getInt(COLUMNS.get("qty"));
	}

	public String getNumber(ResultSet r) throws Exception {
		return r.getString(COLUMNS.get("num"));
	}
	
	public int getCatID(ResultSet r) throws Exception {
		return r.getInt(COLUMNS.get("cid"));
	}
	
	public static void main(String[] args) throws Exception {
		ItemDAO itemDao = new ItemDAO();
		System.out.println(itemDao.getAll().toString());
	}
}
