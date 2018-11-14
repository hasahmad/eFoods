package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO extends DAO{
	
	public CategoryDAO() {
		super("CATEGORY");
	}

	public Category createCategory(ResultSet r) throws Exception {
		Category category = new Category();
		category.setName(getName(r));
		category.setDescription(getDescription(r));
		category.setId(getId(r));
		category.setPicture(getPicture(r));
		return category;
	}

	public void populateBeans() throws Exception {
		Connection conn = ConnectionFactory.getConn();
		List<Category> categories = new ArrayList<>();
		
		String query = "select * from " + TABLE_NAME;
		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet r = stmt.executeQuery();

		while (r.next()) {
			Category i = createCategory(r);
			if (!categories.contains(i)) {
				categories.add(i);
			}
		}
		
		ConnectionFactory.closeConn(r, stmt, conn);
    }
	
	
	private List<CategoryBean> returnCatList(ResultSet r) throws Exception
	{
    	List<Category> resultSet = new ArrayList<Category>();
    	
	    while(r.next()) 
	    {
			 String name = r.getString("NAME");
			 String description = r.getString("DESCRIPTION");
			 int id = r.getInt("ID");
			 Blob picture = r.getBlob("PICTURE");
			 byte[] pic = picture.getBytes(1, (int)picture.length());

			 Category currentCategory = new Category(name, description, id, pic);
			 resultSet.add(currentCategory);
		 }
		return resultSet;
	}
	
	public List<CategoryBean> retrieveAll() throws Exception
	{
		Connection conn = ConnectionFactory.getConn();
		
		String query = "select * from " + TABLE_NAME;
		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet r = stmt.executeQuery();
		
		
		ConnectionFactory.closeConn(r, stmt, conn);
		return returnCatList(r);
	}
	
	

	public String getName(ResultSet r) throws Exception {
		return r.getString("NAME");
	}

	public double getDescription(ResultSet r) throws Exception {
		return r.getString("DESCRIPTION");
	}

	public int getId(ResultSet r) throws Exception {
		return r.getInt("ID");
	}

	public int getPicture(ResultSet r) throws Exception {
		return r.getBlob("PICTURE");
	}
}
