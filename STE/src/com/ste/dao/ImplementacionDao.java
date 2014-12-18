package com.ste.dao;

import java.text.ParseException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;




import com.ste.beans.Cliente;
import com.ste.beans.Implementacion;
import com.ste.counters.Counter;
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
	
	
	public synchronized void createImplementacion(Implementacion imp, String usermail) {
		PersistenceManager pm = PMF.get().getPersistenceManager();	
		CounterDao cDao = CounterDao.getInstance();

		try{
			
			ImplementacionDao impDao = ImplementacionDao.getInstance();
			imp.setFechAlta((Utils.dateConverter(imp.getStrFechAlta())));
			if (imp.getStrFechContratacion()!=null){
				imp.setFechContratacion(Utils.dateConverter(imp.getStrFechContratacion()));
			}
			
			if (imp.getStrFechSubida()!=null){
				imp.setFechSubida(Utils.dateConverter(imp.getStrFechSubida()));
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
