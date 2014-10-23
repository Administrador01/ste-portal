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
									<span class="lbl">Perfil<span class="required-asterisk">*</span>:</span>
									<select id="permiso_select_modal" class="selected selectpicker"
										name="permiso">
										<c:forEach items="${permisos}" var="permiso">		         	
											<option value="${permiso.value}" ${usuario.permiso == permiso.value ? 'selected' : ''}>${permiso.desc}</option>
										</c:forEach>
									</select>
								</div>
							
							</div>
							
							<div class="form-fieldset">
					<span class="fieldset-title">&Aacute;reas:</span>
					<fieldset class='radio-container-holder'>
						<div class="radio-container">
							
								<input type="checkbox" name='areas' value="Clientes" id="clientes_modal" <c:if test="${fn:contains(areas, 'Clientes')}"> checked</c:if>><label for="clientes_modal"><span></span>Clientes</label>
							
						</div>
						<div class="radio-container">
							<input type="checkbox" name='areas' value="Pruebas"
								id="pruebas_modal" <c:if test="${fn:contains(areas, 'Pruebas')}"> checked</c:if>><label for="pruebas_modal"><span></span>Pruebas</label>
						</div>
						<div class="radio-container">
							<input type="checkbox" name='areas'
								value="Servicios" id="servicios_modal" <c:if test="${fn:contains(areas, 'Servicios')}"> checked</c:if>>
								<label	for="servicios_modal" ><span></span>Servicios</label>
						</div>
						<div class="radio-container">
							<input type="checkbox" name='areas' value="Informes" id="informes_modal" <c:if test="${fn:contains(areas, 'Informes')}"> checked</c:if>>
							<label	for="informes_modal" ><span></span>Informes</label>
						</div>
						
						<div class="radio-container">
							<input type="checkbox" name='areas' value="Soporte"
								id="soporte_modal" <c:if test="${fn:contains(areas, 'Soporte')}"> checked</c:if>><label for="soporte_modal"><span></span>Soporte</label>
						</div>
						<div class="radio-container">
							<input type="checkbox" name='areas' value="Documentación"
								id="documentacion_modal" <c:if test="${fn:contains(areas, 'Documentación')}"> checked</c:if>><label for="documentacion_modal"><span></span>Documentación</label>
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