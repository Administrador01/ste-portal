package com.ste.dao;

import java.text.ParseException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.ste.beans.Servicio;
import com.ste.persistence.PMF;
import com.ste.utils.Utils;

public class ServicioDao {
	
	public static ServicioDao getInstance() {
		return new ServicioDao();
	}
	
	@SuppressWarnings("unchecked")
	public List<Servicio> getAllServicios() {

		List<Servicio> Servicios;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Servicio.class.getName());		
		Servicios = (List<Servicio>) q.execute();
		
		
		pm.close();

		return Servicios;
	}
	
	
	public synchronized void createServicio(Servicio imp, String usermail) {
		PersistenceManager pm = PMF.get().getPersistenceManager();	
		CounterDao cDao = CounterDao.getInstance();

		try{
			
			ServicioDao impDao = ServicioDao.getInstance();
		
			try {
				pm.makePersistent(imp);
			} finally {}
			
			
		}finally {
			pm.close();
			
		}
	}
}
