<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.securitymagick.domain.Permissions" %>
<%@ page import="com.securitymagick.domain.Notifications" %>
<%@ page import="com.securitymagick.web.cookie.CookieHandler" %>

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

<nav class="navbar navbar-default"></nav> <!-- Dummy nav bar -->
<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
	<div class="navbar-header">
		<a class="navbar-brand" href="/habit-helper-1.0">
		<div style="text-shadow: 2px 2px orange;">
		<font color="black">
		<i class='glyphicon glyphicon-header'></i>
		<i class='glyphicon glyphicon-glass'></i>		
		<i class='glyphicon glyphicon-header'></i>
		<i class='glyphicon glyphicon-ice-lolly-tasted'></i>
		<c:if test="${not empty title}">
		${title}
		</c:if>
		
		</font>
		</div>
		</a>

		<ul class="nav nav-tabs">
			<li><a href="#" data-toggle="modal" data-target="#myModal" onClick="inspiringNotification('inspireImg', 'inspireTxt')"> Notifications</a></li>
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
			if (showAdmin) {
		%>
			<li><a href="admin">Admin</a></li>	
		<% } 
		   if (showAlreadyLoggedIn) {
		%>
			<li><a href="myAccount">Your Account</a> </li>		
			<li><a href="logout">Sign Out</a></li>
			
		<% } %>	
		<c:if test="${not empty resetpasswordEmail}">
			<li><a href="#" data-toggle="modal" data-target="#myEmail"> NEW EMAIL</a></li>
		</c:if>						
		</ul>
		
		
	</div>
  </div>
  <div id="line"></div>
</nav>

  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <% if (showAlreadyLoggedIn) { %>
          <h4 class="modal-title">Your Account Notices</h4>
           </div>
        <div class="modal-body">
          <p id="inspireTxt"></p>
          <p id="inspireImg"></p>
        </div>
          <% } else { %>
          <h4 class="modal-title">Login For Your Account Notices</h4>
          </div>
          <% } %>

        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>
      </div>
      </div>
    </div>
    
    <div class="modal fade" id="myEmail" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          
          <h4 class="modal-title">Your New Reset Password Email</h4>
         </div>
        <div class="modal-body">
          <p id="resetPasswordTxt">${resetpasswordEmail}</p>
        </div>

        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
</div>    

  <% } %>