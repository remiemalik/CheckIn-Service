package nl.checkin.util;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Utils {
	
	private static int count;
	private static boolean recordExist;
	
	public Utils(){
		
	}
	
	public static boolean isNotNullString(String param){
		if(param == null){
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean recordExists(ResultSet result) throws SQLException {
		while (result.next()) {
			count = result.getInt("count");
		}
		switch (count) {
		case 0:
			recordExist = false;
			break;

		case 1:
			recordExist = true;
			break;
		}
		return recordExist;
	}
	
	
}
