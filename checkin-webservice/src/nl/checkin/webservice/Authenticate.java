package nl.checkin.webservice;

import java.sql.SQLException;

import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import nl.checkin.control.AuthenticationControl;
import nl.checkin.model.Token;
import nl.checkin.util.Utils;

//Sets the path to base URL + 
@Path("/private")
public class Authenticate {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/login")
	public Token loginUser(@QueryParam("username") String username,
			@QueryParam("password") String password) throws SQLException, NamingException {

		Token token = null;
		if (Utils.isNotNullString(username) && Utils.isNotNullString(password)) {
			 AuthenticationControl authControl = new AuthenticationControl();
			  token = authControl.hasValidCredentials(username, password);
	
		}

		return token;

	}

}
