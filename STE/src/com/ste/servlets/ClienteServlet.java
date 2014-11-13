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
				//	deleteClient(req,resp);
				}else if (accion.equals("update")){
				//	updateClient(req,resp);
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
	
	public void createClient(HttpServletRequest req, HttpServletResponse resp) throws JSONException, IOException{
		JSONObject json = new JSONObject();
		
		String fecha_inicio = req.getParameter("fecha_inicio");
		String fecha_fin = req.getParameter("fecha_fin");
		
		String cliente = req.getParameter("cliente");
		String estado = req.getParameter("estado");
		String tipo_servicio = req.getParameter("tipo_servicio");
		String producto_canal = req.getParameter("producto_canal");
		
		String detalles = req.getParameter("detalles");
		
		Cliente c = new Cliente();
		ClienteDao cDao = ClienteDao.getInstance();
		
		c.setStr_fecha_inicio(fecha_inicio);
		c.setStr_fecha_fin(fecha_fin);
		c.setCliente(cliente);
		c.setEstado(estado);
		c.setTipo_servicio(tipo_servicio);
		c.setProducto_canal(producto_canal);
		c.setDetalles(detalles);
		
		cDao.createCliente(c);
		
		
		json.append("success", "true");
	
		
		resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");       
		resp.getWriter().println(json);
	}

}
