package nl.checkin.control;

import java.sql.SQLException;

import javax.naming.NamingException;

import nl.checkin.control.dao.InMemoryResponseDao;
import nl.checkin.control.dao.Response;
import nl.checkin.control.factory.Factory;
import nl.checkin.control.factory.FactoryImpl;
import nl.checkin.control.singleton.DataSource;
import nl.checkin.model.Token;
import nl.checkin.util.Attribute;
import nl.checkin.util.Utils;



public class AuthenticationControl extends Control {

	private Response response;
	private int count;
	private InMemoryResponseDao responseDao;

	public AuthenticationControl() {
		responseDao = InMemoryResponseDao.getInstance();
	}

	public boolean hasValidCredentials(String username, String password)
			throws NamingException, SQLException {

		try {
			connection = DataSource.getInstance().getConnection();
			String query = "select count(*) as count, token from user where username=? AND password=(?)";
			statement = connection.prepareStatement(query);
			statement.setString(1, username);
			statement.setString(2, password);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				count = resultSet.getInt("count");
				if (count > 0) {
					response = new FactoryImpl().createResponse(Factory.Type.SUCCES,
							new Token(resultSet.getString("token")));
				}
			}

		} finally {
			Utils.closeEverything(resultSet, statement, connection);
		}
		return count == 1 ? true : false;
	}

	public boolean hasValidToken(String token) throws SQLException,
			NamingException {
		try {
			connection = DataSource.getInstance().getConnection();
			String query = "select count(*) as count from user where token=?";
			statement = connection.prepareStatement(query);
			statement.setString(1, token);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				count = resultSet.getInt("count");
				if (count > 0) {
					response = responseDao.findResponseByCode(Attribute.SUCCESS);
				}
			}
		} finally {
			Utils.closeEverything(resultSet, statement, connection);
		}

		return count == 1 ? true : false;
	}

	public Response getResponse() {
		return response;
	}
}
