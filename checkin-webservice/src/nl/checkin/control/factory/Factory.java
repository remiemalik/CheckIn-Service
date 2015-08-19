package nl.checkin.control.factory;

import nl.checkin.control.dao.Response;

public abstract class Factory {
	public enum Type {
		SUCCES, ERROR
	};

	public Type type;

	public abstract Response createResponse(Type type);

	public abstract Response createResponse(Type type, Object... content);

}
