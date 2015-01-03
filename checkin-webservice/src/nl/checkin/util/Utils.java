package nl.checkin.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import nl.checkin.model.Response;
import nl.checkin.model.Token;

public class Utils {
	

	
	public Utils(){
		
	}
	
	public static boolean isNotNullString(String param){
		if(param == null){
			return false;
		} else {
			return true;
		}
	}
	
	public static Token recordExists(ResultSet result, boolean returnToken) throws SQLException {
		Token token = null;
		int count;
		boolean recordExist = false;
		
		while (result.next()) {
	
			count = result.getInt("count");
			switch (count) {
			case 0:
				recordExist = false;
				break;

			case 1:
				recordExist = true;
				break;
			}
			
			if(returnToken){
				token = new Token(result.getString("token"), recordExist);
			} else {
				token = new Token(null, recordExist);
			}
			
		}

		return token;
	}
	
	public static int getDifferenceInMinutes(long startTime, long endTime){
		
		long timeDifInMilliSec = 0;
		if(startTime >=  endTime){
			timeDifInMilliSec = startTime - endTime;
		} else{ 
			timeDifInMilliSec = endTime - startTime;
		}
		
		 long timeDifMinutes = timeDifInMilliSec / (60 * 1000);
		
		return (int) timeDifMinutes;
	}
	
	public static Response getResponse(int number){
		Response response;
		if(number == 0){
			response = new Response("204", "Could not perform action");
		} else {
			response = new Response("200", "Succes");
		}
		return response;
	}
	
	public static void closeEverything(ResultSet rs, Statement stmt,
			Connection con) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
	}


	
	
	
}
