package nl.checkin.control.dao;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorResponse extends Response {

	String message;

	public ErrorResponse() {

	}

	public ErrorResponse(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String toString() {
		return "ErrorResponse{code=".concat(code + ",").concat(
				"message=".concat(message).concat("}"));
	}

}
