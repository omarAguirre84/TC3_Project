package proyect.user;

import java.util.ArrayList;

public class Menu {
	private ArrayList<String> options;
	
	public Menu() {
		options = new ArrayList<String>();
		initOptions();
	}
	
	public void initOptions(){
		options.add("Ver logs");
		options.add("Desconectar usuario");
		options.add("Ver logs de Usuario");
		options.add("Ingresar como usuario comun");
	}
	public ArrayList<String> getOptions(){
		return this.options;
	}
}
