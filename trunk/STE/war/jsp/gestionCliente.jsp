<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div id="gestion_cliente">



<h1>Gestión cliente</h1>
<span class="btn-atras" onclick="window.location.href='../../'"></span>

<hr />

<div class="breadcrumbs">
		<span onclick="window.location.href='../../' ">Home</span> > <span> Gestión cliente </span>
	</div>

<div class="headButtonsBox">
	<button id="formButton">
		Alta cliente<span class="user_span"></span>
	</button>
	
	<!-- 
	<button id="excel_btn" onclick=	"window.location.href='../../usersServlet?accion=xls'">
		Descargar Tabla<span class="excel_span"></span>
	</button>
 -->

	<div class="form-holder">
		<form id="new-user-form" name="new-client-form" action="/clientServlet"
			method="POST" novalidate="novalidate">
			<div class="form-slider">
				<div class="form-container">
					<div class="form-field-divider left">
						
						<div class="form-field">
							<span class="lbl">Nombre cliente<span class="required-asterisk">*</span>:</span>
							<input class="long" type="text" required name="nombre_cliente" id="nombre_cliente">
						</div>
						
											
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
						
						<div class="form-field">
							<span class="lbl">Fecha alta<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" readonly="" value="" size="16" class="datepicker" name="fecha_alta" id="fecha_alta" required aria-required="true">
							</div>
						</div>
						
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
						
					</div>
					<div class="form-field-divider right">
						<div class="form-field">
							<span class="lbl">Premium<span class="required-asterisk">*</span>:</span>
							<label class="ui-marmots-label-radio" for="radio_Si">
								<input name="radio" id="radio_Si"  type="radio"/>Si
							</label>
							<label class="ui-marmots-label-radio marmots-label-left on" for="radio_No">
								<input name="radio" selected id="radio_No"  type="radio"/>No
							</label>
						</div>
						
						<div class="form-field">
							<span class="lbl">Subestado<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select id="subestado" class="selectpicker selected" name="subestado" required aria-required="true">
									<option value="default">-</option>																									
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Fecha fin<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<input type="text" readonly="" value="" size="16" class="datepicker" name="fecha_fin" id="fecha_fin" required aria-required="true">
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Tipo cliente<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select id="estado" class="selectpicker selected" name="estado" >
									<option value="default">Seleccionar</option>		
									<option value="CIB">CIB</option>									
									<option value="BEC">BEC</option>	
									<option value="Pymes">Pymes</option>	
									
								</select>
							</div>
						</div>
						
					</div>
				</div>
				<div class="form-container">
					<div class="form-field-divider left">
						<div class="form-field referencia">
							<span class="lbl">Referencia 1<span class="required-asterisk">*</span>:</span>
							<input class="" type="text" required name="referencia1" id="referencia1">
							
						</div>
						
						<div class="form-field referencia">
							<span class="lbl">Referencia 2<span class="required-asterisk">*</span>:</span>
							<input class="" type="text" required name="referencia2" id="referencia2">
							
						</div>
						
						<div class="form-field">
							<span class="lbl">Formato envío<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select id="formato_envio" class="selectpicker selected" name="formato_envio" >
									<option value="default">Seleccionar</option>	
									<option value="MT101">MT101</option>	
									<option value="MT940">MT940</option>	
									<option value="AEB43">AEB43</option>	
									<option value="MT942">MT942</option>	
									<option value="MT199">MT199</option>	
									
									<option value="Formato local">Formato local</option>
									
									
									<option value="AEB19">AEB19</option>	
									<option value="XML">XML</option>
									<option value="XML V2">XML V2</option>
									<option value="AEB34.1">AEB34.1</option>
									<option value="TIN">TIN</option>
									<option value="AEB58">AEB58</option>
									<option value="AEB68">AEB68</option>
									<option value="XML V3">XML V3</option>
									
									<option value="Confirming CNF">Confirming CNF</option>
									<option value="OMF">OMF</option>
									<option value="Euroconfirming">Euroconfirming</option>
									
									
																
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Formato recepción:</span>
							<div class="input">
								<select id="gn1" class="selectpicker" name="gn1" >
									<option value="default">Seleccionar</option>	
									
									<option value="MT300">MT300</option>
									<option value="MT305">MT305</option>
									<option value="MT320">MT320</option>
									<option value="MT940">MT940</option>
									
									<option value="AEB43">AEB43</option>
									<option value="MT942">MT942</option>
									<option value="AF120">AF120</option>
									<option value="MT199">MT199</option>
									<option value="pain.001.002.03">pain.001.002.03</option>
									<option value="AEB 43 (ISM)">AEB 43 (ISM)</option>
									<option value="AEB19 (DVR)">AEB19 (DVR)</option>
									<option value="XML">XML</option>
								
																	
															
								</select>
							</div>
						</div>
						
						
					</div>
					<div class="form-field-divider right">
											
						<div class="form-field">
							<span class="lbl">Gestor negocio 1<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select id="gn1" class="selectpicker selected" name="gn1" >
									<option value="default">Seleccionar</option>								
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Gestor Negocio 2<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select id="gn2" class="selectpicker selected" name="gn2" >
									<option value="default">Seleccionar</option>								
								</select>
							</div>
						</div>
						
						<div class="form-field">
							<span class="lbl">Gestor negocio 3<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select id="gn3" class="selectpicker selected" name="gn3" >
									<option value="default">Seleccionar</option>								
								</select>
							</div>
						</div>
						
						
					</div>
					<div class="form-field-divider down">
						<div class="form-field detalles">
							<span class="lbl">Detalles:</span>
							<div class="input">
								<textarea maxlength="500" rows="1" cols="1"></textarea>
							</div>
						</div>
						
						<div class="form-field" id="firma_contrato">
							<span class="lbl">Firma contrato<span class="required-asterisk">*</span>:</span>
							<div class="input">
								<select id="firma" class="selectpicker selected" name="firma">
									<option value="Si" selected>Si</option>
									<option value="No">No</option>								
								</select>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
		<div class="holder_buttons">
			<button class="close-form">Cancelar</button>
			<button type="submit" id="next_form" data-pagina="1">Siguiente</button>
		</div>
	</div>
	
