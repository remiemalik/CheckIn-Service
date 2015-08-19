package nl.checkin.control.factory;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import nl.checkin.control.dao.ErrorResponse;
import nl.checkin.control.dao.Response;
import nl.checkin.control.dao.SuccesResponse;

public class FactoryImpl extends Factory {

	@Override
	public Response createResponse(Type type) {

		switch (type) {
		case SUCCES:
			return new SuccesResponse();
		case ERROR:
			return new ErrorResponse();

		default:
			throw new ValueException("no type was specified");
		}

	}

	@Override
	public Response createResponse(Type type, Object... content) {

		switch (type) {
		case SUCCES:
			return new SuccesResponse(content[0]);
		case ERROR:
			return new ErrorResponse((String) content[0], (String) content[1]);

		default:
			throw new ValueException("no type was specified");
		}
	}

}
