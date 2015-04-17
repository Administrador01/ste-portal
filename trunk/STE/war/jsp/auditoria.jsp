<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div id="auditoria">
	<h1>Auditor&iacute;a</h1>
	<span class="btn-atras" onclick="window.location.href='javascript:window.history.go(-1);'"></span>
	<hr />
	<div class="breadcrumbs"> <span onclick="window.location.href='../../' ">Home</span> > <span> Auditor&iacute;a </span> </div>
	
	<div class="form-field historico">
		<span class="lbl">Hist&oacute;rico:</span>
		<select id="historico" class="long selected selectpicker" name="historico">
			<option value="default">Seleccionar</option>
			<option value="lastweek" <c:if test="${accion eq 'lastweek'}">selected</c:if>>Última semana</option>
			<option value="lastmonth" <c:if test="${accion eq 'lastmonth'}">selected</c:if>>Último mes</option>
			<option value="lastthreemonths" <c:if test="${accion eq 'lastthreemonths'}">selected</c:if>>Últimos tres meses</option>
		</select>
		
		  <button id="excel_btn" onclick="window.location.href='../../logsServlet?accion=xls'"> Descargar Tabla<span class="excel_span"></span> </button>
		
	</div>


  <div>
    <div class="table-responsive usersTable">
      <table class="table">
        <thead>
          <tr>
            <th><span class="table-title">Historial de revisiones</span></th> 
           
          </tr>          
        </thead>
        <tbody id="myTable" cellspacing="0" data-page="${page}" data-lastpage="${lastpage}" data-numpages="${numpages}">
          <c:choose>
            <c:when test="${empty logs}">
              <tr>
                <td><span>No existen logs.</span></td>
              </tr>
            </c:when>
            <c:otherwise>
              <c:forEach items="${logs}" var="log">
                <tr class="valid-result">
                 <c:choose>
           			 <c:when test="${log.fecha_str eq hoy}">
           			 	<td><div class="usuario_fecha"><span>${log.usuario}</span><br><span>Hoy</span></div><div class="log_accion"> <span class="accion">${log.accion}:</span><span>  ${log.entidad} -  ${log.nombre_entidad}</span></div></td>
           			</c:when>
           			<c:when test="${log.fecha_str eq ayer}">
           			 	<td><div class="usuario_fecha"><span>${log.usuario}</span><br><span>Ayer</span></div> <div class="log_accion"><span class="accion">${log.accion}:</span><span>    ${log.entidad} -  ${log.nombre_entidad}</span></div></td>
           			</c:when>
           			<c:when test="${log.fecha_str ne hoy && log.fecha_str ne ayer}">
		                 <td><div class="usuario_fecha"><span>${log.usuario}</span><br><span>${log.fecha_str}</span></div> <div class="log_accion"><span  class="accion">${log.accion}:</span><span>   ${log.entidad} -  ${log.nombre_entidad}</span></div></td>
           			</c:when>
           		 </c:choose>
                </tr>
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </tbody>
      </table>
    </div>
    <div class="col-md-12 text-center">
      <ul class="pagination" id="myPager">
      </ul>
      <span class="pagesummary"></span>
    </div>
  </div>
</div>
