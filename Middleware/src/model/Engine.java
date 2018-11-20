package model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Engine {
	private static Engine instance = null;
	private Map<String, String[]> products;
	private List<String> processed;
	
	private Engine() {
		products = new HashMap<String, String[]>();
		processed = new ArrayList<String>();
	}
	
	public static Engine getInstance() {
		if (instance == null) {
			instance = new Engine();
		}
		return instance;
	}

	public List<String> getProcessed() {
		return processed;
	}

	public Map<String, String[]> getProducts() {
		return products;
	}

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
	
	public synchronized void processPO(File f, Map<String, String[]> products, List<String> processed) throws Exception {
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
						String itemQty = e.getElementsByTagName("quantity").item(0).getTextContent();

						if (itemNum != null && itemNum != "") {
							if (!products.containsKey(itemNum)) {
								products.put(itemNum, new String[]{itemName, itemQty});
								
							} else {

								String[] product = products.get(itemNum);
								product[1] += itemQty;
							}
						}
					}
				}

				processed.add(f.getName());
			}
		}
	}
	
	public synchronized void processPO(File f) throws Exception {
		processPO(f, this.products, this.processed);
	}

	
	
	
	
	private synchronized static String getFileExtension(File file) {
	    String name = file.getName();
	    int lastIndexOf = name.lastIndexOf(".");
	    if (lastIndexOf == -1) {
	        return ""; // empty extension
	    }
	    return name.substring(lastIndexOf);
	}

	public static void main(String[] args) throws Exception {
		String posDir = "/Users/hasahmad/ws_4413/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/eFoods/POs/";
		Engine e = Engine.getInstance();
		e.processPOs(posDir);
	}

}
