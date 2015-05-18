package com.ste.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.ste.beans.TipoServicio;
import com.ste.persistence.PMF;
import com.ste.utils.Utils;

public class TipoServicioDao {
	
	public static TipoServicioDao getInstance() {
		return new TipoServicioDao();
	}
	
	public synchronized void createTipoServicio(TipoServicio tipoServicio) {
		PersistenceManager pm = PMF.get().getPersistenceManager();	

		tipoServicio.setNme(Utils.toUpperCase(tipoServicio.getName()));
		
		try{
			pm.makePersistent(tipoServicio);			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			pm.close();
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoServicio> getAllServicios() {

		List<TipoServicio> Servicios;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query q = pm.newQuery("select from " + TipoServicio.class.getName());
		q.setOrdering("name asc");
		Servicios = (List<TipoServicio>) q.execute();
		pm.close();

		return Servicios;
	}
	
	public void deleteAll(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		TipoServicioDao sDao = TipoServicioDao.getInstance();
		List<TipoServicio> servicios =sDao.getAllServicios();
		for (TipoServicio s :servicios){
			pm.deletePersistent(pm.getObjectById(s.getClass(),s.getKey().getId()));
		}


	}
}
