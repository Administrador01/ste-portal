package com.ste.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;


import com.ste.beans.Soporte;
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
	public synchronized void updateSoporte(Soporte s) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.makePersistent(s);
		pm.close();
	}
	public synchronized void createSoporte(Soporte s) {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			
			SoporteDao sopDao = SoporteDao.getInstance();	

			List<Soporte> sopor_arr = sopDao.getAllSoportes();
			boolean flag = false;
			for (Soporte sopor : sopor_arr){
				
				if(sopor.getDetalles().equals(s.getDetalles())&&
				   sopor.getProducto_canal().equals(s.getProducto_canal())&&
				   sopor.getEstado().equals(s.getEstado())&&
				   sopor.getStr_fecha_inicio().equals(s.getStr_fecha_inicio())&&
				   sopor.getSolucion().equals(s.getSolucion())&&
				   sopor.getCliente_id().equals(s.getCliente_id())&&
				   sopor.getTipo_soporte().equals(s.getTipo_soporte())&&
				   sopor.getTipo_servicio().equals(s.getTipo_servicio())){
						flag = true;
				}

			}
			if(!flag){
			
				//Conversi'on de las fechas de string a tipo date
				s.setFecha_inicio(Utils.dateConverter(s.getStr_fecha_inicio()));
				if (s.getStr_fecha_fin()!=""){
					s.setFecha_fin(Utils.dateConverter(s.getStr_fecha_fin()));
				}
				
				if (s.getKey()==null){
					CounterDao cDao = CounterDao.getInstance();
					
					Counter count = cDao.getCounterByName("soporte");
					
					String num = String.format("%08d", count.getValue());
					
					s.setId_soporte("STE"+num);
					
					CounterDao countDao = CounterDao.getInstance();
					countDao.increaseCounter(count);
				}
				
				pm.makePersistent(s);
			}
			

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pm.close();
		}
	}
	
	public void deleteSoporteDefinitivo(Soporte s) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistent(pm.getObjectById(s.getClass(), s.getKey().getId()));
		pm.close();

	}
	
	public void deleteSoporte(Soporte s) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		s.setErased(true);
		pm.makePersistent(s);
		pm.close();

	}
	
	
	@SuppressWarnings("unchecked")
	public List<Soporte> getAllSoportes() {

		List<Soporte> soportes;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Soporte.class.getName()+" where erased == false");		
		q.setOrdering("fecha_inicio desc");
		soportes = (List<Soporte>) q.execute();
		
		
		pm.close();

		return soportes;
	}
	
	@SuppressWarnings("unchecked")
	public List<Soporte> getAllDelSoportes() {

		List<Soporte> soportes;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Soporte.class.getName()+" where erased == true");		
		q.setOrdering("fecha_inicio desc");
		soportes = (List<Soporte>) q.execute();
		
		
		pm.close();

		return soportes;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Soporte> getAllEvenDelSoportes() {

		List<Soporte> soportes;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Soporte.class.getName());		
		q.setOrdering("fecha_inicio desc");
		soportes = (List<Soporte>) q.execute();
		
		
		pm.close();

		return soportes;
	}
	
	@SuppressWarnings("unchecked")
	public List<Soporte> getAllSoportesByClientId(String clientID) {

		List<Soporte> soportes;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		
		String query = "select from " + Soporte.class.getName()+" where cliente_id == '"+clientID+"'";
		
		Query q = pm.newQuery(query);//.setFilter(propertyFilter);

		soportes = (List<Soporte>) q.execute();

		
		pm.close();

		return soportes;
	}
	
	@SuppressWarnings("unchecked")
	public boolean existSoporteByClientId(String clientID) {

		List<Soporte> soportes;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String query = "select from " + Soporte.class.getName()+" where cliente_id == '"+clientID+"' && erased==false";
		Query q = pm.newQuery(query);//.setFilter(propertyFilter);
		soportes = (List<Soporte>) q.execute();
		boolean existe = true;
		if (soportes.isEmpty())existe= false;
		
		pm.close();

		return existe;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<Soporte> getSoportesSinceDate (Date fechaDesde) {

		List<Soporte> soportes;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		
		String query = "select from " + Soporte.class.getName();
		Query q = pm.newQuery(query);
		
		q.setFilter("fecha_inicio >= fechaDesde"+" && erased==false");
		q.declareParameters("java.util.Date fechaDesde");
	
		soportes = (List<Soporte>) q.execute(fechaDesde);
		
		pm.close();

		return soportes;
	}
	@SuppressWarnings("unchecked")
	public List<Soporte> getSoportesUntilDate (Date fechaHasta) {

		List<Soporte> soportes;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		
		String query = "select from " + Soporte.class.getName();
		Query q = pm.newQuery(query);
		
		q.setFilter("fecha_inicio <= fechaHasta"+" && erased==false");
		q.declareParameters("java.util.Date fechaHasta");
	
		soportes = (List<Soporte>) q.execute(fechaHasta);
		
		pm.close();

		return soportes;
	}
	
	@SuppressWarnings("unchecked")
	public List<Soporte> getSoportesBetweenDates (Date fechaDesde,Date fechaHasta) {

		List<Soporte> soportes;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		
		String query = "select from " + Soporte.class.getName();
		Query q = pm.newQuery(query);
		
		q.setFilter("fecha_inicio >= fechaDesde && fecha_inicio <= fechaHasta"+" && erased==false");
		q.declareParameters("java.util.Date fechaDesde , java.util.Date fechaHasta");
		
		soportes = (List<Soporte>) q.execute(fechaDesde,fechaHasta);
		
		pm.close();

		return soportes;
	}
	
	
	public List<Soporte> getSoportesSinceDateByClientId (String clientID,Date fechaDesde) {

		List<Soporte> soportes = new ArrayList<Soporte>();
		
		SoporteDao sopDao = SoporteDao.getInstance();
		
		List<Soporte> soportesByImp = sopDao.getAllSoportesByClientId(clientID);
		List<Soporte> soportesByDate = sopDao.getSoportesSinceDate(fechaDesde);
		for(Soporte soporteByImp:soportesByImp){
			for(Soporte soporteByDate:soportesByDate){
				if(soporteByDate.getKey().getId()==soporteByImp.getKey().getId())soportes.add(soporteByDate);
			}
		}

		return soportes;
	}
	
	public List<Soporte> getSoportesUntilDateByClientId (String clientID,Date fechaHasta) {

		List<Soporte> soportes = new ArrayList<Soporte>();
		
		SoporteDao sopDao = SoporteDao.getInstance();
		
		List<Soporte> soportesByImp = sopDao.getAllSoportesByClientId(clientID);
		List<Soporte> soportesByDate = sopDao.getSoportesUntilDate(fechaHasta);
		for(Soporte soporteByImp:soportesByImp){
			for(Soporte soporteByDate:soportesByDate){
				if(soporteByDate.getKey().getId()==soporteByImp.getKey().getId())soportes.add(soporteByDate);
			}
		}

		return soportes;
	}
	
	public List<Soporte> getSoportesBetweenDatesByClientId (String clientID,Date fechaDesde,Date fechaHasta) {

		List<Soporte> soportes = new ArrayList<Soporte>();
		
		SoporteDao sopDao = SoporteDao.getInstance();
		
		List<Soporte> soportesByImp = sopDao.getAllSoportesByClientId(clientID);
		List<Soporte> soportesByDate = sopDao.getSoportesBetweenDates(fechaDesde,fechaHasta);
		for(Soporte soporteByImp:soportesByImp){
			for(Soporte soporteByDate:soportesByDate){
				if(soporteByDate.getKey().getId()==soporteByImp.getKey().getId())soportes.add(soporteByDate);
			}
		}

		return soportes;
	}	
}
