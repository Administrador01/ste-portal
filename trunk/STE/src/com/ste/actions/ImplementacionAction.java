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
import com.ste.beans.EstadoImplementacion;
import com.ste.beans.Implementacion;
import com.ste.beans.Pais;
import com.ste.beans.ProductoCanal;
import com.ste.beans.Servicio;
import com.ste.counters.Counter;
import com.ste.dao.ClienteDao;
import com.ste.dao.CounterDao;
import com.ste.dao.EstadoImplementacionDao;
import com.ste.dao.ImplementacionDao;
import com.ste.dao.PaisDao;
import com.ste.dao.ProductoCanalDao;
import com.ste.dao.PruebaDao;
import com.ste.dao.ServicioDao;
import com.ste.utils.Utils;


public class ImplementacionAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp){
		
		
		ClienteDao cDao = ClienteDao.getInstance();
		List<Cliente> clientes = cDao.getAllClientsAlphabet();
		
		req.setAttribute("clientes", clientes);
		
		clientes = cDao.getAllClientsEvenDeleted();
		req.setAttribute("clientesAll", clientes);
		
		ServicioDao sDao = ServicioDao.getInstance();
		List<Servicio> servicios = sDao.getAllServicios();

		req.setAttribute("servicios", servicios);
		/*
		ImplementacionDao impDao = ImplementacionDao.getInstance();
		List<Implementacion> implementaciones = new ArrayList<Implementacion>();
		implementaciones.addAll(impDao.getAllImplementaciones());
		implementaciones.addAll(impDao.getAllDelImplementaciones());
		req.setAttribute("implementaciones",implementaciones);
		 */
		
		String fechaFilter = req.getParameter("fecha-filter");
		String page = req.getParameter("page");
		int pageint = Utils.stringToInt(page);	
		
		ImplementacionDao impDao = ImplementacionDao.getInstance();
		List<Implementacion> implementaciones = new ArrayList<Implementacion>();
				
		if(fechaFilter!=null){
			
		}else{
			implementaciones = impDao.getAllImplementacionesPagin(pageint);
			CounterDao counterDao = CounterDao.getInstance();
			Counter count = counterDao.getCounterByName("implementacion");
			int numpages = (count.getValue()/PruebaDao.DATA_SIZE) + 1;			
			req.setAttribute("numpages", numpages);
		}
		boolean lastpage = (implementaciones.size() < ImplementacionDao.DATA_SIZE) ? true : false;
		req.setAttribute("lastpage", lastpage);
		req.setAttribute("page", pageint);
		
		req.setAttribute("implementaciones",implementaciones);
		
		PaisDao paisDao = PaisDao.getInstance();
		List<Pais> paises = paisDao.getAllPaises();
		
		req.setAttribute("paises", paises);
		
		ProductoCanalDao prodDao = ProductoCanalDao.getInstance();
		List<ProductoCanal> productos = prodDao.getAllProductosFor("implementaciones");
		
		req.setAttribute("productos", productos);
		
		EstadoImplementacionDao estadoImpDao = EstadoImplementacionDao.getInstance();
		List<EstadoImplementacion> estadosimp = estadoImpDao.getAllEstadoImplementacions();
		
		req.setAttribute("estadosimp", estadosimp);
		
		return  mapping.findForward("ok");		
	}
}