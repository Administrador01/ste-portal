package com.ste.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.ste.beans.Log;
import com.ste.dao.LogsDao;

public class LogsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4580615697049545746L;

	/**
	 * 
	 */

	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		String accion = req.getParameter("accion");
	

		try {

			if (accion.equals("xls")) {
				generateXLS(req, resp);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		doPost(req, resp);
	}
	
	private void generateXLS(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		OutputStream out = null;
		try {
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition",
					"attachment; filename=AuditoriaSTE.xls");

			WritableWorkbook w = Workbook
					.createWorkbook(resp.getOutputStream());

			LogsDao lDao = LogsDao.getInstance();
			List<Log> logs = lDao.getAllLogs();
			
			WritableSheet s = w.createSheet("Auditoría", 0);

			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.WHITE);
			
			

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
			cellFormat.setBackground(Colour.BLUE);
			cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
			cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			
			
			// WritableCellFormat numberFormat=new WritableCellFormat(new  jxl.write.NumberFormat("#.##"));
			// numberFormat.setShrinkToFit(true);


			s.setColumnView(0, 20);
			s.setColumnView(1, 30);
			s.setColumnView(2, 30);
			s.setColumnView(3, 30);
			s.setColumnView(4, 30);
			

			s.addCell(new Label(0, 0, "FECHA", cellFormat));
			s.addCell(new Label(1, 0, "USUARIO", cellFormat));
			s.addCell(new Label(2, 0, "ACCION", cellFormat));
			s.addCell(new Label(3, 0, "ENTIDAD", cellFormat));
			s.addCell(new Label(4, 0, "NOMBRE", cellFormat));
			
			
			int aux = 1;

			for (Log l : logs) {
				
				s.addCell(new Label(0, aux, l.getFecha_str()));
				s.addCell(new Label(1, aux, l.getUsuario()));
				s.addCell(new Label(2, aux, l.getAccion()));
				s.addCell(new Label(3, aux, l.getEntidad()));
				s.addCell(new Label(4, aux, l.getNombre_entidad()));
			
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

	
}
