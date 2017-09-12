<%@page import="service.AdvertisementService"%>
<%@ include file="htmlHead.jsp"%>
<%@ include file="pageHeader.jsp"%>
<%@ include file="leftSideBar.jsp"%>
<%@page import="bean.Advertisement"%>


<div class="container">
	<div class="row">
		<div class="col-sm-9 padding-right">
			<%
				if (request.getSession().getAttribute("user") == null) {
					response.sendRedirect("login");
				} else {
					if (request.getParameter("action") != null && request.getParameter("action").equalsIgnoreCase("deletesuccess")) {
						out.print("<h3>Advertisement deleted successfully.</h3>");
					} else if (request.getParameter("action") != null && request.getParameter("action").equalsIgnoreCase("updatesuccess")) {
						out.print("<h3>Advertisement updated successfully.</h3>");
					} else if (request.getParameter("action") != null && request.getParameter("action").equalsIgnoreCase("postsuccess")) {
						out.print("<h3>Advertisement posted successfully.</h3>");
					} 
					AdvertisementService adService = new AdvertisementService();
					int userId = ((User) request.getSession().getAttribute("user")).getId();
					List<Advertisement> lstAd = adService.getAdvertisementBySeller(userId);
					if (lstAd.size() == 0) {
			%>
			<p>You haven't posted any advertisements.</p>
			<%
				} else {
			%>
			<table cellpadding="9">
				<tr>
					<th>Title</th>
					<th>Date Posted</th>
					<th>Price</th>
					<th>Actions</th>
				</tr>
				<%
					for (Advertisement ad : lstAd) {
						out.println("<tr>");
						out.println("<td>" + ad.getTitle() + "</td>");
						out.println("<td>" + ad.getPostedDate() + "</td>");
						out.println("<td>" + ad.getPrice() + "</td>");
						out.println("<td>");
						out.println("<a href='editads?id=" + ad.getId() + "' class='btn btn-fefault cart' style='margin: 0px;'>Update</a>");
						/* out.println("<a href='' class='btn btn-fefault cart'>Delete</a>"); */
						out.println("</td>");
						out.println("</tr>");
					}
				%>
			</table>
			<%
				}
				}
			%>
		</div>
	</div>
</div>

<%@ include file="pageFooter.jsp"%>