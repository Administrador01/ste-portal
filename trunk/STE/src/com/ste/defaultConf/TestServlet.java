package com.ste.defaultConf;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.ste.beans.User;
import com.ste.dao.UserDao;

public class TestServlet extends HttpServlet {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3249923568753327965L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp){
		String mail = req.getParameter("mail");
		
		UserDao uDao = UserDao.getInstance();
		User u = uDao.getUserByMail(mail);
		
		JSONObject json = new JSONObject();
		try {
		if (u!=null){
			json.append("nombre", u.getNombre());
			
			json.append("Apellidos", u.getApellido1() + " " + u.getApellido2());
		}else{
			json.append("usuario", "NULL");
		}
		
			
		
		
			resp.setCharacterEncoding("UTF-8");
	        resp.setContentType("application/json");       
			resp.getWriter().println(json);
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp){
		doGet(req,resp);
	}
}
