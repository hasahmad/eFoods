package model.catalog;

public class Item {
	
	private String name;
	private double price;
	private int quantity, number, catID;
	
	Item() {
	}
	
	Item(String name, double price, int quantity, int number) {
		setName(name);
		setPrice(price);
		setQuantity(quantity);
		setNumber(number);
		//setCatID(catID);
		//can add catID if needed but for now leave it out
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
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}	
	
	public int getCatID() {
		return catID;
	}
	
	public void setCatID(int catID) {
		this.catID = catID;
	}	
	
	public String toString() {
		return String.format("Name: %s | Price: %s | Quantity: %s | Number: %s \n", name, price, quantity, number);
	}
	
	

}
