function sendEditPrueba(){

	var $form = $("#edit-prueba-form");
	
	if($form.valid()){			
		
		var postData = $form.serialize() + "&accion=update&id="+id;
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
					$form.find('#span_message_modal').html('La prueba ha sido modificado de forma correcta.<br/>En breve volvemos a la p&aacute;gina.');
					$('#modal-footer_submit').css('display','none');
					$('#message_div_modal').css('display','block').removeClass("error").addClass("success");;

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
	$('#pruebas').on('click','#deletePrueba', function (e){
		
		 var formURL = "/pruebaServlet?";
		 var postData="accion=delete&id="+ id;
		 $.ajax({
			url : formURL,
			type: "POST",
			data : postData,
			success:function(data, textStatus, jqXHR) 
			{
				$('#row'+id).fadeOut("fast", function(){
					$(this).remove();
					$('#myTable').paginateMe({
						pagerSelector : '#myPager',
						showPrevNext : true,
						hidePageNumbers : false,
						perPage : 10
					});
				});
				$('#confirm-delete').modal('hide');	        	
			}
		});
	});
	
	$('#cliente-soporte').on('change', function() {
		//console.log($(this).find(":selected"));
		var option = $(this).find(":selected");
		//console.log($(option));
		//console.log(option.data('premium'));
		//console.log(option.data('segmento'));
		
		$('#input-premium-soporte').val(option.data('premium'));
		$('#input-segmento-soporte').val(option.data('segmento'));
		$('#client-id-input').val(option.data('clientid'));
	})
	
	$('#pruebas').on('click','#test_filter_button', function (){

		var val_client_form = $('#cliente-filtro').val();
		var val_entorno_form = $('#entorno-filtro').val();
		var val_estado_form = $('#estado-filtro').val();

		
		//console.log(client_form);
	//	console.log(entorno_form);
		//console.log(estado_form);


		var trs = $('#myTable').find('tr');

		var entradas = [];
		for (a=0; a<=trs.length;a++){
			entradas[a]= $(trs[a]);
		}
		
		for (a=0; a<=trs.length;a++){
			$(trs[a]).removeClass('hidden');
		}

	//	console.log(entradas[2].data('entorno'));	

		if (val_client_form!="" && val_client_form!=null){
			for (a=0; a<=trs.length;a++){
				if (entradas[a].data('nombrecliente')!=val_client_form)
					$(trs[a]).addClass('hidden');
			}
		}

		if (val_entorno_form!="" && val_entorno_form!=null){
			for (a=0; a<=trs.length;a++){
				if (entradas[a].data('entorno')!=val_entorno_form)
					$(trs[a]).addClass('hidden');
			}
		}
		
		if (val_estado_form!="" && val_estado_form!=null){
			for (a=0; a<=trs.length;a++){
				if (entradas[a].data('estado')!=val_estado_form)
					$(trs[a]).addClass('hidden');
			}
		}
		
		

	})
	
	
});