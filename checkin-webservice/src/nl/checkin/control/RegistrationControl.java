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
import nl.checkin.model.User;
import nl.checkin.util.Utils;

public class RegistrationControl {
	
	private ResultSet resultSet;
	private AuthenticationControl authControl;
	private Token authToken;
	private User user;
	private Object response;

	public RegistrationControl() throws SQLException, NamingException {
		 authControl = new AuthenticationControl();
	}
	
	public void register(int user_id, String inputToken) throws SQLException,
			NamingException, ParseException {

		authToken = authControl.hasValidToken(inputToken);

		if (authToken.isValid()) {
			Connection con = DataSourceSingleton.getInstance().getDatasource().getConnection();
			String query = "select count(*) as count from registration where fk_user_id = ? and DATE(checkindate) = DATE(NOW())";
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, user_id);
			resultSet = preparedStatement.executeQuery();
			con.close();
			
			Token registerToken = Utils.recordExists(resultSet, false);
			user = new User(user_id);

			if (registerToken.isValid()) {
				checkOut(user);
			} else {
				checkIn(user);
			}

		} else {
			response = new Response("300", "bad content");
		}

	}
	
	public void checkIn(User user) throws SQLException, NamingException {
		
		Registration registration = new Registration();
		registration.setCheckInDate();
		registration.setDayName();
		
		Connection con = DataSourceSingleton.getInstance().getDatasource().getConnection();
		String query = "insert into registration (fk_user_id, checkindate, dayname) values (?, ?, ?)";
		PreparedStatement preparedStatement = con.prepareStatement(query);
		preparedStatement.setInt(1, user.getId());
		preparedStatement.setString(2, registration.getCheckInDate());
		preparedStatement.setInt(3, registration.getDayName());

		if(preparedStatement.executeUpdate() > 0){
			response = registration;
			con.close();
		} else {
			response = new Response("204","Could not perform action");
			con.close();
		}
	}

	public void checkOut(User user) throws SQLException, ParseException, NamingException {
		
		Registration registration = null;
		Connection con = DataSourceSingleton.getInstance().getDatasource().getConnection();
	 	String query = "select id, checkindate from registration where fk_user_id = ? and DATE(checkindate) = DATE(NOW())";
		PreparedStatement preparedStatement = con.prepareStatement(query);
		preparedStatement.setInt(1, user.getId());
		
		resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			registration = new Registration();
			registration.setId(resultSet.getInt("id"));
			registration.setCheckInDate(resultSet.getString("checkindate"));
			registration.setCheckOutDate();
			registration.setMinutes(Utils.getDifferenceInMinutes(registration.getCheckInDateInstance(), registration.getCheckOutDateInstance()));
		}
		
		query = "update registration set checkoutdate=?, minutes=? where id=? ";
		preparedStatement = con.prepareStatement(query, new String[] { "id" });
	
		preparedStatement.setString(1, registration.getCheckOutDate());
		preparedStatement.setLong(2, registration.getMinutes());
		preparedStatement.setInt(3, registration.getId());
		
		if(preparedStatement.executeUpdate() > 0){
			response = registration;
			con.close();
		} else {
			response = new Response("204","Could not perform action");
			con.close();
		}
	}
	
	public Object getResponse(){
		return response;
	}

}
