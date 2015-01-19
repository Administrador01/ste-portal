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
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.ste.beans.Prueba;
import com.ste.dao.PruebaDao;
import com.ste.utils.Utils;

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
	
	private void informepordefecto(HttpServletRequest req, HttpServletResponse resp)throws JSONException, IOException, ServletException {	
		JSONObject json = new JSONObject();
		

		try {
			
			resp.addHeader("Content-Disposition", "inline; filename=informe.pdf");
			resp.setContentType("application/pdf");
			
			
			Document document = new Document();
			PdfWriter.getInstance(document, resp.getOutputStream());
			document.open();
			document.add(new Paragraph(" hhola hollaa "));
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Exception in Excel", e);
		} finally {

		}
	}
	
}
