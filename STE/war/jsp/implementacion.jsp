<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>



<div id="implementacion">

<h1>Gesti&oacuten implementaciones</h1>
<span class="btn-atras" onclick="window.location.href='javascript:window.history.go(-1);'"></span>

<hr />

<div class="breadcrumbs">
		<span onclick="window.location.href='../../' ">Home</span> > <span onclick="window.location.href='../clientes.do' ">Clientes</span> > <span> Implementaci&oacuten </span>
</div>

<div class="headButtonsBox">


	<button id="formButton">
		Nuevo<span class="user_span"></span>
	</button>
	<button id="excel_btn" onclick=	"window.location.href='../../implementacionServlet?accion=xls'">
		Descargar Tabla<span class="excel_span"></span>
	</button>

	<div class="form-holder">
		<form id="new-user-form" name="new-user-form" action="/implementacionServlet"
			method="POST" novalidate="novalidate">
				
				<button type="button" class="go_pag1">Paso 1</button>
				<button type="button" class="go_pag2">Paso 2</button>
			<!--botones -->
			<div class="form-container">
			

				<div class="page1_imp" id='page1_imp'>
				
				

					<div class="form-field" id="div_cliente_imp">
						<span class="lbl">Cliente<span class="required-asterisk">*</span>:</span>
						<select class="selectpicker selected" name="cliente" id="cliente_imp" data-live-search="true">
							<c:choose>
								<c:when test="${empty clientes}">
									<option value="default">No hay clientes</option>
								</c:when>
							<c:otherwise>
								<option value="default">Seleccionar</option>
								<c:forEach items="${clientes}" var="t">							
									<option value="${t.key.id}" data-segmento="${t.tipo_cliente}" data-clientid="${t.key.id}" >${t.nombre}</option>
								</c:forEach>
							</c:otherwise>
							</c:choose>
						</select>
					</div>
					

					<div class="form-field" id="div_producto_imp">
							<span class="lbl">Producto/canal<span class="required-asterisk">*</span>:</span>
								<select id="producto_imp" class="selectpicker selected"  name="producto_imp">
									<option value="default">Seleccionar</option>	
									
									<c:forEach items="${productos}" var="producto">
										<option value="${producto.name}">${producto.name}</option>
									</c:forEach>
								</select>
					</div>
					
					<div class="form-field" id="div_servicio_imp">
						<span class="lbl">Servicio<span class="required-asterisk">*</span>:</span>
						<select class="selectpicker selected" name="servicio" id="servicio_imp" data-live-search="true">
							<c:choose>
								<c:when test="${empty servicios}">
									<option value="default">No hay servicios</option>
								</c:when>
							<c:otherwise>
								<option value="default">Seleccionar</option>
								<c:forEach items="${servicios}" var="t">							
									<option value="${t.key.id}" data-nombre="${t.name}" data-tipo="${t.tipo}" >${t.name}</option>
								</c:forEach>
							</c:otherwise>
							</c:choose>
						</select>
						<input type="text" name="servicio_id" id="servicio-id-input" value="" hidden>
					</div>
					
					<div class="form-field" >
					<span class="lbl">Segmento:</span>
					<input type="text" class="autorrellenable" name="segmento" id="input-segmento-implementacion" value="" readonly>
					</div>
					
					<div class="form-field">
						<span class="lbl">Estado<span class="required-asterisk">*</span>:</span>
							<select id="estado_imp" class="selectpicker selected"  name="estado_imp">	
									<option value="Pendiente" selected>Pendiente</option>
									<option value="Analisis">An&aacutelisis</option>
									<option value="Pruebas">Pruebas</option>	
									<option value="Penny test">Penny test</option>
									<option value="Finalizado">Finalizado</option>	
									<option value="Parado">Parado</option>
									<option value="Anulado">Anulado</option>
							</select>
					</div>
					
					<div class="form-field" id="div_fecha_alta_imp">
					<span class="lbl">Fecha alta<span class="required-asterisk">*</span>:</span>
					<input type="text" size="16" class="datepicker" name="fecha_alta" id="fecha_alta_imp" readonly="" required aria-required="true">
					</div>
					
					<div class="form-field" id="div_pais_imp">
					<span class="lbl">Pa&iacutes<span class="required-asterisk">*</span>:</span>
								<select id="pais_imp" class="selectpicker selected" name="pais">
									<option value="default">Seleccionar</option>	
									
									<c:forEach items="${paises}" var="pais">
										<option value="${pais.name}">${pais.name}</option>
									</c:forEach>

									
							</select>
					</div>
					
					<div class="form-field">
					<span class="lbl etiqueta">Normalizador<span class="required-asterisk">*</span>:</span>
							<label class="lbl radio ui-marmots-label-radio marmots-label-left" for="radio_Si">
								<input name="normalizador" id="radio_Si" type="radio" value="Si"/>Si
							</label>

							<label class="lbl radio ui-marmots-label-radio marmots-label-left on" for="radio_No">
								<input name="normalizador" id="radio_No" type="radio" value="No" checked/>No
							</label>
					</div>
					<div class="form-field">
					<span class="lbl">Referencia global:</span>
					<input type="text" name="ref_glo" maxlength="11">
					</div>
					<div class="form-field">
					<span class="lbl">Referencia local:</span>
					<input type="text" name="ref_loc" maxlength="18">
					</div>
					
					
					<div class="form-field">
					<span class="lbl etiqueta">Firma contrato<span class="required-asterisk">*</span>:</span>
							<label class="lbl radio ui-marmots-label-radio marmots-label-left" for="crack_Si">
								<input name="firma" id="crack_Si" type="radio" value="Si"/>Si
							</label>

							<label class="lbl radio ui-marmots-label-radio marmots-label-left on" for="crack_No">
								<input name="firma" id="crack_No"  type="radio" value="No" checked />No
							</label>
					</div>
					
					
					
						
						
						
					<div class="form-field">
					<span class="lbl">Gestor GCS:</span>
					<input type="text" name="gestor_gcs" maxlength="25">
					</div>
					
					<div class="form-field">
					<span class="lbl">Gestor promoci&oacuten:</span>
					<input type="text" name="gestor_prom" maxlength="25">
					</div>
					
					<div class="form-field">
					<span class="lbl">Gestor relaci&oacuten:</span>
					<input type="text" name="gestor_relacion" maxlength="25">
					</div>
					
					<div class="form-field detalle">
					<span class="lbl">Detalle:</span>
					<textarea type="text" name="detalle" maxlength="500" rows="3" cols="3" placeholder="Introduzca texto ..."></textarea>
					</div>
						
			
					<div class="entor_integrad">
					<h4>Entorno Integrado</h4>
					<span class="lbl">Referencia externa:</span>
					<input type="text" name="ref_ext" id="ref_ext" maxlength="18">
					</div>
					<!--
					<div class="form-field">
						<button type="button" class="go_pag2 siguiente">Siguiente </button>
						<button class="siguiente close-form">Cancelar</button>	
					</div>
					-->


					
				</div>
				<div class="page2_imp hidden" id='page2_imp'>
				
				<div class="entor_derech">
					<div class="form-field">
						<span class="lbl">Servicio:</span>
						<input type="text"class="autorrellenable" id="input-servicio-name-implementacion" value="" readonly>
					</div>
					
					<div class="form-field">
						<span class="lbl">Tipo servicio:</span>
						<input type="text" class="autorrellenable" id="input-servicio-tipo-implementacion" value="" readonly>
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha contrataci&oacuten:</span>
						<input type="text" size="16" class="datepicker fromTo" data-target-id='fecha_subid' name="fecha_contrat" id="fecha_contrat" readonly>
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha subida:</span>
						<input type="text" size="16" class="datepicker" name="fecha_subid" id="fecha_subid" readonly>
					</div>
					
				</div>
					<div class="entor_integrad2">
					<h3>Entorno Integrado</h3>
						<div class="form-field">
						<span class="lbl">Referencia externa:</span>
						<input type="text" class="autorrellenable" id="input-servicio-referencia" value="" readonly>
						</div>
						<div class="form-field">
						<span class="lbl">Asunto:</span>
						<input type="text" name="asunto" maxlength="30">
						</div>
						<hr style="visibility:hidden;"/>
						<h4>SDD:</h4>
						<hr />
						<div class="form-field">
						<span class="lbl grey">Contrato Adeudos:</span>
						<input type="text" name="contrat_adeud" maxlength="21">
						</div>
						<div class="form-field">
						<span class="lbl grey">ID Acreedor</span>
						<input type="text" name="id_acred" maxlength="16">
						</div>
						<div class="form-field">
						<span class="lbl grey">Cuenta de abono:</span>
						<input type="text" name="cuent_abon" maxlength="16">
						</div>
						
				</div>

					

					
