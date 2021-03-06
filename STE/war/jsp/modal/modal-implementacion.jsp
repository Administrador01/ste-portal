<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script src="../components/checkbox/jquery.marmots-checkbox.js" type="text/javascript"></script>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>




<div class="modal_ajax">
			<div class="modalImplementacion" style="text-align: left;'">
			<button onclick="sendCloneImplementacion();" id="new_prueba_form_modal" class="" type="button" style="float:right">Crear una nueva implementacion con estos datos</button>
				<h2>Editar implementaci&oacuten ${implementacion.id_implementacion}</h2>
				<hr />
			</div><!--
			<div class="" style="text-align:right;'">
				<button type="button" class="go_pag1_modal">P&aacutegina 1</button>
				<button type="button" class="go_pag2_modal">P&aacutegina 2</button>
			</div>-->
			<div class="form-holder" style="margin-top:4%;margin-right:5%;">
		<form id="edit-implementacion-form" name="edit-soporte-form" class="edit-form" action="/implementacionServlet"
			method="POST" novalidate="novalidate">
				

			<!--botones -->
			<div class="form-container">
			

				<div class="page1_imp" id='page1_imp_modal'>
				
					<div class="form-field" id="div_producto_imp">
							<span class="lbl">Identificador:</span>
							<input disabled value="${implementacion.id_implementacion}">
					</div>
					
					<div class="form-field" id="div_producto_imp">
							<span class="lbl">Producto/canal<span class="required-asterisk">*</span>:</span>
							
								<select id="producto_imp" class="selectpicker selected" name="producto_imp" >
											<c:forEach items="${productos}" var="prod">
												<option value="${prod.name}" ${implementacion.producto == prod.name ? 'selected' : ''}>${prod.name}</option>
											</c:forEach>
								</select>
					</div>

					<div class="form-field" id="div_cliente_imp_modal">
						<span class="lbl">Cliente<span class="required-asterisk">*</span>:</span>
						<select class="selectpicker" name="cliente" id="cliente_imp_modal" data-live-search="true">
							<c:choose>
								<c:when test="${empty clientes}">
									<option value="default">No hay clientes</option>
								</c:when>
							<c:otherwise>
								<c:forEach items="${clientes}" var="t">							
									<option value="${t.key.id}" data-segmento="${t.tipo_cliente}" ${t.key.id == cliente.key.id ? 'selected' : ''}>${t.nombre}</option>
								</c:forEach>
							</c:otherwise>
							</c:choose>
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
					
					<!--
					<div class="form-field">
						<span class="lbl">Servicio:</span>
						<input type="text" style="background-color: #F3F3F3;" id="input-servicio-name-implementacion_modal" value="${servicio.name}" readonly>
					</div>
					-->
					
					
					<div class="form-field">
						<span class="lbl">Tipo servicio:</span>
						<input type="text" style="background-color: #F3F3F3;" id="input-servicio-tipo-implementacion_modal" value="${servicio.tipo}" readonly>
					</div>
					
					<div class="form-field" id="div_fecha_alta_imp">
					<span class="lbl">Fecha alta<span class="required-asterisk">*</span>:</span>
					
					<input type="text" readonly="" value="${implementacion.str_fecha_alta}" size="16" class="datepicker" name="fecha_alta" id="fecha_alta_modal">
					</div>
					
					<div class="form-field" id="div_pais_imp">
					<span class="lbl">Pa&iacutes<span class="required-asterisk">*</span>:</span>
								<select id="pais_imp" class="selectpicker selected" name="pais" required aria-required="true">	
									<c:forEach items="${paises}" var="pais">
										<option value="${pais.name}" ${implementacion.pais == pais.name ? 'selected' : ''}>${pais.name}</option>
									</c:forEach>
															
							</select>
					</div>
					
					<div class="form-field">
					<span class="lbl">Gestor GCS:</span>
					<input type="text" name="gestor_gcs" value="${implementacion.gestor_gcs}">
					</div>
					
					
					<div class="form-field">
					<span style="width:38%;" class="lbl">Normalizador<span class="required-asterisk">*</span>:</span>
							<label style="width:15%;" class="lbl ui-marmots-label-radio marmots-label-left ${implementacion.normalizador ? 'on' : ''} " for="radio_Si_Modal">
								<input name="normalizador_modal" id="radio_Si_Modal" type="radio" value="SI" ${implementacion.normalizador ? 'checked' : ''} />SI
							</label>

							<label style="width:15%;" class="lbl ui-marmots-label-radio marmots-label-left ${implementacion.normalizador ? '' : 'on'} " for="radio_No_Modal">
								<input name="normalizador_modal" id="radio_No_Modal" type="radio" value="NO" ${implementacion.normalizador ? '' : 'checked'} />NO
							</label>
					</div>
					
					
					<div class="form-field">
					<span class="lbl">Gestor promoci&oacuten:</span>
					<input type="text" name="gestor_prom" value="${implementacion.gestor_promocion}">
					</div>
					
					
					<div class="form-field">
					<span class="lbl">Referencia global:</span>
					<input type="text" name="ref_glo" value="${implementacion.referencia_global}">
					</div>
					
					<div class="form-field">
					<span class="lbl">Gestor relaci&oacuten:</span>
					<input type="text" name="gestor_relacion" value="${implementacion.gestor_relacion}">
					</div>
					
					<div class="form-field">
					<span class="lbl">Referencia local:</span>
					<input type="text" name="ref_loc" value="${implementacion.referencia_local}">
					</div>
					
					<div class="form-field">
					<span class="lbl" style="width:33%;">Firma contrato<span class="required-asterisk">*</span>:</span>
							<label style="width:15%;" class="lbl ui-marmots-label-radio marmots-label-left ${implementacion.firma_contrato ? 'on' : ''}" for="radio_Si_Firma_Modal">
								<input name="firma_modal" ${implementacion.firma_contrato ? 'checked' : ''} id="radio_Si_Firma_Modal"  type="radio" value="SI"  />SI
							</label>

							<label style="width:15%;" class="lbl radio ui-marmots-label-radio marmots-label-left ${implementacion.firma_contrato ? '' : 'on'}" for="radio_No_Firma_Modal">
								<input name="firma_modal" ${implementacion.firma_contrato ? '' : 'checked'} id="radio_No_Firma_Modal"   type="radio" value="NO"  />NO
							</label>
					</div>
					
					<div class="form-field">
					<span class="lbl">Fecha contrataci&oacuten:</span>
					<input type="text" readonly="" value="${implementacion.str_fech_contratacion}" size="16" class="datepicker" name="fecha_contrat" id="fecha_contrat_modal">
					</div>
					
					<div class="form-field">
						<span class="lbl">Estado<span class="required-asterisk">*</span>:</span>
							<select id="estado_imp" class="selectpicker selected" name="estado_imp" >	
							<c:forEach items="${estadosimp}" var="estadoimp">
								<option value="${estadoimp.name}" ${implementacion.estado == estadoimp.name ? 'selected' : ''}>${estadoimp.name}</option>
							</c:forEach>
							</select>
					</div>

					<div class="form-field">
					<span class="lbl">Fecha subida:</span>
					<input type="text" readonly="" value="${implementacion.str_fech_subida}" size="16" class="datepicker" name="fecha_subid" id="fecha_subid_modal">
					</div>
					
					<div style="margin-top:2%;width:99%;margin-bottom: 4%;" class="form-field">
					<span class="lbl">Detalle:</span>
					<textarea placeholder="Introduzca texto ..." cols="3" rows="3" maxlength="500" name="detalle" type="text" style="width:79.5%!important;">${implementacion.detalle}</textarea>
					</div>
					
					<h3 style="color:#D1DCFF;   margin-top: 5%;
    text-align: center;">Entorno Integrado</h3>
					<hr style="border: 3px solid #D1DCFF; border-radius: 300px/10px; height: 0px;width: 80%;margin-left:10%;"/>
					<div class="form-field">
					<span class="lbl">Referencia externa:</span>
					<input type="text" name="ref_ext" id="ref_ext_modal" value="${implementacion.referencia_externa}">
					</div>
						<div class="form-field">
						<span class="lbl">Asunto:</span>
						<input type="text" name="asunto" value="${implementacion.asunto_ref_ext}">
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
						<span id="span_message">La implementaci&oacuten ha sido modificada de forma correcta.<br/>En breve volvemos a la página.</span>
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