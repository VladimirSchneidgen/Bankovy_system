package Bankomat;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;

public class ZostatokNaUcte extends Okno implements ActionListener{

	public JFrame frame;
	public JLabel lblObrazokPozadia, lblZostatokNapis, lblZostatokHodnota;
	private JButton btnVytlacit, btnSpat;
	public double zostatok;
	private int id;

	public ZostatokNaUcte(double zostatok, int id) {
		this.zostatok = zostatok;
		this.id = id;

		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 396, 186);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Zostatok na účte");
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);

		lblZostatokNapis = new JLabel("Zostatok:");
		lblZostatokNapis.setForeground(Color.WHITE);
		lblZostatokNapis.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblZostatokNapis.setBounds(69, 28, 110, 60);
		frame.getContentPane().add(lblZostatokNapis);

		lblZostatokHodnota = new JLabel(zostatok + " EUR");
		lblZostatokHodnota.setForeground(Color.WHITE);
		lblZostatokHodnota.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblZostatokHodnota.setBounds(189, 28, 170, 60);
		frame.getContentPane().add(lblZostatokHodnota);

		btnVytlacit = new JButton("Vytlačiť ");
		btnVytlacit.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnVytlacit.setBounds(190, 86, 130, 33);
		frame.getContentPane().add(btnVytlacit);
		
		btnSpat = new JButton("OK");
		btnSpat.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSpat.setBounds(79, 86, 90, 33);
		frame.getContentPane().add(btnSpat);
		
		lblObrazokPozadia = new JLabel("");
		lblObrazokPozadia.setBounds(0, 0, 389, 155);
		Image obrazokPozadia = new ImageIcon(this.getClass().getResource(getTmavomodrePozadie())).getImage();
		Image upravenyObrazokPozadia = obrazokPozadia.getScaledInstance(738, 403, java.awt.Image.SCALE_SMOOTH);
		lblObrazokPozadia.setIcon(new ImageIcon(upravenyObrazokPozadia));
		frame.getContentPane().add(lblObrazokPozadia);
		
		btnVytlacit.addActionListener(this);
		btnSpat.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton kliknutyButton = (JButton) e.getSource();	
		if(btnVytlacit == kliknutyButton) {

			zostatok -= 0.05;
			double zaokruhlenyZostatok = Math.round(zostatok *100.0)/100.0;
			String prikazUpdate = "UPDATE Klienti SET Zostatok = '" + zaokruhlenyZostatok + "' WHERE ID = '" + id + "'" ;
		
			Connection spojenie = null;
			try {
				spojenie = DriverManager.getConnection(getUrl(), getPrihlasovacie_meno(), getHeslo());
				Statement prikaz = spojenie.createStatement();
				int vysledok = prikaz.executeUpdate(prikazUpdate);
				if(vysledok > 0) {
					JOptionPane.showMessageDialog(null, "          Zostatok sa tlačí.\nBolo Vám strhnutých 5 centov.", "Potvrdenie", JOptionPane.INFORMATION_MESSAGE);
					HlavneMenu hlavneMenu = new HlavneMenu(id);
				} 
					frame.dispose();
				}
			catch (SQLException | FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		if(btnSpat == kliknutyButton) {
			try {
				HlavneMenu hlavneMenu = new HlavneMenu(id);
				frame.dispose();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