</div>


<div>	
	<div>
		<div class="main-table usersTable">
			<table class="table">
				<thead>
					<tr>
						<th><span class="table-title">Nombre</span></th>
						<th><span class="table-title">Estado</span></th>
						<th><span class="table-title">Fecha alta</span></th>
						<th><span class="table-title">Fecha fin</span></th>
						<th><span class="table-title">Producto</span></th>
						<th><span class="table-title">País</span></th>
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
						<c:when test="${empty clientes}">
							<tr>
								<td><span>No existen clientes.</span></td>
							</tr>
						</c:when>

						<c:otherwise>
							<c:forEach items="${clientes}" var="c">
								<tr class="valid-result" id="row${c.key.id}">
									<td><span></span></td>
									<td><span></span></td>
									<td><span></span></td>
									<td><span></span></td>
									<td><span></span></td>
									<td><span></span></td>
									
									<td><img class="vs" src="../img/vs.png"><a class="lapiz" name="${c.key.id}" href="../clienteModal.do?id=${c.key.id}"	id="lapiz${c.key.id}" data-toggle="modal" data-target="#edit-cliente" ></a>
									<a class="papelera" name="${ckey.id}" data-toggle="modal"	data-target="#confirm-delete" id="papelera${c.key.id}"></a></td>
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


<div class="modal fade" id="edit" tabindex="-1" role="dialog"
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
				<button type="button" class="pink-btn" id="deleteUser">Eliminar</button>
				<button type="button" class="" data-dismiss="modal">Cancelar</button>
			</div>
		</div>
	</div>
</div>

</div>
