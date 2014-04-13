package nl.checkin.webservice;

import java.sql.SQLException;

import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import nl.checkin.control.AuthenticationControl;
import nl.checkin.util.Utils;

//Sets the path to base URL + 
@Path("/private")
public class Authenticate {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/login")
	public String loginUser(@QueryParam("username") String username,
			@QueryParam("password") String password) throws SQLException, NamingException {

		String outcome = "";
		if (Utils.isNotNullString(username) && Utils.isNotNullString(password)) {
			 AuthenticationControl authControl = new AuthenticationControl();
			 if(authControl.hasValidCredentials(username, password)){
				 outcome = "good";
			 } else {
				 outcome = "bad";
			 }
		}

		return outcome;

	}

}
