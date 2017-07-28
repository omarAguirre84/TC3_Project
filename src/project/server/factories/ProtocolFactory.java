package project.server.factories;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

import project.server.protocols.Http;
import project.server.protocols.NetworkSocket;;

public class ProtocolFactory {
	private static ServerSocket serverSocket;
	
	public static Protocol init() {
		Protocol protocol = selectProtocol();
		String iface = selectInterface();
		String serverIp = null;

		try {
			serverIp = (iface.equals("all")) ? 
				"0.0.0.0":
				NetworkInterface.getByName(iface).getInterfaceAddresses().get(0).toString().split("/")[1].toString();
		} catch (Exception e) {
			System.out.println();
		}
		
		return protocol;
	}

	private static Protocol selectProtocol() {
		Protocol sp = null;

		ArrayList<String> opciones = new ArrayList<String>();
		for (Protocols p : Protocols.values()) {
			opciones.add(p.toString());
		}
		
		switch (new Menu("PROTOCOLOS").pedirOpcion(opciones)) {
		case 1:
			sp = new Http();
			sp.setHttp(new FuncionHttpExample());
			break;
		case 2:
			sp = new NetworkSocket();
			serverSocket = new ServerSocket(port, 0, bindAddr);
			sp.setServerSocket(serverSocket);
			break;
		}
		return sp;
	}
	
	private static int searchFreePort() {
		Integer freePort = null;
		boolean done = false;
		do {
			try {
				serverSocket = new ServerSocket(serverDefaultPort);
				serverSocket.setSoTimeout(0);
				System.out.println("Servidor escuchando en " + serverIp + ":" + serverSocket.getLocalPort());
				
				done = true;
			} catch (Exception e) {
				System.out.println("Puerto " + freePort + " ocupado, intentando con otro");
			} finally {
				freePort = freePort + 100;
			}
		} while (!done);
		return freePort;
	} 
	private static String selectInterface() {
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
			res = NetworkInterface.getByName(new Menu("INTERFACES").pedirInterfaz(options)).getName();
		} catch (Exception e) {
		}
		return (res != null) ? res : "all";
	}
}
