<%@page import="service.WishListService"%>
<%@page import="service.AdvertisementService"%>
<%@page import="bean.Advertisement"%>
<%@page import="util.CommonUtils"%>
<%@page import="java.util.*"%>
<%@ include file="htmlHead.jsp"%>
<%@ include file="pageHeader.jsp"%>
<%@ include file="leftSideBar.jsp"%>

<div class="features_items" style="margin-top: 10px;">
	<%
		if(session.getAttribute("user") == null) {
			response.sendRedirect("login");
			return;
		}
		User user = (User) session.getAttribute("user");
		int userId = user.getId();
		WishListService service = new WishListService();
		
		if (request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
			String advertisementIdString = request.getParameter("id");
			int advertisementId = Integer.parseInt(advertisementIdString);
			
			if(request.getParameter("action") != null && request.getParameter("action").equalsIgnoreCase("add")) {
				service.addToWishList(userId, advertisementId);
			} else if (request.getParameter("action") != null && request.getParameter("action").equalsIgnoreCase("remove")) {
				service.removeFromWishList(userId, advertisementId);
			}
		}

		List<Advertisement> wishlist = service.getWishList(userId);
		if (wishlist.size() == 0) {
			out.print("<h3>You haven't added any advertisements yo your wishlist.</h3>");
		} else {
			for (Advertisement ad : wishlist) {
	%>
			<div class="col-sm-4">
			<div class="product-image-wrapper">
				<div class="single-products">
					<div class="productinfo text-center">
						<a href="advertisement?id=<%=ad.getId() %>">
							<img src="UploadServlet?adid=<%=ad.getId()%>" alt="" />
						</a>
						<h2>$<%=String.format("%.2f", ad.getPrice()) %></h2>
						<a href="advertisement?id=<%=ad.getId() %>">
							<p><%=ad.getTitle()%></p>
						</a>
					</div>
				</div>
				<div class="choose">
					<ul class="nav nav-pills nav-justified">
						<li><a href="wishlist?action=remove&id=<%=ad.getId()%>"><i class="fa fa-plus-square"></i>Remove from wishlist</a></li>
						<%-- <li><a href="advertisement?id=<%=ad.getId()%>"><i class="fa fa-plus-square"></i>View Details</a></li> --%>
					</ul>
				</div>
			</div>
		</div>
		<% } } %>
</div>


<%@ include file="pageFooter.jsp"%>