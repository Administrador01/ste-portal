package com.ste.beans;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

import java.util.Date;

@PersistenceCapable
public class Prueba {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

	@Persistent
	private String id_prueba;

	@Persistent
	private Date fecha_estado;
	
	@Persistent
	private String str_fecha_estado;
	
	@Persistent
	private String premium;

	@Persistent
	private String client_name;
	
	@Persistent
	private String tipo_servicio;
	
	@Persistent
	private String entorno;
	
	@Persistent
	private String estado;
	
	@Persistent
	private String producto;
	
	@Persistent
	private String referencia;
	
	@Persistent
	private String detalles;

	@Persistent
	private String solucion;
	
	@Persistent
	private String imp_id;
	
	@Persistent
	private String resultado;
	
	@Persistent
	private String peticionario;
	
	@Persistent
	private Date fecha_inicio;
	
	@Persistent
	private String fecha_inicio_str;
	
	@Persistent
	private String fichero;
	
	@Persistent
	private boolean erased;
	
	public Key getKey() {
		return key;
	}
	
	public void setKey(Key key) {
		this.key = key;
	}
	
	public String getId_prueba() {
		return id_prueba;
	}

	public void setId_prueba(String id_prueba) {
		this.id_prueba = id_prueba;
	}
	
	public Date getFecha_estado() {
		return fecha_estado;
	}

	public void setFecha_estado(Date fecha_estado) {
		this.fecha_estado = fecha_estado;
	}

	public String getStr_fecha_estado() {
		return str_fecha_estado;
	}

	public void setStr_fecha_estado(String str_fecha_estado) {
		this.str_fecha_estado = str_fecha_estado;
	}

	public String getPremium() {
		return premium;
	}

	public void setPremium(String premium) {
		this.premium = premium;
	}
	
	public String getTipo_servicio() {
		return tipo_servicio;
	}

	public void setTipo_servicio(String tipo_servicio) {
		this.tipo_servicio = tipo_servicio;
	}

	public String getEntorno() {
		return entorno;
	}

	public void setEntorno(String entorno) {
		this.entorno = entorno;
	}
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}
	
	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}
	
	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	
	public String getSolucion() {
		return solucion;
	}

	public void setSolucion(String solucion) {
		this.solucion = solucion;
	}

	public String getImp_id() {
		return imp_id;
	}

	public void setImp_id(String id_cliente) {
		this.imp_id = id_cliente;
	}
	
	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	
	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	
	public Date getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(Date fecha) {
		this.fecha_inicio = fecha;
	}
	
	public String getFecha_inicio_str() {
		return fecha_inicio_str;
	}

	public void setFecha_inicio_str(String fechaS) {
		this.fecha_inicio_str = fechaS;
	}
	
	public String getFichero() {
		return fichero;
	}

	public void setFichero(String fich) {
		this.fichero = fich;
	}
	
	public String getPeticionario() {
		return peticionario;
	}

	public void setPeticionario(String peticionario) {
		this.peticionario = peticionario;
	}
	
	public boolean getErased() {
		return erased;
	}

	public void setErased(boolean erased) {
		this.erased = erased;
	}
	
}
