package com.ste.dao;

import java.text.ParseException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

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
		
		
		Query q = pm.newQuery("select from " + Cliente.class.getName());		
		q.setOrdering("nombre asc");
		clientes = (List<Cliente>) q.execute();
		
		
		pm.close();

		return clientes;
	}

	public void createCliente(Cliente c, String usermail) {
		PersistenceManager pm = PMF.get().getPersistenceManager();	
		CounterDao cDao = CounterDao.getInstance();

		try{
			c.setFecha_alta(Utils.dateConverter(c.getStr_fecha_alta()));
			
			boolean isNewClient = false;
			
			if (c.getKey()==null){
				
				Counter count = cDao.getCounterByName("cliente");
				String num = String.format("%04d", count.getValue());
				
				c.setId_cliente("IDGLOBAL_"+num);
				cDao.increaseCounter(count);
				
				isNewClient = true;
				
			}
			
			
			try {
				pm.makePersistent(c);
			} finally {/*
				if (!usermail.equals("")){
					if (c.isErased()){
						Utils.writeLog(usermail, "Eliminó", "Cliente", c.getNombre());
					}else{
						if (isNewClient)
							Utils.writeLog(usermail, "Creó", "Cliente", c.getNombre());
						else{
							Utils.writeLog(usermail, "Editó", "Cliente", c.getNombre());
						}
					}
				}*/
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
		pm.deletePersistent(pm.getObjectById(c.getClass(), c.getKey().getId()));
		pm.close();

	}
}