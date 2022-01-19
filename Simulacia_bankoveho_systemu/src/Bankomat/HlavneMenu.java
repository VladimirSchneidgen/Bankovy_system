package Bankomat;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import Klienti.Klient;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class HlavneMenu extends Okno implements ActionListener {

	private JFrame frame;
	private Klient klient;
	private JButton btnVyber, btnVklad, btnZostatokNaUcte, btnZmenaPin, btnLimity, btnUkoncit;
	public double zostatok;
	public int id, limit;

	public HlavneMenu(int id) throws SQLException {

		this.id = id;
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 620, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Hlavné menu");
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);

		btnVyber = new JButton("Výber hotovosti");
		btnVyber.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnVyber.setBounds(76, 31, 191, 70);
		frame.getContentPane().add(btnVyber);

		btnVklad = new JButton("Vklad");
		btnVklad.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnVklad.setBounds(328, 31, 191, 70);
		frame.getContentPane().add(btnVklad);

		btnZostatokNaUcte = new JButton("Zostatok na účte");
		btnZostatokNaUcte.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnZostatokNaUcte.setBounds(76, 120, 191, 70);
		frame.getContentPane().add(btnZostatokNaUcte);

		btnZmenaPin = new JButton("Zmena PIN");
		btnZmenaPin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnZmenaPin.setBounds(328, 120, 191, 70);
		frame.getContentPane().add(btnZmenaPin);

		btnLimity = new JButton("Limity");
		btnLimity.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnLimity.setBounds(76, 212, 191, 70);
		frame.getContentPane().add(btnLimity);

		btnUkoncit = new JButton("Ukončiť");
		btnUkoncit.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnUkoncit.setBounds(328, 212, 191, 70);
		frame.getContentPane().add(btnUkoncit);

		JLabel lblObrazokPozadia = new JLabel("");
		lblObrazokPozadia.setBounds(0, 0, 616, 319);
		Image obrazokPozadia = new ImageIcon(this.getClass().getResource(getTmavomodrePozadie())).getImage();
		Image upravenyObrazokPozadia = obrazokPozadia.getScaledInstance(738, 403, java.awt.Image.SCALE_SMOOTH);
		lblObrazokPozadia.setIcon(new ImageIcon(upravenyObrazokPozadia));
		frame.getContentPane().add(lblObrazokPozadia);

		btnVyber.addActionListener(this);
		btnVklad.addActionListener(this);
		btnZostatokNaUcte.addActionListener(this);
		btnZmenaPin.addActionListener(this);
		btnLimity.addActionListener(this);
		btnUkoncit.addActionListener(this);

		
		String dopyt = "SELECT * from Klienti WHERE ID = '" + id + "'";
		Connection spojenie;
		try {
			spojenie = DriverManager.getConnection(getUrl(), getPrihlasovacie_meno(), getHeslo());
			Statement prikaz = spojenie.createStatement();
			ResultSet vysledok = prikaz.executeQuery(dopyt);
			if(vysledok.next()) {
			klient = new Klient(vysledok.getInt("id"), vysledok.getString("meno"), vysledok.getString("priezvisko"), vysledok.getString("cislo_karty"),
					vysledok.getString("heslo"), limit = vysledok.getInt("denny_limit"), zostatok = vysledok.getDouble("zostatok"));
			}
		}
		catch (SQLException | FileNotFoundException e) {
			e.printStackTrace();
		}
}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton tlacidloStlacene = new JButton();
		tlacidloStlacene = (JButton) e.getSource();
		if(btnVyber == tlacidloStlacene) {
			try {
				Vyber vyberHotovosti = new Vyber(zostatok, id, limit);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			frame.dispose();
		}
		if(btnVklad == tlacidloStlacene) {
			Vklad vklad = new Vklad(zostatok, id);
			frame.dispose();
		}
		if(btnZostatokNaUcte == tlacidloStlacene) {
			ZostatokNaUcte zostatokNaUcte = new ZostatokNaUcte(zostatok, id);
			frame.dispose();
		}
		if(btnZmenaPin == tlacidloStlacene) {
			try {
				ZmenaPin zmenaPin = new ZmenaPin(id);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			frame.dispose();
		}
		if(btnLimity == tlacidloStlacene) {
			try {
				DennyLimit dennyLimit = new DennyLimit(limit, id);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			frame.dispose();
		}
		if(btnUkoncit == tlacidloStlacene) {
			try {
				Login login = new Login();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			frame.dispose();
		}
	}
}
