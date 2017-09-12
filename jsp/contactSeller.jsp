<%@page import="service.AdvertisementService"%>
<%@page import="bean.Advertisement"%>
<%@ include file="htmlHead.jsp"%>
<%@ include file="pageHeader.jsp"%>
<%@ include file="leftSideBar.jsp"%>

<%
boolean result = false;
Advertisement ad = null;

if (request.getParameter("email") != null || request.getParameter("phone") != null || request.getParameter("message") != null) {
	if (request.getParameter("advertismentId") != null && !request.getParameter("advertismentId").isEmpty()) {
		int advertisementId = Integer.parseInt(request.getParameter("advertismentId"));
		String userMessage = request.getParameter("message");
		User user = (User) session.getAttribute("user");
		boolean specifyPhone = request.getParameter("phone") != null;
		boolean specifyEmail = request.getParameter("email") != null;
		
		result = (new MessageService()).contactSeller(advertisementId, user, userMessage, specifyEmail, specifyPhone);
		ad = (new AdvertisementService()).getAdvertisement(advertisementId);
	}
}
%> 
<div class="container">
	<div class="row">
		<div class="col-sm-9 padding-right">
			<h2>Contact Seller</h2>
			<% if(result) { %>
			<p>The seller has been notified that you are interested in the product <b><%=ad.getTitle() %></b>. The user will contact you soon through your email addres</p>
			<% } else { %>
			<form action="#" method="post">
				<input type="checkbox" disabled="disabled" checked="checked" name="email" value="Email"> Share your email
				<br>
				<input type="checkbox" name="phone" value="Phone"> Share your contact number<br>
				<p></p>
				<p>
					<b>Additional Message</b>
				</p>
				<textarea name="message"></textarea>
				<br>
				<br>
				<input type="hidden" name="advertismentId" value="<%=request.getParameter("id") %>"/>
				<input type="submit" class="btn btn-default pull-right" name="action" value="Submit">
			</form>
			<% } %>
		</div>
	</div>
</div>
<%@ include file="pageFooter.jsp"%>