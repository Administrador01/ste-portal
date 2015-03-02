package com.ste.servlets;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.ste.beans.Cliente;
import com.ste.beans.Estado;
import com.ste.beans.EstadoImplementacion;
import com.ste.beans.Implementacion;
import com.ste.beans.Prueba;
import com.ste.beans.Soporte;
import com.ste.dao.ClienteDao;
import com.ste.dao.EstadoDao;
import com.ste.dao.EstadoImplementacionDao;
import com.ste.dao.ImplementacionDao;
import com.ste.dao.PruebaDao;
import com.ste.dao.ServicioDao;
import com.ste.dao.SoporteDao;
import com.ste.utils.Utils;
import com.aspose.cells.Workbook.*;
import com.aspose.cells.SaveFormat;

import org.artofsolving.*;

public class InformeServlet extends HttpServlet{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp){


		
		
		
		 try {
			 
			HttpSession sesion = req.getSession();
			//int sesionpermiso = (int) sesion.getAttribute("permiso");			 
			String usermail = (String) sesion.getAttribute("mail");
			String accion = req.getParameter("accion");
			
			if(accion.equals("def"))informepordefecto(req,resp);
			if(accion.equals("pruebas"))informePruebas(req,resp);
			if(accion.equals("soporte"))informeSoportes(req,resp);
			if(accion.equals("cliente"))informeCliente(req,resp);
			if(accion.equals("implementaciones"))informeImplementaciones(req,resp);
			
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		doGet(req,resp);
	}
	
