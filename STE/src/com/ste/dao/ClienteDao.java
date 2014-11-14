package com.ste.dao;

import java.text.ParseException;
import java.util.List;

import javax.jdo.PersistenceManager;

import com.ste.beans.Cliente;
import com.ste.beans.Soporte;
import com.ste.beans.User;
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
		String queryStr = "select from " + Cliente.class.getName();
		clientes = (List<Cliente>) pm.newQuery(queryStr).execute();
		
		pm.close();

		return clientes;
	}

	public void createCliente(Cliente c) {
		PersistenceManager pm = PMF.get().getPersistenceManager();	
		CounterDao cDao = CounterDao.getInstance();

		try{
			c.setFecha_alta(Utils.dateConverter(c.getStr_fecha_alta()));
			
			if (c.getKey()==null){
				
				Counter count = cDao.getCounterByName("cliente");
				String num = String.format("%04d", count.getValue());
				
				c.setId_cliente("IDGLOBAL_"+num);
				cDao.increaseCounter(count);
				
			}		
			
			pm.makePersistent(c);
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
