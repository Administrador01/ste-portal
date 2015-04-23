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
import com.ste.dao.ClienteDao;
import com.ste.dao.EstadoDao;
import com.ste.dao.PaisDao;
import com.ste.dao.ProductoCanalDao;
import com.ste.dao.SoporteDao;
import com.ste.dao.TipoServicioDao;
import com.ste.utils.Utils;

public class SoporteAction extends Action{

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		
		SoporteDao sDao = SoporteDao.getInstance();
		List<Soporte> soportes = new ArrayList <Soporte>();
		
		String idCli = req.getParameter("idCli");
		String fechaFilter = req.getParameter("fecha-filter");
		String clienteFilter = req.getParameter("cliente-filter");
		String segmentoFilter = req.getParameter("segmento-filter");
		String estadoFilter = req.getParameter("estado-filter");
		String tipoServFilter = req.getParameter("tipoServ-filter");
		String productoFilter = req.getParameter("producto-filter");
		String descripcionFilter = req.getParameter("descripcion-filter");
		
		String page = req.getParameter("page");
		int pageint = Utils.stringToInt(page);	
		
		if(idCli == null || idCli == ""|| (fechaFilter.equals("")&&clienteFilter.equals("")&&segmentoFilter.equals("")&&estadoFilter.equals("")&&tipoServFilter.equals("")&&productoFilter.equals("")&&descripcionFilter.equals(""))){
			soportes= sDao.getSoportesPaged(pageint);
			req.setAttribute("soportes", soportes);
		}else{
			soportes = sDao.getAllSoportesByClientId(idCli);
		}
		
//		if(idCli == null || idCli == ""|| (fechaFilter.equals("")&&clienteFilter.equals("")&&segmentoFilter.equals("")&&estadoFilter.equals("")&&tipoServFilter.equals("")&&productoFilter.equals("")&&descripcionFilter.equals(""))){
//			soportes= sDao.getAllDelSoportes();
//			req.setAttribute("soportesDel", soportes);
//			soportes= sDao.getAllSoportes();
//			
//		}else{
//			soportes = sDao.getAllSoportesByClientId(idCli);
//		}
//		
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
