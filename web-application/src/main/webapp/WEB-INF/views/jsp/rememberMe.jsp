<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


	<script>
	function turnRememberMeOn() {
		if (document.getElementById("rememberme").checked == true) {
			localStorage.setItem("rememberme", "true");
			localStorage.setItem("rememberMeUser", document.getElementById("username").value);
		}
		if (document.getElementById("rememberme").checked == false) {
			localStorage.removeItem("rememberme");
		}
	}
	function setRememberMe() {
		var url = new URL(window.location.href);
		if (url.searchParams.get('rememberMeToken')) {
			var token = url.searchParams.get("rememberMeToken");
			localStorage.setItem("rememberMeToken", token);
		}
		if (localStorage.getItem("rememberme") != null && document.getElementById("rememberme")!= null) {
			if (localStorage.getItem("rememberme") == "true") {
				document.getElementById("rememberme").checked = true;
				document.getElementById("rememberme").value = true;
				if (localStorage.getItem("rememberMeToken") != null) {
					document.getElementById("rememberMeToken").value = localStorage.getItem("rememberMeToken");
					document.getElementById("password").value = "DoNotUse";
				}
				if (localStorage.getItem("rememberMeUser") != null) {
					document.getElementById("username").value = localStorage.getItem("rememberMeUser");
				}
			}
		}
	}
	window.onload=setRememberMe();
	</script>
