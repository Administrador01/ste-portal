package com.ste.actions;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ste.beans.Cliente;
import com.ste.dao.ClienteDao;

public class ClientsAction extends Action {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		ClienteDao cDao = ClienteDao.getInstance();
		List<Cliente> clientes = cDao.getAllClientsAlphabet();
		
		
		
		Set<String> letras = new HashSet<String>();
		
		for (Cliente c:clientes){
			letras.add(c.getNombre().substring(0,1).toUpperCase());
		}

		String[] alphabet = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","Ñ","O","P","Q","R","S","T","U","V","W","X","Y","Z"}; 
		
		
		req.setAttribute("clientes", clientes);
		req.setAttribute("letras", letras.toString());
		req.setAttribute("alphabet", alphabet);
		
		
		return mapping.findForward("ok");
	}
}
