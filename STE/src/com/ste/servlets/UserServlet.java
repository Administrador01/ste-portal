package com.ste.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
import com.ste.beans.User;
import com.ste.dao.UserDao;
import com.ste.utils.Utils;

public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = -826683004548238295L;

	private static final Logger log = Logger.getLogger(UserServlet.class.getName());

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp){
		
		JSONObject json = new JSONObject();
		
		String accion = req.getParameter("accion");
		
		 try {
			 
			HttpSession sesion = req.getSession();
			int sesionpermiso = (int) sesion.getAttribute("permiso");
			 
			 if (sesionpermiso>2){
					json.append("failure", "true");
					json.append("error", "No tienes los permisos para realizar esta operación");
					
					resp.setCharacterEncoding("UTF-8");
			        resp.setContentType("application/json");       
					resp.getWriter().println(json);
			 }else{   
			 
				 if (accion.equals("new")){
						createUser(req,resp);
					}else if (accion.equals("delete")){
						deleteUser(req,resp);
					}else if (accion.equals("update")){
						updateUser(req,resp);
					}else if (accion.equals("xls")){
						generateXLS(req,resp);
					}
			 }    
		
		} catch (Exception e) {
			e.printStackTrace();
		}			
		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		doGet(req,resp);
	}
	
	private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws JSONException, IOException{
		JSONObject json = new JSONObject();
		
		String nombre = req.getParameter("nombre");
		String ap1 = req.getParameter("ap1");
		String ap2 = req.getParameter("ap2");
		String email = req.getParameter("email");
		
		
		String area_cliente = req.getParameter("area_cliente");
		String area_documentacion = req.getParameter("area_documentacion");
		String area_informes = req.getParameter("area_informes");
		String area_pruebas = req.getParameter("area_pruebas");
		String area_servicios = req.getParameter("area_servicios");
		String area_soporte = req.getParameter("area_soporte");		
		
		String dto = req.getParameter("dto");
		
		
		String id = req.getParameter("id");
		
		Integer permiso = Integer.parseInt(req.getParameter("permiso"));
		String permisoStr = Utils.getPermisoStr(permiso);
		
		UserDao uDao = UserDao.getInstance();
		User u = uDao.getUserbyId(Long.parseLong(id));
		
		u.setNombre(nombre);
		u.setApellido1(ap1);
		u.setApellido2(ap2);
		u.setEmail(email);
		u.setDepartamento(dto);
		u.setPermisoStr(permisoStr);
		u.setPermiso(permiso);

		u.setPermiso_clientes(Integer.parseInt(area_cliente));
		u.setPermiso_documentacion(Integer.parseInt(area_documentacion));
		u.setPermiso_informes(Integer.parseInt(area_informes));
		u.setPermiso_pruebas(Integer.parseInt(area_pruebas));
		u.setPermiso_servicios(Integer.parseInt(area_servicios));
		u.setPermiso_soporte(Integer.parseInt(area_soporte));
		
		uDao.createUser(u);
		
		json.append("success", "true");
		json.append("id", u.getKey().getId());
		json.append("permiso", u.getPermisoStr());
		json.append("permisoid", permiso);
		json.append("dto", dto);
		
		resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");       
		resp.getWriter().println(json);
		
		
	}
	
	private void createUser(HttpServletRequest req, HttpServletResponse resp) throws JSONException, IOException{
		JSONObject json = new JSONObject();
		
		try{
		
		String nombre = req.getParameter("nombre");
		String ap1 = req.getParameter("ap1");
		String ap2 = req.getParameter("ap2");
		String email = req.getParameter("email");
		String dto = req.getParameter("dto");	
		Integer permiso = Integer.parseInt(req.getParameter("permiso"));
		

		String area_cliente = req.getParameter("area_cliente");
		String area_documentacion = req.getParameter("area_documentacion");
		String area_informes = req.getParameter("area_informes");
		String area_pruebas = req.getParameter("area_pruebas");
		String area_servicios = req.getParameter("area_servicios");
		String area_soporte = req.getParameter("area_soporte");		
		
		
		
		String permisoStr = Utils.getPermisoStr(permiso);		
		
		UserDao uDao = UserDao.getInstance();
		User us = uDao.getUserByMail(email);
		Boolean create = true;
		User u = new User();
		
		if (us != null){
			if (us.getErased()==false){
				json.append("failure", "true");
				json.append("error", "Ya existe un usuario con este email");
				create = false;
			}else{
				u = us;
			}
		}		
			
		if (create){
           // u = new User(nombre,ap1,ap2,email,permiso,permisoStr,areas,dto);	
			u.setNombre(nombre);
			u.setApellido1(ap1);
			u.setApellido2(ap2);
			u.setEmail(email);
			u.setPermiso(permiso);
			u.setPermisoStr(permisoStr);
			u.setDepartamento(dto);
			
			u.setPermiso_clientes(Integer.parseInt(area_cliente));
			u.setPermiso_documentacion(Integer.parseInt(area_documentacion));
			u.setPermiso_informes(Integer.parseInt(area_informes));
			u.setPermiso_pruebas(Integer.parseInt(area_pruebas));
			u.setPermiso_servicios(Integer.parseInt(area_servicios));
			u.setPermiso_soporte(Integer.parseInt(area_soporte));
			 
			
            u.setErased(false);
            
			uDao.createUser(u);
			
			json.append("success", "true");
			json.append("id", u.getKey().getId());
			json.append("permiso", permisoStr);
			json.append("permisoid", permiso);
			json.append("dto", dto);
		}
		
		}catch(Exception e){
			log.warning("Error en NewUserServlet");
			log.warning((e.toString()));
		
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos);
			e.printStackTrace(ps);
			String content = baos.toString("ISO-8859-1"); // e.g. ISO-8859-1
			
		
			log.warning(content);
			
			json.append("success", "false");
			json.append("error", "Se ha producido un error inesperado.");
		
		}
		resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");       
		resp.getWriter().println(json);
	}
	
	private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws JSONException, IOException {
		JSONObject json = new JSONObject();
		
		UserDao udao = UserDao.getInstance();
		try{
			User u = udao.getUserbyId(Long.parseLong(req.getParameter("id")));
			
			udao.logicalDelete(u);
			json.append("success", "true");
		}catch(Exception e){
			json.append("failure", "true");
		}
		
		
		resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");       
		resp.getWriter().println(json);
	}	
	
	public void generateXLS(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		OutputStream out = null;
		try {
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition",
					"attachment; filename=UsuariosSTE.xls");
			
			WritableWorkbook w = Workbook.createWorkbook(resp
					.getOutputStream());
			
			UserDao uDao = UserDao.getInstance();
			List<User> usuarios = uDao.getAllNonDeletedUsers();
			
			WritableSheet s = w.createSheet("Usuarios", 0);
		
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
		    cellFont.setColour(Colour.WHITE);
		    
		    WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		    cellFormat.setBackground(Colour.BLUE);
		    cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		    cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
		    cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);			
			
		    s.setColumnView(0, 16);
		    s.setColumnView(1, 16);
		    s.setColumnView(2, 16);
		    s.setColumnView(3, 40);
		    s.setColumnView(4, 16);
		    s.setColumnView(5, 50);
		    s.setColumnView(6, 70);
		    
		    s.setColumnView(6, 70);
		    s.setColumnView(7, 70);
		    s.setColumnView(8, 70);
		    s.setColumnView(9, 70);
		    s.setColumnView(10, 70);
		    s.setColumnView(11, 70);
		    s.setRowView(0, 900);
						
			s.addCell(new Label(0, 0, "NOMBRE",cellFormat));
			s.addCell(new Label(1, 0, "APELLIDO 1",cellFormat));
			s.addCell(new Label(2, 0, "APELLIDO 2",cellFormat));
			s.addCell(new Label(3, 0, "EMAIL",cellFormat));
			s.addCell(new Label(4, 0, "PERFIL",cellFormat));
			s.addCell(new Label(5, 0, "DEPARTAMENTO",cellFormat));
			
			s.addCell(new Label(6, 0, "AREA CLIENTE",cellFormat));
			s.addCell(new Label(7, 0, "AREA PRUEBAS",cellFormat));
			s.addCell(new Label(8, 0, "AREA SERVICIOS",cellFormat));
			s.addCell(new Label(9, 0, "AREA INFORMES",cellFormat));
			s.addCell(new Label(10, 0, "AREA SOPORTE",cellFormat));
			s.addCell(new Label(11, 0, "AREA DOCUMENTACIÓN",cellFormat));
			
			
			int aux=1;
			
			
			
			for (User u:usuarios){
				
				
				
				s.addCell(new Label(0, aux, u.getNombre()));
				s.addCell(new Label(1, aux, u.getApellido1()));
				s.addCell(new Label(2, aux, u.getApellido2()));
				s.addCell(new Label(3, aux, u.getEmail()));
				s.addCell(new Label(4, aux, u.getPermisoStr()));
				s.addCell(new Label(5, aux, u.getDepartamento()));
				
	
				
				s.addCell(new Label(6, aux, u.getPermiso_clientes().toString()));
				s.addCell(new Label(7, aux, u.getPermiso_pruebas().toString()));
				s.addCell(new Label(8, aux, u.getPermiso_servicios().toString()));
				s.addCell(new Label(9, aux, u.getPermiso_informes().toString()));
				s.addCell(new Label(10, aux, u.getPermiso_soporte().toString()));
				s.addCell(new Label(11, aux, u.getPermiso_documentacion().toString()));
				
				aux++;
			}		
			
			w.write();
			w.close();
		} catch (Exception e) {
			throw new ServletException("Exception in Excel", e);
		} finally {
			if (out != null)
				out.close();
		}

	}
}

