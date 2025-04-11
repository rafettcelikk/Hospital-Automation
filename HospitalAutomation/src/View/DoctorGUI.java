package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Model.Doctor;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import com.toedter.calendar.JDateChooser;

import Helper.Helper;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

public class DoctorGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel w_pane;
	private static Doctor doctor = new Doctor();
	private JTable tbl_whour;
	private DefaultTableModel whourModel;
	private Object[] whourData = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorGUI frame = new DoctorGUI(doctor);
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
	public DoctorGUI(Doctor doctor) throws SQLException {
		
		whourModel = new DefaultTableModel();
		Object[] colWhour = new Object[2];
		colWhour[0] = "ID";
		colWhour[1] = "Tarih";
		whourModel.setColumnIdentifiers(colWhour);
		whourData = new Object[2];
		for(int i = 0; i < doctor.getWhourList(doctor.getId()).size(); i++) {
			whourData[0] = doctor.getWhourList(doctor.getId()).get(i).getId();
			whourData[1] = doctor.getWhourList(doctor.getId()).get(i).getWdate();
			whourModel.addRow(whourData);
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
		
		JLabel lbl_welcome = new JLabel("Hoşgeldiniz, Sayın " + doctor.getName());
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
		btn_logout.setBounds(586, 11, 138, 34);
		w_pane.add(btn_logout);
		
		JTabbedPane w_tabpane = new JTabbedPane(JTabbedPane.TOP);
		w_tabpane.setBackground(Color.WHITE);
		w_tabpane.setBounds(10, 68, 714, 382);
		w_pane.add(w_tabpane);
		
		JPanel w_whour = new JPanel();
		w_whour.setBackground(Color.WHITE);
		w_tabpane.addTab("Çalışma Saatleri", null, w_whour, null);
		w_whour.setLayout(null);
		
		JDateChooser select_date = new JDateChooser();
		select_date.setBounds(10, 11, 113, 20);
		w_whour.add(select_date);
		
		JComboBox select_hour = new JComboBox();
		select_hour.setModel(new DefaultComboBoxModel(new String[] {"10:00", "10:30", "11:00", "11:30", "12:00", "13:30", "14:00", "14:30", "15:00"}));
		select_hour.setBounds(133, 11, 75, 22);
		w_whour.add(select_hour);
		
		JButton btn_add_whour = new JButton("Ekle");
		btn_add_whour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = "";
				try {
					date = sdf.format(select_date.getDate());
				} catch (Exception e2) {
					// TODO: handle exception
				}
				if(date.length() == 0) {
					Helper.showMsg("Lütfen geçerli bir tarih giriniz!");
				} else {
					String time = " " + select_hour.getSelectedItem().toString() + ":00";
					String selectDate = date + time;
					try {
						boolean control = doctor.addWhour(doctor.getId(), doctor.getName(), selectDate);
						if(control) {
							Helper.showMsg("success");
							updateWhourModel(doctor);
						} else {
							Helper.showMsg("error");
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		});
		btn_add_whour.setBounds(218, 11, 77, 23);
		w_whour.add(btn_add_whour);
		
		JScrollPane scrl_whour = new JScrollPane();
		scrl_whour.setBounds(0, 42, 709, 312);
		w_whour.add(scrl_whour);
		
		tbl_whour = new JTable(whourModel);
		tbl_whour.setBackground(Color.WHITE);
		scrl_whour.setViewportView(tbl_whour);
		
		JButton btn_delete_whour = new JButton("Sil");
		btn_delete_whour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectRow = tbl_whour.getSelectedRow();
				if(selectRow >= 0) {
					String selectedRow = tbl_whour.getModel().getValueAt(selectRow, 0).toString();
					int selectedId = Integer.parseInt(selectedRow);
					boolean control;
					try {
						control = doctor.deleteWhour(selectedId);
						if(control) {
							Helper.showMsg("success");
							updateWhourModel(doctor);
						} else {
							Helper.showMsg("error");
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				} else {
					Helper.showMsg("Lütfen bir tarih seçiniz!");
				}
			}
		});
		btn_delete_whour.setBounds(622, 11, 77, 23);
		w_whour.add(btn_delete_whour);
	}
	
	public void updateWhourModel(Doctor doctor) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) tbl_whour.getModel();
		clearModel.setRowCount(0);
		for(int i = 0; i < doctor.getWhourList(doctor.getId()).size(); i++) {
			whourData[0] = doctor.getWhourList(doctor.getId()).get(i).getId();
			whourData[1] = doctor.getWhourList(doctor.getId()).get(i).getWdate();
			whourModel.addRow(whourData);
		}
	}
}
