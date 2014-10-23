$(function() {
	initForms();
});

var initForms = function(){
	// Closing and resetting the form.
	$('form').parent().find('button.close-form').off('.close-form').on('click.close-form', function(){
		var $form = $(this).parent().find('form');
		$('#newUserButton').trigger('click');

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
}