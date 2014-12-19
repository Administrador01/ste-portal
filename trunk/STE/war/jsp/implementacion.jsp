<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>



<div id="implementacion">

<h1>Implementacion</h1>
<span class="btn-atras" onclick="window.location.href='javascript:window.history.go(-1);'"></span>

<hr />

<div class="breadcrumbs">
		<span onclick="window.location.href='../../' ">Home</span> > <span> Implementaci&oacuten </span>
</div>

<div class="headButtonsBox">
	<button id="formButton">
		Nuevo<span class="user_span"></span>
	</button>


	<div class="form-holder">
		<form id="new-user-form" name="new-user-form" action="/implementacionServlet"
			method="POST" novalidate="novalidate">
				
				<button type="button" class="go_pag1">P&aacutegina 1</button>
				<button type="button" class="go_pag2">P&aacutegina 2</button>
			<!--botones -->
			<div class="form-container">
			
				<div class="page1_imp" id='page1_imp'>
				
				

					<div class="form-field" id="div_cliente_imp">
						<span class="lbl">Cliente<span class="required-asterisk">*</span>:</span>
						<select class="selectpicker" name="cliente" id="cliente_imp" data-live-search="true">
							<c:choose>
								<c:when test="${empty clientes}">
									<option value="default">No hay clientes</option>
								</c:when>
							<c:otherwise>
								<option value="default">Seleccionar</option>
								<c:forEach items="${clientes}" var="t">							
									<option value="${t.key.id}" data-segmento="${t.tipo_cliente}" data-clientid="${t.key.id}" >${t.nombre}</option>
								</c:forEach>
							</c:otherwise>
							</c:choose>
						</select>
						<input type="text" name="client_id" id="client-id-input" value="" hidden>
					</div>
					
					<h3 style="color:#FF8888;float:right;" class="error hidden">Faltan por introducir campos</h3>
					<div class="form-field" id="div_producto_imp">
							<span class="lbl">Producto/canal<span class="required-asterisk">*</span>:</span>
								<select id="producto_imp" class="selectpicker selected" name="producto_imp" >
									<option value="default">Seleccionar</option>	
									<option value="Swift Fileact">Swift Fileact</option>
									<option value="Swift Fileact Antig">Swift Fileact (antigua conexi&oacuten)</option>
									<option value="Swift FIN">Swift FIN</option>	
									<option value="Swift FIN relay">Swift FIN (Relay Bank)</option>
									<option value="Editran">EDITRAN</option>	
									<option value="BBVA Netcash">BBVA Netcash</option>
									<option value="Edifact">EDIFACT</option>
									<option value="Normalizador">Normalizador</option>
									<option value="Cashpool domestico">Cashpool dom&eacutestico</option>
									<option value="Cashpool internacional">Cashpool internacional</option>
									<option value="Factura integral">Factura integral</option>
								</select>
					</div>
					
					<div class="form-field" id="div_servicio_imp">
						<span class="lbl">Servicio<span class="required-asterisk">*</span>:</span>
						<select class="selectpicker" name="servicio" id="servicio_imp" data-live-search="true">
							<c:choose>
								<c:when test="${empty servicios}">
									<option value="default">No hay servicios</option>
								</c:when>
							<c:otherwise>
								<option value="default">Seleccionar</option>
								<c:forEach items="${servicios}" var="t">							
									<option value="${t.key.id}" data-nombre="${t.name}" data-tipo="${t.tipo}" >${t.name}</option>
								</c:forEach>
							</c:otherwise>
							</c:choose>
						</select>
						<input type="text" name="servicio_id" id="servicio-id-input" value="" hidden>
					</div>
					
					<div class="form-field" >
					<span class="lbl">Segmento:</span>
					<input type="text" class="autorrellenable" name="segmento" id="input-segmento-implementacion" value="" readonly>
					</div>
					
					<div class="form-field">
						<span class="lbl">Estado<span class="required-asterisk">*</span>:</span>
							<select id="estado_imp" class="selectpicker selected" name="producto_imp" >	
									<option value="Pendiente" selected>Pendiente</option>
									<option value="Analisis">An&aacutelisis</option>
									<option value="Pruebas">Pruebas</option>	
									<option value="Penny test">Pesnny test</option>
									<option value="Finalizado">Finalizado</option>	
									<option value="Parado">Parado</option>
									<option value="Anulado">Anulado</option>
							</select>
					</div>
					
					<div class="form-field" id="div_fecha_alta_imp">
					<span class="lbl">Fecha alta<span class="required-asterisk">*</span>:</span>
					<input type="text" size="16" class="datepicker" name="fecha_alta" id="fecha_alta_imp" required readonly>
					</div>
					
					<div class="form-field" id="div_pais_imp">
					<span class="lbl">Pais<span class="required-asterisk">*</span>:</span>
					<input type="text" name="pais" id="pais_imp" required aria-required="true">
					</div>
					
					<div class="form-field">
					<span class="lbl etiqueta">Normalizador<span class="required-asterisk">*</span>:</span>
							<label class="lbl radio ui-marmots-label-radio ">
								<input name="premium" id="radio_Si" type="radio" value="Si"/>Si
							</label>

							<label class="lbl ui-marmots-label-radio marmots-label-left on">
								<input name="premium" id="radio_No"  type="radio" value="No" checked/>No
							</label>
					</div>
					<div class="form-field">
					<span class="lbl">Referencia global:</span>
					<input type="text" name="ref_glo" maxlength="11">
					</div>
					<div class="form-field">
					<span class="lbl">Referencia local:</span>
					<input type="text" name="ref_loc" maxlength="18">
					</div>
					<div class="form-field">
					<span class="lbl etiqueta">Firma contrato<span class="required-asterisk">*</span>:</span>
							<label class="lbl radio ui-marmots-label-radio ">
								<input name="premium" id="radio_Si" type="radio" value="Si"/>Si
							</label>

							<label class="lbl ui-marmots-label-radio marmots-label-left on">
								<input name="premium" id="radio_No"  type="radio" value="No" checked/>No
							</label>
					</div>
					
					<div class="form-field">
					<span class="lbl">Gestor GCS:</span>
					<input type="text" name="gestor_gcs" maxlength="25">
					</div>
					
					<div class="form-field">
					<span class="lbl">Gestor promocion:</span>
					<input type="text" name="gestor_relacion" maxlength="25">
					</div>
					
					<div class="form-field">
					<span class="lbl">Gestor relaci&oacuten:</span>
					<input type="text" name="gestor_relacion" maxlength="25">
					</div>
					
					<div class="form-field">
					<span class="lbl">Detalle:</span>
					<input type="text" name="detalle" maxlength="500">
					</div>
						
			
					<div class="entor_integrad">
					<h4>Entorno Integrado</h4>
					<span class="lbl">Referencia externa:</span>
					<input type="text" name="ref_ext" id="ref_ext" maxlength="18">
					</div>
					
					<div class="form-field">
					<button type="button" id="siguiente" class="go_pag2">Siguiente </button>
					</div>
					
					<h3 style="color:#FF8888" class="error hidden">Faltan por introducir campos</h3>

					
				</div>
				<div class="page2_imp hidden" id='page2_imp'>

					
					<div class="form-field">
						<span class="lbl">Servicio:</span>
						<input type="text"class="autorrellenable" id="input-servicio-name-implementacion" value="" readonly>
					</div>
					
					<div class="form-field">
						<span class="lbl">Tipo servicio:</span>
						<input type="text" class="autorrellenable" id="input-servicio-tipo-implementacion" value="" readonly>
					</div>
					
					<div class="entor_integrad2">
					<h4>Entorno Integrado</h4>
						<div class="form-field">
						<span class="lbl">Referencia externa:</span>
						<input type="text" class="autorrellenable" id="input-servicio-referencia" value="" readonly>
						</div>
						<div class="form-field">
						<span class="lbl">Asunto:</span>
						<input type="text" maxlength="30">
						</div>
						<span class="lbl">SDD:</span>
						<hr />
						<div class="form-field">
						<span class="lbl grey">Contrato Adeudos:</span>
						<input type="text" maxlength="21">
						</div>
						<div class="form-field">
						<span class="lbl grey">ID Acreedor</span>
						<input type="text" maxlength="16">
						</div>
						<div class="form-field">
						<span class="lbl grey">Cuenta de abono:</span>
						<input type="text" maxlength="16">
						</div>
						
					</div>
					
					<div class="form-field">
					<span class="lbl">Fecha alta:</span>
					<input type="text" size="16" class="datepicker" name="fecha_contrat"readonly>
					</div>
					<div class="form-field">
					<span class="lbl">Fecha alta:</span>
					<input type="text" size="16" class="datepicker" name="fecha_subid" readonly>
					</div>
					<div class="form-field">
					<button type="submit"> Aceptar </button>
					</div>
				</div>
			<div id="message_div" class="message_div">
				<span id="span_message">La implementacion ha sido creado de forma correcta.<br/>En breve volvemos a la página.</span>
			</div>
			</div>
		</form>
	</div>
</div>


