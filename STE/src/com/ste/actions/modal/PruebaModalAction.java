package com.ste.actions.modal;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ste.beans.Cliente;
import com.ste.beans.Estado;
import com.ste.beans.Implementacion;
import com.ste.beans.ProductoCanal;
import com.ste.beans.Prueba;
import com.ste.beans.Servicio;
import com.ste.beans.TipoServicio;
import com.ste.dao.ClienteDao;
import com.ste.dao.EstadoDao;
import com.ste.dao.ImplementacionDao;
import com.ste.dao.ProductoCanalDao;
import com.ste.dao.PruebaDao;
import com.ste.dao.ServicioDao;
import com.ste.dao.TipoServicioDao;

import org.apache.struts.action.Action;


public class PruebaModalAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {
			String str_id = req.getParameter("id");
			
			PruebaDao pDao = PruebaDao.getInstance();
			Prueba p = pDao.getPruebabyId(Long.parseLong(str_id));
			req.setAttribute("prueba", p);
			
			
			//Mandamos los clientes para generar la lista select desplegable
			ClienteDao cDao = ClienteDao.getInstance();
			List<Cliente> clientes = cDao.getAllClients();
			req.setAttribute("clientes", clientes);
			
			ImplementacionDao implemnDao = ImplementacionDao.getInstance();
			List<Implementacion> implementaciones = implemnDao.getImplementacionByClientId(pDao.getClientByTestId(p.getKey().getId()).getKey().getId());
			req.setAttribute("implementaciones", implementaciones);
			
			ProductoCanalDao prodDao = ProductoCanalDao.getInstance();
			List<ProductoCanal> productos = prodDao.getAllProductosFor("pruebas");
			
			req.setAttribute("productos", productos);
			
			ServicioDao servDao = ServicioDao.getInstance();
			List<Servicio> servicios = servDao.getAllServicios();
			req.setAttribute("tiposervicios", servicios);
			EstadoDao estDao = EstadoDao.getInstance();
			List<Estado> estados = estDao.getAllEstados();
			req.setAttribute("estados", estados);
			
			}catch(Exception e){
				
			}
		
		return mapping.findForward("ok");

	}
}
