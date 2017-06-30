package project.server;

import java.util.ArrayList;

public interface FileHelper {
	ArrayList<String> getAll();
	ArrayList<String> getByParam(String param);
	
	void save(String param);
	void deleteByParam(String param);
}
