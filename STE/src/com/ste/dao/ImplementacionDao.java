package com.ste.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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
		
		
		Query q = pm.newQuery("select from " + Implementacion.class.getName()+" where erased == false");		
		Implementacions = (List<Implementacion>) q.execute();
		
		
		pm.close();

		return Implementacions;
	}
	
	@SuppressWarnings("unchecked")
	public List<Implementacion> getAllDelImplementaciones() {

		List<Implementacion> Implementacions;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Implementacion.class.getName()+" where erased == true");		
		Implementacions = (List<Implementacion>) q.execute();
		
		
		pm.close();

		return Implementacions;
	}
	
	@SuppressWarnings("unchecked")
	public List<Implementacion> getImplementacionesForClient(Cliente cliente) {
		
		
		
		List<Implementacion> Implementacions;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Implementacion.class.getName()+" where erased == false && cliente_id == "+ cliente.getKey().getId());		
		Implementacions = (List<Implementacion>) q.execute();
		
		
		pm.close();

		return Implementacions;
	}
	
	@SuppressWarnings("unchecked")
	public List<Implementacion> getAllImplementacionesBetweenDates(Date fechaDesde, Date fechaHasta) {

		List<Implementacion> Implementacions;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Implementacion.class.getName());		
		
		q.setFilter("fecha_alta >= fechaDesde && fecha_alta <= fechaHasta"+" && erased==false");
		q.declareParameters("java.util.Date fechaDesde , java.util.Date fechaHasta");
		Implementacions = (List<Implementacion>) q.execute(fechaDesde,fechaHasta);
		
		
		pm.close();

		return Implementacions;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Implementacion> getAllSubidasBetweenDates(Date fechaDesde, Date fechaHasta) {

		List<Implementacion> Implementacions;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Implementacion.class.getName());		
		
		q.setFilter("fecha_alta >= fechaDesde && fecha_alta <= fechaHasta"+" && erased==false");
		q.declareParameters("java.util.Date fechaDesde , java.util.Date fechaHasta");
		Implementacions = (List<Implementacion>) q.execute(fechaDesde,fechaHasta);
		pm.close();
		
		ArrayList<Implementacion> implementaciones = new ArrayList<Implementacion>();
		
		for(Implementacion imp: Implementacions){
			if(!imp.getStr_fech_subida().equals("")&&!imp.getStr_fech_subida().equals(null)){
				implementaciones.add(imp);
			}
		}
		
		
		return implementaciones;
	}
	
	@SuppressWarnings("unchecked")
	public Integer getNumberForVariableValue(String variable, String value) {

		List<Implementacion> Implementacions;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Implementacion.class.getName());		
		
		q.setFilter(variable+" == value &&  erased==false");
		q.declareParameters("String value");
		Implementacions = (List<Implementacion>) q.execute(value);
		
		
		pm.close();

		return Implementacions.size();
	}
	
	@SuppressWarnings("unchecked")
	public Integer getNumberForVariableValueBetween(String variable, String value,Date fechaDesde, Date fechaHasta) {

		List<Implementacion> Implementacions;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Implementacion.class.getName());		
		
		q.setFilter(variable+" == value &&  erased==false && fecha_alta >= fechaDesde && fecha_alta <= fechaHasta");
		q.declareParameters("String value, java.util.Date fechaDesde , java.util.Date fechaHasta");
		Implementacions = (List<Implementacion>) q.execute(value,fechaDesde,fechaHasta);
		
		
		pm.close();

		return Implementacions.size();
	}
	
	@SuppressWarnings("unchecked")
	public List<Implementacion> getImplementacionesForVariableValue(String variable, String value) {

		List<Implementacion> Implementacions;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Implementacion.class.getName());		
		
		q.setFilter(variable+" == value &&  erased==false");
		q.declareParameters("String value");
		Implementacions = (List<Implementacion>) q.execute(value);
		
		
		pm.close();

		return Implementacions;
	}
	
	
public Implementacion getImplementacionById(long l) {
		
		Implementacion c;
		try{			
		
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Implementacion implementacion_temp = pManager.getObjectById(Implementacion.class, l);

		c = pManager.detachCopy(implementacion_temp);
		pManager.close();

		}catch(Exception e){
			c=null;
		}
		return c;
	}

public String getNombreClienteByImpId(long l) {
	
	ImplementacionDao impDao = ImplementacionDao.getInstance();
	Implementacion implementacion = impDao.getImplementacionById(l);
	
	ClienteDao cliDao = ClienteDao.getInstance();
	Cliente cliente = cliDao.getClientebyId(implementacion.getCliente_id());
	return cliente.getNombre();
}

public Cliente getClienteByImpId(long l) {
	
	ImplementacionDao impDao = ImplementacionDao.getInstance();
	Implementacion implementacion = impDao.getImplementacionById(l);
	
	ClienteDao cliDao = ClienteDao.getInstance();
	Cliente cliente = cliDao.getClientebyId(implementacion.getCliente_id());
	return cliente;
}
@SuppressWarnings("unchecked")
public List<Implementacion> getImplementacionByClientId(long l) {
	
	
	List<Implementacion> implementaciones;
	PersistenceManager pm = PMF.get().getPersistenceManager();
	
	
	
	
	String query = "select from " + Implementacion.class.getName()+" where cliente_id == "+l;
	
	Query q = pm.newQuery(query);//.setFilter(propertyFilter);

	implementaciones = (List<Implementacion>) q.execute();
	
	
	pm.close();

	return implementaciones;
}	


	public synchronized void createImplementacionRaw(Implementacion imp){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.makePersistent(imp);
		pm.close();
	}
	public synchronized void createImplementacion(Implementacion imp, String usermail) {
		PersistenceManager pm = PMF.get().getPersistenceManager();	
		

		try{
			
			
			imp.setFecha_alta((Utils.dateConverter(imp.getStr_fecha_alta())));
			if (imp.getStr_fech_contratacion()!=null && imp.getStr_fech_contratacion()!=""){
				imp.setFech_contratacion(Utils.dateConverter(imp.getStr_fech_contratacion()));
			}
			
			if (imp.getStr_fech_subida()!=null && imp.getStr_fech_subida()!=""){
				imp.setFech_subida(Utils.dateConverter(imp.getStr_fech_subida()));
			}		
			if (imp.getKey()==null){
				CounterDao contDao = CounterDao.getInstance();
				Counter contador = contDao.getCounterByName("implementacion");
				ClienteDao cliDao = ClienteDao.getInstance();
				Cliente cliente = cliDao.getClientebyId(imp.getCliente_id());
				String impID = "IMP_"+cliente.getId_cliente().substring(9)+"_"+contador.getValue();
				imp.setId_implementacion(impID);
				
				contador.setValue(contador.getValue()+1);
				contDao.createCounter(contador);
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
