package com.ste.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.ste.beans.EstadoImplementacion;
import com.ste.persistence.PMF;

public class EstadoImplementacionDao {
	public static EstadoImplementacionDao getInstance() {
		return new EstadoImplementacionDao();
	}
	
	public synchronized void createEstadoImplementacion(EstadoImplementacion imp) {
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
	public List<EstadoImplementacion> getAllEstadoImplementacions() {

		List<EstadoImplementacion> EstadoImplementacions;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + EstadoImplementacion.class.getName());
		q.setOrdering("orden asc");
		EstadoImplementacions = (List<EstadoImplementacion>) q.execute();
		
		
		pm.close();

		return EstadoImplementacions;
	}
	
	@SuppressWarnings("unchecked")
	public List<EstadoImplementacion> getEstadoImpForName(String name) {

		List<EstadoImplementacion> EstadoImplementacions;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + EstadoImplementacion.class.getName()+" WHERE name=='"+name+"'");
		EstadoImplementacions = (List<EstadoImplementacion>) q.execute();
		
		
		pm.close();

		return EstadoImplementacions;
	}
	
	public void deleteAll(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		EstadoImplementacionDao sDao = EstadoImplementacionDao.getInstance();
		List<EstadoImplementacion> servicios =sDao.getAllEstadoImplementacions();
		for (EstadoImplementacion s :servicios){
			pm.deletePersistent(pm.getObjectById(s.getClass(),s.getKey().getId()));
		}


	}
}