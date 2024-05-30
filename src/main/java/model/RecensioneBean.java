package model;

public class RecensioneBean {
	public RecensioneBean() {
		voto = 0;
		codiceProdotto = 0;
		testo = null;
	}
	
	public void setVoto(int newVoto) {
		voto = newVoto;
	}
	
	public int getVoto() {
		return voto;
	}
	
	public void setTesto(String newTesto) {
		testo = newTesto;
	}
	
	public String getTesto() {
		return testo;
	}
	
	public void setCodiceProdotto(int newCodice) {
		codiceProdotto = newCodice;
	}
	
	public int getCodiceProdotto() {
		return codiceProdotto;
	}
	
	private int voto;
	private String testo;
	private int codiceProdotto;
}
