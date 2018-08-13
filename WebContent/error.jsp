<%@ taglib prefix="s" uri="/struts-tags" %>
<html>

<head>
<meta charset="ISO-8859-1">
<!-- // First try loading jQuery from Google's CDN -->
<script src="//ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<%-- <script src="js/jquery-3.1.1.min.js" type="text/javascript" ></script> --%>
<script src="js/bootstrap.min.js" type="text/javascript" ></script>
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">


<title>MyStoreApp-Error</title>
</head>


<body>



	<nav class="navbar navbar-default">
	
			<div class="navbar-header">
				<a class="navbar-brand" href="/"  >My Store App</a>
			</div>
	</nav>
	
	
	<div class="container">
 		<div class="col-lg-5">
			<div class="text-center" style="margin-top: 14px;">Some Error Occurred :(. <a href="/MyStoreApp/index.jsp">Go Back to Login page</a> </div>
			
Error Name: <s:property value="exception"/><br><br>

Error Stack Trace: <br>
<s:property value="exceptionStack"/>
		</div>
	</div>
	

</body>
</html>