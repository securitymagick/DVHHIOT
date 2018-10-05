<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.securitymagick.domain.Permissions" %>
<%@ page import="com.securitymagick.web.cookie.CookieHandler" %>

<!DOCTYPE html>
<html lang="en">
<head>
<title>Habit Helper</title>
 
<spring:url value="/resources/core/css/hello.css" var="coreCss" />
<spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />


</head>
<jsp:include page='navbar.jsp'></jsp:include>
<!--  
<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
	<div class="navbar-header">
		<a class="navbar-brand" href="/">
		<font color="orange">
		<i class='glyphicon glyphicon-header'></i>
		<i class='glyphicon glyphicon-glass'></i>		
		<i class='glyphicon glyphicon-header'></i>
		<i class='glyphicon glyphicon-ice-lolly-tasted'></i>
		<c:if test="${not empty title}">
		${title}
		</c:if>
		
		</font>
		</a>
		<ul class="nav nav-tabs">
			<li><a href="#"> Notifications</a></li>
		<% 
			Boolean showAlreadyLoggedIn = false;
			Boolean showAdmin = false;
			CookieHandler p = new CookieHandler("permissions");
			if (p.checkForCookie(request)) {
				Cookie c = p.getCookie();
				Permissions userPermissions = new Permissions(c.getValue());
				if (userPermissions.getUser()) {
					showAlreadyLoggedIn = true;
				}
				if (userPermissions.getAdmin()) {
					showAdmin = true;
				}
			}
			if (showAlreadyLoggedIn) {
		%>
			<li><a href="myAccount">Your Account</a> </li>		
		<% } 
		   if (showAdmin) {
		%>
			<li><a href="admin">Admin</a></li>
		<% } %>				
		</ul>
		
	</div>
  </div>
</nav> -->

	
<div class="container"> 
  <div class="jumbotron">
	<p id="test"></p>
  <br/>

<c:if test="${not empty about}">
  <div class="well">
	<h2>About Us</h2>

	<p> Habit Helper was founded by Lizzie Hayden in 2014.  Lizzie struggled with addiction to chocolate chip cookies and after 20 years she has now been clean (from chocolate chip cookies) for 90 days.  SHe advises taking things one day at a time, and remembering that no matter how bleak things look there is always a way forward.
    </p>
  </div>
</c:if>
<c:if test="${not empty how}">
   <div class="well">
	<h2>How does habit helper work</h2>

	<p> Habit Helper works in several ways.  First, if you are feeling desperate we offer live chat with a therapist.  The discussion boards are there for less desperate times.  Please click the habit resisted button when you have successfully resisted your habit.  You will get an awesome image to view, and it will up your score on the scoreboard.  To keep things more even the scorebaord will only count the first 23 clicks every day.
    </p>
    </div>
</c:if>

<c:if test="${not empty register}">
	<div class="well">
	</br>
	<h2>Provide the following to create an account</h2>
	<form:form id="fm2" modelAttribute="registerForm" method="post" action="register">
	<p>
		<c:if test="${not empty message}">
			<font color="red">
			${message}
			</font>
		</c:if>
	</p>
	<div class="row">
		<div class="col-md-4">
			<p> Username: </p>
		</div>
		<div class="col-md-6 col-md-offset-1">
			<p> <form:input type="text" path="username" name="username" value="" class="form-control" onkeyup="testUsername(document.forms.fm2.username.value, 'userCheck', document.forms.fm2.submit)"/> </p>
		</div>
	</div>
	<p id="userCheck"></p>
	<div class="row">
		<div class="col-md-4">
			<p> Password: </p>
		</div>
		<div class="col-md-6 col-md-offset-1">
			<p> <form:input type="password" path="password" name="password" value="" class="form-control" onkeyup="testPassword(document.forms.fm2.password.value, 'passwordMeter')" /> </p>
			
		</div>
	</div>
	<p id="passwordMeter"></p>
	<div class="row">
		<div class="col-md-4">
			<p> Confirm Password: </p>
		</div>
		<div class="col-md-6 col-md-offset-1">
			<p> <form:input type="password" path="confirmPassword" name="confirmPassword" value="" class="form-control" onkeyup="testPasswordMatch(document.forms.fm2.password.value, document.forms.fm2.confirmPassword.value, 'passwordMatch')" /> </p>
		</div>
	</div>	
	<p id="passwordMatch"></p>
	<div class="row">
		<div class="col-md-4">
			<p> Habit: </p>
		</div>
		<div class="col-md-6 col-md-offset-1">
			<p> <form:input type="text" path="habit" name="habit" value="" class="form-control" /> </p>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<p> Question: </p>
		</div>
		<div class="col-md-6 col-md-offset-1">
			<p> <form:input type="text" path="question" name="question" value="Favorite David Bowie Song" class="form-control" /> </p>
		</div>
	</div>	
	<div class="row">
		<div class="col-md-4">
			<p> Answer: </p>
		</div>
		<div class="col-md-6 col-md-offset-1">
			<p> <form:input type="text" path="answer" name="answer" value="Life on Mars" class="form-control" /> </p>
		</div>
	</div>	
	<div class="row">
		<div class="col-md-4">
		</div>
		<div class="col-md-6 col-md-offset-1">
		<p> 
			<button class="btn btn-info btn-lg" class="form-control" type="submit" value="Submit" name="submit" >Register</button> 
		</p>
		</div>
	</div>
   </form:form>
   </div>
