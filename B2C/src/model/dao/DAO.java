package model.dao;

import java.sql.Connection;

public abstract class DAO {
	protected static String TABLE_NAME;
	protected static Connection conn;
	protected ArrayList <items>;

	public DAO() {
		this("");
	}
	
	public DAO(String tableName) {
		try {
			conn = ConnectionFactory.getConn();
			setTableName(tableName);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void setTableName(String name) {
		TABLE_NAME = name;
	}

//	abstract void populateBeans() throws Exception;

}
