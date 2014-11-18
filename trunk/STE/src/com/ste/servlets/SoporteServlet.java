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
					generateXLS(req,resp);
				}
			 
		
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		doGet(req,resp);
	}
	
	public void generateXLS(HttpServletRequest req, HttpServletResponse resp)
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

			s.setColumnView(0, 16);
			s.setColumnView(1, 30);
			s.setColumnView(2, 20);
			s.setColumnView(3, 20);
			s.setColumnView(4, 20);
			s.setColumnView(5, 20);
			s.setColumnView(6, 20);
			s.setColumnView(7, 20);
			s.setColumnView(8, 30);
			
			s.setRowView(0, 900);

			s.addCell(new Label(0, 0, "IDENTIFICADOR", cellFormat));
			s.addCell(new Label(1, 0, "CLIENTE", cellFormat));
			s.addCell(new Label(2, 0, "FECHA INICIO", cellFormat));
			s.addCell(new Label(3, 0, "FECHA FIN", cellFormat));
			s.addCell(new Label(4, 0, "PREMIUM", cellFormat));
			s.addCell(new Label(5, 0, "ESTADO", cellFormat));
			s.addCell(new Label(6, 0, "SERVICIO", cellFormat));
			s.addCell(new Label(7, 0, "PORIDUCTO/CANAL", cellFormat));
			s.addCell(new Label(8, 0, "DETALLES", cellFormat));
			

			int aux = 1;

			for (Soporte sop : soportes) {
				
				s.addCell(new Label(0, aux, sop.getId_prueba()));
				s.addCell(new Label(1, aux, sop.getCliente_name()));
				s.addCell(new Label(2, aux, sop.getStr_fecha_inicio()));
				s.addCell(new Label(3, aux, sop.getStr_fecha_fin()));
				s.addCell(new Label(4, aux, sop.getPremium()));
				s.addCell(new Label(5, aux, sop.getEstado()));
				s.addCell(new Label(6, aux, sop.getTipo_servicio()));
				s.addCell(new Label(7, aux, sop.getTipo_servicio()));
				s.addCell(new Label(8, aux, sop.getDetalles()));

				

				aux++;
			}

			w.write();
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Exception in Excel", e);
		} finally {
			if (out != null)
				out.close();
		}

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
		String premium = req.getParameter("premium");
		
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
		s.setPremium(premium);
		
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
