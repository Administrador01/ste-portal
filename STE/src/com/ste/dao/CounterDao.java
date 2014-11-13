package com.ste.dao;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import com.ste.beans.User;
import com.ste.counters.Counter;
import com.ste.persistence.PMF;

public class CounterDao {
	
	public static CounterDao getInstance() {
		return new CounterDao();
	}
	
	public void increaseCounter(Counter c){
		c.setValue(c.getValue()+1);
		createCounter(c);
	} 
	
	public Counter getCounterByName(String name) {
		Counter contador;
		PersistenceManager pManager = PMF.get().getPersistenceManager();
		Transaction transaction = pManager.currentTransaction();
		transaction.begin();

		String queryStr = "select from " + Counter.class.getName()
				+ " WHERE nombre == :name";

		List<Counter> contadores = (List<Counter>) pManager.newQuery(queryStr).execute(name);

		transaction.commit();

		pManager.close();

		if (contadores.isEmpty())
			contador = null;
		else
			contador = contadores.get(0);
		return contador;
	}


	public void createCounter(Counter c) {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			pm.makePersistent(c);
		} finally {
			pm.close();
		}
	}
	
	public void deleteCounter(Counter c) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistent(pm.getObjectById(c.getClass(), c.getKey().getId()));
		pm.close();

	}

}
