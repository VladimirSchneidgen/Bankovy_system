package Bankomat;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ZmenaPin extends Okno implements ActionListener{
	
	private JFrame frame;
	private JLabel lblObrazokPozadia, lblNovyPin, lblNovyPinZopakovanie;
	private JPasswordField lblNoveHeslo, lblNoveHesloZopakovanie;
	private JButton btnZrusit, btnSpat, btnPotvrdit;
	private int id;
	
	public ZmenaPin(int id) throws SQLException {
		this.id = id;
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 445, 331);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Zmena PIN");
		frame.setLocationRelativeTo(null);
		
		btnZrusit = new JButton("Zrušiť");
		btnZrusit.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnZrusit.setBounds(164, 221, 106, 43);
		frame.getContentPane().add(btnZrusit);
		
		btnPotvrdit = new JButton("Potvrdiť");
		btnPotvrdit.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnPotvrdit.setBounds(287, 221, 106, 43);
		frame.getContentPane().add(btnPotvrdit);
		
		btnSpat= new JButton("Späť");
		btnSpat.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSpat.setBounds(41, 221, 106, 43);
		frame.getContentPane().add(btnSpat);
		
		lblNoveHeslo = new JPasswordField();
		lblNoveHeslo.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblNoveHeslo.setBounds(41, 64, 352, 43);
		frame.getContentPane().add(lblNoveHeslo);
		
		lblNoveHesloZopakovanie = new JPasswordField();
		lblNoveHesloZopakovanie.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblNoveHesloZopakovanie.setBounds(41, 157, 352, 43);
		frame.getContentPane().add(lblNoveHesloZopakovanie);
		
		lblNovyPin = new JLabel("Zadajte nové heslo");
		lblNovyPin.setForeground(Color.WHITE);
		lblNovyPin.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblNovyPin.setBounds(41, 25, 352, 43);
		frame.getContentPane().add(lblNovyPin);
		
		lblNovyPinZopakovanie = new JLabel("Zopakujte nové heslo");
		lblNovyPinZopakovanie.setForeground(Color.WHITE);
		lblNovyPinZopakovanie.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblNovyPinZopakovanie.setBounds(41, 117, 352, 43);
		frame.getContentPane().add(lblNovyPinZopakovanie);
		
		lblObrazokPozadia = new JLabel("");
		lblObrazokPozadia.setBounds(0, -18, 442, 317);
		Image obrazokPozadia = new ImageIcon(this.getClass().getResource(getTmavomodrePozadie())).getImage();
		Image upravenyObrazokPozadia = obrazokPozadia.getScaledInstance(437, 393, java.awt.Image.SCALE_SMOOTH);
		frame.getContentPane().setLayout(null);
		lblObrazokPozadia.setIcon(new ImageIcon(upravenyObrazokPozadia));
		frame.getContentPane().add(lblObrazokPozadia);
		
		btnZrusit.addActionListener(this);
		btnPotvrdit.addActionListener(this);
		btnSpat.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton kliknutyButton = new JButton();
		kliknutyButton = (JButton) e.getSource();
		if(kliknutyButton == btnZrusit) {
			lblNoveHeslo.setText("");
			lblNoveHesloZopakovanie.setText("");
		}
		if(kliknutyButton == btnSpat) {
			try {
				HlavneMenu hlavneMenu = new HlavneMenu(id);
				frame.dispose();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		if(kliknutyButton == btnPotvrdit) {
			if(lblNovyPin.getText().isBlank() || lblNovyPinZopakovanie.getText().isBlank()) {
				JOptionPane.showMessageDialog(null, "Chýbajúce údaje! Zadajte a zopakujte PIN!", "Chýbajúce údaje", JOptionPane.WARNING_MESSAGE);
			}
			else {
				String heslo = String.valueOf(lblNoveHeslo.getPassword()); //lblNoveHeslo.getText();//
				try {
					heslo = zasifruj_heslo(heslo);
				} catch (FileNotFoundException e2) {
					e2.printStackTrace();
				}
				String prikazUpdate = "UPDATE Klienti SET pin = '" + heslo + "'WHERE id ='" + id + "'";
				try {
					Connection spojenie = DriverManager.getConnection(getUrl(), getPrihlasovacie_meno(), getHeslo());
					Statement prikaz = spojenie.createStatement();
					int vysledok = prikaz.executeUpdate(prikazUpdate);
					if(vysledok > 0) {
						JOptionPane.showMessageDialog(null, "Váš PIN bol úspešne zmenený.", "Potvrdenie", JOptionPane.INFORMATION_MESSAGE);
						HlavneMenu hlavneMenu = new HlavneMenu(id);
						frame.dispose();
					}
				} catch (SQLException | FileNotFoundException e1) {
					e1.printStackTrace();
				} 
			}
		}
	}
}

