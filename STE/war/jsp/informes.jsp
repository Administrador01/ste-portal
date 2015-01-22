<div id="informes">


	<h1>Informes</h1>
	<span class="btn-atras" onclick="window.location.href='../../'"></span>
<hr />

	<div class="breadcrumbs">
		<span onclick="window.location.href='../../' ">Home</span> > <span> Informes </span>
	</div>
	<hr style='visibility:hidden;'/>
	
	
	<div id='filter-box'>
		<div id="filtro-informe-desde">
			<span class="lbl">Seleccione periodo consulta<span class="required-asterisk">*</span>:</span>
			<input type="text" value="" size="16" class="datepicker fromTo" data-target-id='fecha-hasta' name="fecha-desde" id="fecha-desde" >
		</div>
		
		<div id="filtro-informe-hasta">
			<span class="lbl">Hasta:</span>
			<input type="text" value="" size="16" class="datepicker" name="fecha-hasta" id="fecha-hasta" >
		</div>
		
		<hr style='visibility:hidden;'/>
		<br />
		
		<div id='filtro-informe-variable' >
		<span class="lbl">Seleccionar variable consulta<span class="required-asterisk">*</span>:</span>
		<select id='variableInf' class="selectpicker selected" name='variableInf'>
			<option value='pruebas'> Pruebas</option>
			<option value='pruebasServ'> Pruebas por servicio</option>
			<option value='cliente'> Cliente</option>
			<option value='subidas'>Subidas a producci&oacuten</option>
			<option value='nuevasimp'>Nuevas implementaciones</option>
			<option value='detpruebas'>Detalle pruebas</option>
		</select>
		</div>
		
		<button style="margin-bottom:1%;" onclick="verinforme();">  Ver  </button>	
		
	</div>

	<iframe id="iframexls"  width="100%" height="1600px"></iframe>
</div>