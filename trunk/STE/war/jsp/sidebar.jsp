<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div id="sidebar">
	<div class="side-menu">
		<ul>
			<c:if test="${sessionScope.permiso == 1}">
				<li><a href="${entorno}/admin/users.do"><span class="usuarios">Usuarios</span></a></li>
			</c:if>
			
			<li><a href="#"><span class="demanda">Lorem</span></a></li>
			
			<c:if test="${sessionScope.permiso == 1}">
				<li><a href="#"><span class="informes">Lorem</span></a></li>
				<li><a href="${entorno}/admin/auditoria.do"><span class="logs">Auditoria</span></a></li>	
			</c:if>		
		</ul>
	</div>
</div>