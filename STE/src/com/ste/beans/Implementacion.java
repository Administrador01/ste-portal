package com.ste.beans;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Implementacion {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private Date fecha_alta;
	
	@Persistent
	private String str_fecha_alta;
	
	@Persistent
	private long cliente_id;
	@Persistent
	private long servicio_id;
	
	@Persistent
	private String estado;
	
	@Persistent
	private String pais;
	
	@Persistent
	private String referencia_global;
	
	@Persistent
	private String referencia_local;
	
	@Persistent
	private boolean firma_contrato;

	@Persistent
	private String gestor_gcs;
	
	@Persistent
	private String gestor_promocion;
	
	@Persistent
	private String gestor_relacion;
	
	@Persistent
	private String detalle;
	
	@Persistent
	private String referencia_externa;
	
	@Persistent
	private String asunto_ref_ext;
	
	@Persistent
	private String adeudos_ref_ext;
	
	@Persistent
	private String cuenta_ref_ext;
	
	@Persistent
	private String str_fech_contratacion;
	
	@Persistent
	private Date fech_contratacion;
	
	@Persistent
	private String str_fech_subida;
	
	@Persistent
	private Date fech_subida;
	

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
	
	public long getClienteId() {
		return cliente_id;
	}

	public void setClienteId(long id) {
		this.cliente_id = id;
	}
	
	public long getServicioId() {
		return servicio_id;
	}

	public void setServicioId(long id) {
		this.servicio_id = id;
	}
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}
	
	public String getStrFechAlta() {
		return str_fecha_alta;
	}

	public void setStrFechAlta(String fecha) {
		this.str_fecha_alta = fecha;
	}
	

	
	public Date getFechAlta() {
		return fecha_alta;
	}

	public void setFechAlta(Date fecha) {
		this.fecha_alta = fecha;
	}
	
	public String getReferenciaGlobal() {
		return referencia_global;
	}

	public void setReferenciaGlobal(String ref) {
		this.referencia_global = ref;
	}
	
	public String getReferenciaLocal() {
		return referencia_local;
	}

	public void setReferenciaLocal(String ref) {
		this.referencia_local = ref;
	}
	
	public String getGestorGcs() {
		return gestor_gcs;
	}

	public void setGestorGcs(String gcs) {
		this.gestor_gcs = gcs;
	}
	public String getGestorPromocion() {
		return gestor_promocion;
	}

	public void setGestorPromocion(String prom) {
		this.gestor_promocion = prom;
	}
	public String getGestorRelacion() {
		return gestor_relacion;
	}

	public void setGestorRelacion(String rel) {
		this.gestor_relacion = rel;
	}
	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String det) {
		this.detalle = det;
	}
	public String getReferenciaExterna() {
		return referencia_externa;
	}

	public void setRefExt(String ref) {
		this.referencia_externa = ref;
	}
	public String getAsuntoReferenciaExt() {
		return asunto_ref_ext;
	}

	public void setAsuntoRefExt(String asunto) {
		this.asunto_ref_ext = asunto;
	}
	public String getAdeudosRefExt() {
		return adeudos_ref_ext;
	}

	public void setAdeudosRefExt(String adeudos) {
		this.adeudos_ref_ext = adeudos;
	}
	public String getCuentaRefExt() {
		return cuenta_ref_ext;
	}

	public void setCuentaRefExt(String cuenta) {
		this.cuenta_ref_ext = cuenta;
	}
	public String getStrFechContratacion() {
		return str_fech_contratacion;
	}

	public void setStrFechContratacion(String fecha) {
		this.str_fech_contratacion = fecha;
	}
	public Date getFechContratacion() {
		return fech_contratacion;
	}

	public void setFechContratacion(Date fecha) {
		this.fech_contratacion = fecha;
	}
	
	public String getStrFechSubida() {
		return str_fech_subida;
	}

	public void setStrFechSubida(String fecha) {
		this.str_fech_subida = fecha;
	}
	public Date getFechSubida() {
		return fech_subida;
	}

	public void setFechSubida(Date fecha) {
		this.fech_subida = fecha;
	}

}
