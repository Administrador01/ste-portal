package com.ste.actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ste.beans.User;
import com.ste.config.StaticConfig;
import com.ste.dao.UserDao;


public class UsersAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {
			UserDao uDao = UserDao.getInstance();
			List<User> usuarios = uDao.getAllNonDeletedUsers(); 
			req.setAttribute("userList", usuarios);
			req.setAttribute("permisos", StaticConfig.permisos);
			req.setAttribute("departamentos", StaticConfig.departamentos);			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("ok");
	}
}
