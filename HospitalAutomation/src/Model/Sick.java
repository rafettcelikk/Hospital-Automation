package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Helper.Helper;

public class Sick extends User {
	Connection connect = connection.connectDB();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pr = null;
	public Sick() {
	}

	public Sick(int id, String tcnu, String password, String name, String type) {
		super(id, tcnu, password, name, type);
	}
	
	public boolean register(String tcnu, String password, String name) throws SQLException {
		String query = "INSERT INTO user (tcnu, password, name, type) VALUES(?, ?, ?, ?)";
		int key = 0;
		boolean duplicate = false;
		
		try {
			st = connect.createStatement();
			rs = st.executeQuery("SELECT * FROM user WHERE tcnu='" + tcnu + "'");
			while(rs.next()) {
				duplicate = true;
				Helper.showMsg("Bu TC numarasından bulunmaktadır!");
				break;
			}
			if(!duplicate) {
				pr = connect.prepareStatement(query);
				pr.setString(1, tcnu);
				pr.setString(2, password);
				pr.setString(3, name);
				pr.setString(4, "hasta");
				pr.executeUpdate();
				key = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(key == 1)
			return true;
		else
			return false;
	}
	
	public boolean addAppointment(int doctor_id, String doctor_name, int sick_id, String sick_name, String app_date) throws SQLException {
		String query = "INSERT INTO appointment (doctor_id, doctor_name, sick_id, sick_name, app_date) VALUES(?, ?, ?, ?, ?)";
		int key = 0;
		try {
				pr = connect.prepareStatement(query);
				pr.setInt(1, doctor_id);
				pr.setString(2, doctor_name);
				pr.setInt(3, sick_id);
				pr.setString(4, sick_name);
				pr.setString(5, app_date);
				pr.executeUpdate();
				key = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(key == 1)
			return true;
		else
			return false;
	}
	
	public boolean updateWhourStatus(int doctor_id, String wdate) throws SQLException {
		String query = "UPDATE whour SET status = ? WHERE doctor_id = ? AND wdate = ?";
		int key = 0;
		try {
				pr = connect.prepareStatement(query);
				pr.setString(1, "p");
				pr.setInt(2, doctor_id);
				pr.setString(3, wdate);
				pr.executeUpdate();
				key = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(key == 1)
			return true;
		else
			return false;
	}
}
