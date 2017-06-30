package project.server;

import java.util.ArrayList;

public class Menu {
	private ArrayList<String> options;
	
	public Menu() {
		options = new ArrayList<String>();
	}
	
	public void initAdminOptions(){
		options.add("Ver logs");
		options.add("Desconectar usuario");
		options.add("Ver logs de Usuario");
		options.add("Ingresar como usuario comun");
	}
	public void initUserOptions(){
//		options.add("Chatear");
//		options.add("Desconectar usuario");
	}
	
	public ArrayList<String> getOptions(){
		return this.options;
	}
}
