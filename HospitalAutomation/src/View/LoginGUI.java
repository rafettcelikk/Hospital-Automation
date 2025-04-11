package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.DBConnection;
import Helper.Helper;
import Model.Bashekim;
import Model.Doctor;
import Model.Sick;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel w_pane;
	private JTextField fld_sick_tcnu;
	private JTextField fld_doc_tcnu;
	private JPasswordField fld_sick_password;
	private JPasswordField fld_doc_password;
	private DBConnection connection = new DBConnection();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		setResizable(false);
		setTitle("Hastane Otomasyon");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		setLocationRelativeTo(null);
		
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		ImageIcon logo = new ImageIcon(getClass().getResource("hospital.png"));
		Image reSizeLogo = logo.getImage().getScaledInstance(100, 55, Image.SCALE_SMOOTH);
		JLabel lbl_logo = new JLabel(new ImageIcon(reSizeLogo));
		lbl_logo.setBounds(183, 21, 100, 55);
		w_pane.add(lbl_logo);
		
		JLabel lblNewLabel = new JLabel("Hastane Yönetim Sistemine Hoşgelgeldiniz");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lblNewLabel.setBounds(65, 87, 337, 24);
		w_pane.add(lblNewLabel);
		
		JTabbedPane w_tabpane = new JTabbedPane(JTabbedPane.TOP);
		w_tabpane.setBackground(Color.WHITE);
		w_tabpane.setBounds(10, 122, 464, 228);
		w_pane.add(w_tabpane);
		
		JPanel w_pnl_sick = new JPanel();
		w_tabpane.addTab("Hasta Girişi", null, w_pnl_sick, null);
		w_pnl_sick.setLayout(null);
		
		JLabel lbl_sick_tcnu = new JLabel("T.C Numarası:");
		lbl_sick_tcnu.setBounds(42, 11, 126, 36);
		lbl_sick_tcnu.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		w_pnl_sick.add(lbl_sick_tcnu);
		
		JLabel lbl_sick_password = new JLabel("Şifre:");
		lbl_sick_password.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lbl_sick_password.setBounds(42, 58, 61, 38);
		w_pnl_sick.add(lbl_sick_password);
		
		fld_sick_tcnu = new JTextField();
		fld_sick_tcnu.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 18));
		fld_sick_tcnu.setBounds(163, 11, 258, 36);
		w_pnl_sick.add(fld_sick_tcnu);
		fld_sick_tcnu.setColumns(10);
		
		JButton btn_sick_login = new JButton("Giriş Yap");
		btn_sick_login.setBackground(Color.LIGHT_GRAY);
		btn_sick_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_sick_tcnu.getText().length() == 0 || fld_sick_password.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					boolean key = true;
					try {
						Connection connect = connection.connectDB();
						Statement st = connect.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM user");
						while(rs.next()) {
							if(fld_sick_tcnu.getText().equals(rs.getString("tcnu")) && fld_sick_password.getText().equals(rs.getString("password"))) {
								if(rs.getString("type").equals("hasta")) {
									Sick sick = new Sick();
									sick.setId(rs.getInt("id"));
									sick.setTcnu(rs.getString("tcnu"));
									sick.setPassword(rs.getString("password"));
									sick.setName(rs.getString("name"));
									sick.setType(rs.getString("type"));
									SickGUI sickGUI = new SickGUI(sick);
									sickGUI.setVisible(true);
									dispose();
									key = false;
								}
							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					if(key) {
						Helper.showMsg("Böyle bir hasta bulunamadı. Lütfen kayıt olunuz!");
					}
				}
			}
		});
		btn_sick_login.setFont(new Font("Yu Gothic", Font.BOLD, 24));
		btn_sick_login.setBounds(42, 126, 165, 47);
		w_pnl_sick.add(btn_sick_login);
		
		JButton btn_sick_register = new JButton("Kaydol");
		btn_sick_register.setBackground(Color.LIGHT_GRAY);
		btn_sick_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI registerGUI = new RegisterGUI();
				registerGUI.setVisible(true);
				dispose();
			}
		});
		btn_sick_register.setFont(new Font("Yu Gothic", Font.BOLD, 24));
		btn_sick_register.setBounds(256, 126, 165, 47);
		w_pnl_sick.add(btn_sick_register);
		
		fld_sick_password = new JPasswordField();
		fld_sick_password.setBounds(163, 58, 258, 32);
		w_pnl_sick.add(fld_sick_password);
		
		JPanel w_pnl_doctor = new JPanel();
		w_tabpane.addTab("Doktor Girişi", null, w_pnl_doctor, null);
		w_pnl_doctor.setLayout(null);
		
		JLabel lbl_doc_tcnu = new JLabel("T.C Numarası:");
		lbl_doc_tcnu.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lbl_doc_tcnu.setBounds(40, 11, 126, 36);
		w_pnl_doctor.add(lbl_doc_tcnu);
		
		fld_doc_tcnu = new JTextField();
		fld_doc_tcnu.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 18));
		fld_doc_tcnu.setColumns(10);
		fld_doc_tcnu.setBounds(161, 11, 258, 36);
		w_pnl_doctor.add(fld_doc_tcnu);
		
		JLabel lbl_doc_password = new JLabel("Şifre:");
		lbl_doc_password.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lbl_doc_password.setBounds(40, 58, 61, 38);
		w_pnl_doctor.add(lbl_doc_password);
		
		JButton btn_doc_login = new JButton("Giriş Yap");
		btn_doc_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_doc_tcnu.getText().length() == 0 || fld_doc_password.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					try {
						Connection connect = connection.connectDB();
						Statement st = connect.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM user");
						while(rs.next()) {
							if(fld_doc_tcnu.getText().equals(rs.getString("tcnu")) && fld_doc_password.getText().equals(rs.getString("password"))) {
								if(rs.getString("type").equals("bashekim")) {
									Bashekim bashekim = new Bashekim();
									bashekim.setId(rs.getInt("id"));
									bashekim.setTcnu(rs.getString("tcnu"));
									bashekim.setPassword(rs.getString("password"));
									bashekim.setName(rs.getString("name"));
									bashekim.setType(rs.getString("type"));
									BashekimGUI bashekimGUI = new BashekimGUI(bashekim);
									bashekimGUI.setVisible(true);
									dispose();
								}
								if(rs.getString("type").equals("doktor")) {
									Doctor doctor = new Doctor();
									doctor.setId(rs.getInt("id"));
									doctor.setTcnu(rs.getString("tcnu"));
									doctor.setPassword(rs.getString("password"));
									doctor.setName(rs.getString("name"));
									doctor.setType(rs.getString("type"));
									DoctorGUI doctorGUI = new DoctorGUI(doctor);
									doctorGUI.setVisible(true);
									dispose();
								}
							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_doc_login.setFont(new Font("Yu Gothic", Font.BOLD, 24));
		btn_doc_login.setBackground(Color.LIGHT_GRAY);
		btn_doc_login.setBounds(40, 126, 379, 47);
		w_pnl_doctor.add(btn_doc_login);
		
		fld_doc_password = new JPasswordField();
		fld_doc_password.setBounds(161, 64, 258, 32);
		w_pnl_doctor.add(fld_doc_password);
		
	}
}
