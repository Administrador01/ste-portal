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
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
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
	
	public static final Logger log = Logger.getLogger(UserFilter.class.getName());
	  private static String CLIENT_ID = "460945221032-8u3kkoqbf5sf0bcms4rrtjcuc89fthb8.apps.googleusercontent.com";
	  private static String CLIENT_SECRET = "3AI339j0GGOzdbqBv_64Bhw_";
	  private static	HttpTransport httpTransport = new NetHttpTransport();
	  private static	JsonFactory jsonFactory = new JacksonFactory();
	  private static String REDIRECT_URI = "https://portal-ste.appspot.com/oauth2callback";
	  public static  GoogleAuthorizationCodeFlow flow = null;
	  private static final String CLIENTSECRETS_LOCATION = "/WEB-INF/client_secret.json";

	  public static HttpServletRequest urlRedirectOperac =null; 
	@Override
	public void destroy() {
	}
	
	
	
	public static void AutenticacionGoogle(HttpServletRequest req, HttpServletResponse resp) throws IOException{

		flow = new GoogleAuthorizationCodeFlow.Builder(
	            httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE))
	            .setAccessType("online")
	            .setApprovalPrompt("auto").build();
		log.info("mandamos url para token");
	    String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
	    resp.sendRedirect(url);
	}
	
	public static void storeCredentials (String mail, GoogleCredential credential){
		UserDao uDao = UserDao.getInstance();
	    com.ste.beans.User usuario = uDao.getUserByMail(mail);
	    //usuario.setCredencial(credential);
	    uDao.createUser(usuario);
	}
	
	
	
	
	public static GoogleCredential getCredential(HttpServletRequest req, HttpServletResponse resp) throws IOException{

			String mail=(String)req.getAttribute("mail");
		    UserDao uDao = UserDao.getInstance();
		    com.ste.beans.User usuario = uDao.getUserByMail(mail);
		    log.info("usuario de credencial  "+ usuario.getEmail());
		    //GoogleCredential credential = usuario.getCredencial();
		    UserFilter.urlRedirectOperac = req;
		    
		    GoogleCredential credential = null;
		    if(/*credential.getRefreshToken() != null && credential!=null */false){
		    	log.info("la credencial es coorecta  "+ usuario.getEmail());
		    	return credential;
		    }else{
		    	log.info("la credencial huele a pis  "+ usuario.getEmail());
		    	AutenticacionGoogle(req,resp);
		    	return null;
		    }
		
	   
	    
	}
	
	public static Drive getDrive(GoogleCredential credential) throws IOException{
		Drive drive = new Drive.Builder(httpTransport, jsonFactory, null).setHttpRequestInitializer(credential).setApplicationName("Portal Ste").build();
		return drive;
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
			log.info("Retorno de token");
			String code = request.getParameter("code");
		    GoogleTokenResponse respuesta = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();	    
		    GoogleCredential credential = new GoogleCredential().setFromTokenResponse(respuesta);	
			 //Drive service = new Drive.Builder(httpTransport, jsonFactory, null).setHttpRequestInitializer(credential).setApplicationName("Portal Ste").build();
			String mail=(String)req.getAttribute("mail");
		    storeCredentials(mail,credential);
		    log.info("Guardada credencial");
		    log.info("operacion que va a realizar "+ urlRedirectOperac);
			response.set;
		}else{
			if (user != null) {
			if (user.getEmail().contains("@bbva.com")
					|| url.contains("localhost") || url.contains("8888")||true) {

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
	
	 public static class GetCredentialsException extends Exception {

		    protected String authorizationUrl;

		    /**
		     * Construct a GetCredentialsException.
		     *
		     * @param authorizationUrl The authorization URL to redirect the user to.
		     */
		    public GetCredentialsException(String authorizationUrl) {
		      this.authorizationUrl = authorizationUrl;
		    }

		    /**
		     * Set the authorization URL.
		     */
		    public void setAuthorizationUrl(String authorizationUrl) {
		      this.authorizationUrl = authorizationUrl;
		    }

		    /**
		     * @return the authorizationUrl
		     */
		    public String getAuthorizationUrl() {
		      return authorizationUrl;
		    }
		  }

		  /**
		   * Exception thrown when a code exchange has failed.
		   */
		  public static class CodeExchangeException extends GetCredentialsException {

		    /**
		     * Construct a CodeExchangeException.
		     *
		     * @param authorizationUrl The authorization URL to redirect the user to.
		     */
		    public CodeExchangeException(String authorizationUrl) {
		      super(authorizationUrl);
		    }

		  }

		  /**
		   * Exception thrown when no refresh token has been found.
		   */
		  public static class NoRefreshTokenException extends GetCredentialsException {

		    /**
		     * Construct a NoRefreshTokenException.
		     *
		     * @param authorizationUrl The authorization URL to redirect the user to.
		     */
		    public NoRefreshTokenException(String authorizationUrl) {
		      super(authorizationUrl);
		    }

		  }

}
