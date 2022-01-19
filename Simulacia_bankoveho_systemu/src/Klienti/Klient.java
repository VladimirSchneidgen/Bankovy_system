package Klienti;

public class Klient {
	
	private String meno, priezvisko, iban, pin;
	private int id, dennyLimit;
	private double zostatok;
	
	public Klient(int id, String meno, String priezvisko, String iban, String pin, int denny_limit, double zostatok) {
		this.id = id;
		this.meno = meno;
		this.priezvisko = priezvisko;
		this.iban = iban;
		this.pin = pin;
		this.zostatok = zostatok;
		this.dennyLimit = denny_limit;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMeno() {
		return meno;
	}

	public void setMeno(String meno) {
		this.meno = meno;
	}

	public String getPriezvisko() {
		return priezvisko;
	}

	public void setPriezvisko(String priezvisko) {
		this.priezvisko = priezvisko;
	}
	
	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public double getZostatok() {
		return zostatok;
	}

	public void setZostatok(double zostatok) {
		this.zostatok = zostatok;
	}	
	
	public int getDennyLimit() {
		return dennyLimit;
	}
	
	public void setDennyLimit(int dennyLimit) {
		this.dennyLimit = dennyLimit;
	}
}
