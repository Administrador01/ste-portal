<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div class="alta_usuario">



<h1>Gesti&oacute;n de usuarios</h1>
<span class="btn-atras" onclick="window.location.href='../../'"></span>

<hr />

<div class="breadcrumbs">
		<span onclick="window.location.href='../../' ">Home</span> > <span> Gestión de usuarios </span>
	</div>

<div class="headButtonsBox">
	<button id="formButton">
		Nuevo Usuario<span class="user_span"></span>
	</button>
	<button id="excel_btn" onclick="window.location.href='../../usersServlet?accion=xls'">
		Descargar Tabla<span class="excel_span"></span>
	</button>


	<div class="form-holder">
		<form id="new-user-form" name="new-user-form" action="/usersServlet"
			method="POST" novalidate="novalidate">
			<div class="form-container">
				<div class="form-field">
					<span for="nombre">Nombre<span class="required-asterisk">*</span>:</span><input class="long" type="text"
						name="nombre" id="nombre" required aria-required="true">
				</div>
				<div class="form-field">
					<span>Apellido 1<span class="required-asterisk">*</span>:</span><input class="long" type="text" name="ap1"
						id="ap1" required aria-required="true">
				</div>
				<div class="form-field">
					<span>Apellido 2:</span><input class="long" type="text" name="ap2"
						id="ap2">
				</div>
				<div class="form-field">
					<span>E-mail<span class="required-asterisk">*</span>:</span><input class="long email" type="text"
						name="email" id="email" required aria-required="true"
						data-type="email">
				</div>
				<div class="form-field">
					<span>Departamento<span class="required-asterisk">*</span>:</span><select id="dto_select"
						class="long selected selectpicker" name="dto" required>
						<option selected value="default">Seleccionar</option>
						<c:forEach items="${departamentos}" var="departamento">		         	
							<option value="${departamento.value}">${departamento.desc}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-field">
					<span>Perfil App<span class="required-asterisk">*</span>:</span><select id="permiso_select" class="long selected selectpicker"
						name="permiso" required>
						<option selected value="default">Seleccionar</option>
						<c:forEach items="${permisos}" var="permiso">		         	
							<option value="${permiso.value}">${permiso.desc}</option>
						</c:forEach>
					</select>
				</div>
				
				<div class="form-fieldset">
					<span class="fieldset-title">Permisos por área:</span>
					<fieldset class='radio-container-holder'>
						<div class="form-field left">
							<span>Clientes:</span><select id="area_cliente" class="long selectpicker" name="area_cliente">
								<option selected value="1">Propietario</option>	
								<option selected value="2">Editar</option>
								<option selected value="3">Ver</option>															
							</select>
						</div>
						
						<div class="form-field right">
							<span>Pruebas:</span><select id="area_pruebas" class="long selectpicker" name="area_pruebas">
								<option selected value="1">Propietario</option>	
								<option selected value="2">Editar</option>
								<option selected value="3">Ver</option>								
							</select>
						</div>
						
						<div class="form-field left">
							<span>Servicios:</span><select id="area_servicios" class="long selectpicker" name="area_servicios">
								<option selected value="1">Propietario</option>	
								<option selected value="2">Editar</option>
								<option selected value="3">Ver</option>							
							</select>
						</div>
						
						<div class="form-field right">
							<span>Informes:</span><select id="area_informes" class="long selectpicker" name="area_informes">
								<option selected value="1">Propietario</option>	
								<option selected value="2">Editar</option>
								<option selected value="3">Ver</option>							
							</select>
						</div>
						
						<div class="form-field left">
							<span>Soporte:</span><select id="area_soporte" class="long selectpicker" name="area_soporte">
								<option selected value="1">Propietario</option>	
								<option selected value="2">Editar</option>
								<option selected value="3">Ver</option>							
							</select>
						</div>
						
						<div class="form-field right">
							<span>Documentación:</span><select id="area_documentacion" class="long selectpicker" name="area_documentacion">
								<option selected value="1">Propietario</option>	
								<option selected value="2">Editar</option>
								<option selected value="3">Ver</option>							
							</select>
						</div>
						
					</fieldset>
				</div>
				
				<div id="message_div">
					<span id="span_message"></span>
				</div>
			</div>

		</form>
		<button type="submit" id="submit_form">Aceptar</button>
		<button href="#" class="close-form">Cancelar</button>
	</div>
</div>


<div>	
	<div>
		<div class="main-table usersTable">
			<table class="table">
				<thead>
					<tr>
						<th><span class="table-title">Nombre</span></th>
						<th><span class="table-title">Apellido 1</span></th>
						<th><span class="table-title">Apellido 2</span></th>
						<th><span class="table-title">Departamento</span></th>
						<th><span class="table-title">Perfil</span></th>
						<th style="width: 110px;">&nbsp;</th>
					</tr>
					<tr>
						<th class="search-th"><input class="search col0"></th>
						<th class="search-th"><input class="search col1"></th>
						<th class="search-th"><input class="search col2"></th>
						<th class="search-th"><input class="search col3"></th>
						<th class="search-th"><input class="search col4"></th>
						<th style="width: 110px;">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="myTable" cellspacing="0">
					<c:choose>
						<c:when test="${empty userList}">
							<tr>
								<td><span>No existen usuarios.</span></td>
							</tr>
						</c:when>

						<c:otherwise>
							<c:forEach items="${userList}" var="user">
								<tr class="valid-result" id="row${user.key.id}" data-permiso="${user.permiso}" data-mail="${user.email}"
									data-dto="${user.departamento}">
									<td><span>${user.nombre}</span></td>
									<td><span>${user.apellido1}</span></td>
									<td><span>${user.apellido2}</span></td>
									<td><span>${user.departamento}</span></td>
									<td><span>${user.permisoStr}</span></td>
									<td><img class="vs" src="../img/vs.png"><a class="lapiz" name="${user.key.id}" href="../userModal.do?id=${user.key.id}"	id="lapiz${user.key.id}" data-toggle="modal" data-target="#edit-user" ></a><a class="papelera" name="${user.key.id}" data-toggle="modal"	data-target="#confirm-delete" id="papelera${user.key.id}"></a></td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
		<div class="col-md-12 text-center">
			<ul class="pagination" id="myPager"></ul>
			<span class="pagesummary"></span>
		</div>
	</div>
</div>


<div class="modal fade" id="edit-user" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" id="edit_user_dialog">
		<div class="modal-content">
			
		</div>
	</div>
</div>


<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="">
				<h2>Eliminar usuario</h2>
				<hr />
			</div>
			<div class="">
				<p>&iquest;Est&aacute; seguro que desea eliminar al usuario?
			</div>
			<div class="modal-footer">
				<button type="button" class="pink-btn" id="deleteUser">Eliminar</button>
				<button type="button" class="" data-dismiss="modal">Cancelar</button>
			</div>
		</div>
	</div>
</div>
