package com.ste.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.ste.beans.Soporte;
import com.ste.dao.SoporteDao;

public class SoporteServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6544096032936400799L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp){

		JSONObject json = new JSONObject();
		
		String accion = req.getParameter("accion");
			
		 try {
			 
			HttpSession sesion = req.getSession();
			int sesionpermiso = (int) sesion.getAttribute("permiso");			 
			 
			 if (accion.equals("new")){
					createSoporte(req,resp);
				}else if (accion.equals("delete")){
					deleteSoporte(req,resp);
				}else if (accion.equals("update")){
					updateSoporte(req,resp);
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
	
	public void deleteSoporte (HttpServletRequest req, HttpServletResponse resp){
		JSONObject json = new JSONObject();
		
		String id = req.getParameter("id");
		SoporteDao sDao = SoporteDao.getInstance();
		
		Soporte s = sDao.getSoportebyId(Long.parseLong(id));
		sDao.deleteSoporte(s);
		
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
	
	public void updateSoporte(HttpServletRequest req, HttpServletResponse resp){
		
		JSONObject json = new JSONObject();
		
		String id_str = req.getParameter("id");
		SoporteDao sDao = SoporteDao.getInstance();
		Soporte s = sDao.getSoportebyId(Long.parseLong(id_str));
		
		String fecha_inicio = req.getParameter("fecha_inicio");
		String fecha_fin = req.getParameter("fecha_fin");
		
		String cliente = req.getParameter("cliente");
		String estado = req.getParameter("estado");
		String tipo_servicio = req.getParameter("tipo_servicio");
		String producto_canal = req.getParameter("producto_canal");
		
		String detalles = req.getParameter("detalles");
		
		
		
		s.setStr_fecha_inicio(fecha_inicio);
		s.setStr_fecha_fin(fecha_fin);
		s.setCliente_name(cliente);
		s.setEstado(estado);
		s.setTipo_servicio(tipo_servicio);
		s.setProducto_canal(producto_canal);
		s.setDetalles(detalles);
		
		sDao.createSoporte(s);
		
		
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
	
	public void createSoporte(HttpServletRequest req, HttpServletResponse resp){
		JSONObject json = new JSONObject();
		
		String fecha_inicio = req.getParameter("fecha_inicio");
		String fecha_fin = req.getParameter("fecha_fin");
		
		String cliente = req.getParameter("cliente");
		String estado = req.getParameter("estado");
		String tipo_servicio = req.getParameter("tipo_servicio");
		String producto_canal = req.getParameter("producto_canal");
		
		String detalles = req.getParameter("detalles");
		
		Soporte s = new Soporte();
		SoporteDao sDao = SoporteDao.getInstance();
		
		s.setStr_fecha_inicio(fecha_inicio);
		s.setStr_fecha_fin(fecha_fin);
		s.setCliente_name(cliente);
		s.setEstado(estado);
		s.setTipo_servicio(tipo_servicio);
		s.setProducto_canal(producto_canal);
		s.setDetalles(detalles);
		
		sDao.createSoporte(s);
		
		
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
