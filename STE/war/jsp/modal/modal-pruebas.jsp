<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>




<div class="modal_ajax">
			<div class="">
			
			<button onclick="sendCloneTest();" id="new_prueba_form_modal" class="" type="button" style="float:right">Crear una nueva prueba con estos datos</button>
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
									
								<div class="form-field" >
									<span class="lbl">Cliente<span class="required-asterisk">*</span>:</span>
									<div class="input">
									<select class="selectpicker selected" name="cliente" id="cliente_pru_modal" data-live-search="true">
										<c:choose>
											<c:when test="${empty clientes}">
												<option value="default">No hay clientes</option>
											</c:when>
										<c:otherwise>
											<c:forEach items="${clientes}" var="t">							
												<option value="${t.key.id}" data-premium="${t.premium}" data-segmento="${t.tipo_cliente}" ${t.nombre == prueba.client_name ? 'selected' : ''}>${t.nombre}</option>
											</c:forEach>
										</c:otherwise>
										</c:choose>
									</select>
									</div>
								</div>
								
	
												
								<div class="form-field">
									<span class="lbl">Implementaci&oacuten<span class="required-asterisk">*</span>:</span>
									<div class="input">
										<select class="selectpicker selected" name="imp_id_mod" id="imp-pruebas-modal" data-live-search="true">
										<c:choose>											
											<c:when test="${empty implementaciones}">
												<option value="default">hay un error en la aplicacion</option>
											</c:when>
										<c:otherwise>
											<c:forEach items="${implementaciones}" var="imp">							
												<option value="${imp.key.id}" ${imp.key.id == prueba.imp_id ? 'selected' : ''}>${imp.id_implementacion}</option>
											</c:forEach>
										</c:otherwise>
										</c:choose>
										</select>
									</div>
								</div>
								
								<div class="form-field">
									<span class="lbl">Tipo cliente:</span>
									<input type="text" class="input-autorefillable" name="input-premium-prueba" id="input-premium-prueba-modal" value="" readonly>
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
						
								<div class="form-field">
									<span class="lbl">Fichero:</span>
									<input type="text" class=""  name="fichero" id="" value="${prueba.fichero}">
								</div>
								
								<div class="form-field">
									<span class="lbl">Peticionario:</span>
									<input type="text" class="" size="35" name="peticionario" id="" value="${prueba.peticionario}">
								</div>	

								
							<input type="text" class="hidden"  name="id_prueba_hid" value="${prueba.key.id}">	
							</div>
							<div class="form-field-divider right">
			
								<div class="form-field">
									<span class="lbl">Fecha inicio:</span>
									<div class="input">
										<input type="text" readonly="" value="${prueba.fecha_inicio_str}" size="16" class="datepicker" name="fecha_inicio" id="fecha_inicio_modal" required aria-required="true">
									</div>
								</div>
								
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
											<option value="INTEGRADO" ${prueba.entorno == 'INTEGRADO' ? 'selected' : ''} selected>INTEGRADO</option>									
											<option value="PRODUCCION" ${prueba.entorno == 'PRODUCCION' ? 'selected' : ''}>PRODUCCI&Oacute;N</option>	
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
									<span class="lbl">Referencia:</span>
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
								
													
								<div class="form-field">
									<span class="lbl">Resultado<span class="required-asterisk">*</span>:</span>
									<div class="input">
										<select id="resultado" class="selectpicker selected" name="resultado" >
											<option value="OK"${prueba.resultado == "OK" ? 'selected' : ''}>OK</option>
											<option value="KO"${prueba.resultado == "KO" ? 'selected' : ''}>KO</option>
											<option value=""${prueba.resultado == "" ? 'selected' : ''}></option>
											<option value="CANCELADA"${prueba.resultado == "CANCELADA" ? 'selected' : ''}>CANCELADA</option>
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

	var option = $('#cliente_pru_modal').find(":selected");

	$('#input-premium-prueba-modal').val(option.data('premium'));
	

	
	
	

});
</script>