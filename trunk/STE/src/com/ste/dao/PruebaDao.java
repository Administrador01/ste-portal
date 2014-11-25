package com.ste.dao;

import java.text.ParseException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.ste.beans.Prueba;
import com.ste.counters.Counter;
import com.ste.persistence.PMF;
import com.ste.utils.Utils;

public class PruebaDao {

	public static PruebaDao getInstance() {
		return new PruebaDao();
	}
	
	public Prueba getPruebabyId(long l) {
		
		Prueba s;
		try{			
		
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Prueba prueba_temp = pManager.getObjectById(Prueba.class, l);

		s = pManager.detachCopy(prueba_temp);
		pManager.close();

		}catch(Exception e){
			s=null;
		}
		
		return s;
		
		
	}

	public void createPrueba(Prueba s) {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			
			//Conversi'on de las fechas de string a tipo date
			s.setFecha_estado(Utils.dateConverter(s.getStr_fecha_estado()));

			
			if (s.getKey()==null){
				CounterDao cDao = CounterDao.getInstance();
				
				Counter count = cDao.getCounterByName("prueba");
				
				String num = String.format("%08d", count.getValue());
				
				s.setId_prueba("PRU"+num);
				
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
	
	public void deletePrueba(Prueba s) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistent(pm.getObjectById(s.getClass(), s.getKey().getId()));
		pm.close();

	}
	
	@SuppressWarnings("unchecked")
	public List<Prueba> getAllPruebas() {

		List<Prueba> pruebas;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Prueba.class.getName());		
		q.setOrdering("fecha_estado desc");
		pruebas = (List<Prueba>) q.execute();
		
		
		pm.close();

		return pruebas;
	}
}
