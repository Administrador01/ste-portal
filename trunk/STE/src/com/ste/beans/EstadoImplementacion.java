package com.ste.beans;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class EstadoImplementacion {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private String name;
	
	@Persistent
	private int orden;
	
	public Key getKey() {
		return key;
	}
//TODO aniadir color para poder modificarlo dinamicamente
	public void setKey(Key key) {
		this.key = key;
	}
	
	public String getName() {
		return name;
	}

	public void setNme(String name) {
		this.name = name;
	}
	
	public int getOrden() {
		return orden;
	}

	public void setOrden(int Orden) {
		this.orden = Orden;
	}
}