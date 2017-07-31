package project.clients.swicthFactory;

public interface Switch {
	public void send(String msg) ;
	public String receive() ;
	public void process();
	public Thread getThread();
	public String getClientIp();
	public void initInAndOut();
}
