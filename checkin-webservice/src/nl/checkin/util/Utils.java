package nl.checkin.util;

import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	public static long getDifferenceInMinutes(Calendar startTime, Calendar endTime){
		
		long start = startTime.getTimeInMillis();
		long end = endTime.getTimeInMillis();
		long timeDifInMilliSec = 0;
		if(start >=  end){
			timeDifInMilliSec = start - end;
		} else{ 
			timeDifInMilliSec = end - start;
		}
		
		 long timeDifMinutes = timeDifInMilliSec / (60 * 1000);
		
		return timeDifMinutes;
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
	
	
	
}
