package com.ste.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;











import javax.servlet.http.HttpServletRequest;

import com.ste.beans.Log;
import com.ste.beans.User;
import com.ste.dao.LogsDao;
import com.ste.dao.UserDao;

public class Utils {

	
	public static void writeLog(String usermail, String accion, String entidad, String nombre_entidad){
		
		if (!"".equals(usermail)){
			Log log = new Log();
			LogsDao lDao = LogsDao.getInstance();
				
			UserDao uDao = UserDao.getInstance();
			
			User u = uDao.getUserByMail(usermail);
			
			log.setNombre_entidad(nombre_entidad);
			log.setAccion(accion);
			log.setEntidad(entidad);
			log.setUsuario(u.getNombre() + " " + u.getApellido1() + " " + u.getApellido2());
			log.setUsuario_mail(u.getEmail());
			
			
			lDao.createLog(log);
		}
	
	}
	/*Metodo que devuelve la url completa de una peticion web incluyendo datos get y post*/
	public static String getAllUrl (HttpServletRequest req){
		
		String retorno =req.getRequestURL().toString();
		Enumeration parameters = req.getParameterNames();
		Object aux = parameters.nextElement();
		retorno = retorno+"?"+aux.toString()+"="+req.getParameter(aux.toString());
		while(parameters.hasMoreElements()){
			aux = parameters.nextElement();
			retorno=retorno+"&"+aux.toString()+"="+req.getParameter(aux.toString());
		}
		
		return retorno;
	}
	
	public static String getPermisoAreaStr(int num){
		
		String result="";
		
		if (num==1){
			result = "Propietario";
		} else if (num==2){
			result = "Editar";
		} else if (num==3){
			result = "Ver";
		}
		
		return result;
		
	}
	
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    //double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}

	public static String getPermisoStr(int permiso){
		String s = "";
		
		switch(permiso){
		case 1: s="Propietario";
			break;
		case 2: s="Edición";
			break;
		case 3: s="Consulta";
			break;
		
		}
		
		return s;
	}
	
	public static Date dateConverter(String cadena) throws ParseException{
		DateFormat formatter = null;
		formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date convertedDate = (Date) formatter.parse(cadena);
        
        return convertedDate;
	}
	

	public static List<String> getHorasList(){
		

		List<String> listaHoras = new ArrayList<String>();
		
		for (Integer a=0; a<24; a++){
			if (a.toString().length()==1){
				listaHoras.add("0"+a.toString());
			}else{
				listaHoras.add(a.toString());
			}
		}
		
		return listaHoras;
	}
	
	public static List<String> getMinutosList(){
		
		List<String> listaMinutos = new ArrayList<String>();
		
		for (Integer a=0; a<60; a=a+5){
			if (a.toString().length()==1){
				listaMinutos.add("0"+a.toString());
			}else{
				listaMinutos.add(a.toString());
			}
		}
		
		return listaMinutos;
	}

	public static String CalendarConverterToStr(Calendar fecha){
	  
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
      
      String fecha_str =  dateFormat.format(fecha.getTime());
      
      return fecha_str;
	}

	public static String dateConverterToStr(Date fecha){
		

		@SuppressWarnings("deprecation")
		String convertedDate = fecha.getDate() + "/" + (fecha.getMonth()+1) + "/" + (fecha.getYear() + 1900);
		
        return convertedDate;
	}

}
