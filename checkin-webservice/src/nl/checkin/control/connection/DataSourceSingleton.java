package nl.checkin.control.connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import nl.checkin.util.Attribute;



public class DataSourceSingleton {
	 private static DataSourceSingleton instance;
	 private DataSource datasource;
	 private Context context;

	 private DataSourceSingleton() throws NamingException {
		 context = new InitialContext();
		 datasource = (DataSource)context.lookup(Attribute.CONTEXT_NAME);
	 }

	 public static synchronized DataSourceSingleton getInstance() throws NamingException{
		 if(instance == null){
			 instance = new DataSourceSingleton();
		 } 
		 return instance;
	 }

	public DataSource getDatasource() {
		return datasource;
	}

}
