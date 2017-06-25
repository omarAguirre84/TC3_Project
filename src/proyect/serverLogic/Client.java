package proyect.serverLogic;

public class Client {
	private String user;
	private String password;
	private boolean connected;

	public Client(String apodo, String pass) {
		this.user = apodo;
		this.password = pass;
	}

	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

}