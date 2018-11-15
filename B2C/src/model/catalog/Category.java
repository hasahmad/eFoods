package model.catalog;

import java.sql.Blob;

public class Category {
	
	private String name, description;
	private int id;
	private Blob picture;
	
	public Category() {
	}

	public Category(String name, String description, int id, Blob picture) {
		setName(name);
		setDescription(description);
		setId(id);
		setPicture(picture);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Blob getPicture() {
		return picture;
	}
	
	public void setPicture(Blob picture) {
		this.picture = picture;
	}	
	
	
	public String toString() {
		return String.format("Name: %s | Description: %s | ID: %s | Picture: %s \n", name, description, id, picture);
		//fix this
	}
	
	

}
