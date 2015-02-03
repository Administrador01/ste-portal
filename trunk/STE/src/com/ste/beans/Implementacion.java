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
	private String id_implementacion;
	
	@Persistent
	private boolean erased;
	
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
	private boolean normalizador;

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
	private String acreedor_ref_ext;
	
	@Persistent
	private String str_fech_contratacion;
	
	@Persistent
	private Date fech_contratacion;
	
	@Persistent
	private String str_fech_subida;
	
	@Persistent
	private String producto;
	
	@Persistent
	private Date fech_subida;
	

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
	
	public long getCliente_id() {
		return cliente_id;
	}

	public void setCliente_id(long id) {
		this.cliente_id = id;
	}
	
	public long getServicio_id() {
		return servicio_id;
	}

	public void setServicio_id(long id) {
		this.servicio_id = id;
	}
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getId_implementacion() {
		return id_implementacion;
	}

	public void setId_implementacion(String id) {
		this.id_implementacion = id;
	}
	
	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}
	
	public boolean getFirma_contrato(){
		return firma_contrato;
	}
	
	public void setFirma_contrato(boolean firma){
		this.firma_contrato=firma;
	}
	
	public boolean getNormalizador (){
		return normalizador;
	}
	
	public void setNormalizador(boolean normalizador){
		this.normalizador=normalizador;
	}
	
	public String getStr_fecha_alta() {
		return str_fecha_alta;
	}

	public void setStr_fech_alta(String fecha) {
		this.str_fecha_alta = fecha;
	}
	
	public String getProducto() {
		return producto;
	}

	public void setProducto(String fecha) {
		this.producto = fecha;
	}
	
	public Date getFecha_alta() {
		return fecha_alta;
	}

	public void setFecha_alta(Date fecha) {
		this.fecha_alta = fecha;
	}
	
	public String getReferencia_global() {
		return referencia_global;
	}

	public void setReferencia_global(String ref) {
		this.referencia_global = ref;
	}
	
	public String getReferencia_local() {
		return referencia_local;
	}

	public void setReferencia_local(String ref) {
		this.referencia_local = ref;
	}
	
	public String getGestor_gcs() {
		return gestor_gcs;
	}

	public void setGestor_gcs(String gcs) {
		this.gestor_gcs = gcs;
	}
	public String getGestor_promocion() {
		return gestor_promocion;
	}

	public void setGestor_promocion(String prom) {
		this.gestor_promocion = prom;
	}
	public String getGestor_relacion() {
		return gestor_relacion;
	}

	public void setGestor_relacion(String rel) {
		this.gestor_relacion = rel;
	}
	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String det) {
		this.detalle = det;
	}
	public String getReferencia_externa() {
		return referencia_externa;
	}

	public void setReferencia_externa(String ref) {
		this.referencia_externa = ref;
	}
	public String getAsunto_ref_ext() {
		return asunto_ref_ext;
	}

	public void setAsunto_ref_ext(String asunto) {
		this.asunto_ref_ext = asunto;
	}
	public String getAdeudos_ref_ext() {
		return adeudos_ref_ext;
	}
	
	public void setAcreedor_ref_ext(String acreedor) {
		this.acreedor_ref_ext = acreedor;
	}
	public String getAcreedor_ref_ext() {
		return acreedor_ref_ext;
	}

	public void setAdeudos_ref_ext(String adeudos) {
		this.adeudos_ref_ext = adeudos;
	}
	public String getCuenta_ref_ext() {
		return cuenta_ref_ext;
	}

	public void setCuenta_ref_ext(String cuenta) {
		this.cuenta_ref_ext = cuenta;
	}
	public String getStr_fech_contratacion() {
		return str_fech_contratacion;
	}

	public void setStr_fech_contratacion(String fecha) {
		this.str_fech_contratacion = fecha;
	}
	public Date getFech_contratacion() {
		return fech_contratacion;
	}

	public void setFech_contratacion(Date fecha) {
		this.fech_contratacion = fecha;
	}
	
	public String getStr_fech_subida() {
		return str_fech_subida;
	}

	public void setStr_fech_subida(String fecha) {
		this.str_fech_subida = fecha;
	}
	public Date getFech_subida() {
		return fech_subida;
	}

	public void setFech_subida(Date fecha) {
		this.fech_subida = fecha;
	}

	public boolean getErased() {
		return erased;
	}

	public void setErased(boolean erased) {
		this.erased = erased;
	}
	
}
