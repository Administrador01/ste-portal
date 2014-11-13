package com.ste.dao;

import java.text.ParseException;

import javax.jdo.PersistenceManager;

import com.ste.beans.Cliente;
import com.ste.counters.Counter;
import com.ste.persistence.PMF;
import com.ste.utils.Utils;

public class ClienteDao {

	public static ClienteDao getInstance() {
		return new ClienteDao();
	}

	public void createCliente(Cliente c) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		

		try{
		
			
			pm.makePersistent(c);
		}finally {
			pm.close();
		}
	}
	
	public void deleteCliente(Cliente c) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistent(pm.getObjectById(c.getClass(), c.getKey().getId()));
		pm.close();

	}
}
