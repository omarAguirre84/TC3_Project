package proyect.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import proyect.server.files.OrtFileUtils;

public final class Data {
	private File file;
//	private FileWriter fw;
	private ArrayList<String> logsToSave;
	private int cont;

	public Data() {
		this.logsToSave = new ArrayList<String>();
		this.cont = 0;

		try {
			this.file = createFile("log-" + getTodayDate() + ".txt", this.createDirectory("logs-" + getTodayDate()));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void save(String msg) {
		logsToSave.add(msg);
		cont++;
		if (cont >= 10) {
			if (writeTheFileToDisk(file)) {
				logsToSave.clear();
				cont = 0;
			}
		}
	}

	public boolean writeTheFileToDisk(File file) {
		boolean res = false;

		if (!file.canWrite()) {
			file.setWritable(true);
		}
		try {
			FileWriter fw;
			if (file.exists()) {
				fw = new FileWriter(file, true);
			} else {
				fw = new FileWriter(file);
			}
			
			for (String s : logsToSave) {
				fw.write(s + "\n");
			}

			 fw.flush();
			 fw.close();
			res = true;
		} catch (IOException e) {
			res = false;
			System.out.println(e.getMessage());
		}
		return res;
	}

	public void readTheFile(File f) {
		if (this.file.exists()) {

		}
	}

	public void find(String search) {

	}

	private File createFile(String nombre, File folder) {
		File file = new File(folder, nombre);
		try {
			if (OrtFileUtils.isDirectory(folder)) {
				if (!file.exists()) {
					file.createNewFile();
				} 
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		return file;
	}

	private File createDirectory(String nombre) throws Exception {
		File folder = null;
		try {
			folder = new File(nombre);
			folder.mkdirs();
		} catch (Exception e) {
			throw new Exception("error");
		}
		return folder;
	}

	private String getTodayDate() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DATE, 0);
		return new SimpleDateFormat("dd-MM-yyyy").format(date.getTime());
	}
	// private String getNowDate() {
	// Calendar date = Calendar.getInstance();
	// date.add(Calendar.DATE, 0);
	// return new
	// SimpleDateFormat("dd-MM-yyyy_HH:mm:ss.SSS").format(date.getTime()).toString();
	// }
}
