package com.ste.actions.modal;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ste.beans.Cliente;
import com.ste.dao.ClienteDao;

public class ClienteModalAction extends Action{

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {
			String str_id = req.getParameter("id");
			
			ClienteDao cDao = ClienteDao.getInstance();
			Cliente c = cDao.getClientebyId(Long.parseLong(str_id));
			
			req.setAttribute("cliente", c);
					
		
			
			}catch(Exception e){
				
			}
		
		return mapping.findForward("ok");

	}
}
