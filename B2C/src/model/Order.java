package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.catalog.Item;
import model.helpers.Utils;

public class Order {
	private static final double HST_COST = 0.13;
	private static final double SHIPPING_COST = 5.0;
	private static final double SHIPPING_BUFFER_COST = 100.0;
	
	private List<Item> items;
	private Account customer;
	private double total;
	private double shipping;
	private double HST;
	private double grandTotal;
	
	private int id;
	private Date submitted;

	public Order() {
		this(new ArrayList<Item>());
	}

	public Order(List<Item> items) {
		this(0, items);
	}
	
	public Order(int id, List<Item> items) {
		this.id = id;
		this.items = items;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Account getCustomer() {
		return customer;
	}

	public void setCustomer(Account customer) {
		this.customer = customer;
	}

	public double getTotal() {
		return Utils.round(total, 2);
	}

	public void setTotal(double total) {
		this.total = Utils.round(total, 2);
	}

	public double getShipping() {
		return Utils.round(shipping, 2);
	}

	public void setShipping(double shipping) {
		this.shipping = Utils.round(shipping, 2);
	}

	public double getHST() {
		return Utils.round(HST, 2);
	}

	public void setHST(double hST) {
		HST = Utils.round(hST, 2);
	}

	public double getGrandTotal() {
		return Utils.round(grandTotal, 2);
	}

	public void setGrandTotal(double grandTotal) {
		this.grandTotal = Utils.round(grandTotal, 2);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Date submitted) {
		this.submitted = submitted;
	}

	public void computeGrandTotal() {
		this.setGrandTotal(this.getTotal() + this.getHST() + this.getShipping());
	}

	public void computeTotalHST() {
		this.setHST((this.getTotal() + this.getShipping()) * HST_COST);
	}

	public void computeShipping() {
		if (this.getTotal() > 0 && this.getTotal() <= SHIPPING_BUFFER_COST) {
			this.setShipping(SHIPPING_COST);
		} else {
			this.setShipping(0.0);
		}
	}

	public void computeTotalCost() {
		double total = 0.0;
		for (Item it: this.getItems()) {
			total += it.computeTotalPrice();
		}
		this.setTotal(total);
	}
	
	public void computeAllCosts() {
		this.computeTotalCost();
		this.computeTotalHST();
		this.computeShipping();
		this.computeGrandTotal();
	}
}
