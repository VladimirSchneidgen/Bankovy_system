package Bankomat;

import java.sql.*;
import java.util.Scanner;
import java.io.*;

public class Okno {
	
	private String url, prihlasovacie_meno, heslo, tmavomodrePozadie = "/tmavoModrePozadie.jpg";
	private int kluc_cislo_karty, kluc_heslo;
	
	/*Z textoveho suboru precita znakovy retazec (URL), prihlasovacie meno k MySQL Workbench a heslo 
	 * a ulozi ich do jednotlivych premennych (url, prihlasovacie_meno, heslo). */
	public void citaj_spojenie_mysql() throws FileNotFoundException {
		File subor_spojenie = new File("/C:/Users/Asus/Desktop/Java/spojenie_mysql.txt");
		Scanner citaj_spojenie = new Scanner(subor_spojenie);
		while(citaj_spojenie.hasNext()) {
			url = citaj_spojenie.next();
			prihlasovacie_meno = citaj_spojenie.next();
			heslo = citaj_spojenie.next();
		}
	}
	
	public String getTmavomodrePozadie() {
		return tmavomodrePozadie;
	}
	
	// Vola funckiu citaj_spojenie_mysql(), ktora precita URL, prihlasovacie meno a heslo k MySQL Workbench.
	public String getUrl() throws FileNotFoundException {
		citaj_spojenie_mysql();
		return url;
	}

	public String getPrihlasovacie_meno() {
		return prihlasovacie_meno;
	}
	
	public String getHeslo() {
		return heslo;
	}

	 /*  Funkcia cita z cieloveho textoveho suboru 2 cele cisla:
	 *  1. cislo je kluc pre sifrovanie cisla karty
	 *  2. cislo je kluc pre sifrovanie hesla */
	public void getKluc() throws FileNotFoundException {
		File subor_kluc = new File("/C:/Users/Asus/Desktop/Java/kluc.txt");  
		Scanner precitany_kluc = new Scanner(subor_kluc);
		while(precitany_kluc.hasNext()) {
			kluc_cislo_karty = precitany_kluc.nextInt();
			kluc_heslo = precitany_kluc.nextInt();
		}
	}
	
	
	/* Vola funkciu getKluc(), potom zasifruje heslo klucom, ktory precitala z textoveho suboru. Funkcia vrati
	zasifrovane heslo */
	public String zasifruj_heslo(String heslo) throws FileNotFoundException {
		char[] a = heslo.toCharArray();
		getKluc();
		for(int i = 0; i < heslo.length(); i++) {
			a[i] += kluc_heslo;
			i++;
		}
		return String.valueOf(a);
	}
	
	/* Vola funkciu getKluc(), potom desifruje heslo klucom, ktory precitala z textoveho suboru. Funckia vrati 
	desifrovane heslo. */
	public String desifruj_heslo(String heslo) throws FileNotFoundException {
		char[] a = heslo.toCharArray();
		getKluc();
		for(int i = 0; i < heslo.length(); i++) {
			a[i] -= kluc_heslo;
			i++;
		}
		return String.valueOf(a);
	}
	
	/* Vola funkciu getKluc(), potom zasifruje cislo karty klucom, ktory precitala z textoveho suboru. Funkcia 
	vrati zasifrovane cislo karty. */
	public String zasifruj_cislo_karty(String cislo_karty) throws FileNotFoundException {
		char[] a = cislo_karty.toCharArray();
		getKluc();
		for(int i = 0; i < cislo_karty.length(); i++) {
			a[i] += kluc_cislo_karty;
			i++;
		}
		return String.valueOf(a);
	}
	
	/* Vola funkciu getKluc(), potom desifruje heslo klucom, ktory precitala z textoveho suboru. 
	 * Funkcia vrati desifrovane cislo karty. */
	public String desifruj_cislo_karty(String cislo_karty) throws FileNotFoundException {
		char[] a = cislo_karty.toCharArray();
		getKluc();
		for(int i = 0; i < cislo_karty.length(); i++) {
			a[i] -= kluc_cislo_karty;
			i++;
		}
		return String.valueOf(a);
	}
}
