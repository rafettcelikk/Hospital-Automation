package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class Clinic {
	private int id;
	private String name;
	
	DBConnection connection = new DBConnection();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pr = null;
	
	public Clinic(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Clinic() {}
	
	public ArrayList<Clinic> getClinicList() throws SQLException{
		ArrayList<Clinic> clinicList = new ArrayList<>();
		Clinic obj;
		Connection connect = connection.connectDB();
		try {
			st = connect.createStatement();
			rs = st.executeQuery("SELECT * FROM clinic");
			while(rs.next()) {
				obj = new Clinic();
				obj.setId(rs.getInt("id"));
				obj.setName(rs.getString("name"));
				clinicList.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			st.close();
			rs.close();
			connect.close();
		}
		return clinicList;
	}
	
	public boolean addClinic(String name) {
		String query = "INSERT INTO clinic (name) VALUES(?)";
		boolean key = false;
		Connection connect = connection.connectDB();
		try {
			st = connect.createStatement();
			pr = connect.prepareStatement(query);
			pr.setString(1, name);
			pr.executeUpdate();
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(key)
			return true;
		else 
			return false;
	}
	
	public boolean deleteClinic(int id) {
		String query = "DELETE FROM clinic WHERE id=?";
		boolean key = false;
		Connection connect = connection.connectDB();
		try {
			st = connect.createStatement();
			pr = connect.prepareStatement(query);
			pr.setInt(1, id);
			pr.executeUpdate();
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(key)
			return true;
		else 
			return false;
	}
	
	public boolean updateClinic(int id, String name) {
		String query = "UPDATE clinic SET name=? WHERE id=?";
		boolean key = false;
		Connection connect = connection.connectDB();
		try {
			st = connect.createStatement();
			pr = connect.prepareStatement(query);
			pr.setString(1, name);
			pr.setInt(2, id);
			pr.executeUpdate();
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(key)
			return true;
		else 
			return false;
	}
	
	public Clinic getFetch(int id) {
		Connection connect = connection.connectDB();
		Clinic clinic = new Clinic();
		try {
			st = connect.createStatement();
			rs = st.executeQuery("SELECT * FROM clinic WHERE id =" + id);
			while(rs.next()) {
				clinic.setId(rs.getInt("id"));;
				clinic.setName(rs.getString("name"));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clinic;
	}
	
	public ArrayList<User> getClinicDocList(int clinic_id) throws SQLException{
		ArrayList<User> docList = new ArrayList<>();
		User obj;
		try {
			Connection connect = connection.connectDB();
			st = connect.createStatement();
			rs = st.executeQuery("SELECT u.id, u.tcnu, u.name, u.type, u.password FROM worker w LEFT JOIN user u ON w.user_id = u.id WHERE clinic_id= " + clinic_id);
			while(rs.next()) {
				obj = new User(rs.getInt("u.id"), rs.getString("u.tcnu"), rs.getString("u.password"), rs.getString("u.name"), rs.getString("u.type"));
				docList.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return docList;
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
	
	

}
