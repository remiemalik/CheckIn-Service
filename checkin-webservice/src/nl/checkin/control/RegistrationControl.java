package nl.checkin.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import javax.naming.NamingException;
import nl.checkin.connector.DataSourceSingleton;
import nl.checkin.model.Registration;
import nl.checkin.model.Response;
import nl.checkin.model.Token;
import nl.checkin.util.Utils;

public class RegistrationControl {

	private ResultSet resultSet;
	private AuthenticationControl authControl;
	private Token authToken;
	private Object response;

	private Connection connection;
	private PreparedStatement statement;

	public RegistrationControl() throws SQLException, NamingException {
		authControl = new AuthenticationControl();
	}

	public void register(int user_id, String inputToken,
			Registration registration) throws SQLException, NamingException,
			ParseException {

		authToken = authControl.hasValidToken(inputToken);

		if (authToken.isValid()) {

			try {

				connection = DataSourceSingleton.getInstance().getDatasource()
						.getConnection();
				String query = "select count(*) as count from registration where fk_user_id = ? and DATE_FORMAT(FROM_UNIXTIME(checkInDate),"
						+ "    '%Y-%m-%d') = DATE(NOW())"
						+      " AND checkOutDate IS NULL";
				statement = connection.prepareStatement(query);
				statement.setInt(1, user_id);
				resultSet = statement.executeQuery();

				Token registerToken = Utils.recordExists(resultSet, false);

				if (registerToken.isValid()) {
					checkOut(registration);
					System.out.println(" check out");
				} else {
					checkIn(registration);
					System.out.println(" check in");
				}

			} finally {
				Utils.closeEverything(resultSet, statement, connection);
			}

		} else {
			response = new Response("300", "bad content");
		}

	}

	public void checkIn(Registration registration) throws SQLException,
			NamingException {

		try {
			connection = DataSourceSingleton.getInstance().getDatasource()
					.getConnection();
			String query = "insert into registration (fk_user_id, checkInDate, dayname, week, year) values (?, ?, ?, ?, ?)";
			statement = connection.prepareStatement(query);
			statement.setInt(1, registration.getFk_user_id());
			statement.setLong(2, registration.getTemporaryDate());
			statement.setInt(3, registration.getDayName());
			statement.setInt(4, registration.getWeek());
			statement.setInt(5, registration.getYear());

			if (statement.executeUpdate() > 0) {
				response = registration;
			} else {
				response = new Response("204", "Could not perform action");

			}
		} finally {
			Utils.closeEverything(null, statement, connection);
		}
	}

	public void checkOut(Registration registration) throws SQLException,
			ParseException, NamingException {

		try {

			connection = DataSourceSingleton.getInstance().getDatasource()
					.getConnection();
			String query = "select id, checkInDate from registration where fk_user_id = ? and DATE_FORMAT(FROM_UNIXTIME(checkInDate), '%Y-%m-%d') = DATE(NOW())";
			statement = connection.prepareStatement(query);

			statement.setInt(1, registration.getFk_user_id());

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				registration.setId(resultSet.getInt("id"));
				registration.setCheckInDate(resultSet.getLong("checkindate"));
				registration.setCheckOutDate(registration.getTemporaryDate());
				registration.setMinutes(Utils.getDifferenceInMinutes(
				registration.getCheckInDate(),
				registration.getCheckOutDate()));
			}

			query = "update registration set checkoutdate=?, minutes=? where id=? ";
			statement = connection.prepareStatement(query,
					new String[] { "id" });

			statement.setLong(1, registration.getCheckOutDate());
			statement.setLong(2, registration.getMinutes());
			statement.setInt(3, registration.getId());

			if (statement.executeUpdate() > 0) {
				response = registration;
			} else {
				response = new Response("204", "Could not perform action");
			}

		} finally {
			Utils.closeEverything(resultSet, statement, connection);
		}

	}

	public Object getResponse() {
		return response;
	}

}
