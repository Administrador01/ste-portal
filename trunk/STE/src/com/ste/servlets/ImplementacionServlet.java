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
import com.ste.beans.Cliente;
import com.ste.beans.Implementacion;
import com.ste.beans.Prueba;
import com.ste.beans.Servicio;
import com.ste.dao.ClienteDao;
import com.ste.dao.ImplementacionDao;
import com.ste.dao.PruebaDao;
import com.ste.dao.ServicioDao;
import com.ste.utils.Utils;

public class ImplementacionServlet extends HttpServlet{

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp){

		JSONObject json = new JSONObject();
		
		String accion = req.getParameter("accion");
			
		 try {
			 
			HttpSession sesion = req.getSession();
			int sesionpermiso = (int) sesion.getAttribute("permiso");			 
			String usermail = (String) sesion.getAttribute("mail");
			String id = (String) sesion.getAttribute("id");

			
				if (accion.equals("new")){
					createImplementacion(req,resp,usermail);
				}
				
				if (accion.equals("update")){
					modifImplementacion(req,resp,usermail);
				}
				
				if (accion.equals("xls")){
					generateXLS(req,resp,usermail);
				}
				
				if (accion.equals("xlsServ")){
					generateXLSserv(req,resp,usermail);
				}
				if (accion.equals("delete")){
					deleteImplementacion(req,resp,usermail);
				}
				if (accion.equals("restore")){
					restoreImplementacion(req,resp,usermail);
				}
				
				if (accion.equals("clone")){
					cloneImplementacion(req,resp,usermail);
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
			if (normalizador_str.equals(null)||normalizador_str.equals(""))normalizador_str = "No";
			if (firma_str.equals("")||firma_str.equals(null))firma_str = "No";
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
			
			if(servicio_id!=null&&!servicio_id.equals("")){
				imp.setServicio_id(Long.parseLong(servicio_id));
				Servicio serv = ServicioDao.getInstance().getImplementacionById(Long.parseLong(servicio_id));
				imp.setServicio_name(serv.getName());
			}
			
			if(cliente_id!=null&&!cliente_id.equals("")){
				imp.setCliente_id(Long.parseLong(cliente_id));
				Cliente serv = ClienteDao.getInstance().getClientebyId(Long.parseLong(cliente_id));
				imp.setClient_name(serv.getNombre());
			}
			imp.setStr_fech_contratacion(fecha_contrat);
			imp.setStr_fech_subida(fecha_subid);
			imp.setProducto(producto);
			imp.setNormalizador(normalizador);
			imp.setFirma_contrato(firma);
			imp.setAcreedor_ref_ext(id_acred);

			

			
			impDao.createImplementacion(imp, usermail);
			
			Utils.writeLog(usermail, "Crea", "Implementacion", Long.toString(imp.getServicio_id()));
			
			try {
				json.append("success", "true");
				resp.setCharacterEncoding("UTF-8");
		        resp.setContentType("application/json");       
				resp.getWriter().println(json);

				
			} catch (JSONException e) {

				e.printStackTrace();
			} catch (IOException e) {

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
			if (normalizador_str == null)normalizador_str = "No";
			if (firma_str == null)firma_str = "No";
			if (normalizador_str.equals("Si"))normalizador = true;
			if (firma_str.endsWith("Si"))firma = true;
			
			
			
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
			if(servicio_id!=null&&!servicio_id.equals("")){
				imp.setServicio_id(Long.parseLong(servicio_id));
				Servicio serv = ServicioDao.getInstance().getImplementacionById(Long.parseLong(servicio_id));
				imp.setServicio_name(serv.getName());
			}
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
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			

	}
	
	
	public void cloneImplementacion(HttpServletRequest req, HttpServletResponse resp, String usermail) throws InterruptedException{
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
			String normalizador_str = req.getParameter("normalizador_modal");
			String firma_str = req.getParameter("firma_modal");
			
			ClienteDao clienteDao = ClienteDao.getInstance();
			Cliente cliente = null;
			try{
				cliente = clienteDao.getClientebyId(Long.parseLong(cliente_id));
			}catch(Exception e){
				
			}
			
			
			
			boolean normalizador=false;
			boolean firma = false;
			if (normalizador_str == null)normalizador_str = "No";
			if (firma_str == null)firma_str = "No";
			if (normalizador_str.equals("Si"))normalizador = true;
			if (firma_str.endsWith("Si"))firma = true;
			
			
			
			ImplementacionDao impDao = ImplementacionDao.getInstance();
			Implementacion imp = new Implementacion();
			
			
			imp.setClient_name(cliente.getNombre());
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
			if(servicio_id!=null&&!servicio_id.equals("")){
				imp.setServicio_id(Long.parseLong(servicio_id));
				Servicio serv = ServicioDao.getInstance().getImplementacionById(Long.parseLong(servicio_id));
				imp.setServicio_name(serv.getName());
			}
			imp.setStr_fech_contratacion(fecha_contrat);
			imp.setStr_fech_subida(fecha_subid);
			imp.setProducto(producto);
			imp.setNormalizador(normalizador);
			imp.setFirma_contrato(firma);
			imp.setAcreedor_ref_ext(id_acred);

			

			
			impDao.createImplementacion(imp, usermail);
			
			Utils.writeLog(usermail, "Clona", "Implementacion","");
			
			try {
				json.append("success", "true");
				resp.setCharacterEncoding("UTF-8");
		        resp.setContentType("application/json");       
				resp.getWriter().println(json);

				
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			

	}
	
	
	public void generateXLS(HttpServletRequest req, HttpServletResponse resp, String usermail)
			throws ServletException, IOException {
		OutputStream out = null;
		try {
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition",
					"attachment; filename=GestionImplementacionesSTE.xls");

			WritableWorkbook w = Workbook
					.createWorkbook(resp.getOutputStream());

			ImplementacionDao impDao = ImplementacionDao.getInstance();
			List<Implementacion> implementaciones = impDao.getAllImplementaciones();
			
			ClienteDao cliDao = ClienteDao.getInstance();

			
			ServicioDao servDao = ServicioDao.getInstance();


			WritableSheet s = w.createSheet("Gestion de implementaciones", 0);

			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.WHITE);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
			cellFormat.setBackground(Colour.BLUE);
			cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
			cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

			s.setColumnView(0, 20);
			s.setColumnView(1, 20);
			s.setColumnView(2, 50);
			s.setColumnView(3, 20);
			s.setColumnView(4, 20);
			s.setColumnView(5, 20);
			s.setColumnView(6, 20);
			s.setColumnView(7, 20);
			s.setColumnView(8, 30);
			s.setColumnView(9, 30);
			s.setColumnView(10, 20);
			s.setColumnView(11, 30);
			s.setColumnView(12, 30);
			s.setColumnView(13, 30);
			s.setColumnView(14, 30);
			s.setColumnView(15, 30);
			s.setColumnView(16, 30);
			s.setColumnView(17, 30);
			s.setColumnView(18, 30);
			s.setColumnView(19, 30);
			s.setColumnView(20, 50);
			s.setColumnView(21, 30);
			s.setColumnView(22, 30);
			s.setColumnView(23, 30);

			
			s.setRowView(0, 900);

			s.addCell(new Label(0, 0, "CLIENTE", cellFormat));
			s.addCell(new Label(1, 0, "PRODUCTO", cellFormat));
			s.addCell(new Label(2, 0, "SERVICIO", cellFormat));
			s.addCell(new Label(3, 0, "SEGMENTO", cellFormat));
			s.addCell(new Label(4, 0, "ESTADO", cellFormat));
			s.addCell(new Label(5, 0, "FECHA ALTA", cellFormat));
			s.addCell(new Label(6, 0, "PAIS", cellFormat));
			s.addCell(new Label(7, 0, "NORMALIZADOR", cellFormat));
			s.addCell(new Label(8, 0, "REFERENCIA GLOBAL", cellFormat));
			s.addCell(new Label(9, 0, "REFERENCIA LOCAL", cellFormat));
			s.addCell(new Label(10, 0, "FIRMA CONTRATO", cellFormat));
			s.addCell(new Label(11, 0, "GESTOR GCS", cellFormat));
			s.addCell(new Label(12, 0, "GESTOR PROMOCIÓN", cellFormat));
			s.addCell(new Label(13, 0, "GESTOR RELACIÓN", cellFormat));
			s.addCell(new Label(14, 0, "DETALLE", cellFormat));
			s.addCell(new Label(15, 0, "REFERENCIA EXTERNA", cellFormat));
			s.addCell(new Label(16, 0, "ASUNTO", cellFormat));
			s.addCell(new Label(17, 0, "CONTRATO ADEUDOS", cellFormat));
			s.addCell(new Label(18, 0, "ID ACREEDOR", cellFormat));
			s.addCell(new Label(19, 0, "CUENTA ABONO", cellFormat));
			s.addCell(new Label(20, 0, "SERVICIO", cellFormat));
			s.addCell(new Label(21, 0, "TIPO SERVICIO", cellFormat));
			s.addCell(new Label(22, 0, "FECHA CONTRATACION", cellFormat));
			s.addCell(new Label(23, 0, "FECHA SUBIDA", cellFormat));
			
			int aux = 1;

			for ( Implementacion imp : implementaciones) { 
				
				Cliente cliente = cliDao.getClientebyId(imp.getCliente_id());
				Servicio servicio = servDao.getImplementacionById(imp.getServicio_id());
				
				s.addCell(new Label(0, aux,cliente.getNombre()));
				s.addCell(new Label(1, aux,imp.getProducto() ));
				s.addCell(new Label(2, aux,servicio.getName()));
				s.addCell(new Label(3, aux,cliente.getTipo_cliente()));
				s.addCell(new Label(4, aux,imp.getEstado()));
				s.addCell(new Label(5, aux,imp.getStr_fecha_alta()));
				s.addCell(new Label(6, aux,imp.getPais()));
				if(imp.getNormalizador())s.addCell(new Label(7, aux,"Si"));else s.addCell(new Label(7, aux,"No"));
				s.addCell(new Label(8, aux,imp.getReferencia_global()));
				s.addCell(new Label(9, aux,imp.getReferencia_local()));
				if(imp.getFirma_contrato())s.addCell(new Label(10, aux,"Si"));else s.addCell(new Label(10, aux,"No"));
				s.addCell(new Label(11, aux,imp.getGestor_gcs()));
				s.addCell(new Label(12, aux,imp.getGestor_promocion()));
				s.addCell(new Label(13, aux,imp.getGestor_relacion()));
				s.addCell(new Label(14, aux,imp.getDetalle()));
				s.addCell(new Label(15, aux,imp.getReferencia_externa()));
				s.addCell(new Label(16, aux,imp.getAsunto_ref_ext()));
				s.addCell(new Label(17, aux,imp.getAdeudos_ref_ext()));
				s.addCell(new Label(18, aux,imp.getAcreedor_ref_ext()));
				s.addCell(new Label(19, aux,imp.getCuenta_ref_ext()));
				s.addCell(new Label(20, aux,servicio.getName()));
				s.addCell(new Label(21, aux,servicio.getTipo()));
				s.addCell(new Label(22, aux,imp.getStr_fech_contratacion()));
				s.addCell(new Label(23, aux,imp.getStr_fech_subida()));



				aux++;
			}

			w.write();
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Exception in Excel", e);
		} finally {
			Utils.writeLog(usermail, "Descarga XML", "Implementaciones","");
			if (out != null)
				out.close();
		}

	}	
	
	public void generateXLSserv(HttpServletRequest req, HttpServletResponse resp, String usermail)
			throws ServletException, IOException {
		OutputStream out = null;
		try {
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition",
					"attachment; filename=GestionServicios.xls");

			WritableWorkbook w = Workbook
					.createWorkbook(resp.getOutputStream());

			ImplementacionDao impDao = ImplementacionDao.getInstance();
			List<Implementacion> implementaciones = impDao.getAllImplementaciones();
			
			ClienteDao cliDao = ClienteDao.getInstance();

			
			ServicioDao servDao = ServicioDao.getInstance();


			WritableSheet s = w.createSheet("Servicios", 1);

			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.WHITE);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
			cellFormat.setBackground(Colour.BLUE);
			cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
			cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

			s.setColumnView(0, 20);
			s.setColumnView(1, 30);
			s.setColumnView(2, 40);
			s.setColumnView(3, 30);
			s.setColumnView(4, 45);
			s.setColumnView(5, 40);
			s.setColumnView(6, 20);


			
			s.setRowView(0, 900);
			s.addCell(new Label(0, 0, "ID CLIENTE", cellFormat));
			s.addCell(new Label(1, 0, "CLIENTE", cellFormat));
			s.addCell(new Label(2, 0, "FORMATO/SERVICIO", cellFormat));
			s.addCell(new Label(3, 0, "PAÍS", cellFormat));
			s.addCell(new Label(4, 0, "FECHA CONTRATACIÓN PRODUCCIÓN", cellFormat));
			s.addCell(new Label(5, 0, "FECHA SUBIDA A PRODUCCIÓN", cellFormat));
			s.addCell(new Label(6, 0, "NORMALIZADOR", cellFormat));

			
			int aux = 1;

			for ( Implementacion imp : implementaciones) {
				
				Cliente cliente = cliDao.getClientebyId(imp.getCliente_id());
				Servicio servicio = servDao.getImplementacionById(imp.getServicio_id());
				
				s.addCell(new Label(0, aux,cliente.getId_cliente() ));
				s.addCell(new Label(1, aux,cliente.getNombre()));
				s.addCell(new Label(2, aux,servicio.getName()));
				s.addCell(new Label(3, aux,imp.getPais()));
				s.addCell(new Label(4, aux,imp.getStr_fech_contratacion()));
				s.addCell(new Label(5, aux,imp.getStr_fech_subida()));
				if(imp.getNormalizador())s.addCell(new Label(6, aux,"Si"));else s.addCell(new Label(6, aux,"No"));
				



				aux++;
			}

			w.write();
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Exception in Excel", e);
		} finally {
			Utils.writeLog(usermail, "Descarga XML", "Servicios","");
			if (out != null)
				out.close();
		}

	}	
	
	
	public void deleteImplementacion(HttpServletRequest req, HttpServletResponse resp, String usermail)
			throws ServletException, IOException {
		
		JSONObject json = new JSONObject();
		
		String id=req.getParameter("id");
		ImplementacionDao impDao = ImplementacionDao.getInstance();
		Implementacion imp = impDao.getImplementacionById(Long.parseLong(id));
		imp.setErased(true);
		impDao.createImplementacionRaw(imp);
		
		Utils.writeLog(usermail, "Borra", "Implementacion", Long.toString(imp.getServicio_id()));
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
	
	public void restoreImplementacion(HttpServletRequest req, HttpServletResponse resp, String usermail)
			throws ServletException, IOException {
		
		JSONObject json = new JSONObject();
		
		String id=req.getParameter("id");
		ImplementacionDao impDao = ImplementacionDao.getInstance();
		Implementacion imp = impDao.getImplementacionById(Long.parseLong(id));
		imp.setErased(false);
		impDao.createImplementacionRaw(imp);
		
		Utils.writeLog(usermail, "Borra", "Implementacion", Long.toString(imp.getServicio_id()));
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
	
	/*
	
	public void deleteImplementacion(HttpServletRequest req, HttpServletResponse resp, String usermail)
			throws ServletException, IOException {
		
		JSONObject json = new JSONObject();
		
		String id=req.getParameter("id");
		ImplementacionDao impDao = ImplementacionDao.getInstance();
		Implementacion imp = impDao.getImplementacionById(Long.parseLong(id));
		
		
		Utils.writeLog(usermail, "Borra", "Implementacion", Long.toString(imp.getServicio_id()));
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

	}*/
	
	
}
