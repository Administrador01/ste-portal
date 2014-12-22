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
		

			
			String cliente_id = req.getParameter("cliente");
			String producto = req.getParameter("producto_imp");
			String servicio_id = req.getParameter("servicio");
			String estado = req.getParameter("estado_imp");
			String fecha_alta = req.getParameter("fecha_alta");
			String pais = req.getParameter("pais");
			String ref_glob = req.getParameter("ref_glo");
			String ref_loc = req.getParameter("ref_loc");
			String ref_ext = req.getParameter("ref_ext");
			String gestor_gcs = req.getParameter("gestor_gcs");
			String gestor_prom = req.getParameter("gestor_prom");
			String gestor_rel = req.getParameter("gestor_relacion");
			String detalle = req.getParameter("detalle");
			String asunto = req.getParameter("asunto");
			String contrat_adeud= req.getParameter("contrat_adeud");
			String id_acred = req.getParameter("id_acred");
			String cuent_abon = req.getParameter("cuent_abon");
			String fecha_contrat = req.getParameter("fecha_contrat");
			String fecha_subid = req.getParameter("fecha_subid");
			String normalizador_str = req.getParameter("normalizador");
			String firma_str = req.getParameter("firma");
			
			boolean normalizador=false;
			boolean firma = false;
			
			if (normalizador_str == "Si")normalizador = true;
			if (firma_str == "Si")firma = true;
			
			
			
			ImplementacionDao impDao = ImplementacionDao.getInstance();
			Implementacion imp = new Implementacion();
			
			imp.setAdeudos_ref_ext(contrat_adeud);
			imp.setAsunto_ref_ext(asunto);
			imp.setCliente_id(Long.parseLong(cliente_id));
			imp.setCuenta_ref_ext(cuent_abon);
			imp.setDetalle(detalle);
			imp.setEstado(estado);
			imp.setStr_fech_alta(fecha_alta);
			if (gestor_gcs==null ||gestor_gcs==""){
				gestor_gcs="No asignado";
			}
			imp.setGestor_gcs(gestor_gcs);
			imp.setGestor_promocion(gestor_prom);
			imp.setGestor_relacion(gestor_rel);
			imp.setPais(pais);
			imp.setReferencia_global(ref_glob);
			imp.setReferencia_local(ref_loc);
			imp.setReferencia_externa(ref_ext);
			imp.setServicio_id(Long.parseLong(servicio_id));
			imp.setStr_fech_contratacion(fecha_contrat);
			imp.setStr_fech_subida(fecha_subid);
			imp.setProducto(producto);
			imp.setNormalizador(normalizador);
			imp.setFirma_contrato(firma);
			imp.setAcreedor_ref_ext(id_acred);

			

			
			impDao.createImplementacion(imp, usermail);;
			
			Utils.writeLog(usermail, "Crea", "Implementacion", Long.toString(imp.getServicio_id()));
			
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

			

			
			impDao.createImplementacion(imp, usermail);;
			
			Utils.writeLog(usermail, "Crea", "Implementacion",Long.toString(imp.getServicio_id()));
			
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
