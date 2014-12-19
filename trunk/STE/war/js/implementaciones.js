$(function(){
	$('.go_pag2').click(function(e){

		if($('#producto_imp').val()=='default'||$('#cliente_imp').val()=='default'||$('#servicio_imp').val()=='default'||$('#fecha_alta_imp').val()==''||$('#pais_imp').val()==''){
			$('.error').removeClass('hidden');
			if($('#producto_imp').val()=='default')$('#div_producto_imp').addClass('falta');
			if($('#cliente_imp').val()=='default')$('#div_cliente_imp').addClass('falta');
			if($('#servicio_imp').val()=='default')$('#div_servicio_imp').addClass('falta');
			if($('#fecha_alta_imp').val()=='')$('#div_fecha_alta_imp').addClass('falta');
			if($('#pais_imp').val()=='')$('#div_pais_imp').addClass('falta');
		}else{
			$('.error').addClass('hidden');
			$('#page1_imp').addClass('hidden');
			$('#page2_imp').removeClass('hidden');
		}
		
	})
	
	$('#producto_imp').on('change', function() {$('#div_producto_imp').removeClass('falta');if($('#producto_imp').val()!='default'&&$('#cliente_imp').val()!='default'&&$('#servicio_imp').val()!='default'&&$('#fecha_alta_imp').val()!=''&&$('#pais_imp').val()!='')$('.error').addClass('hidden');})
	$('#cliente_imp').on('change', function() {$('#div_cliente_imp').removeClass('falta');if($('#producto_imp').val()!='default'&&$('#cliente_imp').val()!='default'&&$('#servicio_imp').val()!='default'&&$('#fecha_alta_imp').val()!=''&&$('#pais_imp').val()!='')$('.error').addClass('hidden');})
	$('#servicio_imp').on('change', function() {$('#div_servicio_imp').removeClass('falta');if($('#producto_imp').val()!='default'&&$('#cliente_imp').val()!='default'&&$('#servicio_imp').val()!='default'&&$('#fecha_alta_imp').val()!=''&&$('#pais_imp').val()!='')$('.error').addClass('hidden');})
	$('#fecha_alta_imp').on('change', function() {$('#div_fecha_alta_imp').removeClass('falta');if($('#producto_imp').val()!='default'&&$('#cliente_imp').val()!='default'&&$('#servicio_imp').val()!='default'&&$('#fecha_alta_imp').val()!=''&&$('#pais_imp').val()!='')$('.error').addClass('hidden');})
	$('#pais_imp').on('click', function() {$('#div_pais_imp').removeClass('falta');})
	$('#pais_imp').on('change', function() {if($('#producto_imp').val()!='default'&&$('#cliente_imp').val()!='default'&&$('#servicio_imp').val()!='default'&&$('#fecha_alta_imp').val()!=''&&$('#pais_imp').val()!='')$('.error').addClass('hidden');})
	$('.go_pag1').click(function(e){
		$('#page2_imp').addClass('hidden');
		$('#page1_imp').removeClass('hidden');
	})
	
	$('#cliente_imp').on('change', function() {
		var option = $(this).find(":selected");
		$('#input-segmento-implementacion').val(option.data('segmento'));
		$('#client-id-input').val(option.data('clientid'));
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