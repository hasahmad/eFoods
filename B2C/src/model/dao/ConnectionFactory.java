package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectionFactory {
	public static final String DB_HOST = "localhost";
	public static final String DB_PORT = "1527";
	public static final String DB_NAME = "EECS";
	public static final String DB_USER = "student";
	public static final String DB_PASS = "secret";
	
	public static String DB_SCHEMA = "roumani";
	
	public static final String DB_URL = String.format(
			"jdbc:derby://%s:%s/%s;user=%s;password=%s", 
			DB_HOST, DB_PORT, DB_NAME, DB_USER, DB_PASS);
	
	public static final String DB_DRIVER = "org.apache.derby.jdbc.ClientDriver";
	
	public static Connection getConn() throws Exception {
		Class.forName(DB_DRIVER).newInstance();
		Connection con = DriverManager.getConnection(DB_URL);
		if (DB_SCHEMA != null && DB_SCHEMA != "")
			con.createStatement().executeUpdate("set schema " + DB_SCHEMA);
		return con;
	}

	public static void closeConn(ResultSet r, Statement s, Connection c) throws Exception {
		r.close();
		s.close();
		c.close();
	}

}
