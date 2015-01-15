<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!-- body  

<div class="content">
<div class="container">
</div>
</div>
-->

<div id="pruebas">
<h1>Pruebas</h1>
<span class="btn-atras" onclick="window.location.href='../../'"></span>

<hr />

<div class="breadcrumbs">
		<span onclick="window.location.href='../../' ">Home</span> > <span> Pruebas </span>
</div>


<div class="headButtonsBox">
	<button id="formButton"> 
		Nuevo<span class="user_span"></span>
	</button>
	
	<button id="excel_btn" onclick=	"window.location.href='/pruebaServlet?accion=xls'">
		Descargar Tabla<span class="excel_span"></span>
	</button>
	
	<div class="form-holder">
	
		<form id="new-user-form" name="new-client-form" action="/pruebaServlet?accion=new"
			method="POST" novalidate="novalidate">
		
			<div class="form-container">
			
				<div class="form-field-divider left">
				
					<div class="form-field">
						<span class="lbl">Cliente<span class="required-asterisk">*</span>:</span>
						<div class="input">
						
							<select class="selectpicker selected" name="cliente" id="cliente-pruebas" data-live-search="true">
							
								<c:choose>
										<c:when test="${empty clientes}">
											<option value="default">No hay clientes</option>
										</c:when>
										<c:otherwise>
											<option value="default">Seleccionar</option>
											<c:forEach items="${clientes}" var="t">							
												<option value="${t.nombre}" data-premium="${t.premium}" data-clientid="${t.key.id}" data-segmento="${t.tipo_cliente}">${t.nombre}</option>
											</c:forEach>
										</c:otherwise>
								</c:choose>
							</select>
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Tipo cliente:</span>
						<input type="text" name="input-premium-soporte" id="input-premium-soporte" value="" readonly>
					</div>				
						

					
					<input type="text" name="client_id" id="client-id-input" value="" hidden>
					
					
					<div class="form-field">
						<span class="lbl">Referencia:</span>
						<input class="long" type="text" name="referencia" id="referencia">
					</div>
					
					<div class="form-field">
						<span class="lbl">Estado<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<select id="estado" class="selectpicker selected" name="estado">
								<option value="default">Seleccionar</option>
								<c:forEach items="${estados}" var="estado">
										<option value="${estado.name}">${estado.name}</option>
								</c:forEach>		
															
							</select>
						</div>
					</div>
					
				</div>	
				<div class="form-field-divider right">
				
					<div class="form-field">
						<span class="lbl">Fecha alta<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<input type="text" readonly="" value="" size="16" class="datepicker" name="fecha_estado" id="fecha_estado" required aria-required="true">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Producto/canal<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<select id="producto_canal" class="selectpicker selected" name="producto_canal" >
								
								<option value="default">Seleccionar</option>	

								<c:forEach items="${productos}" var="producto">
										<option value="${producto.name}">${producto.name}</option>
								</c:forEach>
															
							</select>
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Entorno<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<select id="entorno" class="selectpicker selected" name="entorno" >
								
								<option value="default">Seleccionar</option>	
								<option value="Preproduccion">Preproducci&oacuten</option>
								<option value="Produccion">Producci&oacuten</option>

															
							</select>
						</div>
					</div>
					

					
					<div class="form-field">
						<span class="lbl">Tipo servicio<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<select id="tipo_servicio" class="selectpicker selected" name="tipo_servicio" >
								<option value="default">Seleccionar</option>	

								<c:forEach items="${tiposervicios}" var="servicio">
										<option value="${servicio.name}">${servicio.name}</option>
								</c:forEach>
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

					
				</div>	
				
					
				<div id="message_div" class="message_div">
					<span id="span_message">La prueba ha sido creado de forma correcta.<br/>En breve volvemos a la p�gina.</span>
				</div>
			</div>
	
		</form>
		
			<button id="submit_form_test" type="submit">Aceptar</button>
			<button class="close-form">Cancelar</button>			
	
	</div>
			

</div>

