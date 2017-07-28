package project.server.factories;

public enum Protocols {
	
	HTTP("80"),
	TCP_SOCKET("8080");
    

    private String protocol;

	Protocols(String protocol) {
        this.protocol = protocol;
    }

    public Integer getPort() {
        return Integer.parseInt(protocol);
    }
}
