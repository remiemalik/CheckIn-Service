package nl.checkin.model;

public class User {
	
	private int id;
	private String username;
	private String password;

	
	public User(int id){
		this.id = id;
	}
	
	public User(){
		
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	

}
