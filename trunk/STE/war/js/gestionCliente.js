function sendEditClient(){
	
	editForm($("#edit-cliente-form"),"cliente")
}

function editForm($form, entidad){
	
	if($form.valid()){			
		
		var postData = $form.serialize() + "&accion=update";
		var formURL = $form.attr("action");
		$.ajax(
		{
		  url : formURL,
		  type: "GET",
		  data : postData,
		  success:function(data, textStatus, jqXHR) 
		  {
				//data: return data from server
			  if (data.success==("true")){
					if ($('.edit-user-form-holder').height()<190){
						$('.edit-user-form-holder').height($('.edit-user-form-holder').height()+35);
					}
					$form.find('.form-container').find('div:not(#message_div_modal)').hide(0);
					$form.find('#span_message_modal').html('El '+ entidad +' ha sido modificado de forma correcta.<br/>En breve volvemos a la p&aacute;gina.');
					$('#modal-footer_submit').css('display','none');
					$('#message_div_modal').css('display','block').removeClass("error").addClass("success");

					setTimeout(function() { 
						resetForm($form);
						location.reload();
					}, 1500);
				}else{
					$('#message_div_modal').removeClass("success").addClass("error");
					if ($('.edit-user-form-holder').height()<190){
						$('.edit-user-form-holder').height($('.edit-user-form-holder').height()+35);
					}
					$('#span_message_modal').html(data.error);
					$('#message_div_modal').css('display','block');
				}
		  }
		},'html');
	}
}

$(function(){
	
	$('#gestion_cliente').on('click','#deleteClient', function (e){
		
		 var formURL = "/clientServlet?";
		 var postData="accion=delete&id="+ id;
		 $.ajax({
			url : formURL,
			type: "POST",
			data : postData,
			success:function(data, textStatus, jqXHR) 
			{
				$('#row'+id).fadeOut("fast", function(){
					$(this).remove();
					location.reload(); 
				});
				$('#confirm-delete').modal('hide');	        	
			}
		});
	})
	
		$('#gestion_cliente').on('click','#restoreClient', function (e){
		
		 var formURL = "/clientServlet?";
		 var postData="accion=restore&id="+ id;
		 $.ajax({
			url : formURL,
			type: "POST",
			data : postData,
			success:function(data, textStatus, jqXHR) 
			{
				$('#row'+id).fadeOut("fast", function(){
					$(this).remove();
					location.reload(); 
				});
				$('#confirm-restore').modal('hide');	        	
			}
		});
	})

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