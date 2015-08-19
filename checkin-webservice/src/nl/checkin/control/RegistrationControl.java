package nl.checkin.control;

import java.sql.SQLException;

import javax.naming.NamingException;

import nl.checkin.control.dao.InMemoryResponseDao;
import nl.checkin.control.dao.Response;
import nl.checkin.control.singleton.DataSource;
import nl.checkin.model.Registration;
import nl.checkin.util.Attribute;
import nl.checkin.util.Utils;

public class RegistrationControl extends Control {

	private AuthenticationControl authControl;
	private Response response;
	private InMemoryResponseDao responseDao;

	public RegistrationControl() {
		authControl = new AuthenticationControl();
		responseDao = InMemoryResponseDao.getInstance();
	}

	public void register(String inputToken, Registration registration) {

		try {
			if (authControl.hasValidToken(inputToken)) {
				connection = DataSource.getInstance().getDatasource()
						.getConnection();
				String query = "select count(*) as count from registration where fk_user_id = ? and DATE_FORMAT(FROM_UNIXTIME(checkInDate),"
						+ " '%Y-%m-%d') = DATE(NOW())"
						+ " AND checkOutDate IS NULL";
				statement = connection.prepareStatement(query);
				statement.setInt(1, registration.getFk_user_id());
				resultSet = statement.executeQuery();

				if (Utils.recordExists(resultSet)) {
					System.out.println(" check out");
					checkOut(registration);
				} else {
					System.out.println(" check in");
					checkIn(registration);
				}
			} else {
				response = responseDao.findResponseByCode(Attribute.INVALID_TOKEN);
			}
		} catch (SQLException | NamingException ex) {
			response = responseDao.findResponseByCode(Attribute.FAILED_SQL);
		}

		finally {
			Utils.closeEverything(resultSet, statement, connection);
		}
	}

	public void checkIn(Registration registration) {

		try {
			connection = DataSource.getInstance().getConnection();
			String query = "insert into registration (fk_user_id, checkInDate, dayname, week, year) values (?, ?, ?, ?, ?)";
			statement = connection.prepareStatement(query);
			statement.setInt(1, registration.getFk_user_id());
			statement.setLong(2, registration.getTemporaryDate() / 1000);
			statement.setInt(3, registration.getDayName());
			statement.setInt(4, registration.getWeek());
			statement.setInt(5, registration.getYear());

			if (statement.executeUpdate() > 0) {
				response = responseDao.findResponseByCode(Attribute.SUCCESS);
			} else {
				response = responseDao.findResponseByCode(Attribute.ACTION_FAILED);
			}
		} catch (SQLException | NamingException ex) {
			response = responseDao.findResponseByCode(Attribute.ACTION_FAILED);
		}

		finally {
			Utils.closeEverything(null, statement, connection);
		}
	}

	public void checkOut(Registration registration) {

		try {
			connection = DataSource.getInstance().getConnection();
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
				response = responseDao.findResponseByCode("200");
			} else {
				response = responseDao.findResponseByCode(Attribute.ACTION_FAILED);
			}

		} catch (SQLException | NamingException e) {
			response = responseDao.findResponseByCode(Attribute.FAILED_SQL);
		}

		finally {
			Utils.closeEverything(resultSet, statement, connection);
		}
	}

	public Response getResponse() {
		return response;
	}
}
