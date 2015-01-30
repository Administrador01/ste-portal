package com.ste.defaultConf;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.ste.counters.Counter;
import com.ste.dao.CounterDao;

public class DefaultConf extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8059778774757795508L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		HttpSession sesion = req.getSession();
		JSONObject json = new JSONObject();
		
		try {
		
		String mail = (String) sesion.getAttribute("mail");
		
		if ("david.martin.beltran.contractor@bbva.com".equals(mail)||"roberto.gonzalez.roman.contractor@bbva.com".equals(mail)){
			CounterDao cDao = CounterDao.getInstance();
			Counter contadorSoporte = cDao.getCounterByName("soporte");
			//instancia para anadir nuevos contadores+
			if (contadorSoporte == null){
				contadorSoporte = new Counter();
			}
			contadorSoporte.setNombre("soporte");
			contadorSoporte.setValue(1);
			cDao.createCounter(contadorSoporte);
			
			
			Counter contadorCliente = cDao.getCounterByName("cliente");
			
			if (contadorCliente == null){
				contadorCliente = new Counter();
			}
			contadorCliente.setNombre("cliente");
			contadorCliente.setValue(1);
			cDao.createCounter(contadorCliente);
			
			Counter contadorPrueba = cDao.getCounterByName("prueba");
			
			if (contadorPrueba == null){
				contadorPrueba = new Counter();
			}
			contadorPrueba.setNombre("prueba");
			contadorPrueba.setValue(1);
			cDao.createCounter(contadorPrueba);			
			
			
			Counter contadorImp = cDao.getCounterByName("implementacion");
			
			if (contadorImp == null){
				contadorImp = new Counter();
			}
			contadorImp.setNombre("implementacion");
			contadorImp.setValue(1);
			cDao.createCounter(contadorImp);	
			json.append("success", true);
			
		}else{
			json.append("failure", true);

		}

		resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");       
		resp.getWriter().println(json);
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp){
		doPost(req,resp);
	}
	
	

}
