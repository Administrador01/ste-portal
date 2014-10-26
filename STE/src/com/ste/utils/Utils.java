package com.ste.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utils {
	
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
	    double d = Double.parseDouble(str);  
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
		
		List<String> listaHoras = new ArrayList();
		
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
		
		List<String> listaMinutos = new ArrayList();
		
		for (Integer a=0; a<60; a=a+5){
			if (a.toString().length()==1){
				listaMinutos.add("0"+a.toString());
			}else{
				listaMinutos.add(a.toString());
			}
		}
		
		return listaMinutos;
	}
}
