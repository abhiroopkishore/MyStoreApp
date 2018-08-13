
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta charset="ISO-8859-1">
<script src="../js/jquery-3.3.1.min.js" type="text/javascript" ></script> 
<script type="text/javascript" src="../js/bootstrap.min.js"></script>

<script src="//ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
<script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/additional-methods.min.js"></script>


<link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css">




<script type="text/javascript">
//function to make ajax call, fetch product list and display in grid
function display_grid(){
jQuery(function($) {
		$.ajax({			
			dataType: "json",
			enctype: 'multipart/form-data',
			type: 'GET',
			url:'<s:url namespace="/store" action="fetchGridDataAction"/>',
            contentType: false,
            processData: false,
			success: function(res){
				var tblData="";
				var recCount=0;
				
				$.each(res.productBeanList, function() {	
					tblData += "<tr><td>" + this.product_id + "</td>" + 
					"<td>" + this.product_name + "</td>" + 
					"<td>" + this.product_description + "</td>" + 
					"<td>" + this.related_products_name + "</td>" +
					"<td> <img border=0 width=\"100px\" height=\"100px\" alt=\"Image Not found\"  src =\" <s:url action='streamImageAction?imageId=" + this.product_image_name + "' />\" /> </td>" +
					"<td>"+
					"<button onclick='fetchOldRecord(this);' class='btn btn-sm btn-info' data-toggle='modal' data-target='#updateModal'>Update</button>"+
					"<button onclick='deleteProduct(this);' class='btn btn-sm btn-danger'>Delete</button>"+
					 "</td></tr>" ;
				});
				$("#tbody").html(tblData);
			},
			error: function(res){
				var tblData="";
				tblData += "<tr><td colspan=5> No records available. </td></tr>";
				$("#tbody").html(tblData);
				 console.log("No records returned for display"); 
			}
		});
}); }
		
		
// function to make ajax call and update exisitng record in product list
		function updateNewRecord() {
			var fd = new FormData();
			jQuery(function($) {
			var file = $('#productImage_update')[0].files[0];
			if(file!==undefined){
				 console.log('image has value');
		      fd.append('productImage', file);
			} else{
				console.log('image is null');
				fd.append('productImage', '');
			}
		      fd.append('product_id', $('#product_id').val());
		      fd.append('product_name', $('#product_name').val());
		      fd.append('product_description', $('#product_description').val()); 
		      fd.append('related_products_name',$('#related_products_name').val());
		      
			$.ajax({
				type:"POST",
				enctype: 'multipart/form-data',
				url:'<s:url namespace="/store" action="updateProductAction"/>',
	           	data:fd,
		       	contentType: false,
		      	processData: false,
				
		       success:function(result){
	            	var ht= result.msg;
	    			$("#resp_update").html(ht);
	    			display_grid();
	            },
	            error: function(result){
	            	alert("Some error occured while updating records.");
	            	console.log("Some error occured while updating records.");
	            }
			});	
		
		});
		}
		
// function to make ajax call and add record to product list
		function addNewRecord(){
			var fd = new FormData();
			jQuery(function($) {
  			      var file = $('#productImage')[0].files[0];
			      fd.append('productImage', file);
			    //  fd.append('product_id', $('#product_id_add').val());
			    fd.append('product_id','0');
			      fd.append('product_name', $('#product_name_add').val());
			      fd.append('product_description', $('#product_description_add').val()); 
			      fd.append('related_products_name',$('#related_products_name_add').val());
			      
				$.ajax({
					type:"POST",
					enctype: 'multipart/form-data',
					url:'<s:url namespace="/store" action="addProductAction"/>',
		            data:fd,
		            contentType: false,
		            processData: false,
		             
		            success:function(result){
		            	var ht= result.msg;
		    			$("#resp_add").html(ht);
		    			display_grid();
		            },
		            error: function(result){
		            	alert("Some error occured while adding records.");
		            }
				});	
			
			});
		}
		
		
		// function to make ajax call and delete record from product list
		function deleteProduct(that) {	
			jQuery(function($) {
			$.ajax({
				type:"POST",
				url:'<s:url namespace="/store" action="deleteProductAction"/>',
				data:"product_id="+$(that).parent().prev().prev().prev().prev().prev().text(),
				success: function(data){
					if(data.msg==="Your record is deleted"){
						display_grid();
						alert(data.msg)
						$(that).closest('tr').remove();
						
					} else{
						display_grid();
						alert(data.msg)
					}
				},
				error:function(data){
					alert("Some error occured.");
				}
			});
			
		});
		}
 
		//function for fecthing old information into the form
		function fetchOldRecord(that){		
			   $("#product_id").val($(that).parent().prev().prev().prev().prev().prev().text());
			   $("#product_name").val($(that).parent().prev().prev().prev().prev().text());
			   $("#product_description").val($(that).parent().prev().prev().prev().text());
			   $("#related_products_name").val($(that).parent().prev().prev().text());
		   	}
		//JQuery validations on forms below:
 function validateAddForm(){
		 console.log('validateAddForm() called.');
		 jQuery.validator.addMethod("alphanumeric", function(value, element) {
			    return this.optional(element) || /^[\w.]+$/i.test(value);
			}, "Only Letters, numbers, and underscores allowed");
		 
		    $('#addProdForm').validate({
		        rules: {
		       /*  	product_id_add: {  required: true }, */
		            product_name_add: { required: true, minlength:5, alphanumeric: true },
		            product_description_add:{ required:true, minlength:5 },
		            productImage:{ required:true, extension: "png|gif|jpeg|pjpeg|jpg" },
		            related_products_name_add:{ }
		        },
		        messages: {
		            product_name_add: { required: "Please provide a Product name",  minlength: "Product name must be at least 5 characters long" },
		            product_description_add: { required: "Please provide a Product description", minlength: "Product name must be at least 5 characters long" },
			        productImage: { required:"Product image is required", extension:"Only following file types are allowed: *.png,*.gif,*.jpeg,*.pjpeg,*.jpg" }
		          },
		        submitHandler: function (form) { addNewRecord(); }
		    });
	
 }
 
 function validateUpdateForm(){
	 jQuery.validator.addMethod("alphanumeric", function(value, element) {
		    return this.optional(element) || /^[\w.]+$/i.test(value);
		}, "Only Letters, numbers, and underscores allowed");
	 
	    $('#updateProdForm').validate({
	        rules: {
	       /*  	product_id_add: {  required: true }, */
	            product_name: { required: true, minlength:5, alphanumeric: true },
	            product_description:{ required:true, minlength:5 },
	            productImage:{extension: "png|gif|jpeg|pjpeg|jpg" },
	            related_products_name:{ }
	        },
	        messages: {
	            product_name_add: { required: "Please provide a Product name",  minlength: "Product name must be at least 5 characters long" },
	            product_description_add: { required: "Please provide a Product description", minlength: "Product name must be at least 5 characters long" },
		        productImage: { required:"Product image is required", extension:"Only following file types are allowed: *.png,*.gif,*.jpeg,*.pjpeg,*.jpg" }
	          },
	        submitHandler: function (form) { updateNewRecord(); }
	    });
 }
