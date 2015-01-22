package com.ste.servlets;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import com.ste.beans.Prueba;
import com.ste.dao.ClienteDao;
import com.ste.dao.EstadoDao;
import com.ste.dao.PruebaDao;
import com.ste.utils.Utils;
import com.aspose.cells.Workbook.*;
import com.aspose.cells.SaveFormat;


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
			if(accion.equals("pruebasServ"))informePruebasServ(req,resp);
			if(accion.equals("cliente"))informeCliente(req,resp);
			
			
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
		
		
		/*------------------------------------------TABLA 1---------------------------------------------*/
		int num = 0;
		for(Estado estado: estados){
			//Creacion de las celdas con bordes de manera dinamica
			sh.createRow(8+num).createCell(5).setCellStyle(cellStyle);
			sh.getRow(8+num).createCell(6).setCellStyle(cellStyle);
			sh.getRow(8+num).createCell(7).setCellStyle(cellStyle);
			sh.getRow(8+num).createCell(8).setCellStyle(cellStyle);
			sh.getRow(8+num).getCell(5).setCellValue(estado.getName());
			sh.getRow(8+num).getCell(6).setCellValue(0.0);
			sh.getRow(8+num).getCell(7).setCellValue(0.0);
			sh.getRow(8+num).getCell(8).setCellValue(0.0);
			num++;
		}
		for(Prueba pru : pruebas){
			for(int i =0;i<estados.size();i++){
				
				
				if(pru.getEntorno().equals("Produccion")){
					
					if(pru.getEstado().equals(estados.get(i).getName()))sh.getRow(i+8).getCell(7).setCellValue(sh.getRow(i+8).getCell(7).getNumericCellValue()+1);
					
				}else{
					if(pru.getEntorno().equals("Preproduccion")){
						
						if(pru.getEstado().equals(estados.get(i).getName()))sh.getRow(i+8).getCell(6).setCellValue(sh.getRow(i+8).getCell(6).getNumericCellValue()+1);
						
					}
				}
				if(pru.getEstado().equals(estados.get(i).getName()))sh.getRow(i+8).getCell(8).setCellValue(sh.getRow(i+8).getCell(8).getNumericCellValue()+1);
			}
		}
		
		sh.createRow(estados.size()+8).createCell(5).setCellStyle(cellStyle);
		sh.getRow(estados.size()+8).createCell(6).setCellStyle(cellStyle);
		sh.getRow(estados.size()+8).createCell(7).setCellStyle(cellStyle);
		sh.getRow(estados.size()+8).createCell(8).setCellStyle(cellStyle);
		sh.getRow(estados.size()+8).getCell(5).setCellValue("Total");
		sh.getRow(estados.size()+8).getCell(6).setCellFormula("SUM(G"+(8+1)+":G"+(8+estados.size())+")");
		sh.getRow(estados.size()+8).getCell(7).setCellFormula("SUM(H"+(8+1)+":H"+(8+estados.size())+")");
		sh.getRow(estados.size()+8).getCell(8).setCellFormula("SUM(I"+(8+1)+":I"+(8+estados.size())+")");
		/*------------------------------FIN DE TABLA 1-------------------------------------------------------*/
		
		/*Insertamos el rango de nombres para la grafica*/
		
		Name rangeTotal = workbook.getName("Total");
		Name rangeEstados = workbook.getName("Estados");
		
		String sheetName = workbook.getSheetName(0);
		
		rangeTotal.setRefersToFormula(sheetName+"!$I$"+9+":$I$"+(estados.size()+8));
		rangeEstados.setRefersToFormula(sheetName+"!$F$"+9+":$F$"+(estados.size()+8));
		
		
		ClienteDao clientDao = ClienteDao.getInstance();
		List<Cliente> clientes = clientDao.getAllClientsEvenDeleted();
		/*------------------------------TABLA 2--------------------------------------------------------------*/
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
			sh.createRow(25+i).createCell(1).setCellStyle(cellStyle);
			sh.getRow(25+i).createCell(2).setCellStyle(cellStyle);
			sh.getRow(25+i).createCell(3).setCellStyle(cellStyle);
			sh.getRow(25+i).createCell(4).setCellStyle(cellStyle);
			sh.getRow(25+i).getCell(1).setCellValue(cli.getId_cliente());
			sh.getRow(25+i).getCell(2).setCellValue(0.0);
			sh.getRow(25+i).getCell(3).setCellValue(0.0);
			sh.getRow(25+i).getCell(4).setCellValue(0.0);

			for(Prueba pru : pruebas){
				if(pru.getEntorno().equals("Preproduccion"))sh.getRow(25+i).getCell(2).setCellValue(sh.getRow(25+i).getCell(2).getNumericCellValue()+1);
				if(pru.getEntorno().equals("Produccion"))sh.getRow(25+i).getCell(3).setCellValue(sh.getRow(25+i).getCell(3).getNumericCellValue()+1);
				sh.getRow(25+i).getCell(4).setCellValue(sh.getRow(25+i).getCell(4).getNumericCellValue()+1);
			}
			i++;
		}
		/*------------------------------FIN TABLA 2----------------------------------------------------------*/
		
		//cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(""));
		
		
		
		workbook.write(resp.getOutputStream());
	}
	
	private void informePruebasServ(HttpServletRequest req, HttpServletResponse resp)throws Exception {
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
				//pruebas = pruDao.getPruebasBetweenDates(fech,haHasta);
				tipoFecha=4;
			}else{
				Date dateHasta = Utils.dateConverter(fechaHasta);
				//pruebas = pruDao.getPruebasUntilDate(dateHasta);
				tipoFecha=3;
			}
		}else{
			if(!fechaDesde.equals("")&&fechaDesde!=null){
				Date dateDesde = Utils.dateConverter(fechaDesde);
				//pruebas = pruDao.getPruebasSinceDate(dateDesde);
				tipoFecha=2;
			}
		}
		
		resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		resp.setHeader("Content-Disposition","attachment; filename=InformePruebasPorServicioSTE.xlsx");
		
		String link= "/datadocs/templatePruebasServ.xlsx";
		InputStream inp = this.getServletContext().getResourceAsStream(link);
		Workbook workbook = new XSSFWorkbook(OPCPackage.open(inp));
		
		//CreationHelper createHelper = workbook.getCreationHelper();
		Sheet sh=workbook.getSheetAt(0);
		
		Name rangeServicios = workbook.getName("Servicios");
		Name rangeValores = workbook.getName("Valores");
		
		String sheetName = workbook.getSheetName(0);
		
		String reference = sheetName + "!$B$"+3 + ":$B$"+5;
		
		rangeServicios.setRefersToFormula(reference);
		
		reference = sheetName + "!$C$"+3 + ":$C$"+5;
		
		rangeValores.setRefersToFormula(reference);
		
		sh.createRow(4).createCell(1).setCellValue(789987);
		sh.getRow(4).createCell(2).setCellValue("roberto");
		
		workbook.write(resp.getOutputStream());
		
	}
	
	private void informeCliente(HttpServletRequest req, HttpServletResponse resp)throws Exception {
		
		
	}
	
	
	private void informepordefecto(HttpServletRequest req, HttpServletResponse resp)throws Exception {	
		JSONObject json = new JSONObject();
		

//		
//		OutputStream out = null;
//		try {
//			resp.setContentType("application/vnd.ms-excel");
//			resp.setHeader("Content-Disposition",
//					"attachment; filename=GestionPruebasSTE.xls");
//
//			
//			InputStream stream = this.getServletContext().getResourceAsStream("/datadocs/");
//			
//			
//			
//			WritableWorkbook w = Workbook
//					.createWorkbook(resp.getOutputStream());
//
//			PruebaDao pDao = PruebaDao.getInstance();
//			
//			
//			ClienteDao cliDao = ClienteDao.getInstance();
//			List<Cliente> clientes = cliDao.getAllClients();
//
//			WritableSheet s = w.createSheet("Informe de pruebas", 0);
//			
//			
//			
//			
//			
//			
//			
//			
//			
//			ByteArrayOutputStream er = new ByteArrayOutputStream();
//			
//			Grafico1 frame = new
//			
//			WritableImage image = new WritableImage(5, 5, 100, 200, er.toByteArray());
//			s.addImage(image);
//			
//			
//			
//			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
//			cellFont.setColour(Colour.BLACK);
//
//			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
//			cellFormat.setBackground(Colour.WHITE);
//			cellFormat.setBorder(Border.ALL, BorderLineStyle.THICK);
//			cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
//			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
//
//			s.setColumnView(0, 40);
//			s.setColumnView(1, 25);
//			s.setColumnView(2, 25);
//			s.setColumnView(3, 25);
//
//
//			s.addCell(new Label(0, 0,""));
//			s.addCell(new Label(1, 0, "PRE PRODUCCIÓN", cellFormat));
//			s.addCell(new Label(2, 0, "PRODUCCIÓN", cellFormat));
//			s.addCell(new Label(3, 0, "TOTAL PRUEBAS P", cellFormat));
//
//			
//			int aux = 1;
//
//			for ( Cliente cliente : clientes) {
//				
//				
//				s.addCell(new Label(0, aux, cliente.getNombre(), cellFormat));
//				List<Prueba> pruebas = pDao.getAllPruebasByClientId(Long.toString(cliente.getKey().getId()));
//				int preprod = 0;
//				int prod = 0;
//				for (Prueba prueba :pruebas){
//					if(prueba.getEntorno().equals("Produccion"))prod++;
//					if(prueba.getEntorno().equals("Preproduccion"))preprod++;
//				}
//				s.addCell(new Label(1, aux, String.valueOf(preprod), cellFormat));
//				s.addCell(new Label(2, aux, String.valueOf(prod), cellFormat));
//				s.addCell(new Label(3, aux, String.valueOf(pruebas.size()), cellFormat));
//
//
//
//
//				aux++;
//			}
//
//			w.write();
//			w.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new ServletException("Exception in Excel", e);
//		} finally {
//			if (out != null)
//				out.close();
//		}
//
//	}
		
	
/*
	resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	resp.setHeader("Content-Disposition","attachment; filename=GestionPruebasSTE.xlsx");
	String link= "/datadocs/template.xlsx";
	InputStream inp = this.getServletContext().getResourceAsStream(link);
	Workbook workbook = new XSSFWorkbook(OPCPackage.open(inp));
	
	CreationHelper createHelper = workbook.getCreationHelper();
	Sheet sh=workbook.getSheetAt(0);
	String sheetName=sh.getSheetName();	
	
	CellStyle cellStyle = workbook.createCellStyle();
	cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yyyy"));
	sh.getRow(7).getCell(1).setCellValue(23);
	
	workbook.write(resp.getOutputStream());

		
		*/
	
 
		
	

/*Metodos de conversion excela pdf atualmente no validos utilizan java.awt.* por lo que appengine lo prohibe*/
	
//	resp.setContentType("application/pdf");
//	resp.setHeader("Content-Disposition","inline; filename=informe.pdf");
//
//	String link= "/datadocs/ex.html";
//	InputStream test = this.getServletContext().getResourceAsStream(link);
//	com.aspose.cells.Workbook salidP = new com.aspose.cells.Workbook(test);
//	salidP.save(resp.getOutputStream(),SaveFormat.PDF);
	
//	resp.setContentType("application/pdf");
//	resp.setHeader("Content-Disposition","inline; filename=informe.pdf");
//	
//	
//	OfficeFile f = new OfficeFile(req.getInputStream(),"localhost","8100",true);
//	f.convert(resp.getOutputStream(),"pdf");
		

//	File inputFile;
//	File outputFile;
//	DocumentFormat outputFormat;
//	String inputExtension = FilenameUtils.getExtension(inputFile.getName());
//	DocumentFormat inputFormat = formatRegistry.getFormatByExtension(inputExtension);
//	StandardConversionTask conversionTask = new StandardConversionTask(inputFile, outputFile, outputFormat);
//	conversionTask.setDefaultLoadProperties(defaultLoadProperties);
//	conversionTask.setInputFormat(inputFormat);
//	officeManager.execute(conversionTask);
	

	
	
/*------------------------------------------------------------------------------*/
	
	}
	
}
