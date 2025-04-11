package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Clinic;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UpdateClinicGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel w_pane;
	private JTextField fld_clinic_name;
	private static Clinic clinic;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateClinicGUI frame = new UpdateClinicGUI(clinic);
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
	public UpdateClinicGUI(Clinic clinic) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 225, 150);
		setLocationRelativeTo(null);
		w_pane = new JPanel();
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lbl_clinic_name = new JLabel("Poliklinik Adı");
		lbl_clinic_name.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lbl_clinic_name.setBounds(10, 0, 189, 20);
		w_pane.add(lbl_clinic_name);
		
		fld_clinic_name = new JTextField();
		fld_clinic_name.setColumns(10);
		fld_clinic_name.setBounds(10, 31, 189, 28);
		fld_clinic_name.setText(clinic.getName());
		w_pane.add(fld_clinic_name);
		
		JButton btn_update_clinic = new JButton("Düzenle");
		btn_update_clinic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Helper.confirm("sure")) {
					try {
						clinic.updateClinic(clinic.getId(), fld_clinic_name.getText());
						Helper.showMsg("success");
						dispose();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
				}
			}
		});
		btn_update_clinic.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		btn_update_clinic.setBackground(Color.LIGHT_GRAY);
		btn_update_clinic.setBounds(10, 70, 189, 30);
		w_pane.add(btn_update_clinic);
	}
}
