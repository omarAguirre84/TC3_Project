package proyect.server;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

public final class MessageLogger {
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
		 return getNowDate()
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
	public String getNowDate() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DATE, 0);
		return new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss.SSS").format(date.getTime()).toString();
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

	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	
}
