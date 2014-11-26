package com.ste.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ste.beans.Log;
import com.ste.dao.LogsDao;
import com.ste.utils.Utils;


public class AuditoriaAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp){
		
		String accion = req.getParameter("p");
		
		LogsDao lDao = LogsDao.getInstance();		
		List<Log> logs = new ArrayList<Log>();
		
		Calendar fecha = Calendar.getInstance();
		fecha.add(Calendar.DATE, 0);
		String hoy = Utils.CalendarConverterToStr(fecha);
		fecha.add(Calendar.DATE, -1);
		String ayer = Utils.CalendarConverterToStr(fecha);
		
		
		
		if("lastweek".equals(accion)){
			logs = lDao.getLogsByLastWeek();
		}else if("lastmonth".equals(accion)){
			logs = lDao.getLogsByLastMonth();
		}else if("lastthreemonths".equals(accion)){
			logs = lDao.getLogsByLast3Months();
		}else{
			logs = lDao.getLogsByLastWeek();

		}
		
		req.setAttribute("accion", accion);
		req.setAttribute("logs", logs);
		req.setAttribute("hoy", hoy);
		req.setAttribute("ayer", ayer);
		
		return  mapping.findForward("ok");
		

		
	}
}
