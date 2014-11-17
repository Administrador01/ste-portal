<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="modal_ajax">
<div class="">
				<h2>Editar cliente</h2>
				<hr />
			</div>
			<div class="form-holder">
				<form id="edit-cliente-form" name="edit-cliente-form" class="edit-form" action="/clientServlet"
						method="POST" novalidate="novalidate">
				<div class="form-container">
					<div class="form-field-divider left">
						
						<div class="form-field">
							<span class="lbl">Identificador:</span>
							<input class="long readonly" type="text" readonly name="id_cliente" id="id_cliente" value="${cliente.id_cliente}">
						</div>
						
						<div class="form-field">
							<span class="lbl">Nombre cliente<span class="required-asterisk">*</span>:</span>
							<input class="long" type="text" required name="nombre_cliente" id="nombre_cliente" value="${cliente.nombre}">
						</div>
					
						<div class="form-field">
							<span class="lbl">Fecha alta<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" readonly="" value="${cliente.str_fecha_alta}" size="16" class="datepicker" name="fecha_alta" id="fecha_alta" required aria-required="true">
							</div>
						</div>
				
					</div>
					<div class="form-field-divider right">
						<div class="form-field"></div>
						<div class="form-field">
							<span class="lbl">Premium<span class="required-asterisk">*</span>:</span>
							
							
							
							
							<label class="ui-marmots-label-radio marmots-label-left ${cliente.premium == 'Si' ? 'on' : ''}" for="radio_Si_modal">
								<input name="premium_modal" id="radio_Si_modal" ${cliente.premium == 'Si' ? 'selected' : ''} type="radio" value="Si"/>Premium
							</label>
							<label class="ui-marmots-label-radio marmots-label-left ${cliente.premium == 'No' ? 'on' : ''}" for="radio_No_modal">
								<input name="premium_modal" ${cliente.premium == 'No' ? 'selected' : ''} id="radio_No_modal" value="No"  type="radio"/>No Premium
							</label>
						</div>
					
						 
						<div class="form-field">
							<span class="lbl">Tipo segmento<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select id="tipo_cliente" class="selectpicker selected" name="tipo_cliente" >
									<option value="CIB" ${cliente.tipo_cliente == 'CIB' ? 'selected' : ''}>CIB</option>									
									<option value="BEC" ${cliente.tipo_cliente == 'BEC' ? 'selected' : ''}>BEC</option>	
									<option value="Pymes" ${cliente.tipo_cliente == 'Pymes' ? 'selected' : ''}>Pymes</option>								
								</select>
							</div>
						</div>
						
					</div>
					<div id="message_div_modal" class="message_div">
						<span id="span_message">El cliente ha sido modificado de forma correcta.<br/>En breve volvemos a la página.</span>
					</div>
				</div>
					</form>
				</div>
				<div class="modal-footer" id="modal-footer_submit">
					<button type="button" id="submit_edit_cliente_form" onclick="sendEditClient();">Aceptar</button>
					<button type="button" class="" data-dismiss="modal">Cancelar</button>
				</div>
			</div>	
			
			
			<script>
			$(document).ready(function(){
				 $('input:checkbox, input:radio').checkbox();

				});
			</script>