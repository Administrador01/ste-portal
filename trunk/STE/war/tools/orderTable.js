var normalize = (function() {
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
		var $this = $(this);
		var text = $this.val().toLowerCase();
		text = normalize(text);
		var columna = $this.attr("class").split("col")[1];		
		var $table_results = $('#myTable').children();		
		for (var e=0; e<$table_results.length; e++){
			var $linea = $($table_results[e]);			
			var cont = $($($linea.children()[columna]).children()).html().toLowerCase();			
			if (cont.indexOf(text)==-1){
				$linea.hide();
				$linea.removeClass('valid-result');
				
			}else{
				$linea.show();
				$linea.addClass('valid-result');
			}			
		}
		
	});
});

