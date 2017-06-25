package proyect.serverLogic;

public class Profile {
	private String name;
	private String lastName;
	private String address;

	
	
	public Profile(String name, String lastName, String address) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
