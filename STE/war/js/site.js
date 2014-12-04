$(function(){
	
	
	
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

