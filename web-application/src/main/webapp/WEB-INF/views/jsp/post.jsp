<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.securitymagick.domain.Permissions" %>
<%@ page import="com.securitymagick.web.cookie.CookieHandler" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Public Habit Helper Posts</title>
 
<spring:url value="/resources/core/css/hello.css" var="coreCss" />
<spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />
</head>
 
<jsp:include page='navbar.jsp'></jsp:include> 
 
  <div class="container">
	<p>
		<c:if test="${not empty message}">
			<font color="red">
			${message}
			</font>
		</c:if>
	</p>
	<hr class="featurette-divider">	
      <div class="row featurette">
        <div class="col-md-7">
          <h2 class="featurette-heading"><a href="post?id=${post.id}">${post.title} </a> <span class="text-muted">Posted by ${post.author}</span></h2>
          <p class="lead">${post.thePost}</p>
        </div>
        <div class="col-md-5">
		  <img class="featurette-image img-responsive center-block" src="/habit-helper-1.0/resources/core/images/${post.imageName}"/>
          <!--<img class="featurette-image img-responsive center-block" data-src="holder.js/500x500/auto" alt="Generic placeholder image">-->
        </div>
      </div>	

	<c:forEach items="${post.comments}" var="comment">
		<hr class="featurette-divider">
      <div class="row featurette">
        <div class="col-md-7">	
		<p class="lead">${comment}</p>		
        </div>
      </div>		
    </c:forEach>
		<% 
		Boolean showAddComment = false;
		CookieHandler p = new CookieHandler("permissions");
		if (p.checkForCookie(request)) {
			Cookie c = p.getCookie();
			Permissions userPermissions = new Permissions(c.getValue());
			if (userPermissions.getUser()) {
				showAddComment = true;
			}
		}
		if (showAddComment) {
	%>
	<h2>Add a new Comment </h2>
	<form:form id="fm1" modelAttribute="postComment" method="post" action="post">
	<div class="row">
		<div class="col-md-4">
			<p> Comment: </p>
		</div>	
		<div class="col-md-4">
			<p> <form:textarea name="comment" path="comment" value="" rows="5" cols="60"/> </p>
		</div>
	</div>
	<form:input type="hidden" name="postid" path="postid" value="${post.id}" />
    <form:input type="hidden" name="username" path="username" value="${user}" />
    <form:input type="hidden" id="csrfToken" name="csrfToken" path="csrfToken" value="" />	
	
	<div class="row">
		<div class="col-md-4">
		</div>
		<div class="col-md-4">
		<p> 
			<button class="btn btn-primary btn-lg" type="submit" value="Submit" >Add Comment</button> 
		</p>
		</div>
	</div>	
	</form:form>
	<% } %>
  <hr>
  <footer>
		<p>&copy; Habit Helper 2017</p>
  </footer>	
</div>
 
<jsp:include page='javascript_includes.jsp'></jsp:include> 
 
</body>
</html>