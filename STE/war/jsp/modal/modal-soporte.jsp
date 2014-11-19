<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="modal_ajax">
			<div class="">
				<h2>Editar soporte</h2>
				<hr />
			</div>
			
			<div class="form-holder">
					<form id="edit-soporte-form" name="edit-soporte-form" class="edit-form" action="/soporteServlet"
						method="POST" novalidate="novalidate">
						<div class="form-container">
								<div class="form-field-divider left">
					<div class="form-field">
						<span class="lbl">Identificador:</span>
						<input class="long readonly" type="text" readonly name="id_prueba" id="id_prueba" value="${soporte.id_prueba}">
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
							<input class="long" type="text" value="${soporte.cliente_name}" name="cliente" id="cliente" required>

						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Tipo de servicio<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<select id="tipo_servicio" class="selectpicker selected" name="tipo_servicio" >
								<option value="cobros" ${soporte.tipo_servicio == 'cobros' ? 'selected' : ''}>Cobros</option>	
								<option value="pagos" ${soporte.tipo_servicio == 'pagos' ? 'selected' : ''}>Pagos</option>
								<option value="MT101" ${soporte.tipo_servicio == 'MT101' ? 'selected' : ''}>MT101</option>
								<option value="MT94x" ${soporte.tipo_servicio == 'MT94x' ? 'selected' : ''}>MT94x</option>
								<option value="cashpool" ${soporte.tipo_servicio == 'cashpool' ? 'selected' : ''}>Cashpool</option>
								<option value="factura integral" ${soporte.tipo_servicio == 'factura integral' ? 'selected' : ''}>Factura integral</option>
								<option value="otros" ${soporte.tipo_servicio == 'otros' ? 'selected' : ''}>Otros</option>	
							</select>
						</div>
					</div>
				</div>
				<div class="form-field-divider right">
					
					<div class="form-field">
							<span class="lbl">Premium<span class="required-asterisk">*</span>:</span>
							<label class="ui-marmots-label-radio marmots-label-left on" for="radio_Si_modal">
								<input name="premium_modal" id="radio_Si_modal" ${soporte.premium == 'Si' ? 'checked' : ''} type="radio" value="Si" />Si
							</label>
							<label class="ui-marmots-label-radio marmots-label-left" for="radio_No_modal">
								<input name="premium_modal"  id="radio_No_modal"  ${soporte.premium == 'No' ? 'checked' : ''}  type="radio" value="No" />No ${soporte.premium}
							</label>
					</div>
					
					<div class="form-field">
							<span class="lbl">Tipo<span class="required-asterisk">*</span>:</span>
							<label class="ui-marmots-label-radio ${soporte.tipo == 'Incidencia' ? 'on' : ''}" for="radio_incidencia">
								<input name="tipo" id="radio_incidencia" ${soporte.tipo == 'Incidencia' ? 'checked' : ''} type="radio" value="incidencia"/>Incidencia
							</label>
							<label class="ui-marmots-label-radio marmots-label-left ${soporte.tipo == 'Consulta' ? 'on' : ''}" for="radio_consulta">
								<input name="tipo"  id="radio_consulta"  ${soporte.tipo == 'Consulta' ? 'checked' : ''}type="radio" value="consulta"/>Consulta<br />${soporte.tipo}
							</label>
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
								<option value="Pendiente" ${soporte.estado == 'Pendiente' ? 'selected' : ''} selected>Pendiente</option>									
								<option value="EnCurso" ${soporte.estado == 'EnCurso' ? 'selected' : ''}>En curso</option>	
								<option value="Finalizado" ${soporte.estado == 'Finalizado' ? 'selected' : ''}>Finalizado</option>
							</select>
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Producto/canal<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<select id="producto_canal" class="selectpicker selected" name="producto_canal" >
								<option value="Swift Fileact" ${soporte.producto_canal == 'Swift Fileact' ? 'selected' : ''}>Swift Fileact</option>
								<option value="Swift Fileact Antig" ${soporte.producto_canal == 'Swift Fileact Antig' ? 'selected' : ''}>Swift Fileact (antigua conexi&oacuten)</option>
								<option value="Swift FIN" ${soporte.producto_canal == 'Swift FIN' ? 'selected' : ''}>Swift FIN</option>	
								<option value="Swift FIN relay" ${soporte.producto_canal == 'Swift FIN relay' ? 'selected' : ''}>Swift FIN (Relay Bank)</option>
								<option value="Editran" ${soporte.producto_canal == 'Editran' ? 'selected' : ''}>EDITRAN</option>	
								<option value="BBVA Netcash" ${soporte.producto_canal == 'BBVA Netcash' ? 'selected' : ''}>BBVA Netcash</option>
								<option value="Edifact" ${soporte.producto_canal == 'Edifact' ? 'selected' : ''}>EDIFACT</option>
								<option value="Normalizador" ${soporte.producto_canal == 'Normalizador' ? 'selected' : ''}>Normalizador</option>
								<option value="Cashpool domestico" ${soporte.producto_canal == 'Cashpool domestico' ? 'selected' : ''}>Cashpool dom&eacutestico</option>
								<option value="Cashpool internacional" ${soporte.producto_canal == 'Cashpool internacional' ? 'selected' : ''}>Cashpool internacional</option>
								<option value="factura integral" ${soporte.producto_canal == 'factura integral' ? 'selected' : ''}>Factura integral</option>
							</select>
						</div>
					</div>
					
				</div>
				<div class="form-field-divider down">
					<div class="form-field detalles">
						<span class="lbl">Descripci&oacuten:</span>
						<div class="input">
							<textarea name="detalles" maxlength="500" rows="1" cols="1" value="${soporte.detalles}"></textarea>
						</div>
					</div>		
					
					
					<div class="form-field detalles">
						<span class="lbl">Soluci&oacuten:</span>
						<div class="input">
							<textarea name="solucion" maxlength="500" rows="1" cols="1"></textarea>
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

});
</script>
