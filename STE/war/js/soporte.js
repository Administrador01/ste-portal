

function filteringSupport(){
	var $form = $("#support-header-filter");
	
	var postData =  $form.serialize();
	postData = postData + "&premium="+$('#tipo_cliente').val();
	window.location = "/soporte.do?"+postData;
}

function sendEditSoporte(){

	var $form = $("#edit-soporte-form");
	
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
					$form.find('#message_div_modal').removeClass("hidden");
					$form.find('.form-container').find('div:not(#message_div_modal)').hide(0);
					$('#new_prueba_form_modal').addClass("hidden");
					$form.find('#span_message_modal').html('El soporte ha sido modificado de forma correcta.<br/>En breve volvemos a la p&aacute;gina.');
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

function sendCloneSoporte(){

	var $form = $("#edit-soporte-form");
	
	if($form.valid()){			
		
		var postData = $form.serialize() + "&accion=clone";
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
					$form.find('#message_div_modal').removeClass("hidden");
					
					$form.find('#span_message_modal').html('El soporte ha sido duplicado de forma correcta.');
					
					$('#message_div_modal').css('display','block').removeClass("error").addClass("success");;

					setTimeout(function() { 
						$form.find('#message_div_modal').addClass("hidden");
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
	$('#soporte').on('click','#deleteSoporte', function (e){
		
		 var formURL = "/soporteServlet?";
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
	});
	
	$('#soporte').on('click','#restoreSoporte', function (e){
		
		 var formURL = "/soporteServlet?";
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
	
	$('#cliente-soporte').on('change', function() {
		//console.log($(this).find(":selected"));
		var option = $(this).find(":selected");
		//console.log($(option));
		//console.log(option.data('premium'));
		//console.log(option.data('clientid'));
		
		$('#input-premium-soporte').val(option.data('premium'));
		$('#input-segmento-soporte').val(option.data('segmento'));
		$('#client-id-input').val(option.data('clientid'));
	})
});