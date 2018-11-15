package model.catalog;

public class Item {
	
	private String name, number;
	private double price, totalPrice;
	private int quantity, catID;
	
	public Item() {
	}
	
	public Item(String name, double price, int quantity, String number) {
		this(name, price, quantity, number, 0);
	}

	public Item(String name, double price, int quantity, String number, int catId) {
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
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
		this.totalPrice = Math.round(totalPrice);
	}
	
	public double getTotalPrice() {
		return totalPrice;
	}
	
	public double computeTotalPrice() {
		this.totalPrice = this.price * this.quantity;
		return Math.round(this.totalPrice);
	}
	
	public String toString() {
		return String.format("Name: %s | Price: %s | Quantity: %s | Number: %s | CatId: %s \n", name, price, quantity, number, catID);
	}
	
	

}
