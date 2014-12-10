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
import com.ste.beans.Prueba;
import com.ste.dao.ClienteDao;
import com.ste.dao.PruebaDao;

public class PruebasAction extends Action{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws Exception{

		//Mandamos los clientes para generar la lista select desplegable
		ClienteDao cDao = ClienteDao.getInstance();
		List<Cliente> clientes = cDao.getAllClients();

		
		
		PruebaDao pDao = PruebaDao.getInstance();
		List<Prueba> pruebas = new ArrayList <Prueba>();
		
		String idCli = req.getParameter("idCli");

		if(idCli == null || idCli == ""){
			pruebas = pDao.getAllPruebas();
		}else{
			pruebas = pDao.getAllPruebasByClientId(idCli);
		}
		
		req.setAttribute("clientes", clientes);	
		req.setAttribute("pruebas", pruebas);
		
		return  mapping.findForward("ok");
	}
}
