package com.ste.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ste.beans.Cliente;
import com.ste.dao.ClienteDao;

public class GestionClienteAction extends Action{

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
				
		ClienteDao cDao = ClienteDao.getInstance();
		List<Cliente> clientes = cDao.getAllClients();
		
		req.setAttribute("clientes", clientes);
		
		return  mapping.findForward("ok");
	}
}