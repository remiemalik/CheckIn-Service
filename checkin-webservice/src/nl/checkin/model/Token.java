package nl.checkin.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Token {
	
	public String token;
	public boolean isValid;

	public Token(String token, boolean isValid) {
		this.token = token;
		this.isValid = isValid;
	}
	
	public Token(){
		
	}

	public String getToken() {
		return token;
	}

	public boolean isValid() {
		return isValid;
	}
	
	


}
