package com.ste.beans;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class ProductoCanal {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private String name;
	
	@Persistent
	private boolean pruebas;
	
	@Persistent
	private boolean soportes;
	
	@Persistent
	private boolean implementaciones;
	
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean getSoportes() {
		return soportes;
	}

	public void setSoportes(boolean visibility) {
		this.soportes = visibility;
	}
	
	public boolean getPruebas() {
		return pruebas;
	}

	public void setPruebas(boolean visibility) {
		this.pruebas = visibility;
	}
	
	public boolean getImplementaciones() {
		return implementaciones;
	}

	public void setImplementaciones(boolean visibility) {
		this.implementaciones = visibility;
	}
}
