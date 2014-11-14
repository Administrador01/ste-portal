package com.ste.dao;

import java.text.ParseException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;


import com.ste.beans.Soporte;
import com.ste.beans.User;
import com.ste.counters.Counter;
import com.ste.persistence.PMF;
import com.ste.utils.Utils;

public class SoporteDao {

	public static SoporteDao getInstance() {
		return new SoporteDao();
	}
	
	public Soporte getSoportebyId(long l) {
		
		Soporte s;
		try{			
		
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Soporte soporte_temp = pManager.getObjectById(Soporte.class, l);

		s = pManager.detachCopy(soporte_temp);
		pManager.close();

		}catch(Exception e){
			s=null;
		}
		
		return s;
		
		
	}

	public void createSoporte(Soporte s) {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			s.setFecha_inicio(Utils.dateConverter(s.getStr_fecha_inicio()));
			s.setFecha_fin(Utils.dateConverter(s.getStr_fecha_fin()));
			
			if (s.getKey()==null){
				CounterDao cDao = CounterDao.getInstance();
				
				Counter count = cDao.getCounterByName("soporte");
				
				String num = String.format("%08d", count.getValue());
				
				s.setId_prueba("STE"+num);
				
				
				
				CounterDao countDao = CounterDao.getInstance();
				countDao.increaseCounter(count);
			}
			
			pm.makePersistent(s);
			
			

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		
		
		Query q = pm.newQuery("select from " + Soporte.class.getName());		
		q.setOrdering("fecha_inicio desc");
		soportes = (List<Soporte>) q.execute();
		
		
		pm.close();

		return soportes;
	}
}
