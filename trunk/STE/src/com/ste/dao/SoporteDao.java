package com.ste.dao;

import java.util.List;

import javax.jdo.PersistenceManager;

import com.ste.beans.Soporte;
import com.ste.beans.User;
import com.ste.persistence.PMF;

public class SoporteDao {

	public static SoporteDao getInstance() {
		return new SoporteDao();
	}

	public void createSoporte(Soporte s) {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			pm.makePersistent(s);
		} finally {
			pm.close();
		}
	}
	
	public void deleteSoporte(Soporte s) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistent(pm.getObjectById(s.getClass(), s.getKey().getId()));
		pm.close();

	}
	
	@SuppressWarnings("unchecked")
	public List<Soporte> getAllSoportes() {

		List<Soporte> soportes;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String queryStr = "select from " + Soporte.class.getName();
		soportes = (List<Soporte>) pm.newQuery(queryStr).execute();
		
		pm.close();

		return soportes;
	}
}
