package com.ste.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.ste.beans.Cliente;
import com.ste.dao.ClienteDao;

public class ClienteServlet extends HttpServlet {
	
public void doGet(HttpServletRequest req, HttpServletResponse resp){
		
		JSONObject json = new JSONObject();
		
		String accion = req.getParameter("accion");
		
		 try {
			 
			HttpSession sesion = req.getSession();
			int sesionpermiso = (int) sesion.getAttribute("permiso");			 
			 
			 if (accion.equals("new")){
					createClient(req,resp);
				}else if (accion.equals("delete")){
					deleteClient(req,resp);
				}else if (accion.equals("update")){
					updateClient(req,resp);
				}else if (accion.equals("xls")){
				//	generateXLS(req,resp);
				}
			 
		
		} catch (Exception e) {
			e.printStackTrace();
		}			
		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		doGet(req,resp);
	}
	
	public void deleteClient(HttpServletRequest req, HttpServletResponse resp) throws JSONException, IOException{
		
		JSONObject json = new JSONObject();
		String str_id = req.getParameter("id");
		
		ClienteDao cDao = ClienteDao.getInstance();
		Cliente c = cDao.getClientebyId(Long.parseLong(str_id));
		
		cDao.deleteCliente(c);
		
		try{
			json.append("success", "true");
			
			
			resp.setCharacterEncoding("UTF-8");
	        resp.setContentType("application/json");       
			resp.getWriter().println(json);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	
	}
	
	public void updateClient(HttpServletRequest req, HttpServletResponse resp) throws JSONException, IOException{
		
		JSONObject json = new JSONObject();
		
		String str_id = req.getParameter("id");
		
		ClienteDao cDao = ClienteDao.getInstance();
		Cliente c = cDao.getClientebyId(Long.parseLong(str_id));
		
		String str_fecha_alta = req.getParameter("fecha_alta");		
		String nombre = req.getParameter("nombre_cliente");
		
		nombre = nombre.substring(0,1).toUpperCase() + nombre.substring(1,nombre.length()-1);
		
		String premium = req.getParameter("premium_modal");
		String tipo_cliente = req.getParameter("tipo_cliente");
		
		c.setStr_fecha_alta(str_fecha_alta);
		c.setNombre(nombre);
		c.setTipo_cliente(tipo_cliente);
		
		c.setPremium(premium);
		
		cDao.createCliente(c);
		
		json.append("success", "true");
	
		
		resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");       
		resp.getWriter().println(json);
				
	}
	
	public void createClient(HttpServletRequest req, HttpServletResponse resp) throws JSONException, IOException{
		JSONObject json = new JSONObject();
		ClienteDao cDao = ClienteDao.getInstance();
		Cliente c = new Cliente();
		
		String str_fecha_alta = req.getParameter("fecha_alta");		
		String nombre = req.getParameter("nombre_cliente");
		String premium = req.getParameter("premium");
		String tipo_cliente = req.getParameter("tipo_cliente");
		
		c.setStr_fecha_alta(str_fecha_alta);
		c.setNombre(nombre);
		c.setTipo_cliente(tipo_cliente);
		
		
		c.setPremium(premium);
	
		
		
		
		cDao.createCliente(c);
		
		
		json.append("success", "true");
	
		
		resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");       
		resp.getWriter().println(json);
	}

}
