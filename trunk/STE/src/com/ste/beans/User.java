package com.ste.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7953675697633829943L;

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

	@Persistent
	private String nombre;
	
	@Persistent
	private String apellido1;
	
	@Persistent
	private String apellido2;
	
	@Persistent
	private String email;
	
	@Persistent
	private String departamento;
	
	@Persistent
	private int permiso;
	
	public ArrayList<String> getAreas() {
		return areas;
	}

	public void setAreas(ArrayList<String> areas) {
		this.areas = areas;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Persistent 
	private String permisoStr;
	
	@Persistent
	private Boolean erased;
	
	@Persistent
	private ArrayList<String> areas;
	
	public User(String nombre, String apellido1, String apellido2,
			String email, int permiso, String permisoStr, String areas,String dto) {
		super();
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.email = email;
		this.permiso = permiso;
		this.permisoStr = permisoStr;
		this.departamento = dto;
	}
		
	public User() {
		super();
	};

	public Boolean getErased() {
		return erased;
	}

	public void setErased(Boolean erased) {
		this.erased = erased;
	}

	public int getPermiso() {
		return permiso;
	}

	public void setPermiso(int permiso) {
		this.permiso = permiso;
	}

	public Key getKey() {
		return key;
	}		

	public void setKey(Key key) {
		this.key = key;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPermisoStr() {
		return permisoStr;
	}

	public void setPermisoStr(String permisoStr) {
		this.permisoStr = permisoStr;
	}
	
	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	
	
	public Boolean isInArray (List<User> lo){
		
		Boolean result = false;
		
		for (User o:lo){
			if (this.equals(o)){
				result = true;
			}
		}		
		return result;
	}
	
	@Override
	public boolean equals(Object object_b) {
		 if (!(object_b instanceof User)) {
		        return false;
		    }

		    User object_a = (User) object_b;
		    
		 // Custom equality check here.
		    return this.email.equals(object_a.email);
		     
	}	
	
	
		
}
