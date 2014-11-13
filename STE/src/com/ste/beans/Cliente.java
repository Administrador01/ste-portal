package com.ste.beans;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Cliente {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private String id_prueba;

	@Persistent
	private String str_fecha_inicio;

	@Persistent
	private Date fecha_inicio; 	
	
	@Persistent
	private Date fecha_fin;
	
	@Persistent
	private String str_fecha_fin;
	
	@Persistent
	private String cliente;

	@Persistent
	private String estado;

	@Persistent
	private String tipo_servicio;
	
	@Persistent
	private String producto_canal;
	
	@Persistent
	private String detalles;

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

	public String getStr_fecha_inicio() {
		return str_fecha_inicio;
	}

	public void setStr_fecha_inicio(String str_fecha_inicio) {
		this.str_fecha_inicio = str_fecha_inicio;
	}

	public Date getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
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

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipo_servicio() {
		return tipo_servicio;
	}

	public void setTipo_servicio(String tipo_servicio) {
		this.tipo_servicio = tipo_servicio;
	}

	public String getProducto_canal() {
		return producto_canal;
	}

	public void setProducto_canal(String producto_canal) {
		this.producto_canal = producto_canal;
	}

	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}
	
	
}
