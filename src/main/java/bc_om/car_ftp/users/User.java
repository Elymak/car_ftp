package bc_om.car_ftp.users;

public class User {

	private String login, password;
	private String current_directory;
	
	public User(String login, String password){
		this.login = login;
		this.password = password;
		this.current_directory = this.login;
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
	
	public String getCurrent_directory() {
		return current_directory;
	}

	public void setCurrent_directory(String current_directory) {
		this.current_directory = current_directory;
	}

	public String toString(){
		return "{" + login + ", " + password +"}";
	}
	
	
}
