package com.ste.dao;

import java.text.ParseException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.ste.beans.Cliente;
import com.ste.counters.Counter;
import com.ste.persistence.PMF;
import com.ste.utils.Utils;

public class ClienteDao {

	public static ClienteDao getInstance() {
		return new ClienteDao();
	}
	
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
}
