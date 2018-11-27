package model.helpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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

import model.Account;

public class Utils {
	
	public static final String AUTH_URL = "https://www.eecs.yorku.ca/~roumani/servers/auth/oauth.cgi";

	public static String doHttpGET(Map<String, String> params, String Url, String contentType) throws Exception 
	{
		StringBuilder result = new StringBuilder();
		for (Map.Entry<String, String> entry: params.entrySet()) {
			result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
			result.append("&");
		}
		String parameters = result.toString();
		
		URL url = new URL(Url + "?" + parameters);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", contentType);
		con.setDoOutput(true);
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		con.disconnect();
		
		return response.toString();
	}
	
	public static void doAuth(String username, String pass, String back) throws Exception {
		HttpURLConnection connection = (HttpURLConnection) new URL(AUTH_URL + "?back=" + back).openConnection();
		String encoded = Base64.getEncoder().encodeToString((username+":"+pass).getBytes(StandardCharsets.UTF_8));  //Java 8
		connection.setRequestProperty("Authorization", "Basic "+encoded);
	}
	
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
	
	public static Boolean isValidUser(Account user) {
		boolean result = false;
		if (user != null && user.getName() != null && !user.getName().isEmpty() 
				&& user.getUsername() != null && !user.getUsername().isEmpty()) {
			result = true;
		}
		return result;
	}

}
