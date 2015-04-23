$.fn.paginateMe = function(opts) {
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
	
		//pager.data("curr", 0);
		
		var page = listElement.data('page');
		var lastpage = listElement.data('lastpage');
		var numpages = listElement.data('numpages');
		var oldparameters = getParameters();
		var sPath=window.location.pathname;
		var sPage = sPath.substring(sPath.lastIndexOf('/') + 1);
		
		if(numpages > 0) {
			if(page > 0) {
				$('<li><a href="#" class="prev_link"><</a></li>').appendTo(pager);
				$('<li><a href="'+ sPage+'?page=0'+"&"+oldparameters+ '" class="page_link">1</a></li>').appendTo(pager);
			}	
			$('<li><a href="'+ sPage+'?page='+page+'" class="page_link active">'+ (page+1) + '</a></li>').appendTo(pager);
			if(page < (numpages-1)) {
				$('<li><a href="'+ sPage+'?page='+(numpages-1)+"&"+oldparameters+'" class="page_link">'+ numpages+ '</a></li>').appendTo(pager);
			}
			if(lastpage == false) {
				$('<li><a href="#" class="next_link">></a></li>').appendTo(pager);
			}
			
			$('<span>Ir a p&aacutegina:</span>').appendTo(pagerGoto);
			var opcionespage = "<select>";
			
			for(var i=1;i<=numpages;i++){
				opcionespage= opcionespage+"<option class=\"pagerselect\" val=\'"+i+"\'>"+i+"</option>";
			
			}
			
			opcionespage = opcionespage+"<select>";
			$(opcionespage).appendTo(pagerGoto);
			
			/*
			$('<li>').appendTo(pager);
			$('<span>Ir a p&aacutegina:</span>').appendTo(pager);
			$('<select>').appendTo(pager);
			for(var i= 1;i<numpages ;i++){
				var auxili = '<option class="irapag" value=\''+i+'\'>'+i;
				$('<option class="irapag" value=\''+i+'\'>'+i).appendTo(pager);
				
				$('</option>').appendTo(pager);
			}
			
			$('</select>').appendTo(pager);
			$('</li>').appendTo(pager);
			*/
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
				if(oldparams != "") {
					location = location + "&" + oldparams;
				}
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
			if(oldparams != "") {
				location = location + "&" + oldparams;
			}
			window.location = location;		
		}
		
		function getParameters(){
			var sPath=window.location.search;
			var queryString = sPath.substring(sPath.lastIndexOf("?") + 1);
			var newQueryString = $.map(queryString.split("&"), function(pair) { 
				  var p = pair.split("="); 
				  if(p[0] != "page") {
					  return p.join("=");
				  }			  
			}).join("&");
			return newQueryString;
		}
	}else{
		
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



$(function(){
	$('.pagerselect').click(function(e){
		
		var page = $(this).val();
		page--;
		if(page > 0) {
		
			var sPath=window.location.pathname;
			var sPage = sPath.substring(sPath.lastIndexOf('/') + 1);
			var location = './' + sPage + '?page=' + page;
			var oldparams = getParameters();
			if(oldparams != "") {
				location = location + "&" + oldparams;
			}
			window.location = location;		
		}
	});
	
	function getParameters(){
		var sPath=window.location.search;
		var queryString = sPath.substring(sPath.lastIndexOf("?") + 1);
		var newQueryString = $.map(queryString.split("&"), function(pair) { 
			  var p = pair.split("="); 
			  if(p[0] != "page") {
				  return p.join("=");
			  }			  
		}).join("&");
		return newQueryString;
	}
	
});

function getParameters(){
	var sPath=window.location.search;
	var queryString = sPath.substring(sPath.lastIndexOf("?") + 1);
	var newQueryString = $.map(queryString.split("&"), function(pair) { 
		  var p = pair.split("="); 
		  if(p[0] != "page") {
			  return p.join("=");
		  }			  
	}).join("&");
	return newQueryString;
}