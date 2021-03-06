$(function() {
	
	$('#historico').on('change', function(e) {
		var accion = $(this).val();
		window.location.replace("./auditoria.do?p="+accion);
	});
});$(function() {
	initForms();
});

var initForms = function(){
	// Closing and resetting the form.
	$('form').parent().find('button.close-form').off('.close-form').on('click.close-form', function(){
		var $form = $(this).parent().find('form');
		$('#formButton').trigger('click');

		return false;
	});

	// Setting labels to checked where needed and click function.
	$('form').find('.radio-container').each(function(){
		var $checkbox = $(this).find('input[type="checkbox"]');
		var $label = $(this).find('label');
		if($checkbox.prop('checked')){
			$label.addClass('checked');
		}
		$label.off('.check-label').on('click.check-label', function(){
			$(this).toggleClass('checked');
		});
	});
}

function resetForm($form) {
	$form.find('input').each(function(){
		if($(this).attr('type') == 'text') {
			$(this).val('');
		} else if (($(this).attr('type') == 'radio') || ($(this).attr('type') == 'checkbox')) {
			$(this).prop('checked', false);
			$(this).parent().find('label').removeClass('checked');
		}
	});
	$form.find('textarea').each(function(){
		$(this).val('');
	});
	var $selects = $form.find('select');
	var selectTotal = $selects.length
	$selects.each(function(i) {
		$(this).find('option:eq(0)').prop('selected', true);
		if(i === selectTotal - 1) {
			// Reset all selectpickers
			$form.find('.selectpicker').selectpicker('refresh');
		}
	});
	var validator = $form.validate();
	validator.resetForm();
	$form.find('.bootstrap-select.error').removeClass('error');
	$form.find('.error-messages').remove();
	
};function sendEditClient(){
	
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

function filteringClient(){
	var $form = $("#client-header-filter");
	
	var postData =  $form.serialize();
	
	window.location = "/gestionCliente.do?"+postData;
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

});;$(function(){
	initDatepickers();
	initSelectpickers();
});

var initDatepickers = function() {
	// init all the datepickers which generally are always inside of a form.
	
	$('.datepicker').each(function(){
		var $datepicker = $(this);
		
		if($datepicker.hasClass('datefuture')) {
			if($datepicker.val()) {
				// already has a date.
				var minDate = new Date(getIsoDate($datepicker.val()));
				$datepicker.datepicker({minDate:minDate});
			} else {
				$datepicker.datepicker({minDate:0});
			}
		} else if($datepicker.hasClass('datepast')) {
			$datepicker.datepicker({maxDate:0});
		} else {
			$datepicker.datepicker();
		}
		if($datepicker.hasClass('fromTo')){
			var $targetDatepicker = $('#'+$datepicker.data('target-id'));
			$datepicker.on('change.fromTo', function(){
				$targetDatepicker.datepicker('option', 'minDate', $datepicker.datepicker('getDate'));
			});
		}
	});
		
}

var initSelectpickers = function() {
	// init all the datepickers which generally are always inside of a form.
	$('html').find('.selectpicker').selectpicker();
}

