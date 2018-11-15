package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DAO<T> {
	protected String TABLE_NAME;
	protected static Connection conn;

	public DAO() throws Exception {
		this("");
	}

	public DAO(String tableName) throws Exception {
		conn = ConnectionFactory.getConn();
		setTableName(tableName);
	}

	public void setTableName(String name) {
		TABLE_NAME = name;
	}
	
	public String getTableName() {
		return TABLE_NAME;
	}
	
	public String getAllQuery() {
		return String.format("select * from %s", getTableName());
	}

	public List<T> getAll() throws Exception {
		return getAll(0);
	}
	
	public List<T> getAll(Integer limit) throws Exception {
		Connection conn = ConnectionFactory.getConn();
		List<T> result = new ArrayList<T>();
		String limitQuery = "";
//		String limitQuery = (limit != null && limit > 0) ? "limit " + limit : "";
		String query = String.format("%s %s", getAllQuery(), limitQuery);

		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet r = stmt.executeQuery();
		
		while (r.next()) {
			T i = createBean(r);
			if (!result.contains(i)) {
				result.add(i);
			}
		}

		ConnectionFactory.closeConn(r, stmt, conn);
		return result;
	}

	public List<T> getAllBy(String by, String val, Boolean like) throws Exception {
		return getAllBy(by, val, like, 0);
	}
	
	public List<T> getAllBy(String by, String val, Boolean like, Integer limit) throws Exception {
		Connection conn = ConnectionFactory.getConn();
		List<T> result = new ArrayList<T>();
		String limitQuery = "";
//		String limitQuery = (limit != null && limit > 0) ? "limit " + limit : "";
		String equal = like ? "like" : "=";
		
		val = like ? "'%" + val + "%'" : val;
		by = this.convertToColumnName(by);

		for (String[] str: getColumns().values()) {
			if (str[0] == by) {
				if (str[1] == "str") {
					if (val.charAt(0) != '\'' && val.charAt(val.length()-1) != '\'') {
						val = "'" + val + "'";
					}
				}
			}
		}

		String query = String.format("%s where %s %s %s %s", getAllQuery(), by, equal, val, limitQuery);
		
		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet r = stmt.executeQuery();

		while (r.next())
		{
			T i = createBean(r);
			if (!result.contains(i)) {
				result.add(i);
			}
		}
		ConnectionFactory.closeConn(r, stmt, conn);
		return result;
	}

	public T findOne(String val) throws Exception {
		return this.findOneBy("id", val, false);
	}

	public T findOneBy(String by, String val, Boolean like) throws Exception {
		return this.findOneBy(by, val, like, 0);
	}
	
	public T findOneBy(String by, String val, Boolean like, Integer limit) throws Exception {
		return this.getAllBy(by, val, like, limit).get(0);
	}
	
	public abstract Map<String, String[]> getColumns();
	public abstract String convertToColumnName(String by);
	public abstract T createBean(ResultSet r) throws Exception;
}
