package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DAO<T> {
	protected static String TABLE_NAME;
	protected static Map<String, String> COLUMNS;
	protected static Connection conn;

	public DAO() throws Exception {
		this("");
	}

	public DAO(String tableName) throws Exception {
		conn = ConnectionFactory.getConn();
		setTableName(tableName);
		COLUMNS = new HashMap<String, String>();
	}

	public static void setTableName(String name) {
		TABLE_NAME = name;
	}
	
	public static String getTableName() {
		return TABLE_NAME;
	}
	
	public static String getAllQuery() {
		return String.format("select * from %s", getTableName());
	}

	public List<T> getAll() throws Exception {
		Connection conn = ConnectionFactory.getConn();
		List<T> result = new ArrayList<T>();
		
		String query = getAllQuery();
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
		Connection conn = ConnectionFactory.getConn();
		List<T> result = new ArrayList<T>();
		
		String equal = like ? "like" : "=";
		val = like ? "'%" + val + "%'" : val;
		by = convertToTableName(by);
		
		String query = String.format("% where %s %s %s", getAllQuery(), by, equal, val);
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
		return this.getAllBy(by, val, like).get(0);
	}

	public abstract String convertToTableName(String by);
	public abstract T createBean(ResultSet r) throws Exception;
}
