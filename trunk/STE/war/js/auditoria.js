$(function() {
	
	$('#historico').on('change', function(e) {
		var accion = $(this).val();
		window.location.replace("./auditoria.do?p="+accion);
	});
})