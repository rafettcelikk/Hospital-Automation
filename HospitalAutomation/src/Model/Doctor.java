package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Doctor extends User{
	Connection connect = connection.connectDB();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pr = null;
	public Doctor() {
		super();
		
	}
	public Doctor(int id, String tcnu, String password, String name, String type) {
		super(id, tcnu, password, name, type);
		
	}
	
	public boolean addWhour(int doctor_id, String doctor_name, String wdate) throws SQLException {
		String query = "INSERT INTO whour (doctor_id, doctor_name, wdate) VALUES(?, ?, ?)";
		int key = 0;
		int count = 0;
		
		try {
			st = connect.createStatement();
			rs = st.executeQuery("SELECT * FROM whour WHERE status='a' AND doctor_id=" + doctor_id + " AND wdate='" + wdate + "'");
			while(rs.next()) {
				count++;
				break;
			}
			if(count == 0) {
				pr = connect.prepareStatement(query);
				pr.setInt(1, doctor_id);
				pr.setString(2, doctor_name);
				pr.setString(3, wdate);
				pr.executeUpdate();
			}
			key = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(key == 1)
			return true;
		else
			return false;
	}
	
	public ArrayList<Whour> getWhourList(int doctor_id) throws SQLException{
		ArrayList<Whour> whourList = new ArrayList<>();
		Whour obj;
		try {
			st = connect.createStatement();
			rs = st.executeQuery("SELECT * FROM whour WHERE status='a' AND doctor_id=" + doctor_id);
			while(rs.next()) {
				obj = new Whour();
				obj.setId(rs.getInt("id"));
				obj.setDoctor_id(rs.getInt("doctor_id"));
				obj.setDoctor_name(rs.getString("doctor_name"));
				obj.setWdate(rs.getString("wdate"));
				obj.setStatus(rs.getString("status"));
				whourList.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return whourList;
	}
	
	public boolean deleteWhour(int id) {
		String query = "DELETE FROM whour WHERE id=?";
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
}
