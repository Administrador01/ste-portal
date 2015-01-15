package com.ste.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.ste.beans.Estado;
import com.ste.persistence.PMF;

public class EstadoDao {
	public static EstadoDao getInstance() {
		return new EstadoDao();
	}
	
	public synchronized void createEstado(Estado imp) {
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
	public List<Estado> getAllEstados() {

		List<Estado> Estados;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Estado.class.getName());
		q.setOrdering("orden asc");
		Estados = (List<Estado>) q.execute();
		
		
		pm.close();

		return Estados;
	}
	
	public void deleteAll(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		EstadoDao sDao = EstadoDao.getInstance();
		List<Estado> servicios =sDao.getAllEstados();
		for (Estado s :servicios){
			pm.deletePersistent(pm.getObjectById(s.getClass(),s.getKey().getId()));
		}


	}
}
