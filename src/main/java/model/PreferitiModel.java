package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;

public class PreferitiModel {
	public synchronized Collection<ProductBean> updatePreferiti(ProductBean bean, Collection<ProductBean> collection) {
		Collection<ProductBean> lista = new LinkedList<ProductBean>();
		
		if (collection != null && collection.size() != 0) {
			Iterator<?> it = collection.iterator();
			while (it.hasNext()) {
				ProductBean prodotto = (ProductBean) it.next();
				if (prodotto.getCodice() == bean.getCodice()) {
					prodotto.setNome(bean.getNome());
					prodotto.setDescrizione(bean.getDescrizione());
					prodotto.setPrezzo(bean.getPrezzo());
					prodotto.setSpedizione(bean.getSpedizione());
					prodotto.setTipologia(bean.getTipologia());
					prodotto.setTag(bean.getTag());
				}
				lista.add(prodotto);
			}
		}
		return lista;
	}
	
	public synchronized Collection<ProductBean> ottieni(String email) {
			String userEmail = email;
			String sql = "SELECT * FROM Prodotto WHERE deleted = 'false' AND codice IN (SELECT codiceProdotto FROM Preferiti WHERE emailCliente = ?)";
			ProductBean bean;
			Collection<ProductBean> preferiti = new LinkedList<ProductBean>(); 
			Connection con = null;
			PreparedStatement ps = null;
			
			try {
				con = DriverManagerConnectionPool.getConnection();
				ps = con.prepareStatement(sql);
				ps.setString(1, userEmail);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					bean = new ProductBean();
					bean.setCodice(rs.getInt("codice"));
					bean.setNome(rs.getString("nome"));
					bean.setDescrizione(rs.getString("descrizione"));
					bean.setPrezzo(rs.getDouble("prezzo"));
					bean.setSpedizione(rs.getDouble("speseSpedizione"));
					bean.setEmail(rs.getString("emailVenditore"));
					bean.setTag(rs.getString("tag"));
					bean.setTipologia(rs.getString("nomeTipologia"));
					bean.setData(rs.getDate("dataAnnuncio"));
					bean.setImmagine(rs.getString("model"));
					
					preferiti.add(bean);
				}
				
				return preferiti;
			}
			catch (Exception e){
				return preferiti;
			}
			finally {
				if (con != null) {
					DriverManagerConnectionPool.releaseConnection(con);
				}	
			}
	}
	
	public synchronized Collection<ProductBean> inserisci(String email, int codice, Collection<ProductBean> preferito) {
		String sql = "INSERT INTO Preferiti(codiceProdotto, emailCliente) VALUES (?, ?)";
		
		Connection connection = null;
		try {
			connection = DriverManagerConnectionPool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, codice);
			preparedStatement.setString(2, email);

			preparedStatement.executeUpdate();
			connection.commit();
			
			Connection con = null;
			String sql2 = "SELECT * FROM Prodotto WHERE codice = ?";
			try {
				con = DriverManagerConnectionPool.getConnection();
				PreparedStatement ps = con.prepareStatement(sql2);
				ps.setInt(1, codice);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					ProductBean bean = new ProductBean();
					bean.setCodice(rs.getInt("codice"));
					bean.setNome(rs.getString("nome"));
					bean.setDescrizione(rs.getString("descrizione"));
					bean.setPrezzo(rs.getDouble("prezzo"));
					bean.setSpedizione(rs.getDouble("speseSpedizione"));
					bean.setEmail(rs.getString("emailVenditore"));
					bean.setTag(rs.getString("tag"));
					bean.setTipologia(rs.getString("nomeTipologia"));
					bean.setData(rs.getDate("dataAnnuncio"));
					bean.setImmagine(rs.getString("model"));
					
					preferito.add(bean);
					return preferito;
				}
			}
			catch (Exception e){
				return preferito;
			}
		}
		catch (Exception e) {
			return preferito;
		}
		finally {
			if (connection != null) {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		return preferito;
	}
}
