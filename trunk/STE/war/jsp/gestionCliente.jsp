<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div id="gestion_cliente">



<h1>Gestión cliente</h1>
<span class="btn-atras" onclick="window.location.href='../clientes.do' "></span>

<hr />

<div class="breadcrumbs">
		<span onclick="window.location.href='../../' ">Home</span> > <span onclick="window.location.href='../clientes.do' ">Clientes</span> > <span> Gestión cliente </span>
	</div>

<div class="headButtonsBox">
	<button id="formButton">
		Nuevo<span class="user_span"></span>
	</button>
	
	<button id="excel_btn" onclick=	"window.location.href='../../clientServlet?accion=xls'">
		Descargar Tabla<span class="excel_span"></span>
	</button>
 
	<!-- 
	<button id="excel_btn" onclick=	"window.location.href='../../usersServlet?accion=xls'">
		Descargar Tabla<span class="excel_span"></span>
	</button>
 -->

	<div class="form-holder">
		<form id="new-user-form" name="new-client-form" action="/clientServlet"
			method="POST" novalidate="novalidate">
				<div class="form-container">
					<div class="form-field-divider left">
						

						<input class="long readonly hidden" type="text" readonly name="id_cliente" id="id_cliente">

						
						<div class="form-field">
							<span class="lbl">Nombre cliente<span class="required-asterisk">*</span>:</span>
							<input class="long" type="text" required name="nombre_cliente" id="nombre_cliente">
						</div>
						
						<!-- 			
						<div class="form-field">
							<span class="lbl">Estado<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select id="estado" class="selectpicker selected" name="estado" required aria-required="true">
									<option value="default">Seleccionar</option>
									<option value="Implementación en curso">Implementación en curso</option>
									<option value="Finalizado">Finalizado</option>
									<option value="Parado">Parado</option>
									<option value="Terminados Fileact">Terminados Fileact</option>								
								</select>
							</div>
						</div>
						 -->
						<div class="form-field">
							<span class="lbl">Fecha alta<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" readonly="" value="" size="16" class="datepicker" name="fecha_alta" id="fecha_alta" required aria-required="true">
							</div>
						</div>
						
						<!-- 
						
						<div class="form-field">
							<span class="lbl">País<span class="required-asterisk">*</span>:</span>
							<select placeholder="Seleccionar" name="pais" class="selectpicker selected" multiple required aria-required="true">
							  <option value="Colombia">Colombia</option>
							  <option value="España">España</option>
							  <option value="Venezuela">Venezuela</option>
							  <option value="Francia">Francia</option>
							  <option value="México">México</option>
							  <option value="Argentina">Argentina</option>
							  <option value="Portugal">Portugal</option>
							  <option value="UK">UK</option>
							  <option value="Perú">Perú</option>
							  <option value="Nueva York">Nueva York</option>					  
							</select>
						</div>
						 -->
						<!-- 
						
						<div class="form-field">
							<span class="lbl">Producto<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select id="producto" class="selectpicker selected" name="producto" required aria-required="true">
									<option value="default">Seleccionar</option>
									<option value="Swift FileAct">Swift FileAct</option>										
									<option value="Swift FileAct (antigua conexión)">Swift FileAct (antigua conexión)</option>
									<option value="Swift FIN">Swift FIN</option>
									<option value="Swift FIN (Relay Bank)">Swift FIN (Relay Bank)</option>
									<option value="HSS">HSS</option>
									<option value="EDITRAN">EDITRAN</option>
									<option value="BBVA net cash">BBVA net cash</option>
									<option value="EDIFACT">EDIFACT</option>
									<option value="Normalizador">Normalizador</option>																					
								</select>
							</div>
						</div>
						 -->
					</div>
					<div class="form-field-divider right">

						<div class="form-field">
							<span class="lbl">Tipo cliente<span class="required-asterisk">*</span>:</span>						
							
							
							<label class="ui-marmots-label-radio marmots-label-left on" for="radio_Si">
								Premium
								<input id="radio_Si" type="radio" name="premium" value="Premium" checked/>
							</label>
							<label class="ui-marmots-label-radio marmots-label-left" for="radio_No">
								No Premium
								<input id="radio_No" type="radio" name="premium" value="No premium" />
							</label>
						</div>
						
						<!-- 
						
						<div class="form-field">
							<span class="lbl">Subestado<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select id="subestado" class="selectpicker selected" name="subestado" required aria-required="true">
									<option value="default">-</option>																									
								</select>
							</div>
						</div>
						
						 -->
						<!-- 
						<div class="form-field">
							<span class="lbl">Fecha fin<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" readonly="" value="" size="16" class="datepicker" name="fecha_fin" id="fecha_fin" required aria-required="true">
							</div>
						</div>
						 -->
						 
						<div class="form-field">
							<span class="lbl">Segmento<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select id="tipo_cliente" class="selectpicker selected" name="tipo_cliente" >
									<option value="default">Seleccionar</option>		
									<option value="CIB">CIB</option>									
									<option value="BEC">BEC</option>	
									<option value="Pymes">Pymes</option>								
								</select>
							</div>
						</div>
						
					</div>
					<div id="message_div" class="message_div">
						<span id="span_message">El cliente ha sido creado de forma correcta.<br/>En breve volvemos a la página.</span>
					</div>
				</div>
			
		</form>
		
			<button id="submit_form_client" type="submit">Aceptar</button>
			<button class="close-form">Cancelar</button>			
	
	</div>
	
