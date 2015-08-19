package nl.checkin.control.singleton;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class DataSource {
	private static DataSource instance = null;
	private javax.sql.DataSource datasource;
	private Context context;

	public DataSource() throws NamingException {
		context = new InitialContext();
		datasource = (javax.sql.DataSource) context
				.lookup("java:comp/env/jdbc/checkin");
	}

	public synchronized static DataSource getInstance() throws NamingException {
		if (instance == null) {
			instance = new DataSource();
		}
		return instance;
	}

	public javax.sql.DataSource getDatasource() {
		return datasource;
	}

	public Connection getConnection() {
		try {
			return datasource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
