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
<spring:url value="/resources/core/js/elizabot.js" var="elizaBotJS" />
<spring:url value="/resources/core/js/elizadata.js" var="elizaDataJS" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />
	<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="${elizaBotJS}"></SCRIPT>
	<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="${elizaDataJS}"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
<!--

var eliza = new ElizaBot();
var elizaLines = new Array();

var displayCols = 50;
var displayRows = 20;

function elizaReset() {
	eliza.reset();
	elizaLines.length = 0;
	elizaStep();
}

function elizaStep() {
	var f = document.forms.e_form;
	var userinput = f.e_input.value;
	 
	if (eliza.quit) {
		f.e_input.value = '';
		if (confirm("This session is over.\nStart over?")) elizaReset();
		f.e_input.focus();
		return;
	}
	else if (userinput != '') {
		var usr = 'YOU:   ' + userinput;
		var rpl ='Dr. Eliza: ' + eliza.transform(userinput);
		elizaLines.push(usr);
		elizaLines.push(rpl);
		// display nicely
		// (fit to textarea with last line free - reserved for extra line caused by word wrap)
		var temp  = new Array();
		var l = 0;
		for (var i=elizaLines.length-1; i>=0; i--) {
			l += 1 + Math.floor(elizaLines[i].length/displayCols);
			if (l >= displayRows) break
			else temp.push(elizaLines[i]);
		}
		elizaLines = temp.reverse();
		f.e_display.value = elizaLines.join('\n');
	}
	else if (elizaLines.length == 0) {
		// no input and no saved lines -> output initial
		var initial = 'ELIZA: ' + eliza.getInitial();
		elizaLines.push(initial);
		f.e_display.value = initial + '\n';
	}
	f.e_input.value = '';
	f.e_input.focus();
}

function elizaInit() {
	document.getElementById("ElizaNote").innerHTML = "Dr. Eliza is writing a message";
}

function elizaClear() {
	 document.getElementById("ElizaNote").innerHTML = "";
}

function elizaOutput() {
	var x = Math.floor((Math.random() * 750) + 250);
	setTimeout(elizaInit, x);
	var y = Math.floor((Math.random() * 4000) + 1001);
	setTimeout(elizaStep,y);
	setTimeout(elizaClear, y-x);
}

//-->
</SCRIPT>
</head>
  <body onload="getScoresOfAll('${user}')">
<jsp:include page='navbar.jsp'></jsp:include>

 <script>
// Harry, the PUT requests got turned into options requests so I changed everything to 
// GET requests, which seemed to work great.... Bob
/*
Bob, that's because of the SOP, it's fine.  Changing back.  Harry
*/
// Harry, your async stuff just doesn't work, so I switched it to use sync.  If you have a problem
// with this you can make it work and let me know.  Bob
 function updateScore() {
		inspiringImage("image");
		var obj = new Object();
        obj["userName"] = "${user}";
		obj["score"] = parseInt(readCookie("score"));
		obj["score"] = obj["score"] + 1;
		if (obj["score"] <= 23) {
			createCookie("score", obj["score"], 1);
		}
		var json = JSON.stringify(obj);
		document.getElementById("test").innerHTML = json;

		//var json = JSON.stringify(obj);
		var xhr = new XMLHttpRequest();
		xhr.open("PUT", "${serviceURL}", false);
		xhr.setRequestHeader('Content-type','application/json; charset=utf-8');
		xhr.send(json);
		document.getElementById("test").innerHTML = xhr.responseText;
		//xhr.onload = function () {
		//    var result = JSON.parse(xhr.responseText);
		//    if (xhr.readyState == 4 && xhr.status == "200") {
		//		document.getElementById("test").innerHTML = result;
		//   } 
		//  xhr.send(json);
		//};
      //document.getElementById("test").innerHTML = obj[1].userName + " " + obj[1].score;
    }
	
	

	
function Get(yourUrl){
    var Httpreq = new XMLHttpRequest(); // a new request
    Httpreq.open("GET",yourUrl,false);
    Httpreq.send(null);
    return Httpreq.responseText;          
}	

