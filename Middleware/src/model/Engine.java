package model;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Engine {
	private static Engine instance = null;

	private static String posDir = System.getProperty("user.home") + "/ws_4413/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/eFoods/POs/";
	private String processedOrdersFileName;
	private List<Product> products;
	private List<String> processed;
	private Report report;


	// ---------------------------------------------------
	private Engine() throws Exception {
		this(posDir);
	}

	private Engine(String POsDir) throws Exception {
		this(POsDir, null);
	}

	private Engine(String POsDir, String processedOrdersFileName) throws Exception {
		products = new ArrayList<Product>();
		processed = new ArrayList<String>();
		report = new Report();
	
		this.setPOsDir(POsDir);
		if (!this.checkPOsDir(POsDir)) {
			this.processedOrdersFileName = processedOrdersFileName;
			this.getProcessedFromFile(processedOrdersFileName);

			processPOs(posDir);
			report.setProducts(products);
			this.saveProcessedToFile();
		}
	}


	// ---------------------------------------------------
	public static Engine getInstance(String POsDir, String processedOrdersFileName) throws Exception {
		if (instance == null) {
			instance = new Engine(POsDir, processedOrdersFileName);
		}
		return instance;
	}

	public static Engine getInstance(String POsDir) throws Exception {
		return getInstance(POsDir, null);
	}

	public static Engine getInstance() throws Exception {
		return getInstance(posDir);
	}


	// ---------------------------------------------------
	public List<String> getProcessed() {
		return processed;
	}

	public List<Product> getProducts() {
		return products;
	}
	
	public void setPOsDir(String POsDir) {
		if (checkPOsDir(POsDir) ) {
			posDir = POsDir;
		}
	}
	
	public boolean checkPOsDir(String POsDir) {
		if (POsDir != null && !POsDir.isEmpty()) {
			return true;
		}
		return false;
	}


	// ---------------------------------------------------
	public synchronized void processPOs(File[] files) throws Exception {
		for (File f: files) {
			processPO(f);
		}
	}

	public synchronized void processPOs(String dirName) throws Exception {
		processPOs(new File(dirName));
	}

	public synchronized void processPOs(File dir) throws Exception {
		processPOs(dir.listFiles());
	}


	// ---------------------------------------------------
	public synchronized void processPO(File f, List<Product> products, List<String> processed) throws Exception {
		if (getFileExtension(f).equals(".xml")) {
			if (!processed.contains(f.getName())) {

				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(f);
				doc.getDocumentElement().normalize();
	
				NodeList items = doc.getElementsByTagName("item");
				for (int i=0; i<items.getLength(); i++) {
					Node item = items.item(i);
					if (item.getNodeType() == Node.ELEMENT_NODE) {

						Element e = (Element) item;

						String itemNum = e.getAttribute("number");
						String itemName = e.getElementsByTagName("name").item(0).getTextContent();
						String itemQtyVal = e.getElementsByTagName("quantity").item(0).getTextContent();
						
						if (itemNum != null && itemNum != "") {
							int itemQty = Integer.parseInt(itemQtyVal);

							Product p = new Product(itemNum, itemName, itemQty);
							if (!products.contains(p)) {
								products.add(p);
							} else {
								p = products.get(products.indexOf(p));
								p.increaseQty(itemQty);
								products.set(products.indexOf(p), p);
							}
						}
					}
				}

				processed.add(f.getName());
				System.out.println("hahahha");
			}
		}
	}

	public synchronized void processPO(File f) throws Exception {
		processPO(f, this.products, this.processed);
	}


	// ---------------------------------------------------
	public synchronized JsonObject createJsonReport(List<Product> products) throws Exception {
		JsonObject json = new JsonObject();
		JsonElement jsonElement = new Gson().toJsonTree(report);
		json.add("data", jsonElement);
		return json;
	}

	public synchronized JsonObject createJsonReport() throws Exception {
		return createJsonReport(this.products);
	}


	// ---------------------------------------------------
	public synchronized String createXmlReport(List<Product> products) throws Exception {
		report.setProducts(products);
		this.saveProcessedToFile();

		JAXBContext jc = JAXBContext.newInstance(Report.class);
		Marshaller m = jc.createMarshaller();
		StringWriter sw = new StringWriter();
		m.marshal(new Report(), sw);
		return sw.toString();
	}

	public synchronized String createXmlReport() throws Exception {
		return createXmlReport(this.products);
	}


	// ---------------------------------------------------
	public synchronized List<String> getProcessedFromFile(String filename) throws Exception {
		File f = this.checkProcessedOrdersFile(filename);
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		while ((line = br.readLine()) != null) {
			if (!processed.contains(line)) {
				processed.add(line);
			}
		}
		br.close();
		return processed;
	}

	public synchronized List<String> getProcessedFromFile() throws Exception {
		return this.getProcessedFromFile(this.processedOrdersFileName);
	}


	// ---------------------------------------------------
	public synchronized void saveProcessedToFile(String filename) throws Exception {
		File f = this.checkProcessedOrdersFile(filename);
		FileWriter fw = new FileWriter(f);
		for (String str: processed) {
			if (!str.isEmpty() && str != null) {
				fw.write(str + "\n");
			}
		}
		fw.close();
	}

	public synchronized void saveProcessedToFile() throws Exception {
		saveProcessedToFile(this.processedOrdersFileName);
	}


	// ---------------------------------------------------
	public synchronized File checkProcessedOrdersFile(String filename) throws Exception {
		System.out.println(filename);
		File f = new File(filename);
		if (!f.exists()) {
			f.createNewFile();
		}
		return f;
	}

	public synchronized File checkProcessedOrdersFile() throws Exception {
		return checkProcessedOrdersFile(this.processedOrdersFileName);
	}


	// ---------------------------------------------------
	private synchronized static String getFileExtension(File file) {
	    String name = file.getName();
	    int lastIndexOf = name.lastIndexOf(".");
	    if (lastIndexOf == -1) {
	        return ""; // empty extension
	    }
	    return name.substring(lastIndexOf);
	}

	public synchronized void clearProducts() {
		products.clear();
	}

	public synchronized void clearProcessed() {
		processed.clear();
	}


}
