$(function(){
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
}