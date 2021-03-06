function sendEditPrueba(){

	var $form = $("#edit-prueba-form");
	
	
	if($form.valid()){			
		$("#submit_edit_prueba_form").addClass("hidden");
		$('#new_prueba_form_modal').addClass("hidden");
		$('#modal-footer_submit').css('display','none');
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
					$form.find('#message_div_modal').removeClass("hidden");
					$form.find('.form-container').find('div:not(#message_div_modal)').hide(0);
					$form.find('#span_message_modal').html('La prueba ha sido modificado de forma correcta.<br/>En breve volvemos a la p&aacute;gina.');
					
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

function sendCloneTest(){

	var $form = $("#edit-prueba-form");
	
	
	if($form.valid()){			
		var postData = $form.serialize() + "&accion=clone";
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
					
					$('#message_div_modal').css('display','block').removeClass("error").addClass("success");
					$form.find('#span_message_modal').html('La prueba ha sido duplicada de forma correcta.');
					

					setTimeout(function() { 
						$('#message_div_modal').css('display','none');
						
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

function filtering(){
	var $form = $("#test-header-filter");
	
	var postData =  $form.serialize();
	postData = postData + "&desde-filter="+$('#fecha-desde-filtro').val()+ "&hasta-filter="+$('#fecha-hasta-filtro').val()+ "&premium="+$('#tipo_cliente').val();
	window.location = "/pruebas.do?"+postData;
}
function ajaxImplementaciones(cliente,target){
	target.empty();
	target.selectpicker("render");
	if (cliente!="default"){
		 var formURL = "/pruebaServlet?";
		 var postData="accion=getImpByClient&client="+cliente;
		 $.ajax(			
			{
				url : formURL,
				type: "POST",
				data : postData,
				success:function(data, textStatus, jqXHR) 
				{
					if (data.success=="true"){
						
						var implementations = data.implementaciones[0];
						
						var tamano = implementations.length;
						if(tamano == 0){
							target.append($("<option></option>").attr("value","default").text("No hay implementaciones"));
						}
						for (var i = 0 ; i < tamano; i=i+2){
							var id = implementations[i];
							var name = implementations[i+1];
							target.append($("<option></option>").attr("value",id).text(name));
						}

						
					}
					target.selectpicker("refresh");
				}					
				
			});
	}else{target.append($("<option></option>").attr("value","default").text("-"));}
	target.selectpicker("refresh");
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
	
	
	

	$('#pruebas').on('change','#cliente-pruebas', function (){
		$('#input-premium-prueba').val($("#cliente-pruebas").find(":selected").data('premium'));
		var cliente = $('#cliente-pruebas').val();
		var target = $('#imp-pruebas');
		ajaxImplementaciones(cliente,target);
	});
	$('#pruebas').on('change','#cliente_pru_modal', function (){
		$('#input-premium-prueba-modal').val($("#cliente_pru_modal").find(":selected").data('premium'));
		var cliente = $('#cliente_pru_modal').val();
		var target = $('#imp-pruebas-modal');
		ajaxImplementaciones(cliente,target);
	});
	
	/*
	$('#pruebas').on('click','#test_filter_button', function (){

		var val_client_form = $('#cliente-filtro').val();
		var val_entorno_form = $('#entorno-filtro').val();
		var val_estado_form = $('#estado-filtro').val();
		var val_fecha_desde_form = $('#fecha-desde-filtro').val();
		var val_fecha_hasta_form = $('#fecha-hasta-filtro').val();
		
 
		//console.log(client_form);
		//console.log(entorno_form);
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
				
				if (compare_dates(val_fecha_desde_form,entradas[a].data('strfechaestado'))){
					//el dato de la tabla es mayor que el del filtro
					$(trs[a]).addClass('hidden');
				}else{
					//el dato del filtro es mayor que el de la tabla
					
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
		
		$table.paginateMe({
			pagerSelector : '#myPager',
			showPrevNext : true,
			hidePageNumbers : false,
			perPage : 10
		});

	})
	*/
	
});


/*
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
          if (xDay> yDay)  
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
};*/