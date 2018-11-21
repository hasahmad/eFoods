package model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="report")
public class Report {
	private List<Product> products;
	private String generatedOn;

	public Report(List<Product> products) {
		this.products = products;
		this.generatedOn = new Date().toString();
	}
	
	public Report() {
		this(new ArrayList<Product>());
	}

	@XmlElementWrapper(name="products")
	@XmlElement(name="product")
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public String getGeneratedOn() {
		return generatedOn;
	}

	public void setGeneratedOn(String generatedOn) {
		this.generatedOn = generatedOn;
	}

	@Override
	public String toString() {
		return this.products.toString();
	}
}
