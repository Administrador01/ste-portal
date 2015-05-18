<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>



<div id="implementaciones">

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
				<!--hidden porque el cliente elimino el modelo en dos paginas-->
				<button type="button" class="go_pag1 hidden">Paso 1</button>
				<button type="button" class="go_pag2 hidden">Paso 2</button>
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
					
					<div class="form-field" >
						<span class="lbl">Segmento:</span>
						<input type="text" class="autorrellenable" name="segmento" id="input-segmento-implementacion" value="" readonly>
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
					
					<div class="form-field" id="div_fecha_alta_imp">
						<span class="lbl">Fecha alta<span class="required-asterisk">*</span>:</span>
						<input type="text" size="16" class="datepicker" name="fecha_alta" id="fecha_alta_imp" readonly="" required aria-required="true">
					</div>

					<div class="form-field">
						<span class="lbl">Tipo servicio:</span>
						<input  type="text1" class="autorrellenable" id="input-servicio-tipo-implementacion">
					</div>
					
					<div class="form-field">
						<span class="lbl">Gestor GCS:</span>
						<input type="text" name="gestor_gcs" maxlength="25">
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
						<span class="lbl">Gestor promoci&oacuten:</span>
						<input type="text" name="gestor_prom" maxlength="25">
					</div>
					
					<div class="form-field">
						<span class="lbl etiqueta">Normalizador<span class="required-asterisk">*</span>:</span>
							<label class="lbl radio ui-marmots-label-radio marmots-label-left" for="radio_Si">
								<input name="normalizador" id="radio_Si" type="radio" value="SI"/>SI
							</label>

							<label class="lbl radio ui-marmots-label-radio marmots-label-left on" for="radio_No">
								<input name="normalizador" id="radio_No" type="radio" value="NO" checked/>NO
							</label>
					</div>
					
					<div class="form-field">
						<span class="lbl">Gestor relaci&oacuten:</span>
						<input type="text" name="gestor_relacion" maxlength="25">
					</div>
					
					<div class="form-field">
						<span class="lbl">Referencia global:</span>
						<input type="text" name="ref_glo" maxlength="11">
					</div>
					
					<div class="form-field">
					<span class="lbl etiqueta">Firma contrato<span class="required-asterisk">*</span>:</span>
							<label class="lbl radio ui-marmots-label-radio marmots-label-left" for="crack_Si">
								<input name="firma" id="crack_Si" type="radio" value="SI"/>SI
							</label>

							<label class="lbl radio ui-marmots-label-radio marmots-label-left on" for="crack_No">
								<input name="firma" id="crack_No"  type="radio" value="NO" checked />NO
							</label>
					</div>
					
					<div class="form-field">
						<span class="lbl">Referencia local:</span>
						<input type="text" name="ref_loc" maxlength="18">
					</div>
					
					
					<div class="form-field">
						<span class="lbl">Estado<span class="required-asterisk">*</span>:</span>
							<select id="estado_imp" class="selectpicker selected"  name="estado_imp">
							<c:forEach items="${estadosimp}" var="estadoimp">
							
								<option value="${estadoimp.name}" ${estadoimp.orden == 1? "selected":""}>${estadoimp.name}</option>
							</c:forEach>
									
							</select>
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha contrataci&oacuten:</span>
						<input type="text" size="16" class="datepicker fromTo" data-target-id='fecha_subid' name="fecha_contrat" id="fecha_contrat" readonly>
					</div>
					
					<div class="form-field detalle">
						<span class="lbl">Detalle:</span>
						<textarea type="text" name="detalle" maxlength="500" rows="3" cols="3" placeholder="Introduzca texto ..."></textarea>
					</div>
					
					<div class="form-field">
						<span class="lbl">Fecha subida:</span>
						<input type="text" size="16" class="datepicker" name="fecha_subid" id="fecha_subid" readonly>
					</div>
					
					<div class="entor_integrad2">
					<h3>Entorno Integrado</h3>
						<div class="form-field">
						<span class="lbl">Referencia externa:</span>
						<input type="text" name="ref_ext" id="ref_ext" maxlength="18">
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
										
				</div>

				</div>
			<div id="message_div" class="message_div">
				<span id="span_message">La implementacion ha sido creado de forma correcta.<br/>En breve volvemos a la página.</span>
			</div>
			</div>
		</form>
		<button type="button" id="botont1" class="go_pag2 siguientebott hidden">Siguiente</button>
		<button type="submit" id="botont2" class="submit_form_implementacion"> Aceptar </button>
		<button class="close-form">Cancelar</button>
	</div>
