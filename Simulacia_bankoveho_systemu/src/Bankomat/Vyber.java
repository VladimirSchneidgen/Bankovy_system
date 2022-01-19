package Bankomat;

import java.awt.Image;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class Vyber extends Okno implements ActionListener{
	
	public JFrame frame;
	public JLabel lblObrazokPozadia, lblVyberSkladbyBankoviek, lblPatEur, lblDesatEur, lblDvadsatEur, lblPatdesiatEur, lblStoEur, 
	lblPatstoEur, lblSumaVyberu;
	public JButton plusPatEur, plusDesatEur, plusDvadsatEur, plusPatdesiatEur, plusStoEur, plusPatstoEur, minusPatEur, minusDesatEur,
	minusDvadsatEur, minusPatdesiatEur, minusStoEur, minusPatstoEur, btnZrušiť, btnPotvrdit, kliknutyButton, btnSpat;
	public double zostatok;
	public int id, limit;
	int sumaVyberu = 0;
	int pocetPatEurovychBankoviek = 0, pocetDesatEurovychBankoviek = 0, pocetDvadsatEurovychBankoviek = 0,
			pocetPatdesiatEurovychBankoviek = 0, pocetStoEurovychBankoviek = 0, pocetPatstoEurovychBankoviek = 0;
	
	public Vyber(double zostatok, int id, int limit) throws SQLException {
		this.zostatok = zostatok;
		this.id = id;
		this.limit = limit;
		
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 511, 492);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Výber Hotovosti");
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		lblVyberSkladbyBankoviek = new JLabel("Zvoľte si skladbu bankoviek tlačidlami +/-");
		lblVyberSkladbyBankoviek.setHorizontalAlignment(SwingConstants.CENTER);
		lblVyberSkladbyBankoviek.setForeground(Color.WHITE);
		lblVyberSkladbyBankoviek.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblVyberSkladbyBankoviek.setBounds(0, 10, 497, 55);
		frame.getContentPane().add(lblVyberSkladbyBankoviek);
	
		plusPatEur = new JButton("+");
		plusPatEur.setFont(new Font("Stencil", Font.PLAIN, 17));
		plusPatEur.setBounds(27, 115, 49, 35);
		frame.getContentPane().add(plusPatEur);
		
		plusDesatEur = new JButton("+");
		plusDesatEur.setFont(new Font("Stencil", Font.PLAIN, 17));
		plusDesatEur.setBounds(27, 160, 49, 35);
		frame.getContentPane().add(plusDesatEur);
		
		plusDvadsatEur = new JButton("+");
		plusDvadsatEur.setFont(new Font("Stencil", Font.PLAIN, 17));
		plusDvadsatEur.setBounds(27, 205, 49, 35);
		frame.getContentPane().add(plusDvadsatEur);
		
		plusPatdesiatEur = new JButton("+");
		plusPatdesiatEur.setFont(new Font("Stencil", Font.PLAIN, 17));
		plusPatdesiatEur.setBounds(27, 250, 49, 35);
		frame.getContentPane().add(plusPatdesiatEur);
		
		plusStoEur = new JButton("+");
		plusStoEur.setFont(new Font("Stencil", Font.PLAIN, 17));
		plusStoEur.setBounds(27, 295, 49, 35);
		frame.getContentPane().add(plusStoEur);
		
		plusPatstoEur = new JButton("+");
		plusPatstoEur.setFont(new Font("Stencil", Font.PLAIN, 17));
		plusPatstoEur.setBounds(27, 340, 49, 35);
		frame.getContentPane().add(plusPatstoEur);
		
		minusPatEur = new JButton("-");
		minusPatEur.setFont(new Font("Stencil", Font.BOLD, 27));
		minusPatEur.setBounds(432, 115, 43, 35);
		frame.getContentPane().add(minusPatEur);
		
		minusDesatEur = new JButton("-");
		minusDesatEur.setFont(new Font("Stencil", Font.BOLD, 27));
		minusDesatEur.setBounds(432, 160, 43, 35);
		frame.getContentPane().add(minusDesatEur);
		
		minusDvadsatEur = new JButton("-");
		minusDvadsatEur.setFont(new Font("Stencil", Font.BOLD, 27));
		minusDvadsatEur.setBounds(432, 205, 43, 35);
		frame.getContentPane().add(minusDvadsatEur);
		
		minusPatdesiatEur = new JButton("-");
		minusPatdesiatEur.setFont(new Font("Stencil", Font.BOLD, 27));
		minusPatdesiatEur.setBounds(432, 250, 43, 35);
		frame.getContentPane().add(minusPatdesiatEur);
		
		minusStoEur = new JButton("-");
		minusStoEur.setFont(new Font("Stencil", Font.BOLD, 27));
		minusStoEur.setBounds(432, 295, 43, 35);
		frame.getContentPane().add(minusStoEur);
		
		minusPatstoEur = new JButton("-");
		minusPatstoEur.setFont(new Font("Stencil", Font.BOLD, 27));
		minusPatstoEur.setBounds(432, 340, 43, 35);
		frame.getContentPane().add(minusPatstoEur);
		
		btnZrušiť = new JButton("Zrušiť");
		btnZrušiť.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnZrušiť.setBounds(182, 389, 136, 43);
		frame.getContentPane().add(btnZrušiť);
		
		btnPotvrdit = new JButton("Potvrdiť");
		btnPotvrdit.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnPotvrdit.setBounds(338, 389, 136, 43);
		frame.getContentPane().add(btnPotvrdit);
		
		btnSpat = new JButton("Späť");
		btnSpat.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSpat.setBounds(27, 389, 136, 43);
		frame.getContentPane().add(btnSpat);
		
		lblPatEur = new JLabel("0 x 5 EUR");
		lblPatEur.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPatEur.setHorizontalAlignment(SwingConstants.CENTER);
		lblPatEur.setForeground(Color.WHITE);
		lblPatEur.setBackground(Color.WHITE);
		lblPatEur.setBounds(70, 115, 364, 35);
		frame.getContentPane().add(lblPatEur);
		
		lblDesatEur = new JLabel("0 x 10 EUR");
		lblDesatEur.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDesatEur.setHorizontalAlignment(SwingConstants.CENTER);
		lblDesatEur.setForeground(Color.WHITE);
		lblDesatEur.setBackground(Color.WHITE);
		lblDesatEur.setBounds(70, 160, 364, 35);
		frame.getContentPane().add(lblDesatEur);
		
		lblDvadsatEur = new JLabel("0 x 20 EUR");
		lblDvadsatEur.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDvadsatEur.setHorizontalAlignment(SwingConstants.CENTER);
		lblDvadsatEur.setForeground(Color.WHITE);
		lblDvadsatEur.setBackground(Color.WHITE);
		lblDvadsatEur.setBounds(70, 205, 364, 35);
		frame.getContentPane().add(lblDvadsatEur);
		
		lblPatdesiatEur = new JLabel("0 x 50 EUR");
		lblPatdesiatEur.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPatdesiatEur.setHorizontalAlignment(SwingConstants.CENTER);
		lblPatdesiatEur.setForeground(Color.WHITE);
		lblPatdesiatEur.setBackground(Color.WHITE);
		lblPatdesiatEur.setBounds(70, 250, 358, 35);
		frame.getContentPane().add(lblPatdesiatEur);
		
		lblStoEur = new JLabel("0 x 100 EUR");
		lblStoEur.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblStoEur.setHorizontalAlignment(SwingConstants.CENTER);
		lblStoEur.setForeground(Color.WHITE);
		lblStoEur.setBackground(Color.WHITE);
		lblStoEur.setBounds(70, 295, 364, 35);
		frame.getContentPane().add(lblStoEur);
		
		lblPatstoEur = new JLabel("0 x 500 EUR");
		lblPatstoEur.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPatstoEur.setHorizontalAlignment(SwingConstants.CENTER);
		lblPatstoEur.setForeground(Color.WHITE);
		lblPatstoEur.setBackground(Color.WHITE);
		lblPatstoEur.setBounds(70, 340, 364, 35);
		frame.getContentPane().add(lblPatstoEur);
		
		lblSumaVyberu = new JLabel("Suma výberu: 0 EUR");
		lblSumaVyberu.setHorizontalAlignment(SwingConstants.CENTER);
		lblSumaVyberu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSumaVyberu.setForeground(Color.WHITE);
		lblSumaVyberu.setBounds(70, 59, 358, 35);
		frame.getContentPane().add(lblSumaVyberu);
		
		lblObrazokPozadia = new JLabel("");
		lblObrazokPozadia.setBounds(-12, -14, 522, 469);
		Image obrazokPozadia = new ImageIcon(this.getClass().getResource(getTmavomodrePozadie())).getImage();
		Image upravenyObrazokPozadia = obrazokPozadia.getScaledInstance(510, 492, java.awt.Image.SCALE_SMOOTH);
		frame.getContentPane().setLayout(null);
		lblObrazokPozadia.setIcon(new ImageIcon(upravenyObrazokPozadia));
		frame.getContentPane().add(lblObrazokPozadia);	
			
		plusPatEur.addActionListener(this);
		minusPatEur.addActionListener(this);
		plusDesatEur.addActionListener(this);
		minusDesatEur.addActionListener(this);
		plusDvadsatEur.addActionListener(this);
		minusDvadsatEur.addActionListener(this);
		plusPatdesiatEur.addActionListener(this);
		minusPatdesiatEur.addActionListener(this);
		plusStoEur.addActionListener(this);
		minusStoEur.addActionListener(this);
		plusPatstoEur.addActionListener(this);
		minusPatstoEur.addActionListener(this);
		btnZrušiť.addActionListener(this);
		btnPotvrdit.addActionListener(this);
		btnSpat.addActionListener(this);
	}
	
	public void vynuluj() {
		sumaVyberu = 0;
		lblSumaVyberu.setText("Suma vyberu: " + 0);
		lblPatEur.setText("0 x 5 EUR");
		lblDesatEur.setText("0 x 10 EUR");
		lblDvadsatEur.setText("0 x 20 EUR");
		lblPatdesiatEur.setText("0 x 50 EUR");
		lblStoEur.setText("0 x 100 EUR");
		lblPatstoEur.setText("0 x 500 EUR");
		pocetPatEurovychBankoviek = 0;
		pocetDesatEurovychBankoviek = 0;
		pocetDvadsatEurovychBankoviek = 0;
		pocetPatdesiatEurovychBankoviek = 0;
		pocetStoEurovychBankoviek = 0;
		pocetPatstoEurovychBankoviek = 0;
	}
	
	public void actionPerformed(ActionEvent e) {
		kliknutyButton = (JButton) e.getSource();
		
		if(kliknutyButton == plusPatEur) {
			pocetPatEurovychBankoviek++;
			sumaVyberu +=  5;
			lblPatEur.setText(pocetPatEurovychBankoviek +" x 5 EUR");
			lblSumaVyberu.setText("Suma vyberu: " + sumaVyberu);
		}
		if(kliknutyButton == minusPatEur) {
			if(pocetPatEurovychBankoviek > 0) {
				pocetPatEurovychBankoviek--;
				sumaVyberu -= 5;
				lblPatEur.setText(pocetPatEurovychBankoviek +" x 5 EUR");
				lblSumaVyberu.setText("Suma vyberu: " + sumaVyberu);	
			}
		}
		if(kliknutyButton == plusDesatEur) {
			pocetDesatEurovychBankoviek++;
			sumaVyberu += 10;
			lblDesatEur.setText(pocetDesatEurovychBankoviek + " x 10 EUR");
			lblSumaVyberu.setText("Suma vyberu: " + sumaVyberu);
		}
		if(kliknutyButton == minusDesatEur) {
			if(pocetDesatEurovychBankoviek > 0) {
				pocetDesatEurovychBankoviek--;
				sumaVyberu -= 10;
				lblDesatEur.setText(pocetDesatEurovychBankoviek + " x 10 EUR");
				lblSumaVyberu.setText("Suma vyberu: " + sumaVyberu);
			}
		}
		if(kliknutyButton == plusDvadsatEur) {
			pocetDvadsatEurovychBankoviek++;
			sumaVyberu += 20;
			lblDvadsatEur.setText(pocetDvadsatEurovychBankoviek + " x 20 EUR");
			lblSumaVyberu.setText("Suma vyberu: " + sumaVyberu);
		}
		if(kliknutyButton == minusDvadsatEur) {
			if(pocetDvadsatEurovychBankoviek > 0) {
				pocetDvadsatEurovychBankoviek--;
				sumaVyberu -= 20;
				lblDvadsatEur.setText(pocetDvadsatEurovychBankoviek + " x 20 EUR");
				lblSumaVyberu.setText("Suma vyberu: " + sumaVyberu);
			}
		}
		if(kliknutyButton == plusPatdesiatEur) {
			pocetPatdesiatEurovychBankoviek++;
			sumaVyberu += 50;
			lblPatdesiatEur.setText(pocetPatdesiatEurovychBankoviek + " x 50 EUR");
			lblSumaVyberu.setText("Suma vyberu: " + sumaVyberu);
		}
		if(kliknutyButton == minusPatdesiatEur) {
			if(pocetPatdesiatEurovychBankoviek > 0) {
				pocetPatdesiatEurovychBankoviek--;
				sumaVyberu -= 50;
				lblPatdesiatEur.setText(pocetPatdesiatEurovychBankoviek + " x 50 EUR");
				lblSumaVyberu.setText("Suma vyberu: " + sumaVyberu);
			}
		}
		if(kliknutyButton == plusStoEur) {
			pocetStoEurovychBankoviek++;
			sumaVyberu += 100;
			lblStoEur.setText(pocetStoEurovychBankoviek + " x 100 EUR");
			lblSumaVyberu.setText("Suma vyberu: " + sumaVyberu);
		}
		if(kliknutyButton == minusStoEur) {
			if(pocetStoEurovychBankoviek > 0) {
				pocetStoEurovychBankoviek--;
				sumaVyberu -= 100;
				lblStoEur.setText(pocetStoEurovychBankoviek + " x 100 EUR");
				lblSumaVyberu.setText("Suma vyberu: " + sumaVyberu);	
			}
		}
		if(kliknutyButton == plusPatstoEur) {
			pocetPatstoEurovychBankoviek++;
			sumaVyberu += 500;
			lblPatstoEur.setText(pocetPatstoEurovychBankoviek + " x 500 EUR");
			lblSumaVyberu.setText("Suma vyberu: " + sumaVyberu);
		}
		if(kliknutyButton == minusPatstoEur) {
			if(pocetPatstoEurovychBankoviek > 0) {
				pocetPatstoEurovychBankoviek--;
				sumaVyberu -= 500;
				lblPatstoEur.setText(pocetPatstoEurovychBankoviek + " x 500 EUR");
				lblSumaVyberu.setText("Suma vyberu: " + sumaVyberu);	
			}
		}
		if(kliknutyButton == btnZrušiť) {
			vynuluj();
		}
		
		if(kliknutyButton == btnPotvrdit) {
			if(sumaVyberu > limit) {
				JOptionPane.showMessageDialog(null, "Váš denný limit je vo výške " + limit + " EUR, preto nemôžete vybrať sumu " + sumaVyberu + " EUR!", "Varovanie", JOptionPane.WARNING_MESSAGE);
			}
			else {
				zostatok -= sumaVyberu;
				String dopyt = "UPDATE Klienti SET Zostatok = '" + zostatok + "' WHERE ID = '" + id + "'";
				try {
					Connection spojenie = DriverManager.getConnection(getUrl(), getPrihlasovacie_meno(), getHeslo());
					Statement prikaz = spojenie.createStatement();
					int vysledok = prikaz.executeUpdate(dopyt);
					if(vysledok > 0) {
						JOptionPane.showMessageDialog(null, "Výber v hodnote " + sumaVyberu + " EUR je dokončený.", "Potvrdenie", JOptionPane.INFORMATION_MESSAGE);
						HlavneMenu hlavneMenu = new HlavneMenu(id);
						frame.dispose();
					}
				} catch (SQLException | FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		}
		if(kliknutyButton == btnSpat) {
			try {
				HlavneMenu hlavneMenu = new HlavneMenu(id);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			frame.dispose();
		}
	}

}
