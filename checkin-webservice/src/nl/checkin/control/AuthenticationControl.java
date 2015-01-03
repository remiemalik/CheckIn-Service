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
	private Connection connection;
	private PreparedStatement statement;

	public AuthenticationControl() throws SQLException, NamingException {
	}
	
	public Token hasValidCredentials(String username, String password) throws SQLException, NamingException{
		try {
			connection = DataSourceSingleton.getInstance().getDatasource().getConnection();
			String query = "select count(*) as count, token from user where username=? AND password=(?)";
			statement = connection.prepareStatement(query);
			statement.setString(1, username);
			statement.setString(2, password);
			resultSet = statement.executeQuery();
			return Utils.recordExists(resultSet, true);
		} finally {
			Utils.closeEverything(resultSet, statement, connection);
		}

	}
	
	public Token hasValidToken(String token) throws SQLException, NamingException{
		try {
		    connection = DataSourceSingleton.getInstance().getDatasource().getConnection();
			String query = "select count(*) as count from user where token=?";
			statement = connection.prepareStatement(query);
			statement.setString(1, token);
			resultSet = statement.executeQuery();
			return Utils.recordExists(resultSet, false);
		} finally {
			Utils.closeEverything(resultSet, statement, connection);
		}
		
	}
}
