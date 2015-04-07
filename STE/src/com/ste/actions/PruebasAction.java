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
		
			pruebas.addAll(pDao.getAllPruebas());
			pruebas.addAll(pDao.getAllDelPruebas());
		}else{
			pruebas = pDao.getAllPruebasByClientId(idCli);
		}
		
		req.setAttribute("clientes", clientes);	
		req.setAttribute("pruebas", pruebas);
		
		ProductoCanalDao prodDao = ProductoCanalDao.getInstance();
		List<ProductoCanal> productos = prodDao.getAllProductosFor("pruebas");
		
		req.setAttribute("productos", productos);
		
		EstadoDao estDao = EstadoDao.getInstance();
		List<Estado> estados = estDao.getAllEstados();
		req.setAttribute("estados", estados);
		
		req.setAttribute("productos", productos);
		
		ServicioDao servDao = ServicioDao.getInstance();
		List<Servicio> servicios = servDao.getAllServicios();
		req.setAttribute("tiposervicios", servicios);
	
		
		return  mapping.findForward("ok");
	}
}
