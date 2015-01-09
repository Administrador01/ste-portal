function sendEditImplementacion(){

	var $form = $("#edit-implementacion-form");
	
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
					
					$form.find('#span_message_modal').html('La implementaci&oacuten ha sido modificado de forma correcta.<br/>En breve volvemos a la p&aacute;gina.');
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
	$('.go_pag2').click(function(e){
		
		var $form = $($('#new-user-form'));
		$form.valid();
		
		
		var hello =$('#pais_imp').val();
		if($('#producto_imp').val()=='default'||$('#cliente_imp').val()=='default'||$('#servicio_imp').val()=='default'||$('#fecha_alta_imp').val()==''||$('#pais_imp').val()=='default'){
			/*$('.error').removeClass('hidden');
			if($('#producto_imp').val()=='default')$('#div_producto_imp').addClass('falta');
			if($('#cliente_imp').val()=='default')$('#div_cliente_imp').addClass('falta');
			if($('#servicio_imp').val()=='default')$('#div_servicio_imp').addClass('falta');
			if($('#fecha_alta_imp').val()=='')$('#div_fecha_alta_imp').addClass('falta');
			if($('#pais_imp').val()=='default')$('#div_pais_imp').addClass('falta');*/
		}else{
			$('#page1_imp').addClass('hidden');
			$('#page2_imp').removeClass('hidden');
			$('#botont1').addClass('hidden');
			$('#botont2').removeClass('hidden');
		}
		
	})
	/*
	$('#producto_imp').on('change', function() {$('#div_producto_imp').removeClass('falta');if($('#producto_imp').val()!='default'&&$('#cliente_imp').val()!='default'&&$('#servicio_imp').val()!='default'&&$('#fecha_alta_imp').val()!=''&&$('#pais_imp').val()!='default')$('.error').addClass('hidden');})
	$('#cliente_imp').on('change', function() {$('#div_cliente_imp').removeClass('falta');if($('#producto_imp').val()!='default'&&$('#cliente_imp').val()!='default'&&$('#servicio_imp').val()!='default'&&$('#fecha_alta_imp').val()!=''&&$('#pais_imp').val()!='default')$('.error').addClass('hidden');})
	$('#servicio_imp').on('change', function() {$('#div_servicio_imp').removeClass('falta');if($('#producto_imp').val()!='default'&&$('#cliente_imp').val()!='default'&&$('#servicio_imp').val()!='default'&&$('#fecha_alta_imp').val()!=''&&$('#pais_imp').val()!='default')$('.error').addClass('hidden');})
	$('#fecha_alta_imp').on('change', function() {$('#div_fecha_alta_imp').removeClass('falta');if($('#producto_imp').val()!='default'&&$('#cliente_imp').val()!='default'&&$('#servicio_imp').val()!='default'&&$('#fecha_alta_imp').val()!=''&&$('#pais_imp').val()!='default')$('.error').addClass('hidden');})
	$('#pais_imp').on('change', function() {$('#div_pais_imp').removeClass('falta');})
	$('#pais_imp').on('change', function() {if($('#producto_imp').val()!='default'&&$('#cliente_imp').val()!='default'&&$('#servicio_imp').val()!='default'&&$('#fecha_alta_imp').val()!=''&&$('#pais_imp').val()!='default')$('.error').addClass('hidden');})
	*/
	
	$('.go_pag1').click(function(e){
		$('#page2_imp').addClass('hidden');
		$('#page1_imp').removeClass('hidden');
		$('#botont2').addClass('hidden');
		$('#botont1').removeClass('hidden');
	})
	
	$('#cliente_imp').on('change', function() {
		var option = $(this).find(":selected");
		$('#input-segmento-implementacion').val(option.data('segmento'));
	})
	$('#servicio_imp').on('change', function() {
		var option = $(this).find(":selected");
		$('#input-servicio-name-implementacion').val(option.data('nombre'));
		$('#input-servicio-tipo-implementacion').val(option.data('tipo'));
	})
	$('#ref_ext').on('change', function() {
		var campo = $(this).val();
		$('#input-servicio-referencia').val(campo);
	})
});