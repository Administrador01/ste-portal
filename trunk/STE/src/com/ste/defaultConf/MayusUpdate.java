package com.ste.defaultConf;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.ste.beans.Cliente;
import com.ste.beans.Estado;
import com.ste.beans.EstadoImplementacion;
import com.ste.beans.Implementacion;
import com.ste.beans.Pais;
import com.ste.beans.ProductoCanal;
import com.ste.beans.Prueba;
import com.ste.beans.Servicio;
import com.ste.beans.Soporte;
import com.ste.beans.TipoServicio;
import com.ste.beans.User;
import com.ste.dao.ClienteDao;
import com.ste.dao.EstadoDao;
import com.ste.dao.EstadoImplementacionDao;
import com.ste.dao.ImplementacionDao;
import com.ste.dao.PaisDao;
import com.ste.dao.ProductoCanalDao;
import com.ste.dao.PruebaDao;
import com.ste.dao.ServicioDao;
import com.ste.dao.SoporteDao;
import com.ste.dao.TipoServicioDao;
import com.ste.dao.UserDao;
import com.ste.utils.Utils;

public class MayusUpdate extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8059778774757795508L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession sesion = req.getSession();
		JSONObject json = new JSONObject();
		String result = "";

		String accion = req.getParameter("accion");

		HttpSession session = req.getSession();
		String usermail = (String) session.getAttribute("usermail");
		try {
			if ("user".equals(accion)) {
				result = createUser(req, resp);
				json.append("success", "true");
				json.append("result", result);
			} else if ("cliente".equals(accion)) {
				result = updateClientes(req, resp);
				json.append("success", "true");
				json.append("result", result);
			} else if ("servicio".equals(accion)) {
				result = updateServicios(req, resp);
				json.append("success", "true");
				json.append("result", result);
			} else if ("tiposervicio".equals(accion)) {
				result = updateTipoServicio(req, resp);
				json.append("success", "true");
				json.append("result", result);
			} else if ("pais".equals(accion)) {
				result = updatePaises(req, resp);
				json.append("success", "true");
				json.append("result", result);
			} else if ("estadoimp".equals(accion)) {
				result = updateEstadoImp(req, resp);
				json.append("success", "true");
				json.append("result", result);
			} else if ("estado".equals(accion)) {
				result = updateEstado(req, resp);
				json.append("success", "true");
				json.append("result", result);
			} else if ("producto".equals(accion)) {
				result = updateProductoCanal(req, resp);
				json.append("success", "true");
				json.append("result", result);
			} else if ("soporte".equals(accion)) {
				result = updateSoporte(req, resp);
				json.append("success", "true");
				json.append("result", result);
			} else if ("prueba".equals(accion)) {
				String page = req.getParameter("page");
				if(!Utils.esNuloVacio(page)) {
					int pageint = Integer.parseInt(page);
					result = updatePrueba(req, resp, pageint);
				}
				else {
					result += " introduzca pagina de actualizacion ";
				}
				json.append("success", "true");
				json.append("result", result);
			} 
			/*else if ("cleanprueba".equals(accion)) {
				result = cleanPrueba(req, resp);
				json.append("success", "true");
				json.append("result", result);
			} */
			else if ("implementacion".equals(accion)) {
				String page = req.getParameter("page");
				if(!Utils.esNuloVacio(page)) {
					int pageint = Integer.parseInt(page);
					result = updateImplementacion(req, resp, pageint);
				}
				else {
					result += " introduzca pagina de actualizacion ";
				}
				json.append("success", "true");
				json.append("result", result);
			} 
			
			
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("text/plain");
			resp.getWriter().println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		doPost(req, resp);
	}

	public String createUser(HttpServletRequest req, HttpServletResponse resp)
			throws InterruptedException, IOException {

		String result = "";
		try {
			UserDao userDao = UserDao.getInstance();
			User u = new User();
			u.setApellido1("Gonzalez");
			u.setApellido2("Roman");
			u.setNombre("Roberto");
			u.setEmail("roberto.gonzalez.roman.contractor@bbva.com");
			u.setDepartamento("Global Customer Service");
			u.setPermiso(1);
			u.setErased(false);
			u.setPermisoStr("Propietario");
			u.setPermiso_clientes(3);
			u.setPermiso_documentacion(3);
			u.setPermiso_informes(3);
			u.setPermiso_pruebas(3);
			u.setPermiso_servicios(3);
			u.setPermiso_soporte(3);

			userDao.createUser(u);
			;
			result += u.getNombre() + "";

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public String updateClientes(HttpServletRequest req,
			HttpServletResponse resp) throws InterruptedException, IOException {

		String result = "";
		try {
			ClienteDao clientesDao = ClienteDao.getInstance();
			List<Cliente> clientes = clientesDao.getAllClients();

			for (Cliente cliente : clientes) {
				cliente.setNombre(Utils.toUpperCase(cliente.getNombre()));
				cliente.setPremium(Utils.toUpperCase(cliente.getPremium()));
				cliente.setTipo_cliente(Utils.toUpperCase(cliente
						.getTipo_cliente()));
				clientesDao.createCliente(cliente);
				result += cliente.getNombre() + ", ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public String updateServicios(HttpServletRequest req,
			HttpServletResponse resp) throws InterruptedException, IOException {

		String result = "";
		try {
			ServicioDao serviciosDao = ServicioDao.getInstance();
			List<Servicio> servicios = serviciosDao.getAllServicios();

			for (Servicio servicio : servicios) {
				servicio.setName(Utils.toUpperCase(servicio.getName()));
				servicio.setTipo(Utils.toUpperCase(servicio.getTipo()));
				serviciosDao.createServicio(servicio);
				result += servicio.getName() + ", ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public String updateTipoServicio(HttpServletRequest req,
			HttpServletResponse resp) throws InterruptedException, IOException {

		String result = "";
		try {
			TipoServicioDao tipoServicioDao = TipoServicioDao.getInstance();
			List<TipoServicio> tipoServicios = tipoServicioDao.getAllServicios();

			for (TipoServicio tipoServicio : tipoServicios) {
				tipoServicio.setNme(Utils.toUpperCase(tipoServicio.getName()));
				tipoServicioDao.createTipoServicio(tipoServicio);
				result += tipoServicio.getName() + ", ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public String updatePaises(HttpServletRequest req,
			HttpServletResponse resp) throws InterruptedException, IOException {

		String result = "";
		try {
			PaisDao paisDao = PaisDao.getInstance();
			List<Pais> paises = paisDao.getAllPaises();

			for (Pais pais : paises) {
				pais.setName(Utils.toUpperCase(pais.getName()));
				paisDao.createPais(pais);
				result += pais.getName() + ", ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public String updateEstadoImp(HttpServletRequest req,
			HttpServletResponse resp) throws InterruptedException, IOException {

		String result = "";
		try {
			EstadoImplementacionDao estadoImpDao = EstadoImplementacionDao.getInstance();
			List<EstadoImplementacion> estados = estadoImpDao.getAllEstadoImplementacions();

			for (EstadoImplementacion estado : estados) {
				estado.setName(Utils.toUpperCase(estado.getName()));
				estadoImpDao.createEstadoImplementacion(estado);
				result += estado.getName() + ", ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public String updateEstado(HttpServletRequest req,
			HttpServletResponse resp) throws InterruptedException, IOException {

		String result = "";
		try {
			EstadoDao estadoDao = EstadoDao.getInstance();
			List<Estado> estados = estadoDao.getAllEstados();

			for (Estado estado : estados) {
				estado.setName(Utils.toUpperCase(estado.getName()));
				estadoDao.createEstado(estado);
				result += estado.getName() + ", ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public String updateProductoCanal(HttpServletRequest req,
			HttpServletResponse resp) throws InterruptedException, IOException {

		String result = "";
		try {
			ProductoCanalDao productoDao = ProductoCanalDao.getInstance();
			List<ProductoCanal> productos = productoDao.getAllProductos();

			for (ProductoCanal producto : productos) {
				producto.setName(Utils.toUpperCase(producto.getName()));
				productoDao.createProductoCanal(producto);
				result += producto.getName() + ", ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public String updateSoporte(HttpServletRequest req,
			HttpServletResponse resp) throws InterruptedException, IOException {

		String result = "";
		try {
			SoporteDao soporteDao = SoporteDao.getInstance();
			List<Soporte> soportes = soporteDao.getAllEvenDelSoportes();

			for (Soporte soporte : soportes) {
				soporte.setCliente_name(Utils.toUpperCase(soporte.getCliente_name()));
				soporte.setDetalles(Utils.toUpperCase(soporte.getDetalles()));
				soporte.setEstado(Utils.toUpperCase(soporte.getEstado()));
				soporte.setPais(Utils.toUpperCase(soporte.getPais()));
				soporte.setPeticionario(Utils.toUpperCase(soporte.getPeticionario()));
				soporte.setPremium(Utils.toUpperCase(soporte.getPremium()));
				soporte.setSolucion(Utils.toUpperCase(soporte.getSolucion()));
				soporte.setTipo_cliente(Utils.toUpperCase(soporte.getTipo_cliente()));
				soporte.setTipo_servicio(Utils.toUpperCase(soporte.getTipo_servicio()));
				soporte.setTipo_soporte(Utils.toUpperCase(soporte.getTipo_soporte()));
				soporte.setProducto_canal(Utils.toUpperCase(soporte.getProducto_canal()));
				soporteDao.createSoporte(soporte);
				result += soporte.getCliente_name() + " - " +soporte.getTipo_servicio() + " * ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String updatePrueba(HttpServletRequest req,
			HttpServletResponse resp, int page) throws InterruptedException, IOException {

		String result = "";
		try {
			PruebaDao pruebaDao = PruebaDao.getInstance();
			List<Prueba> pruebas = pruebaDao.getAllPruebasUpdateMayusPaged(page);
			result += pruebas.size() + " -- ";
			
			for (Prueba prueba : pruebas) {
				prueba.setClient_name(Utils.toUpperCase(prueba.getClient_name()));
				prueba.setDetalles(Utils.toUpperCase(prueba.getDetalles()));
				//prueba.setEntorno(Utils.toUpperCase(prueba.getEntorno()));
				if(prueba.getEntorno().equals("PRODUCCIÓN") || prueba.getEntorno().equals("Producción")) {
					prueba.setEntorno("PRODUCCION");
				}
				else {
					prueba.setEntorno(Utils.toUpperCase(prueba.getEntorno()));
				}
				prueba.setEstado(Utils.toUpperCase(prueba.getEstado()));
				prueba.setPeticionario(Utils.toUpperCase(prueba.getPeticionario()));
				prueba.setPremium(Utils.toUpperCase(prueba.getPremium()));
				prueba.setResultado(Utils.toUpperCase(prueba.getResultado()));
				prueba.setSolucion(Utils.toUpperCase(prueba.getSolucion()));
				prueba.setTipo_servicio(Utils.toUpperCase(prueba.getTipo_servicio()));	
				prueba.setFichero(Utils.toUpperCase(prueba.getFichero()));	
				prueba.setProducto(Utils.toUpperCase(prueba.getProducto()));
				prueba.setReferencia(Utils.toUpperCase(prueba.getReferencia()));
				pruebaDao.createPrueba(prueba);
				result += prueba.getClient_name() + ", ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/*public String cleanPrueba(HttpServletRequest req,
			HttpServletResponse resp) throws InterruptedException, IOException {

		String result = "";
		try {
			PruebaDao pruebaDao = PruebaDao.getInstance();
			List<Prueba> pruebas = pruebaDao.getPruebasByAllParam("", "", "", "", "", "Producci&oacuten", "", "", "TODOS", "", 0);

			for (Prueba prueba : pruebas) {
				result += prueba.getClient_name() + ", ";
				if(prueba.getClient_name() != null) {
					pruebaDao.deletePrueba(prueba);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}*/
	
	public String updateImplementacion(HttpServletRequest req,
			HttpServletResponse resp, int page) throws InterruptedException, IOException {

		String result = "";
		try {
			ImplementacionDao implementacionDao = ImplementacionDao.getInstance();
			List<Implementacion> implementaciones = implementacionDao.getAllImplementacionesUpdateMayusPaged(page);
			result += implementaciones.size() + " -- ";

			for (Implementacion implementacion : implementaciones) {
				implementacion.setClient_name(Utils.toUpperCase(implementacion.getClient_name()));
				implementacion.setDetalle(Utils.toUpperCase(implementacion.getDetalle()));
				implementacion.setEstado(Utils.toUpperCase(implementacion.getEstado()));
				implementacion.setGestor_gcs(Utils.toUpperCase(implementacion.getGestor_gcs()));
				implementacion.setGestor_promocion(Utils.toUpperCase(implementacion.getGestor_promocion()));
				implementacion.setGestor_relacion(Utils.toUpperCase(implementacion.getGestor_relacion()));
				implementacion.setPais(Utils.toUpperCase(implementacion.getPais()));
				implementacion.setServicio_name(Utils.toUpperCase(implementacion.getServicio_name()));
				implementacion.setProducto(Utils.toUpperCase(implementacion.getProducto()));
				implementacion.setReferencia_global(Utils.toUpperCase(implementacion.getReferencia_global()));
				implementacion.setReferencia_local(Utils.toUpperCase(implementacion.getReferencia_local()));
				implementacion.setReferencia_externa(Utils.toUpperCase(implementacion.getReferencia_externa()));
				implementacion.setAsunto_ref_ext(Utils.toUpperCase(implementacion.getAsunto_ref_ext()));
				implementacion.setCuenta_ref_ext(Utils.toUpperCase(implementacion.getCuenta_ref_ext()));
				implementacion.setAdeudos_ref_ext(Utils.toUpperCase(implementacion.getAdeudos_ref_ext()));
				implementacion.setAcreedor_ref_ext(Utils.toUpperCase(implementacion.getAcreedor_ref_ext()));
				
				implementacionDao.createImplementacion(implementacion);
				result += implementacion.getClient_name() + ", ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean isThisDateValid(String dateToValidate, String dateFormat) {

		if (dateToValidate == null || "".equals(dateToValidate)) {
			return false;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setLenient(false);

		try {
			// if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);
			// System.out.println(date);

		} catch (ParseException e) {
			return false;
		}

		return true;
	}
}
