package com.ste.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.ste.beans.Cliente;
import com.ste.counters.Counter;
import com.ste.persistence.PMF;
import com.ste.utils.Utils;

public class ClienteDao {

	public static ClienteDao getInstance() {
		return new ClienteDao();
	}
	
	public static final int DATA_SIZE = 10;
	
	@SuppressWarnings("unchecked")
	public List<Cliente> getAllClients() {

		List<Cliente> clientes;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Cliente.class.getName() +" where erased == false");		
		q.setOrdering("id_cliente asc");
		clientes = (List<Cliente>) q.execute();
		
		
		pm.close();

		return clientes;
	}
	
	@SuppressWarnings("unchecked")
	public List<Cliente> getAllDelClients() {

		List<Cliente> clientes;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Cliente.class.getName() +" where erased == true");		
		q.setOrdering("id_cliente asc");
		clientes = (List<Cliente>) q.execute();
		
		
		pm.close();

		return clientes;
	}
	
	@SuppressWarnings("unchecked")
	public List<Cliente> getAllClientsEvenDeleted() {

		List<Cliente> clientes;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		
		Query q = pm.newQuery("select from " + Cliente.class.getName());		
		q.setOrdering("id_cliente asc");
		clientes = (List<Cliente>) q.execute();
		
		
		pm.close();

		return clientes;
	}
	
	@SuppressWarnings("unchecked")
	public List<Cliente> getAllClientsAlphabet() {

		List<Cliente> clientes;
		PersistenceManager pm = PMF.get().getPersistenceManager();
				
		Query q = pm.newQuery("select from " + Cliente.class.getName() +" where erased == false");		
		q.setOrdering("nombre asc");
		clientes = (List<Cliente>) q.execute();
		
		
		pm.close();

		return clientes;
	}
	
	public synchronized void createClienteRaw(Cliente c) {
		PersistenceManager pm = PMF.get().getPersistenceManager();	
		pm.makePersistent(c);
		pm.close();
	}
	public synchronized void createCliente(Cliente c) {
		PersistenceManager pm = PMF.get().getPersistenceManager();	
		CounterDao cDao = CounterDao.getInstance();

		try{
			
			ClienteDao cliDao = ClienteDao.getInstance();	

			List<Cliente> client_arr = cliDao.getAllClients();
			boolean flag = false;
			for (Cliente clie : client_arr){
				if(clie.getNombre().equals(c.getNombre())&&
				   clie.getStr_fecha_alta().equals(c.getStr_fecha_alta())&&clie.getPremium().equals(c.getPremium())&&
				   clie.getTipo_cliente().equals(c.getTipo_cliente())){
						flag = true;
				}

			}
			if(!flag){
				
				c.setFecha_alta(Utils.dateConverter(c.getStr_fecha_alta()));
				if (c.getKey()==null){
					
					Counter count = cDao.getCounterByName("cliente");
					String num = String.format("%04d", count.getValue());
					
					c.setId_cliente("IDGLOBAL_"+num);
					cDao.increaseCounter(count);
					c.setErased(false);	
				}
				
				
				try {
					pm.makePersistent(c);
				} finally {}
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pm.close();
			
		}
	}
	
	public Cliente getClientebyId(long l) {
		
		Cliente c;
		try{			
		
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Cliente cliente_temp = pManager.getObjectById(Cliente.class, l);

		c = pManager.detachCopy(cliente_temp);
		pManager.close();

		}catch(Exception e){
			c=null;
		}
		
		return c;
		
		
	}
	
	public void deleteCliente(Cliente c) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		c.setErased(true);
		
		ClienteDao clieDao =ClienteDao.getInstance();
		clieDao.createClienteRaw(c);
		
		pm.close();

	}
	public Cliente getClienteByName(String name) {

		Cliente c = new Cliente();

		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Transaction transaction = pManager.currentTransaction();
		transaction.begin();

		String queryStr = "select from " + Cliente.class.getName()
				+ " where nombre  == :p1";

		List<Cliente> clientes = (List<Cliente>) pManager.newQuery(queryStr).execute(name);

		if (!clientes.isEmpty()) {
			c = clientes.get(0);
		} else {
			c = null;
		}

		transaction.commit();
		pManager.close();

		return c;
	}
	
	public List<Cliente> getClientesByName(String name) {
		ClienteDao clienteDao = ClienteDao.getInstance();
		List<Cliente> clientes = clienteDao.getAllClients();
		List<Cliente> clientesCoinc = new ArrayList<Cliente>();
		for (Cliente cli: clientes ){
			if (cli.getNombre().toLowerCase().equals(name.toLowerCase())){
				clientesCoinc.add(cli);
			}
		}
		return clientesCoinc;
	}
	
	public List<Cliente> getClienteByAllParam(String identificador, String nombre, String fecha, String segmento, String premium, Integer page){
		
		
		List<Cliente> clientes=null;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Cliente");

		List<Filter> finalFilters = new ArrayList<>();
		
		int filters = 0;
		if(!identificador.equals("")){
			filters++;
		}
		if(!nombre.equals("")){
			nombre = nombre.toUpperCase();
			filters++;
		}
		if(!fecha.equals("")){
			filters++;
		}
		if(!segmento.equals("")){
			filters++;
		}
		if(!premium.equals("")){
			filters++;
		}
		if(filters<=1){
			if(!identificador.equals("")){
				finalFilters.add(new FilterPredicate("id_cliente", FilterOperator.GREATER_THAN_OR_EQUAL, identificador));
				finalFilters.add(new FilterPredicate("id_cliente", FilterOperator.LESS_THAN, identificador+"\ufffd"));
			}
			if(!nombre.equals("")){
				finalFilters.add(new FilterPredicate("nombre", FilterOperator.GREATER_THAN_OR_EQUAL, nombre));
				finalFilters.add(new FilterPredicate("nombre", FilterOperator.LESS_THAN, nombre+"\ufffd"));
			}
			if(!fecha.equals("")){
				finalFilters.add(new FilterPredicate("str_fecha_alta", FilterOperator.GREATER_THAN_OR_EQUAL, fecha));
				finalFilters.add(new FilterPredicate("str_fecha_alta", FilterOperator.LESS_THAN, fecha+"\ufffd"));
			}
			if(!segmento.equals("")){
				finalFilters.add(new FilterPredicate("tipo_cliente", FilterOperator.GREATER_THAN_OR_EQUAL, segmento));
				finalFilters.add(new FilterPredicate("tipo_cliente", FilterOperator.LESS_THAN, segmento+"\ufffd"));
			}
			if(!premium.equals("")){
				finalFilters.add(new FilterPredicate("premium", FilterOperator.GREATER_THAN_OR_EQUAL, premium));
				finalFilters.add(new FilterPredicate("premium", FilterOperator.LESS_THAN, premium+"\ufffd"));
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
			
			clientes = new ArrayList<>();
			for (Entity result : entities) {
				clientes.add(buildCliente(result));
			}
			Cliente clientePagin = new Cliente();
			clientePagin.setId_cliente("0");
			clientes.add(clientePagin);
			
		}else{
			List<List<Entity>> Entities = new ArrayList<List<Entity>>();
			
			if(!identificador.equals("")){
				q = new com.google.appengine.api.datastore.Query("Cliente");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("id_cliente", FilterOperator.GREATER_THAN_OR_EQUAL, identificador));
				finalFilters.add(new FilterPredicate("id_cliente", FilterOperator.LESS_THAN, identificador+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!nombre.equals("")){
				q = new com.google.appengine.api.datastore.Query("Cliente");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("nombre", FilterOperator.GREATER_THAN_OR_EQUAL, nombre));
				finalFilters.add(new FilterPredicate("nombre", FilterOperator.LESS_THAN, nombre+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!fecha.equals("")){
				q = new com.google.appengine.api.datastore.Query("Cliente");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("str_fecha_alta", FilterOperator.GREATER_THAN_OR_EQUAL, fecha));
				finalFilters.add(new FilterPredicate("str_fecha_alta", FilterOperator.LESS_THAN, fecha+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!segmento.equals("")){
				q = new com.google.appengine.api.datastore.Query("Cliente");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("tipo_cliente", FilterOperator.GREATER_THAN_OR_EQUAL, segmento));
				finalFilters.add(new FilterPredicate("tipo_cliente", FilterOperator.LESS_THAN, segmento+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			if(!premium.equals("")){
				q = new com.google.appengine.api.datastore.Query("Cliente");
				finalFilters = new ArrayList<>();
				finalFilters.add(new FilterPredicate("premium", FilterOperator.GREATER_THAN_OR_EQUAL, premium));
				finalFilters.add(new FilterPredicate("premium", FilterOperator.LESS_THAN, premium+"\ufffd"));
				Filter finalFilter = CompositeFilterOperator.and(finalFilters);
				q.setFilter(finalFilter);
				FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
				Entities.add(datastore.prepare(q).asList(fetchOptions));
			}
			
			
			int lowRowsIndex = 0;
			int lowRowsNumber = Entities.get(0).size();
			
			for(int i=1;i<Entities.size();i++){
				if(lowRowsNumber>Entities.get(i).size()){
					lowRowsIndex=i;
					lowRowsNumber=Entities.get(i).size();
				}
			}
			
			List<Entity> clientesFinal = new ArrayList<Entity>();
			List<Entity> indexDel = new ArrayList<Entity>();
			clientesFinal = Entities.get(lowRowsIndex);
			for(int i=0;i<Entities.size();i++){
				if(i!=lowRowsIndex){
					int j = 0;
					for (Entity result : clientesFinal) {
						if(!Entities.get(i).contains(result)){
							Entity auxEnty = clientesFinal.get(j);
							if(!indexDel.contains(auxEnty))indexDel.add(auxEnty);
						}
						j++;
					}
				}
			}
			for(Entity removEnty:indexDel){
				clientesFinal.remove(removEnty);
			}
			
			
			clientes = new ArrayList<Cliente>();
			int clientesPages = clientesFinal.size();
			for (int i = page*10; i < (page*10)+10&&i<clientesFinal.size();i++) {
				clientes.add(buildCliente(clientesFinal.get(i)));
			}
			Cliente pages= new Cliente();
			pages.setId_cliente(Integer.toString(clientesPages));
			clientes.add(pages);
			
		}
		
		
		
		return clientes;
	}
	
	public List<Cliente> getClientePaged(Integer page){
		List<Cliente> clientes;
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		com.google.appengine.api.datastore.Query q = new com.google.appengine.api.datastore.Query("Cliente");
		
		List<Entity> entities = null;
		FetchOptions fetchOptions=FetchOptions.Builder.withDefaults();
		if(page != null) {
			Integer offset = page * DATA_SIZE;
			fetchOptions.limit(DATA_SIZE);	
			fetchOptions.offset(offset);
		}
		entities = datastore.prepare(q).asList(fetchOptions);
				
		clientes = new ArrayList<>();
		for(Entity result:entities){
			clientes.add(buildCliente(result));
		}
		
		return clientes;
	}
	
	private Cliente buildCliente(Entity entity){
		Cliente cliente = new Cliente();
		
		cliente.setKey(entity.getKey());
		
		String client_name = getString(entity, "nombre");
		if(client_name != null) {
			cliente.setNombre(client_name);
		}
		
		String client_id = getString(entity, "id_cliente");
		if(client_id != null) {
			cliente.setId_cliente(client_id);
		}
		
		cliente.setErased((boolean) entity.getProperty("erased"));
		
		Date fecha_alta = getDate(entity, "fecha_alta");
		if(fecha_alta != null) {
			cliente.setFecha_alta(fecha_alta);
		}
		
		String premium = getString(entity, "premium");
		if(premium != null) {
			cliente.setPremium(premium);
		}
		
		String tipo_cliente = getString(entity, "tipo_cliente");
		if(tipo_cliente != null) {
			cliente.setTipo_cliente(tipo_cliente);
		}
		
		String str_fecha_alta = getString(entity, "str_fecha_alta");
		if(str_fecha_alta != null) {
			cliente.setStr_fecha_alta(str_fecha_alta);
		}
		return cliente;
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
