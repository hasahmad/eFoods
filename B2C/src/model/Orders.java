package model;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;

import model.dao.OrderDAO;

public class Orders {
	private static Orders instance = null;
	
	private Orders() {
		
	}
	
	public static Orders getInstance() {
		if (instance == null) {
			instance = new Orders();
		}
		return instance;
	}
	
	public synchronized StringWriter createPO(String filePath, String fileName, String xslFilename, OrderDAO dao, Order order) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(order.getClass());
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		
		StringWriter sw = new StringWriter();
		sw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
		sw.write("<?xml-stylesheet type=\"text/xsl\" href=\"" + xslFilename + "\"?>\n");

		marshaller.marshal(order, new StreamResult(sw));
		FileWriter orderFileWrite = dao.getFileWriter(fileName);
		orderFileWrite.write(sw.toString());
		orderFileWrite.close();
		
		return sw;
	}

}