<!--
					<div class="form-field">
					<button type="submit" class="submit_form_implementacion"> Aceptar </button>
					<button class="close-form">Cancelar</button>	
					</div>-->
				</div>
			<div id="message_div" class="message_div">
				<span id="span_message">La implementacion ha sido creado de forma correcta.<br/>En breve volvemos a la página.</span>
			</div>
			</div>
		</form>
		<button type="button" id="botont1" class="go_pag2 siguientebott">Siguiente</button>
		<button type="submit" id="botont2" class="submit_form_implementacion hidden"> Aceptar </button>
		<button class="close-form">Cancelar</button>
	</div>
</div>	
		<div class="main-table usersTable">
			<table class="table">
				<thead>
					<tr>
						<th><span class="table-title">Cliente</span></th>
						<th><span class="table-title">Producto</span></th>
						<th><span class="table-title">Servicio</span></th>
						<th><span class="table-title">Estado</span></th>
						<th><span class="table-title">Pa&iacutes</span></th>
						<th><span class="table-title">Gestor GCS</span></th>
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
						<c:when test="${empty implementaciones}">
							<tr>
								<td><span>No existen datos.</span></td>
							</tr>
						</c:when>

						<c:otherwise>
							<c:forEach items="${implementaciones}" var="s">
								<tr class="valid-result" id="row${s.key.id}">
	
									<td><span>
										<c:forEach items="${clientes}" var="t">
										<c:choose>
											<c:when test="${t.key.id==s.cliente_id}">
												${t.nombre}
											</c:when>
										</c:choose>	
										</c:forEach>
									</span></td>
									<td><span>${s.producto}</span></td>
									<td><span>
										<c:forEach items="${servicios}" var="t">
											<c:choose>
												<c:when test="${t.key.id==s.servicio_id}">
													${t.name}
												</c:when>
											</c:choose>	
										</c:forEach>
									</span></td>
									<td><span>${s.estado}</span></td>
									<td><span>${s.pais}</span></td>
									<td><span>${s.gestor_gcs}</span></td>
									<td><img class="vs" src="../img/vs.png"><a class="lapiz" name="${s.key.id}" href="../implementacionModal.do?id=${s.key.id}"	id="lapiz${s.key.id}" data-toggle="modal" data-target="#edit-soporte"></a></td>
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


