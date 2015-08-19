package nl.checkin.view;

import java.sql.SQLException;
import java.text.ParseException;

import javax.naming.NamingException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import nl.checkin.control.AuthenticationControl;
import nl.checkin.control.RegistrationControl;
import nl.checkin.control.dao.InMemoryResponseDao;
import nl.checkin.control.dao.Response;
import nl.checkin.model.Registration;
import nl.checkin.util.Attribute;
import nl.checkin.util.Utils;

//Sets the path to base URL + 
@Path(Attribute.PRIVATE_URL)
public class Service {

	private AuthenticationControl authControl;
	private RegistrationControl regControl;
	private InMemoryResponseDao responseDao;

	public Service() throws SQLException, NamingException {
		authControl = new AuthenticationControl();
		regControl = new RegistrationControl();
		responseDao = InMemoryResponseDao.getInstance();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(Attribute.LOGIN_URL)
	public Response loginUser(@QueryParam(Attribute.USERNAME) String username,
			@QueryParam(Attribute.PASSWORD) String password)
			throws SQLException, NamingException {

		if (Utils.isNotNull(username, password)) {
			if (authControl.hasValidCredentials(username, password)) {
				return authControl.getResponse();
			} else {
				return responseDao.findResponseByCode(Attribute.WRONG_CREDENTIALS);
			}
		} else {
			return responseDao.findResponseByCode(Attribute.MISSING_PARAMETER);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(Attribute.VALID_TOKEN_URL)
	public Response tokenExists(@QueryParam(Attribute.TOKEN) String inputToken)
			throws SQLException, NamingException {

		if (Utils.isNotNull(inputToken)) {
			if (authControl.hasValidToken(inputToken)) {
				return authControl.getResponse();
			} else {
				return responseDao.findResponseByCode(Attribute.INVALID_TOKEN);
			}
		} else {
			return responseDao.findResponseByCode(Attribute.MISSING_PARAMETER);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(Attribute.REGISTER_URL)
	public Response registerEntry(
			@QueryParam(Attribute.TOKEN) String inputToken,
			@QueryParam(Attribute.USER_ID) int user_id,
			@QueryParam(Attribute.CHECK_DATETIME) long checkDateTime,
			@QueryParam(Attribute.DAY_NAME) int dayName,
			@QueryParam(Attribute.WEEK) int week,
			@QueryParam(Attribute.YEAR) int year) throws SQLException,
			NamingException, ParseException {

		if (Utils.isNotNull(inputToken, user_id, checkDateTime, dayName, week,
				year)) {
			Registration registration = new Registration();
			registration.setFk_user_id(user_id);
			registration.setTemporaryDate(checkDateTime);
			registration.setDayName(dayName);
			registration.setWeek(week);
			registration.setYear(year);
			regControl.register(inputToken, registration);
			return regControl.getResponse();
		} else {
			return responseDao.findResponseByCode(Attribute.MISSING_PARAMETER);
		}

	}
}
