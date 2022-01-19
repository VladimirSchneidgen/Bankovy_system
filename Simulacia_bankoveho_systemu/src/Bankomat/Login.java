package Bankomat;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.FileNotFoundException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Klienti.Klient;

import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.JTextArea;
import javax.swing.JSeparator;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Login extends Okno implements ActionListener{

	public JFrame frame;
	public JTextField txtCisloKarty;
	public JPasswordField vlozene_heslo;
	public JButton btnPrihlasit, btnRegistracia;
	public JLabel lblObrazokPozadia, lblCisloKarty, lblHeslo;
	private Klient klient;
	public double zostatok;
	public int id, limit;

	public Login() throws SQLException {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.control);
		frame.setBounds(100, 100, 752, 440);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Login");
		frame.setLocationRelativeTo(null); // Takto sa centruje JFrame na stred

		lblCisloKarty = new JLabel("Číslo účtu");
		lblCisloKarty.setForeground(Color.WHITE);
		lblCisloKarty.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCisloKarty.setBounds(101, 124, 110, 28);
		frame.getContentPane().add(lblCisloKarty);

		lblHeslo = new JLabel("Heslo");
		lblHeslo.setForeground(Color.WHITE);
		lblHeslo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblHeslo.setBounds(101, 177, 110, 28);
		frame.getContentPane().add(lblHeslo);

		vlozene_heslo = new JPasswordField();
		vlozene_heslo.setToolTipText("Sem zadajte heslo");
		vlozene_heslo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		vlozene_heslo.setBounds(211, 177, 271, 26);
		frame.getContentPane().add(vlozene_heslo);

		txtCisloKarty = new JTextField();
		txtCisloKarty.setBackground(SystemColor.window);
		txtCisloKarty.setToolTipText("Sem zadajte číslo karty");
		txtCisloKarty.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtCisloKarty.setBounds(211, 124, 409, 28);
		frame.getContentPane().add(txtCisloKarty);
		txtCisloKarty.setColumns(10);

		btnPrihlasit = new JButton("Prihlásiť");
		btnPrihlasit.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnPrihlasit.setBounds(211, 254, 116, 33);
		frame.getContentPane().add(btnPrihlasit);

		btnRegistracia = new JButton("Registrácia");
		btnRegistracia.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnRegistracia.setBounds(347, 253, 135, 34);
		frame.getContentPane().add(btnRegistracia);

		lblObrazokPozadia = new JLabel("");
		lblObrazokPozadia.setBounds(0, 0, 738, 403);
		Image obrazokPozadia = new ImageIcon(this.getClass().getResource(getTmavomodrePozadie())).getImage();
		Image upravenyObrazokPozadia = obrazokPozadia.getScaledInstance(738, 403, java.awt.Image.SCALE_SMOOTH);
		lblObrazokPozadia.setIcon(new ImageIcon(upravenyObrazokPozadia));
		frame.getContentPane().add(lblObrazokPozadia);

		btnPrihlasit.addActionListener(this);
		btnRegistracia.addActionListener(this);

		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton kliknutyButton = new JButton();
		kliknutyButton = (JButton) e.getSource();
		if(btnPrihlasit == kliknutyButton) {
			try {
				if(!txtCisloKarty.getText().isBlank()) {
					String cislo_uctu = txtCisloKarty.getText(); 
					String heslo = new String(vlozene_heslo.getPassword()); //hesloPin.getText(); --> takto to nefunguje pri heslach.
					cislo_uctu = zasifruj_cislo_karty(cislo_uctu);
					heslo = zasifruj_heslo(heslo);

					String dopyt = "SELECT * from Klienti where cislo_karty ='" + cislo_uctu + "'AND heslo = '" + heslo + "'";
				
						try {
							Connection spojenie = DriverManager.getConnection(getUrl(), getPrihlasovacie_meno(), getHeslo());
							Statement prikaz = spojenie.createStatement();
							ResultSet vysledok = prikaz.executeQuery(dopyt);
							if(vysledok.next()) {
								klient = new Klient(vysledok.getInt("id"), vysledok.getString("meno"), vysledok.getString("priezvisko"), vysledok.getString("cislo_karty"), 
										vysledok.getString("heslo"), vysledok.getInt("denny_limit"), vysledok.getDouble("zostatok"));
										HlavneMenu hlavneMenu = new HlavneMenu(vysledok.getInt("id"));
										frame.dispose();
							} 
							else {
								JOptionPane.showMessageDialog(null, "Nesprávne číslo karty alebo heslo!", "Chyba", JOptionPane.WARNING_MESSAGE);
								txtCisloKarty.setText(""); 
								vlozene_heslo.setText("");
							}
						}
						 catch (HeadlessException | SQLException | FileNotFoundException e1) {
							e1.printStackTrace();
						}
				}
			
				else { // ak pouzivatel nezada cislo karty ani heslo
					JOptionPane.showMessageDialog(null, "Chýbajúce údaje!", "Chyba", JOptionPane.WARNING_MESSAGE);
					txtCisloKarty.setText(""); 
					vlozene_heslo.setText("");
				}
			} catch (HeadlessException | FileNotFoundException e1) {
				e1.printStackTrace();
			} 
		}
		
		if(btnRegistracia == kliknutyButton) {
			Registracia registracia = new Registracia();
			frame.dispose();
		}
	}
}
