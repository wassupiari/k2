package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class CartModel {
	public synchronized CartBean updateCarrello(ProductBean bean, CartBean cart) {
		Collection<ProductBean> collection = cart.getCarrello();
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
		cart.setLista(lista);
		}
		return cart;
	}
	
	public synchronized CartBean acquista(CartBean cart, UserBean user) {
		Connection con = null;
		Collection<ProductBean>carrello = cart.getCarrello();
		String sql = "INSERT INTO Ordine (codiceProdotto, emailCliente, prezzoTotale, quantity, dataAcquisto) VALUES (?, ?, ?, ?, current_date())";
		
		try {
			con = DriverManagerConnectionPool.getConnection();
			
			if (carrello != null && carrello.size() != 0) {
				for (Iterator<ProductBean> i = cart.getCarrello().iterator(); i.hasNext();) {
					ProductBean bean = (ProductBean) i.next();
					Double prezzoTot = ((bean.getPrezzo() + bean.getSpedizione()) * bean.getQuantity());
					
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setInt(1, bean.getCodice());
					ps.setString(2, user.getEmail());
					ps.setDouble(3, prezzoTot);
					ps.setInt(4, bean.getQuantity());
					
					ps.executeUpdate();
				}
				con.commit();
				cart.removeAllItems();
			}
			return cart;
		}
		catch (Exception e) {
			return cart;
		}
		finally {
			if (con != null) {
				DriverManagerConnectionPool.releaseConnection(con);
			}
		}	
	}
	
	public synchronized CartBean aggiungiAlCarrello(CartBean carrello, int codiceProdotto) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;		

		ProductBean bean = new ProductBean();

		String selectSQL = "SELECT * FROM Prodotto WHERE codice = ? AND deleted = false";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, codiceProdotto);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
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
			}
			carrello.setCarrello(bean);
			return carrello;
		}
		catch (Exception e) {
			return carrello;
		}
		finally {
			if (connection != null) {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}
}
