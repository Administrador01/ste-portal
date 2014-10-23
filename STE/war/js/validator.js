$(function() {
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
