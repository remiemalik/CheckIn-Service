package nl.checkin.connector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;



public class DataSourceSingleton {
	 private static DataSourceSingleton instance = null;
	 private DataSource datasource;
	 private Context context;

	 
	 public DataSourceSingleton() throws NamingException {
		 context = new InitialContext();
		 datasource = (DataSource)context.lookup("java:comp/env/jdbc/checkin");
	 }

	 public static DataSourceSingleton getInstance() throws NamingException{
		 if(instance == null){
			 instance = new DataSourceSingleton();
		 } 
		 return instance;
	 }

	public DataSource getDatasource() {
		return datasource;
	}

}
