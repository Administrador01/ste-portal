var userBoxSize;
var id;

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
	
	
	$('#tipo_cliente').on('change', function(e) {
		var val = $('#tipo_cliente').val();
		var trs = $('#myTable').find('tr');
		var a;
		if (val=="Premium")
			for (a=0; a<=trs.length;a++){
				if (!$(trs[a]).hasClass('premium'))
					$(trs[a]).addClass('hidden');
			}
				
		else{
			for (a=0; a<=trs.length;a++)
				$(trs[a]).removeClass('hidden');
		}
		
		$('#myTable').paginateMe({
			pagerSelector : '#myPager',
			showPrevNext : true,
			hidePageNumbers : false,
			perPage : 10
		})
			
	});
	
	$('#tip_crit').on('change', function(e) {		
		var val = $('#tip_crit').val();
		var cajas = $('.client_box');
		var a;
		if (val=="Premium"){
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
					$('#submit_form_client').hide(0);
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
					$('#submit_form_support').hide(0);
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
					$('#submit_form_test').hide(0);
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
					$('.submit_form_implementacion').hide(0);
					$('.close-form').hide(0);
					$form.find('#span_message').html('La implementaci&oacuten ha sido creada de forma correcta.<br/>En breve volvemos a la p&aacute;gina.');
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
