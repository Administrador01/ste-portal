package com.ste.dao;

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
import com.ste.beans.Implementacion;
import com.ste.counters.Counter;
import com.ste.persistence.PMF;
import com.ste.utils.Utils;

public class ImplementacionDao {
	
	public static final int DATA_SIZE = 10;
	
	public static ImplementacionDao getInstance() {
		return new ImplementacionDao();
	}
	
	@SuppressWarnings("unchecked")
	public List<Implementacion> getAllImplementacionesUpdateMayusPaged(Integer page) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Implementacion");
		
		List<Entity> entities = null;
		FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
		if(page != null) {
			Integer offset = page * 200;
			fetchOptions.limit(200);	
			fetchOptions.offset(offset);
		}
		entities = datastore.prepare(q).asList(fetchOptions);		
		List<Implementacion> Implementacions = new ArrayList<Implementacion>();	;
		
		for (Entity result : entities){
			try{
				Implementacions.add(buildImplementacion(result));
			}catch(Exception exp) {}
		}

		return Implementacions;
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
	
	public List<Implementacion> getAllImplementacionesPagin(Integer page) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Implementacion");
		
		List<Entity> entities = null;
		FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
		if(page != null) {
			Integer offset = page * DATA_SIZE;
			fetchOptions.limit(DATA_SIZE);	
			fetchOptions.offset(offset);
		}
		entities = datastore.prepare(q).asList(fetchOptions);		
		List<Implementacion> Implementacions = new ArrayList<Implementacion>();	;
		
		for (Entity result : entities){
			try{
				Implementacions.add(buildImplementacion(result));
			}catch(Exception exp) {}
		}

