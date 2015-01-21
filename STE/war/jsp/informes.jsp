<div id="informes">

	<div class="form-field-fecha-prueba">
		<span class="lbl">Fecha desde:</span>
		<input type="text" value="" size="16" class="datepicker fromTo" data-target-id='fecha-hasta' name="fecha-desde" id="fecha-desde" >					
	</div>
	
	<div id="form-field-fecha-prueba-hasta">
		<span class="lbl">Fecha hasta:</span>
		<input type="text" value="" size="16" class="datepicker" name="fecha-hasta" id="fecha-hasta" >
	</div>
	
	<select id='variableInf' class="selectpicker selected" name='variableInf'>
		<option value='pruebas'> Pruebas</option>
		<option value='pruebasServ'> Pruebas por servicio</option>
		<option value='subidas'>Subidas a producci&oacuten</option>
		<option value='nuevasimp'>Nuevas implementaciones</option>
		<option value='detpruebas'>Subidas a producci&oacuten</option>
	</select>
	
	<button onclick="verinforme();">  Ver  </button>
	<iframe id="iframexls"  width="100%" height="1600px"></iframe>
</div>