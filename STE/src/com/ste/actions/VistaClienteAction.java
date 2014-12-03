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
import com.ste.beans.Prueba;
import com.ste.beans.Soporte;
import com.ste.dao.ClienteDao;
import com.ste.dao.PruebaDao;
import com.ste.dao.SoporteDao;


public class VistaClienteAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {
			String str_id = req.getParameter("id");
		
			//Mandamos los clientes para generar la lista select desplegable
			ClienteDao cDao = ClienteDao.getInstance();
			Cliente c = cDao.getClientebyId(Long.parseLong(str_id));
			
			SoporteDao sDao = SoporteDao.getInstance();
			boolean existeS= sDao.existSoporteByClientId(str_id);
			
			PruebaDao pDao = PruebaDao.getInstance();
			boolean existeP = pDao.existPruebaByClientId(str_id);
			
			req.setAttribute("cliente",c);
			req.setAttribute("existeP",existeP);
			req.setAttribute("existeS",existeS);
			
			}catch(Exception e){
				
			}
		
		return mapping.findForward("ok");

	}
	
}



