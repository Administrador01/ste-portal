package com.ste.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;







import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.ste.beans.Cliente;
import com.ste.beans.Prueba;
import com.ste.beans.Soporte;
import com.ste.counters.Counter;
import com.ste.persistence.PMF;
import com.ste.utils.Utils;

public class SoporteDao {
	public static final int DATA_SIZE = 10; 
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
				
				/*if(sopor.getDetalles().equals(s.getDetalles())&&
				   sopor.getProducto_canal().equals(s.getProducto_canal())&&
				   sopor.getEstado().equals(s.getEstado())&&
				   sopor.getStr_fecha_inicio().equals(s.getStr_fecha_inicio())&&
				   sopor.getSolucion().equals(s.getSolucion())&&
				   sopor.getCliente_id().equals(s.getCliente_id())&&
				   sopor.getTipo_soporte().equals(s.getTipo_soporte())&&
				   sopor.getTipo_servicio().equals(s.getTipo_servicio())){
						flag = true;
				}*/

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
	public List<Soporte> getSoportesByTipoClientEstado(String tipoClient, String estado,Date fechaDesde,Date fechaHasta) {

		List<Soporte> soportes;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		
		String query = "select from " + Soporte.class.getName();
		Query q = pm.newQuery(query);
		if(tipoClient.equals("ANY")){
			q.setFilter("fecha_inicio >= fechaDesde && fecha_inicio <= fechaHasta"+" && erased==false"+" && estado=='"+estado+ "'");
		}else{
			q.setFilter("fecha_inicio >= fechaDesde && fecha_inicio <= fechaHasta"+" && erased==false"+" && premium == '"+tipoClient+"' && estado=='"+estado+ "'");			
		}
		
		q.declareParameters("java.util.Date fechaDesde , java.util.Date fechaHasta");

		soportes = (List<Soporte>) q.execute(fechaDesde,fechaHasta);

		
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
	
	
	public List<Soporte> getSoportesPaged(Integer page) {
		
		List<Soporte> soportes;
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Soporte");
		
		List<Entity> entities = null;
		FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
		if(page != null) {
			Integer offset = page * DATA_SIZE;
			fetchOptions.limit(DATA_SIZE);	
			fetchOptions.offset(offset);
		}
		entities = datastore.prepare(q).asList(fetchOptions);
				
		soportes = new ArrayList<>();	
		for (Entity result : entities) {			
			soportes.add(buildSoporte(result));
		}
		
		return soportes;
		
		/*PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query q = pm.newQuery("select from " + Prueba.class.getName());		
		q.setOrdering("fecha_estado desc");
		long offset = page * DATA_SIZE;
		q.setRange(offset, offset + DATA_SIZE);
		pruebas = (List<Prueba>) q.execute();
		pm.close();*/
		
		//return pruebas;		
	}
	
	public List<Soporte> getSoportesByAllParam(String fecha, String cliente, String segmento , String estado, String servicio, String producto,String descripcion , String premium,String idCli, Integer page) throws ParseException {
		List<Soporte> soportes = null;
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Soporte");
		
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
		if(!fecha.equals("")){	
			filters++;
		}

		if(!cliente.equals("")){
			cliente = cliente.toUpperCase();
			filters++;
		}
		
		if(!segmento.equals("")){
			filters++;
		}

		if(!estado.equals("")){
			Character a = estado.charAt(0);
			String estado2= a.toString().toUpperCase()+estado.substring(1);
			estado = estado2;
			filters++;
		}
		if(!servicio.equals("")){
			filters++;
		}

		if(!producto.equals("")){
			filters++;
		}
		
		if(!descripcion.equals("")){
			filters++;
		}
		
		if(!premium.equals("Todos")){
			filters++;
		}
		
		if(!idCli.equals("")){
			filters++;
		}

		String client_name=null;
		if(!idCli.equals("")){
//			ClienteDao clientDao = ClienteDao.getInstance();
//			Cliente clien = clientDao.getClientebyId(Long.parseLong(idCli));
//			client_name= clien.getNombre();
//			cliente="";
			filters++;
		}
		if(filters<=1){
			
			if(!fecha.equals("")){
				finalFilters.add(new FilterPredicate("str_fecha_inicio", FilterOperator.GREATER_THAN_OR_EQUAL, fecha));
				finalFilters.add(new FilterPredicate("str_fecha_inicio", FilterOperator.LESS_THAN, fecha+"\ufffd"));
			}
			if(!cliente.equals("")){
				finalFilters.add(new FilterPredicate("cliente_name", FilterOperator.GREATER_THAN_OR_EQUAL, cliente));
				finalFilters.add(new FilterPredicate("cliente_name", FilterOperator.LESS_THAN, cliente+"\ufffd"));
			}

			if(!segmento.equals("")){
				finalFilters.add(new FilterPredicate("tipo_cliente", FilterOperator.GREATER_THAN_OR_EQUAL,segmento));
				finalFilters.add(new FilterPredicate("tipo_cliente", FilterOperator.LESS_THAN, segmento+"\ufffd"));
			}
			
			if(!estado.equals("")){
				finalFilters.add(new FilterPredicate("estado", FilterOperator.GREATER_THAN_OR_EQUAL, estado));
				finalFilters.add(new FilterPredicate("estado", FilterOperator.LESS_THAN, estado+"\ufffd"));
			}
			
			if(!servicio.equals("")){
				finalFilters.add(new FilterPredicate("tipo_servicio", FilterOperator.GREATER_THAN_OR_EQUAL, servicio));
				finalFilters.add(new FilterPredicate("tipo_servicio", FilterOperator.LESS_THAN, servicio+"\ufffd"));
			}

			if(!producto.equals("")){
				finalFilters.add(new FilterPredicate("producto_canal", FilterOperator.GREATER_THAN_OR_EQUAL, producto));
				finalFilters.add(new FilterPredicate("producto_canal", FilterOperator.LESS_THAN,producto+"\ufffd"));
			}
			
			if(!descripcion.equals("")){
				finalFilters.add(new FilterPredicate("detalles", FilterOperator.GREATER_THAN_OR_EQUAL, descripcion));
				finalFilters.add(new FilterPredicate("detalles", FilterOperator.LESS_THAN,descripcion+"\ufffd"));
			}
			
			if(!idCli.equals("")){
				finalFilters.add(new FilterPredicate("cliente_id", FilterOperator.EQUAL, idCli));
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
			soportes = new ArrayList<>();	
			for (Entity result : entities) {			
				soportes.add(buildSoporte(result));
			}
			Soporte soportePagin = new Soporte();
			soportePagin.setDetalles("0");
			soportes.add(soportePagin);
			
			
			
			//soportePagin.setDetalles();
		}else{
//			List<Entity> clienteEntities = new ArrayList<Entity>();
//			List<Entity> fechaEntities = new ArrayList<Entity>();
//			List<Entity> servicioEntities = new ArrayList<Entity>();
//			List<Entity> entornoEntities = new ArrayList<Entity>();
//			List<Entity> estadoEntities = new ArrayList<Entity>();
//			List<Entity> productoEntities = new ArrayList<Entity>();
//			List<Entity> entities = new ArrayList<Entity>();
			List<List<Entity>> Entities = new ArrayList<List<Entity>>();
			
			if(!fecha.equals("")){
				q = new com.google.appengine.api.datastore.Query("Soporte");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("str_fecha_inicio", FilterOperator.GREATER_THAN_OR_EQUAL, fecha));
				finalFilters.add(new FilterPredicate("str_fecha_inicio", FilterOperator.LESS_THAN, fecha+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				
//				fechaEntities =datastore.prepare(q).asList(fetchOptions);
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!cliente.equals("")){
				q = new com.google.appengine.api.datastore.Query("Soporte");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("cliente_name", FilterOperator.GREATER_THAN_OR_EQUAL, cliente));
				finalFilters.add(new FilterPredicate("cliente_name", FilterOperator.LESS_THAN, cliente+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
//				clienteEntities =datastore.prepare(q).asList(fetchOptions);
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			
			if(!segmento.equals("")){
				q = new com.google.appengine.api.datastore.Query("Soporte");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("tipo_cliente", FilterOperator.GREATER_THAN_OR_EQUAL,segmento));
				finalFilters.add(new FilterPredicate("tipo_cliente", FilterOperator.LESS_THAN, segmento+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				q.setFilter(finalFilter);
//				entornoEntities =datastore.prepare(q).asList(fetchOptions);
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			

			if(!estado.equals("")){
				q = new com.google.appengine.api.datastore.Query("Soporte");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("estado", FilterOperator.GREATER_THAN_OR_EQUAL, estado));
				finalFilters.add(new FilterPredicate("estado", FilterOperator.LESS_THAN, estado+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				q.setFilter(finalFilter);
//				estadoEntities =datastore.prepare(q).asList(fetchOptions);
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			
			if(!servicio.equals("")){
				q = new com.google.appengine.api.datastore.Query("Soporte");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("tipo_servicio", FilterOperator.GREATER_THAN_OR_EQUAL, servicio));
				finalFilters.add(new FilterPredicate("tipo_servicio", FilterOperator.LESS_THAN, servicio+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				
//				servicioEntities =datastore.prepare(q).asList(fetchOptions);
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}

			if(!producto.equals("")){
				q = new com.google.appengine.api.datastore.Query("Soporte");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("producto_canal", FilterOperator.GREATER_THAN_OR_EQUAL, producto));
				finalFilters.add(new FilterPredicate("producto_canal", FilterOperator.LESS_THAN,producto+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				q.setFilter(finalFilter);
//				productoEntities =datastore.prepare(q).asList(fetchOptions);
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			
			if(!descripcion.equals("")){
				q = new com.google.appengine.api.datastore.Query("Soporte");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("detalles", FilterOperator.GREATER_THAN_OR_EQUAL, descripcion));
				finalFilters.add(new FilterPredicate("detalles", FilterOperator.LESS_THAN,descripcion+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				q.setFilter(finalFilter);
//				productoEntities =datastore.prepare(q).asList(fetchOptions);
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			
			if(!premium.equals("Todos")){
				q = new com.google.appengine.api.datastore.Query("Soporte");
				Filter filtro= new FilterPredicate("cliente_id", FilterOperator.EQUAL, idCli);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				q.setFilter(filtro);
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			
			if(!idCli.equals("")){
				q = new com.google.appengine.api.datastore.Query("Soporte");
				Filter filtro=new FilterPredicate("cliente_name", FilterOperator.EQUAL, client_name);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				q.setFilter(filtro);
				Entities.add(datastore.prepare(q).asList(fetchOptions));	
			}
			List<Entity> soportesFinal = new ArrayList<Entity>();
			int lowRowsIndex = 0;
			int lowRowsNumber = Entities.get(0).size();
			
			for(int i=1;i<Entities.size();i++){
				if(lowRowsNumber>Entities.get(i).size()){
					lowRowsIndex=i;
					lowRowsNumber=Entities.get(i).size();
				}
			}
			
			List<Entity> indexDel = new ArrayList<Entity>();
			soportesFinal = Entities.get(lowRowsIndex);
			for(int i=0;i<Entities.size();i++){
				if(i!=lowRowsIndex){
					int limite = soportesFinal.size();
					for (int j = 0 ; j<limite;j++) {				
							if(!Entities.get(i).contains(soportesFinal.get(j))){
								Entity auxEnty = soportesFinal.get(j);
								if(!indexDel.contains(auxEnty))indexDel.add(auxEnty);
							}
					}
				}
			}
			for(Entity removEnty: indexDel){
				soportesFinal.remove(removEnty);
			}
			
			soportes = new ArrayList<Soporte>();
			int soportesPages = soportesFinal.size();
			for (int i = page*10; i < (page*10)+10&&i<soportesFinal.size();i++) {			
				soportes.add(buildSoporte(soportesFinal.get(i)));
			}
			Soporte pages = new Soporte();
			pages.setDetalles(Integer.toString(soportesPages));
			soportes.add(pages);
		}
		
		
		return soportes;
	}
	
	private Soporte buildSoporte(Entity entity) {
		Soporte soporte = new Soporte();
		
		soporte.setKey(entity.getKey());
		Boolean erasedd = (boolean) entity.getProperty("erased");
		if(erasedd!=null){
			soporte.setErased(erasedd);
		}
		String cliente_id = getString(entity,"cliente_id");
		if(cliente_id != null) {
			soporte.setCliente_id(cliente_id);
		}
		
		String client_name = getString(entity, "cliente_name");
		if(client_name != null) {
			soporte.setCliente_name(client_name);
		}
		
		String detalles = getString(entity, "detalles");
		if(client_name != null) {
			soporte.setDetalles(detalles);
		}
		
		String estado = getString(entity, "estado");
		if(estado != null) {
			soporte.setEstado(estado);
		}
		
		Date fecha_fin = getDate(entity, "fecha_fin");
		if(fecha_fin!=null){
			soporte.setFecha_fin(fecha_fin);
		}
		
		Date fecha_inicio = getDate(entity,"fecha_inicio");
		if(fecha_inicio!=null){
			soporte.setFecha_inicio(fecha_inicio);
		}
		
		String id_soporte = getString(entity, "id_soporte");
		if(id_soporte!=null){
			soporte.setId_soporte(id_soporte);
		}
		
		String pais = getString(entity, "pais");
		if(pais!=null){
			soporte.setPais(pais);
		}
		
		String peticionario = getString(entity, "peticionario");
		if(peticionario!=null){
			soporte.setPeticionario(peticionario);
		}
		
		String premium = getString(entity, "premium");
		if(premium!=null){
			soporte.setPremium(premium);
		}
		
		String producto_canal = getString(entity, "producto_canal");
		if(producto_canal!=null){
			soporte.setProducto_canal(producto_canal);
		}
		
		String solucion = getString(entity, "solucion");
		if(solucion!=null){
			soporte.setSolucion(solucion);
		}
		
		String str_fecha_fin = getString(entity, "str_fecha_fin");
		if(str_fecha_fin!=null){
			soporte.setStr_fecha_fin(str_fecha_fin);
		}
		
		String str_fecha_inicio = getString(entity,"str_fecha_inicio");
		if(str_fecha_inicio!=null){
			soporte.setStr_fecha_inicio(str_fecha_inicio);
		}
		
		String tipo_cliente = getString(entity,"tipo_cliente");
		if(tipo_cliente!=null){
			soporte.setTipo_cliente(tipo_cliente);
		}
		
		String tipo_servicio = getString(entity,"tipo_servicio");
		if(tipo_servicio!=null){
			soporte.setTipo_servicio(tipo_servicio);
		}
		
		String tipo_soporte = getString(entity,"tipo_soporte");
		if(tipo_soporte!=null){
			soporte.setTipo_soporte(tipo_soporte);
		}
		
		
		
		return soporte;
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
