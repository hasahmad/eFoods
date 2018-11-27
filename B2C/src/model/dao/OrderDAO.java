package model.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;

public class OrderDAO {
	
	private File ordersDir;
	
	public OrderDAO() {
	}

	public OrderDAO(String ordersDir) {
		this(new File(ordersDir));
	}

	public OrderDAO(File ordersDir) {
		this.setOrdersDir(ordersDir);
		checkDir();
	}
	
	public synchronized String getOrderFileName(String accountNum, int id) {
		String result = "";
		if (id > 0) {
			result = getOrderFileNameFirstPart(accountNum) + id + ".xml";
		}
		return result;
	}

	public synchronized String getOrderFileNameFirstPart(String accountNum) {
		return "po" + accountNum + "_";
	}
	
	public synchronized File[] getPOs() {
		return ordersDir.listFiles();
	}
	
	public synchronized File[] getPOs(String accountNum) {
		return ordersDir.listFiles(new FilenameFilter() {
			@Override public boolean accept(File dir, String name) {
				return name.startsWith(getOrderFileNameFirstPart(accountNum));
			}
		});
	}
	
	public synchronized File getPO(String fileName) {
		return new File(ordersDir, fileName);
	}
	
	public synchronized FileWriter getFileWriter(String fileName) throws IOException {
		File poFile = getPO(fileName);
		if (poFile.exists()) {
			throw new IOException("File already exists.");
		}
		poFile.createNewFile();
		return new FileWriter(poFile);
	}

	/**
	 * Check if directory exists, else create a new one
	 */
	public synchronized void checkDir() {
		if (!ordersDir.exists()) {
			ordersDir.mkdir();
		}
	}
	
	public File getOrdersDir() {
		return this.ordersDir;
	}
	
	public void setOrdersDir(File dir) {
		this.ordersDir = dir;
	}

}
