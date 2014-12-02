<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>




<div class="modal_ajax">
			<div class="">
				<h2>Editar prueba</h2>
				<hr />
			</div>
			
			<div class="form-holder">
					<form id="edit-prueba-form" name="edit-soporte-form" class="edit-form" action="/pruebaServlet"
						method="POST" novalidate="novalidate">
						
						<div class="form-container" id="form-container-mod-prueb">
						
							<div class="form-field-divider left">
							
								<div class="form-field">
									<span class="lbl">Identificador:</span>
									<input class="long readonly" type="text" readonly name="id_prueba" id="id_prueba" value="${prueba.id_prueba}">
								</div>
			
								
								<div class="form-field">
									<span class="lbl">Cliente<span class="required-asterisk">*</span>:</span>
									<div class="input">
									
										<select class="selectpicker selected" name="cliente" id="cliente-pruebas-modal" required data-live-search="true">
										
											<c:choose>
													<c:when test="${empty clientes}">
														<option value="default">No hay clientes</option>
													</c:when>
													<c:otherwise>
														<option value="default">Seleccionar</option>
														<c:forEach items="${clientes}" var="t">	
															<option value="${t.nombre}" data-premium="${t.premium}" data-segmento="${t.tipo_cliente}" data-clientid="${t.key.id}" ${t.nombre == prueba.nombre_cliente ? 'selected' : ''}>${t.nombre}</option>
														</c:forEach>
													</c:otherwise>
											</c:choose>
										</select>
									</div>
								</div>
								
								<input type="text" name="client_id_mod" id="input-client-id-modal" value="" hidden>
								
								
								<div class="form-field">
									<span class="lbl">Tipo cliente:</span>
									<input type="text" name="input-premium-soporte" id="input-premium-soporte-modal" value="${prueba.premium}" readonly>
								</div>
								
								<div class="form-field">
									<span class="lbl">Tipo de servicio<span class="required-asterisk">*</span>:</span>
									<div class="input">
										<select id="tipo_servicio" class="selectpicker selected" name="tipo_servicio" >
											<option value="Cobros" ${prueba.tipo_servicio == 'Cobros' ? 'selected' : ''}>Cobros</option>	
											<option value="Pagos" ${prueba.tipo_servicio == 'Pagos' ? 'selected' : ''}>Pagos</option>
											<option value="MT101" ${prueba.tipo_servicio == 'MT101' ? 'selected' : ''}>MT101</option>
											<option value="MT94x" ${prueba.tipo_servicio == 'MT94x' ? 'selected' : ''}>MT94x</option>
											<option value="Cashpool" ${prueba.tipo_servicio == 'Cashpool' ? 'selected' : ''}>Cashpool</option>
											<option value="Factura integral" ${prueba.tipo_servicio == 'Factura integral' ? 'selected' : ''}>Factura integral</option>
											<option value="Otros" ${prueba.tipo_servicio == 'Otros' ? 'selected' : ''}>Otros</option>	
										</select>
									</div>
								</div>
						
								
							</div>
							<div class="form-field-divider right">
			
			
								
								<div class="form-field">
									<span class="lbl">Fecha estado:</span>
									<div class="input">
										<input type="text" readonly="" value="${prueba.str_fecha_estado}" size="16" class="datepicker" name="fecha_estado" id="fecha_estado_modal">
									</div>
								</div>
								
								<div class="form-field">
									<span class="lbl">Entorno<span class="required-asterisk">*</span>:</span>
									<div class="input">
										<select id="estado" class="selectpicker selected" name="entorno" >							
											<option value="Preproduccion" ${prueba.entorno == 'Preproduccion' ? 'selected' : ''} selected>Pre producci&oacuten</option>									
											<option value="Produccion" ${prueba.entorno == 'Produccion' ? 'selected' : ''}>Producci&oacuten</option>	
										</select>
									</div>
								</div>

								<div class="form-field">
									<span class="lbl">Estado<span class="required-asterisk">*</span>:</span>
									<div class="input">
										<select id="estado" class="selectpicker selected" name="estado" >							
											<option value="Pendiente" ${prueba.estado == 'Pendiente' ? 'selected' : ''} selected>Pendiente</option>									
											<option value="En curso" ${prueba.estado == 'En curso' ? 'selected' : ''}>En curso</option>	
											<option value="Finalizado" ${prueba.estado == 'Finalizado' ? 'selected' : ''}>Finalizado</option>
										</select>
									</div>
								</div>
								
								<div class="form-field">
									<span class="lbl">Producto/canal<span class="required-asterisk">*</span>:</span>
									<div class="input">
										<select id="producto_canal" class="selectpicker selected" name="producto_canal" >
											<option value="Swift Fileact" ${prueba.producto == 'Swift Fileact' ? 'selected' : ''}>Swift Fileact</option>
											<option value="Swift Fileact Antig" ${prueba.producto == 'Swift Fileact Antig' ? 'selected' : ''}>Swift Fileact (antigua conexi&oacuten)</option>
											<option value="Swift FIN" ${prueba.producto == 'Swift FIN' ? 'selected' : ''}>Swift FIN</option>	
											<option value="Swift FIN relay" ${prueba.producto == 'Swift FIN relay' ? 'selected' : ''}>Swift FIN (Relay Bank)</option>
											<option value="Editran" ${prueba.producto == 'Editran' ? 'selected' : ''}>EDITRAN</option>	
											<option value="BBVA Netcash" ${prueba.producto == 'BBVA Netcash' ? 'selected' : ''}>BBVA Netcash</option>
											<option value="Edifact" ${prueba.producto == 'Edifact' ? 'selected' : ''}>EDIFACT</option>
											<option value="Normalizador" ${prueba.producto == 'Normalizador' ? 'selected' : ''}>Normalizador</option>
											<option value="Cashpool domestico" ${prueba.producto == 'Cashpool domestico' ? 'selected' : ''}>Cashpool dom&eacutestico</option>
											<option value="Cashpool internacional" ${prueba.producto == 'Cashpool internacional' ? 'selected' : ''}>Cashpool internacional</option>
											<option value="factura integral" ${prueba.producto == 'Factura integral' ? 'selected' : ''}>Factura integral</option>
										</select>
									</div>
								</div>
								
							</div>
							
							<div class="form-field-divider down">
								<div class="form-field detalles">
									<span class="lbl">Descripci&oacuten:</span>
									<div class="input editDetalles">
										<textarea name="detalles" maxlength="500" rows="1" cols="1">${prueba.detalles}</textarea>
									</div>
								</div>
								<div class="form-field detalles">
									<span class="lbl">Soluci&oacuten:</span>
									<div class="input editDetalles">
										<textarea name="solucion" maxlength="500" rows="1" cols="1">${prueba.solucion}</textarea>
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
					<button type="button" id="submit_edit_prueba_form" onclick="sendEditPrueba();">Aceptar</button>
					<button type="button" class="" data-dismiss="modal">Cancelar</button>
				</div>
			</div>	

<script>
$(document).ready(function(){

	
	$('#cliente-pruebas-modal').on('change', function() {
		var option = $(this).find(":selected");
		console.log(option.data('clientid'));
		$('#input-premium-soporte-modal').val(option.data('premium'));
		$('#input-segmento-soporte-modal').val(option.data('segmento'));
		$('#input-client-id-modal').val(option.data('clientid'));
	})
	
	
	

});
</script>