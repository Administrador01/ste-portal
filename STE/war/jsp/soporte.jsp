<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div id="soporte">



<h1>Soporte</h1>
<span class="btn-atras" onclick="window.location.href='../../'"></span>

<hr />

<div class="breadcrumbs">
		<span onclick="window.location.href='../../' ">Home</span> > <span> Soporte </span>
	</div>

<div class="headButtonsBox">
	<button id="formButton">
		Alta soporte<span class="user_span"></span>
	</button>
	
	<!-- 
	<button id="excel_btn" onclick=	"window.location.href='../../usersServlet?accion=xls'">
		Descargar Tabla<span class="excel_span"></span>
	</button>
 -->

	<div class="form-holder">
		<form id="new-user-form" name="new-user-form" action="/usersServlet"
			method="POST" novalidate="novalidate">
			<div class="form-container">
				<div class="form-field-divider left">
					<div class="form-field">
						<span class="lbl">Id. Prueba:</span>
						<input class="long readonly" type="text" readonly name="id_prueba" id="id_prueba">
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha inicio<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<input type="text" readonly="" value="" size="16" class="datepicker fromTo" data-target-id='fecha_fin' name="fecha_inicio" id="fecha_inicio" required aria-required="true">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Cliente<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<select id="input_cliente" class="selectpicker selected" name="cliente" required aria-required="true">
								<option value="default">Seleccionar</option>
								<!-- <c:forEach items="${clientes}" var="cliente">	
									<option value="${cliente.key.id}">${cliente.nombre}</option>
								</c:forEach> -->
							</select>
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Tipo de servicio<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<select id="tipo_servicio" class="selectpicker selected" name="tipo_servicio" >
								<option value="default">Seleccionar</option>	
								<option value="CIB">CIB</option>	
								<option value="BEC">BEC</option>	
							</select>
						</div>
					</div>
				</div>
				<div class="form-field-divider right">
					
					<div class="form-field"></div>
					
					<div class="form-field">
						<span class="lbl">Fecha fin<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<input type="text" readonly="" value="" size="16" class="datepicker" name="fecha_fin" id="fecha_fin" required aria-required="true">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Estado<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<select id="estado" class="selectpicker selected" name="estado" >
								<option value="default">Seleccionar</option>		
								<option value="Pendiente">Pendiente</option>									
								<option value="Producción">Producción</option>	
								<option value="Eliminado">Eliminado</option>	
								<option value="Finalizado">Finalizado</option>	
								<option value="Parado">Parado</option>	
															
							</select>
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Producto/canal<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<select id="producto_canal" class="selectpicker selected" name="producto_canal" >
								<option value="default">Seleccionar</option>		
								<option value="SwiftFIN">SwiftFIN</option>	
								<option value="SwiftFileact">SwiftFileact</option>	
								<option value="Editran">Editran</option>	
								<option value="BBVA Netcash">BBVA Netcash</option>	
								<option value="default">Seleccionar</option>	
															
							</select>
						</div>
					</div>
					
				</div>
			</div>

		</form>
		<button type="submit" id="submit_form">Aceptar</button>
		<button href="#" class="close-form">Cancelar</button>
	</div>
</div>


<div>	
	<div>
		<div class="tipo-cliente-field">
			<span class="lbl">Tipo cliente:</span>
			<select id="tipo_servicio" class="selectpicker selected" name="tipo_servicio" >
				<option value="Todos" selected>Todos</option>	
				<option value="Premium">Premium</option>	
			</select>
		</div>
		<div class="main-table usersTable">
			<table class="table">
				<thead>
					<tr>
						<th><span class="table-title">Fecha inicio</span></th>
						<th><span class="table-title">Fecha fin</span></th>
						<th><span class="table-title">Cliente</span></th>
						<th><span class="table-title">Estado</span></th>
						<th><span class="table-title">Tipo de servicio</span></th>
						<th><span class="table-title">Producto / Canal</span></th>
						<th style="width: 110px;">&nbsp;</th>
					</tr>
					<tr>
						<th class="search-th"><input class="search col0"></th>
						<th class="search-th"><input class="search col1"></th>
						<th class="search-th"><input class="search col2"></th>
						<th class="search-th"><input class="search col3"></th>
						<th class="search-th"><input class="search col4"></th>
						<th class="search-th"><input class="search col5"></th>
						<th style="width: 110px;">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="myTable" cellspacing="0">
					<c:choose>
						<c:when test="${empty soportes}">
							<tr>
								<td><span>No existen soportes.</span></td>
							</tr>
						</c:when>

						<c:otherwise>
							<c:forEach items="${soportes}" var="s">
								<tr class="valid-result" id="row${s.key.id}">
									<td><span>${s.str_fecha_inico}</span></td>
									<td><span>${s.str_fecha_fin}</span></td>
									<td><span>${s.cliente_name}</span></td>
									<td><span>${s.estado}</span></td>
									<td><span>${s.tipo_servicio}</span></td>
									<td><span>${s.producto_canal}</span></td>
									
									<td><img class="vs" src="../img/vs.png"><a class="lapiz" name="${s.key.id}" href="../soporteModal.do?id=${s.key.id}"	id="lapiz${s.key.id}" data-toggle="modal" data-target="#edit-soporte" ></a>
									<a class="papelera" name="${s.key.id}" data-toggle="modal"	data-target="#confirm-delete" id="papelera${s.key.id}"></a></td>
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


<div class="modal fade" id="edit-soporte" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" id="edit_soporte_dialog">
		<div class="modal-content">
			
		</div>
	</div>
</div>


<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="">
				<h2>Eliminar soporte</h2>
				<hr />
			</div>
			<div class="">
				<p>&iquest;Est&aacute; seguro que desea eliminar soporte?
			</div>
			<div class="modal-footer">
				<button type="button" class="pink-btn" id="deleteUser">Eliminar</button>
				<button type="button" class="" data-dismiss="modal">Cancelar</button>
			</div>
		</div>
	</div>
</div>

</div>