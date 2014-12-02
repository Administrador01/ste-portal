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
		Nuevo<span class="user_span"></span>
	</button>
	
	 
	<button id="excel_btn" onclick=	"window.location.href='../../soporteServlet?accion=xls'">
		Descargar Tabla<span class="excel_span"></span>
	</button>
 

	<div class="form-holder">
		<form id="new-user-form" name="new-user-form" action="/soporteServlet"
			method="POST" novalidate="novalidate">
			<div class="form-container">
				<div class="form-field-divider left">
					<div class="form-field">
						<span class="lbl">Identificador:</span>
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
						
							<select class="selectpicker selected" name="cliente" id="cliente-soporte" data-live-search="true">
							
								<c:choose>
										<c:when test="${empty clientes}">
											<option value="default">No hay clientes</option>
										</c:when>
										<c:otherwise>
											<option value="default">Seleccionar</option>
											<c:forEach items="${clientes}" var="t">							
												<option value="${t.nombre}" data-premium="${t.premium}" data-segmento="${t.tipo_cliente}" data-clientid="${t.key.id}" >${t.nombre}</option>
											</c:forEach>
										</c:otherwise>
								</c:choose>
							</select>
						</div>
					</div>
					
					<input type="text" name="client_id" id="client-id-input" value="" hidden>
					
					<div class="form-field">
						<span class="lbl">Tipo cliente:</span>
						<input type="text" name="input-premium-soporte" id="input-premium-soporte" value="" readonly>
					</div>
					<div class="form-field">						
						<span class="lbl">Segmento:</span>
						<input type="text" name="tipo_cliente" id="input-segmento-soporte" value="" readonly>
					</div>
					
					<%--
					<div class="form-field">
							<span class="lbl">Premium:</span>
							<label class="ui-marmots-label-radio on" for="radio_Si">
								<input name="premium" id="radio_Si" type="radio" value="Si" checked />Si
							</label>
							<label class="ui-marmots-label-radio marmots-label-left" for="radio_No">
								<input name="premium" id="radio_No"  type="radio" value="No"/>No
							</label>
					</div>
					--%>
					

					
					

				</div>
				<div class="form-field-divider right">



					<div class="form-field">
							<span class="lbl">Tipo<span class="required-asterisk">*</span>:</span>
							<label class="ui-marmots-label-radio on" for="radio_incidencia">
								<input name="tipo" id="radio_incidencia" type="radio" value="Incidencia" checked /><span class="lbl">Incidencia</span>
							</label>
							<label class="ui-marmots-label-radio marmots-label-left" for="radio_consulta">
							<input name="tipo"  id="radio_consulta"  type="radio" value="Consulta"/><span class="lbl">Consulta</span>
							</label>
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha fin:</span>
						<div class="input">
							<input type="text" readonly="" value="" size="16" class="datepicker" name="fecha_fin" id="fecha_fin" >
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Tipo de servicio<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<select id="tipo_servicio" class="selectpicker selected" name="tipo_servicio" >
								<option value="default">Seleccionar</option>	
								<option value="Cobros">Cobros</option>	
								<option value="Pagos">Pagos</option>
								<option value="MT101">MT101</option>
								<option value="MT94x">MT94x</option>
								<option value="Cashpool">Cashpool</option>
								<option value="Factura integral">Factura integral</option>
								<option value="Otros">Otros</option>	
							</select>
						</div>
					</div>

					
					<div class="form-field">
						<span class="lbl">Estado<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<select id="estado" class="selectpicker selected" name="estado">
								<option value="default">Seleccionar</option>
								<option value="Pendiente">Pendiente</option>									
								<option value="En curso">En curso</option>	
								<option value="Finalizado">Finalizado</option>		
															
							</select>
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Producto/canal<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<select id="producto_canal" class="selectpicker selected" name="producto_canal" >
								
								<option value="default">Seleccionar</option>	
								<option value="Swift Fileact">Swift Fileact</option>
								<option value="Swift Fileact Antig">Swift Fileact (antigua conexi&oacuten)</option>
								<option value="Swift FIN">Swift FIN</option>	
								<option value="Swift FIN relay">Swift FIN (Relay Bank)</option>
								<option value="Editran">EDITRAN</option>	
								<option value="BBVA Netcash">BBVA Netcash</option>
								<option value="Edifact">EDIFACT</option>
								<option value="Normalizador">Normalizador</option>
								<option value="Cashpool domestico">Cashpool dom&eacutestico</option>
								<option value="Cashpool internacional">Cashpool internacional</option>
								<option value="factura integral">Factura integral</option>
															
							</select>
						</div>
					</div>
					
				</div>
				<div class="form-field-divider down">
					<div class="form-field detalles">
						<span class="lbl">Descripci&oacuten:</span>
						<div class="input">
							<textarea name="detalles" maxlength="500" rows="1" cols="1" placeholder="Introduzca texto ..."></textarea>
						</div>
					</div>	
					<br />
					<br />
					<div class="form-field solucion">
						<span class="lbl">Soluci&oacuten:</span>
						<div class="input">
							<textarea name="solucion" maxlength="500" rows="1" cols="1" placeholder="Introduzca texto ..."></textarea>
						</div>
					</div>					 
				</div>
				
				

				
				
				<div id="message_div" class="message_div">
					<span id="span_message">El soporte ha sido creado de forma correcta.<br/>En breve volvemos a la página.</span>
				</div>
			</div>

		</form>
		<button type="submit" id="submit_form_support">Aceptar</button>
		<button href="#" class="close-form">Cancelar</button>
	</div>
