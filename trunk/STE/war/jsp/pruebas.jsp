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
												<option value="${t.key.id}" data-premium="${t.premium}" data-clientid="${t.key.id}" data-segmento="${t.tipo_cliente}">${t.nombre}</option>
											</c:forEach>
										</c:otherwise>
								</c:choose>
							</select>
						</div>
					</div>
				
					<div class="form-field" id="div_imp">
						<span class="lbl">Implementaci&oacute;n<span class="required-asterisk">*</span>:</span>
						<select class="selectpicker selected" name="imp_id" id="imp-pruebas" data-live-search="true">
							<!--<c:choose>
								<c:when test="${empty implementaciones}">
									<option value="default">No hay implementaciones</option>
								</c:when>
							<c:otherwise>
								<option value="default">Seleccionar</option>
								<c:forEach items="${implementaciones}" var="t">	
									<c:forEach items="${clientes}" var="cli">
										<c:choose>
											<c:when test="${t.cliente_id==cli.key.id}">				
												<option value="${t.key.id}" data-nombre="${cli.nombre}" data-premium="${cli.premium}" >${t.id_implementacion}&nbsp${cli.nombre}</option>
											</c:when>
											<c:otherwise>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:forEach>
							</c:otherwise>
							</c:choose>-->
							<option value="default">-</option>
						</select>
					</div>
				
					<div class="form-field">
						<span class="lbl">Tipo cliente:</span>
						<input type="text" class="input-autorefillable" name="input-premium-prueba" id="input-premium-prueba" value="" readonly>
					</div>				
					
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
					
					<div class="form-field">
						<span class="lbl">Fichero:</span>
						<input type="text" class=""  name="fichero" id="" value="">
					</div>
					
					<div class="form-field">
						<span class="lbl">Peticionario:</span>
						<input type="text" class="" size="35" name="peticionario" id="" value="">
					</div>	
				
				</div>
					
				<div class="form-field-divider right">
					
					<div class="form-field">
						<span class="lbl">Fecha inicio<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<input type="text" readonly="" value="" size="16" class="datepicker" name="fecha_inicio" id="fecha_inicio" required aria-required="true">
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha estado<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<input type="text" readonly="" value="" size="16" class="datepicker" name="fecha_estado" id="fecha_estado" required aria-required="true">
						</div>
					</div>
							
					
					<div class="form-field">
						<span class="lbl">Producto/canal<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<select id="producto_canal" class="selectpicker selected" name="producto_canal" data-live-search="true">
								
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
								<option value="INTEGRADO">INTEGRADO</option>
								<option value="PRODUCCION">PRODUCCI&Oacute;N</option>															
							</select>
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Resultado<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<select id="resultado" class="selectpicker selected" name="resultado" >
								<option value="OK">OK</option>
								<option value="KO">KO</option>
								<option value=""></option>
								<option value="CANCELADA">CANCELADA</option>
							</select>
						</div>
					</div>
					
					<div class="form-field">
						<span class="lbl">Tipo servicio<span class="required-asterisk">*</span>:</span>
						<div class="input">
							<select id="tipo_servicio" class="selectpicker selected" name="tipo_servicio" data-live-search="true">
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
					<span id="span_message">La prueba ha sido creado de forma correcta.<br/>En breve volvemos a la página.</span>
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
					
					
					<div class="form-field-fecha-prueba">
						<span class="lbl">Fecha inicio desde:</span>
							<input type="text" value="${desdeFilter}" size="16" class="datepicker fromTo" data-target-id='fecha-hasta-filtro' name="fecha-desde-filtro" id="fecha-desde-filtro" >
						
					</div>
					<div id="form-field-fecha-prueba-hasta">
						<span class="lbl">Fecha inicio hasta:</span>

							<input type="text" value="${hastaFilter}" size="16" class="datepicker" name="fecha-hasta-filtro" id="fecha-hasta-filtro" >

					</div>
				
				<button id="test_filter_button" onclick="filtering();" type="button">Buscar</button>
			
			</form>

		</div>
		<div class="tipo-cliente-field">
			<span class="lbl">Tipo cliente:</span>
			<select id="tipo_cliente" class="selectpicker selected" name="tipo_servicio" >
				<option value="TODOS" ${premiumFilter == "TODOS" ? 'selected' : ''}>TODOS</option>	
				<option value="PREMIUM" ${premiumFilter == "PREMIUM" ? 'selected' : ''}>PREMIUM</option>	
			</select>
		</div>
		<div class="main-table usersTable">
			<table class="table">
				<thead>
					<tr>
						<th><span class="table-title">Fecha estado</span></th>
						<th><span class="table-title">Cliente</span></th>
						<th><span class="table-title">Tipo servicio</span></th>
						<th><span class="table-title">Estado</span></th>
						<th><span class="table-title">Producto</span></th>
						<th><span class="table-title">Entorno</span></th>
						<th style="width: 110px;">&nbsp;</th>
					</tr>

					<tr>
						<form id='test-header-filter' action="">
						<th class="search-th">
							<div class="date-container">
								<input class="date" name='fechadia' value='${fechadia}' maxlength="2">								
								<input class="date" name='fechames' value='${fechames}' maxlength="2">
								<input class="date anio" name='fechaanio' value='${fechaanio}' maxlength="4">		
							</div>
						</th>
						<th class="search-th"><input name="cliente-filter" value="${clienteFilter}"></th>
						<th class="search-th"><input name="servicio-filter" value='${servicioFilter}'></th>
						<th class="search-th"><input name="estado-filter" value="${estadoFilter}"></th>
						<th class="search-th"><input name="producto-filter" value="${productoFilter}"></th>
						<th class="search-th"><input name="entorno-filter" value="${entornoFilter}"></th>
						<input name="idCli" class="hidden" value="${idCli}">
						<th style="width: 110px;"><button type='button' onclick="filtering();">  FILTRAR  </button></th>
						</form>
					</tr>
				</thead>
				<tbody id="myTable" cellspacing="0" data-page="${page}" data-lastpage="${lastpage}" data-numpages="${numpages}">
					<c:choose>
						<c:when test="${empty pruebas}">
							<tr>
								<td><span>No existen datos.</span></td>
							</tr>
						</c:when>

						<c:otherwise>
							<c:forEach items="${pruebas}" var="s">
							
															<tr class="valid-result ${s.premium == 'PREMIUM' ? 'premium' : ''}" data-strfechaestado="${s.fecha_inicio_str}"
															 data-nombrecliente="${s.client_name}" data-estado="${s.estado}" data-entorno="${s.entorno}" name="${s.key.id}" id="row${s.key.id}" style="${s.entorno=="PRODUCCION"?'background-color:#C8DBFF;':''}${s.erased?'background-color:#8B8B8B;':''}">
																<td><span>${s.str_fecha_estado}</span></td>
																<td><span>${s.client_name}</span></td>
																<td><span>${s.tipo_servicio}</span></td>
																<td><span style="color:
																
																
																${s.estado=="PENDIENTE"?'orange':''}
																${s.estado=="EN CURSO"?'blue':''}
																${s.estado=="FINALIZADO"?'green':''}
																${s.estado=="CANCELADA"?'grey':''}
																
																															
																">${s.estado}</span></td>
																<td><span>${s.producto}</span></td>
																<td><span>${s.entorno=="PRODUCCION"?'PRODUCCI&Oacute;N':s.entorno}</span></td>
																
																<td>
																${s.erased?'
																
																':'
																
																'}<img class="vs" src="../img/vs.png"><a class="lapiz" name="${s.key.id}" href="../pruebaModal.do?id=${s.key.id}"	id="lapiz${s.key.id}" data-toggle="modal" data-target="#edit-soporte" ></a>
																<!--<a class="papelera" name="${s.key.id}" data-toggle="modal"	data-target="#confirm-delete" id="papelera${s.key.id}"></a>-->
																</td>
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
			<div class="paginationGoto" />
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





