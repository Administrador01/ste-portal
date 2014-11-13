var userBoxSize;
var id;

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
	})

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
	
	
	$('#formButton').click(function(e){
			$('.filter-option').html('Seleccionar');
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
