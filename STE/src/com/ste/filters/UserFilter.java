package com.ste.filters;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ste.dao.UserDao;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class UserFilter implements Filter {
	
	private static final Logger log = Logger.getLogger(UserFilter.class.getName());


	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {

		UserDao uDao = UserDao.getInstance();

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		log.setLevel(Level.INFO);
		
		if (user==null){
			log.info("Usuario ====> NULL;");
		}else{
			log.info("Usuario: " + user);
		}
		
		
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		String url = request.getRequestURL().toString();

		if (user != null) {
			if (user.getEmail().contains("@bbva.com")
					|| url.contains("localhost") || url.contains("8888")) {

				String email = "";

				
				
				email = user.getEmail();
				req.setAttribute("mail", email);
				log.info(email);

				com.ste.beans.User usuario = uDao.getUserByMail(email);
				log.info("Usuario: " + usuario);
				
				if (usuario!=null){
					HttpSession sesion = request.getSession();
					
					log.info("Usuario Logueado: " + usuario.getNombre() + " " + usuario.getApellido1() );

					if (url.contains("localhost") || url.contains("8888")) {
						sesion.setAttribute("entorno", "http://localhost:8888");
					} else {
						if (url.contains("pre.")) {
							sesion.setAttribute("entorno",
									"https://pre.portal-ste.appspot.com");

						} else {
							sesion.setAttribute("entorno",
									"https://portal-ste.appspot.com");
						}						
					}
					
					Integer permiso = usuario.getPermiso();
					sesion.setAttribute("permiso", permiso);
					chain.doFilter(req, resp);
				}else{
					log.info("Usuario NULL");
					if (url.contains("8888")){
						chain.doFilter(req, resp);
					}else{
						response.sendRedirect("http://intranet.bbva.com/");
					}
				}
			}
		} else {
			log.info("Usuario NULL");

			if (url.contains("localhost") || url.contains("8888")) {
				chain.doFilter(req, resp);
			} else {
				response.sendRedirect("http://intranet.bbva.com/");
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
