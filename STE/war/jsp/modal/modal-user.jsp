<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="modal_ajax">
<div class="">
				<h2>Editar usuario</h2>
				<hr />
			</div>
			<div class="form-holder">
					<form id="edit-user-form" name="edit-user-form" action="/usersServlet"
						method="POST" novalidate="novalidate">
						<div class="form-container">
							<div class="form-field-divider left">
								<div class="form-field">
									<span for="nombre" class="lbl">Nombre<span class="required-asterisk">*</span>:</span><input class="" type="text"
										name="nombre" id="nombre_modal" value="${usuario.nombre}" required aria-required="true">
								</div>
								
								<div class="form-field">
									<span class="lbl">Apellido 2:</span><input value="${usuario.apellido2}" class="" type="text" name="ap2"
										id="ap2_modal">
								</div>
								
								<div class="form-field">
									<span class="lbl">Departamento<span class="required-asterisk">*</span>:</span>
									<select id="dto_select_modal"
										class=" selected selectpicker" name="dto">
										<c:forEach items="${departamentos}" var="departamento">		         	
											<option value="${departamento.value}" ${usuario.departamento == departamento.value ? 'selected' : ''}>${departamento.desc}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-field-divider right">
								
								<div class="form-field">
									<span  class="lbl">Apellido 1<span class="required-asterisk">*</span>:</span><input value="${usuario.apellido1}" class="" type="text" name="ap1"
										id="ap1_modal" required aria-required="true">
								</div>
								
								<div class="form-field">
									<span class="lbl">E-mail<span class="required-asterisk">*</span>:</span><input value="${usuario.email}" class="email no_message_error" type="text"
										name="email" id="email_modal" required aria-required="true"
										data-type="email">
								</div>
								
								<div class="form-field">
									<span class="lbl">Perfil App<span class="required-asterisk">*</span>:</span>
									<select id="permiso_select_modal" class="selected selectpicker"
										name="permiso">
										<c:forEach items="${permisos}" var="permiso">		         	
											<option value="${permiso.value}" ${usuario.permiso == permiso.value ? 'selected' : ''}>${permiso.desc}</option>
										</c:forEach>
									</select>
								</div>
							
							</div>
					
					
					<div class="form-fieldset">
					<span class="fieldset-title">Permisos por área:</span>
					<br>
					<fieldset class='radio-container-holder'>
						<div class="form-field">
							<span>Clientes:</span><select id="area_cliente" class="long selectpicker" name="area_cliente">
								<option value="1" ${usuario.permiso_clientes == 1 ? 'selected' : ''}>Propietario</option>	
								<option value="2" ${usuario.permiso_clientes == 2 ? 'selected' : ''}>Editar</option>
								<option value="3" ${usuario.permiso_clientes == 3 ? 'selected' : ''}>Ver</option>															
							</select>
						</div>
						
						<div class="form-field">
							<span>Pruebas:</span><select id="area_pruebas" class="long selectpicker" name="area_pruebas">
								<option value="1" ${usuario.permiso_pruebas == 1 ? 'selected' : ''}>Propietario</option>	
								<option value="2" ${usuario.permiso_pruebas == 2 ? 'selected' : ''}>Editar</option>
								<option value="3" ${usuario.permiso_pruebas == 3 ? 'selected' : ''}>Ver</option>								
							</select>
						</div>
						
						<div class="form-field">
							<span>Servicios:</span><select id="area_servicios" class="long selectpicker" name="area_servicios">
								<option value="1" ${usuario.permiso_servicios == 1 ? 'selected' : ''}>Propietario</option>	
								<option value="2" ${usuario.permiso_servicios == 2 ? 'selected' : ''}>Editar</option>
								<option value="3" ${usuario.permiso_servicios == 3 ? 'selected' : ''}>Ver</option>							
							</select>
						</div>
						
						<div class="form-field">
							<span>Informes:</span><select id="area_informes" class="long selectpicker" name="area_informes">
								<option value="1" ${usuario.permiso_informes == 1 ? 'selected' : ''}>Propietario</option>	
								<option value="2" ${usuario.permiso_informes == 2 ? 'selected' : ''}>Editar</option>
								<option value="3" ${usuario.permiso_informes == 3 ? 'selected' : ''}>Ver</option>							
							</select>
						</div>
						
						<div class="form-field">
							<span>Soporte:</span><select id="area_soporte" class="long selectpicker" name="area_soporte">
								<option value="1" ${usuario.permiso_soporte == 1 ? 'selected' : ''}>Propietario</option>	
								<option value="2" ${usuario.permiso_soporte == 2 ? 'selected' : ''}>Editar</option>
								<option value="3" ${usuario.permiso_soporte == 3 ? 'selected' : ''}>Ver</option>							
							</select>
						</div>
						
						<div class="form-field">
							<span>Documentación:</span><select id="area_documentacion" class="long selectpicker" name="area_documentacion">
								<option value="1" ${usuario.permiso_documentacion == 1 ? 'selected' : ''}>Propietario</option>	
								<option value="2" ${usuario.permiso_documentacion == 2 ? 'selected' : ''}>Editar</option>
								<option value="3" ${usuario.permiso_documentacion == 3 ? 'selected' : ''}>Ver</option>							
							</select>
						</div>
						
					</fieldset>
				</div>
							
							<div id="message_div_modal" class="message_div">
								<span id="span_message_modal"></span>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer" id="modal-footer_submit">
					<button type="button" id="submit_edit_user_form" onclick="sendEditUser();">Aceptar</button>
					<button type="button" class="" data-dismiss="modal">Cancelar</button>
				</div>
			</div>	
		
		
<div class="ajax_loader" id="ajax_loader">
	<img src="../../img/ajax-loader.gif" />
</div>