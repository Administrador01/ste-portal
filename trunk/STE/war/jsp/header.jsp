<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- Menu de STE -->
<div class="header-layout">
	<div class="header-container">
		<img class="main-logo" src="../img/ste-logo.png"/>	
		<div class="main-menu">
			<ul class="menu">
				<li class="home">
					<a href="${sessionScope.entorno}">
						<img src="../img/home.png"/>
					</a>
				</li>
				
				<li class="menu">
					<a class="no-underline"  href="${entorno}/gestionCliente.do">
						<span class="icon-24 clientes-24x"></span> 
						<span class="menu-title">Clientes</span>
					</a>
				</li>
				
				<li class="menu">
					<a class="no-underline" href="#">
						<span class="icon-24 pruebas-24x"></span>
						<span class="menu-title">Pruebas</span>
					</a>
				</li>
				
				<li class="menu">
					<a class="no-underline" href="#">
						<span class="icon-24 servicios-24x"></span>
						<span class="menu-title">Servicios</span>
					</a>
				</li>
				
				<li class="menu">
					<a class="no-underline" href="#">
						<span class="icon-24 informes-24x"></span>
						<span class="menu-title">Informes</span>
					</a>
				</li>
				
				<li class="menu">
					<a class="no-underline" href="${entorno}/soporte.do">
						<span class="icon-24 soporte-24x"></span>
						<span class="menu-title">Soporte</span>
					</a>
				</li>
				
				<li class="menu">
					<a class="no-underline" href="#">
						<span class="icon-24 documentacion-24x"></span>
						<span class="menu-title">Documentación</span>
					</a>
				</li>
			</ul>		
		</div>	
	</div>
</div>

<!-- Menu de STE -->