</c:if>
<c:if test="${not empty login}">
   <div class="well">
	<h1>Enter Credentials Below</h1>
	<p>
		<c:if test="${not empty message}">
			<font color="red">
			${message}
			</font>
		</c:if>
	</p>
	<form:form id="fm1" modelAttribute="loginForm" method="post" action="login">
	<form:input type="hidden" id="rememberMeToken" name="rememberMeToken" path="rememberMeToken" value="" />
	<div class="row">
		<div class="col-md-4">
			<p> Username: </p>
		</div>
		<div class="col-md-6 col-md-offset-1">
			<p> <form:input type="text" name="username" path="username" value="" class="form-control" /> </p>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<p> Password: </p>
		</div>
		<div class="col-md-6 col-md-offset-1">
			<p> <form:input type="password" name="password" path="password" value="" class="form-control"/> </p>
		</div>
	</div>	
	<div class="row">
		<div class="col-md-4">
			<p> Stay logged in: </p>
		</div>
		<div class="col-md-6 col-md-offset-1">
	 		<p><form:checkbox class="form-control" path="rememberme" id="rememberme" onChange="turnRememberMeOn()"/> </p>
	 	</div>
	 </div>
	<div class="row">
		<div class="col-md-4">
		</div>
		<div class="col-md-6 col-md-offset-1">
		<p> 
			<button class="btn btn-info btn-lg" class="form-control" type="submit" value="Submit" >Login</button> 
			<a class="btn btn-default btn-lg" href="forgotpassword" role="button">Forgot your Password?</a>
		</p>
		</div>
	</div>
    </form:form>
	
	</div>
</c:if>
<c:if test="${not empty forgotpassword}">
	<div class="well">
	<h2>Forgot Your Password?</h2>
	<p>
		<c:if test="${not empty message}">
			<font color="red">
			${message}
			</font>
		</c:if>
	</p>	
	<form:form id="fm3" modelAttribute="usernameForm" method="post" action="forgotpassword">
	<div class="row">
		<div class="col-md-4">
			<p> Username: </p>
		</div>
		<div class="col-md-6 col-md-offset-1">
			<p> <form:input type="text" name="username" path="username" value="" class="form-control"/> </p>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
		</div>
		<div class="col-md-6 col-md-offset-1">
		<p> 
			<button class="btn btn-info btn-lg" class="form-control" type="submit" value="Submit" >Login</button> 
		</p>
		</div>
	</div>
    </form:form>
	</br>
	</div>
