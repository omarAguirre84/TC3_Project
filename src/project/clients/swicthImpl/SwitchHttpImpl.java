package project.clients.swicthImpl;

import project.clients.swicthFactory.Switch;
import project.exceptions.ClientDisconectedException;

public class SwitchHttpImpl implements Switch{

	public SwitchHttpImpl() {
		
	}

	@Override
	public void send(String msg) throws ClientDisconectedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String receive() throws ClientDisconectedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Thread getThread() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getClientIp() {
		// TODO Auto-generated method stub
		return null;
	}
}
