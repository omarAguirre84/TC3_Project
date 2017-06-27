package proyect.user;

public abstract class User {
	private String userName;
	private UserType userType;
	private String password;

	public User(String userName, UserType userType, String pass) {
		this.userName = userName;
		this.password = pass;
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
}