function getScoresOfAll(user) {

	result = Get("${serviceURL}");
	obj = JSON.parse(result);
	document.getElementById("score").innerHTML = "<br/>";
	createCookie("score", "0", 1);
	for(var i = 0; i < obj.length; i++) {
		//alert("(" + obj[i].userName + ")(" + user + ")"); 
		if (obj[i].userName === user) {
			createCookie("score", obj[i].score, 1);
		}
		document.getElementById("score").innerHTML += "<div class=\"row\"><div class=\"col-md-5 col-md-offset-1\"><p>" + obj[i].userName + "</p></div><div class=\"col-md-5 col-md-offset-1\"><p>" + obj[i].score + "</p></div></div>";
	}
}

function getScore() {
	//var xhttp = new XMLHttpRequest();
	//xhttp.onreadystatechange = function() {
     // if (this.readyState == 4 && this.status == 200) {
	//	var obj= JSON.parse(this.responseText);
	obj = Get("${serviceURL}Duck");
		document.getElementById("test").innerHTML = obj["userName"] + " " + obj["score"];
	 // }
	 // xhttp.open("GET", "http://localhost:8080/rest/provider/Duck", true);
	 // xhttp.send();
    //};
    //return obj;	
}


</script>
 
<div class="container"> 

  <br/>

	<hr class="featurette-divider">	
<c:if test="${not empty update}">	
	<h2>Hello, ${user} </h2>
	<p>
		<c:if test="${not empty message}">
			<font color="red">
			${message}
			</font>
		</c:if>
	</p>
	<div class="well">
	<h2>Update your habit</h2>
	<form:form id="fm1" modelAttribute="updateHabitForm" method="post" action="?update=yes&updatehabit=yes">
	<div class="row">
		<div class="col-md-4">
			<p> Habit: </p>
		</div>
		<div class="col-md-6 col-md-offset-1">
			<p> <form:input type="text" path="habit" name="habit" value="${habit}" class="form-control" /> </p>
		</div>
	</div>	
		<div class="row">
		<div class="col-md-4">
		</div>
		<div class="col-md-6 col-md-offset-1">
		<p> 
			<button class="btn btn-info btn-lg" class="form-control" type="submit" value="Submit" >Update</button> 
		</p>
		</div>
	</div>
   </form:form>
   </div>
   <br/>
   <div class="well">
   <h2>Update your password</h2>
   <form:form id="fm2" modelAttribute="updatePasswordForm" method="post" action="?update=yes&updatepassword=yes">
	<div class="row">
		<div class="col-md-4">
			<p> Password: </p>
		</div>
		<div class="col-md-6 col-md-offset-1">
			<p> <form:input type="password" path="password" name="password" value="" class="form-control" onkeyup="testPassword(document.forms.fm2.password.value, 'passwordCheck')"/> </p>
		</div>
	</div>
	<p id="passwordCheck"></p>
	<div class="row">
		<div class="col-md-4">
			<p> Confirm Password: </p>
		</div>
		<div class="col-md-6 col-md-offset-1">
			<p> <form:input type="password" path="confirmPassword" name="confirmPassword" value="" class="form-control" onkeyup="testPasswordMatch(document.forms.fm2.password.value, document.forms.fm2.confirmPassword.value, 'passwordMatch')" /> </p>
		</div>
	</div>	
    <p id='passwordMatch'></p>
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
<c:if test="${not empty score}">
	<div class="well text-center">	
	<h2>Scoreboard </h2>
	<div class="row">
		<div class="col-md-5 col-md-offset-1">
			<p>User</p>
		</div>
			<p>Score</p>
		<div class="col-md-5 col-md-offset-1">
		</div>
	</div>	
	  <hr class="featurette-divider">	
	  <p id="score"> </p>
	  
	<p>
	<button class="btn btn-info btn-lg btn-block" type="submit" value="Submit" onclick="getScoresOfAll('${user}');" >Reload scores</button> 
	</p>
	</div>	  
