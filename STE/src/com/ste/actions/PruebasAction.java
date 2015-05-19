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
	
	private static final String TODOS = "TODOS";
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws Exception{

		//Mandamos los clientes para generar la lista select desplegable
		ClienteDao cDao = ClienteDao.getInstance();
		
		List<Cliente> clientes = cDao.getAllClients();
		
		PruebaDao pDao = PruebaDao.getInstance();
		List<Prueba> pruebas = new ArrayList <Prueba>();
		
		String idCli = req.getParameter("idCli");
		String fechaDiaFilter = req.getParameter("fechadia");
		String fechaMesFilter = req.getParameter("fechames");
		String fechaAnioFilter = req.getParameter("fechaanio");
		String clienteFilter = req.getParameter("cliente-filter");
		String servicioFilter = req.getParameter("servicio-filter");
		String estadoFilter = req.getParameter("estado-filter");
		String productoFilter = req.getParameter("producto-filter");
		String entornoFilter = req.getParameter("entorno-filter");
		String desdeFilter = req.getParameter("desde-filter");
		String hastaFilter = req.getParameter("hasta-filter");
		String premium = req.getParameter("premium");
		
		String page = req.getParameter("page");
		int pageint = Utils.stringToInt(page);	
		int numpages = 0;
		boolean isNumResultsCalculated = false;
		
		if(!Utils.esNuloVacio(idCli)) {
			// por id cliente
			pruebas = pDao.getAllPruebasByClientIdPaged(idCli, pageint);
			req.setAttribute("idCli", idCli);
			numpages = 0;			
			req.setAttribute("numpages", numpages);			
		}
		else if((Utils.esNuloVacio(premium) || TODOS.equals(premium)) &&
				Utils.isFechaFilterEmpty(fechaDiaFilter, fechaMesFilter, fechaAnioFilter) &&
				Utils.esNuloVacio(clienteFilter) &&
				Utils.esNuloVacio(servicioFilter) &&
				Utils.esNuloVacio(estadoFilter) &&
				Utils.esNuloVacio(productoFilter) &&
				Utils.esNuloVacio(entornoFilter) &&
				Utils.esNuloVacio(desdeFilter) &&
				Utils.esNuloVacio(hastaFilter) &&
				Utils.esNuloVacio(idCli)) {
			
			// sin filtros
			pruebas = pDao.getPruebasPaged(pageint);		
			CounterDao counterDao = CounterDao.getInstance();
			Counter count = counterDao.getCounterByName("prueba");
			numpages = Utils.calcNumPages(count.getValue(), PruebaDao.DATA_SIZE);	
			isNumResultsCalculated = true;
			req.setAttribute("numpages", numpages);
		} 
		else {
			// con filtros
			if(!Utils.esNuloVacio(clienteFilter)){
				idCli="";
			}
			pruebas = pDao.getPruebasByAllParam(fechaDiaFilter, fechaMesFilter, fechaAnioFilter, clienteFilter, servicioFilter, estadoFilter, productoFilter, entornoFilter,desdeFilter,hastaFilter, premium,idCli, pageint);		
			int numPagesItemIndex = pruebas.size()-1;
			int numResultsCalculated = Integer.parseInt(pruebas.get(numPagesItemIndex).getDetalles());
			if(numResultsCalculated > 0) {
				isNumResultsCalculated = true;
			}
			pruebas.remove(numPagesItemIndex);
			numpages = Utils.calcNumPages(numResultsCalculated, PruebaDao.DATA_SIZE);
			
			req.setAttribute("idCli", idCli);
			req.setAttribute("numpages", numpages);
			req.setAttribute("clienteFilter", clienteFilter);
			req.setAttribute("servicioFilter", servicioFilter);
			req.setAttribute("fechadia", fechaDiaFilter);
			req.setAttribute("fechames", fechaMesFilter);
			req.setAttribute("fechaanio", fechaAnioFilter);
			req.setAttribute("estadoFilter", estadoFilter);
			req.setAttribute("productoFilter", productoFilter);
			req.setAttribute("entornoFilter", entornoFilter);
			req.setAttribute("desdeFilter", desdeFilter);
			req.setAttribute("hastaFilter", hastaFilter);
			req.setAttribute("premiumFilter", premium);			
		}

		boolean lastpage = Utils.isLastPage(pageint, numpages, isNumResultsCalculated, pruebas.size(), PruebaDao.DATA_SIZE);
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