</script>


<title>MyStoreApp-Homepage</title>
</head>
<body onload="display_grid();">
Hello <b><%=session.getAttribute("loggedInUser")%></b>, you have been successfully logged in!


	<nav class="navbar navbar-default">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="/">My Store App</a>
			</div>
			<ul class="nav navbar-nav">
				<li>
					<button class='btn btn-sm btn-info' data-toggle='modal' data-target='#addProductModal'>Add new product</button>
				</li>
				<li>
					 <a href="logoutAction">Logout</a> 
				</li>
			</ul>
		</div>
	</nav>
	<div class="container">
		<table class="table table-bordered">
			<thead>
				<tr class="bg-info">
					<th>ID</th>
					<th>Name</th>
					<th>Description</th>
					<th>Related Products</th>
					<th>Image</th>
				</tr>
			</thead>
			
			<!-- The product lists are displayed here  -->
			<tbody id="tbody">
			</tbody>
		</table>
	</div>
	

	
 <div class="container" id="updateBlock">
		<div class="modal fade" id="updateModal" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						
						 <h4 class="modal-title">Update New Information</h4> 
						<button type="button" class="close" data-dismiss="modal">×</button>
					</div>
					<form id="updateProdForm">
					<div class="modal-body">
						<div class="row">
							 <div class="col-xs-6 col-sm-6 col-md-6">
								<div class="form-group">
									<input type="text" name="product_id" id="product_id" class="form-control input-sm" placeholder="Product ID" disabled>
								</div>
							</div> 
							<div class="col-xs-6 col-sm-6 col-md-6">
								<div class="form-group">
									<input type="text" name="product_name" id="product_name" class="form-control input-sm" placeholder="Product Name">
								</div>
							</div>
						</div>
						<div class="form-group">
							<input type="text" name="product_description" id="product_description" class="form-control input-sm" placeholder="Product Description">
						</div>
						
						<div class="form-group">
							<input type="text" name="related_products_name" id="related_products_name" class="form-control input-sm" placeholder="Update related products here ">
						</div>
						
						
						<div class="form-group">
							<input type="file" size="50" name="productImage" id="productImage_update" placeholder="Product Image" />
						</div>
 
						<button onclick="validateUpdateForm();" class="btn btn-info btn-block">Update</button>
						<div id="resp_update" class="text-center" style="margin-top: 13px;"></div>
					</div></form>
				</div>
			</div>
		</div>
	</div> 


	
 <div class="container" id="addProductBlock">
		<div class="modal fade" id="addProductModal" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						
						 <h4 class="modal-title">Add a new Product</h4> 
						<button type="button" class="close" data-dismiss="modal">×</button>
					</div>
					<form id="addProdForm">
					<div class="modal-body">
						<div class="row">
						<!-- 	<div class="col-xs-6 col-sm-6 col-md-6">
								<div class="form-group">
									<input type="text" name="product_id_add" id="product_id_add" class="form-control input-sm" placeholder="product_id">
								</div>
							</div> -->
							<div class="col-xs-6 col-sm-6 col-md-6">
								<div class="form-group">
									<input type="text" name="product_name" id="product_name_add"  maxlength=30 class="form-control input-sm" placeholder="Product Name" >
								</div>
							</div>
						</div>
						<div class="form-group">
							<input type="text" name="product_description" id="product_description_add" maxlength=30  class="form-control input-sm" placeholder="Product Description" >
						</div>
						
						<div class="form-group">
							<input type="text" name="related_products_name" id="related_products_name_add" maxlength=60 class="form-control input-sm" placeholder="Add related products here" >
						</div>
						
						<div class="form-group">
							<input type="file" size="50" class="form-control input-sm" name="productImage" id="productImage" placeholder="Product Image"/>
						</div>

						<button onclick="validateAddForm();" class="btn btn-info btn-block">Add Product</button>
						<div id="resp_add" class="text-center" style="margin-top: 13px;"></div>
					</div>
					</form>
				</div>
			</div>
		</div>
	</div> 
	
</body>
</html>