package bc_om.car_ftp.users;

public class User implements Comparable<User> {

	private String login, password;
	private String current_directory;
	
	public User(String login, String password) {
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

	public int compareTo(User user) {
		int res;
		if((res = this.login.compareTo(user.getLogin())) != 0)
			return res;
		if((res = this.password.compareTo(user.getPassword())) != 0)
			return res;
		return this.current_directory.compareTo(user.getCurrent_directory());
	}

}
