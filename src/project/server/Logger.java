package project.server;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

public final class Logger {
	private static final String SEPARATOR = "; ";
	private static final String eof = "; ";
	private static StringBuilder messageToLog;
	
	private static Calendar date;
	private static FileHelper fileHelper;

	private Logger() {
	}
	
	public static void log(String who, String socket, String content) {
		messageToLog = new StringBuilder();
		
		messageToLog.append(getNowDate());
		messageToLog.append(SEPARATOR);
		messageToLog.append(who);
		messageToLog.append(SEPARATOR);
		messageToLog.append(socket);
		messageToLog.append(SEPARATOR);
		messageToLog.append(content + eof);
	}
//	private static String formReg(String who, String socket, String content) {
//		 return getNowDate()
//				 + SEPARATOR
//				 + who
//				 + SEPARATOR
//				 + socket
//				 + SEPARATOR
//				 + content + eof;
//	}
	
	private static String getTodayDate() {
		date = Calendar.getInstance();
		date.add(Calendar.DATE, 0);
		return new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(date.getTime());
	}
	private static String getNowDate() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DATE, 0);
		return new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss.SSS").format(date.getTime()).toString();
	}
	private static void setFileHelper(FileHelper fh){
		fileHelper = fh;
	}
}
