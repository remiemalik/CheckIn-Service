package nl.checkin.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class Control {
	ResultSet resultSet;
	Connection connection;
	PreparedStatement statement;

}
