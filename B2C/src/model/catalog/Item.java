package model.catalog;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import model.helpers.Utils;

@XmlRootElement(name="item")
public class Item {
	
	private String name, number, unit;

	private double price, totalPrice;
	private int quantity, catID;
	
	public Item() {
	}
	
	public Item(String name, double price, int quantity, String number, String unit) throws Exception {
		this(name, price, quantity, number, 0, unit);
	}

	public Item(String name, double price, int quantity, String number, int catId, String unit) throws Exception {
		setName(name);
		setPrice(price);
		setQuantity(quantity);
		setNumber(number);
		setCatID(catID);
		setUnit(unit);
	}
	
	@XmlElement(name="name")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement(name="price")
	public double getPrice() {
		return Utils.round(price, 2);
	}
	
	public void setPrice(double price) throws Exception {
		if (price < 0.0) {
			throw new IllegalArgumentException("Price < 0.0");
		}
		this.price = Utils.round(price, 2);
	}
	
	@XmlElement(name="quantity")
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) throws Exception {
		if (quantity < 0) {
			throw new IllegalArgumentException("Qty < 0");
		}
		this.quantity = quantity;
	}
	
	public void setQuantity(String quantity) throws Exception {
		int qty = Integer.parseInt(quantity);
		this.setQuantity(qty);
	}

	@XmlAttribute(name="number")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getCatID() {
		return catID;
	}

	public void setCatID(int catID) {
		this.catID = catID;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = Utils.round(totalPrice, 2);
	}
	
	@XmlElement(name="extended")
	public double getTotalPrice() {
		return Utils.round(totalPrice, 2);
	}
	
	public void increaseQty() {
		this.quantity++;
	}
	
	public void increaseQty(int qty) {
		this.quantity += qty;
	}
	
	public void increaseQty(String qtyStr) throws Exception {
		int qty = Integer.parseInt(qtyStr);
		this.setQuantity(qty);
	}
	
	public double computeTotalPrice() {
		this.setTotalPrice(this.price * this.quantity);
		return Utils.round(this.totalPrice, 2);
	}
	
	public String toString() {
		return String.format("Name: %s | Price: %s | Quantity: %s | Number: %s | CatId: %s | Unit: %s \n", name, price, quantity, number, catID, unit);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Item)) return false;
		
		Item other = (Item) obj;
		if (!this.number.equals(other.number)) {
			return false;
		}
		
		return true;
	}
	
	
}
