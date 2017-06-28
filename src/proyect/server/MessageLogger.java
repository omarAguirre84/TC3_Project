package proyect.server;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

public final class MessageLogger extends Observable{
	private static MessageLogger instance;
	private String userName;
	private String clientSocket;
	private String content;
	private final String SEPARATOR = "; ";
	private final String eof = "; ";
	
	private Calendar date;
	private Data data;

	private MessageLogger() {
		this.data= new Data();
	}
	
	
	
	public String formReg() {
		 return getDateToday()
					 + SEPARATOR
					 + userName
					 + SEPARATOR
					 + clientSocket
					 + SEPARATOR
					 + content + eof;
	}
	
	public String getDateToday() {
		date = Calendar.getInstance();
		date.add(Calendar.DATE, 0);
		return new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(date.getTime());
	}
	
	public void setAllMembers(String userName, String client_socket, String content) {
		this.userName = userName;
		this.clientSocket = client_socket;
		this.content = content;
	}
	
	public static MessageLogger getInstance() {
		return (instance == null) ? instance = new MessageLogger() : instance;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	
	
	@Override
	public synchronized void addObserver(Observer o) {
		// TODO Auto-generated method stub
		super.addObserver(o);
	}

	@Override
	protected synchronized void setChanged() {
		// TODO Auto-generated method stub
		super.setChanged();
	}

	@Override
	public synchronized void deleteObserver(Observer o) {
		// TODO Auto-generated method stub
		super.deleteObserver(o);
	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		super.notifyObservers();
	}

	@Override
	public void notifyObservers(Object arg) {
		// TODO Auto-generated method stub
		super.notifyObservers(arg);
	}

	@Override
	public synchronized void deleteObservers() {
		// TODO Auto-generated method stub
		super.deleteObservers();
	}

	@Override
	protected synchronized void clearChanged() {
		// TODO Auto-generated method stub
		super.clearChanged();
	}

	@Override
	public synchronized boolean hasChanged() {
		// TODO Auto-generated method stub
		return super.hasChanged();
	}

	@Override
	public synchronized int countObservers() {
		// TODO Auto-generated method stub
		return super.countObservers();
	}
}
