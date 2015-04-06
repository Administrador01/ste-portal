package com.ste.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;






import com.ste.beans.Cliente;
import com.ste.beans.Implementacion;
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

			
			boolean flag = false;
			
			

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
		q.setDatastoreReadTimeoutMillis(30000000);
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
	
	

	
	public Cliente getClientByTestId(long l) {
		
		PruebaDao pruDao = PruebaDao.getInstance();
		Prueba prueba = pruDao.getPruebabyId(l);
		ImplementacionDao impDao = ImplementacionDao.getInstance();
		

		return impDao.getClienteByImpId(Long.parseLong(prueba.getImp_id()));
	}
	
	public Implementacion getImplementacionByTestId(long l) {
		
		PruebaDao pruDao = PruebaDao.getInstance();
		Prueba prueba = pruDao.getPruebabyId(l);
		ImplementacionDao impDao = ImplementacionDao.getInstance();
		

		return impDao.getImplementacionById(Long.parseLong(prueba.getImp_id()));
	}	

	
	@SuppressWarnings("unchecked")
	public List<Prueba> getAllPruebasByClientId(String clientID) {

		PruebaDao pruDao = PruebaDao.getInstance();
		List<Prueba> pruebas = new ArrayList();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		ImplementacionDao impDao = ImplementacionDao.getInstance();
		List<Implementacion> implementaciones = impDao.getImplementacionByClientId(Long.parseLong(clientID));
		
		for(Implementacion imp : implementaciones){
			pruebas.addAll(pruDao.getAllPruebasByImpId(imp.getKey().getId()));
		}
		pm.close();

		return pruebas;
	}
	
	@SuppressWarnings("unchecked")
	public List<Prueba> getAllPruebasByImpId(long impID) {

		List<Prueba> pruebas;
		PersistenceManager pm = PMF.get().getPersistenceManager();

		String query = "select from " + Prueba.class.getName()+" where imp_id == '"+impID+"' && erased==false";
		
		Query q = pm.newQuery(query);//.setFilter(propertyFilter);

		pruebas = (List<Prueba>) q.execute();

		
		pm.close();

		return pruebas;
	}
	
	@SuppressWarnings("unchecked")
	public boolean existPruebaByClientId (String clientID) {

		PruebaDao pruDao = PruebaDao.getInstance();
		List<Prueba> pruebas = pruDao.getAllPruebasByClientId(clientID);
		
		boolean existe = true;
		if(pruebas.isEmpty())existe = false;

		return existe;
	}
	@SuppressWarnings("unchecked")
	public List<Prueba> getPruebasByResultado (String Resultado) {

		List<Prueba> pruebas;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		
		String query = "select from " + Prueba.class.getName();
		Query q = pm.newQuery(query);
		
		q.setFilter("resultado == Resultado"+" && erased==false");
		q.declareParameters("String Resultado");
	
		pruebas = (List<Prueba>) q.execute(Resultado);
		
		pm.close();

		return pruebas;
	}	
	@SuppressWarnings("unchecked")
	public List<Prueba> getPruebasSinceDate (Date fechaDesde) {

		List<Prueba> pruebas;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		
		String query = "select from " + Prueba.class.getName();
		Query q = pm.newQuery(query);
		
		q.setFilter("fecha_estado >= fechaDesde"+" && erased==false");
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
		
		q.setFilter("fecha_estado <= fechaHasta"+" && erased==false");
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
		
		q.setFilter("fecha_estado >= fechaDesde && fecha_estado <= fechaHasta"+" && erased==false");
		q.declareParameters("java.util.Date fechaDesde , java.util.Date fechaHasta");
		
		pruebas = (List<Prueba>) q.execute(fechaDesde,fechaHasta);
		
		pm.close();

		return pruebas;
	}
	
	
	public List<Prueba> getPruebasSinceDateByClientId (String clientID,Date fechaDesde) {

		List<Prueba> pruebas = new ArrayList<Prueba>();
		
		PruebaDao pruDao = PruebaDao.getInstance();
		
		List<Prueba> pruebasByImp = pruDao.getAllPruebasByClientId(clientID);
		List<Prueba> pruebasByDate = pruDao.getPruebasSinceDate(fechaDesde);
		for(Prueba pruebaByImp:pruebasByImp){
			for(Prueba pruebaByDate:pruebasByDate){
				if(pruebaByDate.getKey().getId()==pruebaByImp.getKey().getId())pruebas.add(pruebaByDate);
			}
		}

		return pruebas;
	}
	
	public List<Prueba> getPruebasUntilDateByClientId (String clientID,Date fechaHasta) {

		List<Prueba> pruebas = new ArrayList<Prueba>();
		
		PruebaDao pruDao = PruebaDao.getInstance();
		
		List<Prueba> pruebasByImp = pruDao.getAllPruebasByClientId(clientID);
		List<Prueba> pruebasByDate = pruDao.getPruebasUntilDate(fechaHasta);
		for(Prueba pruebaByImp:pruebasByImp){
			for(Prueba pruebaByDate:pruebasByDate){
				if(pruebaByDate.getKey().getId()==pruebaByImp.getKey().getId())pruebas.add(pruebaByDate);
			}
		}

		return pruebas;
	}
	
	public List<Prueba> getPruebasBetweenDatesByClientId (String clientID,Date fechaDesde,Date fechaHasta) {

		List<Prueba> pruebas = new ArrayList<Prueba>();
		
		PruebaDao pruDao = PruebaDao.getInstance();
		
		List<Prueba> pruebasByImp = pruDao.getAllPruebasByClientId(clientID);
		List<Prueba> pruebasByDate = pruDao.getPruebasBetweenDates(fechaDesde,fechaHasta);
		for(Prueba pruebaByImp:pruebasByImp){
			for(Prueba pruebaByDate:pruebasByDate){
				if(pruebaByDate.getKey().getId()==pruebaByImp.getKey().getId())pruebas.add(pruebaByDate);
			}
		}

		return pruebas;
	}	
	/*
	@SuppressWarnings("unchecked")
	public List<Prueba> getPruebasSinceDateByImpId (String impID,Date fechaDesde) {

		List<Prueba> pruebas;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		String query = "select from " + Prueba.class.getName();
		Query q = pm.newQuery(query);
		
		q.setFilter("fecha_estado >= fechaDesde && cliente_id==impID"+" && erased==false");
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
		
		q.setFilter("fecha_estado <= fechaHasta && imp_id==impID"+" && erased==false");
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
		
		q.setFilter("fecha_estado >= fechaDesde && fecha_estado <= fechaHasta && imp_id==impID"+" && erased==false");
		q.declareParameters("java.util.Date fechaDesde, java.util.Date fechaHasta, String impID");
		
		pruebas = (List<Prueba>) q.execute(fechaDesde,fechaHasta,impID);
		
		pm.close();

		return pruebas;
	}
	
*/
	
	@SuppressWarnings("unchecked")
	public List<Prueba> getPruebasByResultado (String resultado,Date fechaDesde,Date fechaHasta) {

		List<Prueba> pruebas;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		String query = "select from " + Prueba.class.getName();
		Query q = pm.newQuery(query);
		
		q.setFilter("fecha_estado >= fechaDesde && fecha_estado <= fechaHasta && resultado==Resultado"+" && erased==false");
		q.declareParameters("java.util.Date fechaDesde, java.util.Date fechaHasta, String Resultado");
		
		pruebas = (List<Prueba>) q.execute(fechaDesde,fechaHasta,resultado);
		
		pm.close();

		return pruebas;
	}
	
	@SuppressWarnings("unchecked")
	public List<Prueba> getPruebasByFichero (String fichero,Date fechaDesde,Date fechaHasta) {

		List<Prueba> pruebas;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		String query = "select from " + Prueba.class.getName();
		Query q = pm.newQuery(query);
		
		q.setFilter("fecha_estado >= fechaDesde && fecha_estado <= fechaHasta && fichero==Fichero"+" && erased==false");
		q.declareParameters("java.util.Date fechaDesde, java.util.Date fechaHasta, String Fichero");
		
		pruebas = (List<Prueba>) q.execute(fechaDesde,fechaHasta,fichero);
		
		pm.close();

		return pruebas;
	}
}
