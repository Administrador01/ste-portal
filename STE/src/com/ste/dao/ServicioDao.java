package com.ste.dao;

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
	
	public synchronized void createServicio(Servicio servicio) {
		PersistenceManager pm = PMF.get().getPersistenceManager();	

		servicio.setName(Utils.toUpperCase(servicio.getName()));
		servicio.setTipo(Utils.toUpperCase(servicio.getTipo()));
		
		try{
			pm.makePersistent(servicio);			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			pm.close();
		}
	}
	public void deleteAll(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ServicioDao sDao = ServicioDao.getInstance();
		List<Servicio> servicios =sDao.getAllServicios();
		for (Servicio s :servicios){
			pm.deletePersistent(pm.getObjectById(s.getClass(),s.getKey().getId()));
		}
	}
	
	public Servicio getImplementacionById(long l) {
		Servicio c;
		try {		
			PersistenceManager pManager = PMF.get().getPersistenceManager();
			Servicio servicio_temp = pManager.getObjectById(Servicio.class, l);
	
			c = pManager.detachCopy(servicio_temp);
			pManager.close();

		}catch(Exception e){
			c=null;
		}
		return c;
	}
	
	public List<Servicio> getServicioByName(String name) {
		List<Servicio> servicios;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query q = pm.newQuery("select from " + Servicio.class.getName()+" where name == '"+Utils.toUpperCase(name)+"'");		
		servicios = (List<Servicio>) q.execute();
		pm.close();

		return servicios;
	}
	
}
