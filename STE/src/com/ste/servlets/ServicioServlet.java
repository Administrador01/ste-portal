package com.ste.servlets;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;













import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.ste.beans.Estado;
import com.ste.beans.EstadoImplementacion;
import com.ste.beans.Pais;
import com.ste.beans.ProductoCanal;
import com.ste.beans.Servicio;
import com.ste.beans.TipoServicio;
import com.ste.beans.Cliente;
import com.ste.dao.ClienteDao;
import com.ste.dao.EstadoDao;
import com.ste.dao.EstadoImplementacionDao;
import com.ste.dao.PaisDao;
import com.ste.dao.ProductoCanalDao;
import com.ste.dao.ServicioDao;
import com.ste.dao.TipoServicioDao;


import com.ste.filters.UserFilter;
import com.ste.utils.Utils;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


import java.io.*;
import java.net.URL;
import java.util.List;

public class ServicioServlet  extends HttpServlet{

	  
	public void doGet(HttpServletRequest req, HttpServletResponse resp){

		JSONObject json = new JSONObject();
		
		String accion = req.getParameter("accion");
			
		 try {
			 
			HttpSession sesion = req.getSession();
			int sesionpermiso = (int) sesion.getAttribute("permiso");			 
			String usermail = (String) sesion.getAttribute("mail");
			

				if (accion.equals("importar")){pruebamethod(req,resp,usermail);}
				if (accion.equals("inicial")){
					createServicio(req,resp,usermail);
				}
				if (accion.equals("prueba"))tester(req,resp,usermail);


			
		} catch (Exception e) {

			e.printStackTrace();
		}		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		doGet(req,resp);
	}
	
	
	public void tester(HttpServletRequest req, HttpServletResponse resp, String usermail) throws InterruptedException, IOException{
		/*METODO PARA PODER AUTENTICARSE POR OAUTH2 
		 * Metodo para poder destruir los archivos 
		 * --------------------------------------------------------------------------------
		GoogleCredential credential = null;
		Drive drive = null;
	    try{
	    	credential = UserFilter.getCredential(req, resp);
	   		drive = UserFilter.getDrive(credential);
	    	drive.files().trash(req.getParameter("link")).execute();
	    }catch(Exception e){
	    	e.printStackTrace();
	    	UserFilter.AutenticacionGoogle(req,resp);
	    }
		 */
	}
	
