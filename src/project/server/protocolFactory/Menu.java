package project.server.protocolFactory;

import java.util.ArrayList;
import java.util.Scanner;

public final class Menu {
	private Scanner input;
	private String titulo;

	
	private Menu(){};
	
	public Menu(String titulo) {
		this.titulo = titulo;
		this.input = new Scanner(System.in);
	}

	public String pedirInterfaz(ArrayList<String> opciones) {
		Integer opcionElegida = null;
		if (opciones!=null && this.titulo !=null) {
			System.out.println(titulo +"\n");
			
			System.out.println("Ingrese opcion:");
			for (int i = 0; i < opciones.size(); i++) {
				System.out.println((i + 1) + ": " + opciones.get(i));
			}
			System.out.println();
			System.out.print(">> ");
			
			try {
				opcionElegida = Integer.parseInt(input.nextLine());
			} catch(NumberFormatException nfe) {
				opcionElegida = 0;
			}
			while (opcionElegida < 1 || opcionElegida > opciones.size()) {
				System.out.println("Error: la opcion debe estar entre 1 y " + opciones.size());
				try {
					opcionElegida = Integer.parseInt(input.nextLine());
				} catch(NumberFormatException nfe) {
					opcionElegida = 0;
				}
			}
			
		}
		return opciones.get(opcionElegida-1).split("-")[0].trim();
	}
	
	public int pedirOpcion(ArrayList<String> opciones) {
		System.out.println(titulo);
		
		System.out.println();
		System.out.println("Ingrese opcion:");
		for (int i = 0; i < opciones.size(); i++) {
			System.out.println((i + 1) + ": " + opciones.get(i));
		}
		System.out.println();
		System.out.print(">> ");

		int opcion;
		try {
			opcion = Integer.parseInt(input.nextLine());
		} catch(NumberFormatException nfe) {
			opcion = 0;
		}
		while (opcion < 1 || opcion > opciones.size()) {
			System.out.println("Error: la opcion debe estar entre 1 y " + opciones.size());
			try {
				opcion = Integer.parseInt(input.nextLine());
			} catch(NumberFormatException nfe) {
				opcion = 0;
			}
		}
		return opcion;
	}

//	public int opcionFin() {
//		return opcionesIn.size();
//	}

}
