package com.ste.dao;

import java.text.ParseException;
import java.util.Date;
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
	
	public synchronized void updatePrueba(Prueba s) {

		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.makePersistent(s);
		pm.close();
	}

	public synchronized void createPrueba(Prueba s) {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			
			PruebaDao pruDao = PruebaDao.getInstance();	

			List<Prueba> prueb_arr = pruDao.getAllPruebas();
			boolean flag = false;
			
			
			for (Prueba prub : prueb_arr){
				
				if(prub.getDetalles().equals(s.getDetalles())&&
				   prub.getEntorno().equals(s.getEntorno())&&
				   prub.getEstado().equals(s.getEstado())&&
				   prub.getStr_fecha_estado().equals(s.getStr_fecha_estado())&&
				   prub.getSolucion().equals(s.getSolucion())&&
				   prub.getImp_id().equals(s.getImp_id())&&
				   prub.getProducto().equals(s.getProducto())&&
				   prub.getReferencia().equals(s.getReferencia())&&
				   prub.getTipo_servicio().equals(s.getTipo_servicio())){
						flag = true;
				}
					
			}
			if(!flag){
				//Conversi'on de las fechas de string a tipo date
				s.setFecha_estado(Utils.dateConverter(s.getStr_fecha_estado()));
				if(!s.getFecha_inicio_str().equals("")||!s.getFecha_inicio_str().equals(null)){
					s.setFecha_inicio(Utils.dateConverter(s.getFecha_inicio_str()));
				}
				
				if (s.getKey()==null){
					CounterDao cDao = CounterDao.getInstance();
					
					Counter count = cDao.getCounterByName("prueba");
					
					String num = String.format("%08d", count.getValue());
					
					s.setId_prueba("PRU"+num);
					
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

	public void deletePrueba(Prueba s) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistent(pm.getObjectById(s.getClass(), s.getKey().getId()));
		pm.close();

	}
	
	@SuppressWarnings("unchecked")
	public List<Prueba> getAllPruebas() {

		List<Prueba> pruebas;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Prueba.class.getName()+" where erased == false");		
		q.setOrdering("fecha_estado desc");
		pruebas = (List<Prueba>) q.execute();
		
		
		pm.close();

		return pruebas;
	}
	@SuppressWarnings("unchecked")
	public List<Prueba> getAllDelPruebas() {

		List<Prueba> pruebas;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Prueba.class.getName()+" where erased == true");		
		q.setOrdering("fecha_estado desc");
		pruebas = (List<Prueba>) q.execute();
		
		
		pm.close();

		return pruebas;
	}
	
	@SuppressWarnings("unchecked")
	public List<Prueba> getAllPruebasEvenDel() {

		List<Prueba> pruebas;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Prueba.class.getName());		
		q.setOrdering("fecha_estado desc");
		pruebas = (List<Prueba>) q.execute();
		
		
		pm.close();

		return pruebas;
	}
	
	@SuppressWarnings("unchecked")
	public List<Prueba> getAllPruebasByClientId(String clientID) {

		List<Prueba> pruebas;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		
		String query = "select from " + Prueba.class.getName()+" where cliente_id == '"+clientID+"'";
		
		Query q = pm.newQuery(query);//.setFilter(propertyFilter);

		pruebas = (List<Prueba>) q.execute();

		
		pm.close();

		return pruebas;
	}
	
	@SuppressWarnings("unchecked")
	public boolean existPruebaByClientId (String clientID) {

		List<Prueba> pruebas;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		
		String query = "select from " + Prueba.class.getName()+" where cliente_id == '"+clientID+"'";
		
		Query q = pm.newQuery(query);//.setFilter(propertyFilter);

		pruebas = (List<Prueba>) q.execute();
		
		boolean existe = true;
		if(pruebas.isEmpty())existe = false;
		
		pm.close();

		return existe;
	}
	
	@SuppressWarnings("unchecked")
	public List<Prueba> getPruebasSinceDate (Date fechaDesde) {

		List<Prueba> pruebas;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		
		String query = "select from " + Prueba.class.getName();
		Query q = pm.newQuery(query);
		
		q.setFilter("fecha_estado >= fechaDesde");
		q.declareParameters("java.util.Date fechaDesde");
	
		pruebas = (List<Prueba>) q.execute(fechaDesde);
		
		pm.close();

		return pruebas;
	}
	@SuppressWarnings("unchecked")
	public List<Prueba> getPruebasUntilDate (Date fechaHasta) {

		List<Prueba> pruebas;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		
		String query = "select from " + Prueba.class.getName();
		Query q = pm.newQuery(query);
		
		q.setFilter("fecha_estado <= fechaHasta");
		q.declareParameters("java.util.Date fechaHasta");
	
		pruebas = (List<Prueba>) q.execute(fechaHasta);
		
		pm.close();

		return pruebas;
	}
	
	@SuppressWarnings("unchecked")
	public List<Prueba> getPruebasBetweenDates (Date fechaDesde,Date fechaHasta) {

		List<Prueba> pruebas;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		
		String query = "select from " + Prueba.class.getName();
		Query q = pm.newQuery(query);
		
		q.setFilter("fecha_estado >= fechaDesde && fecha_estado <= fechaHasta");
		q.declareParameters("java.util.Date fechaDesde , java.util.Date fechaHasta");
		
		pruebas = (List<Prueba>) q.execute(fechaDesde,fechaHasta);
		
		pm.close();

		return pruebas;
	}
	
	@SuppressWarnings("unchecked")
	public List<Prueba> getPruebasSinceDateByImpId (String impID,Date fechaDesde) {

		List<Prueba> pruebas;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		String query = "select from " + Prueba.class.getName();
		Query q = pm.newQuery(query);
		
		q.setFilter("fecha_estado >= fechaDesde && cliente_id==impID");
		q.declareParameters("java.util.Date fechaDesde,String impID");
	
		pruebas = (List<Prueba>) q.execute(fechaDesde,impID);
		
		pm.close();

		return pruebas;
	}
	@SuppressWarnings("unchecked")
	public List<Prueba> getPruebasUntilDateByImpId (String impID,Date fechaHasta) {

		List<Prueba> pruebas;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		String query = "select from " + Prueba.class.getName();
		Query q = pm.newQuery(query);
		
		q.setFilter("fecha_estado <= fechaHasta && imp_id==impID");
		q.declareParameters("java.util.Date fechaHasta,String impID");
	
		pruebas = (List<Prueba>) q.execute(fechaHasta,impID);
		
		pm.close();

		return pruebas;
	}
	
	@SuppressWarnings("unchecked")
	public List<Prueba> getPruebasBetweenDatesByImpId (String impID,Date fechaDesde,Date fechaHasta) {

		List<Prueba> pruebas;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		String query = "select from " + Prueba.class.getName();
		Query q = pm.newQuery(query);
		
		q.setFilter("fecha_estado >= fechaDesde && fecha_estado <= fechaHasta && imp_id==impID");
		q.declareParameters("java.util.Date fechaDesde, java.util.Date fechaHasta, String impID");
		
		pruebas = (List<Prueba>) q.execute(fechaDesde,fechaHasta,impID);
		
		pm.close();

		return pruebas;
	}
	
	@SuppressWarnings("unchecked")
	public List<Prueba> getPruebasByResultado (String resultado,Date fechaDesde,Date fechaHasta) {

		List<Prueba> pruebas;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		String query = "select from " + Prueba.class.getName();
		Query q = pm.newQuery(query);
		
		q.setFilter("fecha_estado >= fechaDesde && fecha_estado <= fechaHasta && Resultado==resultado");
		q.declareParameters("java.util.Date fechaDesde, java.util.Date fechaHasta, String resultado");
		
		pruebas = (List<Prueba>) q.execute(fechaDesde,fechaHasta,resultado);
		
		pm.close();

		return pruebas;
	}
}
