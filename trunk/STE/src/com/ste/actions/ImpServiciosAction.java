package com.ste.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ste.beans.Cliente;
import com.ste.beans.Implementacion;
import com.ste.beans.Pais;
import com.ste.beans.ProductoCanal;
import com.ste.beans.Servicio;
import com.ste.dao.ClienteDao;
import com.ste.dao.ImplementacionDao;
import com.ste.dao.PaisDao;
import com.ste.dao.ProductoCanalDao;
import com.ste.dao.ServicioDao;

public class ImpServiciosAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp){

		ImplementacionDao impDao = ImplementacionDao.getInstance();
		List<Implementacion> implementaciones = impDao.getAllImplementaciones();
		req.setAttribute("implementaciones",implementaciones);
		
		ClienteDao cDao = ClienteDao.getInstance();
		List<Cliente> clientes = cDao.getAllClientsAlphabet();
		req.setAttribute("clientes", clientes);
		
		clientes = cDao.getAllClientsEvenDeleted();
		req.setAttribute("clientesAll", clientes);
		
		ServicioDao sDao = ServicioDao.getInstance();
		List<Servicio> servicios = sDao.getAllServicios();

		req.setAttribute("servicios", servicios);
		return  mapping.findForward("ok");		
	}
}
