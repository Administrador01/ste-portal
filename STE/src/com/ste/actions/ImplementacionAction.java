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

import com.ste.beans.Cliente;
import com.ste.beans.Log;
import com.ste.beans.Servicio;
import com.ste.dao.ClienteDao;
import com.ste.dao.LogsDao;
import com.ste.dao.ServicioDao;
import com.ste.utils.Utils;


public class ImplementacionAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp){
		
		
		ClienteDao cDao = ClienteDao.getInstance();
		List<Cliente> clientes = cDao.getAllClientsAlphabet();

		req.setAttribute("clientes", clientes);

		ServicioDao sDao = ServicioDao.getInstance();
		List<Servicio> servicios = sDao.getAllServicios();

		req.setAttribute("servicios", servicios);


		return  mapping.findForward("ok");
		

		
	}
}