package project.protocolFactory;

import java.io.IOException;
import java.net.BindException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;;

public final class ProtocolFactory {
	private static ServerSocket serverSocket;
	private static String serverIp;
	private static Integer freePort;
	
	private ProtocolFactory(){}
	
	public static Protocol getProtocol() {
		serverIp = null;
		String ifaceName = selectInterface();

		try {
			if (ifaceName.equals("all")){
				serverIp = "0.0.0.0";
			}else{
				NetworkInterface networkInterface = NetworkInterface.getByName(ifaceName);
			    Enumeration<InetAddress> inetAddress = networkInterface.getInetAddresses();
			    InetAddress currentAddress;
			    
			    while(inetAddress.hasMoreElements()){
			        currentAddress = inetAddress.nextElement();
			        if(currentAddress instanceof Inet4Address && !currentAddress.isLoopbackAddress()){
			        	serverIp = currentAddress.toString().replace("/", "");
			            break;
			        }
			    }
			}
		} catch (Exception e) {
			System.out.println();
		}
		
		Protocol protocol = selectProtocol();
		
		return protocol;
	}

	private static Protocol selectProtocol() {
		Protocol sp = null;

		ArrayList<String> opciones = new ArrayList<String>();
		for (ProtocolsEnum p : ProtocolsEnum.values()) {
			opciones.add(p.toString());
		}
		
		switch (new Menu("****PROTOCOLOS DISPONIBLES****").pedirOpcion(opciones)) {
		case 1:
			sp = new Http();
			sp.setHttp(new FuncionHttpMock());
			selectProtocol();
			break;
		case 2:
			sp = new TcpSocket();
			sp.setServerSocket(createServerSocket());
			break;
		}
		return sp;
	}
	
	private static ServerSocket createServerSocket() {
		freePort = ProtocolsEnum.TCP_SOCKET.getDefaultPort();
		boolean done = false;
		do {
			try {
				serverSocket = new ServerSocket();
				serverSocket.setSoTimeout(0);
				serverSocket.bind(new InetSocketAddress(serverIp, freePort));
				System.out.println("Servidor escuchando en " + serverIp + ":" + serverSocket.getLocalPort());
				
				done = true;
 			} catch (BindException e) {
				System.out.println(e+" puerto " + freePort + " ocupado, intentando con otro");
			} catch (SocketException e) {
				System.out.println(e);
			} catch (IOException e) {
				System.out.println(e);
			} finally {
				freePort = freePort + 100;
			}
		} while (!done);
		return serverSocket;
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
