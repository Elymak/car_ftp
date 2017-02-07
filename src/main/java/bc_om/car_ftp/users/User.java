package bc_om.car_ftp.users;

public class User {

	private String login, password;
	
	public User(String login, String password){
		this.login = login;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public String toString(){
		return "{" + login + ", " + password +"}";
	}
	
	
}
