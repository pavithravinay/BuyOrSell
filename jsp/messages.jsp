<%@page import="java.text.SimpleDateFormat"%>
<%@page import="service.UserService"%>
<%@page import="bean.Message"%>
<%@page import="java.util.List"%>
<%@page import="service.MessageService"%>
<%@ include file="htmlHead.jsp"%>
<%@ include file="pageHeader.jsp"%>
<%
if(session.getAttribute("user") == null) {
	response.sendRedirect("login");
	return;
}

MessageService service = new MessageService();
if(request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
	service.markMessageRead(Integer.parseInt(request.getParameter("id")));
}

User user = (User) session.getAttribute("user");
int userId = user.getId();

List<Message> sentMessages = service.getSentMessages(userId);
List<Message> receivedMessages = service.getRecievedMessages(userId);
%>
<div class="category-tab shop-details-tab">
	<!--category-tab-->
	<div class="col-sm-12">
		<ul class="nav nav-tabs">
			<li class="active"><a href="#received" data-toggle="tab">Received</a></li>
			<li><a href="#sent" data-toggle="tab">Sent</a></li>
		</ul>
	</div>
	
	<div class="tab-content">
		<div class="tab-pane fade active in messages" id="received">
			<div class="col-sm-12">
			<% 
			UserService userService = new UserService();
			SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy");
			
			if (receivedMessages.size() == 0) {
			%>
			<p>No Received messages found.</p>
			<% } else { %>
			<% if (service.getUnreadMessagesCount(userId) > 0) { %>
			<p><b>Click on the message to mark it as read.</b></p>
			<% }
				for(Message message: receivedMessages) { 
					User from = userService.getUser(message.getFromUserId()); 
					User to = userService.getUser(message.getToUserId()); 
			%>
				<ul style="margin: 0 0 10px;">
					<li><a href="#"><i class="fa fa-clock-o"></i><%=timeFormatter.format(message.getDateTime()) %></a></li>
					<li><a href="#"><i class="fa fa-calendar-o"></i><%=dateFormatter.format(message.getDateTime()) %></a></li>
					<li><a href="#"><i class="fa fa-user"></i>From: <%=from.getEmail() %></a></li>
				</ul>
				
				<% if(message.isRead()) { %>
					<p style="margin: 0 0 15px;"><%=message.getMessage() %></p>
				<% } else { %>
					<p style="margin: 0 0 15px;"><a href="messages?id=<%=message.getId()%>" style="">R <b><%=message.getMessage() %></b></a></p>
				<% } } } %>
			</div>
		</div>

		<div class="tab-pane fade messages" id="sent">
			<div class="col-sm-12">
			<% if(sentMessages.size()  == 0) { %>
			<p>No Sent messages found.</p>
			<%
				} else {
					for(Message message: sentMessages) { 
						User from = userService.getUser(message.getFromUserId()); 
						User to = userService.getUser(message.getToUserId()); 
			%>
				<ul style="margin: 0 0 10px;">
					<li><a href="#"><i class="fa fa-clock-o"></i><%=timeFormatter.format(message.getDateTime()) %></a></li>
					<li><a href="#"><i class="fa fa-calendar-o"></i><%=dateFormatter.format(message.getDateTime()) %></a></li>
					<li><a href="#"><i class="fa fa-user"></i>To: <%=to.getEmail() %></a></li>
				</ul>
				<p style="margin: 0 0 15px;"><%=message.getMessage() %></p>
				<% } } %>
			</div>
		</div>

	</div>
</div>
<!--/form-->
<%@ include file="pageFooter.jsp"%>