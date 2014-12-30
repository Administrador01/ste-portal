package com.ste.dao;

import java.text.ParseException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.ste.beans.Implementacion;
import com.ste.persistence.PMF;
import com.ste.utils.Utils;

public class ImplementacionDao {
	public static ImplementacionDao getInstance() {
		return new ImplementacionDao();
	}
	
	@SuppressWarnings("unchecked")
	public List<Implementacion> getAllImplementaciones() {

		List<Implementacion> Implementacions;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Implementacion.class.getName());		
		Implementacions = (List<Implementacion>) q.execute();
		
		
		pm.close();

		return Implementacions;
	}
	
public Implementacion getImplementacionById(long l) {
		
		Implementacion c;
		try{			
		
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Implementacion implementacion_temp = pManager.getObjectById(Implementacion.class, l);

		c = pManager.detachCopy(implementacion_temp);
		pManager.close();

		}catch(Exception e){
			c=null;
		}
		return c;
	}
@SuppressWarnings("unchecked")
public List<Implementacion> getImplementacionByClientId(long l) {
	
	
	List<Implementacion> implementaciones;
	PersistenceManager pm = PMF.get().getPersistenceManager();
	
	
	
	
	String query = "select from " + Implementacion.class.getName()+" where cliente_id == "+l;
	
	Query q = pm.newQuery(query);//.setFilter(propertyFilter);

	implementaciones = (List<Implementacion>) q.execute();
	
	
	pm.close();

	return implementaciones;
}	

	public synchronized void createImplementacion(Implementacion imp, String usermail) {
		PersistenceManager pm = PMF.get().getPersistenceManager();	
		

		try{
			
			
			imp.setFecha_alta((Utils.dateConverter(imp.getStr_fecha_alta())));
			if (imp.getStr_fech_contratacion()!=null && imp.getStr_fech_contratacion()!=""){
				imp.setFech_contratacion(Utils.dateConverter(imp.getStr_fech_contratacion()));
			}
			
			if (imp.getStr_fech_subida()!=null && imp.getStr_fech_subida()!=""){
				imp.setFech_subida(Utils.dateConverter(imp.getStr_fech_subida()));
			}			
			if (imp.getKey()==null){
					
					//campo id opcional	
			}
				
				
			try {
				pm.makePersistent(imp);
			} finally {}
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pm.close();
			
		}
	}
}