	public void pruebamethod(HttpServletRequest req, HttpServletResponse resp, String usermail) throws InterruptedException{
	      
		

	      String link = req.getParameter("link");
	      
	      try {
	    	  
	    	  List <String> line = new ArrayList<String>();
	          URL myURL = new URL(link);
	          InputStreamReader a = new InputStreamReader(myURL.openStream());
	          BufferedReader in = new BufferedReader(a);
	          
	          
	          String inputLine = new String();
	          String elemento = new String();
	          
	          Pais pais = new Pais();
	          ProductoCanal prod = new ProductoCanal();
	          TipoServicio tipserv = new TipoServicio();
	          Cliente client = new Cliente();
	          Servicio servicio = new Servicio();
	          
	          PaisDao paisDao = PaisDao.getInstance();
	          ProductoCanalDao prodDao = ProductoCanalDao.getInstance();
	          TipoServicioDao tipservDao = TipoServicioDao.getInstance();
	          ClienteDao clientDao = ClienteDao.getInstance();
	          ServicioDao servicioDao = ServicioDao.getInstance();
	          
	          while ((inputLine = in.readLine()) != null){
	        	  if (inputLine.equals("Implementacion")||inputLine.equals("Cliente")||inputLine.equals("Pais")||inputLine.equals("ProductoCanal")||inputLine.equals("Prueba")||inputLine.equals("Servicio")||inputLine=="Soporte"||inputLine=="TipoServicio"||inputLine=="User"){
	        		  elemento = inputLine;
	        	  }else{
		        	  switch (elemento){
		        	  	case "Pais":
		        			pais = new Pais();
		        			String nombrePais = inputLine;
		        			pais.setNme(nombrePais);
		        			paisDao.createPais(pais);	
		        			break;
		        			
		        	  	case "ProductoCanal":
		      
		        			prod = new ProductoCanal();
		        			String nombreProducto = inputLine;
		        			prod.setNme(nombreProducto);
		        			prodDao.createProductoCanal(prod);
		        			break;
		        			
		        	  	case "TipoServicio":
		        			tipserv = new TipoServicio();
		        			String nombreTipoServ = "Pagos";
		        			tipserv.setNme(nombreTipoServ);
		        			tipservDao.createTipoServicio(tipserv);
		        			break;
		        			
		        	  	case "Servicio":
		        	  		servicio = new Servicio();
		        	  		String[] arrServ = inputLine.split(" ");
		        			servicio.setNme(arrServ[0]);
		        			servicio.setTipo(arrServ[1]);
		        			servicioDao.createServicio(servicio, usermail);
		        	  		break;
		        	  	case "Cliente":
		        	  		client = new Cliente();
		        	  		String[] arrCliente = inputLine.split(" ");
		        	  		client.setNombre(arrCliente[0]);
		        	  		client.setPremium(arrCliente[1]);
		        	  		client.setStr_fecha_alta(arrCliente[2]);
		        	  		client.setTipo_cliente(arrCliente[0]);
		        	  		clientDao.createCliente(client);
		        	  		break;

		        	  	  
		        	  }
	        	  }
	          }
	          in.close();	  
	    	  

	      }
	      catch(Exception e){
	          e.printStackTrace();
	       }
	}
	public void createServicio(HttpServletRequest req, HttpServletResponse resp, String usermail) throws InterruptedException{
		ServicioDao sDao = ServicioDao.getInstance();
		sDao.deleteAll();
		
		TipoServicioDao tipservDao = TipoServicioDao.getInstance();
		tipservDao.deleteAll();
		
		PaisDao paisDao = PaisDao.getInstance();
		paisDao.deleteAll();
		
		ProductoCanalDao prodDao = ProductoCanalDao.getInstance();
		prodDao.deleteAll();
		
		EstadoDao estDao = EstadoDao.getInstance();
		estDao.deleteAll();
		
		EstadoImplementacionDao estImpDao = EstadoImplementacionDao.getInstance();
		estImpDao.deleteAll();
		
		Estado es = new Estado();
		String nombrees = "Pendiente";
		es.setNme(nombrees);
		es.setOrden(1);
		estDao.createEstado(es);
		es = new Estado();
		nombrees = "En curso";
		es.setNme(nombrees);
		es.setOrden(536870911);
		estDao.createEstado(es);
		es = new Estado();
		nombrees = "Finalizado";
		es.setNme(nombrees);
		es.setOrden(1073741823);
		estDao.createEstado(es);
		es = new Estado();
		nombrees = "Cancelado";
		es.setNme(nombrees);
		es.setOrden(2147483640);
		estDao.createEstado(es);
		es = new Estado();
		nombrees = "Terminado";
		es.setNme(nombrees);
		es.setOrden(1610612735);
		estDao.createEstado(es);

		EstadoImplementacion estadoImplementacion = new EstadoImplementacion();
		nombrees = "Pendiente";
		estadoImplementacion.setNme(nombrees);
		estadoImplementacion.setOrden(1);
		estImpDao.createEstadoImplementacion(estadoImplementacion);
		estadoImplementacion = new EstadoImplementacion();
		nombrees = "An&aacutelisis";
		estadoImplementacion.setNme(nombrees);
		estadoImplementacion.setOrden(536870911);
		estImpDao.createEstadoImplementacion(estadoImplementacion);
		estadoImplementacion = new EstadoImplementacion();
		nombrees = "Pruebas";
		estadoImplementacion.setNme(nombrees);
		estadoImplementacion.setOrden(1073741823);
		estImpDao.createEstadoImplementacion(estadoImplementacion);
		estadoImplementacion = new EstadoImplementacion();
		nombrees = "Penny test";
		estadoImplementacion.setNme(nombrees);
		estadoImplementacion.setOrden(1500000001);
		estImpDao.createEstadoImplementacion(estadoImplementacion);
		estadoImplementacion = new EstadoImplementacion();
		nombrees = "Finalizada";
		estadoImplementacion.setNme(nombrees);
		estadoImplementacion.setOrden(1500000001);
		estImpDao.createEstadoImplementacion(estadoImplementacion);
		estadoImplementacion = new EstadoImplementacion();
		nombrees = "Anulado";
		estadoImplementacion.setNme(nombrees);
		estadoImplementacion.setOrden(2147483640);
		estImpDao.createEstadoImplementacion(estadoImplementacion);
		estadoImplementacion = new EstadoImplementacion();
		nombrees = "Parado";
		estadoImplementacion.setNme(nombrees);
		estadoImplementacion.setOrden(1610612735);
		estImpDao.createEstadoImplementacion(estadoImplementacion);
		
		
		
		Pais a = new Pais();
		String nombrePais = "B&eacutelgica";
		a.setNme(nombrePais);
		paisDao.createPais(a);	
		a = new Pais();
		nombrePais = "China (Hong Kong)";
		a.setNme(nombrePais);
		paisDao.createPais(a);	
		a = new Pais();
		nombrePais = "Espa&ntildea";
		a.setNme(nombrePais);
		paisDao.createPais(a);
		a = new Pais();
		nombrePais = "Francia";
		a.setNme(nombrePais);
		paisDao.createPais(a);
		a = new Pais();
		nombrePais = "Italia";
		a.setNme(nombrePais);
		paisDao.createPais(a);
		a = new Pais();
		nombrePais = "Portugal";
		a.setNme(nombrePais);
		paisDao.createPais(a);
		a = new Pais();
		nombrePais = "Reino Unido";
		a.setNme(nombrePais);
		paisDao.createPais(a);
		a = new Pais();
		nombrePais = "US (New York)";
		a.setNme(nombrePais);
		paisDao.createPais(a);
		
		ProductoCanal prod = new ProductoCanal();
		String nombreProducto = "Swift Fileact";
		prod.setNme(nombreProducto);
		prodDao.createProductoCanal(prod);
		
		prod = new ProductoCanal();
		nombreProducto = "Swift Fileact (antigua conecxi&oacuten)";
		prod.setNme(nombreProducto);
		prodDao.createProductoCanal(prod);
		
		prod = new ProductoCanal();
		nombreProducto = "Swift FIN";
		prod.setNme(nombreProducto);
		prodDao.createProductoCanal(prod);
		
		prod = new ProductoCanal();
		nombreProducto = "Swift FIN (Relay Bank)";
		prod.setNme(nombreProducto);
		prodDao.createProductoCanal(prod);
		prod = new ProductoCanal();
		nombreProducto = "EDITRAN";
		prod.setNme(nombreProducto);
		prodDao.createProductoCanal(prod);
		prod = new ProductoCanal();
		nombreProducto = "BBVA Netcash";
		prod.setNme(nombreProducto);
		prodDao.createProductoCanal(prod);
		prod = new ProductoCanal();
		nombreProducto = "EDIFACT";
		prod.setNme(nombreProducto);
		prodDao.createProductoCanal(prod);
		prod = new ProductoCanal();
		nombreProducto = "Normalizador";
		prod.setNme(nombreProducto);
		prodDao.createProductoCanal(prod);
		prod = new ProductoCanal();
		nombreProducto = "Cashpool dom&eacutestico";
		prod.setNme(nombreProducto);
		prodDao.createProductoCanal(prod);
		prod = new ProductoCanal();
		nombreProducto = "Cashpool internacional";
		prod.setNme(nombreProducto);
		prodDao.createProductoCanal(prod);
		prod = new ProductoCanal();
		nombreProducto = "Factura integral";
		prod.setNme(nombreProducto);
		prodDao.createProductoCanal(prod);
		prod = new ProductoCanal();
		nombreProducto = "HSS";
		prod.setNme(nombreProducto);
		prodDao.createProductoCanal(prod);
		
		TipoServicio tipserv = new TipoServicio();
		String nombreTipoServ = "Cobros";
		tipserv.setNme(nombreTipoServ);
		tipservDao.createTipoServicio(tipserv);
		tipserv = new TipoServicio();
		nombreTipoServ = "Pagos";
		tipserv.setNme(nombreTipoServ);
		tipservDao.createTipoServicio(tipserv);
		tipserv = new TipoServicio();
		nombreTipoServ = "MT101";
		tipserv.setNme(nombreTipoServ);
		tipservDao.createTipoServicio(tipserv);
		tipserv = new TipoServicio();
		nombreTipoServ = "MT94x";
		tipserv.setNme(nombreTipoServ);
		tipservDao.createTipoServicio(tipserv);
		tipserv = new TipoServicio();
		nombreTipoServ = "Cashpool";
		tipserv.setNme(nombreTipoServ);
		tipservDao.createTipoServicio(tipserv);
		tipserv = new TipoServicio();
		nombreTipoServ = "Factura integral";
		tipserv.setNme(nombreTipoServ);
		tipservDao.createTipoServicio(tipserv);
		tipserv = new TipoServicio();
		nombreTipoServ = "Otros";
		tipserv.setNme(nombreTipoServ);
		tipservDao.createTipoServicio(tipserv);
		tipserv = new TipoServicio();
		nombreTipoServ = "AEB43";
		tipserv.setNme(nombreTipoServ);
		tipservDao.createTipoServicio(tipserv);
		
		
		
		Servicio s = new Servicio();
		String nombre = "Adeudos B2B (Sepa) París       ";
		String tipo = "Envio";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		
		
		
		/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		/*-------------------------------------------------CODIGO GENERADO MEDIANTE REPLACE EN NOTEPAD++ PARA EXPORTAR DE EXCEL----------------------------------------------------*/
		/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

		s = new Servicio();
		 nombre ="Adeudos Core (Sepa) París";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Adeudos directos 19.14 (Sepa)";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Adeudos directos 19.44 (Sepa B2B)   ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="AEB43           ";
		tipo = "Recepción";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="C19.14 Cancelaciones/retrocesiones (Sepa)   ";
		tipo = "Recepción";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="C19.44 Cancel/retroc. (Sepa B2B)     ";
		tipo = "Recepción";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Cancel./retr. B2b (Sepa) París";
		tipo = "Recepción";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Cancel./retr. Core (Sepa) París           ";
		tipo = "Recepción";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Cesión de recibos domiciliados           ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Cesiones de anticipos de crédito         ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Cesiones de efectos al descuento        ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Cesiones de pagos certificados           ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Cesiones de pagos domiciliados en fichero      ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Confirming de clientes ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Confirming en divisa    ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Devol.efectos descontados en fichero ";
		tipo = "Recepción";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Devol.recibos en fichero         ";
		tipo = "Recepción";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Devoluciones de antic.cred. Vía fichero          ";
		tipo = "Recepción";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Emisión cheques en fichero 34.1";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Euroconfirming";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Factoring - remesas nuevas     ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Impag. Adeud. Direct. (Sepa B2B)      ";
		tipo = "Recepción";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Impag. Cuaderno 19.44 (Sepa B2B)   ";
		tipo = "Recepción";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Impagados adeudos directos (Sepa)   ";
		tipo = "Recepción";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Impagados B2B (Sepa) París   ";
		tipo = "Recepción";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Impagados Core (Sepa) París  ";
		tipo = "Recepción";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Impagados cuaderno 19.14 (Sepa)     ";
		tipo = "Recepción";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Inf.pago imptos. Autonómicos en fichero        ";
		tipo = "Recepción";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Inf.pago imptos. Municipales en fichero          ";
		tipo = "Recepción";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Infor. Facturación comercios en fichero          ";
		tipo = "Recepción";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Infor. Facturación comercios on line    ";
		tipo = "Recepción";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Información recibos no domiciliados en fichero           ";
		tipo = "Recepción";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Mov. Tarj. Crédito en fichero fecha de liquidación      ";
		tipo = "Recepción";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Mt940";
		tipo = "Recepción";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Mt942";
		tipo = "Recepción";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Mvtos. Ctas.pers.fich.-etebac ";
		tipo = "Recepción";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Mvtos.tarjeta crédito en fichero          ";
		tipo = "Recepción";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Mvtos.tarjeta cto en fich-altas automáticas      ";
		tipo = "Recepción";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Nóminas 34.14           ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="OMF Banco de España en fichero      ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Órdenes de emisión de cheques          ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Órdenes de nomina vía fichero ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Órdenes de transferencia vía fichero     ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Órdenes nominas en fichero 34.1         ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Órdenes pago internacionales en fichero          ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Pago internacionales - Bruselas            ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Pago nóminas - París   ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Pago proveedores - París        ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Pagos (payment) - N.Y.          ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Pagos chaps-tesorería mismo día Londres      ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Pagos domésticos - Bruselas   ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Pagos domésticos ach n.y.      ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Pagos financieros - n.y.           ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Pagos internacionales - París   ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Pagos internacionales Londres";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Pagos nacionales Londres       ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Pagos nóminas bacs Londres";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Rech. Adeud. Directos (Sepa B2B)     ";
		tipo = "Recepción";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Rechazos adeudos directos (Sepa)     ";
		tipo = "Recepción";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Sepa - pagos Londres ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Sepa - pagos nóminas París    ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Sepa - pagos París      ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Transferencia 34.12    ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Transferencias 34.14   ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Transferencias tesorería - París           ";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);
		s = new Servicio();
		 nombre ="Transferencias, en fichero 34.1";
		tipo = "Envío";
		s.setNme(nombre);
		s.setTipo(tipo);
		sDao.createServicio(s, usermail);		
		
	}
	
}
