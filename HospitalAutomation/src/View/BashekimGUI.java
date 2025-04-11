package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Helper.Helper;
import Helper.Item;
import Model.Bashekim;
import Model.Clinic;

import java.awt.Window.Type;
import java.sql.SQLException;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.Point;

import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class BashekimGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel w_pane;
	static Bashekim bashekim = new Bashekim();
	private JTextField fld_name;
	private JPasswordField fld_password;
	private JTextField fld_tcnu;
	private JTextField fld_user_id;
	private JTable tbl_doc;
	private DefaultTableModel docModel = null;
	private Object[] docData = null;
	private JTable tbl_clinic;
	private JTextField fld_clinic_name;
	private DefaultTableModel clinicModel = null;
	private Object[] clinicData = null;
	private JPopupMenu clinicMenu;
	Clinic clinic = new Clinic();
	private JTable tbl_worker;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BashekimGUI frame = new BashekimGUI(bashekim);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public BashekimGUI(Bashekim bashekim) throws SQLException {
		
		// Doc Model
		
		docModel = new DefaultTableModel();
		Object[] colDocName = new Object[4];
		colDocName[0] = "ID";
		colDocName[1] = "Ad Soyad";
		colDocName[2] = "T.C Numarası";
		colDocName[3] = "Şifre";
		docModel.setColumnIdentifiers(colDocName);
		docData = new Object[4];
		for(int i = 0; i < bashekim.getDocList().size(); i++) {
			docData[0] = bashekim.getDocList().get(i).getId();
			docData[1] = bashekim.getDocList().get(i).getName();
			docData[2] = bashekim.getDocList().get(i).getTcnu();
			docData[3] = bashekim.getDocList().get(i).getPassword();
			docModel.addRow(docData);
		}
		
		// Clinic Model
		
		clinicModel = new DefaultTableModel();
		Object[] colClinicName = new Object[2];
		colClinicName[0] = "ID";
		colClinicName[1] = "Poliklinik Adı";
		clinicModel.setColumnIdentifiers(colClinicName);
		clinicData = new Object[2];
		for(int i = 0; i < clinic.getClinicList().size(); i++) {
			clinicData[0] = clinic.getClinicList().get(i).getId();
			clinicData[1] = clinic.getClinicList().get(i).getName();
			clinicModel.addRow(clinicData);
		}
		
		// Worker Model
		
		DefaultTableModel workerModel = new DefaultTableModel();
		Object[] colWorkerName = new Object[2];
		colWorkerName[0] = "ID";
		colWorkerName[1] = "Ad Soyad";
		workerModel.setColumnIdentifiers(colWorkerName);
		Object[] workerData = new Object[2];
		
		setTitle("Hastane Yönetim Sistemi");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 600);
		setLocationRelativeTo(null);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lbl_welcome = new JLabel("Hoşgeldiniz, Sayın " + bashekim.getName());
		lbl_welcome.setBounds(10, 11, 276, 25);
		lbl_welcome.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		w_pane.add(lbl_welcome);
		
		JButton btn_logout = new JButton("Çıkış Yap");
		btn_logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI loginGUI = new LoginGUI();
				loginGUI.setVisible(true);
				dispose();
			}
		});
		btn_logout.setBounds(686, 11, 138, 34);
		btn_logout.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		w_pane.add(btn_logout);
		
		JTabbedPane w_tabpane = new JTabbedPane(JTabbedPane.TOP);
		w_tabpane.setBounds(10, 74, 814, 433);
		w_tabpane.setBackground(Color.WHITE);
		w_pane.add(w_tabpane);
		
		JPanel pnl_doctor = new JPanel();
		w_tabpane.addTab("Doktor Yönetimi", null, pnl_doctor, null);
		pnl_doctor.setLayout(null);
		
		JLabel lbl_name = new JLabel("Ad Soyad");
		lbl_name.setBounds(598, 11, 201, 20);
		lbl_name.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		pnl_doctor.add(lbl_name);
		
		fld_name = new JTextField();
		fld_name.setBounds(598, 42, 201, 28);
		pnl_doctor.add(fld_name);
		fld_name.setColumns(10);
		
		JLabel lbl_password = new JLabel("Şifre");
		lbl_password.setBounds(598, 152, 201, 20);
		lbl_password.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		pnl_doctor.add(lbl_password);
		
		fld_password = new JPasswordField();
		fld_password.setBounds(598, 183, 201, 28);
		pnl_doctor.add(fld_password);
		
		JLabel lbl_tcnu = new JLabel("T.C Numarası");
		lbl_tcnu.setBounds(598, 81, 201, 20);
		lbl_tcnu.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		pnl_doctor.add(lbl_tcnu);
		
		fld_tcnu = new JTextField();
		fld_tcnu.setBounds(598, 112, 201, 28);
		pnl_doctor.add(fld_tcnu);
		fld_tcnu.setColumns(10);
		
		JButton btn_add = new JButton("Ekle");
		btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_name.getText().length() == 0 || fld_tcnu.getText().length() == 0 || fld_password.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					try {
						boolean control = bashekim.addDoc(fld_tcnu.getText(), fld_password.getText(), fld_name.getText());
						if(control) {
							Helper.showMsg("success");
							fld_name.setText(null);
							fld_tcnu.setText(null);
							fld_password.setText(null);
							updateDocTable();
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_add.setBackground(Color.LIGHT_GRAY);
		btn_add.setBounds(598, 222, 201, 38);
		btn_add.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		pnl_doctor.add(btn_add);
		
		JLabel lbl_user_id = new JLabel("Kullanıcı ID");
		lbl_user_id.setBounds(598, 271, 201, 20);
		lbl_user_id.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		pnl_doctor.add(lbl_user_id);
		
		fld_user_id = new JTextField();
		fld_user_id.setBounds(598, 302, 201, 28);
		pnl_doctor.add(fld_user_id);
		fld_user_id.setColumns(10);
		
		JButton btn_delete = new JButton("Sil");
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_user_id.getText().length() == 0) {
					Helper.showMsg("Lütfen bir doktor seçiniz!");
				} else {
					if(Helper.confirm("sure")) {
						int selectId = Integer.parseInt(fld_user_id.getText());
						try {
							boolean control = bashekim.deleteDoc(selectId);
							if(control) {
								Helper.showMsg("success");
								fld_user_id.setText(null);
								updateDocTable();
							}
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}
			}
		});
		btn_delete.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		btn_delete.setBounds(598, 341, 201, 38);
		pnl_doctor.add(btn_delete);
		
		JScrollPane w_scrl_doc = new JScrollPane();
		w_scrl_doc.setBounds(10, 11, 578, 368);
		pnl_doctor.add(w_scrl_doc);
		
		tbl_doc = new JTable(docModel);
		w_scrl_doc.setViewportView(tbl_doc);
		
		JPanel pnl_clinic = new JPanel();
		pnl_clinic.setBackground(Color.WHITE);
		w_tabpane.addTab("Poliklinikler", null, pnl_clinic, null);
		pnl_clinic.setLayout(null);
		
		JScrollPane scrl_clinic = new JScrollPane();
		scrl_clinic.setBounds(10, 11, 279, 383);
		pnl_clinic.add(scrl_clinic);
		
		clinicMenu = new JPopupMenu();
		JMenuItem updateMenu = new JMenuItem("Güncelle");
		JMenuItem deleteMenu = new JMenuItem("Sil");
		clinicMenu.add(updateMenu);
		clinicMenu.add(deleteMenu);
		
		updateMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedId = Integer.parseInt(tbl_clinic.getValueAt(tbl_clinic.getSelectedRow(), 0).toString());
				Clinic selectedClinic = clinic.getFetch(selectedId);
				UpdateClinicGUI updateClinicGUI = new UpdateClinicGUI(selectedClinic);
				updateClinicGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				updateClinicGUI.setVisible(true);
				updateClinicGUI.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						try {
							updateClinicModel();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						super.windowClosed(e);
					}
				});
			}
		});
		
		deleteMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Helper.confirm("sure")) {
					int selectedId = Integer.parseInt(tbl_clinic.getValueAt(tbl_clinic.getSelectedRow(), 0).toString());
					if(clinic.deleteClinic(selectedId)) {
						Helper.showMsg("success");
						try {
							updateClinicModel();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					} else {
						Helper.showMsg("error");
					}
				}
				
				
			}
		});
		
		tbl_clinic = new JTable(clinicModel);
		tbl_clinic.setComponentPopupMenu(clinicMenu);
		tbl_clinic.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point point = e.getPoint();
				int selectedRow = tbl_clinic.rowAtPoint(point);
				tbl_clinic.setRowSelectionInterval(selectedRow, selectedRow);
			}
		});
		scrl_clinic.setViewportView(tbl_clinic);
		
		JLabel lbl_clinic = new JLabel("Poliklinik Adı");
		lbl_clinic.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lbl_clinic.setBounds(299, 11, 202, 20);
		pnl_clinic.add(lbl_clinic);
		
		fld_clinic_name = new JTextField();
		fld_clinic_name.setColumns(10);
		fld_clinic_name.setBounds(299, 42, 202, 28);
		pnl_clinic.add(fld_clinic_name);
		
		JScrollPane scrl_worker_name = new JScrollPane();
		scrl_worker_name.setBounds(511, 11, 288, 383);
		pnl_clinic.add(scrl_worker_name);
		
		tbl_worker = new JTable();
		scrl_worker_name.setViewportView(tbl_worker);
		
		JButton btn_clinic_add = new JButton("Ekle");
		btn_clinic_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_clinic_name.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					try {
						if(clinic.addClinic(fld_clinic_name.getText())) {
							Helper.showMsg("success");
							fld_clinic_name.setText(null);
							updateClinicModel();
						}
					} catch (Exception e3) {
						e3.printStackTrace();
					}
				}
			}
		});
		btn_clinic_add.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		btn_clinic_add.setBackground(Color.LIGHT_GRAY);
		btn_clinic_add.setBounds(299, 81, 201, 38);
		pnl_clinic.add(btn_clinic_add);
		
		JComboBox select_doc = new JComboBox();
		select_doc.setBounds(299, 307, 202, 38);
		for(int i = 0; i < bashekim.getDocList().size(); i++) {
			select_doc.addItem(new Item(bashekim.getDocList().get(i).getId(), bashekim.getDocList().get(i).getName()));
		}
		select_doc.addActionListener(e -> {
			JComboBox c = (JComboBox) e.getSource();
			Item item = (Item) c.getSelectedItem();
			System.out.println(item.getKey() + ": " + item.getValue());
		});
		pnl_clinic.add(select_doc);
		
		JButton btn_add_worker = new JButton("Ekle");
		btn_add_worker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tbl_clinic.getSelectedRow();
				if(selectedRow >= 0) {
					String selectedClinic = tbl_clinic.getModel().getValueAt(selectedRow, 0).toString();
					int selectedClinicId = Integer.parseInt(selectedClinic);
					Item docItem = (Item) select_doc.getSelectedItem();
					try {
						boolean control = bashekim.addWorker(docItem.getKey(), selectedClinicId);
						if(control) {
							Helper.showMsg("success");
							DefaultTableModel clearModel = (DefaultTableModel) tbl_worker.getModel();
							clearModel.setRowCount(0);
							for(int i = 0; i < bashekim.getClinicDocList(selectedClinicId).size(); i++) {
								workerData[0] = bashekim.getClinicDocList(selectedClinicId).get(i).getId();
								workerData[1] = bashekim.getClinicDocList(selectedClinicId).get(i).getName();
								workerModel.addRow(workerData);
							}
							tbl_worker.setModel(workerModel);
						} else {
							Helper.showMsg("error");
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
				} else {
					Helper.showMsg("Lütfen bir poliklinik seçiniz!");
				}
			}
		});
		btn_add_worker.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		btn_add_worker.setBackground(Color.LIGHT_GRAY);
		btn_add_worker.setBounds(300, 356, 201, 38);
		pnl_clinic.add(btn_add_worker);
		
		JLabel lbl_worker = new JLabel("Poliklinik Adı");
		lbl_worker.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lbl_worker.setBounds(299, 181, 202, 20);
		pnl_clinic.add(lbl_worker);
		
		JButton btn_select_worker = new JButton("Seç");
		btn_select_worker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tbl_clinic.getSelectedRow();
				if(selectedRow >= 0) {
					String selectedClinic = tbl_clinic.getModel().getValueAt(selectedRow, 0).toString();
					int selectedClinicId = Integer.parseInt(selectedClinic);
					DefaultTableModel clearModel = (DefaultTableModel) tbl_worker.getModel();
					clearModel.setRowCount(0);
					try {
						for(int i = 0; i < bashekim.getClinicDocList(selectedClinicId).size(); i++) {
							workerData[0] = bashekim.getClinicDocList(selectedClinicId).get(i).getId();
							workerData[1] = bashekim.getClinicDocList(selectedClinicId).get(i).getName();
							workerModel.addRow(workerData);
						}
					} catch (Exception e2) {
						// TODO: handle exception
					}
					tbl_worker.setModel(workerModel);
				} else {
					Helper.showMsg("Lütfen bir poliklinik seçiniz!");
				}
			}
		});
		btn_select_worker.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		btn_select_worker.setBackground(Color.LIGHT_GRAY);
		btn_select_worker.setBounds(299, 212, 201, 38);
		pnl_clinic.add(btn_select_worker);
		
		tbl_doc.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					fld_user_id.setText(tbl_doc.getValueAt(tbl_doc.getSelectedRow(), 0).toString());
				} catch (Exception e2) {
	
				}
			}
		});
		
		tbl_doc.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				if(e.getType() == TableModelEvent.UPDATE) {
					int selectId = Integer.parseInt(tbl_doc.getValueAt(tbl_doc.getSelectedRow(), 0).toString());
					String selectName = tbl_doc.getValueAt(tbl_doc.getSelectedRow(), 1).toString();
					String selectTcNu = tbl_doc.getValueAt(tbl_doc.getSelectedRow(), 2).toString();
					String selectPassword = tbl_doc.getValueAt(tbl_doc.getSelectedRow(), 3).toString();
					try {
						boolean control = bashekim.updateDoc(selectId, selectTcNu, selectPassword, selectName);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
				}
			}
		});
	}
	
	public void updateDocTable() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) tbl_doc.getModel();
		clearModel.setRowCount(0);
		for(int i = 0; i < bashekim.getDocList().size(); i++) {
			docData[0] = bashekim.getDocList().get(i).getId();
			docData[1] = bashekim.getDocList().get(i).getName();
			docData[2] = bashekim.getDocList().get(i).getTcnu();
			docData[3] = bashekim.getDocList().get(i).getPassword();
			docModel.addRow(docData);
		}
	}
	
	public void updateClinicModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) tbl_clinic.getModel();
		clearModel.setRowCount(0);
		for(int i = 0; i < clinic.getClinicList().size(); i++) {
			clinicData[0] = clinic.getClinicList().get(i).getId();
			clinicData[1] = clinic.getClinicList().get(i).getName();
			clinicModel.addRow(clinicData);
		}
	}
}
