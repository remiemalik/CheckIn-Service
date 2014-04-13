package nl.checkin.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import nl.checkin.connector.DataSourceSingleton;
import nl.checkin.model.Token;
import nl.checkin.util.Utils;



public class AuthenticationControl {

	private ResultSet resultSet;
	private Connection con;

	public AuthenticationControl() throws SQLException, NamingException {
		// TODO Auto-generated constructor stub
		 con = DataSourceSingleton.getInstance().getDatasource().getConnection();
	}
	
	@SuppressWarnings("unused")
	public Token hasValidCredentials(String username, String password) throws SQLException, NamingException{
		String query = "select count(*) as count, token from user where username=? AND password=(?)";
		PreparedStatement preparedStatement = con.prepareStatement(query);
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, password);
		resultSet = preparedStatement.executeQuery();
		return Utils.recordExists(resultSet);
	}
	

	

}
