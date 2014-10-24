$(function() {
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
};$(function(){
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
};var userBoxSize;
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
			var $formButton = $(this);
			if ($formButton.hasClass('white-btn')){
				if ($('.form-holder').css('overflow')=="visible"){
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

	var listElement = $this;
	var perPage = settings.perPage;
	var children = listElement.find(".valid-result");
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

	pager.data("curr", 0);

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
	var ocursinpage = ((currentPage*5)>numItems) ? numItems : (currentPage*5);

	
	$(resumen).html('');
	
	if (numItems>0) {
		if (numItems>=5){
			$(resumen).html('Resultados '+ ((currentPage*5)-4) + " a " + ocursinpage + ' de '+ numItems);
		}else{
			$(resumen).html('Resultados '+ ((currentPage*5)-4) + " a " + ocursinpage + ' de '+ numItems);
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
		previous();
		return false;
	});
	pager.find('li .next_link').click(function() {
		next();
		return false;
	});

	function previous() {
		var goToPage = parseInt(pager.data("curr")) - 1;
		goTo(goToPage);
	}

	function next() {
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
		
		var ocursinpage2 = (((page+1)*5)>numItems) ? numItems : ((page+1)*5);
		
		if (numItems>0) {
			if (numItems>=5){
				$(resumen).html('Resultados '+ (((page+1)*5)-4) + " a " + ocursinpage2 + ' de '+ numItems);
			}else{
				$(resumen).html('Resultados '+ (((page+1)*5)-4) + " a " + ocursinpage2 + ' de '+ numItems);
			}
		} else {
			$(resumen).html('No hay resultados');
		}
		
	}
};

;var normalize = (function() {
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
			console.log($(this).closest('form').attr('action'));
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

;function sendEditUser(){

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