// Convert a string date dd/mm/yyyy to yyyy/mm/dd.
// IMPORTANT: Use dd/mm/yyyy for dateString.
var getIsoDate = function(dateString) {
	var collection = dateString.split("/");
	var day = collection[0];
	var month = collection[1];
	var year = collection[2];
	var isoDate = year + '/' + month + '/' + day;

	return isoDate;
};function sendCloneImplementacion(){
	var $form = $("#edit-implementacion-form");
	
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
					
					$('#message_div_modal').css('display','block').removeClass("error").addClass("success");
					
					$form.find('#span_message').html('La implementaci&oacuten ha sido duplicada de forma correcta.');
					
					

					setTimeout(function() { 
						$('#message_div_modal').css('display','none');
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

function filteringImplementation(){
	var $form = $("#imp-header-filter");
	var postData =  $form.serialize();
	window.location = "/implementacion.do?"+postData;
}

function sendEditImplementacion(){

	var $form = $("#edit-implementacion-form");
	$('#modal-footer_submit').css('display','none');
	
	if($form.valid()){			
		$('#new_prueba_form_modal').addClass("hidden");
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
					$('#message_div_modal').css('display','block').removeClass("error").addClass("success");
					$form.find('.form-container').find('div:not(#message_div_modal)').hide(0);
					
					$form.find('#span_message').html('La implementaci&oacuten ha sido modificado de forma correcta.<br/>En breve volvemos a la p&aacute;gina.');
					
					

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
	$('#implementaciones').on('click','#deleteImplementacion', function (e){
		
		 var formURL = "/implementacionServlet?";
		 var postData="accion=delete&id="+ id;
		 $.ajax({
			url : formURL,
			type: "POST",
			data : postData,
			success:function(data, textStatus, jqXHR) 
			{
				location.reload();        	
			}
		});
	});
	
	$('#implementaciones').on('click','#restoreImplementacion', function (e){
		
		 var formURL = "/implementacionServlet?";
		 var postData="accion=restore&id="+ id;
		 $.ajax({
			url : formURL,
			type: "POST",
			data : postData,
			success:function(data, textStatus, jqXHR) 
			{
				location.reload();        	
			}
		});
	});

	
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
});;$(function(){
	$('#iframexls').attr('src',"");
	

	
})

function verinforme(){
	var variable =$('#variableInf').find(":selected").val();
	var fechadesde = $('#fecha-desde').val();
	var fechahasta = $('#fecha-hasta').val();
	$('#iframexls').attr('src',"/informeServlet?accion="+variable+"&fechaHasta="+fechahasta+"&fechaDesde="+fechadesde);
};var userBoxSize;
var id;

$.ajaxSetup({ cache:false });


function  changeActionsButtonColor(){
	if ($('#accion_menu').hasClass('white')){
		$('#accion_menu').removeClass('white');
	}else{
		$('#accion_menu').addClass('white');
	}
}

function isIE () {
	  var myNav = navigator.userAgent.toLowerCase();
	  return (myNav.indexOf('msie') != -1) ? parseInt(myNav.split('msie')[1]) : false;
	}

function drawLetters(){
	var isEmpty;
	var cajas = $('.clients_container').children();
	var a=0;
	var letra;
	var letrasValidas="";
	for (a; a<=cajas.length-1; a++){
		var div = $(cajas[a]);
		if (div.hasClass('letter_anchor')){
			isEmpty = true;
			letra = div;
		}else if (div.hasClass('clientes_letra')){
			var clientes = div.children();
			var b=0;
			for (b; b<=clientes.length-1;b++){
				var cliente = $(clientes[b]);
				if (!cliente.hasClass('hidden')){
					isEmpty = false;
				}
			}				
			if (isEmpty==true){
				letra.css('display','none');
				letra.prev().css('display','none');
				div.css('display','none');
				
			}else{
				letra.css('display','block');
				letrasValidas += $(letra.find('span')[0]).text();
				letra.prev().css('display','inline-block');
				div.css('display','inherit');
			}
		}
		
	}
	
	drawbar(letrasValidas);
}

function drawbar(string){
	
	var barra = $('.abc').children();
	var z = 0;
	for (z=0; z<=barra.length-1;z++){
		var letras = $(barra[z]).children();		
		var a=0;
		for (a; a<=letras.length-1;a++){
			var letra_div = $(letras[a]);
			var letra = letra_div[0].id.substring(6,7);
			if (string.indexOf(letra)!=-1){
				letra_div.attr('href','#'+letra+'_anchor');
				letra_div.children().addClass('active');	
				letra_div.children().removeClass('inactive');				

			}else{
				letra_div.removeAttr('href');
				letra_div.children().addClass('inactive');
				letra_div.children().removeClass('active');
			}
		}
	}
	
}

$(document).on('hidden.bs.modal', function (e) {
	$(".modal-content").html();
	$(e.target).removeData('bs.modal');	
});

function showModal(){
	initSelectpickers();
	initDatepickers();
	$('#ajax_loader').css("display","none");
	$('.modal_ajax').css("display","block");	
}

function resetFormSlider(){
	var $slider = $('.form-slider');
	var $buttons = $('.holder_buttons');
	$slider.css("left","0px");
	$buttons.css("bottom","0px");
}

function validate_multiform(form, elements){
	var result = true;
	$.each(elements, function( index, value ){
		if(!(form.validate().element(value))){ // validate the input field
		   //   form.validate().element(value).focusInvalid(); // focus it if it was invalid
		      result = false;
	      }
	})
	
	return result;
	 
}

$(function() {
		
	$('#myTable').paginateMe({
		pagerSelector : '#myPager',
		showPrevNext : true,
		hidePageNumbers : false,
		perPage : 10
	});
	
	$('html').on('click', '.lapiz', function(e) {		
		id= $(this).attr('name');	
	});
	
	$('html').on('click', '.papelera', function(e) {		
		id= $(this).attr('name');	
	});
	
	$('html').on('click', '.subida', function(e) {		
		id= $(this).attr('name');	
	});
	
	$('html').on('loaded.bs.modal', function () {
		showModal();
	});
	
	$("#next_form").on('click', function(e){
		var $slider = $('.form-slider');
		var pagina = $(this).data('pagina');
		var left = $slider.css('left');
		var contenedores = $('.form-slider').find('.form-container');
		var form = $($slider.parent());
		var $prevElement = $(contenedores[pagina-1]);

		var elements = $prevElement.find("input, select");
		var $nextElement = $(contenedores[pagina]);
		var $formholder = $('.form-holder');
		
		if (validate_multiform(form, elements )){
			$('.holder_buttons').css("bottom", $prevElement.outerHeight() - $nextElement.outerHeight());
			$formholder.animate({height: "+=" + ($nextElement.outerHeight() - $prevElement.outerHeight())}, 500);
			$slider.animate({left: "-=962px"}, 500);
			
			$(this).data=pagina+1;
		}
	});
	
	$('#buscador_cliente').on('keyup', function(e) {
		var busqueda = $(this).val();
		var ln_busqueda = busqueda.length;
		var clientes = $('.client_box');
		var a = 0;
		for (a; a<=clientes.length-1;a++){
			if (busqueda.toUpperCase() == $(clientes[a]).children().text().substring(0,ln_busqueda).toUpperCase()){
				$(clientes[a]).removeClass('search_h');
			}else{
				$(clientes[a]).addClass('search_h');
			}
		}
		drawLetters();
	});
	
	$('#tip_crit').on('change', function(e) {		
		var val = $('#tip_crit').val();
		var cajas = $('.client_box');
		var a;
		if (val=="PREMIUM"){
			for (a = 0; a<=cajas.length-1; a++){
				if (!$(cajas[a]).hasClass('tipo_premium'))
					$(cajas[a]).addClass('hidden');
			}
		}else{
			for (a = 0; a<=cajas.length-1; a++){
				$(cajas[a]).removeClass('hidden');	
			} 
		}	
		
		drawLetters();
	});
	
	
	$(window).scroll(function (event) {
	    var scroll = $(window).scrollTop();
	    if (scroll>350){
	    	$('#abc_child_scroll').addClass('inScroll');
	    	$('#abc_child_scroll').removeClass('scroll_hidden');
	    }else{
	    	$('#abc_child_scroll').removeClass('inScroll');
	    	$('#abc_child_scroll').addClass('scroll_hidden');

	    }
	});
		
	
	$('.clients').on('click', '#accion_menu', function(e) {
		 changeActionsButtonColor();
		
	});
	
	// Submit for creating a new user.
	$("#submit_form").on('click',function(e) {
		e.preventDefault(); //STOP default action
		
		
		var $form = $($(this).prev());
		
		if($form.valid()){
						
			var postData = $form.serialize() + "&accion=new";
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
					if ($('.form-holder').height()<190){
						$('.form-holder').height($('.form-holder').height()+35);
					}
					$form.find('.form-container').find('div:not(#message_div)').hide(0);
					$form.find('#span_message').html('El usuario ha sido creado de forma correcta.<br/>En breve volvemos a la p&aacute;gina.');
					$('#message_div').css('display','block').removeClass("error").addClass("success");;

					setTimeout(function() { 
						resetForm($form);
						location.reload();
					}, 1500);
				}else{
					$('#message_div').removeClass("success").addClass("error");
					if ($('.form-holder').height()<190){
						$('.form-holder').height($('.form-holder').height()+35);
					}
					$('#span_message').html(data.error);
					$('#message_div').css('display','block');
				}
			  },
			  error: function(jqXHR, textStatus, errorThrown) 
			  {
				if (errorThrown.length > 0){
					$('#span_message').html(errorThrown);
					$('#message_div').addClass('error').removeClass('success');
				}
			  }
			});
			
		}
		return false;
	});
	
	// Submit for creating a new client.
	$("#submit_form_client").on('click',function(e) {
		e.preventDefault(); //STOP default action
		
		
		var $form = $($(this).prev());
		
		if($form.valid()){
			$('#submit_form_client').hide(0);			
			var postData = $form.serialize() + "&accion=new";
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
					if ($('.form-holder').height()<190){
						$('.form-holder').height($('.form-holder').height()+35);
					}
					$form.find('.form-container').find('div:not(#message_div)').hide(0);
					
					$form.find('#span_message').html('El cliente ha sido creado de forma correcta.<br/>En breve volvemos a la p&aacute;gina.');
					$('#message_div').css('display','block').removeClass("error").addClass("success");;

					setTimeout(function() { 
						resetForm($form);
						location.reload();
					}, 1500);
				}else{
					$('#message_div').removeClass("success").addClass("error");
					if ($('.form-holder').height()<190){
						$('.form-holder').height($('.form-holder').height()+35);
					}
					$('#span_message').html(data.error);
					$('#message_div').css('display','block');
				}
			  },
			  error: function(jqXHR, textStatus, errorThrown) 
			  {
				if (errorThrown.length > 0){
					$('#span_message').html(errorThrown);
					$('#message_div').addClass('error').removeClass('success');
				}
			  }
			});
			
		}
		return false;
	});	
	
	// Submit for creating a new support.
	$("#submit_form_support").on('click',function(e) {
		e.preventDefault(); //STOP default action
		
		
		var $form = $($(this).prev());
		
		if($form.valid()){
			$('#submit_form_support').hide(0);			
			var postData = $form.serialize() + "&accion=new";
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
					if ($('.form-holder').height()<190){
						$('.form-holder').height($('.form-holder').height()+35);
					}
					$form.find('.form-container').find('div:not(#message_div)').hide(0);
					
					$form.find('#span_message').html('El soporte ha sido creado de forma correcta.<br/>En breve volvemos a la p&aacute;gina.');
					$('#message_div').css('display','block').removeClass("error").addClass("success");;

					setTimeout(function() { 
						resetForm($form);
						location.reload();
					}, 1500);
				}else{
					$('#message_div').removeClass("success").addClass("error");
					if ($('.form-holder').height()<190){
						$('.form-holder').height($('.form-holder').height()+35);
					}
					$('#span_message').html(data.error);
					$('#message_div').css('display','block');
				}
			  },
			  error: function(jqXHR, textStatus, errorThrown) 
			  {
				if (errorThrown.length > 0){
					$('#span_message').html(errorThrown);
					$('#message_div').addClass('error').removeClass('success');
				}
			  }
			});
			
		}
		return false;
	});	
	
	//submit method for new test
	
	$("#submit_form_test").on('click',function(e) {
		
		
		e.preventDefault(); //STOP default action
		
		
		var $form = $($(this).prev());
		
		if($form.valid()){
			$('#submit_form_test').hide(0);
			
			var postData = $form.serialize() + "&accion=new";
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
					if ($('.form-holder').height()<190){
						$('.form-holder').height($('.form-holder').height()+35);
					}
					$form.find('.form-container').find('div:not(#message_div)').hide(0);
					$('.close-form').hide(0);
					
					$form.find('#span_message').html('La prueba ha sido creada de forma correcta.<br/>En breve volvemos a la p&aacute;gina.');
					$('#message_div').css('display','block').removeClass("error").addClass("success");;

					setTimeout(function() { 
						resetForm($form);
						location.reload();
					}, 1500);
				}else{
					$('#message_div').removeClass("success").addClass("error");
					if ($('.form-holder').height()<190){
						$('.form-holder').height($('.form-holder').height()+35);
					}
					$('#span_message').html(data.error);
					$('#message_div').css('display','block');
				}
			  },
			  error: function(jqXHR, textStatus, errorThrown) 
			  {
				if (errorThrown.length > 0){
					$('#span_message').html(errorThrown);
					$('#message_div').addClass('error').removeClass('success');
				}
			  }
			});
			
		}
		return false;
	});
	
