package com.ste.servlets;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import com.ste.beans.Prueba;
import com.ste.dao.ClienteDao;
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

				
			
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		doGet(req,resp);
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

		
		
	
		
		/*
		
	resp.setContentType("application/pdf");
	resp.setHeader("Content-Disposition","inline; filename=informe.pdf");

	String link= "/datadocs/template.xlsx";
	InputStream inp = this.getServletContext().getResourceAsStream(link);
	Workbook workbook = new XSSFWorkbook(OPCPackage.open(inp));
	
	CreationHelper createHelper = workbook.getCreationHelper();
	Sheet sh=workbook.getSheetAt(0);
	String sheetName=sh.getSheetName();	
	
	CellStyle cellStyle = workbook.createCellStyle();
	cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("d/m/yyyy"));
	sh.getRow(7).getCell(1).setCellValue(23);
	
	

	
	workbook.write(resp.getOutputStream());*/
	/*
	InputStream test = this.getServletContext().getResourceAsStream(link);
	com.aspose.cells.Workbook salidP = new com.aspose.cells.Workbook(test);
	
	salidP.save(resp.getOutputStream(),SaveFormat.PDF);*/

	
	}
	
}
