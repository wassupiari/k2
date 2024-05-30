package model;

import java.io.Serializable;
import java.sql.Date;


public class ProductBean implements Serializable {
	private static final long serialVersionUID = -4893809106798767527L;
	
	public ProductBean() {
		codice = -1;
		nome = "";
		descrizione = "";
		prezzo = 0;
		spedizione = 0;
		email = "";
		tag = "";
		tipologia = "";
		data = null;
		immagine = null;
		votazione = 0;
		quantity = 1;
	}
	
	public void setCodice(int newCodice) {
		codice = newCodice;
	}
	
	public int getCodice() {
		return codice;
	}
	
	public void setNome(String newNome) {
		nome = newNome;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setDescrizione(String newDesc) {
		descrizione = newDesc;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setPrezzo(double newPrezzo) {
		prezzo = newPrezzo;
	}
	
	public double getPrezzo() {
		return prezzo;
	}
	
	public void setSpedizione(double newSpeseSped) {
		spedizione = newSpeseSped;
	}
	
	public double getSpedizione() {
		return spedizione;
	}
	
	public void setEmail(String newEmail) {
		email = newEmail;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setTag(String newTag) {
		tag = newTag;
	}
	
	public String getTag() {
		return tag;
	}
	
	public void setTipologia(String newTipologia) {
		tipologia = newTipologia;
	}
	
	public String getTipologia() {
		return tipologia;
	}
	
	public void setData(Date newData) {
		data = newData;
	}
	
	public Date getData() {
		return data;
	}
	
	public void setImmagine(String newImmagine) {
		/*InputStream inputStream = null;
		try {
			inputStream = newImmagine.getBinaryStream();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			int bytesRead = -1;
			 
			while ((bytesRead = inputStream.read(buffer)) != -1) {
			    outputStream.write(buffer, 0, bytesRead);
			}
			 
			byte[] imageBytes = outputStream.toByteArray();
			 
			immagine = Base64.getEncoder().encodeToString(imageBytes);
			 
			inputStream.close();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		immagine = newImmagine;
	}
	
	public String getImmagine() {
		return immagine;
	}
	
	public void setVotazione(double newVotazione) {
		votazione = newVotazione;
	}
	
	public double getVotazione() {
		return votazione;
	}
	
	public void setQuantity(int newQuantity) {
		quantity = newQuantity;
	}
	
	public void decreaseQuantity() {
		if (quantity > 0) {
			quantity--;
		}
	}
	
	public void addQuantity() {
		quantity++;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	private int codice;
	private String nome;
	private String descrizione;
	private double prezzo;
	private double spedizione;
	private String email;
	private String tag;
	private String tipologia;
	private Date data;
	private String immagine;
	private double votazione;
	private int quantity;
}
