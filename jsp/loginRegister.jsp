<%@page import="util.Errors"%>
<%@ include file="htmlHead.jsp" %>
<%@ include file="pageHeader.jsp" %>
<%@ include file="leftSideBar.jsp" %>
<!--form-->
<%
String error = request.getParameter("error");
/* if (error != null) {
	switch(Integer.parseInt(error.trim())) {
	case Error.
	}
} */
%>
<div class="col-sm-4">
	<div class="signup-form">
		<!--sign up form-->
		<h2>New User Signup!</h2>
		<form id="signup" action="SignUpServlet" method="post">
			<p><input type="text" name="firstname" data-validation="required" placeholder="First Name" /></p>
			<p><input type="text" name="lastname" data-validation="required" placeholder="Last Name" /></p>
			<p><input type="text" name="email" data-validation="email" placeholder="Email Address" /></p>
			<p><input type="password" name="password" data-validation="strength" data-validation-strength="2" data-validation="length" data-validation-length="min8" placeholder="Password" /></p>
			<p><input type="password" name="confirmpassword" data-validation="confirmation" data-validation-confirm="password" placeholder="Confirm Password" /></p>
			<button type="submit" name="action" value="register" class="btn btn-default">Signup</button>
		</form>
	</div>
	<!--/sign up form-->
</div>
<div class="col-sm-1" style="padding-top: 20px">
	<h2 class="or">OR</h2>
</div>
<div class="col-sm-4">
	<div class="login-form">
		<!--login form-->
		<h2>Login!!</h2>
		<form id="login" action="LoginServlet" method="post">
			<p><input type="text" data-validation="email" name="email" placeholder="Email Address" /></p>
			<p><input type="password" data-validation="required" name="password" placeholder="Password" /></p>
			<p><a href="forgot-password">Forgot Password</a></p>
			<input type="hidden" name="action" value="<%=request.getParameter("action")%>" />
			<input type="hidden" name="id" value="<%=request.getParameter("id")%>" />
			<p><button type="submit" class="btn btn-default">Login</button></p>
		</form>
	</div>
	<!--/login form-->
</div>
<!--/form-->
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.3.26/jquery.form-validator.min.js"></script>
<script>
$.validate({
	form : '#signup, #login'/*,
	modules : 'security' ,
	  onModulesLoaded : function() {
	    var optionalConfig = {
	      fontSize: '12pt',
	      padding: '4px',
	      bad : 'Very bad',
	      weak : 'Weak',
	      good : 'Good',
	      strong : 'Strong'
	    };

	    $('input[name="password"]').displayPasswordStrength(optionalConfig);
	  } */
	});
</script>
<%@ include file="pageFooter.jsp" %>