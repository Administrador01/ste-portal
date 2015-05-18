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
import com.ste.beans.Pais;
import com.ste.beans.ProductoCanal;
import com.ste.beans.Soporte;
import com.ste.beans.TipoServicio;
import com.ste.counters.Counter;
import com.ste.dao.ClienteDao;
import com.ste.dao.CounterDao;
import com.ste.dao.EstadoDao;
import com.ste.dao.PaisDao;
import com.ste.dao.ProductoCanalDao;
import com.ste.dao.SoporteDao;
import com.ste.dao.TipoServicioDao;
import com.ste.utils.Utils;

public class SoporteAction extends Action{

	private static final String TODOS = "TODOS";
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		
		SoporteDao sDao = SoporteDao.getInstance();
		CounterDao counterDao = CounterDao.getInstance();
		List<Soporte> soportes = new ArrayList <Soporte>();
		
		String idCli = req.getParameter("idCli");
		String fechaDiaFilter = req.getParameter("fechadia");
		String fechaMesFilter = req.getParameter("fechames");
		String fechaAnioFilter = req.getParameter("fechaanio");
		String clienteFilter = req.getParameter("clienteFilter");
		String segmentoFilter = req.getParameter("segmentoFilter");
		String estadoFilter = req.getParameter("estadoFilter");
		String tipoServFilter = req.getParameter("servicioFilter");
		String productoFilter = req.getParameter("productoFilter");
		String descripcionFilter = req.getParameter("descripcionFilter");
		String premiumFilter = req.getParameter("premium");
		
		String page = req.getParameter("page");
		int pageint = Utils.stringToInt(page);	
		
		if(!Utils.esNuloVacio(idCli)) {
			// Por id cliente
			req.setAttribute("idCli", idCli);
			soportes = sDao.getAllSoportesByClientId(idCli);
		}
		else if(idCli == null) {
			// Carga normal sin filtros
			soportes= sDao.getSoportesPaged(pageint);
			req.setAttribute("soportes", soportes);
			Counter count = counterDao.getCounterByName("soporte");
			int numpages = (count.getValue()/SoporteDao.DATA_SIZE) + 1;			
			req.setAttribute("numpages", numpages);
		} 
		else if((Utils.esNuloVacio(premiumFilter) || TODOS.equals(premiumFilter)) &&
				Utils.isFechaFilterEmpty(fechaDiaFilter, fechaMesFilter, fechaAnioFilter) &&
				Utils.esNuloVacio(clienteFilter) &&
				Utils.esNuloVacio(segmentoFilter) &&
				Utils.esNuloVacio(estadoFilter) &&
				Utils.esNuloVacio(tipoServFilter) &&
				Utils.esNuloVacio(productoFilter) &&
				Utils.esNuloVacio(descripcionFilter)) {
			
			// Carga sin filtros con TODOS
			soportes= sDao.getSoportesPaged(pageint);
			req.setAttribute("soportes", soportes);
			Counter count = counterDao.getCounterByName("soporte");
			int numpages = (count.getValue()/SoporteDao.DATA_SIZE) + 1;			
			req.setAttribute("numpages", numpages);			
		}
		else {
			// Carga filtrada
			if(!clienteFilter.equals("")){
				idCli="";
			}
			soportes= sDao.getSoportesByAllParam(fechaDiaFilter, fechaMesFilter, fechaAnioFilter, clienteFilter, segmentoFilter, estadoFilter, tipoServFilter, productoFilter, descripcionFilter, premiumFilter, idCli, pageint);
			int numPagesItemIndex = soportes.size()-1;
			int numpages = (Integer.parseInt(soportes.get(numPagesItemIndex).getDetalles())/SoporteDao.DATA_SIZE) + 1;
			soportes.remove(numPagesItemIndex);
			req.setAttribute("idCli", idCli);
			req.setAttribute("numpages", numpages);
			req.setAttribute("clienteFilter", clienteFilter);
			req.setAttribute("segmentoFilter", segmentoFilter);
			req.setAttribute("servicioFilter", tipoServFilter);
			req.setAttribute("fechadia", fechaDiaFilter);
			req.setAttribute("fechames", fechaMesFilter);
			req.setAttribute("fechaanio", fechaAnioFilter);
			req.setAttribute("estadoFilter", estadoFilter);
			req.setAttribute("productoFilter", productoFilter);
			req.setAttribute("descripcionFilter", descripcionFilter);
			req.setAttribute("premiumFilter", premiumFilter);
		}
		
		boolean lastpage = (soportes.size() < SoporteDao.DATA_SIZE) ? true : false;
		
		req.setAttribute("lastpage", lastpage);
		req.setAttribute("page", pageint);
		
		//Mandamos los clientes para generar la lista select desplegable
		ClienteDao cDao = ClienteDao.getInstance();
		List<Cliente> clientes = cDao.getAllClients();
		
		req.setAttribute("clientes", clientes);
		req.setAttribute("soportes", soportes);
		
		ProductoCanalDao prodDao = ProductoCanalDao.getInstance();
		List<ProductoCanal> productos = prodDao.getAllProductosFor("soportes");
		
		req.setAttribute("productos", productos);
		
		PaisDao paisDao = PaisDao.getInstance();
		List<Pais> paises = paisDao.getAllPaises();
		
		req.setAttribute("paises", paises);
		
		
		TipoServicioDao servDao = TipoServicioDao.getInstance();
		List<TipoServicio> servicios = servDao.getAllServicios();
		req.setAttribute("tiposervicios", servicios);
		
		EstadoDao estDao = EstadoDao.getInstance();
		List<Estado> estados = estDao.getAllEstados();
		req.setAttribute("estados", estados);
		
		return  mapping.findForward("ok");
	}
}
