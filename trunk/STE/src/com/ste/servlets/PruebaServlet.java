package com.ste.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.ste.beans.Prueba;
import com.ste.dao.PruebaDao;
import com.ste.utils.Utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

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

public class PruebaServlet extends HttpServlet{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp){


		
		String accion = req.getParameter("accion");
		
		 try {
			 
			HttpSession sesion = req.getSession();
			//int sesionpermiso = (int) sesion.getAttribute("permiso");			 
			String usermail = (String) sesion.getAttribute("mail");
			

			
				if (accion.equals("new")){
					createPrueba(req,resp,usermail);
				}else if (accion.equals("delete")){
					deletePrueba(req,resp,usermail);
				}else if (accion.equals("update")){
					updatePrueba(req,resp,usermail);
				}else if (accion.equals("xls")){
					generateXLS(req,resp,usermail);
				}
				
			
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		doGet(req,resp);
	}
	
	public void createPrueba(HttpServletRequest req, HttpServletResponse resp, String usermail) throws InterruptedException{
		JSONObject json = new JSONObject();
		

			
			String fecha_estado = req.getParameter("fecha_estado");
			String nombre_cliente = req.getParameter("cliente");
			String referencia = req.getParameter("referencia");
			String producto = req.getParameter("producto_canal");
			String premium = req.getParameter("input-premium-soporte");
			String estado = req.getParameter("estado");
			String entorno = req.getParameter("entorno");
			String detalles = req.getParameter("detalles");
			String solucion = req.getParameter("solucion");
			String clientID = req.getParameter("client_id");
			String tipo_servicio = req.getParameter("tipo_servicio");
			
			
			PruebaDao pDao = PruebaDao.getInstance();	
			Prueba p = new Prueba();
			
			p.setStr_fecha_estado(fecha_estado);
			p.setNombre_cliente(nombre_cliente);
			p.setReferencia(referencia);
			p.setProducto(producto);
			p.setPremium(premium);
			p.setEntorno(entorno);
			p.setEstado(estado);
			p.setDetalles(detalles);
			p.setSolucion(solucion);
			p.setIdCliente(clientID);
			p.setTipo_servicio(tipo_servicio);
			

			
			pDao.createPrueba(p);		
			
			Utils.writeLog(usermail, "Crea", "Prueba", p.getId_prueba());
			
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
	//ACTUALIZAR PRUEBA	
	public void updatePrueba(HttpServletRequest req, HttpServletResponse resp, String usermail){
		
		JSONObject json = new JSONObject();
		
		String id_str = req.getParameter("id");
		
		String fecha_estado = req.getParameter("fecha_estado");
		String nombre_cliente = req.getParameter("cliente");
		String referencia = req.getParameter("referencia");
		String producto = req.getParameter("producto_canal");
		String premium = req.getParameter("input-premium-soporte");
		String estado = req.getParameter("estado");
		String entorno = req.getParameter("entorno");
		String servicio =  req.getParameter("tipo_servicio");
		String detalles = req.getParameter("detalles");
		String solucion = req.getParameter("solucion");
		String clientID = req.getParameter("client_id_mod");
		
		
		PruebaDao pDao = PruebaDao.getInstance();	
		Prueba p = pDao.getPruebabyId(Long.parseLong(id_str));
		Utils.writeLog(usermail, "Modifica", "Prueba", p.getId_prueba());
		
		p.setStr_fecha_estado(fecha_estado);
		p.setNombre_cliente(nombre_cliente);
		p.setReferencia(referencia);
		p.setProducto(producto);
		p.setPremium(premium);
		p.setTipo_servicio(servicio);
		p.setEntorno(entorno);
		p.setEstado(estado);
		p.setDetalles(detalles);
		p.setSolucion(solucion);
		if (clientID !="" && clientID != null) p.setIdCliente(clientID);
		

		
		pDao.updatePrueba(p);
		
		
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
	
	public void deletePrueba (HttpServletRequest req, HttpServletResponse resp, String usermail){
		JSONObject json = new JSONObject();
		
		String id = req.getParameter("id");
		PruebaDao pruebaDao = PruebaDao.getInstance();
		Prueba p = pruebaDao.getPruebabyId(Long.parseLong(id));
		Utils.writeLog(usermail, "Elimina", "Prueba", p.getId_prueba());
		pruebaDao.deletePrueba(p);
		
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

			PruebaDao pDao = PruebaDao.getInstance();
			List<Prueba> pruebas = pDao.getAllPruebas();

			WritableSheet s = w.createSheet("Gestion de pruebas", 0);

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
			s.setColumnView(4, 20);
			s.setColumnView(5, 20);
			s.setColumnView(6, 20);
			s.setColumnView(7, 20);
			s.setColumnView(8, 20);
			s.setColumnView(9, 40);
			s.setColumnView(10, 40);
		

			s.addCell(new Label(0, 0, "IDENTIFICADOR", cellFormat));
			s.addCell(new Label(1, 0, "CLIENTE", cellFormat));
			s.addCell(new Label(2, 0, "FECHA ALTA", cellFormat));
			s.addCell(new Label(3, 0, "TIPO CLIENTE", cellFormat));
			s.addCell(new Label(4, 0, "PRODUCTO/CANAL", cellFormat));
			s.addCell(new Label(5, 0, "REFERENCIA", cellFormat));
			s.addCell(new Label(6, 0, "ENTORNO", cellFormat));
			s.addCell(new Label(7, 0, "ESTADO", cellFormat));
			s.addCell(new Label(8, 0, "TIPO SERVICIO", cellFormat));
			s.addCell(new Label(9, 0, "DESCRIPCI�N", cellFormat));
			s.addCell(new Label(10, 0, "SOLUCI�N", cellFormat));
			
			int aux = 1;

			for ( Prueba pru : pruebas) {
				
				
				s.addCell(new Label(0, aux, pru.getId_prueba()));
				s.addCell(new Label(1, aux, pru.getNombre_cliente()));
				s.addCell(new Label(2, aux, pru.getStr_fecha_estado()));
				s.addCell(new Label(3, aux, pru.getPremium()));
				s.addCell(new Label(4, aux, pru.getProducto()));
				s.addCell(new Label(5, aux, pru.getReferencia()));
				s.addCell(new Label(6, aux, pru.getEntorno()));
				s.addCell(new Label(7, aux, pru.getEstado()));
				s.addCell(new Label(8, aux, pru.getTipo_servicio()));
				s.addCell(new Label(9, aux, pru.getDetalles()));
				s.addCell(new Label(10, aux, pru.getSolucion()));



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