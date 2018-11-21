package helpers;

import java.io.InputStream;
import java.io.StringReader;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


public class Utils {

	public static void renderXsltTHtml(String xml, InputStream xslIs, Writer out) throws Exception {
		DocumentBuilder factory_db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = factory_db.parse(new InputSource(new StringReader(xml)));
		Source xmlSource = new DOMSource(doc);
		
		TransformerFactory transfomerFactory = TransformerFactory.newInstance();
		Templates cachedXSLT = transfomerFactory.newTemplates(new StreamSource(xslIs));
		Transformer transformer = cachedXSLT.newTransformer();
		
		transformer.transform(xmlSource, new StreamResult(out));
	}

	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
