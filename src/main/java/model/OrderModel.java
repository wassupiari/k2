package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class OrderModel {
	public synchronized CartBean doOrder(CartBean carr, UserBean userr) {
		Collection<ProductBean> carrello = null;

			Connection con = null;
			CartBean cart = carr;
			UserBean user = userr;
			carrello = cart.getCarrello();
			
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
				}
				cart.removeAllItems();
				return cart;
			}
			catch (Exception e) {
				return null;
			}
			finally {
				if (con != null) {
					DriverManagerConnectionPool.releaseConnection(con);
				}
			}	
	}
	
	public synchronized Collection<OrderBean> getOrders(String email) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection<OrderBean> listaOrdini = new LinkedList<OrderBean>();
		
		String sql = "SELECT o.codiceOrdine, o.codiceProdotto, o.prezzoTotale, o.quantity, o.dataAcquisto, p.nome, p.emailVenditore, p.model FROM Ordine AS o, Prodotto AS p WHERE emailCliente = ? AND o.codiceProdotto = p.codice ORDER BY codiceOrdine DESC";
		
		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, email);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				ProductBean bean = new ProductBean();
				OrderBean ordine = new OrderBean();
				
				bean.setPrezzo(rs.getDouble("prezzoTotale"));
				bean.setQuantity(rs.getInt("quantity"));
				bean.setNome(rs.getString("nome"));
				bean.setImmagine(rs.getString("model"));
				bean.setCodice(rs.getInt("codiceProdotto"));
				ordine.setProdotto(bean);
				
				ordine.setCodice(rs.getInt("codiceOrdine"));
				ordine.setData(rs.getDate("dataAcquisto"));
				ordine.setVenditore(rs.getString("emailVenditore"));
				listaOrdini.add(ordine);
			}
			return listaOrdini;
		}
		catch (Exception e) {
			return null;
		}
		finally {
			if (con != null) {
				DriverManagerConnectionPool.releaseConnection(con);
			}
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
		}
	}
	
	public synchronized Collection<OrderBean> getRecensioni(Collection<OrderBean> collezione, String email) throws SQLException {
		String sql2 = "SELECT votazione FROM Recensione WHERE codiceProdotto = ? AND emailCliente = ?";
		Collection<OrderBean> lista = new LinkedList<OrderBean>();
		Connection con = null;
		PreparedStatement ps = null;
		
		Iterator<OrderBean> it = collezione.iterator();
		
		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement(sql2);
			
			while (it.hasNext()) {
				OrderBean ordine = (OrderBean) it.next();
				ProductBean bean = ordine.getProdotto();
				
				ps.setInt(1, bean.getCodice());
				ps.setString(2, email);
				
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					bean.setVotazione(rs.getDouble(1));
				}
				ordine.setProdotto(bean);
				lista.add(ordine);
			}
			return lista;
		}
		catch (Exception e){
			return null;
		}
		finally {
			if (con != null) {
				DriverManagerConnectionPool.releaseConnection(con);
			}
			if (ps != null) {
				ps.close();
			}
		}
	}
	
	public synchronized Collection<OrderBean> addOrdini(CartBean cart, String email, Collection<OrderBean> lista) throws SQLException {
		Collection<ProductBean> carrello = cart.getCarrello();
		String sql = "SELECT o.codiceOrdine, o.dataAcquisto, p.emailVenditore FROM Ordine AS o, Prodotto AS p WHERE o.codiceProdotto = p.codice AND o.codiceProdotto = ? AND o.emailCliente = ? ORDER BY o.dataAcquisto DESC";
		Connection con = null;
		PreparedStatement ps = null;
		
		if (carrello != null && carrello.size() != 0) {
			Iterator<ProductBean> it = carrello.iterator();
			while (it.hasNext()) {
				ProductBean bean = (ProductBean) it.next();
				OrderBean order = new OrderBean();
				try {
					con = DriverManagerConnectionPool.getConnection();
					ps = con.prepareStatement(sql);
					ps.setInt(1, bean.getCodice());
					ps.setString(2, email);
					
					ResultSet rs = ps.executeQuery();
					if (rs.next()) {
						order.setProdotto(bean);
						order.setCodice(rs.getInt(1));
						order.setData(rs.getDate(2));
						order.setVenditore(rs.getString(3));
						
						lista.add(order);
					}
					return lista;
				}
				catch (Exception e) {
					return lista;
				}
				finally {
					if (con != null) {
						DriverManagerConnectionPool.releaseConnection(con);
					}
					if (ps != null) {
						ps.close();
					}
				}
			}
		}
		return lista;
	}
	
	public synchronized Collection<OrderBean> addRecensione(int codiceProdotto, String email, int votazione, String text, Collection<OrderBean> ordini) {
		String sql = "INSERT INTO Recensione (codiceProdotto, emailCliente, votazione, testo, dataRecensione) VALUES (?, ?, ?, ?, current_date())";
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, codiceProdotto);
			ps.setString(2, email);
			ps.setInt(3, votazione);
			ps.setString(4, text);
			
			ps.executeUpdate();
			con.commit();
			
			
			if (ordini != null && ordini.size() != 0) {
				Iterator<?> it = ordini.iterator();
				while (it.hasNext()) {
					OrderBean ord = (OrderBean) it.next();
					ProductBean prod = ord.getProdotto();
					
					if (prod.getCodice() == codiceProdotto) {
						prod.setVotazione(votazione);
					}
				}
			}
			return ordini;
		}
		catch (Exception e) {
			return ordini;
		}
		finally {
			if (con != null) {
				DriverManagerConnectionPool.releaseConnection(con);
			}
		}
	}
	
	public synchronized Collection<OrderBean> addSingleRecensione(String email, RecensioneBean bean, Collection<OrderBean> ordini) {
		String sql = "UPDATE Recensione SET votazione = ?, testo = ? WHERE codiceProdotto = ? AND emailCliente = ?";
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, bean.getVoto());
			ps.setString(2, bean.getTesto());
			ps.setInt(3, bean.getCodiceProdotto());
			ps.setString(4, email);
			
			ps.executeUpdate();
			con.commit();
			
			
			if (ordini != null && ordini.size() != 0) {
				Iterator<?> it = ordini.iterator();
				while (it.hasNext()) {
					OrderBean ord = (OrderBean) it.next();
					ProductBean prod = ord.getProdotto();
					
					if (prod.getCodice() == bean.getCodiceProdotto()) {
						prod.setVotazione(bean.getVoto());
					}
				}
			}
			return ordini;
		}
		catch (Exception e) {
			return ordini;
		}
		finally {
			if (con != null) {
				DriverManagerConnectionPool.releaseConnection(con);
			}
		}
	}
	
	public synchronized Collection<OrderBean> eliminaSingleRecensione(String email, int codice, Collection<OrderBean> ordini) {
		String sql = "DELETE FROM Recensione WHERE codiceProdotto = ? AND emailCliente = ?";
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			con = DriverManagerConnectionPool.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, codice);
			ps.setString(2, email);
			
			ps.executeUpdate();
			con.commit();
			
			
			if (ordini != null && ordini.size() != 0) {
				Iterator<?> it = ordini.iterator();
				while (it.hasNext()) {
					OrderBean ord = (OrderBean) it.next();
					ProductBean prod = ord.getProdotto();
					
					if (prod.getCodice() == codice) {
						prod.setVotazione(0);
					}
				}
			}
			return ordini;
		}
		catch (Exception e) {
			return ordini;
		}
		finally {
			if (con != null) {
				DriverManagerConnectionPool.releaseConnection(con);
			}
		}
	}
}
