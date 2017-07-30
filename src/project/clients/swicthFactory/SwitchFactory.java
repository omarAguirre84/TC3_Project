package project.clients.swicthFactory;

import project.clients.swicthImpl.SwitchHttpImpl;
import project.clients.swicthImpl.SwitchTcpSocketImpl;
import project.protocolFactory.ProtocolsEnum;

public class SwitchFactory {
	
	public static Switch makeSwitch(ProtocolsEnum type) {
		Switch res ;
		if (type.equals(ProtocolsEnum.TCP_SOCKET)) {
			res = new SwitchTcpSocketImpl();
		} else if(type.equals(ProtocolsEnum.HTTP)) {
			res = new SwitchHttpImpl();
		}else {
			res = null;
		}
		return res;
	}
}
