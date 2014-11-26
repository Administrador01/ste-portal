package com.ste.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.ste.beans.Log;
//import com.ste.beans.Servicio;
import com.ste.persistence.PMF;
import com.ste.utils.Utils;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class LogsDao {


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
	
	public List<Log> getAllLogs() {

		List<Log> logs;
		PersistenceManager pm = PMF.get().getPersistenceManager();		
		
		Query q = pm.newQuery("select from " + Log.class.getName());		
		q.setOrdering("fecha desc");
		logs = (List<Log>) q.execute();
		
		pm.close();

		return logs;
	}
	
	public List<Log> getLogsByLastWeek() {

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
	}
	
	public List<Log> getLogsByLastMonth() {

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
	}
	
	public List<Log> getLogsByLast3Months() {

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

		return logs;
	}
	
}
