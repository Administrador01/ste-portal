<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>




<div class="modal_ajax">
			<div class="">
				<h2>Editar prueba</h2>
				<hr />
			</div>
			
				<button type="button" class="go_pag1_modal">P&aacutegina 1</button>
				<button type="button" class="go_pag2_modal">P&aacutegina 2</button>
			
			<div class="form-holder" style="margin-top:4%;">
		<form id="edit-implementacion-form" name="edit-soporte-form" class="edit-form" action="/implementacionServlet"
			method="POST" novalidate="novalidate">
				

			<!--botones -->
			<div class="form-container">
			

				<div class="page1_imp" id='page1_imp_modal'>
				
				

					<div class="form-field" id="div_cliente_imp_modal">
						<span class="lbl">Cliente<span class="required-asterisk">*</span>:</span>
						<select class="selectpicker" name="cliente" id="cliente_imp_modal" data-live-search="true">
							<c:choose>
								<c:when test="${empty clientes}">
									<option value="default">No hay clientes</option>
								</c:when>
							<c:otherwise>
								<option value="default">Seleccionar</option>
								<c:forEach items="${clientes}" var="t">							
									<option value="${t.key.id}" data-segmento="${t.tipo_cliente}" ${t.key.id == cliente.key.id ? 'selected' : ''}>${t.nombre}</option>
								</c:forEach>
							</c:otherwise>
							</c:choose>
						</select>
					</div>
					

					<div class="form-field" id="div_producto_imp">
							<span class="lbl">Producto/canal<span class="required-asterisk">*</span>:</span>
								<select id="producto_imp" class="selectpicker selected" name="producto_imp" >
											<option value="Swift Fileact" ${implementacion.producto == 'Swift Fileact' ? 'selected' : ''}>Swift Fileact</option>
											<option value="Swift Fileact Antig" ${implementacion.producto == 'Swift Fileact Antig' ? 'selected' : ''}>Swift Fileact (antigua conexi&oacuten)</option>
											<option value="Swift FIN" ${implementacion.producto == 'Swift FIN' ? 'selected' : ''}>Swift FIN</option>	
											<option value="Swift FIN relay" ${implementacion.producto == 'Swift FIN relay' ? 'selected' : ''}>Swift FIN (Relay Bank)</option>
											<option value="Editran" ${implementacion.producto == 'Editran' ? 'selected' : ''}>EDITRAN</option>	
											<option value="BBVA Netcash" ${implementacion.producto == 'BBVA Netcash' ? 'selected' : ''}>BBVA Netcash</option>
											<option value="Edifact" ${implementacion.producto == 'Edifact' ? 'selected' : ''}>EDIFACT</option>
											<option value="Normalizador" ${implementacion.producto == 'Normalizador' ? 'selected' : ''}>Normalizador</option>
											<option value="Cashpool domestico" ${implementacion.producto == 'Cashpool domestico' ? 'selected' : ''}>Cashpool dom&eacutestico</option>
											<option value="Cashpool internacional" ${implementacion.producto == 'Cashpool internacional' ? 'selected' : ''}>Cashpool internacional</option>
											<option value="Factura integral" ${implementacion.producto == 'Factura integral' ? 'selected' : ''}>Factura integral</option>
								</select>
					</div>
					
					<div class="form-field" id="div_servicio_imp_modal">
						<span class="lbl">Servicio<span class="required-asterisk">*</span>:</span>
						<select class="selectpicker" name="servicio" id="servicio_imp_modal" data-live-search="true">
							<c:choose>
								<c:when test="${empty servicios}">
									<option value="default">No hay servicios</option>
								</c:when>
							<c:otherwise>
								<option value="default">Seleccionar</option>
								<c:forEach items="${servicios}" var="t">							
									<option value="${t.key.id}" data-nombre="${t.name}" data-tipo="${t.tipo}" ${t.key.id == servicio.key.id ? 'selected' : ''}>${t.name}</option>
								</c:forEach>
							</c:otherwise>
							</c:choose>
						</select>
						<input type="text" name="servicio_id" id="servicio-id-input" value="" hidden>
					</div>
					
					<div class="form-field" >
					<span class="lbl">Segmento:</span>
					<input type="text" style="background-color: #F3F3F3;" name="segmento" id="input-segmento-implementacion_modal" value="${cliente.tipo_cliente}" readonly>
					</div>
					
					<div class="form-field">
						<span class="lbl">Estado<span class="required-asterisk">*</span>:</span>
							<select id="estado_imp" class="selectpicker selected" name="estado_imp" >	
									<option value="Pendiente"  ${implementacion.estado == 'Pendiente' ? 'selected' : ''}>Pendiente</option>
									<option value="Analisis" ${implementacion.estado == 'Analisis' ? 'selected' : ''}>An&aacutelisis</option>
									<option value="Pruebas" ${implementacion.estado == 'Pruebas' ? 'selected' : ''}>Pruebas</option>	
									<option value="Penny test" ${implementacion.estado == 'Penny test' ? 'selected' : ''}>Pesnny test</option>
									<option value="Finalizado"${implementacion.estado == 'Finalizado' ? 'selected' : ''}>Finalizado</option>	
									<option value="Parado" ${implementacion.estado == 'Parado' ? 'selected' : ''}>Parado</option>
									<option value="Anulado" ${implementacion.estado == 'Anulado' ? 'selected' : ''}>Anulado</option>
							</select>
					</div>
					
					<div class="form-field" id="div_fecha_alta_imp">
					<span class="lbl">Fecha alta<span class="required-asterisk">*</span>:</span>
					
					<input type="text" readonly="" value="${implementacion.str_fecha_alta}" size="16" class="datepicker" name="fecha_alta" id="fecha_alta_modal">
					</div>
					
					<div class="form-field" id="div_pais_imp">
					<span class="lbl">Pais<span class="required-asterisk">*</span>:</span>
								<select id="pais_imp" class="selectpicker selected" name="pais" required aria-required="true">	
									<option value="Francia" ${implementacion.pais == 'Francia' ? 'selected' : ''}>Francia</option>
									<option value="Belgica" ${implementacion.pais == 'Belgica' ? 'selected' : ''}>Belgica</option>
									<option value="Italia" ${implementacion.pais == 'Italia' ? 'selected' : ''}>Italia</option>	
									<option value="Portugal" ${implementacion.pais == 'Portugal' ? 'selected' : ''}>Portugal</option>
									<option value="Espa&ntildea" ${implementacion.pais == 'Espa&ntildea' ? 'selected' : ''}>Espa&ntildea</option>	
									<option value="Reino Unido" ${implementacion.pais == '' ? 'selected' : ''}>Reino Unido</option>
							</select>
					</div>
					
					<div class="form-field">
					<span style="width:44%;"| class="lbl etiqueta">Normalizador<span class="required-asterisk">*</span>:</span>
							<label style="width:15%;" class="lbl radio ui-marmots-label-radio marmots-label-left ${implementacion.normalizador ? 'on' : ''} " for="radio_Si_Modal">
								<input name="normalizador_modal" id="radio_Si_Modal" type="radio" value="Si" ${implementacion.normalizador ? 'checked' : ''} />Si
							</label>

							<label style="width:15%;" class="lbl radio ui-marmots-label-radio marmots-label-left ${implementacion.normalizador ? '' : 'on'} " for="radio_No_Modal">
								<input name="normalizador_modal" id="radio_No_Modal" type="radio" value="No" ${implementacion.normalizador ? '' : 'checked'} />No
							</label>
					</div>
					<div class="form-field">
					<span class="lbl">Referencia global:</span>
					<input type="text" name="ref_glo" maxlength="11" value="${implementacion.referencia_global}">
					</div>
					<div class="form-field">
					<span class="lbl">Referencia local:</span>
					<input type="text" name="ref_loc" maxlength="18" value="${implementacion.referencia_local}">
					</div>

					<div class="form-field">
					<span class="lbl etiqueta" style="width:44%;">Firma contrato<span class="required-asterisk">*</span>:</span>
							<label style="width:15%;" class="lbl radio ui-marmots-label-radio marmots-label-left ${implementacion.firma_contrato ? 'on' : ''}" for="crack_Si_Modal">
								<input name="firma_modal" id="crack_Si_Modal" ${implementacion.firma_contrato ? 'checked' : ''} type="radio" value="Si"  />Si
							</label>

							<label style="width:15%;" class="lbl radio ui-marmots-label-radio marmots-label-left ${implementacion.firma_contrato ? '' : 'on'}" for="crack_No_Modal">
								<input name="firma_modal" id="crack_No_Modal" ${implementacion.firma_contrato ? '' : 'checked'}  type="radio" value="No"  />No
							</label>
					</div>
					
					<div class="form-field">
					<span class="lbl">Gestor GCS:</span>
					<input type="text" name="gestor_gcs" maxlength="25" value="${implementacion.gestor_gcs}">
					</div>
					
					<div class="form-field">
					<span class="lbl">Gestor promocion:</span>
					<input type="text" name="gestor_prom" maxlength="25" value="${implementacion.gestor_promocion}">
					</div>
					
					<div class="form-field">
					<span class="lbl">Gestor relaci&oacuten:</span>
					<input type="text" name="gestor_relacion" maxlength="25" value="${implementacion.gestor_relacion}">
					</div>
					
					<div class="form-field">
					<span class="lbl">Detalle:</span>
					<input type="text" name="detalle" maxlength="500" value="${implementacion.detalle}">
					</div>
						
			
					<div class="form-field">
					<span class="lbl">Referencia externa:</span>
					<input type="text" name="ref_ext" id="ref_ext_modal" maxlength="18" value="${implementacion.referencia_externa}">
					</div>
					
					<div class="form-field">
						
					</div>
					
					<h3 style="color:#FF8888" class="error hidden">Faltan por introducir campos</h3>

					
				</div>
				<div class="page2_imp hidden" id='page2_imp_modal'>

					
					<div class="form-field">
						<span class="lbl">Servicio:</span>
						<input type="text" style="background-color: #F3F3F3;" id="input-servicio-name-implementacion_modal" value="${servicio.name}" readonly>
					</div>
					
					<div class="form-field">
						<span class="lbl">Tipo servicio:</span>
						<input type="text" style="background-color: #F3F3F3;" id="input-servicio-tipo-implementacion_modal" value="${servicio.tipo}" readonly>
					</div>
					
					
					<div class="form-field">
					<span class="lbl">Fecha contrataci&oacuten:</span>
					<input type="text" readonly="" value="${implementacion.str_fech_contratacion}" size="16" class="datepicker" name="fecha_contrat" id="fecha_contrat_modal">
					</div>
					<div class="form-field">
					<span class="lbl">Fecha subida:</span>
					<input type="text" readonly="" value="${implementacion.str_fech_subida}" size="16" class="datepicker" name="fecha_subid" id="fecha_subid_modal">
					</div>
					
					<h3 style="color:#D1DCFF;">Entorno Integrado</h3>
					<hr style="border: 3px solid #D1DCFF; border-radius: 300px/10px; height: 0px;width: 80%;margin-left:10%;"/>
						<div class="form-field">
						<span class="lbl">Referencia externa:</span>
						<input type="text" style="background-color: #F3F3F3;" id="input-servicio-referencia_modal" value="${implementacion.referencia_externa}" readonly>
						</div>
						<div class="form-field">
						<span class="lbl">Asunto:</span>
						<input type="text" name="asunto" maxlength="30" value="${implementacion.asunto_ref_ext}">
						</div>
						<hr style=" width: 80%;margin-left:10%;"/>
		
						<div class="form-field">
						<span class="lbl grey">Contrato Adeudos:</span>
						<input type="text" name="contrat_adeud" maxlength="21" value="${implementacion.adeudos_ref_ext}">
						</div>
						<div class="form-field">
						<span class="lbl grey">ID Acreedor</span>
						<input type="text" name="id_acred" maxlength="16" value="${implementacion.acreedor_ref_ext}">
						</div>
						<div class="form-field">
						<span class="lbl grey">Cuenta de abono:</span>
						<input type="text" name="cuent_abon" maxlength="16" value="${implementacion.cuenta_ref_ext}">
						</div>
				

				</div>
				<div id="message_div_modal" class="message_div">
						<span id="span_message">La implementacion ha sido modificada de forma correcta.<br/>En breve volvemos a la página.</span>
					</div>
				</div>
					</form>
				</div>
				<br />
				<br />
				<div class="modal-footer" id="modal-footer_submit">
					<button type="button" id="submit_edit_implementacion_form" onclick="sendEditImplementacion();">Aceptar</button>
					<button type="button" class="" data-dismiss="modal">Cancelar</button>
				</div>
			</div>	
			
			

<script>	

	$('.go_pag2_modal').click(function(e){

			$('#page1_imp_modal').addClass('hidden');
			$('#page2_imp_modal').removeClass('hidden');
		
	})
	
	$('.go_pag1_modal').click(function(e){

			$('#page2_imp_modal').addClass('hidden');
			$('#page1_imp_modal').removeClass('hidden');
		
	})
	
	$('#submit_edit_implementacion_form').click(function(e){

			$('.go_pag1_modal').addClass('hidden');
			$('.go_pag2_modal').addClass('hidden');
		
	})
	
	$('#ref_ext_modal').on('change', function() {
		var campo = $(this).val();
		$('#input-servicio-referencia_modal').val(campo);
	})
	
	$('#cliente_imp_modal').on('change', function() {
		var option = $('#cliente_imp_modal').find(":selected");
		$('#input-segmento-implementacion_modal').val(option.data('segmento'));
	})
	$('#servicio_imp_modal').on('change', function() {
		var option = $(this).find(":selected");
		$('#input-servicio-name-implementacion_modal').val(option.data('nombre'));
		$('#input-servicio-tipo-implementacion_modal').val(option.data('tipo'));
	})
</script>