<div>	
	<div>	
		<div id="div-filtro-pruebas" class="form-container">
			<form id="filtro-pruebas" name="filtro-pruebas">

				
					<%-- 
					<div class="form-field">
							<span class="lbl">Cliente:</span>
							<div class="input">
							
								<select class="selectpicker selected" name="cliente-filtro" id="cliente-filtro" data-live-search="true">
								
									<c:choose>
											<c:when test="${empty clientes}">
												<option value="default">No hay clientes</option>
											</c:when>
											<c:otherwise>
												<option value="">Filtro vacio</option>
												<c:forEach items="${clientes}" var="t">							
													<option value="${t.nombre}" clientid="${t.key.id}">${t.nombre}</option>
												</c:forEach>
											</c:otherwise>
									</c:choose>
								</select>
							</div>
						</div>
					--%>
					<div class="form-field-fecha-prueba">
						<span class="lbl">Fecha alta desde:</span>
							<input type="text" value="" size="16" class="datepicker fromTo" data-target-id='fecha-hasta-filtro' name="fecha-desde-filtro" id="fecha-desde-filtro" >
						
					</div>
					<%-- 
					<div class="form-field">
						<span class="lbl">Tipo servicio:</span>
						<div class="input">
						
							<select class="selectpicker selected" name="servicio-filtro" id="servicio-filtro" data-live-search="true">
								<option value="no disponible">No disponible dep imple</option>
							</select>
						</div>
					</div>
					--%>
				
				
				
				<%-- 
					<div class="form-field">
						<span class="lbl">Entorno:</span>
						<div class="input">
							<select class="selectpicker selected" name="entorno-filtro" id="entorno-filtro" >
								<option value="">Filtro vacio</option>	
								<option value="Preproduccion">Preproducci&oacuten</option>
								<option value="Produccion">Producci&oacuten</option>
							</select>
						</div>
					</div>
				--%>	
					<div id="form-field-fecha-prueba-hasta">
						<span class="lbl">Fecha alta hasta:</span>

							<input type="text" value="" size="16" class="datepicker" name="fecha-hasta-filtro" id="fecha-hasta-filtro" >

					</div>
					<%-- 
					<div class="form-field">
						<span class="lbl">Estado:</span>
						<div class="input">
							<select class="selectpicker selected" name="estado-filtro" id="estado-filtro">
								<option value="">Filtro vacio</option>
								<option value="Pendiente">Pendiente</option>									
								<option value="En curso">En curso</option>	
								<option value="Finalizado">Finalizado</option>		
															
							</select>
						</div>
					</div>
					--%>
					
					
				
				
				<button id="test_filter_button" type="button">Buscar</button>
			
			</form>

		</div>
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
						<th><span class="table-title">Fecha alta</span></th>
						<th><span class="table-title">Cliente</span></th>
						<th><span class="table-title">Tipo servicio</span></th>
						<th><span class="table-title">Estado</span></th>
						<th><span class="table-title">Producto</span></th>
						<th><span class="table-title">Entorno</span></th>
						<th style="width: 110px;">&nbsp;</th>
					</tr>

					<tr>
						<th class="search-th"><input class="search col0 search_anywhere"></th>
						<th class="search-th"><input class="search col1 search_anywhere"></th>
						<th class="search-th"><input class="search col2 search_anywhere"></th>
						<th class="search-th"><input class="search col3 search_anywhere"></th>
						<th class="search-th"><input class="search col4 search_anywhere"></th>
						<th class="search-th"><input class="search col5 search_anywhere"></th>
						<th style="width: 110px;">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="myTable" cellspacing="0">
					<c:choose>
						<c:when test="${empty pruebas}">
							<tr>
								<td><span>No existen datos.</span></td>
							</tr>
						</c:when>

						<c:otherwise>
							<c:forEach items="${pruebas}" var="s">
								<tr class="valid-result ${s.premium == 'Premium' ? 'premium' : ''}" data-strfechaestado="${s.str_fecha_estado}"
								 data-nombrecliente="${s.nombre_cliente}" data-estado="${s.estado}" data-entorno="${s.entorno}"  id="row${s.key.id}">
									<td><span>${s.str_fecha_estado}</span></td>
									<td><span>${s.nombre_cliente}</span></td>
									<td><span>${s.tipo_servicio}</span></td>
									<td><span>${s.estado}</span></td>
									<td><span>${s.producto}</span></td>
									<td><span>${s.entorno}</span></td>
									
									<td><img class="vs" src="../img/vs.png"><a class="lapiz" name="${s.key.id}" href="../pruebaModal.do?id=${s.key.id}"	id="lapiz${s.key.id}" data-toggle="modal" data-target="#edit-soporte" ></a>
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
	<div class="modal-dialog">
		<div class="modal-content">
			
		</div>
	</div>
</div>


<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="">
				<h2>Eliminar prueba</h2>
				<hr />
			</div>
			<div class="">
				<p>&iquest;Est&aacute; seguro que desea eliminar la prueba?
			</div>
			<div class="modal-footer">
				<button type="button" class="pink-btn" id="deletePrueba">Eliminar</button>
				<button type="button" class="" data-dismiss="modal">Cancelar</button>
			</div>
		</div>
	</div>
</div>

</div>




