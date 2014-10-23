package com.ste.actions.modal;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ste.beans.User;
import com.ste.config.StaticConfig;
import com.ste.dao.UserDao;

public class UserModalAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {
			String id = req.getParameter("id");
						
			UserDao uDao = UserDao.getInstance();
			User usuario = uDao.getUserbyId(Long.parseLong(id));
			
			ArrayList<String> areas = usuario.getAreas();
			String areasStr = "";
			
			if (areas!=null){
				for (String a:areas){
					areasStr += a;
				}
			}
			
			
			
			
			req.setAttribute("areas", areasStr);
			req.setAttribute("usuario", usuario);
			req.setAttribute("permisos", StaticConfig.permisos);
			req.setAttribute("departamentos", StaticConfig.departamentos);			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("ok");
	}
}
