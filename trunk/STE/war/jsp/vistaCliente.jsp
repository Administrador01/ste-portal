
<div id="vistaCliente">
	<h1>${cliente.nombre}</h1>
	<span class="btn-atras" onclick="window.location.href='../clientes.do'"></span>
	<hr/>
	
	
	<div class="breadcrumbs">
		<span onclick="window.location.href='../../'">Home</span> > <span onclick="window.location.href='../clientes.do'"> Clientes </span> > <span> Ficha cliente </span>
	</div>
	
	<!--
	
	
	
	${existeS}
	${existeP}
	-->
	<div>
		<div class="vista-cliente left">
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
			</div>
		</div>
		
		<div class="vista-cliente right">
			<div>
			<span class="lbl">Fecha alta</span>
		
			</div>
		</div>	
	</div>
	
	<div class="limpiar"></div>
	<h2>Implementaciones asociadas</h2>
	<hr/>
</div>