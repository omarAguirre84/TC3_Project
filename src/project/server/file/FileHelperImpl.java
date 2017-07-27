package project.server.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import project.exceptions.AlreadyExistsException;

public final class FileHelperImpl implements FileHelper{
	private File file;
	private ArrayList<String> logsStack;
	private int cont;

	public FileHelperImpl() {
		this.logsStack = new ArrayList<String>();
		this.cont = 0;

		try {
			this.file = createFile("log-" + getTodayDate() + ".txt", this.createDirectory("logs"));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	@Override
	public void save(String msg) {
		logsStack.add(msg);
		cont++;
		if (cont >= 10) {
			if (writeTheFileToDisk(this.file)) {
				logsStack.clear();
				cont = 0;
			}
		}
	}
	@Override
	public ArrayList<String> getAll() {
		ArrayList<String> res = null;
		if (this.file.exists()) {
			String a = file.getName();
			if (!file.canRead()) {
				file.setReadable(true);
			}
			try {
				res = new ArrayList<String>();
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				do {
					String row = br.readLine();
					res.add(row);
				} while (br.ready());
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return res;
	}
	@Override
	public ArrayList<String> getByParam(String param) {
		ArrayList<String> res = null;
		res = new ArrayList<String>();
		
		return res;
	}
	@Override
	public void deleteByParam(String param){
		
	}
	
	private boolean writeTheFileToDisk(File file) {
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

			for (String s : logsStack) {
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

	private File createDirectory(String nombre) throws AlreadyExistsException {
		File folder = new File(nombre);
		try {
			folder.mkdirs();
		} catch (Exception e) {
			throw new AlreadyExistsException("folder "+nombre);
		}
		return folder;
	}

	private String getTodayDate() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DATE, 0);
		return new SimpleDateFormat("dd-MM-yyyy").format(date.getTime());
	}
}