		return Implementacions;
	}
	
	public List<Implementacion> getImplementacionFor(String clienteName,String productoCanal,String servicio,String gestorGcs,String pais,
			String gestorPromocion,String gestorRelacion,String referenciaGlobal,boolean firmaBol,boolean normalizadorbol, String referenciaLocal,
			String estado,String detalle,String referenciaExterna,String asunto,String contratoAdeudos, String idAcreedor,String cuentaAbono) {

		clienteName = Utils.toUpperCase(clienteName);
		productoCanal = Utils.toUpperCase(productoCanal);
		servicio = Utils.toUpperCase(servicio);
		gestorGcs = Utils.toUpperCase(gestorGcs);
		pais = Utils.toUpperCase(pais);
		gestorPromocion = Utils.toUpperCase(gestorPromocion);
		gestorRelacion = Utils.toUpperCase(gestorRelacion);
		estado = Utils.toUpperCase(estado);
		detalle = Utils.toUpperCase(detalle);
		
		List<Implementacion> Implementacions = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery("select from " + Implementacion.class.getName());
		q.setFilter(" ");
		q.declareParameters("String clienteName,String productoCanal,String servicio,String gestorGcs,String pais,String gestorPromocion,String gestorRelacion,String referenciaGlobal,boolean firmaBol,boolean normalizadorbol, String referenciaLocal,String estado,String detalle,String referenciaExterna,String asunto,String contratoAdeudos, String idAcreedor,String cuentaAbono");
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
			if(imp.getStr_fech_subida()!=null){
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
	
	@SuppressWarnings("unchecked")
	public List<Implementacion> getImplementacionesByServiceClient(String client, String service) {
		List<Implementacion> Implementacions;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery("select from " + Implementacion.class.getName());		
		q.setFilter("client_name == client && servicio_name == service && erased==false");
		q.declareParameters("String client, String service");
		Implementacions = (List<Implementacion>) q.execute(client,service);
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

	public void deleteAll() {
		ImplementacionDao impDao = ImplementacionDao.getInstance();
		List<Implementacion> implementaciones = impDao.getAllImplementaciones();
		for(Implementacion imp : implementaciones){
			impDao.deleteImplementaciones(imp);
		}
	}
	
	public void deleteImplementaciones(Implementacion imp) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistent( pm.getObjectById( imp.getClass(), imp.getKey().getId())); 
		pm.close();
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
		String query = "select from " + Implementacion.class.getName()+" where cliente_id == "+l+" && erased==false";
		Query q = pm.newQuery(query);//.setFilter(propertyFilter);
		implementaciones = (List<Implementacion>) q.execute();
		pm.close();
	
		return implementaciones;
	}	

	public synchronized void createImplementacionRaw(Implementacion imp){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		imp.setClient_name(Utils.toUpperCase(imp.getClient_name()));
		imp.setDetalle(Utils.toUpperCase(imp.getDetalle()));
		imp.setEstado(Utils.toUpperCase(imp.getEstado()));
		imp.setGestor_gcs(Utils.toUpperCase(imp.getGestor_gcs()));
		imp.setGestor_promocion(Utils.toUpperCase(imp.getGestor_promocion()));
		imp.setGestor_relacion(Utils.toUpperCase(imp.getGestor_relacion()));
		imp.setPais(Utils.toUpperCase(imp.getPais()));
		imp.setServicio_name(Utils.toUpperCase(imp.getServicio_name()));
		imp.setProducto(Utils.toUpperCase(imp.getProducto()));
		imp.setReferencia_global(Utils.toUpperCase(imp.getReferencia_global()));
		imp.setReferencia_local(Utils.toUpperCase(imp.getReferencia_local()));
		imp.setReferencia_externa(Utils.toUpperCase(imp.getReferencia_externa()));
		imp.setAsunto_ref_ext(Utils.toUpperCase(imp.getAsunto_ref_ext()));
		imp.setCuenta_ref_ext(Utils.toUpperCase(imp.getCuenta_ref_ext()));
		imp.setAdeudos_ref_ext(Utils.toUpperCase(imp.getAdeudos_ref_ext()));
		imp.setAcreedor_ref_ext(Utils.toUpperCase(imp.getAcreedor_ref_ext()));
		
		pm.makePersistent(imp);
		pm.close();
	}
	
	public synchronized void createImplementacion(Implementacion imp) {
		PersistenceManager pm = PMF.get().getPersistenceManager();	
		
		imp.setClient_name(Utils.toUpperCase(imp.getClient_name()));
		imp.setDetalle(Utils.toUpperCase(imp.getDetalle()));
		imp.setEstado(Utils.toUpperCase(imp.getEstado()));
		imp.setGestor_gcs(Utils.toUpperCase(imp.getGestor_gcs()));
		imp.setGestor_promocion(Utils.toUpperCase(imp.getGestor_promocion()));
		imp.setGestor_relacion(Utils.toUpperCase(imp.getGestor_relacion()));
		imp.setPais(Utils.toUpperCase(imp.getPais()));
		imp.setServicio_name(Utils.toUpperCase(imp.getServicio_name()));
		imp.setProducto(Utils.toUpperCase(imp.getProducto()));
		imp.setReferencia_global(Utils.toUpperCase(imp.getReferencia_global()));
		imp.setReferencia_local(Utils.toUpperCase(imp.getReferencia_local()));
		imp.setReferencia_externa(Utils.toUpperCase(imp.getReferencia_externa()));
		imp.setAsunto_ref_ext(Utils.toUpperCase(imp.getAsunto_ref_ext()));
		imp.setCuenta_ref_ext(Utils.toUpperCase(imp.getCuenta_ref_ext()));
		imp.setAdeudos_ref_ext(Utils.toUpperCase(imp.getAdeudos_ref_ext()));
		imp.setAcreedor_ref_ext(Utils.toUpperCase(imp.getAcreedor_ref_ext()));
		
		try{
			imp.setFecha_alta((Utils.dateConverter(imp.getStr_fecha_alta())));
			if (!Utils.esNuloVacio(imp.getStr_fech_contratacion())){
				imp.setFech_contratacion(Utils.dateConverter(imp.getStr_fech_contratacion()));
			}
			
			if (!Utils.esNuloVacio(imp.getStr_fech_subida())){
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
			pm.makePersistent(imp);
						
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			pm.close();			
		}
	}
	
	
	public List<Implementacion> getImplementacionesByAllParam(String fechaDia, String fechaMes, String fechaAnio, String cliente, String pais, String producto, String servicio, String normalizador, String estado, Integer page){
		List<Implementacion> implementaciones= null;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Implementacion");
		List<Filter> finalFilters = new ArrayList<>();
		
		int filters =0;
		if(Utils.isFechaFilterValid(fechaDia, fechaMes, fechaAnio)){
			filters++;
		}
		if(!Utils.esNuloVacio(cliente)){
			cliente = cliente.toUpperCase();
			filters++;
		}
		if(!Utils.esNuloVacio(pais)){
			pais = pais.toUpperCase();
			filters++;
		}
		if(!Utils.esNuloVacio(producto)){
			producto = producto.toUpperCase();
			filters++;
		}
		if(!Utils.esNuloVacio(estado)){
			estado = estado.toUpperCase();
			filters++;
		}
		if(!Utils.esNuloVacio(servicio)){
			servicio = servicio.toUpperCase();
			filters++;
		}
		if(!Utils.esNuloVacio(normalizador)){
			normalizador = normalizador.toUpperCase();
			filters++;
		}
		
		if(filters<=1){
			if(Utils.isFechaFilterValid(fechaDia, fechaMes, fechaAnio)){
				if(!Utils.esNuloVacio(fechaAnio) && !Utils.esNuloVacio(fechaMes) && !Utils.esNuloVacio(fechaDia)) {
					Date fullDate = Utils.buildDate(fechaDia, fechaMes, fechaAnio);
					finalFilters.add(new FilterPredicate("fecha_alta", FilterOperator.EQUAL, fullDate));
				}
				else if(!Utils.esNuloVacio(fechaAnio)) {
					Date desdeDate = Utils.getDesdeDate(fechaMes, fechaAnio);
			        Date hastaDate = Utils.getHastaDate(fechaMes, fechaAnio);

			        if(desdeDate != null){
			            finalFilters.add(new FilterPredicate("fecha_alta", FilterOperator.GREATER_THAN_OR_EQUAL, desdeDate));
			        }
			        if(hastaDate != null){
			            finalFilters.add(new FilterPredicate("fecha_alta", FilterOperator.LESS_THAN_OR_EQUAL, hastaDate));
			        }
				}					
			}
			if(!Utils.esNuloVacio(cliente)){
				finalFilters.add(new FilterPredicate("client_name",FilterOperator.GREATER_THAN_OR_EQUAL, cliente));
				finalFilters.add(new FilterPredicate("client_name",FilterOperator.LESS_THAN, cliente+"\ufffd"));
			}
			if(!Utils.esNuloVacio(pais)){
				finalFilters.add(new FilterPredicate("pais",FilterOperator.GREATER_THAN_OR_EQUAL, pais));
				finalFilters.add(new FilterPredicate("pais",FilterOperator.LESS_THAN, pais+"\ufffd"));
			}
			if(!Utils.esNuloVacio(producto)){
				finalFilters.add(new FilterPredicate("producto",FilterOperator.GREATER_THAN_OR_EQUAL, producto));
				finalFilters.add(new FilterPredicate("producto",FilterOperator.LESS_THAN, producto+"\ufffd"));
			}
			if(!Utils.esNuloVacio(estado)){
				finalFilters.add(new FilterPredicate("estado",FilterOperator.GREATER_THAN_OR_EQUAL, estado));
				finalFilters.add(new FilterPredicate("estado",FilterOperator.LESS_THAN, estado+"\ufffd"));
			}
			if(!Utils.esNuloVacio(servicio)){
				finalFilters.add(new FilterPredicate("servicio_name",FilterOperator.GREATER_THAN_OR_EQUAL, servicio));
				finalFilters.add(new FilterPredicate("servicio_name",FilterOperator.LESS_THAN, servicio+"\ufffd"));
			}
			if(!Utils.esNuloVacio(normalizador)){				
				if(normalizador.equals("S") || normalizador.equals("SI")) {
					finalFilters.add(new FilterPredicate("normalizador",FilterOperator.EQUAL, true));
				}
				else if(normalizador.equals("N") || normalizador.equals("NO")) {
					finalFilters.add(new FilterPredicate("normalizador",FilterOperator.EQUAL, false));
				}
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
			implementaciones = new ArrayList<>();
			for(Entity result:entities){
				try{
					implementaciones.add(buildImplementacion(result));
				}catch(Exception exp) {}
			}
			Implementacion impPage = new Implementacion();
			impPage.setDetalle("0");
			implementaciones.add(impPage);
		
		}
		else {			
			List<List<Entity>> Entities = new ArrayList<List<Entity>>();
			
			if(Utils.isFechaFilterValid(fechaDia, fechaMes, fechaAnio)){
				q = new com.google.appengine.api.datastore.Query("Implementacion");
				if(!Utils.esNuloVacio(fechaAnio) && !Utils.esNuloVacio(fechaMes) && !Utils.esNuloVacio(fechaDia)) {
					Date fullDate = Utils.buildDate(fechaDia, fechaMes, fechaAnio);
					q.setFilter(new FilterPredicate("fecha_alta", FilterOperator.EQUAL, fullDate));
				}
				else if(!Utils.esNuloVacio(fechaAnio)) {
					Date desdeDate = Utils.getDesdeDate(fechaMes, fechaAnio);
			        Date hastaDate = Utils.getHastaDate(fechaMes, fechaAnio);
			        finalFilters = new ArrayList<>();
			        if(desdeDate != null){
			            finalFilters.add(new FilterPredicate("fecha_alta", FilterOperator.GREATER_THAN_OR_EQUAL, desdeDate));
			        }
			        if(hastaDate != null){
			            finalFilters.add(new FilterPredicate("fecha_alta", FilterOperator.LESS_THAN_OR_EQUAL, hastaDate));
			        }
			        Filter finalFilter = CompositeFilterOperator.and(finalFilters);
					q.setFilter(finalFilter);
				}
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!Utils.esNuloVacio(cliente)){
				q = new com.google.appengine.api.datastore.Query("Implementacion");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("client_name",FilterOperator.GREATER_THAN_OR_EQUAL, cliente));
				finalFilters.add(new FilterPredicate("client_name",FilterOperator.LESS_THAN, cliente+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!Utils.esNuloVacio(pais)){
				q = new com.google.appengine.api.datastore.Query("Implementacion");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("pais",FilterOperator.GREATER_THAN_OR_EQUAL, pais));
				finalFilters.add(new FilterPredicate("pais",FilterOperator.LESS_THAN, pais+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!Utils.esNuloVacio(producto)){
				q = new com.google.appengine.api.datastore.Query("Implementacion");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("producto",FilterOperator.GREATER_THAN_OR_EQUAL, producto));
				finalFilters.add(new FilterPredicate("producto",FilterOperator.LESS_THAN, producto+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!Utils.esNuloVacio(estado)){
				q = new com.google.appengine.api.datastore.Query("Implementacion");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("estado",FilterOperator.GREATER_THAN_OR_EQUAL, estado));
				finalFilters.add(new FilterPredicate("estado",FilterOperator.LESS_THAN, estado+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!Utils.esNuloVacio(servicio)){
				q = new com.google.appengine.api.datastore.Query("Implementacion");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("servicio_name",FilterOperator.GREATER_THAN_OR_EQUAL, servicio));
				finalFilters.add(new FilterPredicate("servicio_name",FilterOperator.LESS_THAN, servicio+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!Utils.esNuloVacio(normalizador)){		
				q = new com.google.appengine.api.datastore.Query("Implementacion");
				if(normalizador.equals("S") || normalizador.equals("SI")) {
					q.setFilter(new FilterPredicate("normalizador",FilterOperator.EQUAL, true));
					FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
					Entities.add(datastore.prepare(q).asList(fetchOptions));
				}
				else if(normalizador.equals("N") || normalizador.equals("NO")) {
					q.setFilter(new FilterPredicate("normalizador",FilterOperator.EQUAL, false));
					FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
					Entities.add(datastore.prepare(q).asList(fetchOptions));
				}
			}
			
			List<Entity> implementacionesFinal = new ArrayList<>();
			int lowRowsIndex = 0;
			int lowRowsNumber = Entities.get(0).size();
			
			for(int i=1;i<Entities.size();i++){
				if(lowRowsNumber>Entities.get(i).size()){
					lowRowsIndex=i;
					lowRowsNumber=Entities.get(i).size();
				}
			}
			
			implementacionesFinal = Entities.get(lowRowsIndex);
			List<Entity> indexDel = new ArrayList<Entity>();
			for(int i=0;i<Entities.size();i++){
				if(i!=lowRowsIndex){
					int j = 0;
					for (Entity result : implementacionesFinal) {
						if(!Entities.get(i).contains(result)){
							Entity auxEnty = implementacionesFinal.get(j);
							if(!indexDel.contains(auxEnty))indexDel.add(auxEnty);
						}
						j++;
					}
				}
			}
			
			for (Entity impborr : indexDel){
				implementacionesFinal.remove(impborr);
			}
			
			implementaciones = new ArrayList<Implementacion>();
			int implementacionesPages  = implementacionesFinal.size();
			for(int i = page*DATA_SIZE; i<(page*DATA_SIZE)+DATA_SIZE && i<implementacionesFinal.size();i++){
				try{
					implementaciones.add(buildImplementacion(implementacionesFinal.get(i)));
				}catch(Exception exp) {}
			}
			Implementacion pages = new Implementacion();
			pages.setDetalle(Integer.toString(implementacionesPages));
			implementaciones.add(pages);
		}
		return implementaciones;
	}
	
	private Implementacion buildImplementacion(Entity entity) {
		Implementacion implementacion = new Implementacion();
		
		implementacion.setKey(entity.getKey());
		
		String client_name = getString(entity, "client_name");
		if(client_name != null) {
			implementacion.setClient_name(client_name);
		}
		String detalle = getString(entity, "detalle");
		if(detalle != null) {
			implementacion.setDetalle(detalle);
		}

		
		implementacion.setErased((boolean) entity.getProperty("erased"));
		
		implementacion.setFirma_contrato((boolean) entity.getProperty("firma_contrato"));
		
		implementacion.setNormalizador((boolean) entity.getProperty("normalizador"));
		
		String estado = getString(entity, "estado");
		if(estado != null) {
			implementacion.setEstado(estado);
		}
		Date fecha_alta = getDate(entity, "fecha_alta");
		if(fecha_alta != null) {
			implementacion.setFecha_alta(fecha_alta);
		}
		Date fecha_contratacion = getDate(entity, "fecha_contratacion");
		if(fecha_contratacion != null) {
			implementacion.setFech_contratacion(fecha_contratacion);
		}
		
		Date fecha_subida = getDate(entity, "fecha_subida");
		if(fecha_subida != null) {
			implementacion.setFech_subida(fecha_subida);
		}
		
		String str_fecha_alta = getString(entity, "str_fecha_alta");
		if(str_fecha_alta != null) {
			implementacion.setStr_fech_alta(str_fecha_alta);
		}
		
		String str_fecha_contratacion = getString(entity, "str_fecha_contratacion");
		if(str_fecha_contratacion != null) {
			implementacion.setStr_fech_contratacion(str_fecha_contratacion);
		}
		
		String str_fecha_subida = getString(entity, "str_fecha_subida");
		if(str_fecha_subida != null) {
			implementacion.setStr_fech_subida(str_fecha_subida);
		}
		
		String acreedor = getString(entity, "acreedor_ref_ext");
		if(acreedor != null) {
			implementacion.setAcreedor_ref_ext(acreedor);
		}
		
		String adeudos = getString(entity, "adeudos_ref_ext");
		if(adeudos != null) {
			implementacion.setAdeudos_ref_ext(adeudos);
		}
		
		String asunto = getString(entity, "asunto_ref_ext");
		if(asunto != null) {
			implementacion.setAsunto_ref_ext(asunto);
		}
		

		long cliente_id  = getLong(entity, "cliente_id");
		if(cliente_id != 0l) {
			implementacion.setCliente_id(cliente_id);
		}
		String cuenta  = getString(entity, "cuenta_ref_ext");
		if(cuenta != null) {
			implementacion.setCuenta_ref_ext(cuenta);
		}
		String estadoImp = getString(entity, "estado");
		if(estadoImp != null) {
			implementacion.setEstado(estadoImp);
		}
		String gestorGcs = getString(entity, "gestor_gcs");
		if(gestorGcs != null) {
			implementacion.setGestor_gcs(gestorGcs);
		}
		String gestorPromocion = getString(entity, "gestor_promocion");
		if( gestorPromocion!= null) {
			implementacion.setGestor_promocion(gestorPromocion);
		}
		
		String gestorRelacion = getString(entity, "gestor_relacion");
		if( gestorRelacion!= null) {
			implementacion.setGestor_relacion(gestorRelacion);
		}		
		String id_implementacion = getString(entity,"id_implementacion");
		if(id_implementacion!= null){
			implementacion.setId_implementacion(id_implementacion);
		}
		String pais = getString(entity,"pais");
		if(pais!= null){
			implementacion.setPais(pais);
		}
		String producto  = getString(entity,"producto");
		if(producto!= null){
			implementacion.setProducto(producto);
		}
		String externa = getString(entity,"referencia_externa");
		if(externa!= null){
			implementacion.setReferencia_externa(externa);
		}
		String global = getString(entity,"referencia_global");
		if(global!= null){
			implementacion.setReferencia_global(global);
		}
		String local  = getString(entity,"referencia_local");
		if(local!= null){
			implementacion.setReferencia_local(local);
		}
		String servicioName = getString(entity,"servicio_name");
		if(servicioName!= null){
			implementacion.setServicio_name(servicioName);
		}
		long servicioId = getLong(entity,"servicio_id");
		if(servicioId!=0l){
			implementacion.setServicio_id(servicioId);;
		}
		return implementacion;
	}
	
	
	private String getString(Entity e, String field) {
		try {
			return (String) e.getProperty(field);
		}
		catch(Exception exp) {
			return null;
		}
	}
	
	private Long getLong(Entity e, String field) {
		try {
			return (Long) e.getProperty(field);
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
