package com.ste.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.ste.beans.Servicio;
import com.ste.dao.ServicioDao;

public class ServicioServlet  extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse resp){

		JSONObject json = new JSONObject();
		
		String accion = req.getParameter("accion");
			
		 try {
			 
			HttpSession sesion = req.getSession();
			int sesionpermiso = (int) sesion.getAttribute("permiso");			 
			String usermail = (String) sesion.getAttribute("mail");
			

			
				if (accion.equals("inicial")){
					createServicio(req,resp,usermail);
				}


			
		} catch (Exception e) {

			e.printStackTrace();
		}		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		doGet(req,resp);
	}
	public void createServicio(HttpServletRequest req, HttpServletResponse resp, String usermail) throws InterruptedException{
		
		ServicioDao sDao = ServicioDao.getInstance();
		Servicio s = new Servicio();
		String nombre = "ADEUDOS B2B (SEPA) PARIS";
		String tipo = "Envio";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);		
	}
	
}
