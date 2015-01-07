<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>



<div id="implementacion">

	<h1>Gesti&oacuten servicios</h1>
	<span class="btn-atras" onclick="window.location.href='javascript:window.history.go(-1);'"></span>
	
	<hr />
	
	<div class="breadcrumbs">
			<span onclick="window.location.href='../../' ">Home</span> > <span> Sevicios </span>
	</div>

	<div class="headButtonsBox">
		<button id="excel_btn" onclick=	"window.location.href='../../implementacionServlet?accion=xls'">
			Descargar Tabla<span class="excel_span"></span>
		</button>
	</div>
</div>
		<div class="main-table usersTable">
			<table class="table">
				<thead>
					<tr>
						<th><span class="table-title">Cliente</span></th>
						<th><span class="table-title">Servicio</span></th>
						<th><span class="table-title">Pa&iacutes</span></th>
						<th><span class="table-title">Fecha subida producci&oacuten</span></th>
						<th><span class="table-title">Fecha contraci&oacuten prod</span></th>
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
									<td><span>
										<c:forEach items="${servicios}" var="t">
											<c:choose>
												<c:when test="${t.key.id==s.servicio_id}">
													${t.name}
												</c:when>
											</c:choose>	
										</c:forEach>
									</span></td>
									<td><span>${s.pais}</span></td>
									<td><span>${s.str_fech_subida}</span></td>
									<td><span>${s.str_fech_contratacion}</span></td>
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

