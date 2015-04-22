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
		List<Cliente> clientesBorrado = new ArrayList<Cliente>();
		
		String idCli = req.getParameter("idCli");
		String fechaFilter = req.getParameter("fecha");
		
		String page = req.getParameter("page");
		int pageint = Utils.stringToInt(page);	
		
		if(idCli == null || idCli == ""){
			if(fechaFilter==null){
			clientes = cDao.getClientePaged(pageint);
			Counter count = counterDao.getCounterByName("cliente");
			int numpages = (count.getValue()/ClienteDao.DATA_SIZE) + 1;			
			req.setAttribute("numpages", numpages);
			boolean lastpage = (clientes.size() < ClienteDao.DATA_SIZE) ? true : false;
			
			req.setAttribute("lastpage", lastpage);
			//clientesBorrado = cDao.getAllDelClients();
			//req.setAttribute("clientesBorrados", clientesBorrado);
			}else{
				String identificadorFilter = req.getParameter("identificador");
				String nombreFilter = req.getParameter("nombre");
				String segmentoFilter = req.getParameter("segmento");
				String premiumFilter = req.getParameter("tipo");
				clientes = cDao.getClienteByAllParam(identificadorFilter, nombreFilter, fechaFilter, segmentoFilter, premiumFilter, pageint);
				int numpages = (Integer.parseInt(clientes.get(clientes.size()-1).getId_cliente()));
				clientes.remove(clientes.size()-1);
				req.setAttribute("numpages", numpages);
				req.setAttribute("identificador", identificadorFilter);
				req.setAttribute("nombre", nombreFilter);
				req.setAttribute("fecha", fechaFilter);
				req.setAttribute("segmento", segmentoFilter);
				req.setAttribute("tipo", premiumFilter);
				
				boolean lastpage = (numpages < ClienteDao.DATA_SIZE) ? true : false;
				if(identificadorFilter.equals("")&& nombreFilter.equals("")&& fechaFilter.equals("")&& segmentoFilter.equals("")&& premiumFilter.equals(""))lastpage=false;
				req.setAttribute("lastpage", lastpage);
			}
		}else{
			clientes.add(cDao.getClientebyId(Long.valueOf(idCli)));
			int numpages = 0;			
			req.setAttribute("numpages", numpages);
		}
		
		
		req.setAttribute("clientes", clientes);
		req.setAttribute("page", page);
		
		return  mapping.findForward("ok");
	}
}
