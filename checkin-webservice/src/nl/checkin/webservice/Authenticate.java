package nl.checkin.webservice;

import java.sql.SQLException;
import java.text.ParseException;

import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import nl.checkin.control.AuthenticationControl;
import nl.checkin.control.RegistrationControl;
import nl.checkin.model.Token;
import nl.checkin.util.Utils;

//Sets the path to base URL + 
@Path("/private")
public class Authenticate {

	private AuthenticationControl authControl;
	private RegistrationControl regControl;
	private Token token;
	
	public Authenticate() throws SQLException, NamingException{
		  authControl = new AuthenticationControl();
		  regControl = new RegistrationControl();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/login")
	public Token loginUser(@QueryParam("username") String username,
			@QueryParam("password") String password) throws SQLException, NamingException {

		token = null;
		if (Utils.isNotNullString(username) && Utils.isNotNullString(password)) {
			  token = authControl.hasValidCredentials(username, password);
		}
		return token;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/validtoken")
	public Token tokenExists(@QueryParam("token") String inputToken) throws SQLException, NamingException{
		token = null;
		if (Utils.isNotNullString(inputToken)){
			  token = authControl.hasValidToken(inputToken);
		}
		
		return token;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/register")
	public Object registerEntry(@QueryParam("token") String inputToken,
			@QueryParam("user_id") int user_id) throws SQLException, NamingException, ParseException {

		if (Utils.isNotNullString(inputToken) && Utils.isNotNullString(Integer.toString(user_id))) {
			  regControl.register(user_id, inputToken);
		}
		
		return regControl.getResponse();
	}

}
