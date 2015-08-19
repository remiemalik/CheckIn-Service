package nl.checkin.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Utils {

	public static boolean isNotNull(Object... parameters) {
		for (Object parameter : parameters) {
			if (parameter == null
					|| (parameter instanceof Integer && (Integer) parameter == 0)) {
				setMissingParameter(parameter);
				return false;
			}
		}
		return parameters.length > 0 ? true : false;
	}

	public static void setMissingParameter(Object parameter) {

	}

	public static synchronized boolean recordExists(ResultSet result)
			throws SQLException {
		int count = 0;

		while (result.next()) {
			count = result.getInt("count");
		}

		return count > 1 ? true : false;
	}

	public static int getDifferenceInMinutes(long startTime, long endTime) {

		long timeDifInMilliSec = 0;

		if (startTime >= endTime) {
			timeDifInMilliSec = startTime - endTime;
		} else {
			timeDifInMilliSec = endTime - startTime;
		}

		long timeDifMinutes = (timeDifInMilliSec * 1000L) / (60 * 1000);
		return (int) timeDifMinutes;
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
