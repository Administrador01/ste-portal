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
<div id="chart_div" style="width:400; height:300"></div>
   <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
   
      // Load the Visualization API and the piechart package.
      google.load('visualization', '1.0', {'packages':['corechart']});
     
      // Set a callback to run when the Google Visualization API is loaded.
      google.setOnLoadCallback(drawChart);


      // Callback that creates and populates a data table, 
      // instantiates the pie chart, passes in the data and
      // draws it.
      function drawChart() {

      // Create the data table.
      var data = new google.visualization.DataTable();
      data.addColumn('string', 'Topping');
      data.addColumn('number', 'Slices');
      data.addRows([
        ['Mushrooms', 3],
        ['Onions', 1],
        ['Olives', 1],
        ['Zucchini', 1],
        ['Pepperoni', 2]
      ]);

      // Set chart options
      var options = {'title':'How Much Pizza I Ate Last Night',
                     'width':400,
                     'height':300};

      // Instantiate and draw our chart, passing in some options.
      var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
      chart.draw(data, options);
    }
    </script>

	<iframe id="iframexls"  width="100%" height="1600px"></iframe>
</div>