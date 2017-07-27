package project.server.factories;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import project.server.protocols.Http;
import project.server.protocols.NetworkSocket;;

public class ProtocolFactory {
	
	public static Protocol selectProtocol(){
		Protocol sp = null;
		
		ArrayList<String> opciones = new ArrayList<String>();
		opciones.add("HTTP");
		opciones.add("Socket");
		switch (new Menu("PROTOCOLOS").pedirOpcion(opciones)) {
		case 1:
			sp = new Http();
			break;
		case 2:
			sp = new NetworkSocket();
			break;
		}
		return sp;
	}
	
	public static String selectInterface(String titulo){
		ArrayList<String> options;
		Enumeration<NetworkInterface> nwis;
		String res = null;
		
		try {
			options = new ArrayList<String>();
			nwis = NetworkInterface.getNetworkInterfaces(); 
			
			while (nwis.hasMoreElements()) {
				NetworkInterface ni = nwis.nextElement(); 
				if (ni.isUp()) {
					options.add(ni.getName() + " - " + ni.getDisplayName());
				}				
			}
			options.add("Listen from any - 0.0.0.0");
			res = NetworkInterface.getByName(new Menu(titulo).pedirInterfaz(options)).getName();
		} catch (Exception e) {
		}
		return (res != null)? res : "all";
	}
}
