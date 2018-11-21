package model;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="item")
public class Product {
	private String number;
	private String name;
	private int qty;
	
	public Product() {
	}
	
	public Product(String number, String name, int qty) {
		this.setNumber(number);
		this.setName(name);
		this.setQty(qty);
	}
	
	@XmlAttribute(name="number")
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	@XmlElement(name="name")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name="quantity")
	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}
	
	public synchronized void increaseQty() {
		this.qty++;
	}
	
	public synchronized void increaseQty(int qty) {
		this.qty += qty;
	}
	
	@Override
	public String toString() {
		return String.format("{ Number: %s | Name: %s | Quantity: %s }\n", number, name, qty);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Product)) return false;
		
		Product other = (Product) obj;
		if (!this.number.equals(other.number)) {
			return false;
		}

		return true;
	}
}
