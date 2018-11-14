package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO extends DAO{
	
	public ItemDAO() {
		super("ITEM");
	}

	public Item createItem(ResultSet r) throws Exception {
		Item item = new Item();
		item.setName(getName(r));
		item.setQuantity(getQuantity(r));
		item.setPrice(getPrice(r));
		item.setNumber(getNumber(r));
		return item;
	}

	public void populateBeans() throws Exception {
		Connection conn = ConnectionFactory.getConn();
		List<Item> items = new ArrayList<>();
		
		String query = "select * from " + TABLE_NAME;
		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet r = stmt.executeQuery();

		while (r.next()) {
			Item i = createItem(r);
			if (!items.contains(i)) {
				items.add(i);
			}
		}
		
		ConnectionFactory.closeConn(r, stmt, conn);
    }
	
	public List<Item> getItemsByCatID(int catID) throws Exception
	{
		Connection conn = ConnectionFactory.getConn();
		List<Item> resultSet = new ArrayList<Item>();
		
		String query = "select * from " + TABLE_NAME + " where CATID = " + catID;
		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet r = stmt.executeQuery();
		
		while (r.next())
		{
			String name = r.getString("NAME");
			double price = r.getDouble("PRICE");
			int quantity = r.getInt("QUANTITY");
			int number = r.getInt("NUMBER");
			//int catID = r.getCatID("CATID");

			Item currentItem = new Item(name, price, quantity, number);
			resultSet.add(currentItem);
		}
		ConnectionFactory.closeConn(r, stmt, conn);
		return resultSet;
	}
	
	
	public List<Item> getItemsByName(String name) throws Exception
	{
		Connection conn = ConnectionFactory.getConn();
		List<Item> resultSet = new ArrayList<Item>();
		
		String query = "select * from ROUMANI.ITEM where NAME like '%" + name + "%'";
		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet r = stmt.executeQuery();
		
		while (r.next())
		{
			String name = r.getString("NAME");
			double price = r.getDouble("PRICE");
			int quantity = r.getInt("QUANTITY");
			int number = r.getInt("NUMBER");
			//int catID = r.getCatID("CATID");

			Item currentItem = new Item(name, price, quantity, number);
			resultSet.add(currentItem);
		}
		ConnectionFactory.closeConn(r, stmt, conn);
		return resultSet;
	}
	
	public List<Item> getItemsByNumber(Int number) throws Exception
	{
		Connection conn = ConnectionFactory.getConn();
		List<Item> resultSet = new ArrayList<Item>();
		
		String query = "select * from ROUMANI.ITEM where NUMBER = " + number;
		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet r = stmt.executeQuery();
		
		while (r.next())
		{
			String name = r.getString("NAME");
			double price = r.getDouble("PRICE");
			int quantity = r.getInt("QUANTITY");
			int number = r.getInt("NUMBER");
			//int catID = r.getCatID("CATID");

			Item currentItem = new Item(name, price, quantity, number);
			resultSet.add(currentItem);
		}
		ConnectionFactory.closeConn(r, stmt, conn);
		return resultSet;
	}
	
	

	public String getName(ResultSet r) throws Exception {
		return r.getString("NAME");
	}

	public double getPrice(ResultSet r) throws Exception {
		return r.getDouble("PRICE");
	}

	public int getQuantity(ResultSet r) throws Exception {
		return r.getInt("QUANTITY");
	}

	public int getNumber(ResultSet r) throws Exception {
		return r.getInt("NUMBER");
	}
}
