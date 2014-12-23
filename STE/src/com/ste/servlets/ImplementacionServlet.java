package com.ste.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

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
				
				if (accion.equals("update")){
					modifImplementacion(req,resp,usermail);
				}
				
				if (accion.equals("xls")){
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
			
			if (normalizador_str.equals("Si"))normalizador = true;
			if (firma_str.equals("Si"))firma = true;
			
			
			
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
		

			String id = req.getParameter("id");
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
			String normalizador_str = req.getParameter("normalizador_modal");
			String firma_str = req.getParameter("firma_modal");
			
			boolean normalizador=false;
			boolean firma = false;
			
			if (normalizador_str == "Si")normalizador = true;
			if (firma_str == "Si")firma = true;
			
			
			
			ImplementacionDao impDao = ImplementacionDao.getInstance();
			Implementacion imp = impDao.getImplementacionById(Long.parseLong(id));
			
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
			
			Utils.writeLog(usermail, "Modifica", "Implementacion", Long.toString(imp.getServicio_id()));
			
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
	public void generateXLS(HttpServletRequest req, HttpServletResponse resp, String usermail)
			throws ServletException, IOException {
		OutputStream out = null;
		try {
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition",
					"attachment; filename=GestionPruebasSTE.xls");

			WritableWorkbook w = Workbook
					.createWorkbook(resp.getOutputStream());

			ImplementacionDao impDao = ImplementacionDao.getInstance();
			List<Implementacion> implementaciones = impDao.getAllImplementaciones();

			WritableSheet s = w.createSheet("Gestion de pruebas", 0);

			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.WHITE);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
			cellFormat.setBackground(Colour.BLUE);
			cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
			cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

			s.setColumnView(0, 16);
			s.setColumnView(1, 20);
			s.setColumnView(2, 20);
			s.setColumnView(3, 20);
			s.setColumnView(4, 20);
			s.setColumnView(5, 20);
			s.setColumnView(6, 20);
			s.setColumnView(7, 20);
			s.setColumnView(8, 40);
			s.setColumnView(9, 40);

			
			s.setRowView(0, 900);

			s.addCell(new Label(0, 0, "FECHA ALTA", cellFormat));
			s.addCell(new Label(1, 0, "CLIENTE", cellFormat));
			s.addCell(new Label(2, 0, "TIPO SERVICIO", cellFormat));
			s.addCell(new Label(3, 0, "ESTADO", cellFormat));
			s.addCell(new Label(4, 0, "PRODUCTO", cellFormat));
			s.addCell(new Label(5, 0, "ENTORNO", cellFormat));
			s.addCell(new Label(6, 0, "IDENTIFICADOR", cellFormat));
			s.addCell(new Label(7, 0, "TIPO CLIENTE", cellFormat));
			s.addCell(new Label(8, 0, "DESCRIPCIÓN", cellFormat));
			s.addCell(new Label(9, 0, "SOLUCIÓN", cellFormat));
			
			int aux = 1;

			for ( Implementacion imp : implementaciones) {
				/*
				s.addCell(new Label(0, aux,));
				s.addCell(new Label(1, aux, ));
				s.addCell(new Label(2, aux, ));
				s.addCell(new Label(3, aux, ));
				s.addCell(new Label(4, aux, ));
				s.addCell(new Label(5, aux, ));
				s.addCell(new Label(6, aux, ));				
				s.addCell(new Label(7, aux, ));
				s.addCell(new Label(8, aux, ));
				s.addCell(new Label(9, aux, ));

*/

				aux++;
			}

			w.write();
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Exception in Excel", e);
		} finally {
			Utils.writeLog(usermail, "Descarga XML", "Pruebas","");
			if (out != null)
				out.close();
		}

	}	
	
}
