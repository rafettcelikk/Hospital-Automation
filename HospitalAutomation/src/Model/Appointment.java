package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class Appointment {
	private int id, doctor_id, sick_id;
	private String doctor_name, sick_name, app_date;
	DBConnection connection = new DBConnection();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pr = null;
	public Appointment() {
		
	}
	public Appointment(int id, int doctor_id, int sick_id, String doctor_name, String sick_name, String app_date) {
		super();
		this.id = id;
		this.doctor_id = doctor_id;
		this.sick_id = sick_id;
		this.doctor_name = doctor_name;
		this.sick_name = sick_name;
		this.app_date = app_date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDoctor_id() {
		return doctor_id;
	}
	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}
	public int getSick_id() {
		return sick_id;
	}
	public void setSick_id(int sick_id) {
		this.sick_id = sick_id;
	}
	public String getDoctor_name() {
		return doctor_name;
	}
	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}
	public String getSick_name() {
		return sick_name;
	}
	public void setSick_name(String sick_name) {
		this.sick_name = sick_name;
	}
	public String getApp_date() {
		return app_date;
	}
	public void setApp_date(String app_date) {
		this.app_date = app_date;
	}
	
	public ArrayList<Appointment> getSickList(int sick_id) throws SQLException{
		ArrayList<Appointment> sickList = new ArrayList<>();
		Appointment obj;
		Connection connect = connection.connectDB();
		try {
			st = connect.createStatement();
			rs = st.executeQuery("SELECT * FROM appointment WHERE sick_id = " + sick_id);
			while(rs.next()) {
				obj = new Appointment();
				obj.setId(rs.getInt("id"));
				obj.setDoctor_id(rs.getInt("doctor_id"));
				obj.setDoctor_name(rs.getString("doctor_name"));
				obj.setSick_id(rs.getInt("sick_id"));
				obj.setSick_name(rs.getString("sick_name"));
				obj.setApp_date(rs.getString("app_date"));
				sickList.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			st.close();
			rs.close();
			connect.close();
		}
		return sickList;
	}
	
	public ArrayList<Appointment> getDocList(int doctor_id) throws SQLException{
		ArrayList<Appointment> docList = new ArrayList<>();
		Appointment obj;
		Connection connect = connection.connectDB();
		try {
			st = connect.createStatement();
			rs = st.executeQuery("SELECT * FROM appointment WHERE doctor_id = " + doctor_id);
			while(rs.next()) {
				obj = new Appointment();
				obj.setId(rs.getInt("id"));
				obj.setDoctor_id(rs.getInt("doctor_id"));
				obj.setDoctor_name(rs.getString("doctor_name"));
				obj.setSick_id(rs.getInt("sick_id"));
				obj.setSick_name(rs.getString("sick_name"));
				obj.setApp_date(rs.getString("app_date"));
				docList.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			st.close();
			rs.close();
			connect.close();
		}
		return docList;
	}
}
