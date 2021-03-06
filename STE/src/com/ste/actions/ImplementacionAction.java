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
		
		String clienteFilter = req.getParameter("cliente");
		String paisFilter = req.getParameter("pais");
		String fechaDiaFilter = req.getParameter("fechadia");
		String fechaMesFilter = req.getParameter("fechames");
		String fechaAnioFilter = req.getParameter("fechaanio");
		String productoFilter = req.getParameter("producto");
		String normalizadorFilter = req.getParameter("normalizator");
		String servicioFilter = req.getParameter("servicio");
		String estadoFilter = req.getParameter("estado");
				
		String page = req.getParameter("page");
		int pageint = Utils.stringToInt(page);	
		int numpages = 0;
		boolean isNumResultsCalculated = false;
		
		ImplementacionDao impDao = ImplementacionDao.getInstance();
		List<Implementacion> implementaciones = new ArrayList<Implementacion>();
				
		if(Utils.isFechaFilterEmpty(fechaDiaFilter, fechaMesFilter, fechaAnioFilter) &&
			Utils.esNuloVacio(clienteFilter) &&
			Utils.esNuloVacio(paisFilter) &&		
			Utils.esNuloVacio(productoFilter) &&
			Utils.esNuloVacio(servicioFilter) &&
			Utils.esNuloVacio(normalizadorFilter) &&
			Utils.esNuloVacio(estadoFilter)) {
			
			// Sin filtros
			implementaciones = impDao.getAllImplementacionesPagin(pageint);
			CounterDao counterDao = CounterDao.getInstance();
			Counter count = counterDao.getCounterByName("implementacion");
			numpages = Utils.calcNumPages(count.getValue(), PruebaDao.DATA_SIZE);	
			isNumResultsCalculated = true;
			req.setAttribute("numpages", numpages);		
		}
		else {
			// Con filtros
			implementaciones = impDao.getImplementacionesByAllParam(fechaDiaFilter, fechaMesFilter, fechaAnioFilter, clienteFilter, paisFilter, productoFilter, servicioFilter, normalizadorFilter, estadoFilter, pageint);
			int numPagesItemIndex = implementaciones.size()-1;
			int numResultsCalculated = Integer.parseInt(implementaciones.get(numPagesItemIndex).getDetalle());
			if(numResultsCalculated > 0) {
				isNumResultsCalculated = true;
			}
			implementaciones.remove(numPagesItemIndex);
			numpages = Utils.calcNumPages(numResultsCalculated, ImplementacionDao.DATA_SIZE);
			
			req.setAttribute("numpages", numpages);
			req.setAttribute("fechadia", fechaDiaFilter);
			req.setAttribute("fechames", fechaMesFilter);
			req.setAttribute("fechaanio", fechaAnioFilter);
			req.setAttribute("cliente", clienteFilter);
			req.setAttribute("pais", paisFilter);
			req.setAttribute("producto", productoFilter);
			req.setAttribute("normalizator", normalizadorFilter);
			req.setAttribute("servicio", servicioFilter);
			req.setAttribute("estado", estadoFilter);
		}
		boolean lastpage = Utils.isLastPage(pageint, numpages, isNumResultsCalculated, implementaciones.size(), ImplementacionDao.DATA_SIZE);
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