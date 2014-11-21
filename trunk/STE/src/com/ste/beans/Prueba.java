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
	private Date fecha_inicio;
	
	@Persistent
	private String str_fecha_inicio;
	
	@Persistent
	private Date fecha_fin;
	
	@Persistent
	private String str_fecha_fin;

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
	
	public Date getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public String getStr_fecha_inicio() {
		return str_fecha_inicio;
	}

	public void setStr_fecha_inicio(String str_fecha_inicio) {
		this.str_fecha_inicio = str_fecha_inicio;
	}

	public Date getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(Date fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

	public String getStr_fecha_fin() {
		return str_fecha_fin;
	}

	public void setStr_fecha_fin(String str_fecha_fin) {
		this.str_fecha_fin = str_fecha_fin;
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
