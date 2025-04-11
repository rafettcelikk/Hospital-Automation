package Model;

import Helper.DBConnection;

public class User {
	private int id;
	private String tcnu, password, name, type;
	DBConnection connection = new DBConnection();
	public User(int id, String tcnu, String password, String name, String type) {
		super();
		this.id = id;
		this.tcnu = tcnu;
		this.password = password;
		this.name = name;
		this.type = type;
	}
	
	public User() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTcnu() {
		return tcnu;
	}
	public void setTcnu(String tcnu) {
		this.tcnu = tcnu;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
