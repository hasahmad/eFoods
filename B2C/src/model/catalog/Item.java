package model.catalog;

import model.helpers.Utils;

public class Item {
	
	private String name, number;
	private double price, totalPrice;
	private int quantity, catID;
	
	public Item() {
	}
	
	public Item(String name, double price, int quantity, String number) throws Exception {
		this(name, price, quantity, number, 0);
	}

	public Item(String name, double price, int quantity, String number, int catId) throws Exception {
		setName(name);
		setPrice(price);
		setQuantity(quantity);
		setNumber(number);
		setCatID(catID);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getPrice() {
		return Utils.round(price, 2);
	}
	
	public void setPrice(double price) throws Exception {
		if (price < 0.0) {
			throw new IllegalArgumentException("Price < 0.0");
		}
		this.price = Utils.round(price, 2);
	}
	
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
	
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = Utils.round(totalPrice, 2);
	}

	public double getTotalPrice() {
		return Utils.round(totalPrice, 2);
	}
	
	public void increaseQty() {
		this.quantity++;
	}
	
	public double computeTotalPrice() {
		this.setTotalPrice(this.price * this.quantity);
		return Utils.round(this.totalPrice, 2);
	}
	
	public String toString() {
		return String.format("Name: %s | Price: %s | Quantity: %s | Number: %s | CatId: %s \n", name, price, quantity, number, catID);
	}
}
