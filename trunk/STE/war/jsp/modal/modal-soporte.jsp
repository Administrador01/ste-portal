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
						<span class="lbl">Id. Prueba:</span>
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
							
								<option value="CIB" ${soporte.tipo_servicio == 'CIB' ? 'selected' : ''} >CIB</option>	
								<option value="BEC" ${soporte.tipo_servicio == 'BEC' ? 'selected' : ''}>BEC</option>	
							</select>
						</div>
					</div>
				</div>
				<div class="form-field-divider right">
					
					<div class="form-field"></div>
					
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
								
								<option value="Pendiente" ${soporte.estado == 'Pendiente' ? 'selected' : ''}>Pendiente</option>									
								<option value="Producción" ${soporte.estado == 'Producción' ? 'selected' : ''}>Producción</option>	
								<option value="Eliminado" ${soporte.estado == 'Eliminado' ? 'selected' : ''}>Eliminado</option>	
								<option value="Finalizado" ${soporte.estado == 'Finalizado' ? 'selected' : ''}>Finalizado</option>	
								<option value="Parado" ${soporte.estado == 'Parado' ? 'selected' : ''}>Parado</option>	
															
							</select>
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Producto/canal<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<select id="producto_canal" class="selectpicker selected" name="producto_canal" >
								<option value="Swift FIN" ${soporte.producto_canal == 'SwiftFIN' ? 'selected' : ''}>Swift FIN</option>	
								<option value="Swift Fileact" ${soporte.producto_canal == 'SwiftFileact' ? 'selected' : ''}>Swift Fileact</option>	
								<option value="Editran" ${soporte.producto_canal == 'Editran' ? 'selected' : ''}>Editran</option>	
								<option value="BBVA Netcash" ${soporte.producto_canal == 'BBVA Netcash' ? 'selected' : ''}>BBVA Netcash</option>	
															
							</select>
						</div>
					</div>
					
				</div>
				<div class="form-field-divider down">
					<div class="form-field detalles">
						<span class="lbl">Detalles:</span>
						<div class="input">
							<textarea name="detalles" maxlength="500" rows="1" cols="1" value="${soporte.detalles}"></textarea>
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
