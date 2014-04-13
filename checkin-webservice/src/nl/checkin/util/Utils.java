package nl.checkin.util;

import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	public static Token recordExists(ResultSet result) throws SQLException {
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
			
			token = new Token(result.getString("token"), recordExist);
		}
		
		return token;
	}
	
	
}
