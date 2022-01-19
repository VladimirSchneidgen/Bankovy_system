package Bankomat;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JButton;

public class Vklad extends Okno implements ActionListener{
	
	private JFrame frame;
	private JLabel lblObrazokPozadia, lblVyskaVkladu;
	private JButton btnZrusit, btnPotvrdit, btnSpat;
	private JTextPane txtVyskaVkladu;
	private double zostatok;
	private int id;
	
	public Vklad(double zostatok, int id) {
		this.zostatok = zostatok;
		this.id = id;
		
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 348, 277);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Vklad");
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		lblVyskaVkladu = new JLabel("Zadajte výšku vkladu:");
		lblVyskaVkladu.setBounds(57, 67, 244, 45);
		lblVyskaVkladu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblVyskaVkladu.setForeground(Color.WHITE);
		frame.getContentPane().add(lblVyskaVkladu);
		
		txtVyskaVkladu = new JTextPane();
		txtVyskaVkladu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtVyskaVkladu.setBounds(57, 111, 217, 36);
		frame.getContentPane().add(txtVyskaVkladu);
		
		btnZrusit = new JButton("Zrušiť");
		btnZrusit.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnZrusit.setBounds(57, 163, 94, 36);
		frame.getContentPane().add(btnZrusit);
		
		btnPotvrdit = new JButton("OK");
		btnPotvrdit.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnPotvrdit.setBounds(171, 163, 103, 36);
		frame.getContentPane().add(btnPotvrdit);
		
		btnSpat = new JButton("Späť");
		btnSpat.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSpat.setBounds(0, 0, 94, 36);
		frame.getContentPane().add(btnSpat);
		
		lblObrazokPozadia = new JLabel("");
		lblObrazokPozadia.setBounds(0, 0, 420, 247);
		Image obrazokPozadia = new ImageIcon(this.getClass().getResource(getTmavomodrePozadie())).getImage();
		Image upravenyObrazokPozadia = obrazokPozadia.getScaledInstance(738, 403, java.awt.Image.SCALE_SMOOTH);
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
		if(btnZrusit == kliknutyButton) {
			txtVyskaVkladu.setText("");
		}
		if(btnPotvrdit == kliknutyButton) {
			double novyZostatok = zostatok + Double.parseDouble(txtVyskaVkladu.getText());
			String dopyt = "UPDATE Klienti SET Zostatok = '" + novyZostatok + "' WHERE ID = '" +  id + "'";
			try {
				Connection spojenie = DriverManager.getConnection(getUrl(), getPrihlasovacie_meno(), getHeslo());
				Statement prikaz = spojenie.createStatement();
				int vysledok = prikaz.executeUpdate(dopyt);
				if(vysledok > 0) {
					JOptionPane.showMessageDialog(null, "Vklad v hodnote " + txtVyskaVkladu.getText() + " EUR je dokončený.", "Potvrdenie", JOptionPane.INFORMATION_MESSAGE);
					HlavneMenu hlavneMenu = new HlavneMenu(id);
					frame.dispose();
				}
			} catch (SQLException | FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		if(btnSpat == kliknutyButton) {
			try {
				HlavneMenu hlavneMenu = new HlavneMenu(id);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			frame.dispose();
		}
	}
}
