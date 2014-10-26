$.fn.paginateMe = function(opts) {
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
};

