package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="customer")
public class Account {
	private String username;
	private String name;
	
	public Account() {
		
	}

	public Account(String name, String username) {
		this.setName(name);
		this.setUsername(username);
	}
	
	@XmlElement(name="name")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlAttribute(name="account")
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
}
