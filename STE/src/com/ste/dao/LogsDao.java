package com.ste.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.ste.beans.Log;
import com.ste.beans.Prueba;
//import com.ste.beans.Servicio;
import com.ste.persistence.PMF;
import com.ste.utils.Utils;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

public class LogsDao {

	
	public static final int DATA_SIZE = 10;

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public static LogsDao getInstance() {
		return new LogsDao();
	}

	public void createLog(Log log){
		Date fecha = new Date();
		String fecha_str = Utils.dateConverterToStr(fecha);
		
		log.setFecha_str(fecha_str);
		log.setFecha(fecha);
		
		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			pm.makePersistent(log);
		} finally {
			pm.close();
		}
	}
	@SuppressWarnings("unchecked")
	public List<Log> getAllLogs() {

		List<Log> logs;
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		
		Query q = pm.newQuery("select from " + Log.class.getName());		
		q.setOrdering("fecha desc");
		logs = (List<Log>) q.execute();
		
		pm.close();

		return logs;
	}
	@SuppressWarnings("unchecked")
	public List<Log> getLogsByLastWeek(Integer page) {
		Date date = new Date();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -7);
		date.setTime(cal.getTimeInMillis());
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Log");
		Filter finalFilter = new FilterPredicate("fecha",FilterOperator.GREATER_THAN_OR_EQUAL,date);
		q.setFilter(finalFilter);
		q.addSort("fecha",SortDirection.DESCENDING);
		List<Entity> entities = null;
		FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
		if(page != null) {
			Integer offset = page * DATA_SIZE;
			fetchOptions.limit(DATA_SIZE);	
			fetchOptions.offset(offset);
		}
		entities = datastore.prepare(q).asList(fetchOptions);
		
		List<Log> logs = new ArrayList<>();
		
		for (Entity result : entities) {			
			logs.add(buildLog(result));
		}
		
		return logs;
		
		/*
		Date date = new Date();
		List<Log> logs;
		PersistenceManager pm = PMF.get().getPersistenceManager();	
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		cal.add(Calendar.DAY_OF_MONTH, -7);
		date.setTime(cal.getTimeInMillis());
		
		Query q = pm.newQuery("select from " + Log.class.getName() + " where fecha > date");		
		q.setOrdering("fecha desc");
		
		q.declareImports("import java.util.Date");
		q.declareParameters("Date date");
		
		
		logs = (List<Log>) q.execute(date);
		
		pm.close();

		return logs;
		
		*/
	}
	@SuppressWarnings("unchecked")
	public List<Log> getLogsByLastMonth(Integer page) {

		
		/*
		Date date = new Date();
		List<Log> logs;
		PersistenceManager pm = PMF.get().getPersistenceManager();	
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		cal.add(Calendar.DAY_OF_MONTH, -30);
		date.setTime(cal.getTimeInMillis());
		
		Query q = pm.newQuery("select from " + Log.class.getName() + " where fecha > date");		
		q.setOrdering("fecha desc");
		
		q.declareImports("import java.util.Date");
		q.declareParameters("Date date");
		
		
		logs = (List<Log>) q.execute(date);
		
		pm.close();

		return logs;
		
		*/
		
		
		Date date = new Date();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -30);
		date.setTime(cal.getTimeInMillis());
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Log");
		Filter finalFilter = new FilterPredicate("fecha",FilterOperator.GREATER_THAN_OR_EQUAL,date);
		q.setFilter(finalFilter);
		q.addSort("fecha",SortDirection.DESCENDING);
		List<Entity> entities = null;
		FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
		if(page != null) {
			Integer offset = page * DATA_SIZE;
			fetchOptions.limit(DATA_SIZE);	
			fetchOptions.offset(offset);
		}
		entities = datastore.prepare(q).asList(fetchOptions);
		
		List<Log> logs = new ArrayList<>();
		
		for (Entity result : entities) {			
			logs.add(buildLog(result));
		}
		
		return logs;
	}
	@SuppressWarnings("unchecked")
	public List<Log> getLogsByLast3Months(Integer page) {
/*
		Date date = new Date();
		List<Log> logs;
		PersistenceManager pm = PMF.get().getPersistenceManager();	
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		cal.add(Calendar.DAY_OF_MONTH, -91);
		date.setTime(cal.getTimeInMillis());
		
		Query q = pm.newQuery("select from " + Log.class.getName() + " where fecha > date");		
		q.setOrdering("fecha desc");
		
		q.declareImports("import java.util.Date");
		q.declareParameters("Date date");
		
		
		logs = (List<Log>) q.execute(date);
		
		pm.close();

		return logs;*/
		
		Date date = new Date();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -91);
		date.setTime(cal.getTimeInMillis());
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Log");
		Filter finalFilter = new FilterPredicate("fecha",FilterOperator.GREATER_THAN_OR_EQUAL,date);
		q.setFilter(finalFilter);
		q.addSort("fecha",SortDirection.DESCENDING);
		List<Entity> entities = null;
		FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
		if(page != null) {
			Integer offset = page * DATA_SIZE;
			fetchOptions.limit(DATA_SIZE);	
			fetchOptions.offset(offset);
		}
		entities = datastore.prepare(q).asList(fetchOptions);
		
		List<Log> logs = new ArrayList<>();
		
		for (Entity result : entities) {			
			logs.add(buildLog(result));
		}
		
		return logs;
		
	}
	
	
	private Log buildLog(Entity entity) {
		Log log = new Log();
		
		log.setKey(entity.getKey());
		
		String accion = getString(entity,"accion");
		if(accion!=null){
			log.setAccion(accion);
		}
		
		String entidad = getString(entity,"entidad");
		if(entidad!=null){
			log.setEntidad(entidad);
		}
		String fecha_str = getString(entity,"fecha_str");
		if(fecha_str!=null){
			log.setFecha_str(fecha_str);
		}
		
		String nombre_entidad = getString(entity,"nombre_entidad");
		if(nombre_entidad!=null){
			log.setNombre_entidad(nombre_entidad);
		}
		
		String usuario = getString(entity,"usuario");
		if(usuario!=null){
			log.setUsuario(usuario);
		}
		String usuario_mail = getString(entity,"usuario_mail");
		if(usuario_mail!=null){
			log.setUsuario_mail(usuario_mail);
		}
		
		
		
		return log;
	}
	private String getString(Entity e, String field) {
		try {
			return (String) e.getProperty(field);
		}
		catch(Exception exp) {
			return null;
		}
	}
	
	private Date getDate(Entity e, String field) {
		try {
			return (Date) e.getProperty(field);
		}
		catch(Exception exp) {
			return null;
		}
	}
}
