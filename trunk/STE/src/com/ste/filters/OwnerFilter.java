package com.ste.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.ste.dao.UserDao;

public class OwnerFilter implements Filter {
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {

		UserDao uDao = UserDao.getInstance();
		
		 UserService userService = UserServiceFactory.getUserService();
		 User user = userService.getCurrentUser();
		 
		 HttpServletRequest request = (HttpServletRequest)req;
		 HttpServletResponse response = (HttpServletResponse)resp;
		 
		 String url = request.getRequestURL().toString();
		 
		 com.ste.beans.User usuario = uDao.getUserByMail(user.getEmail());
		 
		 if (usuario!=null){
			 if (usuario.getPermiso()<=3) {		
				 chain.doFilter(req, resp);
			 }else{
				 if (url.contains("localhost")){
					 response.sendRedirect("http://localhost:8888");
				 }else{
					 if (url.contains("pre.")){
						 response.sendRedirect("https://pre.gcs-areacliente.appspot.com");

					 }else{
						 response.sendRedirect("https://gcs-areacliente.appspot.com");
					 }
				 }				 			 
			 }
		 }else{
			 if (url.contains("localhost")){
				 response.sendRedirect("http://localhost:8888");
			 }else{
				 if (url.contains("pre.")){
					 response.sendRedirect("https://pre.gcs-areacliente.appspot.com");

				 }else{
					 response.sendRedirect("https://gcs-areacliente.appspot.com");
				 }
			 }	
		 }

		
		
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
		 UserService userService = UserServiceFactory.getUserService();
		 
		 User user = userService.getCurrentUser();

		 if (user != null) {
		     String email = user.getEmail();
		 }
		
	}

}