</div>


<div>	
	<div>
		<div class="tipo-cliente-field">
			<span class="lbl">Tipo cliente:</span>
			<select id="tipo_cliente" class="selectpicker selected" name="tipo_servicio" >
				<option value="Todos" selected>Todos</option>	
				<option value="Premium">Premium</option>	
			</select>
		</div>
		<div class="main-table usersTable">
			<table class="table">
				<thead>
					<tr>
						<th><span class="table-title">Fecha inicio</span></th>
						<th><span class="table-title">Cliente</span></th>
						<th><span class="table-title">Segmento</span></th>
						<th><span class="table-title">Estado</span></th>
						<th><span class="table-title">Tipo de servicio</span></th>
						<th><span class="table-title">Producto / Canal</span></th>
						<th><span class="table-title">Descripci&oacuten</span></th>
						<th style="width: 110px;">&nbsp;</th>
					</tr>
					<tr>
						<th class="search-th"><input class="search col0 search_anywhere"></th>
						<th class="search-th"><input class="search col1 search_anywhere"></th>
						<th class="search-th"><input class="search col2 search_anywhere"></th>
						<th class="search-th"><input class="search col3 search_anywhere"></th>
						<th class="search-th"><input class="search col4 search_anywhere"></th>
						<th class="search-th"><input class="search col5 search_anywhere"></th>
						<th class="search-th"><input class="search col6 search_anywhere"></th>
						<th style="width: 110px;">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="myTable" cellspacing="0">
					<c:choose>
						<c:when test="${empty soportes}">
							<tr>
								<td><span>No existen datos.</span></td>
							</tr>
						</c:when>

						<c:otherwise>
							<c:forEach items="${soportes}" var="s">
								<tr class="valid-result ${s.premium == 'Premium' ? 'premium' : ''}" id="row${s.key.id}">
									<td><span>${s.str_fecha_inicio}</span></td>
									<td><span>${s.cliente_name}</span></td>
									<td><span>${s.tipo_cliente}</span></td>
									<td><span>${s.estado}</span></td>
									<td><span>${s.tipo_servicio}</span></td>
									<td><span>${s.producto_canal}</span></td>
									<td><span>${s.detalles}</span></td>
									
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
				<button type="button" class="pink-btn" id="deleteSoporte">Eliminar</button>
				<button type="button" class="" data-dismiss="modal">Cancelar</button>
			</div>
		</div>
	</div>
</div>

</div>

<script type="text/javascript">
  $(function() {
       $('.selectpicker').selectpicker();
  });
</script>