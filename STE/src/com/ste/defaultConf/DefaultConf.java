package com.ste.defaultConf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ste.beans.Cliente;
import com.ste.dao.ClienteDao;
import com.ste.utils.Utils;
import com.ste.beans.User;
import com.ste.dao.UserDao;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.ste.counters.Counter;
import com.ste.dao.CounterDao;

public class DefaultConf extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8059778774757795508L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		HttpSession sesion = req.getSession();
		JSONObject json = new JSONObject();
		String result = "";
		
		String accion = req.getParameter("accion");
		
		HttpSession session = req.getSession();
		String usermail = (String)session.getAttribute("usermail");
		try{
			if ("clientes".equals(accion)){
				result = loadClientes(req,resp);
				json.append("success", "true");
				json.append("result", result);
			}else if ("inicializacionContadores".equals(accion)){
				counterInicialization(req,resp, usermail);
				json.append("success", "true");
				result = "todo bien, contadores inicializados";
				json.append("result", result);
			}
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("text/plain");
			resp.getWriter().println(result);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp){
		doPost(req,resp);
	}
	
	public void counterInicialization(HttpServletRequest req, HttpServletResponse resp, String usermail) throws InterruptedException, IOException{
		JSONObject json = new JSONObject();
		HttpSession sesion = req.getSession();
		try {
			
		String mail = (String) sesion.getAttribute("mail");
		if ("david.martin.beltran.contractor@bbva.com".equals(mail)||"roberto.gonzalez.roman.contractor@bbva.com".equals(mail)){
			CounterDao cDao = CounterDao.getInstance();
			Counter contadorSoporte = cDao.getCounterByName("soporte");
			//instancia para anadir nuevos contadores+
			if (contadorSoporte == null){
				contadorSoporte = new Counter();
			}
			contadorSoporte.setNombre("soporte");
			contadorSoporte.setValue(1);
			cDao.createCounter(contadorSoporte);
			
			
			Counter contadorCliente = cDao.getCounterByName("cliente");
			
			if (contadorCliente == null){
				contadorCliente = new Counter();
			}
			contadorCliente.setNombre("cliente");
			contadorCliente.setValue(1);
			cDao.createCounter(contadorCliente);
			
			Counter contadorPrueba = cDao.getCounterByName("prueba");
			
			if (contadorPrueba == null){
				contadorPrueba = new Counter();
			}
			contadorPrueba.setNombre("prueba");
			contadorPrueba.setValue(1);
			cDao.createCounter(contadorPrueba);			
			
			
			Counter contadorImp = cDao.getCounterByName("implementacion");
			
			if (contadorImp == null){
				contadorImp = new Counter();
			}
			contadorImp.setNombre("implementacion");
			contadorImp.setValue(1);
			cDao.createCounter(contadorImp);	
			json.append("success", true);
			
		}else{
			json.append("failure", true);
	
		}
	
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
	
	public String loadClientes(HttpServletRequest req, HttpServletResponse resp) throws InterruptedException, IOException{
		HttpSession sesion = req.getSession();
		boolean save = false;
		String saveParam = req.getParameter("save"); 
		if(saveParam != null && saveParam.equals("true")) {
			save = true;
		}
		String link = "/datadocs/clientesCarga.csv";
		
		String result = "";
		try {
			
			InputStream stream = this.getServletContext().getResourceAsStream(link);
			BufferedReader in = new BufferedReader(new InputStreamReader(stream, "Cp1252"));

			String inputLine = new String();

			ClienteDao clientesDao = ClienteDao.getInstance();
			Cliente cliente = null;
			int counter = 0;
			boolean error = false;

			while ((inputLine = in.readLine()) != null) {
				error = false;
				
				String line = inputLine;
				String[] clienteSplit = line.split(";", -1);

				boolean procesar = true;
				if (clienteSplit.length < 4) {
					procesar = false;
				}
				String nombre= clienteSplit[0];
				if (esNuloVacio(nombre)) {
					procesar = false;
					break;
				}

				if (procesar) {
					String str_fecha_alta_cliente = clienteSplit[1];
					String tipo = clienteSplit[2];
					String premium = clienteSplit[3];
							
					cliente = clientesDao.getClienteByName(nombre);
					List<Cliente> clientes = clientesDao.getAllClientsEvenDeleted();
					Boolean exist_client = false;
					
					for (Cliente cl:clientes){
						if (cl.getNombre().toLowerCase().equals(nombre.toLowerCase())){
							exist_client = true;
						}
					}
										
					if (exist_client){
						result += "Error\r\nYa existe un cliente con este nombre \r\n";		
						error = true;
					}
					
					cliente = new Cliente();		
				
					
					if(!esNuloVacio(nombre)) {
						cliente.setNombre(nombre);
					}
					else {
						result += "Error\r\nError nombre \r\n";
						error = true;
					}
					
					
					
					if (isThisDateValid(str_fecha_alta_cliente, "dd/MM/yyyy")) {
							cliente.setStr_fecha_alta(str_fecha_alta_cliente);;
						}
						else {
							result += "Error\r\nError fecha alta \r\n";
							error = true;
						}

					if(!esNuloVacio(tipo)) {
						cliente.setTipo_cliente(tipo);
					}
					else {
						result += "Error\r\nError Tipo \r\n";
						error = true;
					}
					
					if(!esNuloVacio(premium)) {
						cliente.setPremium(premium);
					}
					else {
						result += "Error\r\nError en premium o no \r\n";
						error = true;
					}
					
										
					if(!error) {
						result += cliente.toString() + "\r\n\r\n";
					}
					else {
						result += "\r\n";
					}
					
					if(save) {
						clientesDao.createCliente(cliente);
					}
				}
				counter++;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private String loadUsuarios(HttpServletRequest req, HttpServletResponse resp, String usermail) throws InterruptedException{
		boolean save = false;
		String saveParam = req.getParameter("save");
		String deleteParam = req.getParameter("delete"); 
		
		boolean delete = false;
		 
		if(deleteParam != null && deleteParam.equals("true")) {
			delete = true;
		}
		if(saveParam != null && saveParam.equals("true")) {
			save = true;
		}
		String link = "/datadocs/users.csv";
		
		String result = "";
		try {
			
			InputStream stream = this.getServletContext().getResourceAsStream(link);
			BufferedReader in = new BufferedReader(new InputStreamReader(stream, "Cp1252"));

			String inputLine = new String();

			UserDao userDao = UserDao.getInstance();
			if(delete){
				userDao.deleteAll();
			}
			User user = null;
			
			int counter = 0;
			boolean error = false;

			while ((inputLine = in.readLine()) != null) {
				error = false;
				
				String line = inputLine;
				String[] userSplit = line.split(";", -1);

				boolean procesar = true;
				if (userSplit.length < 8) {
					procesar = false;
				}
				String nombre = userSplit[0];
				if (esNuloVacio(nombre)) {
					procesar = false;
					break;
				}

				if (procesar) {
					
					String apellido1 = userSplit[1];
					String apellido2 = userSplit[2];
					int perm1= 0;
					String permiso = Integer.toString(perm1); 
					permiso = userSplit[3];
					String permisoStr = userSplit[4];
					String departamento = userSplit[5];
					String email = userSplit[6];
					email.replace(" ", "");
					Boolean activo = true;
					
					String activo1 = userSplit[7];
					
					
					user = new User();		
					 
					if(!esNuloVacio(nombre)) {
						user.setNombre(nombre);
					}
					else {
						result += "Error Falta Nombre \r\n";
						error = true;
					}	
						if(!esNuloVacio(apellido1)) {
							user.setApellido1(apellido1);
						}
						else {
							result += "Error Falta Apellido1 \r\n";
							error = true;
						}	
												
						if(!esNuloVacio(email)) {
							user.setEmail(email);
						}
						else {
							result += "Error Falta email \r\n";
							error = true;
						}
						
						
						if(!esNuloVacio(departamento)) {
							user.setDepartamento(departamento);
						}
						else {
							result += "Error Falta Departamento \r\n";
							error = true;
						}	
						
						if(!esNuloVacio(permisoStr)) {
							user.setPermisoStr(permisoStr);
						}
						else {
							result += "Error Falta Permiso de Perfil \r\n";
							error = true;
						}
						if(activo1.equals("false")||activo1.equals("FALSE"))activo=false;
						user.setPermiso(Integer.parseInt(permiso));
						user.setApellido2(apellido2);
						
						user.setErased(false);
										
					if(!error) {
						result += user.toString() + "\r\n\r\n";
					}
					else {
						result += "\r\n";
					}
					
					if(save) {
						userDao.createUser(user);
					}
				}
				counter++;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		
		return result;
					
				
				
	}
	public boolean esNuloVacio(String string) {
		if(string == null || "".equals(string)) {
			return true;
		}
		return false;
	}
	
	public boolean isThisDateValid(String dateToValidate, String dateFormat){
		 
		if(dateToValidate == null || "".equals(dateToValidate)){
			return false;
		}
 
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setLenient(false);
 
		try {
			//if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);
			//System.out.println(date);
 
		} catch (ParseException e) {
			return false;
		}
 
		return true;
	}
}
