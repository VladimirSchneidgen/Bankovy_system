package Bankomat;

import java.awt.EventQueue;
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
import javax.swing.JTextArea;

import javax.swing.JButton;

public class DennyLimit extends Okno implements ActionListener {

	private JFrame frame;
	private JLabel lblNovyLimit, lblAktualnyLimit, lblObrazokPozadia;
	private JButton btnZrusit, btnPotvrdit, btnSpat;
	private JTextArea txtNovyLimit;
	private int id, limit;
	
	public DennyLimit(int limit, int id) throws SQLException{
		this.limit = limit;
		this.id = id;
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 368, 282);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Limit vyberu");
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null); // nastavi okno na stred
		
		lblNovyLimit = new JLabel("Nastavte si limit výberu:");
		lblNovyLimit.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNovyLimit.setForeground(Color.WHITE);
		lblNovyLimit.setBounds(47, 82, 254, 39);
		frame.getContentPane().add(lblNovyLimit);
		
		lblAktualnyLimit = new JLabel("Váš aktuálny limit: " + limit);
		lblAktualnyLimit.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAktualnyLimit.setForeground(Color.WHITE);
		lblAktualnyLimit.setBounds(47, 47, 314, 39);
		frame.getContentPane().add(lblAktualnyLimit);
		
		txtNovyLimit = new JTextArea();
		txtNovyLimit.setFont(new Font("Tahoma", Font.PLAIN, 24));
		txtNovyLimit.setBounds(47, 126, 254, 39);
		frame.getContentPane().add(txtNovyLimit);
		
		btnZrusit = new JButton("Zrušiť");
		btnZrusit.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnZrusit.setBounds(47, 179, 120, 35);
		frame.getContentPane().add(btnZrusit);
		
		btnPotvrdit = new JButton("Potvrdiť");
		btnPotvrdit.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnPotvrdit.setBounds(181, 179, 120, 35);
		frame.getContentPane().add(btnPotvrdit);
		
		btnSpat = new JButton("Späť");
		btnSpat.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSpat.setBounds(0, 0, 94, 36);
		frame.getContentPane().add(btnSpat);
		
		lblObrazokPozadia = new JLabel("");
		lblObrazokPozadia.setBounds(0, 0, 361, 255);
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
			txtNovyLimit.setText("");
		}
		if(btnPotvrdit == kliknutyButton) {
			if(txtNovyLimit.getText().isBlank()) {
				JOptionPane.showMessageDialog(null, "Zadajte výšku Vášho denného limitu!", "Chýbajúce údaje", JOptionPane.WARNING_MESSAGE);
			}
			else {
				int limit = Integer.parseInt(txtNovyLimit.getText());
				String dopyt = "UPDATE klienti SET denny_limit = '" + limit + "' WHERE id = '" +  id + "'";
				try {
					Connection spojenie = DriverManager.getConnection(getUrl(), getPrihlasovacie_meno(), getHeslo());
					Statement prikaz = spojenie.createStatement();
					int vysledok = prikaz.executeUpdate(dopyt);
					if(vysledok > 0) {
						JOptionPane.showMessageDialog(null, "Denný limit bol zmenený na " + txtNovyLimit.getText() + " EUR.", "Potvrdenie", JOptionPane.INFORMATION_MESSAGE);
						try {
							HlavneMenu hlavneMenu = new HlavneMenu(id);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						frame.dispose();
					}
				}
				catch (SQLException | FileNotFoundException e1) {
					e1.printStackTrace();
					}
			}
		}
		if(btnSpat == kliknutyButton) {
			try {
				HlavneMenu hlavneMenu = new HlavneMenu(id);
			} catch (SQLException e1) {e1.printStackTrace();}
			frame.dispose();
		}
	}
}