</div>	
		<div class="main-table usersTable">
			<table class="table">
				<thead>
					<tr>
						
						<th><span class="table-title">Fecha alta</span></th>
						<th><span class="table-title">Cliente</span></th>
						<th><span class="table-title">Pa&iacutes</span></th>
						<th><span class="table-title">Producto</span></th>
						<th><span class="table-title">Servicio</span></th>
						<th><span class="table-title">Normalizador</span></th>
						<th><span class="table-title">Estado</span></th>
						<th style="width: 110px;"></th>
					</tr>

					<tr>
					<form id='imp-header-filter' action="" >
						<th class="search-th">
							<div class="date-container">
								<input class="date" name='fechadia' value='${fechadia}' maxlength="2">								
								<input class="date" name='fechames' value='${fechames}' maxlength="2">
								<input class="date anio" name='fechaanio' value='${fechaanio}' maxlength="4">		
							</div>
						</th>
						<th class="search-th"><input name='cliente' value='${cliente}'></th>
						<th class="search-th"><input name='pais' value='${pais}'></th>
						<th class="search-th"><input name='producto' value='${producto}'></th>
						<th class="search-th"><input name='servicio' value='${servicio}'></th>
						<th class="search-th"><input name='normalizator' value='${normalizator}'></th>
						<th class="search-th"><input name='estado' value='${estado}'></th>
						<th style="width: 110px;"><button type='button' onclick='filteringImplementation();'>  FILTRAR  </button></th>
					</form>
					</tr>
				</thead>
				<tbody id="myTable" cellspacing="0"  data-page="${page}" data-lastpage="${lastpage}" data-numpages="${numpages}">
					<c:choose>
						<c:when test="${empty implementaciones}">
							<tr>
								<td><span>No existen datos.</span></td>
							</tr>
						</c:when>

						<c:otherwise>
							<c:forEach items="${implementaciones}" var="s">
								<tr class="valid-result" id="row${s.key.id}" style="${s.erased?'background-color:#8B8B8B;':''}">
									<td><span>${s.str_fecha_alta}</span></td>
									<td><span>${s.client_name}</span></td>
									<td><span>${s.pais}</span></td>
									<td><span>${s.producto}</span></td>
									<td><span>
									<!--
										<c:forEach items="${servicios}" var="t">
											<c:choose>
												<c:when test="${t.key.id==s.servicio_id}">
													${t.name}
												</c:when>
											</c:choose>	
										</c:forEach>
									-->
									
										${s.servicio_name}
									</span></td>
									<td><span>
 										<b>${s.normalizador ? 'SI' : 'NO'}</b>
									</span></td>
									<td><span style="color:
									
									${s.estado == "FINALIZADA"? 'green' : ''}
									${s.estado == "AN&AacuteLISIS"? 'black' : ''}
									${s.estado == "PENDIENTE"? 'orange' : ''}
									${s.estado == "PRUEBAS"? 'blue' : ''}
									${s.estado == "PENNY TEST"? 'purple' : ''}
									${s.estado == "ANULADO"? 'grey' : ''}
									${s.estado == "PARADO"? 'red' : ''}
									
									
									
									;">${s.estado}</span></td>
									
									<td>
									<c:choose>
										<c:when test="${s.erased}">
											<a class="subida" name="${s.key.id}" data-toggle="modal"	data-target="#confirm-restore" id="subida${s.key.id}"></a>
										</c:when>
										<c:otherwise>
											<img class="vs" src="../img/vs.png"><a class="lapiz" name="${s.key.id}" href="../implementacionModal.do?id=${s.key.id}"	id="lapiz${s.key.id}" data-toggle="modal" data-target="#edit-soporte"></a>
											<a class="papelera" name="${s.key.id}" data-toggle="modal"	data-target="#confirm-delete" id="papelera${s.key.id}"></a></td>
										</c:otherwise>
									</c:choose>
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
					<h2>Eliminar implementaci&oacuten</h2>
					<hr />
				</div>
				<div class="">
					<p>&iquest;Est&aacute; seguro que desea eliminar la implementaci&oacuten?
				</div>
				<div class="modal-footer">
					<button type="button" class="pink-btn" id="deleteImplementacion">Eliminar</button>
					<button type="button" class="" data-dismiss="modal">Cancelar</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="confirm-restore" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="">
					<h2>Restaurar implementaci&oacuten</h2>
					<hr />
				</div>
				<div class="">
					<p>&iquest;Est&aacute; seguro que desea restaurar la implementaci&oacuten?
				</div>
				<div class="modal-footer">
					<button type="button" class="pink-btn" id="restoreImplementacion">Restaurar</button>
					<button type="button" class="" data-dismiss="modal">Cancelar</button>
				</div>
			</div>
		</div>
	</div>
</div>


