package nl.checkin.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Token {

	private String token;

	public Token(String token) {
		this.token = token;
	}

	public Token() {

	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
