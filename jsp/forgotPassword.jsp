<%@page import="util.Errors"%>
<%@ include file="htmlHead.jsp" %>
<%@ include file="pageHeader.jsp" %>
<section id="form">
	<!--form-->
	<div class="container">
		<%
		String error = request.getParameter("error");
		/* if (error != null) {
			switch(Integer.parseInt(error.trim())) {
			case Error.
			}
		} */
		%>
		<div class="row">
			<div class="col-sm-4">
				<div class="login-form">
					<!--login form-->
					<h2>Reset Password</h2>
					<form action="ForgotPasswordServlet" method="post">
						<p><input type="text" data-validation="email" name="email" placeholder="Email Address" /></p>
						<p><button type="submit" class="btn btn-default">Forgot Password</button></p>
					</form>
				</div>
				<!--/login form-->
			</div>
		</div>
	</div>
</section>
<!--/form-->
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.3.26/jquery.form-validator.min.js"></script>
<script>
$.validate();
</script>
<%@ include file="pageFooter.jsp" %>