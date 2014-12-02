function sendEditPrueba(){

	var $form = $("#edit-prueba-form");
	
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
					
					$form.find('#span_message_modal').html('La prueba ha sido modificado de forma correcta.<br/>En breve volvemos a la p&aacute;gina.');
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


$(function(){
	$('#pruebas').on('click','#deletePrueba', function (e){
		
		 var formURL = "/pruebaServlet?";
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
	});
	
	$('#cliente-pruebas').on('change', function() {
		//console.log($(this).find(":selected"));
		var option = $(this).find(":selected");
		//console.log($(option));
		//console.log(option.data('premium'));
		//console.log(option.data('clientid'));
		
		$('#input-premium-soporte').val(option.data('premium'));
		$('#input-segmento-soporte').val(option.data('segmento'));
		$('#client-id-input').val(option.data('clientid'));
	})
	
	$('#pruebas').on('click','#test_filter_button', function (){

		var val_client_form = $('#cliente-filtro').val();
		var val_entorno_form = $('#entorno-filtro').val();
		var val_estado_form = $('#estado-filtro').val();
		var val_fecha_desde_form = $('#fecha-desde-filtro').val();
		var val_fecha_hasta_form = $('#fecha-hasta-filtro').val();
		
 
		//console.log(client_form);
	//	console.log(entorno_form);
		//console.log(estado_form);

		var trs = $('#myTable').find('tr');

		var entradas = [];
		for (a=0; a<=trs.length;a++){
			entradas[a]= $(trs[a]);
		}
		
		for (a=0; a<trs.length;a++){
			$(trs[a]).removeClass('hidden');
		}

	

		if (val_client_form!="" && val_client_form!=null){
			for (a=0; a<trs.length;a++){
				if (entradas[a].data('nombrecliente')!=val_client_form)
					$(trs[a]).addClass('hidden');
			}
		}

		if (val_entorno_form!="" && val_entorno_form!=null){
			for (a=0; a<trs.length;a++){
				if (entradas[a].data('entorno')!=val_entorno_form)
					$(trs[a]).addClass('hidden');
			}
		}
		
		if (val_estado_form!="" && val_estado_form!=null){
			for (a=0; a<trs.length;a++){
				if (entradas[a].data('estado')!=val_estado_form)
					$(trs[a]).addClass('hidden');
			}
		}
		
		if (val_fecha_desde_form!="" && val_fecha_desde_form!=null){
			for (a=0; a<trs.length;a++){
				if (compare_dates(entradas[a].data('strfechaestado'),val_fecha_desde_form)){
					//el dato de la tabla es mayor que el del filtro
				}else{
					//el dato del filtro es mayor que el de la tabla
					$(trs[a]).addClass('hidden');
				}
			}
		}
		

		
		if (val_fecha_hasta_form!="" && val_fecha_hasta_form!=null){
			for (a=0; a<=trs.length;a++){
				if (compare_dates(entradas[a].data('strfechaestado'),val_fecha_hasta_form)){
					//el dato de la tabla es mayor que el del filtro
					
					$(trs[a]).addClass('hidden');
				}else{
					//el dato del filtro es mayor que el de la tabla
					
				}
			}
		}

	})
	
	
});

function compare_dates(fecha, fecha2)  
{  
  var xMonth=fecha.substring(3, 5);  
  var xDay=fecha.substring(0, 2);  
  var xYear=fecha.substring(6,10);  
  var yMonth=fecha2.substring(3, 5);  
  var yDay=fecha2.substring(0, 2);  
  var yYear=fecha2.substring(6,10);  
  
  if (xYear> yYear)  
  {  
      return(true)  
  }  
  else  
  {  
    if (xYear == yYear)  
    {   
      if (xMonth> yMonth)  
      {  
          return(true)  
      }  
      else  
      {   
        if (xMonth == yMonth)  
        {  
          if (xDay>= yDay)  
            return(true);  
          else  
            return(false);  
        }  
        else  
          return(false);  
      }  
    }  
    else  
      return(false);  
  }  
};