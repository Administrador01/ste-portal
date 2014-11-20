package com.ste.actions.modal;

import java.io.IOException;
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

public class SoporteModalAction extends Action {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {
			
			String str_id = req.getParameter("id");
			SoporteDao sDao = SoporteDao.getInstance();
			Soporte s = sDao.getSoportebyId(Long.parseLong(str_id));
			
			req.setAttribute("soporte", s);
			
			//Mandamos los clientes para generar la lista select desplegable
			ClienteDao cDao = ClienteDao.getInstance();
			List<Cliente> clientes = cDao.getAllClients();
			
			req.setAttribute("clientes", clientes);
			
		}catch (Exception e){
			
		}
		
		return mapping.findForward("ok");
	}
}
