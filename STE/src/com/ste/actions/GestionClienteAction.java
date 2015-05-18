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
import com.ste.counters.Counter;
import com.ste.dao.ClienteDao;
import com.ste.dao.CounterDao;
import com.ste.dao.ImplementacionDao;
import com.ste.dao.PruebaDao;
import com.ste.utils.Utils;

public class GestionClienteAction extends Action{

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
				

		ClienteDao cDao = ClienteDao.getInstance();
		CounterDao counterDao = CounterDao.getInstance();
		List<Cliente> clientes = new ArrayList<Cliente>();
		
		String idCli = req.getParameter("idCli");
		String fechaDiaFilter = req.getParameter("fechadia");
		String fechaMesFilter = req.getParameter("fechames");
		String fechaAnioFilter = req.getParameter("fechaanio");
		String identificadorFilter = req.getParameter("identificador");
		String nombreFilter = req.getParameter("nombre");
		String segmentoFilter = req.getParameter("segmento");
		String premiumFilter = req.getParameter("tipo");
		
		String page = req.getParameter("page");
		int pageint = Utils.stringToInt(page);	
		int numpages = 0;
		
		if(!Utils.esNuloVacio(idCli)) {
			// Por id cliente
			clientes.add(cDao.getClientebyId(Long.valueOf(idCli)));
			numpages = 0;
			req.setAttribute("numpages", numpages);
		}
		else {
			if(Utils.esNuloVacio(identificadorFilter) &&
					Utils.esNuloVacio(nombreFilter) &&
					Utils.isFechaFilterEmpty(fechaDiaFilter, fechaMesFilter, fechaAnioFilter) &&
					Utils.esNuloVacio(segmentoFilter) &&
					Utils.esNuloVacio(premiumFilter)) {
				// Sin filtros
				clientes = cDao.getClientePaged(pageint);
				Counter count = counterDao.getCounterByName("cliente");
				numpages = Utils.calcNumPages(count.getValue(), ClienteDao.DATA_SIZE);			
				req.setAttribute("numpages", numpages);		
			}
			else{
				// Con filtros
				clientes = cDao.getClienteByAllParam(identificadorFilter, nombreFilter, fechaDiaFilter, fechaMesFilter, fechaAnioFilter, segmentoFilter, premiumFilter, pageint);
				int numPagesItemIndex = clientes.size()-1;
				int numClientes = Integer.parseInt(clientes.get(numPagesItemIndex).getId_cliente());
				clientes.remove(numPagesItemIndex);
				numpages = Utils.calcNumPages(numClientes, ClienteDao.DATA_SIZE);
				
				req.setAttribute("numpages", numpages);
				req.setAttribute("identificador", identificadorFilter);
				req.setAttribute("nombre", nombreFilter);
				req.setAttribute("fechadia", fechaDiaFilter);
				req.setAttribute("fechames", fechaMesFilter);
				req.setAttribute("fechaanio", fechaAnioFilter);
				req.setAttribute("segmento", segmentoFilter);
				req.setAttribute("tipo", premiumFilter);
			}
		}
		
		boolean lastpage = Utils.isLastPage(pageint, numpages, clientes.size(), ClienteDao.DATA_SIZE);
		req.setAttribute("lastpage", lastpage);
		req.setAttribute("clientes", clientes);
		req.setAttribute("page", pageint);
		
		return  mapping.findForward("ok");
	}
}
