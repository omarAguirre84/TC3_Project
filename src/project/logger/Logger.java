package project.logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

import project.clients.Client;
import project.file.FileHelper;

public final class Logger{
	private final String SEPARATOR = "; ";
	private final String eof = "; ";
	private Calendar date;
	private FileHelper fileHelper;

	public Logger(FileHelper fileHelper) {
		this.fileHelper = fileHelper;
	}
	
	public void log(Client observer, String msg) {
		String who = observer.getNickName();
//		String socket = observer.getSw().getClientSocket().getRemoteSocketAddress().toString().replace("/", "");
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
	
//	private String getTodayDate() {
//		date = Calendar.getInstance();
//		date.add(Calendar.DATE, 0);
//		return new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(date.getTime());
//	}
	private String getNowDate() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DATE, 0);
		return new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss.SSS").format(date.getTime()).toString();
	}

//	public void update(Observable o, Object arg) {
//		String who = ((Client) o).getNickName();
//		String socket = ((Client) o).getClientSocket().getRemoteSocketAddress().toString().replace("/", "");
//		String content = arg.toString();
//		this.log(who, socket, content);
//	}
}
