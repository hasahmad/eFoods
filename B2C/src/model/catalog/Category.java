package model.catalog;

public class Category {
	
	private String name, description;
	private int id;
	private String picture;
	
	Category() {
	}
	
	Category(String name, String description, int id, String picture) {
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
	
	public String getPicture() {
		return picture;
	}
	
	public void setPicture(String picture) {
		this.picture = picture;
	}	
	
	
	public String toString() {
		return String.format("Name: %s | Description: %s | ID: %s | Picture: %s \n", name, description, id, picture);
		//fix this
	}
	
	

}
