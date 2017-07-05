package project.server;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

public final class Logger implements Observer {
	private final String SEPARATOR = "; ";
	private final String eof = "; ";
	private Calendar date;
	private FileHelper fileHelper;

	public Logger(FileHelper fileHelper) {
		this.fileHelper = fileHelper;
	}
	
	public void log(String who, String socket, String content) {
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
	
	private String getTodayDate() {
		date = Calendar.getInstance();
		date.add(Calendar.DATE, 0);
		return new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(date.getTime());
	}
	private String getNowDate() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DATE, 0);
		return new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss.SSS").format(date.getTime()).toString();
	}

	@Override
	public void update(Observable o, Object arg) {
		String who = ((Session) o).getUserName();
		String socket = ((Session) o).getClientSocket().getRemoteSocketAddress().toString().replace("/", "");
		String content = arg.toString();
		this.log(who, socket, content);
	}
}