</c:if>	
<c:if test="${not empty resist}">	
    <div class="well">
	<h2>Have you resisted your bad habit?</h2>
	<p class="lead"> Remember clicking you IOT device is more effective.</P>
	<ul class="unstyled"> 
		<li>Clicking a physical button will produce more dopamine </li> 
		<li>Increases the reward feel good chemicals in your brain </li>  
		<li>Only click here if you do not have your IOT device </li></ul>
	<p>
	<button class="btn btn-info btn-lg btn-block" type="submit" value="Submit" onclick="updateScore();" >Click if you resisted your bad habit</button> 
	</p>
	<p id="image"></p>
	<p id="test"></p>
	</div>
</c:if>	
<c:if test="${not empty therapist}">	
     <div class="well text-center">
	<h2>Talk to our online therapist</h2>
	<p>
	<FORM NAME="e_form" onsubmit="elizaOutput();return false">
    <TEXTAREA NAME="e_display" class="form-control" COLS="50" ROWS="25"></TEXTAREA>
	</p><p>
    <INPUT TYPE="text" NAME="e_input" class="form-control">
	</p><p>
	<button class="btn btn-info btn-lg" class="form-control" TYPE="submit" VALUE="&nbsp;Talk&nbsp;">&nbsp;Talk&nbsp;</button> 
	<button class="btn btn-default btn-lg" class="form-control" TYPE="reset" VALUE="Reset" onClick="window.setTimeout('elizaReset()',100)">reset</button>
    </FORM>	
	</p>
	<p id="ElizaNote"/>
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
	<div class="col-md-10 col-md-offset-1">
		<h2>Account Menu</h2>
	</div>
  </div>
  <div class="row text-center">
	<div class="col-md-8 col-md-offset-2 col-xs-10 col-xs-offset-1 well">
		<font color="orange" size="32px">
		<i class='glyphicon glyphicon-eye-open'></i>
		</font>
		<h2>Check</h2>
		<p>Check today's scoreboard.</p>
		<p>
			<a class="btn btn-warning btn-lg" href="?score" role="button">View details</a>
		</p>
	</div>
  </div>
  <div class="row text-center">
	<div class="col-md-5 col-md-offset-1 col-xs-6 well">
		<font color="orange" size="32px">
		<i class='glyphicon glyphicon-star-empty'></i>
		</font>
		<h2>Resist</h2>
		<p>Click the resist button.</p>
		<p>
			<a class="btn btn-default btn-lg" href="?resist" role="button">View details</a>
		</p>
	</div>
	<div class="col-md-5 col-md-offset-1 col-xs-6 well">
		<font color="orange" size="32px">
		<i class='glyphicon glyphicon-edit'></i>
		</font>
		<h2>Update</h2>
		<p>Change your account setting and preferences</p>
		<p>
			<a class="btn btn-default btn-lg" href="?update" role="button">View details</a>
		</p>
	</div>  
  </div>
  <div class="row text-center">  
	<div class="col-md-5 col-md-offset-1 col-xs-6 well">
		<font color="orange" size="32px">
		<i class='glyphicon glyphicon-comment'></i>
		</font>
		<h2>Forum</h2>
		<p>Go to the public area to see others latest posts</p>
		<p>
			<a class="btn btn-default btn-lg" href="public" role="button">View details</a>
		</p>
	</div>  
	<div class="col-md-5 col-md-offset-1 col-xs-6 well">
		<font color="orange" size="32px">
		<i class='glyphicon glyphicon-apple'></i>
		</font>
		<h2>Therapy</h2>
		<p>Talk to our online therapist</p>
		<p>
			<a class="btn btn-default btn-lg" href="?therapist" role="button">View details</a>
		</p>
	</div>  
  </div>
 <% } %>
  <hr>
  <footer>
		<p>&copy; Habit Helper 2017</p>
  </footer>
</div>
 <jsp:include page='javascript_includes.jsp'></jsp:include> 
</body>
</html>