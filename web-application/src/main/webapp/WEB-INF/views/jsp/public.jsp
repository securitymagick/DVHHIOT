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
%>
<div class="container"> 


  <br/>
	<hr class="featurette-divider">
<c:if test="${not empty write}">
	<div class="well">
	<h2>Add New Forum to Post</h2>
	<p>
		<c:if test="${not empty message}">
			<font color="red">
			${message}
			</font>
		</c:if>
	</p>
	<c:if test="${not empty part1}">

	<form id="fm1" enctype="multipart/form-data" method="post" action="?write=yes&uploadfile=yes">
	<div class="row">
		<div class="col-md-4">
			<p> To make every post helpful to our other users please upload an inspring image.
			<p> Select Image file to upload: </p>
		</div>
		<div class="col-md-6 col-md-offset-1">
			<p> <input type="file" name="file"  value="" /> </p>
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
		</div>
		<div class="col-md-6 col-md-offset-1">
		<p> 
			<button class="btn btn-info btn-lg" type="submit" value="Submit" >Upload</button> 
		</p>
		</div>
	</div>
    </form>
	</c:if>
	<c:if test="${not empty part2}">
	<br/><img src="/habit-helper-1.0/resources/core/images/${imageName}"/>
	<form:form id="fm2" modelAttribute="forumPostForm" method="post" action="?write=yes&createpost=yes">
	<div class="row">
		<div class="col-md-4">
			<p> Enter Title (Inspiring or Call for Help): </p>
		</div>
		<div class="col-md-4">
			<p> <form:input type="text" name="title" path="title"  value="" /> </p>
			    <form:input type="hidden" name="author" path="author" value="${user}" />
				 <form:input type="hidden" name="imageName" path="imageName" value="${imageName}" />
				 <form:input type="hidden" id="csrfToken" name="csrfToken" path="csrfToken" value="" />
		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<p> Post: </p>
		</div>	
		<div class="col-md-6 col-md-offset-1">
			<p> <form:textarea name="thePost" path="thePost" value="" rows="5" cols="60" onkeyup="testText(document.forms.fm2.thePost.value, 'jsCheck', document.forms.fm2.submitPost)" /> </p>
		</div>
	</div>	
	<p id='jsCheck'></p>
	<div class="row">
		<div class="col-md-4">
		</div>
		<div class="col-md-6 col-md-offset-1">
		<p> 
			<button class="btn btn-info btn-lg" type="submit" value="Submit" name="submitPost" >Submit</button> 
		</p>
		</div>
	</div>
    </form:form>
	</c:if>
    </div>
</c:if>
<c:if test="${not empty read}">
	<div class="well">
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
		if (showAddComment) {
	%>
	<h2>Add a new Comment </h2>
	<form:form id="fm1" modelAttribute="postComment" method="post" action="?read=yes&addComment=yes">
	<div class="row">
		<div class="col-md-4">
			<p> Comment: </p>
		</div>	
		<div class="col-md-6 col-md-offset-1">
			<p> <form:textarea name="comment" path="comment" value="" rows="5" cols="60" onkeyup="testText(document.forms.fm1.comment.value, 'jsComment', document.forms.fm1.submitComment)" /> </p>
		</div>
	</div>
	<p id='jsComment'></p>
	<form:input type="hidden" name="postid" path="postid" value="${post.id}" />
    <form:input type="hidden" name="username" path="username" value="${user}" />	
    <form:input type="hidden" id="csrfToken" name="csrfToken" path="csrfToken" value="" />
	
	<div class="row">
		<div class="col-md-4">
		</div>
		<div class="col-md-6 col-md-offset-1">
		<p> 
			<button class="btn btn-info btn-lg" type="submit" value="Submit" name="submitComment" >Add Comment</button> 
		</p>
		</div>
	</div>	
	</form:form>
	<% } %>
	</div>
</c:if>
<c:if test="${not empty forum}">
	<div class="well">
	<h2>Latest Posts</h2>
	<p>
		<c:if test="${not empty message}">
			<font color="red">
			${message}
			</font>
		</c:if>
	</p>	
	<c:forEach items="${posts}" var="post">
	  <hr class="featurette-divider">

      <div class="row featurette">
        <div class="col-md-7">
          <h2 class="featurette-heading"><a href="?read=yes&id=${post.id}">${post.title} </a> <span class="text-muted">It'll blow your mind.</span></h2>
          <p class="lead">Posted by ${post.author}</p>
        </div>
        <div class="col-md-5">
		  <img class="featurette-image img-responsive center-block" src="/habit-helper-1.0/resources/core/images/${post.imageName}"/>
          <!--<img class="featurette-image img-responsive center-block" data-src="holder.js/500x500/auto" alt="Generic placeholder image">-->
        </div>
      </div>
    </c:forEach>
</div>
</c:if>
  <div class="row text-center"> 
	<div class="col-md-10 col-md-offset-1">
		<h2>Forum Menu</h2>
	</div>
  </div>
  <div class="row text-center">
	<div class="col-md-5 col-md-offset-1 col-xs-6 well">
		<font color="orange" size="32px">
		<i class='glyphicon glyphicon-list-alt'></i>
		</font>
		<h2>Posts</h2>
		<p>Read posts and help others on the forum</p>
		<p>
			<a class="btn btn-warning btn-lg" href="?forum" role="button">View details</a>
		</p>
	</div>
	<div class="col-md-5 col-md-offset-1 col-xs-6 well">
		<font color="orange" size="32px">
		<i class='glyphicon glyphicon-pencil'></i>
		</font>
		<h2>Write</h2>
		<p>Create a new forum post to get help or inspire others</p>
		<p>
			<a class="btn btn-default btn-lg" href="?write" role="button">View details</a>
		</p>
	</div>
  </div>	
 
 
  <hr>
  <footer>
		<p>&copy; Habit Helper 2017</p>
  </footer>
</div>

 <jsp:include page='javascript_includes.jsp'></jsp:include> 
</body>
</html>
