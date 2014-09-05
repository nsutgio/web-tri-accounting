<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<c:url value="/resources/css/bootstrap/bootstrap.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/bootstrap/bootstrap-theme.css" />" rel="stylesheet"> 
<link href="<c:url value="/resources/css/custom/app.css" />" rel="stylesheet">
<title>Login</title>
</head>
<body>
<div class="container"> 
	<div class="row-top-buffer"></div>
	<form name="f" method="post" action="${pageContext.request.contextPath}/login"> 
		<div class="row">
			<div class="col-md-4 col-md-offset-4" style="border: solid 1px gray;"> 
		            <fieldset>
		                <legend>Please Login:</legend>
		                <c:if test="${param.error != null}">
			                <div class="alert alert-danger">    
			                    Invalid username and password.
			                </div>
		                </c:if>
		                 <c:if test="${param.logout != null}">
			                <div class="alert alert-info">    
			                    You have been logged out.
			                </div>
		                </c:if>   
		                
		                <div class="row">
					      <div class="col-md-4">
				      	    <label for="username">Username</label>
				      	  </div>
					      <div class="col-md-8">
					      	<div class="input-group">
								  <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
								  <input class="form-control" type="text" id="username" name="username" placeholder="Username" />    
							</div>     
					      </div>
					    </div>  
					    
					    <div class="row row-top-buffer">
					      <div class="col-md-4">
					         <label for="password">Password</label>
					      </div>
					      <div class="col-md-8"> 
						      <div class="input-group">
								  <span class="input-group-addon">@</span>
								  <input class="form-control" type="password" id="password" name="password" placeholder="Password" />    
							  </div>
						  </div>
					    </div>  
					    
					    <div class="row row-top-buffer">
					      <div class="col-md-4">
					         <label for="password">&nbsp;</label>
					      </div>
					      <div class="col-md-8 form-actions">
		                    <button type="submit" class="btn btn-primary">Log in</button> 
					      </div>
					    </div>   
					    
					    <div class="row row-top-buffer"> 
					    </div> 
		            </fieldset>
		        	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
	 		</div>
	 	</div>
 	</form>
</div>  
    <script src="<c:url value="/resources/js/jquery/jquery-1.11.1.min.js" />"></script>
    <script src="<c:url value="/resources/js/bootstrap/bootstrap.min.js" />"></script> 
</body>
</html>