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



import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.ste.dao.UserDao;




public class UserFilter implements Filter {
	
	public static final Logger log = Logger.getLogger(UserFilter.class.getName());
	
	
	/*	 *METODO PARA PODER AUTENTICARSE POR OAUTH2 
		 * Variables 
		 * --------------------------------------------------------------------------------
	  private static String CLIENT_ID = "460945221032-8u3kkoqbf5sf0bcms4rrtjcuc89fthb8.apps.googleusercontent.com";
	  private static String CLIENT_SECRET = "3AI339j0GGOzdbqBv_64Bhw_";
	  private static	HttpTransport httpTransport = new NetHttpTransport();
	  private static	JsonFactory jsonFactory = new JacksonFactory();
	  private static String REDIRECT_URI = "https://portal-ste.appspot.com/oauth2callback";
	  public static  GoogleAuthorizationCodeFlow flow = null;
	  private static final String CLIENTSECRETS_LOCATION = "/WEB-INF/client_secret.json";
	  private static ArrayList<GoogleCredential> Credenciales = new ArrayList<GoogleCredential>();
	  private static ArrayList<String> Emails = new ArrayList<String>();
	  
	  public static String urlRedirectOperac =null; */
	
	
	@Override
	public void destroy() {
	}
	
	/*	METODO PARA PODER AUTENTICARSE POR OAUTH2 
	 *	Metodos de solicitud manejo y despacho de las claves
	 * --------------------------------------------------------------------------------
	
	public static void AutenticacionGoogle(HttpServletRequest req, HttpServletResponse resp) throws IOException{

		flow = new GoogleAuthorizationCodeFlow.Builder(
	            httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE))
	            .setAccessType("online")
	            .setApprovalPrompt("auto").build();
		log.info("mandamos url para token");
	    String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
	    resp.sendRedirect(url);
	}
	
	
	
	  static GoogleAuthorizationCodeFlow getFlow() throws IOException {
		    if (flow == null) {
		    	
		      HttpTransport httpTransport = new NetHttpTransport();
		      JacksonFactory jsonFactory = new JacksonFactory();
		      flow =
		          new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE))
		              .setAccessType("offline").setApprovalPrompt("force").build();
		    }
		    return flow;
		  }

	
	  public static String getAuthorizationUrl(String emailAddress, String state) throws IOException {
		  log.info("mandamos url para token");
		    GoogleAuthorizationCodeRequestUrl urlBuilder =
		        getFlow().newAuthorizationUrl().setRedirectUri(REDIRECT_URI).setState(state);
		    urlBuilder.set("user_id", emailAddress);
		    return urlBuilder.build();
		  }


	public static GoogleCredential getCredential(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		UserFilter.urlRedirectOperac  = Utils.getAllUrl(req);
		
		GoogleCredential credencial=null;
		String mail = (String)req.getAttribute("mail");
		int i = 0;
		boolean flag = true;
		if(Emails.contains(mail)){
			while(i<Emails.size()&&flag){
				if(Emails.get(i).equals(mail)){
					credencial = Credenciales.get(i);
					flag=false;
				}
				i++;
			}
			
		}else {
			credencial = new GoogleCredential();
		}
		return credencial;
	}
	

	
	public static Drive getDrive(GoogleCredential credential) throws IOException{
		
		Drive drive = new Drive.Builder(httpTransport, jsonFactory, null).setHttpRequestInitializer(credential).setApplicationName("Portal Ste").build();
		return drive;
	}*/

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
		/*METODO PARA PODER AUTENTICARSE POR OAUTH2 
		 * Recepcion de token y almacenamiento en arraylist y base de datos
		 * --------------------------------------------------------------------------------
		if (url.contains("oauth2")){
			log.info("Retorno de token");
			String code = request.getParameter("code");
			flow = getFlow();
		    GoogleTokenResponse respuesta = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();	    
		    GoogleCredential credential = new GoogleCredential().setFromTokenResponse(respuesta);	
		   
			String mail=(String)req.getAttribute("mail");
		    //storeCredentials(mail,credential);
			
			int i =0;
			boolean flag = true;
			if (Emails.contains(mail)&&Emails!=null&&mail!=null&&!Emails.isEmpty()){
				while(i<Emails.size()&&flag){
					if(Emails.get(i).equals(mail)){
						Credenciales.remove(i);
						Emails.remove(i);
						flag=false;
						log.info("error de primera pasada");
					}
					i++;
				}
				
			}
			UserFilter.Credenciales.add(credential);
			UserFilter.Emails.add(mail);
			log.info("Guardada credencial"+" "+Emails.get(i));
		    log.info("operacion que va a realizar "+ urlRedirectOperac);
			response.sendRedirect(urlRedirectOperac);
			
		}else{*/
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
	

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		UserService userService = UserServiceFactory.getUserService();

		User user = userService.getCurrentUser();

		if (user != null) {
			String email = user.getEmail();
		}
		
	}


}
