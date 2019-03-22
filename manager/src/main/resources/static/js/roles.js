
function openRoleDialog(){
	
	var form = $("#addrolesform");
	form.find("input[type=text], textarea").val("");
	form.find(".help-block").remove();
	form.find(".has-error").removeClass('.input-icon right has-error');
	
	var w = $("#addRoleModal");
	w.modal("toggle");
}

function submitajax(id){$
	

	
	$form = $("#"+id);
	$groupId = 	 $("#groupId").text();
	$categoryId =  $("#categoryId").val();
$.ajax({
    url: $form.attr('action'),
    type: 'post',
    data: $form.serialize(),
    success: function(response) {
    	var r = $(response);
    	if(r.find("#groupId").length){
    		r.find("#groupId").text($groupId);
    	}
    	if(r.find("#categoryId").length){
    		r.find("#categoryId").val($categoryId);
    	}
    	
    	// if the response contains any errors, replace the form
      if (r.find('.has-error').length) {
    	  $form.replaceWith(r);
      	} else {
      		$form.find("input[type=text], textarea").val("");
      		$form.find(".help-block").remove();
      		$form.find(".has-error").removeClass('.input-icon right has-error');
      		$("#addRoleModal").modal("hide");
      		
      	
      	}
      }
	});
	return false;
}

function oncategoryselect(id){
	$select = $("#"+id);

	 var category = $select.val();
	 var display = $select.find("option:selected").text();
	 if(category!=0){
		 $("#openAddRole").prop( "disabled", false );
		 $("#groupId").text(display);
		 $("#categoryId").val(category);
	 }else if(category == 0){
		 $("#openAddRole").prop( "disabled", true );
	 }
}