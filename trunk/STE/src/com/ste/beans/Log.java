package com.ste.beans;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Log {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

	@Persistent
	private Date fecha;
	
	@Persistent
	private String fecha_str;

	@Persistent
	private String usuario;

	@Persistent
	private String usuario_mail;
	
	@Persistent
	private String accion;
	
	@Persistent
	private String entidad;
	
	@Persistent
	private String nombre_entidad;
	
	public String getFecha_str() {
		return fecha_str;
	}

	public void setFecha_str(String fecha_str) {
		this.fecha_str = fecha_str;
	}

	public String getNombre_entidad() {
		return nombre_entidad;
	}

	public void setNombre_entidad(String nombre_entidad) {
		this.nombre_entidad = nombre_entidad;
	}

	public Log() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUsuario_mail() {
		return usuario_mail;
	}

	public void setUsuario_mail(String usuario_mail) {
		this.usuario_mail = usuario_mail;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	
}