//submit method for new test
	
	$(".submit_form_implementacion").on('click',function(e) {

		e.preventDefault(); //STOP default action

		
		var $form = $($('#new-user-form'));
		
		if($form.valid()){
			$('.submit_form_implementacion').hide(0);			
			var postData = $form.serialize() + "&accion=new";
			var formURL = $form.attr("action");
			$.ajax(
			{
			  url : formURL,
			  type: "GET",
			  data : postData,
			  cache : false,
			  success:function(data, textStatus, jqXHR) 
			  {
					//data: return data from server
				if (data.success==("true")){
					if ($('.form-holder').height()<190){
						$('.form-holder').height($('.form-holder').height()+35);
					}
					$form.find('.form-container').find('div:not(#message_div)').hide(0);
					
					$('.close-form').hide(0);
					$form.find('#span_message').html('La implementaci&oacuten ha sido creada de forma correcta.<br/>En breve volvemos a la p&aacute;gina.');
					$('#message_div').css('display','block').removeClass("error").addClass("success");;

					setTimeout(function() { 
						resetForm($form);
						window.location = "/implementacion.do";
					}, 1500);
				}else{
					$('#message_div').removeClass("success").addClass("error");
					if ($('.form-holder').height()<190){
						$('.form-holder').height($('.form-holder').height()+35);
					}
					$('#span_message').html(data.error);
					$('#message_div').css('display','block');
				}
			  },
			  error: function(jqXHR, textStatus, errorThrown) 
			  {
				if (errorThrown.length > 0){
					$('#span_message').html(errorThrown);
					$('#message_div').addClass('error').removeClass('success');
				}
			  }
			});
			
		}
		return false;
	});
	
	$('#formButton').click(function(e){
			//$('.filter-option').html('Seleccionar');
			var $formButton = $(this);
			if ($formButton.hasClass('white-btn')){
				if ($('.form-holder').css('overflow')=="visible"){
					resetFormSlider();
					$('.form-holder').css('overflow','hidden');
					userBoxSize = $('.form-holder.open').outerHeight();
					$('.form-holder.open').css('height', userBoxSize);
					setTimeout(function(){
						$('.form-holder.open').removeClass('open').css('height', '0px');
					}, 25);
					setTimeout(function(){
						$('#formButton').removeClass('white-btn');	
						$($('#formButton').children()[0]).removeClass('blue');
						
						
						
	
						var $form = $formButton.parent().find('form');
						resetForm($form);
					}, 1000);
				}
			} else {
				if ($('.form-holder').css('overflow')=="hidden"){
					$('#formButton').addClass('white-btn');
					$($('#formButton').children()[0]).addClass('blue');
					
					$('.form-holder').addClass('open');
					if(userBoxSize > 0) {
						setTimeout(function(){
							$('.form-holder').css('height', userBoxSize);
						}, 25);
					}
					setTimeout(function(){
						$('.form-holder.open').css('height', 'auto');
					}, 1000);
					setTimeout( function(){ 
						$('.form-holder').css('overflow','visible');
	
					  }
					 , 1000 );
				}
			}
		});
	
		
	
});
;$.fn.paginateMe = function(opts) {
	var $this = this, defaults = {
		perPage : 10,
		showPrevNext : false,
		numbersperPage : 10,
		hidePageNumbers : false
	}, settings = $.extend(defaults, opts);

	var old = $(this).data('old');
	
	if (!old){
		var listElement = $this;
		var perPage = settings.perPage;
	
		
		var children = listElement.find(".valid-result").not(".hidden");
		
	
		var pager = $('.pagination');
		var pagerGoto = $('.paginationGoto');
		var resumen = $('.pagesummary');
	
		if (typeof settings.childSelector != "undefined") {
			children = listElement.find(settings.childSelector);
		}
	
		if (typeof settings.pagerSelector != "undefined") {
			pager = $(settings.pagerSelector);
		}
	
		var numItems = children.size();
		var numPages = Math.ceil(numItems / perPage);
		// clean up.
		$(pager).html('');
		
		var page = listElement.data('page');
		var lastpage = listElement.data('lastpage');
		var numpages = listElement.data('numpages');
		//console.log("page - " + page);
		//console.log("lastpage - " + lastpage);
		//console.log("numpages - " + numpages);
		var oldparameters = getParameters();
		var sPath=window.location.pathname;
		var sPage = sPath.substring(sPath.lastIndexOf('/') + 1);
		
		if(numpages > 0) {
			if(page > 0) {
				$('<li><a href="#" class="prev_link"><</a></li>').appendTo(pager);
				$('<li><a href="'+ sPage +'?page=0' + oldparameters + '" class="page_link">1</a></li>').appendTo(pager);
			}	
			$('<li><a href="#" class="page_link active">'+ (page+1) + '</a></li>').appendTo(pager);
			if(page < (numpages-1)) {
				$('<li><a href="' + sPage + '?page='+(numpages-1) + oldparameters + '" class="page_link">' + numpages + '</a></li>').appendTo(pager);
			}
			if(lastpage == false) {
				$('<li><a href="#" class="next_link">></a></li>').appendTo(pager);
			}
			
			if((numpages != null) && (numpages > 1)) {
				$('<span>Ir a p&aacutegina:</span>').appendTo(pagerGoto);
				var opcionespage = "<select class=\"page-select\">";
				
				for(var i=1;i<=numpages;i++){
					if(i == page+1) {
						opcionespage= opcionespage+"<option class=\"pagerselect\" val=\'"+i+"\' selected>"+i+"</option>";
					}
					else {
						opcionespage= opcionespage+"<option class=\"pagerselect\" val=\'"+i+"\'>"+i+"</option>";
					}
				}
				
				opcionespage = opcionespage+"<select>";
				$(opcionespage).appendTo(pagerGoto);
				
				$('.page-select').on('change', function() {
					var page = $(this).val();
					page--;
					if(page >= 0) {				
						var sPath=window.location.pathname;
						var sPage = sPath.substring(sPath.lastIndexOf('/') + 1);
						var location = './' + sPage + '?page=' + page;
						var oldparams = getParameters();
						location = location + oldparams;
						
						window.location = location;		
					}
				});
			}
		}
		else {
			if(page > 0) {
				$('<li><a href="#" class="prev_link"><</a></li>').appendTo(pager);
			}
			if(lastpage == false) {
				$('<li><a href="#" class="next_link">></a></li>').appendTo(pager);
			}
		}
		
		
		pager.find('li .prev_link').click(function() {
			previous();
			return false;
		});
		pager.find('li .next_link').click(function() {
			next();
			return false;
		});
		
		function previous() {
			var page = listElement.data('page');
			if(page > 0) {
				page = page - 1;
				
				var location = './' + sPage + '?page=' + page;
				var oldparams = getParameters();
				location = location + oldparams;
				window.location = location;		
			}
		}
	
		function next() {
			var page = listElement.data('page');
			page = page + 1;		
			var sPath=window.location.pathname;
			var sPage = sPath.substring(sPath.lastIndexOf('/') + 1);
			
			var location = './' + sPage + '?page=' + page;
			var oldparams = getParameters();
			location = location + oldparams;
			
			window.location = location;		
		}
				
	}
	else{
		
		var listElement = $this;
		var perPage = settings.perPage;
	
		
		var children = listElement.find(".valid-result").not(".hidden");
		
		var pager = $('.pagination');
		var resumen = $('.pagesummary');
	
		if (typeof settings.childSelector != "undefined") {
			children = listElement.find(settings.childSelector);
		}
	
		if (typeof settings.pagerSelector != "undefined") {
			pager = $(settings.pagerSelector);
		}
	
		var numItems = children.size();
		var numPages = Math.ceil(numItems / perPage);
		// clean up.
		$(pager).html('');
		
		
		if (settings.showPrevNext) {
			$('<li><a href="#" class="prev_link"><</a></li>').appendTo(pager);
		}
	
		var curr = 0;
		while (numPages > curr && (settings.hidePageNumbers == false)) {
			$('<li><a href="#" class="page_link">' + (curr + 1) + '</a></li>')
					.appendTo(pager);
			curr++;
		}
	
		if (settings.numbersPerPage > 1) {
			$('.page_link').hide();
			$('.page_link').slice(pager.data("curr"), settings.numbersPerPage)
					.show();
		}
	
		if (settings.showPrevNext) {
			$('<li><a href="#" class="next_link">></a></li>').appendTo(pager);
		}
	
		pager.find('.page_link:first').addClass('active');
		pager.find('.prev_link').hide();
		if (numPages <= 1) {
			pager.find('.next_link').hide();
		}
		pager.children().eq(1).addClass("active");
		
		
		
	
		children.hide();
		children.slice(0, perPage).show();
		
		var currentPage = pager.children().eq(1).children().html();
		var ocursinpage = ((currentPage*10)>numItems) ? numItems : (currentPage*10);
	
		
		$(resumen).html('');
		
		if (numItems>0) {
			if (numItems>=10){
				$(resumen).html('Resultados '+ ((currentPage*10)-9) + " a " + ocursinpage + ' de '+ numItems);
			}else{
				$(resumen).html('Resultados '+ ((currentPage*10)-9) + " a " + ocursinpage + ' de '+ numItems);
			}
		} else {
			$(resumen).html('No hay resultados');
		}
			
		
	
		pager.find('li .page_link').click(function() {
			var clickedPage = $(this).html().valueOf() - 1;
			goTo(clickedPage, perPage);
			return false;
		});
		pager.find('li .prev_link').click(function() {
			previous1();
			return false;
		});
		pager.find('li .next_link').click(function() {
			next1();
			return false;
		});
	
		function previous1() {
			var goToPage = parseInt(pager.data("curr")) - 1;
			goTo(goToPage);
		}
	
		function next1() {
			goToPage = parseInt(pager.data("curr")) + 1;
			goTo(goToPage);
		}
	
		function goTo(page) {
			var startAt = page * perPage, endOn = startAt + perPage;
	
			children.css('display', 'none').slice(startAt, endOn).show();
	
			if (page >= 1) {
				pager.find('.prev_link').show();
			} else {
				pager.find('.prev_link').hide();
			}
	
			if (page < (numPages - 1)) {
				pager.find('.next_link').show();
			} else {
				pager.find('.next_link').hide();
			}
	
			pager.data("curr", page);
	
			if (settings.numbersPerPage > 1) {
				$('.page_link').hide();
				$('.page_link').slice(page, settings.numbersPerPage + page)
						.show();
			}
	
			pager.children().removeClass("active");
			pager.children().eq(page + 1).addClass("active");
			
			
			$(resumen).html('');
			
			var ocursinpage2 = (((page+1)*10)>numItems) ? numItems : ((page+1)*10);
			
			if (numItems>0) {
				if (numItems>=10){
					$(resumen).html('Resultados '+ (((page+1)*10)-9) + " a " + ocursinpage2 + ' de '+ numItems);
				}else{
					$(resumen).html('Resultados '+ (((page+1)*10)-9) + " a " + ocursinpage2 + ' de '+ numItems);
				}
			} else {
				$(resumen).html('No hay resultados');
			}
			$.scollTop();
		}
	}
};

