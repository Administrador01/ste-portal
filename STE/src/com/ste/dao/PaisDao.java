package com.ste.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.ste.beans.Pais;
import com.ste.persistence.PMF;
import com.ste.utils.Utils;

public class PaisDao {
	public static PaisDao getInstance() {
		return new PaisDao();
	}
	
	public synchronized void createPais(Pais pais) {
		PersistenceManager pm = PMF.get().getPersistenceManager();	

		pais.setName(Utils.toUpperCase(pais.getName()));		
		try{
			pm.makePersistent(pais);			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			pm.close();
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Pais> getAllPaises() {
		List<Pais> Paises;
		PersistenceManager pm = PMF.get().getPersistenceManager();
				
		Query q = pm.newQuery("select from " + Pais.class.getName());	
		q.setOrdering("name asc");
		Paises = (List<Pais>) q.execute();
		pm.close();

		return Paises;
	}
	
	public void deleteAll(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		PaisDao sDao = PaisDao.getInstance();
		List<Pais> servicios =sDao.getAllPaises();
		for (Pais s :servicios){
			pm.deletePersistent(pm.getObjectById(s.getClass(),s.getKey().getId()));
		}
	}
	
}
