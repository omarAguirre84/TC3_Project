package project.logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import project.clients.Client;
import project.file.FileHelper;

public final class Logger{
	private final String SEPARATOR = "; ";
	private final String eof = "; ";
	private FileHelper fileHelper;

	public Logger(FileHelper fileHelper) {
		this.fileHelper = fileHelper;
	}
	
	public void logObserverActivity(Client observer, String msg) {
		String who = observer.getNickName();
		String socket = observer.getSw().getClientIp();
		String content = msg.toString();
		
		fileHelper.save(
				getNowDate()+
				SEPARATOR+
				who+
				SEPARATOR+
				socket+
				SEPARATOR+
				content + eof
		);
	}
	
	private String getNowDate() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DATE, 0);
		return new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss.SSS").format(date.getTime()).toString();
	}
}
