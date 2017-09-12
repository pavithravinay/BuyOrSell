$(document).ready(function(){
	var ADD_NEW_ADDRESS = "addAddress";
	var REMOVE_ADDRESS = "removeAddress";
	var ADD_NEW_AD = "addAd";
	var FETCH_SUBCATEGORY = "fetchSubCategory";
	var REMOVE_AD = "removeAd";
	var UPDATE_AD = "updateAd";
	var adId =0;
		
	$("#newAddButton").click(function(){
		 $('#addAddressDiv').show();
	});
	
	$('#addAddressDiv').hide();
	$('#subCategory').hide();
	$('#uploadImageBtn').hide();
	
	$("#postAddBtn").click(function(){
		
    	$.post('MyAdServlet', {
    		
    		title: $("#title").val(),
    		description: $("#description").val(),
    		itemCondition:$("#condition").val(),
    		category: $("#category").val(),
    		subCategory: $("#subCategory").val(),
    		addressId: $(".checkboxAdd").attr('checked', true).val(),
    		price:$("#price").val(),
    		contactNumber: $("#contactNumber").val(),
    		isNegotiable:  $("input[name=isNegotiable]:checked").val(),
    		displayContactNumber: $("input[name=displayContactNumber]:checked").val(),
    		
    		action: ADD_NEW_AD,
            dataType : 'json' // data type
	    }, function(jsonResponse) {
	                 //  
	    	$("#errorDiv0").empty();
	          if(jsonResponse.success == true) {  
	        	  adId=jsonResponse.obj ;
	        	 //alert(adId);
	        	 $("#uploadForm").append("<input name=\"adId\" value=\""+adId+"\"/>");
	        	
	            $( "#uploadImageBtn" ).trigger( "click" );
	          } else {
	        	  $("#errorDiv0").append("<h4 class=\"heading\" style = \"color: #ee4949;\">"+jsonResponse.msg+"</h4>");
	          }
	                                       
	    });
	 });
	
	function callSubCat() {
		$.get('MyAdServlet', {
			
			categoryPID: $("#category").val(),
			    		
			action: FETCH_SUBCATEGORY,
	        dataType : 'json' // data typ
	    }, function(jsonResponse) {
	    	$('#subCategory').show();
	    	$( "#subCategory" ).empty();
	          $.each(jsonResponse.obj, function(index, element) {
	             $('#subCategory').append("<option value="+element.id+">"+element.name+"</option>");
	                                   
	          });
	    });
	}
	
	callSubCat();
	$(document.body).on('change','#category',function(){
		callSubCat();
	});
	
	$.fn.removeAddress = function(addId) {
		var element = $(this).parent().closest('tr');   
		$.post('MyAdServlet', {
    		
			addressId: addId,
    		action: REMOVE_ADDRESS,
            dataType : 'json' // data type
	    }, function(jsonResponse) {
	          if(jsonResponse.success == true)  {      
	        	  //$(this).parent().closest('tr').remove(); 
	        	  element.remove();
	          } else {
	        		$("#errorDiv3").empty();
	        		$("#errorDiv3").append("<h4 class=\"heading\" style = \"color: #ee4949;\">"+jsonResponse.msg+"</h4>");
	  	          
	          }
	    });
    };

	
	$("#saveButton").click(function(){
		
		
    	$.post('MyAdServlet', {
    		
    		address: $("#address").val(),
    		state: $("#state").val(),
    		country:$("#country").val(),
    		city: $("#city").val(),
    		zipcode:$("#zipcode").val(),
    		action: ADD_NEW_ADDRESS,
            dataType : 'json' // data typ
	    }, function(jsonResponse) {
	    	$("#errorDiv").empty();
	    	if(jsonResponse.success == false) {
	    		$("#errorDiv").append("<h5 class=\"heading\" style = \"color: #ee4949;\">"+jsonResponse.msg+"</h5>");
	    	} else {
	                    var myvar = '<tr>'+
	                	'							<td class="cart_product">'+
	                	'								<input type="radio" class="checkboxAdd" name="add1" value="'+jsonResponse.obj.id+'"  checked> <br>'+
	                	'							</td>'+
	                	'							<td class="cart_description">'+
	                	'								<h4><a href="">'+jsonResponse.obj.address+'</a></h4>'+
	                	'								<p>'+jsonResponse.obj.state+' & '+jsonResponse.obj.country+'</p>'+
	                	'							</td>'+
	                	'							<td class="cart_price">'+
	                	'								<p>'+jsonResponse.obj.city+'</p>'+
	                	'							</td>'+
	                	'							<td class="cart_price">'+
	                	'								<p>'+jsonResponse.obj.zipcode+'</p>'+
	                	'							</td>'+
	                	'							'+
	                	'							<td class="cart_delete">'+
	                	'								<a class="cart_quantity_delete" href=""><i class="fa fa-times"></i></a>'+
	                	'							</td>'+
	                	'						</tr>';
	                    $( "#table_body" ).append( myvar );
	    	}
	                                       
	    });
	 });
	
	$("#editAdBtn").click(function(){

		var adsid = $("#adid").val();
		//alert(adsid);
		$.post('EditAdsServlet', {
			id: adsid,
			action: UPDATE_AD,
			title: $("#title").val(),
    		description: $("#description").val(),
    		itemCondition:$("#condition").val(),
    		category: $("#category").val(),
    		subCategory: $("#subCategory").val(),
    		addressId: $(".checkboxAdd").attr('checked', true).val(),
    		isNegotiable:  $("input[name=isNegotiable]:checked").val(),
    		isActive: $("input[name=isActive]:checked").val(),
    		isSoldOut: $("input[name=isSoldOut]:checked").val(),
    		displayContactNumber: $("input[name=displayContactNumber]:checked").val(),
    		price:$("#price").val(),
    		contactNumber: $("#contactNumber").val(),
    		
            dataType : 'json' // data type
	    }, function(jsonResponse) {
	              
	    	$("#errorDiv0").empty();
	          if(jsonResponse.success == true) {  
	        	  adId=jsonResponse.obj ;
	        	 
	        	 $("#uploadForm").append("<input name=\"adId\" value=\""+adId+"\"/>");
	        	
	        	 $(location).attr('href', '/myaccount');
	            
	          } else {
	        	  $("#errorDiv0").append("<h4 class=\"heading\" style = \"color: #ee4949;\">"+jsonResponse.msg+"</h4>");
	          }
	                                       
	    });
		window.location = "my-advertisements?action=updatesuccess";
	});
	
	$("#deleteAdBtn").click(function(){
		var adsid = $("#adid").val();
		//alert(adsid);
		$.post('EditAdsServlet', {
			id: adsid,
			action: REMOVE_AD,
	        dataType : 'json' // data type
	    }, function(jsonResponse) {
	          if(jsonResponse.success == true)  {      
	        	  $(location).attr('href', '/myaccount');
	          }
	    });
		window.location = "my-advertisements?action=deletesuccess";
	 });
	
});