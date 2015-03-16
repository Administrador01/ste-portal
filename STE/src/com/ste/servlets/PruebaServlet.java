package com.ste.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.ste.beans.Cliente;
import com.ste.beans.Implementacion;
import com.ste.beans.Prueba;
import com.ste.dao.ImplementacionDao;
import com.ste.dao.PruebaDao;
import com.ste.utils.Utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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
				}else if (accion.equals("getImpByClient")){
					getImpByClient(req,resp,usermail);
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
			//String nombre_cliente = req.getParameter("cliente");
			String referencia = req.getParameter("referencia");
			String producto = req.getParameter("producto_canal");
			String premium = req.getParameter("input-premium-soporte");
			String estado = req.getParameter("estado");
			String entorno = req.getParameter("entorno");
			String detalles = req.getParameter("detalles");
			String solucion = req.getParameter("solucion");
			String impID = req.getParameter("imp_id");
			String tipo_servicio = req.getParameter("tipo_servicio");
			String resultado = req.getParameter("resultado");
			String peticionario = req.getParameter("peticionario");
			String fichero = req.getParameter("fichero");
			String str_fecha_inicio = req.getParameter("fecha_inicio");
			
			
			PruebaDao pDao = PruebaDao.getInstance();	
			Prueba p = new Prueba();
			
			ImplementacionDao impDao = ImplementacionDao.getInstance();
			Cliente cliente =  impDao.getClienteByImpId(Long.parseLong(impID));
			
			
			p.setClient_name(cliente.getNombre());
			p.setPremium(cliente.getPremium());
			
			p.setStr_fecha_estado(fecha_estado);
			//p.setNombre_cliente(nombre_cliente);
			p.setReferencia(referencia);
			p.setProducto(producto);
			p.setPremium(premium);
			p.setEntorno(entorno);
			p.setEstado(estado);
			if(p.getEstado().equals("Cancelado")){p.setErased(true);}else{p.setErased(false);}
			p.setDetalles(detalles);
			p.setSolucion(solucion);
			p.setImp_id(impID);
			p.setTipo_servicio(tipo_servicio);
			p.setResultado(resultado);
			p.setFecha_inicio_str(str_fecha_inicio);
			p.setFichero(fichero);
			p.setPeticionario(peticionario);
			p.setFecha_inicio_str(str_fecha_inicio);
			
			
			
			pDao.createPrueba(p);		
			
			Utils.writeLog(usermail, "Crea", "Prueba", p.getId_prueba());
			
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
	public void getImpByClient(HttpServletRequest req, HttpServletResponse resp, String usermail){
		JSONObject json = new JSONObject();
		try{
			ImplementacionDao implementacionDao = ImplementacionDao.getInstance();
			
			String client = req.getParameter("client");
			ArrayList<String> implmns = new ArrayList<String>();
			
			List<Implementacion> implementaciones = implementacionDao.getImplementacionByClientId(Long.parseLong(client));
			for(Implementacion implementacion : implementaciones){
				implmns.add(String.valueOf(implementacion.getKey().getId()));
				implmns.add(implementacion.getId_implementacion());
			}
			
			
			
			json.append("success","true");
			json.append("implementaciones", implmns);
			
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("application/json");
			resp.getWriter().println(json);
		
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	//ACTUALIZAR PRUEBA	
	public void updatePrueba(HttpServletRequest req, HttpServletResponse resp, String usermail){
		
		JSONObject json = new JSONObject();
		
		String id_str = req.getParameter("id");
		
		String fecha_estado = req.getParameter("fecha_estado");
		//String nombre_cliente = req.getParameter("cliente");
		String referencia = req.getParameter("referencia");
		String producto = req.getParameter("producto_canal");
		String premium = req.getParameter("input-premium-soporte");
		String estado = req.getParameter("estado");
		String entorno = req.getParameter("entorno");
		String servicio =  req.getParameter("tipo_servicio");
		String detalles = req.getParameter("detalles");
		String solucion = req.getParameter("solucion");
		String impID = req.getParameter("imp_id_mod");
		String resultado = req.getParameter("resultado");
		String peticionario = req.getParameter("peticionario");
		String fichero = req.getParameter("fichero");
		String str_fecha_inicio = req.getParameter("fecha_inicio");
		
		
		PruebaDao pDao = PruebaDao.getInstance();	
		Prueba p = pDao.getPruebabyId(Long.parseLong(id_str));
		Utils.writeLog(usermail, "Modifica", "Prueba", p.getId_prueba());
		
		ImplementacionDao impDao = ImplementacionDao.getInstance();
		Cliente cliente =  impDao.getClienteByImpId(Long.parseLong(impID));
		
		
		p.setClient_name(cliente.getNombre());
		p.setPremium(cliente.getPremium());
		
		
		p.setStr_fecha_estado(fecha_estado);
		//p.setNombre_cliente(nombre_cliente);
		p.setReferencia(referencia);
		p.setProducto(producto);
		p.setPremium(premium);
		p.setTipo_servicio(servicio);
		p.setEntorno(entorno);
		p.setEstado(estado);
		if(p.getEstado().equals("Cancelado")){p.setErased(true);}else{p.setErased(false);}
		p.setDetalles(detalles);
		p.setSolucion(solucion);
		if (impID !="" && impID != null) p.setImp_id(impID);
		p.setResultado(resultado);
		p.setFecha_inicio_str(str_fecha_inicio);
		p.setFichero(fichero);
		p.setPeticionario(peticionario);
		
		

		
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
			
			ImplementacionDao impdao = ImplementacionDao.getInstance();
					
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
			s.setColumnView(11, 20);
			s.setColumnView(12, 20);
			s.setColumnView(13, 20);
			s.setColumnView(14, 20);
			s.setColumnView(15, 40);
		

			s.addCell(new Label(0, 0, "IDENTIFICADOR", cellFormat));
			s.addCell(new Label(1, 0, "CLIENTE", cellFormat));
			s.addCell(new Label(2, 0, "FECHA INICIO", cellFormat));
			s.addCell(new Label(3, 0, "FECHA ESTADO", cellFormat));
			s.addCell(new Label(4, 0, "TIPO CLIENTE", cellFormat));
			s.addCell(new Label(5, 0, "PRODUCTO/CANAL", cellFormat));
			s.addCell(new Label(6, 0, "REFERENCIA", cellFormat));
			s.addCell(new Label(7, 0, "ENTORNO", cellFormat));
			s.addCell(new Label(8, 0, "ESTADO", cellFormat));
			s.addCell(new Label(9, 0, "IMPLEMENTACIÓN", cellFormat));
			s.addCell(new Label(10, 0, "TIPO SERVICIO", cellFormat));
			s.addCell(new Label(11, 0, "RESULTADO", cellFormat));
			s.addCell(new Label(12, 0, "PETICIONARIO", cellFormat));
			s.addCell(new Label(13, 0, "FICHERO", cellFormat));
			s.addCell(new Label(14, 0, "DESCRIPCIÓN", cellFormat));

			
			
			int aux = 1;

			for ( Prueba pru : pruebas) {
				
				
				s.addCell(new Label(0, aux, pru.getId_prueba()));
				s.addCell(new Label(1, aux, pDao.getClientByTestId(pru.getKey().getId()).getNombre()));
				s.addCell(new Label(2, aux, pru.getFecha_inicio_str()));
				s.addCell(new Label(3, aux, pru.getStr_fecha_estado()));
				s.addCell(new Label(4, aux, pDao.getClientByTestId(pru.getKey().getId()).getPremium()));
				s.addCell(new Label(5, aux, pru.getProducto()));
				s.addCell(new Label(6, aux, pru.getReferencia()));
				s.addCell(new Label(7, aux, pru.getEntorno()));
				s.addCell(new Label(8, aux, pru.getEstado()));
				s.addCell(new Label(9, aux, pDao.getImplementacionByTestId(pru.getKey().getId()).getId_implementacion()));
				s.addCell(new Label(10, aux, pru.getTipo_servicio()));
				s.addCell(new Label(11, aux, pru.getResultado()));
				s.addCell(new Label(12, aux, pru.getPeticionario()));
				s.addCell(new Label(13, aux, pru.getFichero()));
				s.addCell(new Label(14, aux, pru.getDetalles()));



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
