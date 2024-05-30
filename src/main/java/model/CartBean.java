package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class CartBean implements Serializable {
	private static final long serialVersionUID = -4830856131240011211L;

	public CartBean() {
		carrello = new LinkedList<ProductBean>();
	}
	
	public void setCarrello(ProductBean newProdotto) {
		ProductBean bean = this.retriveByKey(newProdotto.getCodice());
		if (bean == null) {
			carrello.add(newProdotto);
		}
		else {
			this.addQuantity(bean.getCodice());
		}
	}
	
	public void addProduct(ProductBean newProdotto) {
		carrello.add(newProdotto);
	}
	
	public ProductBean retriveByKey(int codiceProdotto) {
		for (Iterator<ProductBean> i = this.getCarrello().iterator(); i.hasNext(); ) {
			ProductBean bean = (ProductBean) i.next();
			if (bean.getCodice() == codiceProdotto)
				return bean;
		}
		return null;
	}
	
	public void addQuantity(int codiceProdotto) {
		for (Iterator<ProductBean> i = this.getCarrello().iterator(); i.hasNext(); ) {
			ProductBean bean = (ProductBean) i.next();
			if (bean.getCodice() == codiceProdotto)
				bean.addQuantity();
		}
	}
	
	public void decreaseQuantity(int codiceProdotto) {
		for (Iterator<ProductBean> i = this.getCarrello().iterator(); i.hasNext(); ) {
			ProductBean bean = (ProductBean) i.next();
			if (bean.getCodice() == codiceProdotto)
				bean.decreaseQuantity();
		}
	}
	
	public Collection<ProductBean> getCarrello() {
		return carrello;
	}
	
	public void removeItem(int codiceProdotto) {
		if (carrello.size() > 0) {
			carrello.removeIf(a -> a.getCodice() == codiceProdotto);
		}
	}
	
	public boolean isEmpty() {
		return carrello.isEmpty();
	}
	
	public void removeAllItems() {
		carrello.removeAll(carrello);
	}
	
	public void setLista(Collection<ProductBean> lista) {
		carrello = lista;
	}
	
	private Collection<ProductBean> carrello;
}




