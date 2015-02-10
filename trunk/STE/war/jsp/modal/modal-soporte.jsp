<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script src="../components/checkbox/jquery.marmots-checkbox.js" type="text/javascript"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>




<div class="modal_ajax">
			<div class="">
				<h2>Editar soporte</h2>
				<hr />
			</div>
			
			<div class="form-holder">
				<form id="edit-soporte-form" name="edit-prueba-form" class="edit-form" action="/soporteServlet"
						method="POST" novalidate="novalidate">
						<div class="form-container">
								<div class="form-field-divider left">
					<div class="form-field">
						<span class="lbl">Identificador:</span>
						<input class="long readonly" type="text" readonly name="id_prueba" id="id_prueba" value="${soporte.id_soporte}">
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha inicio<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<input type="text" readonly="" value="${soporte.str_fecha_inicio}" size="16" class="datepicker fromTo" data-target-id='fecha_fin_modal' name="fecha_inicio" id="fecha_inicio_modal" required aria-required="true">
						</div>
					</div>
					


					<div class="form-field">
						<span class="lbl">Cliente<span class="required-asterisk">*</span>:</span>
						<div class="input">
						
							<select class="selectpicker selected" name="cliente" id="cliente-soporte-modal" data-live-search="true">
							
								<c:choose>
										<c:when test="${empty clientes}">
											<option value="default">No hay clientes</option>
											<option value="Ninguno" data-premium="No premium" data-segmento="Ninguno">Ninguno</option>
										</c:when>
										<c:otherwise>
											<option value="Ninguno" data-premium="No premium" data-segmento="Ninguno">Ninguno</option>
											<c:forEach items="${clientes}" var="t">	
												<option value="${t.nombre}" data-premium="${t.premium}" data-segmento="${t.tipo_cliente}"  data-clientid="${t.key.id}" ${t.nombre == soporte.cliente_name ? 'selected' : ''}>${t.nombre}</option>
											</c:forEach>
										</c:otherwise>
								</c:choose>
							</select>
						</div>
					</div>


					<input type="text" name="client_id" id="input-client-id-modal" value="${soporte.cliente_id}" hidden>

					<div class="form-field">
						<span class="lbl">Tipo cliente :</span>
						<input type="text" name="input-premium-soporte" id="input-premium-soporte-modal" value="${soporte.premium}" readonly>
					</div>
					<div class="form-field">						
						<span class="lbl">Segmento:</span>
						<input type="text" name="tipo_cliente" id="input-segmento-soporte-modal" value="${soporte.tipo_cliente}" readonly>
					</div>
			
					<div class="form-field">
						<span class="lbl">Peticionario:</span>
						<input type="text" class="" size="35" name="peticionario" id="" value="${soporte.peticionario}">
					</div>	
				</div>
				<div class="form-field-divider right">
					
					<%--
					<div class="form-field">
							<span class="lbl">Premium<span class="required-asterisk">*</span>:</span>
							<label class="ui-marmots-label-radio marmots-label-left on" for="radio_Si_modal">
								<input name="premium_modal" id="radio_Si_modal" ${soporte.premium == 'Si' ? 'checked' : ''} type="radio" value="Si" />Si
							</label>
							<label class="ui-marmots-label-radio marmots-label-left" for="radio_No_modal">
								<input name="premium_modal"  id="radio_No_modal"  ${soporte.premium == 'No' ? 'checked' : ''}  type="radio" value="No" />No ${soporte.premium}
							</label>
					</div>
					--%>
					
					
					<div class="form-field">
							<span class="lbl">Tipo<span class="required-asterisk">*</span>:</span>
							<label class="lbl ui-marmots-label-radio ${soporte.tipo_soporte == 'Incidencia' ? 'on' : ''}" for="radio_incidencia_modal">
								<input name="tipoModal" id="radio_incidencia_modal" ${soporte.tipo_soporte == 'Incidencia' ? 'checked' : ''} type="radio" value="Incidencia"/>Incidencia
							</label>
							<label class="lbl ui-marmots-label-radio marmots-label-left ${soporte.tipo_soporte == 'Consulta' ? 'on' : ''}" for="radio_consulta_modal">
								<input name="tipoModal"  id="radio_consulta_modal"  ${soporte.tipo_soporte == 'Consulta' ? 'checked' : ''} type="radio" value="Consulta"/>Consulta<br />
							</label>
					</div>

					
					<div class="form-field">
						<span class="lbl">Tipo de servicio<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<select id="tipo_servicio" class="selectpicker selected" name="tipo_servicio" >
								<c:forEach items="${tiposervicios}" var="servicio">
										<option value="${servicio.name}" ${soporte.tipo_servicio == servicio.name ? 'selected' : ''}>${servicio.name}</option>
								</c:forEach>	
							</select>
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha fin:</span>
						<div class="input">
							<input type="text" readonly="" value="${soporte.str_fecha_fin}" size="16" class="datepicker" name="fecha_fin" id="fecha_fin_modal">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Estado<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<select id="estado" class="selectpicker selected" name="estado" >							

								<c:forEach items="${estados}" var="estado">
										<option value="${estado.name}" ${soporte.estado == estado.name ? 'selected' : ''}>${estado.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Producto/canal<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<select id="producto_canal" class="selectpicker selected" name="producto_canal" >
							
								<c:forEach items="${productos}" var="producto">
										<option value="${producto.name}" ${soporte.producto_canal == producto.name ? 'selected' : ''}>${producto.name}</option>
								</c:forEach>

							</select>
						</div>
					</div>
					
					<div class="form-field" >
						<span class="lbl">Pa&iacutes<span class="required-asterisk">*</span>:</span>
						<div class="input">
						<select id="pais_imp" class="selectpicker selected" name="pais" required aria-required="true">	
								<c:forEach items="${paises}" var="pais">
									<option value="${pais.name}" ${soporte.pais == pais.name ? 'selected' : ''}>${pais.name}</option>
								</c:forEach>
						</select>
						</div>
					</div>
					
				</div>
				<div class="form-field-divider down">
					<div class="form-field detalles">
						<span class="">Descripci&oacuten:</span>
						<div class="input">
							<textarea name="detalles" maxlength="500" rows="1" cols="1">${soporte.detalles}</textarea>
						</div>
					</div>		
					
					
					<div class="form-field detalles">
						<span class="">Soluci&oacuten:</span>
						<div class="input">
							<textarea name="solucion" maxlength="500" rows="1" cols="1">${soporte.solucion}</textarea>
						</div>
					</div>	
							
				</div>					
							
				<div id="message_div_modal" class="message_div">
					<span id="span_message_modal"></span>
				</div>
						</div>
					</form>
				</div>
				<div class="modal-footer" id="modal-footer_submit">
					<button type="button" id="submit_edit_soporte_form" onclick="sendEditSoporte();">Aceptar</button>
					<button type="button" class="" data-dismiss="modal">Cancelar</button>
				</div>
			</div>	
			
<script>
$(document).ready(function(){
	$('input:checkbox, input:radio').checkbox();
	
	
	$('#cliente-soporte-modal').on('change', function() {
		var option = $(this).find(":selected");
		var pasfg = option.data('clientid');
		$('#input-premium-soporte-modal').val(option.data('premium'));
		$('#input-segmento-soporte-modal').val(option.data('segmento'));
		$('#input-client-id-modal').val(option.data('clientid'));
	})
	
	
	

});
</script>
