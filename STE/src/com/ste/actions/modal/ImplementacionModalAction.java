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
import com.ste.beans.EstadoImplementacion;
import com.ste.beans.Implementacion;
import com.ste.beans.Pais;
import com.ste.beans.ProductoCanal;
import com.ste.beans.Servicio;
import com.ste.dao.ClienteDao;
import com.ste.dao.EstadoImplementacionDao;
import com.ste.dao.ImplementacionDao;
import com.ste.dao.PaisDao;
import com.ste.dao.ProductoCanalDao;
import com.ste.dao.ServicioDao;

public class ImplementacionModalAction extends Action{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String str_id = req.getParameter("id");
		ImplementacionDao impDao = ImplementacionDao.getInstance();
		Implementacion implementacion = impDao.getImplementacionById(Long.parseLong(str_id));
		req.setAttribute("implementacion", implementacion);
		
		long idCli = implementacion.getCliente_id();
		long idServ = implementacion.getServicio_id();
		
		ClienteDao cDao = ClienteDao.getInstance();
		ServicioDao sDao = ServicioDao.getInstance();
		
		List<Cliente> clientes = cDao.getAllClients();
		List<Servicio> servicios = sDao.getAllServicios();
		
		req.setAttribute("clientes",clientes);
		req.setAttribute("servicios",servicios);
		
		Cliente c = cDao.getClientebyId(idCli);
		Servicio s = sDao.getImplementacionById(idServ);
		
		req.setAttribute("cliente", c);
		req.setAttribute("servicio", s);
		
		PaisDao paisDao = PaisDao.getInstance();
		List<Pais> paises = paisDao.getAllPaises();
		
		req.setAttribute("paises", paises);
		
		ProductoCanalDao prodDao = ProductoCanalDao.getInstance();
		List<ProductoCanal> productos = prodDao.getAllProductosFor("implementaciones");
		
		req.setAttribute("productos", productos);
		
		EstadoImplementacionDao estadoImpDao = EstadoImplementacionDao.getInstance();
		List<EstadoImplementacion> estadosimp = estadoImpDao.getAllEstadoImplementacions();
		
		req.setAttribute("estadosimp", estadosimp);
		
		return mapping.findForward("ok");
	}

}
