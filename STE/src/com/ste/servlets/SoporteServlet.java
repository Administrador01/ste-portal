package com.ste.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
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
import com.ste.beans.Soporte;
import com.ste.dao.ClienteDao;
import com.ste.dao.SoporteDao;
import com.ste.utils.Utils;

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
			String usermail = (String) sesion.getAttribute("mail");
			 
			 if (accion.equals("new")){
					createSoporte(req,resp,usermail);
				}else if (accion.equals("delete")){
					deleteSoporte(req,resp,usermail);
				}else if (accion.equals("update")){
					updateSoporte(req,resp,usermail);
				}else if (accion.equals("xls")){
					generateXLS(req,resp,usermail);
				}else if (accion.equals("restore")){
					restore(req,resp,usermail);
				}else if (accion.equals("clone")){
					cloneSoporte(req,resp,usermail);
				}
			 
		
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		doGet(req,resp);
	}
	
	public void generateXLS(HttpServletRequest req, HttpServletResponse resp, String usermail)
			throws ServletException, IOException {
		OutputStream out = null;
		try {
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition",
					"attachment; filename=GestionSoporteSTE.xls");

			WritableWorkbook w = Workbook
					.createWorkbook(resp.getOutputStream());

			SoporteDao sDao = SoporteDao.getInstance();
			List<Soporte> soportes = sDao.getAllSoportes();

			WritableSheet s = w.createSheet("Gestion de soporte", 0);

			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.WHITE);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
			cellFormat.setBackground(Colour.BLUE);
			cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
			cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

			s.setColumnView(0, 20);
			s.setColumnView(1, 20);
			s.setColumnView(2, 20);
			s.setColumnView(3, 20);
			s.setColumnView(4, 30);
			s.setColumnView(5, 40);
			s.setColumnView(6, 20);
			s.setColumnView(7, 20);
			s.setColumnView(8, 20);
			s.setColumnView(9, 20);
			s.setColumnView(10, 20);
			s.setColumnView(11, 20);
			s.setColumnView(12, 40);
			s.setColumnView(13, 40);

			s.setRowView(0, 900);

			s.addCell(new Label(0, 0, "IDENTIFICADOR", cellFormat));
			s.addCell(new Label(1, 0, "CLIENTE", cellFormat));
			s.addCell(new Label(2, 0, "TIPO CLIENTE", cellFormat));
			s.addCell(new Label(3, 0, "TIPO SOPORTE", cellFormat));
			s.addCell(new Label(4, 0, "FECHA INICIO", cellFormat));
			s.addCell(new Label(5, 0, "FECHA FIN", cellFormat));
			s.addCell(new Label(6, 0, "TIPO SERVICIO", cellFormat));
			s.addCell(new Label(7, 0, "ESTADO", cellFormat));
			s.addCell(new Label(8, 0, "SEGMENTO", cellFormat));
			s.addCell(new Label(9, 0, "PRODUCTO/CANAL", cellFormat));
			s.addCell(new Label(10, 0, "PAÍS", cellFormat));
			s.addCell(new Label(11, 0, "PETICIONARIO", cellFormat));
			s.addCell(new Label(12, 0, "DESCRIPCIÓN", cellFormat));
			s.addCell(new Label(13, 0, "SOLUCIÓN", cellFormat));
			
			int aux = 1;

			for (Soporte sop : soportes) {
				
				s.addCell(new Label(0, aux, sop.getId_soporte()));
				s.addCell(new Label(1, aux, sop.getCliente_name()));
				s.addCell(new Label(2, aux, sop.getPremium()));
				s.addCell(new Label(3, aux, sop.getTipo_soporte()));
				s.addCell(new Label(4, aux, sop.getStr_fecha_inicio()));
				s.addCell(new Label(5, aux, sop.getStr_fecha_fin()));
				s.addCell(new Label(6, aux, sop.getTipo_servicio()));
				s.addCell(new Label(7, aux, sop.getEstado()));
				s.addCell(new Label(8, aux, sop.getTipo_cliente()));
				s.addCell(new Label(9, aux, sop.getProducto_canal()));
				s.addCell(new Label(10, aux, sop.getPais()));
				s.addCell(new Label(11, aux, sop.getPeticionario()));
				s.addCell(new Label(12, aux, sop.getDetalles()));
				s.addCell(new Label(13, aux, sop.getSolucion()));
				
				
				


				aux++;
			}

			w.write();
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Exception in Excel", e);
		} finally {
			Utils.writeLog(usermail, "Descarga XML", "Soporte","");
			if (out != null)
				out.close();
		}

	}

	
	public void deleteSoporte (HttpServletRequest req, HttpServletResponse resp, String usermail){
		JSONObject json = new JSONObject();
		
		String id = req.getParameter("id");
		SoporteDao sDao = SoporteDao.getInstance();
		
		Soporte s = sDao.getSoportebyId(Long.parseLong(id));
		
		Utils.writeLog(usermail, "Elimina", "Soporte", s.getId_soporte()+" "+s.getCliente_name());
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
	
	public void restore(HttpServletRequest req, HttpServletResponse resp, String usermail) throws JSONException, IOException, ParseException{
		
		JSONObject json = new JSONObject();
		String str_id = req.getParameter("id");
		
		SoporteDao cDao = SoporteDao.getInstance();
		Soporte c = cDao.getSoportebyId(Long.parseLong(str_id));
		//registramos la operacion
		Utils.writeLog(usermail, "Restaura", "soporte", c.getId_soporte());
		
		
		c.setErased(false);
		cDao.updateSoporte(c);
		
		try{
			json.append("success", "true");
			
			
			resp.setCharacterEncoding("UTF-8");
	        resp.setContentType("application/json");       
			resp.getWriter().println(json);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	
	}
	
	public void updateSoporte(HttpServletRequest req, HttpServletResponse resp, String usermail) throws ParseException{
		
		JSONObject json = new JSONObject();
		
		String id_str = req.getParameter("id");
		SoporteDao sDao = SoporteDao.getInstance();
		Soporte s = sDao.getSoportebyId(Long.parseLong(id_str));
		
		Utils.writeLog(usermail, "Modifica", "Soporte", s.getId_soporte()+" "+s.getCliente_name());
		
		String fecha_inicio = req.getParameter("fecha_inicio");
		String fecha_fin = req.getParameter("fecha_fin");
		String tipo = req.getParameter("tipoModal");
		String cliente = req.getParameter("cliente");
		String estado = req.getParameter("estado");
		String tipo_servicio = req.getParameter("tipo_servicio");
		String producto_canal = req.getParameter("producto_canal");
		String premium = req.getParameter("input-premium-soporte");
		String tipo_cliente = req.getParameter("tipo_cliente");		
		String detalles = req.getParameter("detalles");
		String solucion = req.getParameter("solucion");
		String clienteID = req.getParameter("client_id");
		String pais = req.getParameter("pais");
		String peticionario = req.getParameter("peticionario");
		
		s.setStr_fecha_inicio(fecha_inicio);
		s.setStr_fecha_fin(fecha_fin);
		s.setTipo_soporte(tipo);
		s.setCliente_name(cliente);
		s.setEstado(estado);
		s.setTipo_servicio(tipo_servicio);
		s.setProducto_canal(producto_canal);
		s.setDetalles(detalles);
		s.setPremium(premium);
		s.setTipo_cliente(tipo_cliente);
		s.setSolucion(solucion);
		s.setCliente_id(clienteID);
		s.setPais(pais);
		s.setPeticionario(peticionario);
		
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
	
public void cloneSoporte(HttpServletRequest req, HttpServletResponse resp, String usermail){
		
		JSONObject json = new JSONObject();
		
		
		SoporteDao sDao = SoporteDao.getInstance();
		Soporte s = new Soporte();
		
		Utils.writeLog(usermail, "Clona", "Soporte", "");
		
		String fecha_inicio = req.getParameter("fecha_inicio");
		String fecha_fin = req.getParameter("fecha_fin");
		String tipo = req.getParameter("tipoModal");
		String cliente = req.getParameter("cliente");
		String estado = req.getParameter("estado");
		String tipo_servicio = req.getParameter("tipo_servicio");
		String producto_canal = req.getParameter("producto_canal");
		String premium = req.getParameter("input-premium-soporte");
		String tipo_cliente = req.getParameter("tipo_cliente");		
		String detalles = req.getParameter("detalles");
		String solucion = req.getParameter("solucion");
		String clienteID = req.getParameter("client_id");
		String pais = req.getParameter("pais");
		String peticionario = req.getParameter("peticionario");
		
		s.setStr_fecha_inicio(fecha_inicio);
		s.setStr_fecha_fin(fecha_fin);
		s.setTipo_soporte(tipo);
		s.setCliente_name(cliente);
		s.setEstado(estado);
		s.setTipo_servicio(tipo_servicio);
		s.setProducto_canal(producto_canal);
		s.setDetalles(detalles);
		s.setPremium(premium);
		s.setTipo_cliente(tipo_cliente);
		s.setSolucion(solucion);
		s.setCliente_id(clienteID);
		s.setPais(pais);
		s.setPeticionario(peticionario);
		
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
	
	public void createSoporte(HttpServletRequest req, HttpServletResponse resp, String usermail){
		JSONObject json = new JSONObject();
		
		String fecha_inicio = req.getParameter("fecha_inicio");
		String fecha_fin = req.getParameter("fecha_fin");
		String tipo = req.getParameter("tipo");
		String cliente = req.getParameter("cliente");
		String estado = req.getParameter("estado");
		String tipo_servicio = req.getParameter("tipo_servicio");
		String producto_canal = req.getParameter("producto_canal");
		String premium = req.getParameter("input-premium-soporte");
		String tipo_cliente = req.getParameter("tipo_cliente");		
		String detalles = req.getParameter("detalles");
		String solucion = req.getParameter("solucion");
		String clienteID = req.getParameter("client_id");
		String pais = req.getParameter("pais");
		String peticionario = req.getParameter("peticionario");
		Soporte s = new Soporte();
		SoporteDao sDao = SoporteDao.getInstance();
		
		if(tipo == ""||tipo == null)tipo = "Incidencia";
		
		s.setErased(false);
		s.setStr_fecha_inicio(fecha_inicio);
		s.setStr_fecha_fin(fecha_fin);
		s.setTipo_soporte(tipo);
		s.setCliente_name(cliente);
		s.setEstado(estado);
		s.setTipo_servicio(tipo_servicio);
		s.setProducto_canal(producto_canal);
		s.setDetalles(detalles);
		s.setPremium(premium);
		s.setTipo_cliente(tipo_cliente);
		s.setSolucion(solucion);
		s.setCliente_id(clienteID);
		s.setPais(pais);
		s.setPeticionario(peticionario);
		
		
		sDao.createSoporte(s);
		
		Utils.writeLog(usermail, "Crea", "Soporte", s.getId_soporte()+" "+s.getCliente_name());
		
		
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
