package model.dao;

import java.sql.Blob;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.catalog.Category;

public class CategoryDAO extends DAO<Category> {
	private static Map<String, String[]> COLUMNS;
	private static List<String[]> searchColumns;

	public CategoryDAO() throws Exception {
		super("CATEGORY");
		COLUMNS = new HashMap<String, String[]>();
		searchColumns = new ArrayList<String[]>();
		
		COLUMNS.put("name", new String[]{"NAME", "str"});
		COLUMNS.put("desc", new String[]{"DESCRIPTION", "str"});
		COLUMNS.put("pic", new String[]{"PICTURE", "str"});
		COLUMNS.put("id", new String[]{"ID", "num"});
		
		searchColumns.add(COLUMNS.get("name"));
		searchColumns.add(COLUMNS.get("desc"));
	}

	@Override
	public String convertToColumnName(String by) {
		String result = COLUMNS.get("id")[0];

		if (COLUMNS.containsKey(by) || COLUMNS.containsKey(by.toLowerCase())) 
		{
			result = COLUMNS.get(by)[0];
		} 
		else if (by.equals(COLUMNS.get("name")[0])) 
		{
			result = COLUMNS.get("name")[0];
		} 
		else if (by.toLowerCase().equals("description") || by.equals(COLUMNS.get("desc")[0])) 
		{
			result = COLUMNS.get("desc")[0];
		} 
		else if (by.toLowerCase().equals("picture") || by.equals(COLUMNS.get("pic")[0])) 
		{
			result = COLUMNS.get("pic")[0];
		} 
		else if (by.equals(COLUMNS.get("id")[0])) 
		{
			result = COLUMNS.get("id")[0];
		} 
		return result;
	}
	
	@Override
	public List<String[]> getSearchColumns() {
		return searchColumns;	
	}

	@Override
	public Map<String, String[]> getColumns() {
		return COLUMNS;
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
		return r.getString(COLUMNS.get("name")[0]);
	}

	public String getDescription(ResultSet r) throws Exception {
		return r.getString(COLUMNS.get("desc")[0]);
	}

	public int getId(ResultSet r) throws Exception {
		return r.getInt(COLUMNS.get("id")[0]);
	}

	public Blob getPicture(ResultSet r) throws Exception {
		return r.getBlob(COLUMNS.get("pic")[0]);
	}
	
	public static void main(String[] args) throws Exception {
		CategoryDAO catDao = new CategoryDAO();
		System.out.println(catDao.getAll().toString());
	}
}
