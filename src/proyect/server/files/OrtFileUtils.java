package proyect.server.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class OrtFileUtils {

	public static String getFullFileName(String fileName) throws IOException {
		File x = new File(fileName);
		if (x.exists()) {
			return x.getCanonicalPath();
		} else {
			throw new IOException("File or Directory no existe");
		}
	}

	public static String checkFolder(String pathname, boolean crear) throws RuntimeException, IOException {
		File x = new File(pathname);
		String res = "ya existe";
		if (!x.exists() && crear) {
			try {
				x.mkdirs();
				res = x.getCanonicalPath();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				throw new IOException("folder or file already exists");
			}
		} else if (!x.exists() && !crear) {
			throw new RuntimeException("El path no existe");
		} else if (!x.isDirectory()) {
			throw new RuntimeException(x.getName() + " existe, pero no es directorio");
		}
		return res;
	}

	public static String buscarArchivo(String fileName) {
		String res = null;
//		File nombre = new File(fileName);
//		File root = new File(nombre.getAbsolutePath().replaceAll("/" + nombre.getName(), ""));
//		try {
//			boolean recursive = true;
////			Collection files = FileUtils.listFiles(root, null, recursive);
//			
//			for (Iterator iterator = files.iterator(); iterator.hasNext();) {
//				File file = (File) iterator.next();
//
//				if (file.getName().contains(fileName)) {
//					res = file.getAbsolutePath();
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return res;
	}

	public static void mostrarArchivoEnteros(String fullFilename) throws IOException, FileNotFoundException {
		String a = OrtFileUtils.buscarArchivo(fullFilename);
		File f = new File(a);
		if (!f.canRead()) {
			f.setReadable(true);
		}
		try {
			FileInputStream fr = new FileInputStream(f);
			boolean ok = true;
			do {
				int num = fr.read();
				if (num == -1) {
					ok = false;
					fr.close();
				} else {
					System.out.println(num);
				}
			} while (ok);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static Boolean existe(File f) {
		return (f.exists()) ? true : false;
	}

	public static Boolean isFile(File f) {
		return (f.isDirectory()) ? true : false;
	}

	public static Boolean isDirectory(File f) {
		return (!f.isFile()) ? true : false;
	}
}