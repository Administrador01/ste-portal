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
	private String id_cliente;
	
	@Persistent
	private Date fecha_alta;
	
	@Persistent
	private String str_fecha_alta;
	
	@Persistent
	private String premium;
	
	@Persistent
	private String tipo_cliente;
	
	@Persistent
	private String nombre;
	
	@Persistent
	private boolean erased;

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Boolean isErased() {
		return erased;
	}

	public void setErased(Boolean erased) {
		this.erased = erased;
	}
	
	public String getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(String id_cliente) {
		this.id_cliente = id_cliente;
	}

	public Date getFecha_alta() {
		return fecha_alta;
	}

	public void setFecha_alta(Date fecha_alta) {
		this.fecha_alta = fecha_alta;
	}

	public String getPremium() {
		return premium;
	}

	public void setPremium(String premium) {
		this.premium = premium;
	}

	public String getStr_fecha_alta() {
		return str_fecha_alta;
	}

	public void setStr_fecha_alta(String str_fecha_alta) {
		this.str_fecha_alta = str_fecha_alta;
	}

	public String getTipo_cliente() {
		return tipo_cliente;
	}

	public void setTipo_cliente(String tipo_cliente) {
		this.tipo_cliente = tipo_cliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	@Override
	public String toString() {
		return "\r\nNombre Cliente= " + nombre 
				+ "\r\nId="+ id_cliente 
				+ "\r\nFecha alta Cliente= " + str_fecha_alta
				+ "\r\nfecha_alta_cliente= " + fecha_alta+ "\r\nTipo= " + tipo_cliente
				+ "\r\nPremium= " +premium
				+ "\r\nErased= " + erased ;
	}	
}