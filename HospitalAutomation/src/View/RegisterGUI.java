package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Sick;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class RegisterGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel w_pane;
	private JTextField fld_name;
	private JTextField fld_tcnu;
	private JPasswordField fld_password;
	private Sick sick = new Sick();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
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
	public RegisterGUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 350);
		setLocationRelativeTo(null);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lbl_name = new JLabel("Ad Soyad");
		lbl_name.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lbl_name.setBounds(10, 11, 201, 20);
		w_pane.add(lbl_name);
		
		fld_name = new JTextField();
		fld_name.setColumns(10);
		fld_name.setBounds(10, 42, 264, 28);
		w_pane.add(fld_name);
		
		JLabel lbl_tcnu = new JLabel("T.C Numarası");
		lbl_tcnu.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lbl_tcnu.setBounds(10, 81, 201, 20);
		w_pane.add(lbl_tcnu);
		
		fld_tcnu = new JTextField();
		fld_tcnu.setColumns(10);
		fld_tcnu.setBounds(10, 112, 264, 28);
		w_pane.add(fld_tcnu);
		
		JLabel lbl_password = new JLabel("Şifre");
		lbl_password.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lbl_password.setBounds(10, 152, 201, 20);
		w_pane.add(lbl_password);
		
		fld_password = new JPasswordField();
		fld_password.setBounds(10, 183, 264, 28);
		w_pane.add(fld_password);
		
		JButton btn_register = new JButton("Kayıt Ol");
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_tcnu.getText().length() == 0 || fld_password.getText().length() == 0 || fld_name.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					try {
						boolean control = sick.register(fld_tcnu.getText(), fld_password.getText(), fld_name.getText());
						if(control) {
							Helper.showMsg("success");
							LoginGUI loginGUI = new LoginGUI();
							loginGUI.setVisible(true);
							dispose();
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_register.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		btn_register.setBackground(Color.LIGHT_GRAY);
		btn_register.setBounds(10, 222, 264, 28);
		w_pane.add(btn_register);
		
		JButton btn_back = new JButton("Geri Dön");
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI loginGUI = new LoginGUI();
				loginGUI.setVisible(true);
				dispose();
			}
		});
		btn_back.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		btn_back.setBackground(Color.LIGHT_GRAY);
		btn_back.setBounds(10, 263, 264, 28);
		w_pane.add(btn_back);
	}
}
