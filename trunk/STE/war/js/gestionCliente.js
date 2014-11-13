$(function(){
	
	

	$('#estado').change(function(e){
		//vaciamos el select
		
		
	});

	$('#estado').change(function(e){
			//vaciamos el select
			$("#subestado").empty();
			if ($('#estado').val().indexOf("Fileact") >= 0) {
				$("#subestado").append($("<option></option>").attr("value","Finalizado").text("Finalizado"));
			}else if ($('#estado').val().indexOf("Finalizado") >= 0) {
				$("#subestado").append($("<option></option>").attr("value","default").text("Seleccionar"));
				$("#subestado").append($("<option></option>").attr("value","Eliminado").text("Eliminado"));
				$("#subestado").append($("<option></option>").attr("value","Finalizado").text("Finalizado"));
				$("#subestado").append($("<option></option>").attr("value","Producci\u00f3n").text("Producci\u00f3n"));
			}else  if ($('#estado').val().indexOf("Parado") >= 0) {
				$("#subestado").append($("<option></option>").attr("value","default").text("Seleccionar"));
				$("#subestado").append($("<option></option>").attr("value","Pendiente").text("Pendiente"));
				$("#subestado").append($("<option></option>").attr("value","Parado").text("Parado"));
			}else  if ($('#estado').val().indexOf("Implementaci") >= 0) {
				$("#subestado").append($("<option></option>").attr("value","default").text("Seleccionar"));
				$("#subestado").append($("<option></option>").attr("value","Producci\u00f3n").text("Producci\u00f3n"));
				$("#subestado").append($("<option></option>").attr("value","Pruebas").text("Pruebas"));
			}
			
			if ($('#estado').val().indexOf("Parado") >= 0) {
				$('#firma_contrato').css("display","none");
			}else{
				$('#firma_contrato').css("display","inline-block");
			}
			
			$('#subestado').selectpicker('refresh');
	});

});