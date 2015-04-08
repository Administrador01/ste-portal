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
import com.ste.beans.ProductoCanal;
import com.ste.beans.Prueba;
import com.ste.beans.Servicio;
import com.ste.counters.Counter;
import com.ste.dao.ClienteDao;
import com.ste.dao.CounterDao;
import com.ste.dao.EstadoDao;
import com.ste.dao.ProductoCanalDao;
import com.ste.dao.PruebaDao;
import com.ste.dao.ServicioDao;
import com.ste.utils.Utils;

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
		
		String page = req.getParameter("page");
		int pageint = Utils.stringToInt(page);		
		
		if(idCli == null || idCli == ""){
			pruebas = pDao.getPruebasPaged(pageint);		
			
			CounterDao counterDao = CounterDao.getInstance();
			Counter count = counterDao.getCounterByName("prueba");
			int numpages = (count.getValue()/PruebaDao.DATA_SIZE) + 1;			
			req.setAttribute("numpages", numpages);			
		} else {
			pruebas = pDao.getAllPruebasByClientIdPaged(idCli, pageint);
			
			int numpages = 0;			
			req.setAttribute("numpages", numpages);
		}

		boolean lastpage = (pruebas.size() < PruebaDao.DATA_SIZE) ? true : false;
		
		req.setAttribute("lastpage", lastpage);
		req.setAttribute("page", pageint);
		
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
