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
											<c:forEach items="${tiposervicios}" var="servicio">
												<option value="${servicio.name}" ${prueba.tipo_servicio == servicio.name ? 'selected' : ''}>${servicio.name}</option>
											</c:forEach>
											<option value="Cobros" >Cobros</option>		
										</select>
									</div>
								</div>
						
								
							</div>
							<div class="form-field-divider right">
			
			
								
								<div class="form-field">
									<span class="lbl">Fecha alta:</span>
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
											<c:forEach items="${estados}" var="estado">
												<option value="${estado.name}" ${prueba.estado == estado.name ? 'selected' : ''}>${estado.name}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								
								<div class="form-field">
									<span class="lbl">Referencia<span class="required-asterisk">*</span>:</span>
									<div class="input">
										<input class="long" type="text" name="referencia" id="referencia" value="${prueba.referencia}">
									</div>
								</div>
								
					<div class="form-field">
						<span class="lbl">Producto/canal<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<select id="producto_canal" class="selectpicker selected" name="producto_canal" >
							
								<c:forEach items="${productos}" var="producto">
										<option value="${producto.name}" ${prueba.producto == producto.name ? 'selected' : ''}>${producto.name}</option>
								</c:forEach>

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
		$('#input-premium-soporte-modal').val(option.data('premium'));
		$('#input-segmento-soporte-modal').val(option.data('segmento'));
		$('#input-client-id-modal').val(option.data('clientid'));
	})
	
	
	

});
</script>