</div>


<div>	
	<div>
		<div class="main-table usersTable">
			<table class="table">
				<thead>
					<tr>
						<th><span class="table-title">Identificador</span></th>
						<th><span class="table-title">Nombre</span></th>
						<th><span class="table-title">Fecha alta</span></th>
						<th><span class="table-title">Segmento</span></th>
						<th><span class="table-title">Tipo Cliente</span></th>
						<th style="width: 110px;">&nbsp;</th>
					</tr>
					<tr>
						<th class="search-th"><input class="search col0 search_anywhere"></th>
						<th class="search-th"><input class="search col1 search_anywhere"></th>
						<th class="search-th"><input class="search col2 search_anywhere"></th>
						<th class="search-th"><input class="search col3 search_anywhere"></th>
						<th class="search-th"><input class="search col4 search_anywhere"></th>
						<th style="width: 110px;">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="myTable" cellspacing="0">
					<c:choose>
						<c:when test="${empty clientes}">
							<tr>
								<td><span>No existen clientes.</span></td>
							</tr>
						</c:when>

						<c:otherwise>
							<c:forEach items="${clientes}" var="c">
								<tr class="valid-result" id="row${c.key.id}">
									<td><span>${c.id_cliente}</span></td>
									<td><span>${c.nombre}</span></td>
									<td><span>${c.str_fecha_alta}</span></td>
									<td><span>${c.tipo_cliente}</span></td>
									<td><span>${c.premium}</span></td>
									
									<td><img class="vs" src="../img/vs.png"><a class="lapiz" name="${c.key.id}" href="../clienteModal.do?id=${c.key.id}"	id="lapiz${c.key.id}" data-toggle="modal" data-target="#edit-cliente" ></a>
									<a class="papelera" name="${c.key.id}" data-toggle="modal"	data-target="#confirm-delete" id="papelera${c.key.id}"></a></td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
					
					<c:forEach items="${clientesBorrados}" var="c">
						<tr class="valid-result" style='background-color:#8B8B8B;' id="row${c.key.id}"  >
							<td><span>${c.id_cliente}</span></td>
							<td><span>${c.nombre}</span></td>
							<td><span>${c.str_fecha_alta}</span></td>
							<td><span>${c.tipo_cliente}</span></td>
							<td><span>${c.premium}</span></td>
							
							<td>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="col-md-12 text-center">
			<ul class="pagination" id="myPager"></ul>
			<span class="pagesummary"></span>
		</div>
	</div>
</div>


<div class="modal fade" id="edit-cliente" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" id="edit_cliente_dialog">
		<div class="modal-content">
			
		</div>
	</div>
</div>


<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="">
				<h2>Eliminar cliente</h2>
				<hr />
			</div>
			<div class="">
				<p>&iquest;Est&aacute; seguro que desea eliminar el cliente?
			</div>
			<div class="modal-footer">
				<button type="button" class="pink-btn" id="deleteClient">Eliminar</button>
				<button type="button" class="" data-dismiss="modal">Cancelar</button>
			</div>
		</div>
	</div>
</div>

</div>
