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
	private String nombre_cliente;
	
	@Persistent
	private String tipo_servicio;
	
	@Persistent
	private String entorno;
	
	@Persistent
	private String producto;
	
	
	
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
	
	public String getNombre_cliente() {
		return nombre_cliente;
	}

	public void setNombre_cliente(String nombre_cliente) {
		this.nombre_cliente = nombre_cliente;
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
	
	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}
		
}
