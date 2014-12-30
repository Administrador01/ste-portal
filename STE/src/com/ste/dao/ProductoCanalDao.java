package com.ste.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.ste.beans.Pais;
import com.ste.beans.ProductoCanal;
import com.ste.persistence.PMF;

public class ProductoCanalDao {
	public static ProductoCanalDao getInstance() {
		return new ProductoCanalDao();
	}
	
	public synchronized void createProductoCanal(ProductoCanal imp) {
		PersistenceManager pm = PMF.get().getPersistenceManager();	

		try{
			
			//ServicioDao impDao = ServicioDao.getInstance();
		
			try {
				pm.makePersistent(imp);
			} finally {}
			
			
		}finally {
			pm.close();
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductoCanal> getAllProductos() {

		List<ProductoCanal> ProductoCanales;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + ProductoCanal.class.getName());
		q.setOrdering("name asc");
		ProductoCanales = (List<ProductoCanal>) q.execute();
		
		
		pm.close();

		return ProductoCanales;
	}
	
	public void deleteAll(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ProductoCanalDao sDao = ProductoCanalDao.getInstance();
		List<ProductoCanal> servicios =sDao.getAllProductos();
		for (ProductoCanal s :servicios){
			pm.deletePersistent(pm.getObjectById(s.getClass(),s.getKey().getId()));
		}
	}
}
