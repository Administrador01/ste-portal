package com.ste.filters;

import java.io.IOException;
import java.util.Arrays;
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

import com.ste.beans.TipoServicio;
import com.ste.dao.TipoServicioDao;
import com.ste.dao.UserDao;
import com.ste.servlets.ServicioServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class UserFilter implements Filter {
	
	private static final Logger log = Logger.getLogger(UserFilter.class.getName());
	  private static String CLIENT_ID = "460945221032-8u3kkoqbf5sf0bcms4rrtjcuc89fthb8.apps.googleusercontent.com";
	  private static String CLIENT_SECRET = "3AI339j0GGOzdbqBv_64Bhw_";

	  private static String REDIRECT_URI = "https://portal-ste.appspot.com/oauth2callback";

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
		
		if (url.contains("oauth2")){
			
			log.info(url);
			
			String code = request.getParameter("code");
			
			


		    HttpTransport httpTransport = ServicioServlet.httpTransport;
		    JsonFactory jsonFactory = ServicioServlet.jsonFactory;
			
		    GoogleAuthorizationCodeFlow flow = ServicioServlet.flow;
			
		    GoogleTokenResponse respuesta = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
		    
		   // log.info(respuesta.parseIdToken().toString());
		    
		    
		    GoogleCredential credential = new GoogleCredential().setFromTokenResponse(respuesta);
		
		   // log.info(credential.getAccessToken());
			
			 Drive service = new Drive.Builder(httpTransport, jsonFactory, null).setHttpRequestInitializer(credential).setApplicationName("Portal Ste").build();
			
			 
			//Insert a file  
			/*File body = new File();
			body.setTitle("My document");
			body.setDescription("A test document");
			body.setMimeType("text/plain");
			    
			java.io.File fileContent = new java.io.File("./document.txt");
			FileContent mediaContent = new FileContent("text/plain", fileContent);*/
			//File file = service.files().insert(body, mediaContent).execute();
			//System.out.println("File ID: " + file.getId());
			
			response.sendRedirect("https://drive.google.com/");
			
			
			
		}else{
			if (user != null) {
			if (user.getEmail().contains("@bbva.com")
					|| url.contains("localhost") || url.contains("8888")) {

				String email = "";

				
				
				email = user.getEmail();
				HttpSession sesion = request.getSession();
				sesion.setAttribute("mail", email);
				req.setAttribute("mail", email);
				log.info(email);

				com.ste.beans.User usuario = uDao.getUserByMail(email);
				log.info("Usuario: " + usuario);
				
				if (usuario!=null){
					
					
					log.info("Usuario Logueado: " + usuario.getNombre() + " " + usuario.getApellido1() );

					String entorno = url.split("http://")[1].split("/")[0];
					
					
					sesion.setAttribute("entorno", "http://" +entorno);
					
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
