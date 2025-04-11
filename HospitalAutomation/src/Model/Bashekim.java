package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Bashekim extends User{
	Connection connect = connection.connectDB();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pr = null;
	
	public Bashekim(int id, String tcnu, String password, String name, String type) {
		super(id, tcnu, password, name, type);
	}
	public Bashekim() {
		
	}
	
	public ArrayList<User> getDocList() throws SQLException{
		ArrayList<User> docList = new ArrayList<>();
		User obj;
		try {
			st = connect.createStatement();
			rs = st.executeQuery("SELECT * FROM user WHERE type= 'doktor'");
			while(rs.next()) {
				obj = new User(rs.getInt("id"), rs.getString("tcnu"), rs.getString("password"), rs.getString("name"), rs.getString("type"));
				docList.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return docList;
	}
	
	public boolean addDoc(String tcnu, String password, String name) {
		String query = "INSERT INTO user (tcnu, password, name, type) VALUES(?, ?, ?, ?)";
		boolean key = false;
		try {
			st = connect.createStatement();
			pr = connect.prepareStatement(query);
			pr.setString(1, tcnu);
			pr.setString(2, password);
			pr.setString(3, name);
			pr.setString(4, "doktor");
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
	
	public boolean deleteDoc(int id) {
		String query = "DELETE FROM user WHERE id=?";
		boolean key = false;
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
	
	public boolean updateDoc(int id, String tcnu, String password, String name) {
		String query = "UPDATE user SET tcnu=?, password=?, name=? WHERE id=?";
		boolean key = false;
		try {
			st = connect.createStatement();
			pr = connect.prepareStatement(query);
			pr.setString(1, tcnu);
			pr.setString(2, password);
			pr.setString(3, name);
			pr.setInt(4, id);
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
	
	public boolean addWorker(int user_id, int clinic_id) {
		String query = "INSERT INTO worker (user_id, clinic_id) VALUES(?, ?)";
		boolean key = false;
		int count = 0;
		try {
			st = connect.createStatement();
			rs = st.executeQuery("SELECT * FROM worker WHERE clinic_id=" + clinic_id + " AND user_id=" + user_id);
			while(rs.next()) {
				count++;
			}
			if(count == 0) {
				pr = connect.prepareStatement(query);
				pr.setInt(1, user_id);
				pr.setInt(2, clinic_id);
				pr.executeUpdate();
			}
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(key)
			return true;
		else 
			return false;
	}
	
	public ArrayList<User> getClinicDocList(int clinic_id) throws SQLException{
		ArrayList<User> docList = new ArrayList<>();
		User obj;
		try {
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
}
