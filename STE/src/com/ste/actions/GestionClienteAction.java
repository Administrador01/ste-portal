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
import com.ste.dao.ClienteDao;

public class GestionClienteAction extends Action{

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
				

		ClienteDao cDao = ClienteDao.getInstance();
		
		List<Cliente> clientes = new ArrayList<Cliente>();;
		
		String idCli = req.getParameter("idCli");
		
		if(idCli == null || idCli == ""){
			clientes = cDao.getAllClients();
		}else{
			clientes.add(cDao.getClientebyId(Long.valueOf(idCli)));
		}
		
		req.setAttribute("clientes", clientes);
		
		return  mapping.findForward("ok");
	}
}
