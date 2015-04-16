package com.ste.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.ste.beans.Cliente;
import com.ste.beans.Implementacion;
import com.ste.beans.Prueba;
import com.ste.counters.Counter;
import com.ste.persistence.PMF;
import com.ste.utils.Utils;

public class PruebaDao {
	
	public static final int DATA_SIZE = 10;

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
		//q.setDatastoreReadTimeoutMillis(30000000);
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
		
		List<Long> impIds = new ArrayList<>();
		for(Implementacion imp : implementaciones){
			impIds.add(new Long(imp.getKey().getId()));
			//pruebas.addAll(pruDao.getAllPruebasByImpId(imp.getKey().getId()));
		}
		pruebas.addAll(pruDao.getAllPruebasByImpIdPaged(impIds, null));
		/*for(Implementacion imp : implementaciones){
			pruebas.addAll(pruDao.getAllPruebasByImpIdPaged(imp.getKey().getId(), null));
		}*/
		pm.close();

		return pruebas;
	}
	
	/*@SuppressWarnings("unchecked")
	public List<Prueba> getAllPruebasByImpId(long impID) {

		List<Prueba> pruebas;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String query = "select from " + Prueba.class.getName()+" where imp_id == '"+impID+"' && erased==false";
		Query q = pm.newQuery(query);
		pruebas = (List<Prueba>) q.execute();		
		pm.close();

		return pruebas;
	}*/
	
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
		
	
	public List<Prueba> getPruebasPaged(Integer page) {
		List<Prueba> pruebas;
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Prueba");
		
		List<Entity> entities = null;
		FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
		if(page != null) {
			Integer offset = page * DATA_SIZE;
			fetchOptions.limit(DATA_SIZE);	
			fetchOptions.offset(offset);
		}
		entities = datastore.prepare(q).asList(fetchOptions);
				
		pruebas = new ArrayList<>();	
		for (Entity result : entities) {			
			pruebas.add(buildPrueba(result));
		}
		
		return pruebas;
		
		/*PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query q = pm.newQuery("select from " + Prueba.class.getName());		
		q.setOrdering("fecha_estado desc");
		long offset = page * DATA_SIZE;
		q.setRange(offset, offset + DATA_SIZE);
		pruebas = (List<Prueba>) q.execute();
		pm.close();*/
		
		//return pruebas;		
	}
	
	
	public List<Prueba> getPruebasByAllParam(String fecha, String cliente, String servicio , String estado, String producto, String entorno, String desde, String hasta, String premium, Integer page) throws ParseException {
		List<Prueba> pruebas = null;
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Prueba");
		
		List<Filter> finalFilters = new ArrayList<>();

		Date dateDesde= null;
		Date dateHasta= null;
		
		/*TODO no se puede consultar sobre mas de una coluna con esta operacion
		 * ver pagina
		 * 
		 * http://gae-java-persistence.blogspot.de/2009/12/queries-with-and-in-filters.html
		 * 
		 * */
		int filters = 0;
		if(!cliente.equals("")){
			cliente = cliente.toUpperCase();
			filters++;
		}
		if(!fecha.equals("")){
			
			filters++;
		}
		if(!servicio.equals("")){
			filters++;
		}
		if(!estado.equals("")){
			Character a = estado.charAt(0);
			String estado2= a.toString().toUpperCase()+estado.substring(1);
			estado = estado2;
			filters++;
		}
		if(!entorno.equals("")){
			Character a = entorno.charAt(0);
			String entorno2= a.toString().toUpperCase()+entorno.substring(1);
			entorno = entorno2;
			filters++;
		}
		boolean auxFechDesde= Utils.isThisDateValid(desde, "dd/MM/yyyy");
		if(!desde.equals("")&& auxFechDesde){
			dateDesde = Utils.dateConverter(desde);
			filters++;
		}
		boolean auxFechHasta = Utils.isThisDateValid(hasta, "dd/MM/yyyy");
		if(!hasta.equals("") && auxFechHasta){
			dateHasta = Utils.dateConverter(hasta);
			filters++;
		}
		if(!premium.equals("Todos")){
			filters++;
		}
		if(!producto.equals("")){

			filters++;
		}
		if(filters<=1){
			if(!cliente.equals("")){
				finalFilters.add(new FilterPredicate("client_name", FilterOperator.GREATER_THAN_OR_EQUAL, cliente));
				finalFilters.add(new FilterPredicate("client_name", FilterOperator.LESS_THAN, cliente+"\ufffd"));
			}
			
			if(!desde.equals("")&& auxFechDesde){
				finalFilters.add(new FilterPredicate("fecha_inicio", FilterOperator.GREATER_THAN_OR_EQUAL, dateDesde));
			}
			if(!hasta.equals("")&& auxFechHasta){
				finalFilters.add(new FilterPredicate("fecha_inicio", FilterOperator.LESS_THAN_OR_EQUAL, dateHasta));
			}
			if(!fecha.equals("")){
				finalFilters.add(new FilterPredicate("str_fecha_estado", FilterOperator.GREATER_THAN_OR_EQUAL, fecha));
				finalFilters.add(new FilterPredicate("str_fecha_estado", FilterOperator.LESS_THAN, fecha+"\ufffd"));
			}
			if(!servicio.equals("")){
				finalFilters.add(new FilterPredicate("tipo_servicio", FilterOperator.GREATER_THAN_OR_EQUAL, servicio));
				finalFilters.add(new FilterPredicate("tipo_servicio", FilterOperator.LESS_THAN, servicio+"\ufffd"));
			}
			if(!entorno.equals("")){
				finalFilters.add(new FilterPredicate("entorno", FilterOperator.GREATER_THAN_OR_EQUAL,entorno));
				finalFilters.add(new FilterPredicate("entorno", FilterOperator.LESS_THAN, entorno+"\ufffd"));
			}
			if(!estado.equals("")){
				finalFilters.add(new FilterPredicate("estado", FilterOperator.GREATER_THAN_OR_EQUAL, estado));
				finalFilters.add(new FilterPredicate("estado", FilterOperator.LESS_THAN, estado+"\ufffd"));
			}
			if(!producto.equals("")){
				finalFilters.add(new FilterPredicate("producto", FilterOperator.GREATER_THAN_OR_EQUAL, producto));
				finalFilters.add(new FilterPredicate("producto", FilterOperator.LESS_THAN,producto+"\ufffd"));
			}
			if(!premium.equals("Todos")){
				finalFilters.add(new FilterPredicate("premium", FilterOperator.EQUAL,premium));
			}
			Filter finalFilter = null;
			if(finalFilters.size()>1) finalFilter = CompositeFilterOperator.and(finalFilters);
			if(finalFilters.size()==1) finalFilter = finalFilters.get(0);
			if(finalFilters.size()!=0)q.setFilter(finalFilter);
			
			
			List<Entity> entities = null;
			FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
			if(page != null) {
				Integer offset = page * DATA_SIZE;
				fetchOptions.limit(DATA_SIZE);	
				fetchOptions.offset(offset);
			}
			entities = datastore.prepare(q).asList(fetchOptions);
			pruebas = new ArrayList<>();	
			for (Entity result : entities) {			
				pruebas.add(buildPrueba(result));
			}
			Prueba pruebaPagin = new Prueba();
			pruebaPagin.setDetalles("0");
			pruebas.add(pruebaPagin);
			
			
			
			//pruebaPagin.setDetalles();
		}else{
//			List<Entity> clienteEntities = new ArrayList<Entity>();
//			List<Entity> fechaEntities = new ArrayList<Entity>();
//			List<Entity> servicioEntities = new ArrayList<Entity>();
//			List<Entity> entornoEntities = new ArrayList<Entity>();
//			List<Entity> estadoEntities = new ArrayList<Entity>();
//			List<Entity> productoEntities = new ArrayList<Entity>();
//			List<Entity> entities = new ArrayList<Entity>();
			List<List<Entity>> Entities = new ArrayList<List<Entity>>();
			
			if(!cliente.equals("")){
				q = new com.google.appengine.api.datastore.Query("Prueba");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("client_name", FilterOperator.GREATER_THAN_OR_EQUAL, cliente));
				finalFilters.add(new FilterPredicate("client_name", FilterOperator.LESS_THAN, cliente+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
//				clienteEntities =datastore.prepare(q).asList(fetchOptions);
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!fecha.equals("")){
				q = new com.google.appengine.api.datastore.Query("Prueba");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("str_fecha_estado", FilterOperator.GREATER_THAN_OR_EQUAL, fecha));
				finalFilters.add(new FilterPredicate("str_fecha_estado", FilterOperator.LESS_THAN, fecha+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				
//				fechaEntities =datastore.prepare(q).asList(fetchOptions);
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!servicio.equals("")){
				q = new com.google.appengine.api.datastore.Query("Prueba");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("tipo_servicio", FilterOperator.GREATER_THAN_OR_EQUAL, servicio));
				finalFilters.add(new FilterPredicate("tipo_servicio", FilterOperator.LESS_THAN, servicio+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				
//				servicioEntities =datastore.prepare(q).asList(fetchOptions);
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!entorno.equals("")){
				q = new com.google.appengine.api.datastore.Query("Prueba");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("entorno", FilterOperator.GREATER_THAN_OR_EQUAL,entorno));
				finalFilters.add(new FilterPredicate("entorno", FilterOperator.LESS_THAN, entorno+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				q.setFilter(finalFilter);
//				entornoEntities =datastore.prepare(q).asList(fetchOptions);
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!estado.equals("")){
				q = new com.google.appengine.api.datastore.Query("Prueba");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("estado", FilterOperator.GREATER_THAN_OR_EQUAL,estado));
				finalFilters.add(new FilterPredicate("estado", FilterOperator.LESS_THAN, estado+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				q.setFilter(finalFilter);
//				estadoEntities =datastore.prepare(q).asList(fetchOptions);
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!producto.equals("")){
				q = new com.google.appengine.api.datastore.Query("Prueba");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("producto", FilterOperator.GREATER_THAN_OR_EQUAL, producto));
				finalFilters.add(new FilterPredicate("producto", FilterOperator.LESS_THAN,producto+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				q.setFilter(finalFilter);
//				productoEntities =datastore.prepare(q).asList(fetchOptions);
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			
			if(!desde.equals("")&& auxFechDesde){
				q = new com.google.appengine.api.datastore.Query("Prueba");
				Filter filtro = new FilterPredicate("fecha_inicio", FilterOperator.GREATER_THAN_OR_EQUAL, dateDesde);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				q.setFilter(filtro);
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!hasta.equals("")&& auxFechHasta){
				q = new com.google.appengine.api.datastore.Query("Prueba");
				Filter filtro = new FilterPredicate("fecha_inicio", FilterOperator.LESS_THAN_OR_EQUAL, dateHasta);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				q.setFilter(filtro);
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!premium.equals("Todos")){
				q = new com.google.appengine.api.datastore.Query("Prueba");
				Filter filtro= new FilterPredicate("premium", FilterOperator.EQUAL,premium);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				q.setFilter(filtro);
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			List<Entity> pruebasFinal = new ArrayList<Entity>();
			int lowRowsIndex = 0;
			int lowRowsNumber = Entities.get(0).size();
			
			for(int i=1;i<Entities.size();i++){
				if(lowRowsNumber>Entities.get(i).size()){
					lowRowsIndex=i;
					lowRowsNumber=Entities.get(i).size();
				}
			}
			
			List<Entity> indexDel = new ArrayList<Entity>();
			pruebasFinal = Entities.get(lowRowsIndex);
			for(int i=0;i<Entities.size();i++){
				if(i!=lowRowsIndex){
					int limite = pruebasFinal.size();
					for (int j = 0 ; j<limite;j++) {				
							if(!Entities.get(i).contains(pruebasFinal.get(j))){
								Entity auxEnty = pruebasFinal.get(j);
								if(!indexDel.contains(auxEnty))indexDel.add(auxEnty);
							}
					}
				}
			}
			
			pruebasFinal.removeAll(indexDel);
			
			pruebas = new ArrayList<Prueba>();
			int pruebasPages = pruebasFinal.size();
			for (int i = page*10; i < (page*10)+10&&i<pruebasFinal.size();i++) {			
				pruebas.add(buildPrueba(pruebasFinal.get(i)));
			}
			Prueba pages = new Prueba();
			pages.setDetalles(Integer.toString(pruebasPages));
			pruebas.add(pages);
		}
		
		
		return pruebas;
	}
	
	@SuppressWarnings("unchecked")
	public List<Prueba> getAllPruebasByClientIdPaged(String clientID, Integer page) {

		ImplementacionDao impDao = ImplementacionDao.getInstance();
		List<Implementacion> implementaciones = impDao.getImplementacionByClientId(Long.parseLong(clientID));
		
		List<Long> impIds = new ArrayList<>();
		for(Implementacion imp : implementaciones){
			impIds.add(new Long(imp.getKey().getId()));
		}
		PruebaDao pruDao = PruebaDao.getInstance();
		List<Prueba> pruebas = pruDao.getAllPruebasByImpIdPaged(impIds, page);
		
		return pruebas;
	}
	
	@SuppressWarnings("unchecked")
	public List<Prueba> getAllPruebasByImpIdPaged(List<Long> impIds, Integer page) {
		List<Prueba> pruebas;
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Prueba");
		/*		
		List<Filter> impIdSubFilters = new ArrayList<>();
		for(Long id : impIds) {
			impIdSubFilters.add(new FilterPredicate("imp_id", FilterOperator.EQUAL, id.toString()));
		}		
		Filter impIdFilter = CompositeFilterOperator.or(impIdSubFilters);
		*/
		int i = 0;
		Collection<String> impIdsCollect = new ArrayList<String>();
		impIdsCollect.add("0");
		for(Long id : impIds) {
			impIdsCollect.add(impIds.get(i).toString());
			i++;
		}
		List<Filter> finalFilters = new ArrayList<>();
		finalFilters.add(new FilterPredicate("erased", FilterOperator.EQUAL, false));
		finalFilters.add(new FilterPredicate("imp_id", FilterOperator.IN, impIdsCollect));
		//finalFilters.add(impIdFilter);
		Filter finalFilter = CompositeFilterOperator.and(finalFilters);
		q.setFilter(finalFilter);
		
		List<Entity> entities = null;
		FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
		if(page != null) {
			Integer offset = page * DATA_SIZE;
			fetchOptions.limit(DATA_SIZE);	
			fetchOptions.offset(offset);
		}
		entities = datastore.prepare(q).asList(fetchOptions);
				
		pruebas = new ArrayList<>();	
		for (Entity result : entities) {			
			pruebas.add(buildPrueba(result));
		}
		
		return pruebas;
	}
	
	private Prueba buildPrueba(Entity entity) {
		Prueba prueba = new Prueba();
		
		prueba.setKey(entity.getKey());
		
		String client_name = getString(entity, "client_name");
		if(client_name != null) {
			prueba.setClient_name(client_name);
		}
		String detalles = getString(entity, "detalles");
		if(detalles != null) {
			prueba.setDetalles(detalles);
		}
		String entorno = getString(entity, "entorno");
		if(entorno != null) {
			prueba.setEntorno(entorno);
		}
		prueba.setErased((boolean) entity.getProperty("erased"));
		String estado = getString(entity, "estado");
		if(estado != null) {
			prueba.setEstado(estado);
		}
		Date fecha_estado = getDate(entity, "fecha_estado");
		if(fecha_estado != null) {
			prueba.setFecha_estado(fecha_estado);
		}
		Date fecha_inicio = getDate(entity, "fecha_inicio");
		if(fecha_inicio != null) {
			prueba.setFecha_inicio(fecha_inicio);
		}
		String fecha_inicio_str = getString(entity, "fecha_inicio_str");
		if(fecha_inicio_str != null) {
			prueba.setFecha_inicio_str(fecha_inicio_str);
		}
		String fichero = getString(entity, "fichero");
		if(fichero != null) {
			prueba.setFichero(fichero);
		}
		String id_prueba = getString(entity, "id_prueba");
		if(id_prueba != null) {
			prueba.setId_prueba(id_prueba);
		}
		String imp_id = getString(entity, "imp_id");
		if(imp_id != null) {
			prueba.setImp_id(imp_id);
		}
		String peticionario = getString(entity, "peticionario");
		if(peticionario != null) {
			prueba.setPeticionario(peticionario);
		}
		String premium = getString(entity, "premium");
		if(premium != null) {
			prueba.setPremium(premium);
		}
		String producto = getString(entity, "producto");
		if(producto != null) {
			prueba.setProducto(producto);
		}
		String referencia = getString(entity, "referencia");
		if(referencia != null) {
			prueba.setReferencia(referencia);
		}
		String resultado = getString(entity, "resultado");
		if(resultado != null) {
			prueba.setResultado(resultado);
		}
		String solucion = getString(entity, "solucion");
		if(solucion != null) {
			prueba.setSolucion(solucion);
		}
		String str_fecha_estado = getString(entity, "str_fecha_estado");
		if(str_fecha_estado != null) {
			prueba.setStr_fecha_estado(str_fecha_estado);
		}
		String tipo_servicio = getString(entity, "tipo_servicio");
		if(tipo_servicio != null) {
			prueba.setTipo_servicio(tipo_servicio);
		}
		return prueba;
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
