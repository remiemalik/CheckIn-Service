package nl.checkin.control.dao;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import nl.checkin.model.Registration;
import nl.checkin.model.Token;

@XmlRootElement
@XmlSeeAlso({ Registration.class, Token.class })
public class SuccesResponse extends Response {

	private Object load;
	private final String code = "200";

	public SuccesResponse() {

	}

	public SuccesResponse(Object load) {
		this.load = load;
	}

	public Object getLoad() {
		return load;
	}

	public void setLoad(Object load) {
		this.load = load;
	}

	public String getCode() {
		return code;
	}

}
