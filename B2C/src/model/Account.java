package model;

public class Account {
	private int id;
	private String username;
	private String name;
	private String hash;
	
	public Account() {
		
	}

	public Account(int id) {
		this.setId(id);
	}

	public Account(int id, String name, String username, String hash) {
		this.setId(id);
		this.setName(name);
		this.setUsername(username);
		this.setHash(hash);
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getHash() {
		return hash;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}

}
