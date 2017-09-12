<%@page import="java.text.SimpleDateFormat"%>
<%@page import="bean.Address"%>
<%@page import="bean.User"%>
<%@ include file="htmlHead.jsp" %>
<%@ include file="pageHeader.jsp" %>
<%-- <%@ include file="leftSideBar.jsp" %> --%>
<%
if(session.getAttribute("user") == null) {
	response.sendRedirect("login");
	return;	
}

if(request.getParameter("error") != null && request.getParameter("error").equalsIgnoreCase("2")) {
	response.sendRedirect("home");
	return;	
}

User user = (User) session.getAttribute("user");
SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

String gender = user.getGender();
String dateOfBirth = formatter.format(user.getDateOfBirth());
String contactNumber = user.getContactNumber();
Address address = user.getAddress();
String add = address != null ? address.getAddress() : "";
String city = address != null ? address.getCity() : "";
String state = address != null ? address.getState() : "";
String zipcode = address != null ? address.getZipcode() : "";

%>
<div class="col-sm-5">
	<div class="signup-form">
		<!--update profile form-->
		<h2>Update Profile</h2>
		<form action="SignUpServlet" method="post">
			<p>
				Gender : <select name="gender" style="width: 100px;">
					<option value="Male"
						<%=gender.equalsIgnoreCase("male") ? "selected" : ""%>>Male</option>
					<option value="Female"
						<%=gender.equalsIgnoreCase("female") ? "selected" : ""%>>Female</option>
					<option value="Other"
						<%=gender.equalsIgnoreCase("other") ? "selected" : ""%>>Other</option>
				</select>
			</p>
			<p>Date of Birth : <input type="text" id="dateOfBirth" name="dateOfBirth" placeholder="Date Of Birth" value="<%=dateOfBirth%>" /></p>
			Contact Number : <input type="text" name="contact" placeholder="Contact Number" value="<%=contactNumber%>" />
			Address Line 1 : <input type="text" name="address1" placeholder="Address Line 1" value="<%=add%>" />
			Address Line 2 : <input type="text" name="address2" placeholder="Address Line 2" />
			City : <input type="text"
				name=city placeholder="City" value="<%=city%>" />
			State : <input
				type="text" name="state" placeholder="State" value="<%=state%>" />
			Zipcode: <input type="text" name="zipcode" placeholder="Zipcode"
				value="<%=zipcode%>" />
			<button type="submit" name="action" value="updateProfile"
				class="btn btn-default">Update Profile</button>
			<!-- <button type="submit" name="action" value="skip" class="btn btn-default">Skip</button> -->
		</form>
	</div>
	<!--/update profile form-->
</div>
<div class="col-sm-1" style="padding-top: 90px">
	<h2 class="or">OR</h2>
</div>
<div class="col-sm-4">
	<div class="login-form">
		<!--change password form-->
		<h2>Change Password</h2>
		<form action="SignUpServlet" method="post">
			<input type="password" name="currentpwd" placeholder="Current Password" /> <input
				type="password" name="newpwd" placeholder="New Password" /> <input
				type="password" name="confirmpwd" placeholder="Confirm New Password" />
			<button type="submit" name="action" value="updatePassword"
				class="btn btn-default">Change Password</button>
		</form>
	</div>
	<!--/change password form-->
</div>
<!--/form-->
<%@ include file="pageFooter.jsp" %>
<script>
  $( function() {
    $( "#dateOfBirth" ).datepicker({ minDate: "-150Y", maxDate: "-10Y", dateFormat: "mm-dd-yy" });
  } );
</script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.3.26/jquery.form-validator.min.js"></script>
<script>
  $.validate({
    modules : 'security'
  });
</script>