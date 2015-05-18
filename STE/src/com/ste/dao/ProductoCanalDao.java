package com.ste.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.ste.beans.ProductoCanal;
import com.ste.persistence.PMF;
import com.ste.utils.Utils;

public class ProductoCanalDao {
	public static ProductoCanalDao getInstance() {
		return new ProductoCanalDao();
	}
	
	public synchronized void createProductoCanal(ProductoCanal producto) {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		producto.setName(Utils.toUpperCase(producto.getName()));
		
		try{
			pm.makePersistent(producto);			
		} catch(Exception e) {
			e.printStackTrace();
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
	
	@SuppressWarnings("unchecked")
	public List<ProductoCanal> getAllProductosFor(String Visibilidad) {

		List<ProductoCanal> ProductoCanales;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query q = pm.newQuery("select from " + ProductoCanal.class.getName() +" where "+Visibilidad+" == true");
		q.setOrdering("name asc");
		ProductoCanales = (List<ProductoCanal>) q.execute();
		pm.close();

		return ProductoCanales;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductoCanal> getEstadoImpForNameAndEnty(String name, String enty) {

		List<ProductoCanal> EstadoImplementacions;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery("select from " + ProductoCanal.class.getName()+" WHERE name=='"+Utils.toUpperCase(name)+"' && "+enty+"==true");
		EstadoImplementacions = (List<ProductoCanal>) q.execute();
		pm.close();

		return EstadoImplementacions;
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
