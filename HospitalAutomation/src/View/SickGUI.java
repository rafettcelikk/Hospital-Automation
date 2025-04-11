package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Helper.Helper;
import Helper.Item;
import Model.Appointment;
import Model.Clinic;
import Model.Sick;
import Model.Whour;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class SickGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel w_pane;
	private static Sick sick = new Sick();
	private Clinic clinic = new Clinic();
	private JTable tbl_doc;
	private DefaultTableModel doctorModel;
	private Object[] doctorData = null;
	private JTable tbl_whour;
	private Whour whour = new Whour();
	private DefaultTableModel whourModel;
	private Object[] whourData = null;
	private int selectDocId = 0;
	private String selectDocName = null;
	private JTable tbl_appoint;
	private DefaultTableModel appointmentModel;
	private Object[] appointmentData = null;
	private Appointment appointment = new Appointment();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SickGUI frame = new SickGUI(sick);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public SickGUI(Sick sick) throws SQLException {
		
		doctorModel = new DefaultTableModel();
		Object[] colDoc = new Object[2];
		colDoc[0] = "ID";
		colDoc[1] = "Ad Soyad";
		doctorModel.setColumnIdentifiers(colDoc);
		doctorData = new Object[2];
		
		whourModel = new DefaultTableModel();
		Object[] colWhour = new Object[2];
		colWhour[0] = "ID";
		colWhour[1] = "Tarih";
		whourModel.setColumnIdentifiers(colWhour);
		whourData = new Object[2];
		
		appointmentModel = new DefaultTableModel();
		Object[] colAppoitnment = new Object[3];
		colAppoitnment[0] = "ID";
		colAppoitnment[1] = "Doktor";
		colAppoitnment[2] = "Tarih";
		appointmentModel.setColumnIdentifiers(colAppoitnment);
		appointmentData = new Object[3];
		for(int i = 0; i < appointment.getSickList(sick.getId()).size(); i++) {
			appointmentData[0] = appointment.getSickList(sick.getId()).get(i).getId();
			appointmentData[1] = appointment.getSickList(sick.getId()).get(i).getDoctor_name();
			appointmentData[2] = appointment.getSickList(sick.getId()).get(i).getApp_date();
			appointmentModel.addRow(appointmentData);
		}
		
		setTitle("Hastane Yönetim Sistemi");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		setLocationRelativeTo(null);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lbl_welcome = new JLabel("Hoşgeldiniz, Sayın " + sick.getName());
		lbl_welcome.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lbl_welcome.setBounds(10, 11, 276, 25);
		w_pane.add(lbl_welcome);
		
		JButton btn_logout = new JButton("Çıkış Yap");
		btn_logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI loginGUI = new LoginGUI();
				loginGUI.setVisible(true);
				dispose();
			}
		});
		btn_logout.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		btn_logout.setBounds(586, 11, 138, 25);
		w_pane.add(btn_logout);
		
		JTabbedPane w_tabpane = new JTabbedPane(JTabbedPane.TOP);
		w_tabpane.setBackground(Color.WHITE);
		w_tabpane.setBounds(10, 47, 714, 403);
		w_pane.add(w_tabpane);
		
		JPanel w_appointment = new JPanel();
		w_appointment.setBackground(Color.WHITE);
		w_tabpane.addTab("Randevu Sistemi", null, w_appointment, null);
		w_appointment.setLayout(null);
		
		JLabel lbl_doc_list = new JLabel("Doktor Listesi");
		lbl_doc_list.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lbl_doc_list.setBounds(10, 11, 116, 14);
		w_appointment.add(lbl_doc_list);
		
		JLabel lbl_clinic_name = new JLabel("Poliklinik Adı");
		lbl_clinic_name.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lbl_clinic_name.setBounds(300, 14, 116, 14);
		w_appointment.add(lbl_clinic_name);
		
		JScrollPane scrl_doc_list = new JScrollPane();
		scrl_doc_list.setBounds(10, 40, 281, 324);
		w_appointment.add(scrl_doc_list);
		
		tbl_doc = new JTable(doctorModel);
		scrl_doc_list.setViewportView(tbl_doc);
		
		JComboBox select_clinic = new JComboBox();
		select_clinic.setBounds(300, 39, 150, 30);
		select_clinic.addItem("--Poliklinik seçiniz");
		for(int i = 0; i < clinic.getClinicList().size(); i++) {
			select_clinic.addItem(new Item(clinic.getClinicList().get(i).getId(), clinic.getClinicList().get(i).getName()));
		}
		select_clinic.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(select_clinic.getSelectedIndex() != 0) {
					JComboBox comboBox = (JComboBox) e.getSource();
					Item item = (Item) comboBox.getSelectedItem();
					DefaultTableModel clearModel = (DefaultTableModel) tbl_doc.getModel();
					clearModel.setRowCount(0);
					try {
						for(int i = 0; i < clinic.getClinicDocList(item.getKey()).size(); i++) {
							doctorData[0] = clinic.getClinicDocList(item.getKey()).get(i).getId();
							doctorData[1] = clinic.getClinicDocList(item.getKey()).get(i).getName();
							doctorModel.addRow(doctorData);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					DefaultTableModel clearModel = (DefaultTableModel) tbl_doc.getModel();
					clearModel.setRowCount(0);
				}
			}
		});
		w_appointment.add(select_clinic);
		
		JLabel lbl_select_doc = new JLabel("Doktor Seç");
		lbl_select_doc.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lbl_select_doc.setBounds(300, 130, 150, 20);
		w_appointment.add(lbl_select_doc);
		
		JButton btn_select_doc = new JButton("Seç");
		btn_select_doc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tbl_doc.getSelectedRow();
				if(row >= 0) {
					String value = tbl_doc.getModel().getValueAt(row, 0).toString();
					int id = Integer.parseInt(value);
					DefaultTableModel clearModel = (DefaultTableModel) tbl_whour.getModel();
					clearModel.setRowCount(0);
					try {
						for(int i = 0; i < whour.getWhourList(id).size(); i++) {
							whourData[0] = whour.getWhourList(id).get(i).getId();
							whourData[1] = whour.getWhourList(id).get(i).getWdate();
							whourModel.addRow(whourData);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					tbl_whour.setModel(whourModel);
					selectDocId = id;
					selectDocName = tbl_doc.getModel().getValueAt(row, 1).toString();
				} else {
					Helper.showMsg("Lütfen bir doktor seçiniz!");
				}
			}
		});
		btn_select_doc.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		btn_select_doc.setBackground(Color.LIGHT_GRAY);
		btn_select_doc.setBounds(300, 161, 150, 30);
		w_appointment.add(btn_select_doc);
		
		JLabel lbl_whour_list = new JLabel("Doktor Randevu Saatleri");
		lbl_whour_list.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lbl_whour_list.setBounds(460, 11, 239, 14);
		w_appointment.add(lbl_whour_list);
		
		JScrollPane scrl_whour = new JScrollPane();
		scrl_whour.setBounds(460, 40, 239, 324);
		w_appointment.add(scrl_whour);
		
		tbl_whour = new JTable(whourModel);
		scrl_whour.setViewportView(tbl_whour);
		
		JLabel lbl_appointment = new JLabel("Randevu");
		lbl_appointment.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lbl_appointment.setBounds(300, 260, 150, 20);
		w_appointment.add(lbl_appointment);
		
		JButton btn_add_appoint = new JButton("Randevu Al");
		btn_add_appoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = tbl_whour.getSelectedRow();
				if(selRow >= 0) {
					String date = tbl_whour.getModel().getValueAt(selRow, 1).toString();
					try {
						boolean control = sick.addAppointment(selectDocId, selectDocName, sick.getId(), sick.getName(), date);
						if(control) {
							Helper.showMsg("success");
							sick.updateWhourStatus(selectDocId, date);
							updateWhourModel(selectDocId);
							updateAppointModel(sick.getId());
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					Helper.showMsg("Lütfen geçerli bir tarih giriniz!");
				}
			}
		});
		btn_add_appoint.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		btn_add_appoint.setBackground(Color.LIGHT_GRAY);
		btn_add_appoint.setBounds(300, 291, 150, 30);
		w_appointment.add(btn_add_appoint);
		
		JPanel w_appoint = new JPanel();
		w_appoint.setBackground(Color.WHITE);
		w_tabpane.addTab("Randevularım", null, w_appoint, null);
		w_appoint.setLayout(null);
		
		JScrollPane scrl_appoint = new JScrollPane();
		scrl_appoint.setBounds(10, 11, 689, 353);
		w_appoint.add(scrl_appoint);
		
		tbl_appoint = new JTable(appointmentModel);
		tbl_appoint.setBackground(Color.WHITE);
		scrl_appoint.setViewportView(tbl_appoint);
		tbl_whour.getColumnModel().getColumn(0).setPreferredWidth(5);
	}
	
	public void updateWhourModel(int doctor_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) tbl_whour.getModel();
		clearModel.setRowCount(0);
		for(int i = 0; i < whour.getWhourList(doctor_id).size(); i++) {
			whourData[0] = whour.getWhourList(doctor_id).get(i).getId();
			whourData[1] = whour.getWhourList(doctor_id).get(i).getWdate();
			whourModel.addRow(whourData);
		}
	}
	
	public void updateAppointModel(int sick_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) tbl_appoint.getModel();
		clearModel.setRowCount(0);
		for(int i = 0; i < appointment.getSickList(sick_id).size(); i++) {
			appointmentData[0] = appointment.getSickList(sick_id).get(i).getId();
			appointmentData[1] = appointment.getSickList(sick_id).get(i).getDoctor_name();
			appointmentData[2] = appointment.getSickList(sick_id).get(i).getApp_date();
			appointmentModel.addRow(appointmentData);
		}
	}
}
