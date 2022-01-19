package Bankomat;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.awt.Color;

public class Registracia extends Okno implements ActionListener{

	public JFrame frame;
	public JLabel lblObrazokPozadia, lblMeno, lblPriezvisko, lblTelefon, lblPociatocnyVklad, lblLimitVyberu, lblPohlavie;
	public JButton btnVynuluj, btnPridat, btnSpat;
	public JRadioButton rdbtnMuz, rdbtnZena;
	public ButtonGroup buttonGroup;
	public JTextPane txtPociatocnyVklad, txtMeno, txtPriezvisko, txtLimitVyberu, txtTelefon;
	public String meno, priezvisko, heslo, cislo_uctu;
	public int id, zostatok, dennyLimit;
	public Random random = new Random();
	public ArrayList <String> zoznamId = new <String> ArrayList(); 
	public ArrayList <String> zoznamCisielUctu = new <String> ArrayList();
	
	public Registracia() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 845, 280);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Vklad");
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);

		lblMeno = new JLabel("Meno");
		lblMeno.setForeground(Color.WHITE);
		lblMeno.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMeno.setBounds(42, 33, 93, 25);
		frame.getContentPane().add(lblMeno);

		lblPriezvisko = new JLabel("Priezvisko");
		lblPriezvisko.setForeground(Color.WHITE);
		lblPriezvisko.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPriezvisko.setBounds(42, 83, 93, 25);
		frame.getContentPane().add(lblPriezvisko);

		lblTelefon = new JLabel("Telefón");
		lblTelefon.setForeground(Color.WHITE);
		lblTelefon.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTelefon.setBounds(42, 133, 93, 25);
		frame.getContentPane().add(lblTelefon);

		lblPociatocnyVklad = new JLabel("Počiatočný vklad (EUR)");
		lblPociatocnyVklad.setForeground(Color.WHITE);
		lblPociatocnyVklad.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPociatocnyVklad.setBounds(385, 30, 215, 25);
		frame.getContentPane().add(lblPociatocnyVklad);

		lblLimitVyberu = new JLabel("Limit pre výber (EUR)");
		lblLimitVyberu.setForeground(Color.WHITE);
		lblLimitVyberu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblLimitVyberu.setBounds(385, 80, 191, 25);
		frame.getContentPane().add(lblLimitVyberu);

		lblPohlavie = new JLabel("Pohlavie");
		lblPohlavie.setForeground(Color.WHITE);
		lblPohlavie.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPohlavie.setBounds(42, 183, 93, 25);
		frame.getContentPane().add(lblPohlavie);

		txtPociatocnyVklad = new JTextPane();
		txtPociatocnyVklad.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtPociatocnyVklad.setBounds(615, 27, 177, 31);
		frame.getContentPane().add(txtPociatocnyVklad);

		txtMeno = new JTextPane();
		txtMeno.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtMeno.setBounds(167, 27, 177, 31);
		frame.getContentPane().add(txtMeno);

		txtPriezvisko = new JTextPane();
		txtPriezvisko.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtPriezvisko.setBounds(167, 77, 177, 31);
		frame.getContentPane().add(txtPriezvisko);

		txtTelefon = new JTextPane();
		txtTelefon.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtTelefon.setBounds(167, 127, 177, 31);
		frame.getContentPane().add(txtTelefon);

		txtLimitVyberu = new JTextPane();
		txtLimitVyberu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtLimitVyberu.setBounds(615, 77, 177, 31);
		frame.getContentPane().add(txtLimitVyberu);

		rdbtnMuz = new JRadioButton("Muž");
		rdbtnMuz.setForeground(Color.BLACK);
		rdbtnMuz.setFont(new Font("Tahoma", Font.PLAIN, 19));
		rdbtnMuz.setBounds(166, 180, 88, 28);
		frame.getContentPane().add(rdbtnMuz);

		rdbtnZena = new JRadioButton("Žena");
		rdbtnZena.setForeground(Color.BLACK);
		rdbtnZena.setFont(new Font("Tahoma", Font.PLAIN, 19));
		rdbtnZena.setBounds(256, 180, 88, 28);
		frame.getContentPane().add(rdbtnZena);

		buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnMuz);
		buttonGroup.add(rdbtnZena);

		btnVynuluj = new JButton("Vynuluj");
		btnVynuluj.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnVynuluj.setBounds(519, 171, 131, 37);
		frame.getContentPane().add(btnVynuluj);

		btnSpat = new JButton("Späť");
		btnSpat.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSpat.setBounds(378, 171, 131, 37);
		frame.getContentPane().add(btnSpat);

		btnPridat = new JButton("Pridať");
		btnPridat.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnPridat.setBounds(660, 171, 131, 37);
		frame.getContentPane().add(btnPridat);

		lblObrazokPozadia = new JLabel();
		lblObrazokPozadia.setBounds(0, -18, 837, 267);
		Image obrazokPozadia = new ImageIcon(this.getClass().getResource(getTmavomodrePozadie())).getImage();
		Image upravenyObrazokPozadia = obrazokPozadia.getScaledInstance(1100, 908, java.awt.Image.SCALE_SMOOTH);
		lblObrazokPozadia.setIcon(new ImageIcon(upravenyObrazokPozadia));
		frame.getContentPane().add(lblObrazokPozadia);

		btnVynuluj.addActionListener(this);
		btnSpat.addActionListener(this);
		btnPridat.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton kliknutyButton = new JButton();
		kliknutyButton = (JButton) e.getSource();
		if(btnVynuluj == kliknutyButton) {
			txtMeno.setText("");
			txtPriezvisko.setText("");
			txtTelefon.setText("");
			txtPociatocnyVklad.setText("");
			txtLimitVyberu.setText("");
			buttonGroup.clearSelection();
		}

		if(btnSpat == kliknutyButton) {
			try {
				Login login = new Login();
			}catch (SQLException e1) {
				e1.printStackTrace();
			}
			frame.dispose();
		}

		if(btnPridat == kliknutyButton) {
			if(txtMeno.getText().isBlank() || txtPriezvisko.getText().isBlank() || txtLimitVyberu.getText().isBlank() || txtPociatocnyVklad.getText().isBlank()) {	
				JOptionPane.showMessageDialog(null, "Chýbajúce Údaje!", "Chýbajúce údaje", JOptionPane.WARNING_MESSAGE);
			}
			else {
				meno = txtMeno.getText();
				priezvisko = txtPriezvisko.getText();
				zostatok = Integer.parseInt((txtPociatocnyVklad.getText().trim())); 
				dennyLimit = Integer.parseInt(txtLimitVyberu.getText());

				try {
					Connection spojenie = DriverManager.getConnection(getUrl(), getPrihlasovacie_meno(), getHeslo());
					Statement prikaz = spojenie.createStatement();
					ResultSet vysledok_vyberu = prikaz.executeQuery("SELECT * FROM klienti");
					while(vysledok_vyberu.next()) {
						zoznamId.add(vysledok_vyberu.getString("id"));
						zoznamCisielUctu.add(vysledok_vyberu.getString("cislo_karty"));
						id = vysledok_vyberu.getInt("id");
						id += 1;	
					}
					if(id == 0) 
						id = 1;
					
					vygenerujHeslo();
					String heslo2 = heslo; 
					heslo = zasifruj_heslo(heslo);	
					cislo_uctu = vygenerujCisloUctu();
					String cislo_uctu2 = cislo_uctu; //
					cislo_uctu = zasifruj_cislo_karty(cislo_uctu);
					PreparedStatement prikaz_vyberu = spojenie.prepareStatement("INSERT INTO `klienti` (id, meno, priezvisko, cislo_karty, heslo, denny_limit, zostatok) VALUE ('"+ id +"', '"+ meno +"', '"+ priezvisko +"', '"+ cislo_uctu + "', '"+ heslo + "', '"+ dennyLimit +"', '"+ zostatok +"')");
					int vysledok = prikaz_vyberu.executeUpdate();
					
					if(vysledok > 0) {
						JOptionPane.showMessageDialog(null, "Vitajte " + meno + "! Vaše prihlasovacie údaje: \n Číslo karty: " + cislo_uctu2 + ", Heslo: " + heslo2, "Potvrdenie", JOptionPane.INFORMATION_MESSAGE);
						Login login = new Login();
						frame.dispose();
					}
				}
				catch (SQLException | FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	 /* Funkcia generujúca jedinečné číslo karty Klienta. Obsahuje cyklus for ktorý zaručí, že vygenerované číslo nebude rovné už 
	 * existujúcemu číslu uloženému v ArrayListe zoznamIban. Generuje čísla v rozsahu 100 000 - 200 000.*/
	public String vygenerujCisloUctu() {
		String nove_cislo_uctu = "";
		if(zoznamCisielUctu.size() == 0) // ak bude zoznam
			zoznamCisielUctu.add(nove_cislo_uctu);
		for(int x = 0; x < zoznamCisielUctu.size(); x++) {
			nove_cislo_uctu = String.valueOf(100001 + random.nextInt(100000));
			if(!zoznamCisielUctu.contains(nove_cislo_uctu)) 
				break;
			else 
				continue;
		}
		return nove_cislo_uctu;
	}
	
	// Funkcia generujúca PIN Klienta v rozsahu 1000 - 9999.
	public void vygenerujHeslo() {
		heslo = String.valueOf(1001 + random.nextInt(9000));
	}
	
}
