<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


	<script>
	function setCSRF() {
		var url = new URL(window.location.href);
		if (url.searchParams.get('newCsrfToken')) {
			var token = url.searchParams.get("newCsrfToken");
			sessionStorage.setItem("csrfToken", token);
		}
		document.getElementById("csrfToken").value = sessionStorage.getItem("csrfToken");
	}
	window.onload=setCSRF();
	</script>
