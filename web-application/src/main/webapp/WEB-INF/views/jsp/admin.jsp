<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.securitymagick.domain.Permissions" %>
<%@ page import="com.securitymagick.web.cookie.CookieHandler" %>

<!DOCTYPE html>
<html lang="en">
<head>
<title>Habit Helper Admin Panel</title>
 
<spring:url value="/resources/core/css/hello.css" var="coreCss" />
<spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />
</head>
 
<jsp:include page='navbar.jsp'></jsp:include> 
 

 <div class="container">
	<h1>${title}</h1>
	<p>
		<c:if test="${not empty message}">
			<font color="red">
			${message}
			</font>
		</c:if>
	</p>	
		<% 
			Boolean mobile = false;
			CookieHandler n = new CookieHandler("mobile");
			if (n.checkForCookie(request)) {
				Cookie nc = n.getCookie();
				if (nc.getValue().equals("true")) {
					mobile = true;
				}
			}
			
	
		Boolean showAdmin = false;
		CookieHandler p = new CookieHandler("permissions");
		if (p.checkForCookie(request)) {
			Cookie c = p.getCookie();
			Permissions userPermissions = new Permissions(c.getValue());
			if (userPermissions.getAdmin()) {
				showAdmin = true;
			}
		}
		if (showAdmin && !mobile) {
	%>
		
<c:if test="${not empty showLogs}">	
<div class="well">
	<h2>Logs</h2>
	<c:forEach items="${logs}" var="log">
		<br/>
		
		<div class="row">
			<div class="col-md-3">
				${log.username}
			</div>
			<div class="col-md-4">
				${log.useragent}
			</div>
			<div class="col-md-5">
				${log.message}
			</div>
		</div>
    </c:forEach>
</div>
</c:if>
<c:if test="${not empty showPosts}">	
    <div class="well">
    <h2>Posts</h2>
	<c:forEach items="${posts}" var="post">
		<br/>
		<div class="row">
			<div class="col-md-6">
				<a href="public?read=yes&id=${post.id}"> ${post.title} </a> By ${post.author}
			</div>
			<div class="col-md-3">
				<a href="?deletepost=${post.id}"> Delete </a>
			</div>
			<div class="col-md-3">
				<a href="?editpost=${post.id}"> Edit </a>
			</div>
		</div>
    </c:forEach> 
    </div>
</c:if>	
<c:if test="${not empty showDeletePost}">
	<div class="well">
	<h3>${post.title}</h3>

	<br/><img src="/habit-helper-1.0/resources/core/images/${post.imageName}"/>
	<br/><h3> Posted by ${post.author} </h3>
	<br/>${post.thePost}
	<br/>
	<form:form id="fm3" modelAttribute="postToDelete" method="post" action="?deletepost">
	<form:input type="hidden" name="id" path="id" value="${post.id}" />
	<form:input type="hidden" id="csrfToken" name="csrfToken" path="csrfToken" value="" />

	<div class="row">
		
		<div class="col-md-5 col-md-offset-7">
		<p> 
			<button class="btn btn-info btn-lg" type="submit" value="Submit" >Delete</button> 
		</p>
		</div>
	</div>	
	</form:form>
	</div>
</c:if>	
<c:if test="${not empty showEditPost}">
	<div class="well">
	<br/><img src="/habit-helper-1.0/resources/core/images/${post.imageName}"/>
	<br/><h3> Posted by ${post.author} </h3>
	<br/>
	<br/>
	<form:form id="fm4" modelAttribute="postToEdit" method="post" action="?editpost">
	<form:input type="hidden" name="id" path="id" value="${post.id}" />
	<form:input type="hidden" id="csrfToken" name="csrfToken" path="csrfToken" value="" />
	<div class="row">
		<div class="col-md-5 col-md-offset-1">
			<p> Enter Title (Inspiring or Call for Help): </p>
		</div>
		<div class="col-md-5 col-md-offset-1">
			<p> <form:input type="text" name="title" path="title" class="form-control" value="${post.title}" /> </p>
		</div>
	</div>
	<div class="row">
		<div class="col-md-5 col-md-offset-1">
			<p> Post: </p>
		</div>	
		<div class="col-md-5 col-md-offset-1">
			<p> <form:input type="text" name="thePost" path="thePost" class="form-control" value="${post.thePost}" size="60"/></p>
		</div>
	</div>	
	
	<div class="row">
		<div class="col-md-5 col-md-offset-1">
		</div>
		<div class="col-md-5 col-md-offset-1">
		<p> 
			<button class="btn btn-info btn-lg" type="submit" value="Submit" >Update</button> 
		</p>
		</div>
	</div>	
	</form:form>
	</div>
</c:if>		
<c:if test="${not empty showComments}">
	<div class="well">
	<h2>Comments</h2>
	<c:forEach items="${comments}" var="c">
		<br/>
		<div class="row">
			<div class="col-md-5 col-md-offset-1">
				${c.username} said ${c.comment}
			</div>
			<div class="col-md-5 col-md-offset-1">
				<a href="?deleteComment=${c.id}"> Delete </a>
			</div>
		</div>
    </c:forEach>
    </div>
</c:if>
<c:if test="${not empty showAccounts}">
	<div class="well">
	<h2>User Accounts</h2>
	<c:forEach items="${users}" var="user">
		</br>
		<div class="row">
			<div class="col-md-4">
				${user.username}
			</div>
			<div class="col-md-4">
				<a href="?deleteaccount=${user.id}"> Delete Account </a>
			</div>
			<div class="col-md-4">
				<a href="?editaccount=${user.id}"> Edit Account</a>
			</div>
		</div>
    </c:forEach>
    </div>
</c:if>
<c:if test="${not empty showDeleteAccount}">
	<div class="well">
	<br/>User: ${user.username}
	<br/>User Habit: ${user.habit}
	<br/>User status: ${user.isUser}
	<br/>Admin status: ${user.isAdmin}
	<br/>
	<form:form id="fm1" modelAttribute="userToDelete" method="post" action="?deletetheaccount=${user.id}">
	<form:input type="hidden" name="id" path="id" value="${user.id}" />
	<form:input type="hidden" id="csrfToken" name="csrfToken" path="csrfToken" value="" />

	<div class="row">
		<div class="col-md-5 col-md-offset-1">
		</div>
		<div class="col-md-5 col-md-offset-1">
		<p> 
			<button class="btn btn-info btn-lg" type="submit" value="Submit" >Delete</button> 
		</p>
		</div>
	</div>	
	</form:form>
	</div>
</c:if>	
<c:if test="${not empty showEditAccount}">
    <div class="well">
	<br/>User: ${user.username}
	<form:form id="fm2" modelAttribute="userToEdit" method="post" action="?edittheaccount=${user.id}">
	<form:input type="hidden" name="id" path="id" value="${user.id}" />
	<form:input type="hidden" id="csrfToken" name="csrfToken" path="csrfToken" value="" />
	<div class="row">
		<div class="col-md-5 col-md-offset-1">
			<p> Habit: </p>
		</div>
		<div class="col-md-5 col-md-offset-1">
			<p> <form:input type="text" name="habit" path="habit" class="form-control" value="${user.habit}" /> </p>
		</div>
	</div>
	<div class="row">
		<div class="col-md-5 col-md-offset-1">
			<p> Password: </p>
		</div>
		<div class="col-md-5 col-md-offset-1">
			<p> <form:input type="text" name="password" path="password" class="form-control" value="${user.password}" /> </p>
		</div>
	</div>	
	<div class="row">
		<div class="col-md-5 col-md-offset-1">
			<p> User Status </p>
		</div>
		<div class="col-md-5 col-md-offset-1">
			<p> <form:input type="text" name="isUser" path="isUser" class="form-control" value="${user.isUser}" /> </p>
		</div>
	</div>
	<div class="row">
		<div class="col-md-5 col-md-offset-1">
			<p> Admin Status </p>
		</div>
		<div class="col-md-5 col-md-offset-1">
			<p> <form:input type="text" name="isAdmin" path="isAdmin" class="form-control" value="${user.isAdmin}" /> </p>
		</div>
	</div>	
	<div class="row">
		<div class="col-md-5 col-md-offset-7">
		<p> 
			<button class="btn btn-info btn-lg" type="submit" value="Submit" >Update</button> 
		</p>
		</div>
	</div>	
	</form:form>
	</div>
</c:if>	
<c:if test="${not empty adminSettings}">
    <div class="well">
    <h2>Admin Config</h2>
	<c:forEach items="${adminItems}" var="adminItem">
		<br/>
		<div class="row">
		    <form:form id="fm${adminItem.id}" modelAttribute="adminDBItemToEdit" method="post" action="?editadminitem">
		    <form:input type="hidden" name="id" path="id" value="${adminItem.id}" />
		    <form:input type="hidden" name="settingName" path="settingName" value="${adminItem.settingName}" />
			<div class="col-md-3">
				${adminItem.settingName}
			</div>
			<div class="col-md-6">
			<form:input type="text" name="settingValue" path="settingValue" class="form-control" value="${adminItem.settingValue}" size="50" />
			</div>
			<div class="col-md-3">
				<button class="btn btn-info btn-lg" type="submit" value="Submit" >Update</button> 
			</div>
			</form:form>
		</div>
    </c:forEach> 
    </div>
</c:if>
<h2>Admin Menu</h2>
  <div class="row text-center">
	<div class="col-md-8 col-md-offset-2 col-xs-10 col-xs-offset-1 well">
		<font color="orange" size="32px">
		<i class='glyphicon glyphicon-book'></i>
		</font>
		<h2>Logs</h2>
		<p>View Logs</p>
		<p>
			<a class="btn btn-warning btn-lg" href="?logs" role="button">View details</a>
		</p>
	</div>
  </div>
  <div class="row text-center">
	<div class="col-md-5 col-md-offset-1 col-xs-6 well">
		<font color="orange" size="32px">
		<i class='glyphicon glyphicon-flag'></i>
		</font>
		<h2>Posts</h2>
		<p>Approve / Delete posts</p>
		<p>
			<a class="btn btn-default btn-lg" href="?posts" role="button">View details</a>
		</p>
	</div>
	<div class="col-md-5 col-md-offset-1 col-xs-6 well">
		<font color="orange" size="32px">
		<i class='glyphicon glyphicon-cloud'></i>
		</font>
		<h2>Comments</h2>
		<p>Approve / Delete comments</p>
		<p>
			<a class="btn btn-default btn-lg" href="?comments" role="button">View details</a>
		</p>
	</div>
  </div>
  <div class="row text-center">  
	<div class="col-md-5 col-md-offset-1 col-xs-6 well">
		<font color="orange" size="32px">
		<i class='glyphicon glyphicon-leaf'></i>
		</font>
		<h2>Accounts</h2>
		<p>Update user accounts</p>
		<p>
			<a class="btn btn-default btn-lg" href="?accounts" role="button">View details</a>
		</p>
	</div>
	<div class="col-md-5 col-md-offset-1 col-xs-6 well">
		<font color="orange" size="32px">
		<i class='glyphicon glyphicon-wrench'></i>
		</font>
		<h2>Config</h2>
		<p>Update admin settings</p>
		<p>
			<a class="btn btn-default btn-lg" href="?adminSettings" role="button">View details</a>
		</p>
	</div>	
  </div>
 <% } else {%>
 <H3><font color="red"><b>Beware for the path that you take leads to certain destruction! </b></font></H3>
 <% } %>
  <hr>
  <footer>
	<p>&copy; Habit Helper 2017</p>
  </footer>
</div>
 
 <jsp:include page='javascript_includes.jsp'></jsp:include> 
</body>
</html>