$(function(){

	// run function on all dialog opens
	$(document).on('dialogopen', '.ui-dialog', function(event, ui) {
		fluidDialog();
	});

	// remove window resize namespace
	$(document).on('dialogclose', '.ui-dialog', function(event, ui) {
		$(window).off('resize.responsive');
	});

	var fluidDialog = function() {
		// each open dialog
		$('.ui-dialog:visible').each(function() {
			var $this = $(this);
			var $dialog = $this.find('.ui-dialog-content');
			// if fluid option == true
			if ($dialog.dialog('option', 'maxWidth') && $dialog.dialog('option', 'width')) {
				// fix maxWidth bug
				$this.css('max-width', $dialog.dialog('option', 'maxWidth'));
				//reposition dialog
				$dialog.dialog('option', 'position', $dialog.dialog('option', 'position'));
			}

			if ($dialog.dialog('option', 'fluid')) {
				// namespace window resize
				$(window).on('resize.responsive', function() {
					var wWidth = $(window).width();
					// check window width against dialog width
					if (wWidth < $dialog.dialog('option', 'maxWidth') + 50) {
						// keep dialog from filling entire screen
						$this.css('width', '90%');

					}
					
					if($dialog.dialog('option', 'height') === undefined){
						var hHeight = $(window).height();
						// check window height against dialog height
						if (hHeight < $this.height() + 50) {
							// keep dialog from filling entire screen
							$this.css('height', '90%');
							$dialog.css('max-height', $this.height() - $this.find('.ui-dialog-titlebar').height());
						} else {
							$this.css('height', 'auto');
							$dialog.css('max-height', $dialog.css('height'));
						}
					}
					
					//reposition dialog
					$dialog.dialog('option', 'position', $dialog.dialog('option', 'position'));
				});
				$(window).trigger('resize.responsive');
			}

		});
	};

	$('.lightbox-trigger').click(function() {
		if($(this).attr('data-element') !== undefined){
			$('#' + $(this).attr('data-element')).each(function(i, el) {
				var _this = $(el);
				
				var dialogWidth;
				if (_this.attr('data-width') !== undefined) {
					dialogWidth = parseInt(_this.attr('data-width'));
				} else {
					dialogWidth = 600;
				} // End else
				
				var dialogHeight;
				if (_this.attr('data-height') !== undefined) {
					dialogHeight = parseInt(_this.attr('data-height'));
				} 

				_this.dialog({
					dialogClass : 'bbva-lightbox',
					title: _this.attr('data-title'),
					autoOpen : true,
					resizable : false,
					draggable: false,
					modal : true,
					fluid : true, //new option
					width : 'auto',
					maxWidth : dialogWidth,
					height: dialogHeight,
					open : function(event, ui) {
						$(document.body).css('overflow', 'hidden');
						fluidDialog(); // needed when autoOpen is set to true in this codepen
					},
					close : function() {
						$(document.body).css('overflow', 'auto');
					}
				}); // End dialog
			}); // End each
		} else if($(this).attr('data-href') !== undefined || $(this).attr('href') !== undefined){
			var url = $(this).attr('data-href');
			if(url === undefined){
				url = $(this).attr('href');
			}
			
			$.ajax({
				url: url,
				success: function(html) {
					var _this = $(html);
					var dialogWidth;

					if (_this.attr('data-width') !== undefined) {
						dialogWidth = parseInt(_this.attr('data-width'));
					} else {
						dialogWidth = 600;
					}
					
					var dialogHeight;
					if (_this.attr('data-height') !== undefined) {
						dialogHeight = parseInt(_this.attr('data-height'));
					} 

					_this.dialog({
						dialogClass : 'bbva-lightbox',
						title: _this.attr('data-title'),
						autoOpen : true,
						resizable : false,
						draggable: false,
						modal : true,
						fluid : true, //new option
						width : 'auto',
						maxWidth : dialogWidth,
						height: dialogHeight,
						open : function(event, ui) {
							$(document.body).css('overflow', 'hidden');
							fluidDialog(); // needed when autoOpen is set to true in this codepen
							
							$(document).trigger('capgemini.ajax.load', _this);
						},
						close : function() {
							$(document.body).css('overflow', 'auto');
							_this.remove();
						}
					}); // End dialog
				}
			});
		}
		return false;
	}); // End click
	
}); // End ready