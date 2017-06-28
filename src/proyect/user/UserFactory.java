package proyect.user;

public class UserFactory {
	
	private User user;
	
	public UserFactory(){
		
	}
	
	public User userCreate(String userName, UserType userType, String pass){
		Client client = null;
		Admin admin = null;
		User res = null;
		
		switch (userType) {
		case CLIENT:
			res = new Client(userName, userType, pass);
			break;
		
		case ADMIN:
			res = new Admin(userName, userType, pass);
			break;
		}
		return res;
	}
	
}
