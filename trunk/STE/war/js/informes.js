$(function(){
	$('#iframexls').attr('src',"");
	

	
})

function verinforme(){
	var variable =$('#variableInf').find(":selected").val();
	var fechadesde = $('#fecha-desde').val();
	var fechahasta = $('#fecha-hasta').val();
	$('#iframexls').attr('src',"/informeServlet?accion="+variable+"&fechaHasta="+fechahasta+"&fechaDesde="+fechadesde);
}