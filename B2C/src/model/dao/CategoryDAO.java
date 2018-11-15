package model.dao;

import java.sql.Blob;
import java.sql.ResultSet;

import model.catalog.Category;

public class CategoryDAO extends DAO<Category> {

	public CategoryDAO() throws Exception {
		super("CATEGORY");
		
		COLUMNS.put("name", "NAME");
		COLUMNS.put("desc", "DESCRIPTION");
		COLUMNS.put("pic", "PICTURE");
		COLUMNS.put("id", "ID");
	}

	@Override
	public String convertToTableName(String by) {
		String result = COLUMNS.get("id");

		if (COLUMNS.containsKey(by) || COLUMNS.containsKey(by.toLowerCase())) {
			result = COLUMNS.get(by);
		} else if (by.equals(COLUMNS.get("name"))) {
			result = COLUMNS.get("name");
		} else if (by.toLowerCase().equals("description") || by.equals(COLUMNS.get("desc"))) {
			result = COLUMNS.get("desc");
		} else if (by.toLowerCase().equals("picture") || by.equals(COLUMNS.get("pic"))) {
			result = COLUMNS.get("pic");
		} else if (by.equals(COLUMNS.get("id"))) {
			result = COLUMNS.get("id");
		} 
		return result;
	}

	@Override
	public Category createBean(ResultSet r) throws Exception {
		Category result = new Category();
		result.setId(getId(r));
		result.setName(getName(r));
		result.setDescription(getDescription(r));
		result.setPicture(getPicture(r));
		return result;
	}

	public String getName(ResultSet r) throws Exception {
		return r.getString("NAME");
	}

	public String getDescription(ResultSet r) throws Exception {
		return r.getString("DESCRIPTION");
	}

	public int getId(ResultSet r) throws Exception {
		return r.getInt("ID");
	}

	public Blob getPicture(ResultSet r) throws Exception {
		return r.getBlob("PICTURE");
	}
	
	public static void main(String[] args) throws Exception {
		CategoryDAO catDao = new CategoryDAO();
		System.out.println(catDao.getAll().toString());
	}
}
