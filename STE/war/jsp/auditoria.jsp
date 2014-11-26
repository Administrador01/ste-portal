<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



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