	private void informePruebas(HttpServletRequest req, HttpServletResponse resp)throws Exception {

		
		PruebaDao pruDao =PruebaDao.getInstance();
		EstadoDao est = EstadoDao.getInstance();
		
		List<Prueba> pruebas = pruDao.getAllPruebas();
		List<Estado> estados = est.getAllEstados();
		
		String fechaHasta = req.getParameter("fechaHasta");
		String fechaDesde = req.getParameter("fechaDesde");
		
		int tipoFecha = 1;
		
		if (!fechaHasta.equals("")&&fechaHasta!=null){
			if(!fechaDesde.equals("")&&fechaDesde!=null){
				Date fech = Utils.dateConverter(fechaDesde);
				Date haHasta = Utils.dateConverter(fechaHasta);
				pruebas = pruDao.getPruebasBetweenDates(fech,haHasta);

				tipoFecha=4;
			}else{
				Date dateHasta = Utils.dateConverter(fechaHasta);
				pruebas = pruDao.getPruebasUntilDate(dateHasta);
				tipoFecha=3;
			}
		}else{
			if(!fechaDesde.equals("")&&fechaDesde!=null){
				Date dateDesde = Utils.dateConverter(fechaDesde);
				pruebas = pruDao.getPruebasSinceDate(dateDesde);
				tipoFecha=2;
			}
		}
		
		int totalPruebas =pruebas.size();
		
		
		resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		resp.setHeader("Content-Disposition","attachment; filename=InformePruebasSTE.xlsx");
		String link= "/datadocs/templatePruebas.xlsx";
		InputStream inp = this.getServletContext().getResourceAsStream(link);
		Workbook workbook = new XSSFWorkbook(OPCPackage.open(inp));
		
		//CreationHelper createHelper = workbook.getCreationHelper();
		Sheet sh=workbook.getSheetAt(0);
		//String sheetName=sh.getSheetName();	
		
		/*ESTILO DE CELDA CON BORDES*/
		short width = 1;
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom(width);
		cellStyle.setBorderLeft(width);
		cellStyle.setBorderRight(width);
		cellStyle.setBorderTop(width);
		
		CellStyle clientCellStyle = workbook.createCellStyle();
		clientCellStyle.setBorderBottom(width);
		clientCellStyle.setBorderLeft(width);
		clientCellStyle.setBorderRight(width);
		clientCellStyle.setBorderTop((short)0);
		
		CellStyle footerCellStyle = workbook.createCellStyle();
		footerCellStyle.setBorderBottom(width);
		footerCellStyle.setBorderLeft(width);
		footerCellStyle.setBorderRight(width);
		footerCellStyle.setBorderTop((short)2);
		
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setBorderBottom((short)2);
		headerCellStyle.setBorderLeft(width);
		headerCellStyle.setBorderRight(width);
		headerCellStyle.setBorderTop(width);	
		/*------------------------------------------TABLA 1---------------------------------------------*/
		int num = 0;
		for(Estado estado: estados){
			//Creacion de las celdas con bordes de manera dinamica
			sh.createRow(8+num).createCell(5).setCellStyle(clientCellStyle);
			sh.getRow(8+num).createCell(6).setCellStyle(clientCellStyle);
			sh.getRow(8+num).createCell(7).setCellStyle(clientCellStyle);
			sh.getRow(8+num).createCell(8).setCellStyle(clientCellStyle);
			sh.getRow(8+num).getCell(5).setCellValue(estado.getName());
			sh.getRow(8+num).getCell(6).setCellValue(0.0);
			sh.getRow(8+num).getCell(7).setCellValue(0.0);
			sh.getRow(8+num).getCell(8).setCellValue(0.0);
			num++;
		}
		for(Prueba pru : pruebas){
			for(int i =0;i<estados.size();i++){
				
				
				if(pru.getEntorno().equals("Producci&oacuten")){
					
					if(pru.getEstado().equals(estados.get(i).getName()))sh.getRow(i+8).getCell(7).setCellValue(sh.getRow(i+8).getCell(7).getNumericCellValue()+1);
					
				}else{
					if(pru.getEntorno().equals("Integrado")){
						
						if(pru.getEstado().equals(estados.get(i).getName()))sh.getRow(i+8).getCell(6).setCellValue(sh.getRow(i+8).getCell(6).getNumericCellValue()+1);
						
					}
				}
				if(pru.getEstado().equals(estados.get(i).getName()))sh.getRow(i+8).getCell(8).setCellValue(sh.getRow(i+8).getCell(8).getNumericCellValue()+1);
			}
		}
		
		sh.createRow(estados.size()+8).createCell(5).setCellStyle(footerCellStyle);
		sh.getRow(estados.size()+8).createCell(6).setCellStyle(footerCellStyle);
		sh.getRow(estados.size()+8).createCell(7).setCellStyle(footerCellStyle);
		sh.getRow(estados.size()+8).createCell(8).setCellStyle(footerCellStyle);
		sh.getRow(estados.size()+8).getCell(5).setCellValue("Total");
		sh.getRow(estados.size()+8).getCell(6).setCellFormula("SUM(G"+(8+1)+":G"+(8+estados.size())+")");
		sh.getRow(estados.size()+8).getCell(7).setCellFormula("SUM(H"+(8+1)+":H"+(8+estados.size())+")");
		sh.getRow(estados.size()+8).getCell(8).setCellFormula("SUM(I"+(8+1)+":I"+(8+estados.size())+")");
		/*------------------------------FIN DE TABLA 1-------------------------------------------------------*/
		
		
		/*------------------------------TABLA FICHEROS--------------------------------------------------------------*/

		switch (tipoFecha){
		case 1:
			pruebas = pruDao.getAllPruebas();
			break;
		case 2:
			Date dateDesde = Utils.dateConverter(fechaDesde);
			pruebas = pruDao.getPruebasSinceDate(dateDesde);
			break;
		case 3:
			Date dateHasta = Utils.dateConverter(fechaHasta);
			pruebas = pruDao.getPruebasUntilDate(dateHasta);
			break;
		case 4:
			Date datDesde = Utils.dateConverter(fechaDesde);
			Date datHasta = Utils.dateConverter(fechaHasta);
			pruebas = pruDao.getPruebasBetweenDates(datDesde, datHasta);
			break;
		default:
			break;
		}
		
		List<String> ficheros = new ArrayList<String>();
		
		for(Prueba prueba : pruebas){
			if(ficheros.contains(prueba.getFichero())){
				int pos = ficheros.indexOf(prueba.getFichero());
				sh.getRow(49+pos).getCell(6).setCellValue(sh.getRow(49+pos).getCell(6).getNumericCellValue()+1);
			}else{
				
				sh.createRow(49+ficheros.size()).createCell(5).setCellStyle(clientCellStyle);
				sh.getRow(49+ficheros.size()).getCell(5).setCellValue(prueba.getFichero());
				sh.getRow(49+ficheros.size()).createCell(6).setCellStyle(clientCellStyle);
				sh.getRow(49+ficheros.size()).getCell(6).setCellValue(1);				
				ficheros.add(prueba.getFichero());
			}
		}
		
		
		sh.createRow(49+ficheros.size()).createCell(5).setCellStyle(footerCellStyle);
		sh.getRow(49+ficheros.size()).getCell(5).setCellValue("Total");
		sh.getRow(49+ficheros.size()).createCell(6).setCellStyle(footerCellStyle);
		sh.getRow(49+ficheros.size()).getCell(6).setCellValue(pruebas.size());
		sh.getRow(49+ficheros.size()).createCell(7).setCellStyle(footerCellStyle);
		sh.getRow(49+ficheros.size()).getCell(7).setCellValue(100);		
		
		for(int head = 0 ; head<ficheros.size();head++){
			sh.getRow(49+head).createCell(7).setCellStyle(footerCellStyle);
			sh.getRow(49+head).getCell(7).setCellFormula("INT("+sh.getRow(49+head).getCell(6).getNumericCellValue()*100+"/"+pruebas.size()+")");		
		}
		/*------------------------------FIN TABLA FICHEROS----------------------------------------------------------*/
		
		int head= ficheros.size()+49+14; 										//A partir de ahora lo leeremos para las filas como si se tratase de un fichero ya que se actura sobre filas variables
		//numero ficheros + desplazamento a headFicheros + espacio entre tablas
		
		sh.createRow(head).createCell(6).setCellStyle(headerCellStyle);
		sh.getRow(head).getCell(6).setCellValue("Integrado");
		sh.getRow(head).createCell(7).setCellStyle(headerCellStyle);
		sh.getRow(head).getCell(7).setCellValue("Producción");
		sh.getRow(head).createCell(8).setCellStyle(headerCellStyle);
		sh.getRow(head).getCell(8).setCellValue("Total pruebas");
		sh.getRow(head).createCell(9).setCellStyle(headerCellStyle);
		sh.getRow(head).getCell(9).setCellValue("Total porcentual");
		head++;
		
		int headClientes= head;
		
		
		
		ClienteDao clientDao = ClienteDao.getInstance();
		List<Cliente> clientes = clientDao.getAllClients();
		/*------------------------------TABLA CLIENTES--------------------------------------------------------------*/
		int i = 0;
		for(Cliente cli :clientes){
			switch (tipoFecha){
				case 1:
					pruebas = pruDao.getAllPruebasByClientId(Long.toString(cli.getKey().getId()));
					break;
				case 2:
					Date dateDesde = Utils.dateConverter(fechaDesde);
					
					pruebas = pruDao.getPruebasSinceDateByClientId(Long.toString(cli.getKey().getId()), dateDesde);
					break;
				case 3:
					Date dateHasta = Utils.dateConverter(fechaHasta);
					pruebas = pruDao.getPruebasUntilDateByClientId(Long.toString(cli.getKey().getId()), dateHasta);
					break;
				case 4:
					Date datDesde = Utils.dateConverter(fechaDesde);
					Date datHasta = Utils.dateConverter(fechaHasta);
					pruebas = pruDao.getPruebasBetweenDatesByClientId(Long.toString(cli.getKey().getId()), datDesde, datHasta);
					break;
				default:
					break;
			}
			sh.createRow(head).createCell(5).setCellStyle(cellStyle);
			sh.getRow(head).createCell(6).setCellStyle(clientCellStyle);
			sh.getRow(head).createCell(7).setCellStyle(clientCellStyle);
			sh.getRow(head).createCell(8).setCellStyle(clientCellStyle);
			sh.getRow(head).getCell(5).setCellValue(cli.getId_cliente()+"  -  "+cli.getNombre());
			sh.getRow(head).getCell(6).setCellValue(0.0);
			sh.getRow(head).getCell(7).setCellValue(0.0);
			sh.getRow(head).getCell(8).setCellValue(0.0);


			for(Prueba pru : pruebas){
				if(pru.getEntorno().equals("Integrado"))sh.getRow(head).getCell(6).setCellValue(sh.getRow(head).getCell(6).getNumericCellValue()+1);
				if(pru.getEntorno().equals("Producci&oacuten"))sh.getRow(head).getCell(7).setCellValue(sh.getRow(head).getCell(7).getNumericCellValue()+1);
				sh.getRow(head).getCell(8).setCellValue(sh.getRow(head).getCell(8).getNumericCellValue()+1);				
			}
			
			
			sh.getRow(head).createCell(9).setCellStyle(clientCellStyle);
			sh.getRow(head).getCell(9).setCellFormula("INT("+sh.getRow(head).getCell(8).getNumericCellValue()*100+"/"+totalPruebas+")");
			head++;
			i++;
		}
		
		sh.createRow(head).createCell(5).setCellStyle(footerCellStyle);
		sh.getRow(head).createCell(6).setCellStyle(footerCellStyle);
		sh.getRow(head).createCell(7).setCellStyle(footerCellStyle);
		sh.getRow(head).createCell(8).setCellStyle(footerCellStyle);
		sh.getRow(head).createCell(9).setCellStyle(footerCellStyle);
		sh.getRow(head).getCell(5).setCellValue("Total");
		sh.getRow(head).getCell(6).setCellFormula("SUM(G"+(headClientes)+":G"+(head)+")");
		sh.getRow(head).getCell(7).setCellFormula("SUM(H"+(headClientes)+":H"+(head)+")");
		sh.getRow(head).getCell(8).setCellFormula("SUM(I"+(headClientes)+":I"+(head)+")");
		sh.getRow(head).getCell(9).setCellValue(1);
		
		
		
		/*------------------------------FIN TABLA CLIENTES----------------------------------------------------------*/
		
		
		
		
		Date dateDesde= null;
		Date dateHasta= null;
		switch (tipoFecha){
		case 1:
			dateDesde = Utils.dateConverter("29/11/1857");
			dateHasta = Utils.dateConverter("29/11/2500");
			break;
		case 2:
			dateDesde = Utils.dateConverter(fechaDesde);
			dateHasta = Utils.dateConverter("29/11/2500");
			break;
		case 3:
			dateDesde = Utils.dateConverter("29/11/1857");
			dateHasta = Utils.dateConverter(fechaHasta);
			break;
		case 4:
			dateDesde = Utils.dateConverter(fechaDesde);
			dateHasta = Utils.dateConverter(fechaHasta);
			
			
			break;
		default:
			break;
		}
		
		/*Tabla ok ko generada estaticamente*/
		sh.getRow(29).createCell(6).setCellStyle(clientCellStyle);
		sh.getRow(29).getCell(6).setCellValue((pruDao.getPruebasByResultado("OK", dateDesde, dateHasta)).size());
		sh.getRow(29).createCell(7).setCellStyle(clientCellStyle);
		sh.getRow(29).getCell(7).setCellFormula("INT("+sh.getRow(29).getCell(6).getNumericCellValue()*100+"/"+totalPruebas+")");
		sh.getRow(30).createCell(6).setCellStyle(clientCellStyle);
		sh.getRow(30).createCell(7).setCellStyle(clientCellStyle);
		sh.getRow(30).getCell(6).setCellValue((pruDao.getPruebasByResultado("KO", dateDesde, dateHasta)).size());
		sh.getRow(30).getCell(7).setCellFormula("INT("+sh.getRow(30).getCell(6).getNumericCellValue()*100+"/"+totalPruebas+")");
		sh.getRow(31).createCell(6).setCellStyle(clientCellStyle);
		sh.getRow(31).createCell(7).setCellStyle(clientCellStyle);
		sh.getRow(31).getCell(6).setCellValue((pruDao.getPruebasByResultado("Cancelada", dateDesde, dateHasta)).size());
		sh.getRow(31).getCell(7).setCellFormula("INT("+sh.getRow(31).getCell(6).getNumericCellValue()*100+"/"+totalPruebas+")");
		sh.getRow(32).createCell(6).setCellStyle(footerCellStyle);
		sh.getRow(32).getCell(6).setCellValue(totalPruebas);
		
		
		/*Tabla de fichero generada estaticamente para desplegable*/
//		sh.getRow(29).createCell(6).setCellStyle(clientCellStyle);
//		sh.getRow(29).createCell(7).setCellStyle(clientCellStyle);
//		sh.getRow(29).getCell(6).setCellValue((pruDao.getPruebasByFichero("Confirming", dateDesde, dateHasta)).size());
//		sh.getRow(29).getCell(7).setCellFormula("INT("+sh.getRow(39).getCell(6).getNumericCellValue()*100+"/"+totalPruebas+")");
//		
//		sh.getRow(30).createCell(6).setCellStyle(clientCellStyle);
//		sh.getRow(30).createCell(7).setCellStyle(clientCellStyle);
//		sh.getRow(30).getCell(6).setCellValue((pruDao.getPruebasByFichero("FIT", dateDesde, dateHasta)).size());
//		sh.getRow(30).getCell(7).setCellFormula("INT("+sh.getRow(30).getCell(6).getNumericCellValue()*100+"/"+totalPruebas+")");
//		
//		sh.getRow(31).createCell(6).setCellStyle(clientCellStyle);
//		sh.getRow(31).createCell(7).setCellStyle(clientCellStyle);
//		sh.getRow(31).getCell(6).setCellValue((pruDao.getPruebasByFichero("Pagos Francia", dateDesde, dateHasta)).size());
//		sh.getRow(31).getCell(7).setCellFormula("INT("+sh.getRow(31).getCell(6).getNumericCellValue()*100+"/"+totalPruebas+")");
//		
//		sh.getRow(32).createCell(6).setCellStyle(clientCellStyle);
//		sh.getRow(32).createCell(7).setCellStyle(clientCellStyle);
//		sh.getRow(32).getCell(6).setCellValue((pruDao.getPruebasByFichero("SDD", dateDesde, dateHasta)).size());
//		sh.getRow(32).getCell(7).setCellFormula("INT("+sh.getRow(31).getCell(6).getNumericCellValue()*100+"/"+totalPruebas+")");
//		
//		sh.getRow(33).createCell(6).setCellStyle(clientCellStyle);
//		sh.getRow(33).createCell(7).setCellStyle(clientCellStyle);
//		sh.getRow(33).getCell(6).setCellValue((pruDao.getPruebasByFichero("TIN", dateDesde, dateHasta)).size());
//		sh.getRow(33).getCell(7).setCellFormula("INT("+sh.getRow(31).getCell(6).getNumericCellValue()*100+"/"+totalPruebas+")");
//		
//		sh.getRow(34).createCell(6).setCellStyle(clientCellStyle);
//		sh.getRow(34).createCell(7).setCellStyle(clientCellStyle);
//		sh.getRow(34).getCell(6).setCellValue((pruDao.getPruebasByFichero("Pagos UK", dateDesde, dateHasta)).size());
//		sh.getRow(34).getCell(7).setCellFormula("INT("+sh.getRow(31).getCell(6).getNumericCellValue()*100+"/"+totalPruebas+")");
//		
//		sh.getRow(35).createCell(6).setCellStyle(footerCellStyle);
//		sh.getRow(35).getCell(6).setCellValue(totalPruebas);
		
		
		
		//Escribimos los rangos de valores para dibujar las graficas
		String sheetName = workbook.getSheetName(0);

				
		Name rangeTotalPorClie = workbook.getName("TotalPorCliente");
		Name rangeClientes = workbook.getName("Clientes");
		rangeTotalPorClie.setRefersToFormula(sheetName+"!$I$"+(headClientes+1)+":$I$"+head);
		rangeClientes.setRefersToFormula(sheetName+"!$F$"+(headClientes+1)+":$F$"+head);
		Name rangeTotal = workbook.getName("Total");
		Name rangeEstados = workbook.getName("Estados");
		rangeTotal.setRefersToFormula(sheetName+"!$I$"+9+":$I$"+(estados.size()+8));
		rangeEstados.setRefersToFormula(sheetName+"!$F$"+9+":$F$"+(estados.size()+8));
		Name rangeEntorno = workbook.getName("PruebasPorEntorno");
		Name rangeEntornos = workbook.getName("Entorno");
		rangeEntornos.setRefersToFormula(sheetName+"!$G$"+(headClientes)+":$H$"+(headClientes));
		rangeEntorno.setRefersToFormula(sheetName+"!$G$"+(head+1)+":$H$"+(head+1));
		Name rangePruebPorFich = workbook.getName("PruebasPorFichero");
		rangePruebPorFich.setRefersToFormula(sheetName+"!$G$"+50+":$G$"+(ficheros.size()+49));
		Name rangeTipoFich = workbook.getName("TipoFichero");
		rangeTipoFich.setRefersToFormula(sheetName+"!$F$"+50+":$F$"+(ficheros.size()+49));

		//rangeResultados.setRefersToFormula("");
		
		
		workbook.write(resp.getOutputStream());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void informeImplementaciones(HttpServletRequest req, HttpServletResponse resp)throws Exception {
		
		
		
		/*obtenemos las fechas para las que va a aplicarse el filtro*/
		String fechaHasta = req.getParameter("fechaHasta");
		String fechaDesde = req.getParameter("fechaDesde");
		
		Date dateDesde=null;
		Date dateHasta=null;
		
		int tipoFecha = 1;
		
		if (!fechaHasta.equals("")&&fechaHasta!=null){
			if(!fechaDesde.equals("")&&fechaDesde!=null){
				tipoFecha=4;
			}else{
				tipoFecha=3;
			}
		}else{
			if(!fechaDesde.equals("")&&fechaDesde!=null){
				tipoFecha=2;
			}
		}
		
		switch (tipoFecha){
			case 1:
				dateDesde = Utils.dateConverter("29/11/1857");
				dateHasta = Utils.dateConverter("29/11/2500");
				break;
			case 2:
				dateDesde = Utils.dateConverter(fechaDesde);
				dateHasta = Utils.dateConverter("29/11/2500");
				break;
			case 3:
				dateDesde = Utils.dateConverter("29/11/1857");
				dateHasta = Utils.dateConverter(fechaHasta);
				break;
			case 4:
				dateDesde = Utils.dateConverter(fechaDesde);
				dateHasta = Utils.dateConverter(fechaHasta);
				
				
				break;
			default:
				break;
		}
		
		/*INICIAMOS EL LIBRO EXCEL E INDICAMOS LA RESPUESTA DEL SERVIDOR*/
		resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		resp.setHeader("Content-Disposition","attachment; filename=InformeImplantacionesSTE.xlsx");
		String link= "/datadocs/templateImplementaciones.xlsx";
		InputStream inp = this.getServletContext().getResourceAsStream(link);
		Workbook workbook = new XSSFWorkbook(OPCPackage.open(inp));
		
		
		
		Sheet sh=workbook.getSheetAt(0);
			
		
		/*ESTILO DE CELDA CON BORDES*/
		short width = 1;
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom(width);
		cellStyle.setBorderLeft(width);
		cellStyle.setBorderRight(width);
		cellStyle.setBorderTop(width);
		
		CellStyle clientCellStyle = workbook.createCellStyle();
		clientCellStyle.setBorderBottom(width);
		clientCellStyle.setBorderLeft(width);
		clientCellStyle.setBorderRight(width);
		clientCellStyle.setBorderTop((short)0);
		
		CellStyle footerCellStyle = workbook.createCellStyle();
		footerCellStyle.setBorderBottom(width);
		footerCellStyle.setBorderLeft(width);
		footerCellStyle.setBorderRight(width);
		footerCellStyle.setBorderTop((short)2);
		
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setBorderBottom((short)2);
		headerCellStyle.setBorderLeft(width);
		headerCellStyle.setBorderRight(width);
		headerCellStyle.setBorderTop(width);
		
		
		/*-------------------------------------TABLA DE IMPLEMENTACIONES POR ESTADO------------------------------------------------------*/
		ImplementacionDao impDao = ImplementacionDao.getInstance();
		
		
		EstadoImplementacionDao estDao = EstadoImplementacionDao.getInstance();
		List<EstadoImplementacion> estados = estDao.getAllEstadoImplementacions();
		
		int headEstados = 4;
		int head = 4;
		
		for(EstadoImplementacion estado: estados){
			//Creacion de las celdas con bordes de manera dinamica
			sh.createRow(head).createCell(2).setCellStyle(clientCellStyle);
			sh.getRow(head).createCell(3).setCellStyle(clientCellStyle);
			sh.getRow(head).createCell(4).setCellStyle(clientCellStyle);
			sh.getRow(head).getCell(2).setCellValue(estado.getName());
			sh.getRow(head).getCell(3).setCellValue(impDao.getNumberForVariableValueBetween("estado", estado.getName(),dateDesde,dateHasta));
			head++;
		}
		
		sh.createRow(head).createCell(2).setCellStyle(footerCellStyle);
		sh.getRow(head).createCell(3).setCellStyle(footerCellStyle);
		sh.getRow(head).createCell(4).setCellStyle(footerCellStyle);
		sh.getRow(head).getCell(2).setCellValue("Total de implementaciones:");
		sh.getRow(head).getCell(3).setCellValue(impDao.getAllImplementacionesBetweenDates(dateDesde, dateHasta).size());
		sh.getRow(head).getCell(4).setCellValue(1);
		
		int totalImp = impDao.getAllImplementacionesBetweenDates(dateDesde, dateHasta).size();
		
		for(int i=0; i<(head-headEstados);i++){
			sh.getRow(headEstados+i).getCell(4).setCellFormula("INT("+sh.getRow(headEstados+i).getCell(3).getNumericCellValue()*100+"/"+totalImp+")");
		}
		
		String sheetName = workbook.getSheetName(0);
		Name rangeImpEstados = workbook.getName("ImplementacionesEstados");
		Name rangeEstados = workbook.getName("Estados");
		rangeImpEstados.setRefersToFormula(sheetName+"!$D$"+(headEstados+1)+":$D$"+head);
		rangeEstados.setRefersToFormula(sheetName+"!$C$"+(headEstados+1)+":$C$"+head);
		
		/*-----------------------FIN TABLA IMPLEMENTACIONES POR ESTADO --------------------------------------------*/
		
		head = head+16;
		int headNuevas = head;
		
		ClienteDao cliDao = ClienteDao.getInstance();
		List<Implementacion> implementaciones = impDao.getAllImplementacionesBetweenDates(dateDesde, dateHasta);
		
		ServicioDao servDao = ServicioDao.getInstance();
		
		/*----------------------TABLA DE NUEVAS IMPLEMENTACIONES -------------------------------------------------*/
		
		
		sh.createRow(head).createCell(2).setCellStyle(headerCellStyle);
		sh.getRow(head).getCell(2).setCellValue("Cliente");
		sh.getRow(head).createCell(3).setCellStyle(headerCellStyle);
		sh.getRow(head).getCell(3).setCellValue("Producto");
		sh.getRow(head).createCell(4).setCellStyle(headerCellStyle);
		sh.getRow(head).getCell(4).setCellValue("Servicio");
		sh.getRow(head).createCell(5).setCellStyle(headerCellStyle);
		sh.getRow(head).getCell(5).setCellValue("Fecha alta");
		head++;
		
		
		for(Implementacion imp:implementaciones){
			
			sh.createRow(head).createCell(2).setCellStyle(clientCellStyle);
			sh.getRow(head).createCell(3).setCellStyle(clientCellStyle);
			sh.getRow(head).createCell(4).setCellStyle(clientCellStyle);
			sh.getRow(head).createCell(5).setCellStyle(clientCellStyle);
			
			sh.getRow(head).getCell(2).setCellValue(cliDao.getClientebyId(imp.getCliente_id()).getNombre());	
			sh.getRow(head).getCell(3).setCellValue(imp.getProducto());
			sh.getRow(head).getCell(4).setCellValue(servDao.getImplementacionById(imp.getServicio_id()).getName());
			sh.getRow(head).getCell(5).setCellValue(imp.getStr_fecha_alta());
			head++;
		}
		
		int totalNuevas = implementaciones.size();
		sh.createRow(head).createCell(2).setCellStyle(footerCellStyle);
		sh.getRow(head).createCell(3).setCellStyle(footerCellStyle);
		sh.getRow(head).getCell(2).setCellValue("Nuevas implementaciones:");
		sh.getRow(head).getCell(3).setCellValue(totalNuevas);
		head++;
		sh.createRow(head).createCell(2).setCellStyle(footerCellStyle);
		sh.getRow(head).createCell(3).setCellStyle(footerCellStyle);
		sh.getRow(head).getCell(2).setCellValue("Implementaciones antiguas:");
		sh.getRow(head).getCell(3).setCellValue(totalImp-totalNuevas);
		head++;
		

		
		Name rangeNuevImp = workbook.getName("NuevasImp");
		Name rangeNameNuevImp = workbook.getName("NameNuevasImp");
		rangeNuevImp.setRefersToFormula(sheetName+"!$D$"+(head-1)+":"+sheetName+"!$D$"+head);
		rangeNameNuevImp.setRefersToFormula(sheetName+"!$C$"+(head-1)+":"+sheetName+"!$C$"+head);
		
		/*----------------------FIN TABLA DE NUEVAS IMPLEMENTACIONES -------------------------------------------------*/
		
		implementaciones = impDao.getAllSubidasBetweenDates(dateDesde, dateHasta);
		head = head+16;
		int headSubidas = head;
		
		/*----------------------TABLA DE SUBIDAS -------------------------------------------------*/
		
		sh.createRow(head).createCell(2).setCellStyle(headerCellStyle);
		sh.getRow(head).getCell(2).setCellValue("Cliente");
		sh.getRow(head).createCell(3).setCellStyle(headerCellStyle);
		sh.getRow(head).getCell(3).setCellValue("Producto");
		sh.getRow(head).createCell(4).setCellStyle(headerCellStyle);
		sh.getRow(head).getCell(4).setCellValue("Servicio");
		sh.getRow(head).createCell(5).setCellStyle(headerCellStyle);
		sh.getRow(head).getCell(5).setCellValue("Fecha subida");
		sh.getRow(head).createCell(6).setCellStyle(headerCellStyle);
		sh.getRow(head).getCell(6).setCellValue("Observaciones");
		head++;
		
		for(Implementacion imp:implementaciones){
			
			sh.createRow(head).createCell(2).setCellStyle(clientCellStyle);
			sh.getRow(head).createCell(3).setCellStyle(clientCellStyle);
			sh.getRow(head).createCell(4).setCellStyle(clientCellStyle);
			sh.getRow(head).createCell(5).setCellStyle(clientCellStyle);
			sh.getRow(head).createCell(6).setCellStyle(clientCellStyle);
			
			sh.getRow(head).getCell(2).setCellValue(cliDao.getClientebyId(imp.getCliente_id()).getNombre());	
			sh.getRow(head).getCell(3).setCellValue(imp.getProducto());
			sh.getRow(head).getCell(4).setCellValue(servDao.getImplementacionById(imp.getServicio_id()).getName());
			sh.getRow(head).getCell(5).setCellValue(imp.getStr_fech_subida());
			sh.getRow(head).getCell(6).setCellValue(imp.getDetalle());
			head++;
		}
		int totalSubidas = implementaciones.size();
		sh.createRow(head).createCell(2).setCellStyle(footerCellStyle);
		sh.getRow(head).createCell(3).setCellStyle(footerCellStyle);
		sh.getRow(head).getCell(2).setCellValue("Total de subidas a producción:");
		sh.getRow(head).getCell(3).setCellValue(totalSubidas);
		head++;

		sh.createRow(head).createCell(2).setCellStyle(footerCellStyle);
		sh.getRow(head).createCell(3).setCellStyle(footerCellStyle);
		sh.getRow(head).getCell(2).setCellValue("Implementaciones no subidas en fecha:");
		sh.getRow(head).getCell(3).setCellValue(totalImp-totalSubidas);
		head++;
		

		
		Name rangeSubidImp = workbook.getName("SubidasImp");
		Name rangeNameSubidImp = workbook.getName("NameSubidasImp");
		rangeSubidImp.setRefersToFormula(sheetName+"!$D$"+(head-1)+":"+sheetName+"!$D$"+head);
		rangeNameSubidImp.setRefersToFormula(sheetName+"!$C$"+(head-1)+":"+sheetName+"!$C$"+head);

		
		/*----------------------FIN TABLA DE SUBIDAS -------------------------------------------------*/
		workbook.write(resp.getOutputStream());
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void informeSoportes(HttpServletRequest req, HttpServletResponse resp)throws Exception {
		String fechaHasta = req.getParameter("fechaHasta");
		String fechaDesde = req.getParameter("fechaDesde");
		
		SoporteDao sopDao = SoporteDao.getInstance();
		EstadoDao est = EstadoDao.getInstance();
		
		ClienteDao cliDao = ClienteDao.getInstance();
		List<Estado> estados = est.getAllEstados();
		List<Soporte> soportes = sopDao.getAllSoportes();
		
		int tipoFecha = 1;
		
		if (!fechaHasta.equals("")&&fechaHasta!=null){
			if(!fechaDesde.equals("")&&fechaDesde!=null){
				Date fech = Utils.dateConverter(fechaDesde);
				Date haHasta = Utils.dateConverter(fechaHasta);
				soportes = sopDao.getSoportesBetweenDates(fech,haHasta);

				tipoFecha=4;
			}else{
				Date dateHasta = Utils.dateConverter(fechaHasta);
				soportes = sopDao.getSoportesUntilDate(dateHasta);
				tipoFecha=3;
			}
		}else{
			if(!fechaDesde.equals("")&&fechaDesde!=null){
				Date dateDesde = Utils.dateConverter(fechaDesde);
				soportes = sopDao.getSoportesSinceDate(dateDesde);
				tipoFecha=2;
			}
		}
		
		int totalSoportes = soportes.size();
		
		
		resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		resp.setHeader("Content-Disposition","attachment; filename=InformePruebasSTE.xlsx");
		String link= "/datadocs/templateSoportes.xlsx";
		InputStream inp = this.getServletContext().getResourceAsStream(link);
		Workbook workbook = new XSSFWorkbook(OPCPackage.open(inp));
		
		
		//CreationHelper createHelper = workbook.getCreationHelper();
		Sheet sh=workbook.getSheetAt(0);
		//String sheetName=sh.getSheetName();	
		
		/*ESTILO DE CELDA CON BORDES*/
		short width = 1;
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom(width);
		cellStyle.setBorderLeft(width);
		cellStyle.setBorderRight(width);
		cellStyle.setBorderTop(width);
		
		CellStyle clientCellStyle = workbook.createCellStyle();
		clientCellStyle.setBorderBottom(width);
		clientCellStyle.setBorderLeft(width);
		clientCellStyle.setBorderRight(width);
		clientCellStyle.setBorderTop((short)0);
		
		CellStyle footerCellStyle = workbook.createCellStyle();
		footerCellStyle.setBorderBottom(width);
		footerCellStyle.setBorderLeft(width);
		footerCellStyle.setBorderRight(width);
		footerCellStyle.setBorderTop((short)2);
		
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setBorderBottom((short)2);
		headerCellStyle.setBorderLeft(width);
		headerCellStyle.setBorderRight(width);
		headerCellStyle.setBorderTop(width);
		
		
		/*------------------------------TABLA estados--------------------------------------------------------------*/
		int num = 0;
		for(Estado estado: estados){
			//Creacion de las celdas con bordes de manera dinamica
			sh.createRow(10+num).createCell(10).setCellStyle(clientCellStyle);
			sh.getRow(10+num).createCell(11).setCellStyle(clientCellStyle);
			sh.getRow(10+num).createCell(12).setCellStyle(clientCellStyle);
			sh.getRow(10+num).createCell(13).setCellStyle(clientCellStyle);
			sh.getRow(10+num).getCell(10).setCellValue(estado.getName());
			sh.getRow(10+num).getCell(11).setCellValue(0.0);
			sh.getRow(10+num).getCell(12).setCellValue(0.0);
			sh.getRow(10+num).getCell(13).setCellValue(0.0);
			num++;
		}
		for(Soporte sop : soportes){
			for(int i =0;i<estados.size();i++){
				/*primer if para gestion de cliente = ninguno*/
				if(sop.getCliente_id()==""){
					if(sop.getEstado().equals(estados.get(i).getName()))sh.getRow(i+10).getCell(12).setCellValue(sh.getRow(i+10).getCell(12).getNumericCellValue()+1);
					if(sop.getEstado().equals(estados.get(i).getName()))sh.getRow(i+10).getCell(13).setCellValue(sh.getRow(i+10).getCell(13).getNumericCellValue()+1);
				}else{
					if(cliDao.getClientebyId(Long.parseLong(sop.getCliente_id())).getPremium().equals("Premium")){
						
						if(sop.getEstado().equals(estados.get(i).getName()))sh.getRow(i+10).getCell(11).setCellValue(sh.getRow(i+10).getCell(11).getNumericCellValue()+1);
						
					}else{
						if(cliDao.getClientebyId(Long.parseLong(sop.getCliente_id())).getPremium().equals("No Premium")){
							
							if(sop.getEstado().equals(estados.get(i).getName()))sh.getRow(i+10).getCell(12).setCellValue(sh.getRow(i+10).getCell(12).getNumericCellValue()+1);
							
						}
					}
					if(sop.getEstado().equals(estados.get(i).getName()))sh.getRow(i+10).getCell(13).setCellValue(sh.getRow(i+10).getCell(13).getNumericCellValue()+1);
				}
			}
		}
		
		sh.createRow(estados.size()+10).createCell(10).setCellStyle(footerCellStyle);
		sh.getRow(estados.size()+10).createCell(11).setCellStyle(footerCellStyle);
		sh.getRow(estados.size()+10).createCell(12).setCellStyle(footerCellStyle);
		sh.getRow(estados.size()+10).createCell(13).setCellStyle(footerCellStyle);
		sh.getRow(estados.size()+10).getCell(10).setCellValue("Total");
		sh.getRow(estados.size()+10).getCell(11).setCellFormula("SUM(L"+(10+1)+":L"+(10+estados.size())+")");
		sh.getRow(estados.size()+10).getCell(12).setCellFormula("SUM(M"+(10+1)+":M"+(10+estados.size())+")");
		sh.getRow(estados.size()+10).getCell(13).setCellFormula("SUM(N"+(10+1)+":N"+(10+estados.size())+")");
		
		
		String sheetName = workbook.getSheetName(0);
		
		Name rangeTotal = workbook.getName("totalSopEstado");
		Name rangeEstados = workbook.getName("estadosSop");
		rangeTotal.setRefersToFormula(sheetName+"!$N$"+(10+1)+":$N$"+(estados.size()+(10)));
		rangeEstados.setRefersToFormula(sheetName+"!$K$"+(10+1)+":$K$"+(estados.size()+(10)));
		
		
		/*------------------------------fin tabla estados--------------------------------------------------------------*/
		
		int head= estados.size()+10+14; 										//A partir de ahora lo leeremos para las filas como si se tratase de un fichero ya que se actura sobre filas variables
		//numero ficheros + desplazamento a headFicheros + espacio entre tablas
		
		sh.createRow(head).createCell(11).setCellStyle(headerCellStyle);
		sh.getRow(head).getCell(11).setCellValue("INCIDENCIA");
		sh.getRow(head).createCell(12).setCellStyle(headerCellStyle);
		sh.getRow(head).getCell(12).setCellValue("CONSULTA");
		sh.getRow(head).createCell(13).setCellStyle(headerCellStyle);
		sh.getRow(head).getCell(13).setCellValue("TOTAL");
		sh.getRow(head).createCell(14).setCellStyle(headerCellStyle);
		sh.getRow(head).getCell(14).setCellValue("TOTAL PORCENTUAL");
		head++;
		
		int headClientes= head;
		
		
		
		ClienteDao clientDao = ClienteDao.getInstance();
		List<Cliente> clientes = clientDao.getAllClients();
		/*------------------------------TABLA CLIENTES--------------------------------------------------------------*/
		Date dateDesde = null;
		Date dateHasta = null;
		
		int i = 0;
		for(Cliente cli :clientes){
			switch (tipoFecha){
				case 1:
					soportes = sopDao.getAllSoportesByClientId(Long.toString(cli.getKey().getId()));
					break;
				case 2:
					dateDesde = Utils.dateConverter(fechaDesde);
					
					soportes = sopDao.getSoportesSinceDateByClientId(Long.toString(cli.getKey().getId()), dateDesde);
					break;
				case 3:
					dateHasta = Utils.dateConverter(fechaHasta);
					soportes = sopDao.getSoportesUntilDateByClientId(Long.toString(cli.getKey().getId()), dateHasta);
					break;
				case 4:
					dateDesde = Utils.dateConverter(fechaDesde);
					dateHasta = Utils.dateConverter(fechaHasta);
					soportes = sopDao.getSoportesBetweenDatesByClientId(Long.toString(cli.getKey().getId()), dateDesde, dateHasta);
					break;
				default:
					break;
			}
			sh.createRow(head).createCell(10).setCellStyle(cellStyle);
			sh.getRow(head).createCell(11).setCellStyle(clientCellStyle);
			sh.getRow(head).createCell(12).setCellStyle(clientCellStyle);
			sh.getRow(head).createCell(13).setCellStyle(clientCellStyle);
			sh.getRow(head).getCell(10).setCellValue(cli.getId_cliente()+"  -  "+cli.getNombre());
			sh.getRow(head).getCell(11).setCellValue(0.0);
			sh.getRow(head).getCell(12).setCellValue(0.0);
			sh.getRow(head).getCell(13).setCellValue(0.0);


			for(Soporte sop : soportes){
				if(sop.getTipo_soporte().equals("Incidencia"))sh.getRow(head).getCell(11).setCellValue(sh.getRow(head).getCell(11).getNumericCellValue()+1);
				if(sop.getTipo_soporte().equals("Consulta"))sh.getRow(head).getCell(12).setCellValue(sh.getRow(head).getCell(12).getNumericCellValue()+1);
				sh.getRow(head).getCell(13).setCellValue(sh.getRow(head).getCell(13).getNumericCellValue()+1);				
			}
			
			
			sh.getRow(head).createCell(14).setCellStyle(clientCellStyle);
			sh.getRow(head).getCell(14).setCellFormula("INT("+sh.getRow(head).getCell(13).getNumericCellValue()*100+"/"+totalSoportes+")");
			head++;
			i++;
		}
		
		
		/*fila para mostrar la no existencia de cliente */
		sh.createRow(head).createCell(10).setCellStyle(clientCellStyle);
		sh.getRow(head).createCell(11).setCellStyle(footerCellStyle);
		sh.getRow(head).createCell(12).setCellStyle(footerCellStyle);
		sh.getRow(head).createCell(13).setCellStyle(footerCellStyle);
		sh.getRow(head).createCell(14).setCellStyle(footerCellStyle);
		sh.getRow(head).getCell(10).setCellValue("Ninguno");
		sh.getRow(head).getCell(11).setCellValue(0);
		sh.getRow(head).getCell(12).setCellValue(0);
		sh.getRow(head).getCell(13).setCellValue(0);
		
		switch (tipoFecha){
			case 1:
				soportes = sopDao.getAllSoportesByClientId("");
				break;
			case 2:
				dateDesde = Utils.dateConverter(fechaDesde);
				
				soportes = sopDao.getSoportesSinceDateByClientId("", dateDesde);
				break;
			case 3:
				dateHasta = Utils.dateConverter(fechaHasta);
				soportes = sopDao.getSoportesUntilDateByClientId("", dateHasta);
				break;
			case 4:
				dateDesde = Utils.dateConverter(fechaDesde);
				dateHasta = Utils.dateConverter(fechaHasta);
				soportes = sopDao.getSoportesBetweenDatesByClientId("", dateDesde, dateHasta);
				break;
			default:
				break;
		}
		for(Soporte sop : soportes){
			if(sop.getTipo_soporte().equals("Incidencia"))sh.getRow(head).getCell(11).setCellValue(sh.getRow(head).getCell(11).getNumericCellValue()+1);
			if(sop.getTipo_soporte().equals("Consulta"))sh.getRow(head).getCell(12).setCellValue(sh.getRow(head).getCell(12).getNumericCellValue()+1);
			sh.getRow(head).getCell(13).setCellValue(sh.getRow(head).getCell(13).getNumericCellValue()+1);
		}
		
		sh.getRow(head).getCell(14).setCellFormula("INT("+sh.getRow(head).getCell(13).getNumericCellValue()*100+"/"+totalSoportes+")");
		
		/*fin de fila para mostrar la no existencia de cliente*/
		
		head++;
		
		sh.createRow(head).createCell(10).setCellStyle(footerCellStyle);
		sh.getRow(head).createCell(11).setCellStyle(footerCellStyle);
		sh.getRow(head).createCell(12).setCellStyle(footerCellStyle);
		sh.getRow(head).createCell(13).setCellStyle(footerCellStyle);
		sh.getRow(head).createCell(14).setCellStyle(footerCellStyle);
		sh.getRow(head).getCell(10).setCellValue("Total");
		sh.getRow(head).getCell(11).setCellFormula("SUM(L"+(headClientes)+":L"+(head)+")");
		sh.getRow(head).getCell(12).setCellFormula("SUM(M"+(headClientes)+":M"+(head)+")");
		sh.getRow(head).getCell(13).setCellFormula("SUM(N"+(headClientes)+":N"+(head)+")");
		sh.getRow(head).getCell(14).setCellValue(1);
		
		
		Name rangeTipo = workbook.getName("tiposSop");
		Name rangeTotalTipos = workbook.getName("totalSopTip");
		rangeTipo.setRefersToFormula(sheetName+"!$L$"+(headClientes)+":$M$"+(headClientes));
		rangeTotalTipos.setRefersToFormula(sheetName+"!$L$"+(head+1)+":$M$"+(head+1));
		/*------------------------------fin tabla CLIENTES--------------------------------------------------------------*/
		
		workbook.write(resp.getOutputStream());
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void informeCliente(HttpServletRequest req, HttpServletResponse resp)throws Exception {
		PruebaDao pruDao =PruebaDao.getInstance();
		ClienteDao cliDao =ClienteDao.getInstance();
		
		
		String fechaHasta = req.getParameter("fechaHasta");
		String fechaDesde = req.getParameter("fechaDesde");
		
		int tipoFecha = 1;
		
		if (!fechaHasta.equals("")&&fechaHasta!=null){
			if(!fechaDesde.equals("")&&fechaDesde!=null){
				tipoFecha=4;
			}else{
				tipoFecha=3;
			}
		}else{
			if(!fechaDesde.equals("")&&fechaDesde!=null){
				tipoFecha=2;
			}
		}

		resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		resp.setHeader("Content-Disposition","attachment; filename=InformePruebasPorServicioSTE.xlsx");
		
		String link= "/datadocs/templatePruebasCliente.xlsx";
		InputStream inp = this.getServletContext().getResourceAsStream(link);
		Workbook workbook = new XSSFWorkbook(OPCPackage.open(inp));
		
		Sheet sh=workbook.getSheetAt(0);
		String sheetName = workbook.getSheetName(0);
		
		CellStyle clientCellStyle = workbook.createCellStyle();
		clientCellStyle.setBorderBottom((short)1);
		clientCellStyle.setBorderLeft((short)2);
		clientCellStyle.setBorderRight((short)2);
		
		
		CellStyle testCellStyle = workbook.createCellStyle();
		testCellStyle.setBorderRight((short)2);
		clientCellStyle.setBorderLeft((short)2);
		
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderRight((short)2);
		cellStyle.setBorderLeft((short)2);		
		cellStyle.setBorderTop((short)2);
		cellStyle.setBorderBottom((short)2);
		
		
		
		List<Cliente> clientes = cliDao.getAllClients();
		List<Prueba> pruebas = null;
		
		int i = 0;
		
		for(Cliente cli :clientes){
			switch (tipoFecha){
			case 1:
				pruebas = pruDao.getAllPruebasByClientId(Long.toString(cli.getKey().getId()));
				break;
			case 2:
				Date dateDesde = Utils.dateConverter(fechaDesde);
				//TODO
				//pruebas = pruDao.getPruebasSinceDateByClientId(Long.toString(cli.getKey().getId()), dateDesde);
				break;
			case 3:
				Date dateHasta = Utils.dateConverter(fechaHasta);
				//pruebas = pruDao.getPruebasUntilDateByClientId(Long.toString(cli.getKey().getId()), dateHasta);
				break;
			case 4:
				Date datDesde = Utils.dateConverter(fechaDesde);
				Date datHasta = Utils.dateConverter(fechaHasta);
				//pruebas = pruDao.getPruebasBetweenDatesByClientId(Long.toString(cli.getKey().getId()), datDesde, datHasta);
				break;
			default:
				break;
			}
			
			sh.createRow(7+i).createCell(1).setCellStyle(clientCellStyle);
			sh.getRow(7+i).getCell(1).setCellValue(cli.getNombre());
			sh.getRow(7+i).createCell(2).setCellStyle(testCellStyle);
			sh.getRow(7+i).getCell(2).setCellValue(0);
			sh.getRow(7+i).createCell(3).setCellStyle(testCellStyle);
			sh.getRow(7+i).getCell(3).setCellValue(0);
			sh.getRow(7+i).createCell(4).setCellStyle(cellStyle);
			//sh.getRow(7+i).getCell(4).setCellFormula("SUM(C"+(8+i)+":D"+(8+i)+")");
			sh.getRow(7+i).getCell(4).setCellValue(0);
			sh.getRow(7+i).createCell(5).setCellStyle(cellStyle);
			
			for(Prueba pru : pruebas){
				if(pru.getEntorno().equals("Integrado"))sh.getRow(7+i).getCell(2).setCellValue((sh.getRow(7+i).getCell(2).getNumericCellValue()+1));
				if(pru.getEntorno().equals("Produccion"))sh.getRow(7+i).getCell(3).setCellValue((sh.getRow(7+i).getCell(3).getNumericCellValue()+1));
				sh.getRow(7+i).getCell(4).setCellValue((sh.getRow(7+i).getCell(4).getNumericCellValue()+1));
			}
			
			i++;
		}
		/*i = clientes.size()*/
		i--;
		
		Name rangeIntegrado = workbook.getName("Integrado");
		Name rangeProduccion = workbook.getName("Produccion");
		Name rangeTotal = workbook.getName("Total");
		Name rangeCliente = workbook.getName("Cliente");

		rangeCliente.setRefersToFormula(sheetName + "!$B$"+8 + ":$B$"+(8+i));
		rangeIntegrado.setRefersToFormula(sheetName + "!$C$"+ 8 + ":$C$"+(8+i));
		rangeProduccion.setRefersToFormula(sheetName + "!$D$"+ 8 + ":$D$"+(8+i));
		rangeTotal.setRefersToFormula(sheetName + "!$E$"+ 8 + ":$E$"+(8+i));
		
		
		/*Fila de adesion del total*/
		sh.createRow(8+i).createCell(1).setCellStyle(clientCellStyle);
		sh.getRow(8+i).getCell(1).setCellValue("Total");
		sh.getRow(8+i).createCell(2).setCellStyle(cellStyle);
		sh.getRow(8+i).getCell(2).setCellFormula("SUM(C"+8+":C"+(8+i)+")");
		sh.getRow(8+i).createCell(3).setCellStyle(cellStyle);
		sh.getRow(8+i).getCell(3).setCellFormula("SUM(D"+8+":D"+(8+i)+")");
		sh.getRow(8+i).createCell(4).setCellStyle(cellStyle);
		sh.getRow(8+i).getCell(4).setCellFormula("SUM(C"+(9+i)+":D"+(9+i)+")");
		
		/*Adesion del valor de los pocentajes*/
		for(int j=0; j<=i;j++){
			//sh.getRow(7+j).getCell(5).setCellFormula("E"+(8+j)+"/E"+(9+i)+"*100");
			double parteEntera1 = (sh.getRow(7+j).getCell(4).getNumericCellValue());
			pruebas = pruDao.getAllPruebas();
			double parteEntera2 = pruebas.size();
			sh.getRow(7+j).getCell(5).setCellFormula("INT("+parteEntera1+"/"+parteEntera2+"*"+"100"+")");
		}
		
		/*Fila de adesion del promedio*/
		sh.createRow(9+i).createCell(1).setCellStyle(clientCellStyle);
		sh.getRow(9+i).getCell(1).setCellValue("Promedio");
		sh.getRow(9+i).createCell(2).setCellStyle(cellStyle);
		sh.getRow(9+i).getCell(2).setCellFormula("AVERAGE(C"+8+":C"+(8+i)+")");
		sh.getRow(9+i).createCell(3).setCellStyle(cellStyle);
		sh.getRow(9+i).getCell(3).setCellFormula("AVERAGE(D"+8+":D"+(8+i)+")");
		sh.getRow(9+i).createCell(4).setCellStyle(cellStyle);
		sh.getRow(9+i).getCell(4).setCellFormula("SUM(C"+(10+i)+":D"+(10+i)+")");

		/*Fila de adesion del maximo*/
		sh.createRow(10+i).createCell(1).setCellStyle(clientCellStyle);
		sh.getRow(10+i).getCell(1).setCellValue("Máximo");
		sh.getRow(10+i).createCell(2).setCellStyle(cellStyle);
		sh.getRow(10+i).getCell(2).setCellFormula("MAX(C"+8+":C"+(8+i)+")");
		sh.getRow(10+i).createCell(3).setCellStyle(cellStyle);
		sh.getRow(10+i).getCell(3).setCellFormula("MAX(D"+8+":D"+(8+i)+")");
		sh.getRow(10+i).createCell(4).setCellStyle(cellStyle);
		sh.getRow(10+i).getCell(4).setCellFormula("MAX(E"+8+":E"+(8+i)+")");

		/*Fila de adesion del minimo*/
		sh.createRow(11+i).createCell(1).setCellStyle(clientCellStyle);
		sh.getRow(11+i).getCell(1).setCellValue("Mínimo");
		sh.getRow(11+i).createCell(2).setCellStyle(cellStyle);
		sh.getRow(11+i).getCell(2).setCellFormula("MIN(C"+8+":C"+(8+i)+")");
		sh.getRow(11+i).createCell(3).setCellStyle(cellStyle);
		sh.getRow(11+i).getCell(3).setCellFormula("MIN(D"+8+":D"+(8+i)+")");
		sh.getRow(11+i).createCell(4).setCellStyle(cellStyle);
		sh.getRow(11+i).getCell(4).setCellFormula("MIN(E"+8+":E"+(8+i)+")");
		
		
		
		workbook.write(resp.getOutputStream());
		
	}
	
	
	private void informepordefecto(HttpServletRequest req, HttpServletResponse resp)throws Exception {	
		JSONObject json = new JSONObject();
		
		String fechaHasta = req.getParameter("fechaHasta");
		String fechaDesde = req.getParameter("fechaDesde");
		
		int tipoFecha = 1;
		
		if (!fechaHasta.equals("")&&fechaHasta!=null){
			if(!fechaDesde.equals("")&&fechaDesde!=null){
				tipoFecha=4;
			}else{
				tipoFecha=3;
			}
		}else{
			if(!fechaDesde.equals("")&&fechaDesde!=null){
				tipoFecha=2;
			}
		}

		resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		resp.setHeader("Content-Disposition","attachment; filename=InformePruebasPorServicioSTE.xlsx");
		
		String link= "/datadocs/templatePruebasCliente.xlsx";
		InputStream inp = this.getServletContext().getResourceAsStream(link);
		Workbook workbook = new XSSFWorkbook(OPCPackage.open(inp));
		
		Sheet sh=workbook.getSheetAt(0);
		String sheetName = workbook.getSheetName(0);
		
		/*ESTILO DE CELDA CON BORDES*/
		short width = 1;
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom(width);
		cellStyle.setBorderLeft(width);
		cellStyle.setBorderRight(width);
		cellStyle.setBorderTop(width);
		
		CellStyle clientCellStyle = workbook.createCellStyle();
		clientCellStyle.setBorderBottom(width);
		clientCellStyle.setBorderLeft(width);
		clientCellStyle.setBorderRight(width);
		clientCellStyle.setBorderTop((short)0);
		
		CellStyle footerCellStyle = workbook.createCellStyle();
		footerCellStyle.setBorderBottom(width);
		footerCellStyle.setBorderLeft(width);
		footerCellStyle.setBorderRight(width);
		footerCellStyle.setBorderTop((short)2);
		
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setBorderBottom((short)2);
		headerCellStyle.setBorderLeft(width);
		headerCellStyle.setBorderRight(width);
		headerCellStyle.setBorderTop(width);
		
		
		
		

	}
}
