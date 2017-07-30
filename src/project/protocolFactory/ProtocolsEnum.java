package project.protocolFactory;

public enum ProtocolsEnum {
	
	HTTP("80"),
	TCP_SOCKET("8080");
    

    private String port;

	ProtocolsEnum(String protocol) {
        this.port = protocol;
    }

    public Integer getDefaultPort() {
        return Integer.parseInt(port);
    }
}
