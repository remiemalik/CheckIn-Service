package nl.checkin.util;

public class Attribute {
	
	// Url's
	public static final String PRIVATE_URL = "/private";
	public static final String PUBLIC_URL = "/public";
	public static final String RESPONSES = "/responses";
	public static final String LOGIN_URL = "/login";
	public static final String VALID_TOKEN_URL = "/validtoken";
	public static final String REGISTER_URL = "/register";
	
	// User
	public static final String USER_ID = "user_id";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	
	// Token
	public static final String TOKEN = "token";
	public static final String CHECK_DATETIME = "check_datetime";
	public static final String DAY_NAME = "dayname";
	public static final String WEEK = "week";
	public static final String YEAR = "year";
	
	public static final String CONTEXT_NAME = "java:comp/env/jdbc/checkin";
	
	// Errors
	public static final String MISSING_PARAMETER = "302";
	public static final String INVALID_TOKEN = "303";
	public static final String WRONG_CREDENTIALS = "304";
	public static final String FAILED_SQL = "305";
	public static final String ACTION_FAILED = "306";
	
	// Succes
	public static final String SUCCESS = "200";

}
