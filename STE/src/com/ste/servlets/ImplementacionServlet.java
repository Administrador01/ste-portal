package com.ste.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.ste.beans.Implementacion;
import com.ste.beans.Prueba;
import com.ste.dao.ImplementacionDao;
import com.ste.dao.PruebaDao;
import com.ste.utils.Utils;

public class ImplementacionServlet extends HttpServlet{

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp){

		JSONObject json = new JSONObject();
		
		String accion = req.getParameter("accion");
			
		 try {
			 
			HttpSession sesion = req.getSession();
			int sesionpermiso = (int) sesion.getAttribute("permiso");			 
			String usermail = (String) sesion.getAttribute("mail");
			

			
				if (accion.equals("new")){
					createImplementacion(req,resp,usermail);
				}
				
				if (accion.equals("modif")){
					modifImplementacion(req,resp,usermail);
				}

			
		} catch (Exception e) {

			e.printStackTrace();
		}		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		doGet(req,resp);
	}
	
	public void createImplementacion(HttpServletRequest req, HttpServletResponse resp, String usermail) throws InterruptedException{
		JSONObject json = new JSONObject();
		

			
			String fecha_estado = req.getParameter("fecha_estado");

			
			
			ImplementacionDao impDao = ImplementacionDao.getInstance();
			Implementacion imp = new Implementacion();
			
			
			imp.getAdeudosRefExt();

			

			
			impDao.createImplementacion(imp, usermail);;
			
			Utils.writeLog(usermail, "Crea", "Implementacion", Long.toString(imp.getServicioId()));
			
			try {
				json.append("success", "true");
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
	
	public void modifImplementacion(HttpServletRequest req, HttpServletResponse resp, String usermail) throws InterruptedException{
		JSONObject json = new JSONObject();
		

			
			String fecha_estado = req.getParameter("fecha_estado");

			
			
			ImplementacionDao impDao = ImplementacionDao.getInstance();
			Implementacion imp = new Implementacion();
			
			
			imp.getAdeudosRefExt();

			

			
			impDao.createImplementacion(imp, usermail);;
			
			Utils.writeLog(usermail, "Crea", "Implementacion",Long.toString(imp.getServicioId()));
			
			try {
				json.append("success", "true");
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
	
	
}
