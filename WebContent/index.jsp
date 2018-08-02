<%@ taglib prefix="s" uri="/struts-tags" %>
<html>

<head>
<meta charset="ISO-8859-1">
<!-- // First try loading jQuery from Google's CDN -->
<script src="//ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<%-- <script src="js/jquery-3.1.1.min.js" type="text/javascript" ></script> --%>
<script src="js/bootstrap.min.js" type="text/javascript" ></script>
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">


<title>MyStoreApp-Homepage</title>
</head>


<body>
<s:form action="loginAction">

	<nav class="navbar navbar-default">
	
			<div class="navbar-header">
				<a class="navbar-brand" href="/"  >My Store App</a>
			</div>
	</nav>
	
	
	<div class="container">
 		<div class="col-lg-5">
			<div class="form-group">
				<input type="text" name="user.uname" class="form-control input-sm" placeholder="Username" required/>
			</div>
			<div class="form-group">
				<input type="password" name="user.password"  class="form-control input-sm" placeholder="Password" required/>
			</div>
				<input type="submit" class="btn btn-success btn-block" value="Login" />
			<div class="text-center" id="resp" style="margin-top: 14px;"></div>
		</div>
	</div>
	


</s:form>
</body>
</html>