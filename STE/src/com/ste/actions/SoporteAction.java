package com.ste.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ste.beans.Cliente;
import com.ste.beans.Soporte;
import com.ste.dao.ClienteDao;
import com.ste.dao.SoporteDao;

public class SoporteAction extends Action{

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		
		SoporteDao sDao = SoporteDao.getInstance();
		List<Soporte> soportes = new ArrayList <Soporte>();
		
		String idCli = req.getParameter("idCli");
		
		if(idCli == null || idCli == ""){
			soportes= sDao.getAllSoportes();
		}else{
			soportes = sDao.getAllSoportesByClientId(idCli);
		}
		
		//Mandamos los clientes para generar la lista select desplegable
		ClienteDao cDao = ClienteDao.getInstance();
		List<Cliente> clientes = cDao.getAllClients();
		
		req.setAttribute("clientes", clientes);
		req.setAttribute("soportes", soportes);
		
		return  mapping.findForward("ok");
	}
}
