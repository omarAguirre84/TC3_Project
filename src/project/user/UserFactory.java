package project.user;

public class UserFactory {

	public static User userCreate(String userName, UserType userType, String pass) {
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
