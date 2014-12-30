<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="vistaCliente">
	<h1>${cliente.nombre}</h1>
	<span class="btn-atras" onclick="window.location.href='javascript:window.history.go(-1);'"></span>
	<hr/>
	
	
	<div class="breadcrumbs">
		<span onclick="window.location.href='../../'">Home</span> > <span onclick="window.location.href='../clientes.do'"> Clientes </span> > <span> Ficha cliente </span>
	</div>
	<div id="superior">
		<div class="cuadro-cliente left">
			<div>
				<div class="pres-field">
					<span class="lbl">Fecha alta:</span>
					<span class="lbl b right">${cliente.str_fecha_alta}</span>
				</div>
				<div class="pres-field">
					<span class="lbl">Tipo cliente:</span>
					<span class="lbl b right">${cliente.premium}</span>
				</div>
				<div class="pres-field">
					<span class="lbl">Segmento:</span>
					<span class="lbl b right">${cliente.tipo_cliente}</span>
				</div>
				<hr>
				<div class="limpiar"></div>
				<div class="pres-field boton">
					<button id="boton-cli" onclick="window.location.href='../../gestionCliente.do?idCli=${cliente.key.id}'"> Gestionar Cliente </button>
				</div>
			</div>
		</div>
		<div class="cuadro-cliente right">
			<div>
				<div class="pres-field big">
					<span class="lbl">Soportes:</span>
					<a class="right-button ${existeS == true ? '' : 'desactivado'}" onclick="window.location.href='../../soporte.do?idCli=${cliente.key.id}'" ${existeS == true ? '' : 'disabled'}> Gestionar soportes </a>
				</div>
				<hr>
				<div class="pres-field big last">
					<span class="lbl">Pruebas:</span>
					<a class="right-button ${existeP == true ? '' : 'desactivado'}" onclick="window.location.href='../../pruebas.do?idCli=${cliente.key.id}'" ${existeP == true ? '' : 'disabled'}> Gestionar pruebas </a>
				</div>
				<hr>
			</div>
		</div>
	</div>

	<div class="limpiar"></div>
	
	<h2>Implementaciones asociadas</h2>
	<hr>
	<div class="main-table usersTable">
		<table class="table">
			<thead>
				<tr>
					<th>
						<span class="table-title">PRODUCTO</span>
					</th>
					<th>
						<span class="table-title">PAIS</span>
					</th>
					<th>
						<span class="table-title">FORMATO</span>
					</th>
					<th>
						<span class="table-title">SERVICIO</span>
					</th>
					<th>
						<span class="table-title">ESTADO</span>
					</th>
					<th>
						<span class="table-title">GESTOR</span>
					</th>
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
								<td><span>${s.producto}</span></td>
								<td><span>${s.pais}</span></td>
								<td>unknow</td>
								<td><span>
								<c:forEach items="${servicios}" var="t">
									
									<c:choose>
										<c:when test="${t.key.id==s.servicio_id}">
											${t.name}
										</c:when>
										<c:otherwise>
										</c:otherwise>
									</c:choose>	
								</c:forEach>
								</span></td>
								<td><span>${s.estado}</span></td>
								
								<td><span>${s.gestor_gcs}</span></td>
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