function getParameters(){
	var sPath=window.location.search;
	var queryString = sPath.substring(sPath.lastIndexOf("?") + 1);
	var newQueryString = $.map(queryString.split("&"), function(pair) { 
		  var p = pair.split("="); 
		  if(p[0] != "page") {
			  return p.join("=");
		  }			  
	}).join("&");
	
	if((newQueryString != null) && (newQueryString.length > 0)) {
		newQueryString = "&" + newQueryString;
	}
	
	return newQueryString;
}
;function sendEditPrueba(){

	var $form = $("#edit-prueba-form");
	
	
	if($form.valid()){			
		$("#submit_edit_prueba_form").addClass("hidden");
		$('#new_prueba_form_modal').addClass("hidden");
		$('#modal-footer_submit').css('display','none');
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
					$form.find('#span_message_modal').html('La prueba ha sido modificado de forma correcta.<br/>En breve volvemos a la p&aacute;gina.');
					
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

function sendCloneTest(){

	var $form = $("#edit-prueba-form");
	
	
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
					
					$('#message_div_modal').css('display','block').removeClass("error").addClass("success");
					$form.find('#span_message_modal').html('La prueba ha sido duplicada de forma correcta.');
					

					setTimeout(function() { 
						$('#message_div_modal').css('display','none');
						
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

function filtering(){
	var $form = $("#test-header-filter");
	
	var postData =  $form.serialize();
	postData = postData + "&desde-filter="+$('#fecha-desde-filtro').val()+ "&hasta-filter="+$('#fecha-hasta-filtro').val()+ "&premium="+$('#tipo_cliente').val();
	window.location = "/pruebas.do?"+postData;
}
function ajaxImplementaciones(cliente,target){
	target.empty();
	target.selectpicker("render");
	if (cliente!="default"){
		 var formURL = "/pruebaServlet?";
		 var postData="accion=getImpByClient&client="+cliente;
		 $.ajax(			
			{
				url : formURL,
				type: "POST",
				data : postData,
				success:function(data, textStatus, jqXHR) 
				{
					if (data.success=="true"){
						
						var implementations = data.implementaciones[0];
						
						var tamano = implementations.length;
						if(tamano == 0){
							target.append($("<option></option>").attr("value","default").text("No hay implementaciones"));
						}
						for (var i = 0 ; i < tamano; i=i+2){
							var id = implementations[i];
							var name = implementations[i+1];
							target.append($("<option></option>").attr("value",id).text(name));
						}

						
					}
					target.selectpicker("refresh");
				}					
				
			});
	}else{target.append($("<option></option>").attr("value","default").text("-"));}
	target.selectpicker("refresh");
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
	
	
	

	$('#pruebas').on('change','#cliente-pruebas', function (){
		$('#input-premium-prueba').val($("#cliente-pruebas").find(":selected").data('premium'));
		var cliente = $('#cliente-pruebas').val();
		var target = $('#imp-pruebas');
		ajaxImplementaciones(cliente,target);
	});
	$('#pruebas').on('change','#cliente_pru_modal', function (){
		$('#input-premium-prueba-modal').val($("#cliente_pru_modal").find(":selected").data('premium'));
		var cliente = $('#cliente_pru_modal').val();
		var target = $('#imp-pruebas-modal');
		ajaxImplementaciones(cliente,target);
	});
	
	/*
	$('#pruebas').on('click','#test_filter_button', function (){

		var val_client_form = $('#cliente-filtro').val();
		var val_entorno_form = $('#entorno-filtro').val();
		var val_estado_form = $('#estado-filtro').val();
		var val_fecha_desde_form = $('#fecha-desde-filtro').val();
		var val_fecha_hasta_form = $('#fecha-hasta-filtro').val();
		
 
		//console.log(client_form);
		//console.log(entorno_form);
		//console.log(estado_form);

		var trs = $('#myTable').find('tr');

		var entradas = [];
		for (a=0; a<=trs.length;a++){
			entradas[a]= $(trs[a]);
		}
		
		for (a=0; a<trs.length;a++){
			$(trs[a]).removeClass('hidden');
		}

	

		if (val_client_form!="" && val_client_form!=null){
			for (a=0; a<trs.length;a++){
				if (entradas[a].data('nombrecliente')!=val_client_form)
					$(trs[a]).addClass('hidden');
			}
		}

		if (val_entorno_form!="" && val_entorno_form!=null){
			for (a=0; a<trs.length;a++){
				if (entradas[a].data('entorno')!=val_entorno_form)
					$(trs[a]).addClass('hidden');
			}
		}
		
		if (val_estado_form!="" && val_estado_form!=null){
			for (a=0; a<trs.length;a++){
				if (entradas[a].data('estado')!=val_estado_form)
					$(trs[a]).addClass('hidden');
			}
		}
		
		if (val_fecha_desde_form!="" && val_fecha_desde_form!=null){
			for (a=0; a<trs.length;a++){
				
				if (compare_dates(val_fecha_desde_form,entradas[a].data('strfechaestado'))){
					//el dato de la tabla es mayor que el del filtro
					$(trs[a]).addClass('hidden');
				}else{
					//el dato del filtro es mayor que el de la tabla
					
				}
			}
		}
		

		
		if (val_fecha_hasta_form!="" && val_fecha_hasta_form!=null){
			for (a=0; a<=trs.length;a++){
				if (compare_dates(entradas[a].data('strfechaestado'),val_fecha_hasta_form)){
					//el dato de la tabla es mayor que el del filtro
					
					$(trs[a]).addClass('hidden');
				}else{
					//el dato del filtro es mayor que el de la tabla
					
				}
			}
		}
		
		$table.paginateMe({
			pagerSelector : '#myPager',
			showPrevNext : true,
			hidePageNumbers : false,
			perPage : 10
		});

	})
	*/
	
});


/*
function compare_dates(fecha, fecha2)  
{  
  var xMonth=fecha.substring(3, 5);  
  var xDay=fecha.substring(0, 2);  
  var xYear=fecha.substring(6,10);  
  var yMonth=fecha2.substring(3, 5);  
  var yDay=fecha2.substring(0, 2);  
  var yYear=fecha2.substring(6,10);  
  
  if (xYear> yYear)  
  {  
      return(true)  
  }  
  else  
  {  
    if (xYear == yYear)  
    {   
      if (xMonth> yMonth)  
      {  
          return(true)  
      }  
      else  
      {   
        if (xMonth == yMonth)  
        {  
          if (xDay> yDay)  
            return(true);  
          else  
            return(false);  
        }  
        else  
          return(false);  
      }  
    }  
    else  
      return(false);  
  }  
};*/;var normalize = (function() {
	var from = "ÃÀÁÄÂÈÉËÊÌÍÏÎÒÓÖÔÙÚÜÛãàáäâèéëêìíïîòóöôùúüûÑñÇç",
	  to   = "AAAAAEEEEIIIIOOOOUUUUaaaaaeeeeiiiioooouuuunncc",
	  mapping = {};

	for(var i = 0, j = from.length; i < j; i++ )
	  mapping[ from.charAt( i ) ] = to.charAt( i );

	return function( str ) {
	  var ret = [];
	  for( var i = 0, j = str.length; i < j; i++ ) {
	      var c = str.charAt( i );
	      if( mapping.hasOwnProperty( str.charAt( i ) ) )
	          ret.push( mapping[ c ] );
	      else
	          ret.push( c );
	  }
	  return ret.join( '' );
	}
})();


$(function() {
	$('.search').on('keyup', function(e) { 
		
		var $table = $(this).closest('table').find('tbody').first();	
		var $table_results = $table.children();
		
		var $current_input = $(this);

		$(this).closest('tr').find('.search-th').find('input').each(function(){
			if (!($(this).is($current_input)) && $(this).val().length != 0) {
				
			    multipleFilter = true;
			}
		});

		for (var e=0; e<$table_results.length; e++){
			var isValid = true;
			var $linea = $($table_results[e]);	
			$(this).closest('tr').find('.search-th').find('input').each(function(){
				if ($(this).val().length != 0) {
					var text = normalize($(this).val().toLowerCase());
					var columna = $(this).attr("class").split("col")[1];
					if ($(this).hasClass('search_anywhere'))
						columna = columna.split(" ")[0];
				    var cont = $($linea.children()[columna]).children().html().toLowerCase();
				    var textLength = text.length;
				    
				    if ($(this).hasClass('search_anywhere')){
				    	if(cont.indexOf(text)==-1){
					    	isValid = false;
						}
				    }else{
				    	if(text != cont.substring(0, textLength)){
					    	isValid = false;
						}
				    }				    
				}
			});

			if (isValid){
				$linea.show().addClass('valid-result');
			}else{
				$linea.hide().removeClass('valid-result');
			}			
		}
		// re-paginate
		$table.paginateMe({
			pagerSelector : '#myPager',
			showPrevNext : true,
			hidePageNumbers : false,
			perPage : 10
		});
	});
});;$(function() {
	$('#sidebar').hover(
		function() {
			// mouse on
			$(this).addClass('hovering');
		}, function() {
			// mouse out
			$(this).removeClass('hovering');
		}
	);
});;$(function(){
	
	
	
	/* main menu text search focused */
	var initialSearchWidthDesktop = $('.body-header .main-menu li.search input[type="search"]').css('width');
	$('.body-header .main-menu li.search input[type="search"]').focus(function(){
		$(this).animate({'width': '180px'}, 'fast');
	}).focusout(function(){
		$(this).animate({'width': initialSearchWidthDesktop}, 'fast');
	});
	
	/* main menu search icon handler */ 
	$('.body-header .main-menu li.search img').click(function(){
		if($('.body-header .main-menu li.search input[type="search"]').val() == ''){
			$('.body-header .main-menu li.search input[type="search"]').focus();
		} else {
			//console.log($(this).closest('form').attr('action'));
			$(this).closest('form').submit();
		}
	});
	
	/* tablet/phone menu handler */
	$('.navbar-trigger').click(function(){
		$('.tablet-header .main-menu').slideToggle();
	});
	
	$('.menu-trigger').click(function(){
		var $element = $($(this).attr('data-target'));
		if($element.is(':visible')){
			$(this).parent().find('.open').removeClass('open').next().slideUp();
			$(this).removeClass('open');
			$element.slideUp();
		} else {
			$(this).parent().find('.open').removeClass('open').next().slideUp();
			$(this).addClass('open');
			$element.slideDown();
		}
	});
	
	/* tablet/phone main menu text search focused */
	var initialSearchWidthTablet = $('header.tablet-header .main-menu>div.header-buttons input[type="search"]').css('width');
	var initialAccesoHtml = $('header.tablet-header .main-menu>div.header-buttons a.btn.success').html();
	
	$('header.tablet-header .main-menu>div.header-buttons img').click(function(){
		$(this).parent().find('input[type="search"]').focus();
		return false;
	});
	
	var textWidthHandle;
	$('header.tablet-header .main-menu>div.header-buttons input[type="search"]').focus(function(){
		clearTimeout(textWidthHandle);
		
		var $search = $(this);
		$search.parent().find('img').attr('src', 'img/icon-text-search-close.png').css('top', '5px').unbind('click').click(function(){
			$search.val('').focus();
			return false;
		});
		
		var $alta = $search.parent().find('a.btn:not(.success)');
		var $acceso = $search.parent().find('a.btn.success');
		if($(window).width() <= 480){
			$acceso.html('<i class="icon-16 white ico-acceso-clientes"></i>');
			$alta.html($alta.attr('data-smalltext'));
		}
		$search.animate({'width': $search.parent().width() - 90 - $alta.width() - $acceso.width()}, 'fast');
		
	}).focusout(function(){
		var $search = $(this);
		textWidthHandle = setTimeout(function(){
			$search.parent().find('img').attr('src', 'img/icon-text-search-big.png').css('top', '8px').unbind('click').click(function(){
				$search.focus();
				return false;
			});
			
			var $alta = $search.parent().find('a.btn:not(.success)');
			$alta.html($alta.attr('data-fulltext'));
			
			$search.parent().find('a.btn.success').html(initialAccesoHtml);
			$search.animate({'width': initialSearchWidthTablet}, 'fast');
		}, 200);
	});
	
	
	
	
	
});

;

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
});;function sendEditUser(){

	var $form = $("#edit-user-form");
	
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
					$form.find('#span_message_modal').html('El usuario ha sido modificado de forma correcta.<br/>En breve volvemos a la p&aacute;gina.');
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

$(function() {
	$('.alta_usuario').on('click','#deleteUser', function (e){
	
		 var formURL = "/usersServlet?";
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
	})
});;$(function() {
	$.extend($.validator.messages, {
		required: "Este campo es obligatorio.",
		remote: "Por favor, rellena este campo.",
		email: "Por favor, escribe una direcci&oacuten de correo v&aacutelida.(Terminada en @bbva.com)",
		url: "Por favor, escribe una URL válida.",
		date: "Por favor, escribe una fecha válida.",
		dateISO: "Por favor, escribe una fecha (ISO) válida.",
		number: "Por favor, escribe un n&uacutemero v&aacutelido.",
		digits: "Por favor, escribe s&oacutelo dígitos.",
		creditcard: "Por favor, escribe un número de tarjeta válido.",
		equalTo: "Por favor, escribe el mismo valor de nuevo.",
		extension: "Por favor, escribe un valor con una extensión aceptada.",
		maxlength: $.validator.format("Por favor, no escribas más de {0} caracteres."),
		minlength: $.validator.format("Por favor, no escribas menos de {0} caracteres."),
		rangelength: $.validator.format("Por favor, escribe un valor entre {0} y {1} caracteres."),
		range: $.validator.format("Por favor, escribe un valor entre {0} y {1}."),
		max: $.validator.format("Por favor, escribe un valor menor o igual a {0}."),
		min: $.validator.format("Por favor, escribe un valor mayor o igual a {0}."),
		nifES: "Por favor, escribe un NIF válido.",
		nieES: "Por favor, escribe un NIE válido.",
		cifES: "Por favor, escribe un CIF válido."
	});

	
	
	$.validator.addMethod("money", function(value, element) {
		 return this.optional(element) || /^-?(?:\d+|\d{1,3}(?:[\s\.,]\d{3})+)(?:[\.,]\d+)?$/.test(value);
		}, "Por favor, introduce una cifra v&aacute;lida");
	
	// One to rule the ... selects
	$.validator.addMethod("selected", function(value, element){
		var valid = false;

		if(value != 'default') {
			valid = true;
			$(element).parent().find('.bootstrap-select').removeClass('error');
		}else{
			$(element).parent().find('.bootstrap-select').addClass('error');
		}
		return valid;
	}, "Por favor, selecciona un valor.");

	$.validator.addMethod('require-one', function(value, element) {
		var valid = false;
		var selector = 'input[name=' + $(element).attr('name') + ']';

		$(element).closest('form').find(selector).each(function() {
			if($(this).is(":checked")) {
				valid = true;
			}
		});
		
		var modal =  $(element).attr('id').indexOf('modal') != -1;
		if (valid==false) {
			if (modal) {
				$('#message_div_cliente_modal').addClass('error');
				$('#span_message_cliente_modal').text('Debe seleccionar un pa\u00EDs');
			} else {
				$('#message_div_cliente').addClass('error');
				$('#span_message_cliente').text('Debe seleccionar un pa\u00EDs');
			}
		} else {
			if (modal) {
				$('#message_div_cliente_modal').removeClass('error');
				$('#span_message_cliente_modal').text('');
			} else {
				$('#message_div_cliente').removeClass('error');
				$('#span_message_cliente').text('');
				
			}
		}
		
			
			
		return valid;
	},'Por favor, selecciona una opci&oacute;n.');

	initValidator();
});


var initValidator = function() {
	// Setup form validation on all the form elements.
	$('form').each(function(){
		$(this).validate({
			ignore: ":hidden:not(select):not([type='radio']):not([type='checkbox'])",
			focusCleanup: false,
			onkeyup: false,
		    submitHandler: function(form) {
		        form.submit();
		    },
		    errorPlacement: function(error, $element) {
		    	var $target = $element.parent();
		    	$target.find('.error-messages').remove();
				var $container = '';

				if($element.hasClass('selectpicker')){
					$element = $target.find('.bootstrap-select');
				}
				// overwritable when using the tag data-error-show-style = tooltip
				if(($element.is(':checkbox') || $element.is(':radio')) && $element.parent().hasClass('radio-container')) {
					var $target = $element.closest('.radio-container-holder');
					var $container = $target.find('#error-messages');
					if($container.length == 0){
						$container = $('<div id="error-messages" class="block-error server-errors"><ul></ul></div>');
						$element.closest('.radio-container-holder').prepend($container);
					}

					
					// Create error element and append it to error container
					var $errorelement = $('<li>');
					$errorelement.append(error);
					$container.find('ul').append($errorelement);

				} else {
					$container = $('<div class="error-messages"><ul></ul></div>');
					$target.css({position:'relative'}).prepend($container);
				}
				
				if (!$element.hasClass("no_message_error")){
					// Create error element and append it to error container
					var $errorelement = $('<li>');
					$errorelement.append(error);
					$container.find('ul').append($errorelement);
					var leftPosition = 0;
					if ($element.outerWidth() < $container.outerWidth()) {
						// Error message is bigger than element.
						leftPosition = ($element.outerWidth() - $container.outerWidth()) / 2;
					} else if ($element.outerWidth() > $container.outerWidth()) {
						// Error message is smaller than element.
						leftPosition = ($element.outerWidth() - $container.outerWidth()) / 2;
					}
					// In two steps so the element can have a real height to work with.
					$container.css({left: ($element.position().left + leftPosition) + 'px', marginLeft: $element.css('margin-left'), maxWidth:'200px'});
					$container.css({top:'-' + ($container.outerHeight() + 10) + 'px'});

					$element.hover(
					  function() {
					    $container.addClass("hover");
					  }, function() {
					    $container.removeClass("hover");
					  }
					);
				}
				
			},
			success: function(label) {
				label.closest('.error-messages').remove();
			}
		});
	});
	
	
}
