package nl.checkin.control;

import java.sql.SQLException;

import javax.naming.NamingException;

import nl.checkin.control.connection.DataSourceSingleton;
import nl.checkin.model.ErrorResponse;
import nl.checkin.model.Registration;
import nl.checkin.model.Response;
import nl.checkin.model.SuccesResponse;
import nl.checkin.model.Token;
import nl.checkin.util.Utils;

public class RegistrationControl extends Control {

	private AuthenticationControl authControl;
	private Token authToken;
	private Response response;

	public RegistrationControl() {
		authControl = new AuthenticationControl();
	}

	public void register(String inputToken, Registration registration) {

		try {
			authToken = authControl.hasValidToken(inputToken);
		} catch (SQLException | NamingException e) {
			response = new ErrorResponse("303",
					"some sql or parsing went wrong");
		}

		if (authToken.isValid()) {

			try {
				connection = DataSourceSingleton.getInstance().getDatasource()
						.getConnection();
				String query = "select count(*) as count from registration where fk_user_id = ? and DATE_FORMAT(FROM_UNIXTIME(checkInDate),"
						+ " '%Y-%m-%d') = DATE(NOW())"
						+ " AND checkOutDate IS NULL";
				statement = connection.prepareStatement(query);
				statement.setInt(1, registration.getFk_user_id());
				resultSet = statement.executeQuery();

				Token registerToken = Utils.recordExists(resultSet, false);

				if (registerToken.isValid()) {
					System.out.println(" check out");
					checkOut(registration);
				} else {
					System.out.println(" check in");
					checkIn(registration);
				}

			} catch (SQLException | NamingException ex) {
				response = new ErrorResponse("303",
						"some sql or parsing went wrong");
			}

			finally {
				Utils.closeEverything(resultSet, statement, connection);
			}
		} else {
			response = new ErrorResponse("300", "bad content");
		}
	}

	public void checkIn(Registration registration) {

		try {
			connection = DataSourceSingleton.getInstance().getDatasource()
					.getConnection();
			String query = "insert into registration (fk_user_id, checkInDate, dayname, week, year) values (?, ?, ?, ?, ?)";
			statement = connection.prepareStatement(query);
			statement.setInt(1, registration.getFk_user_id());
			statement.setLong(2, registration.getTemporaryDate() / 1000);
			statement.setInt(3, registration.getDayName());
			statement.setInt(4, registration.getWeek());
			statement.setInt(5, registration.getYear());

			if (statement.executeUpdate() > 0) {
				response = new SuccesResponse();
			} else {
				response = new ErrorResponse("204", "Could not perform action");

			}
		} catch (SQLException | NamingException ex) {
			response = new ErrorResponse("303", "some sql went wrong");
		}

		finally {
			Utils.closeEverything(null, statement, connection);
		}
	}

	public void checkOut(Registration registration) {

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
						registration.getCheckOutDate() / 1000));
			}

			query = "update registration set checkoutdate=?, minutes=? where id=? ";
			statement = connection.prepareStatement(query,
					new String[] { "id" });

			statement.setLong(1, registration.getCheckOutDate() / 1000);
			statement.setLong(2, registration.getMinutes());
			statement.setInt(3, registration.getId());

			if (statement.executeUpdate() > 0) {

				response = new SuccesResponse();
			} else {
				response = new ErrorResponse("204", "Could not perform action");
			}

		} catch (SQLException | NamingException e) {
			response = new ErrorResponse("303",
					"some sql or parsing went wrong");
		}

		finally {
			Utils.closeEverything(resultSet, statement, connection);
		}
	}

	public Response getResponse() {
		return response;
	}
}
