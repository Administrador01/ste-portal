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
import com.ste.beans.Soporte;
import com.ste.dao.ClienteDao;
import com.ste.dao.SoporteDao;

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
					"attachment; filename=GestionClientesSTE.xls");

			WritableWorkbook w = Workbook
					.createWorkbook(resp.getOutputStream());

			ClienteDao cDao = ClienteDao.getInstance();
			List<Cliente> clientes = cDao.getAllClients();
					
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
			
			
			s.setRowView(0, 900);

			s.addCell(new Label(0, 0, "IDENTIFICADOR", cellFormat));
			s.addCell(new Label(1, 0, "NOMBRE", cellFormat));
			s.addCell(new Label(2, 0, "FECHA ALTA", cellFormat));
			s.addCell(new Label(3, 0, "TIPO CLIENTE", cellFormat));
			s.addCell(new Label(4, 0, "SEGMENTO", cellFormat));
			

			int aux = 1;

			for (Cliente cli : clientes) {
				
				s.addCell(new Label(0, aux, cli.getId_cliente()));
				s.addCell(new Label(1, aux, cli.getNombre()));
				s.addCell(new Label(2, aux, cli.getStr_fecha_alta()));
				s.addCell(new Label(3, aux, cli.getPremium()));
				s.addCell(new Label(4, aux, cli.getTipo_cliente()));
			

				

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
		
		nombre = nombre.substring(0,1).toUpperCase() + nombre.substring(1,nombre.length());
		
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