</c:if>
<c:if test="${not empty resetpassword}">
	<div class="well">
	<h2>Provide the following to reset your password</h2>
	<p>
		<c:if test="${not empty message}">
			<font color="red">
			${message}
			</font>
		</c:if>
	</p>
	<form:form id="fm1" modelAttribute="resetPasswordForm" method="post" action="resetpassword">
	<div class="row">
		<div class="col-md-4">
		<p> Answer your security question:  
		<c:if test="${not empty question}">	
			${question}
		</c:if>
		</p>
		</div>
		<div class="col-md-6 col-md-offset-1">
			<p> <form:input type="text" path="answer" name="answer" value="" class="form-control"/> </p>
		</div>
	</div>	
	<div class="row">
		<div class="col-md-4">
			<p> New Password: </p>
		</div>
		<div class="col-md-6 col-md-offset-1">
			<p> <form:input type="password" path="password" name="password" value="" class="form-control" onkeyup="testPassword(document.forms.fm1.password.value, 'passwordCheck')"/> </p>
		</div>
		
	</div>
	<p id="passwordCheck"></p>
	<div class="row">
		<div class="col-md-4">
			<p> Confirm Password: </p>
		</div>
		<div class="col-md-6 col-md-offset-1">
			<p> <form:input type="password" path="confirmPassword" name="confirmPassword" value="" class="form-control" onkeyup="testPasswordMatch(document.forms.fm1.password.value, document.forms.fm1.confirmPassword.value, 'passwordMatchCheck')" /> </p>
		</div>
	</div>	
	<p id='passwordMatchCheck'></p>

	<div class="row">
		<div class="col-md-6 col-md-offset-5">
		<p> 
			<button class="btn btn-info btn-lg" class="form-control" type="submit" value="Submit" >Reset Password</button> 
		</p>
		</div>
	</div>
   </form:form>
   </div>
 </c:if>
 		<% 
			Boolean mobile = false;
			CookieHandler n = new CookieHandler("mobile");
			if (n.checkForCookie(request)) {
				Cookie nc = n.getCookie();
				if (nc.getValue().equals("true")) {
					mobile = true;
				}
			}
			if (!mobile) {
		%>
<div class="row text-center">
	<div class="col-md-8 col-md-offset-2 col-xs-10 col-xs-offset-1 well">
		<font color="orange" size="32px">
<i class='glyphicon glyphicon-user'></i>
		</font>
		<h2>Login</h2>
		<p>To get help resisting your bad habits</p>
		<p>
			<a class="btn btn-warning btn-lg" href="?login" role="button">View details</a>
	</div>
  </div>		
  <div class="row text-center">
	<div class="col-md-5 col-md-offset-1 col-xs-6 well">
		<font color="orange" size="32px">
		<i class='glyphicon glyphicon-plus'></i>
		<i class='glyphicon glyphicon-user'></i>
		</font>
		<h2 class='text-center'>Register</h2>
		<p>Create a new account to get help </p>
		<p>
			<a class="btn btn-default btn-lg" href="?register" role="button">View details</a>
		</p>
	</div>
	<div class="col-md-5 col-md-offset-1 col-xs-6 well">
		<font color="orange" size="32px">
		<i class='glyphicon glyphicon-question-sign'></i>
		</font>
		<h2>Forgot</h2>
		<p>Reset your password here</p>
		<p>
			<a class="btn btn-default btn-lg" href="?forgotpassword" role="button">View details</a>
		</p>
	</div>	
  </div>	
  <div class="row text-center">
	<div class="col-md-5 col-md-offset-1 col-xs-6 well">
		<font color="orange" size="32px">
		<i class='glyphicon glyphicon-info-sign'></i>
		</font>
		<h2 class='text-center'>Tell me</h2>
		<p>How does Habit Helper Work</p>
		<p>
			<a class="btn btn-default btn-lg" href="?how" role="button">View details</a>
		</p>
	</div>
	<div class="col-md-5 col-md-offset-1 col-xs-6 well">
		<font color="orange" size="32px">
		<i class='glyphicon glyphicon-sunglasses'></i>
		</font>
		<h2 class='text-center'>About</h2>
		
		<p>Learn more about our company</p>
		<p>
			<a class="btn btn-default btn-lg" href="?about" role="button">View details</a>
		</p>
	</div>	
  </div>
<% } %>
 
 
  <hr>
  <footer>
		<p>&copy; Habit Helper 2017</p>
  </footer>
</div>
 </div>
<jsp:include page='javascript_includes.jsp'></jsp:include> 
 
</body>
</html>