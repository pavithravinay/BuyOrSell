<!-- <Pavithra> -->

<%@page import="service.AdvertisementService"%>
<%@page import="bean.Advertisement"%>
<%@page import="util.CommonUtils"%>
<%@page import="java.util.*"%>

<%@ include file="htmlHead.jsp"%>
<%@ include file="pageHeader.jsp"%>
<%@ include file="leftSideBar.jsp"%>

<div class="features_items" style="margin-top: 10px;">
	<!--features_items-->
	<!-- <h2 class="title text-center">All Ads</h2> -->
	<%
		String pageNumberString = request.getParameter("pageNumber");
		String categoryIdString = request.getParameter("categoryId");
		int pageNumber = 1;
		int categoryId = 0;
		int totalAdCount = 0;
		
		if (pageNumberString != null && !pageNumberString.isEmpty()) {
			pageNumber = Integer.parseInt(pageNumberString);
		}
		if (categoryIdString != null && !categoryIdString.isEmpty()) {
			categoryId = Integer.parseInt(categoryIdString);
		}

		List<Advertisement> advertisementList = null;
		AdvertisementService advertisementService = new AdvertisementService();
		
		if (categoryId != 0) {
			advertisementList = advertisementService.getLimitedAdvertisementsByCategory(CommonUtils.ADS_PER_PAGE,
					pageNumber, categoryId);
			totalAdCount = advertisementService.getTotalAdvertisementCountByCategory(categoryId);
		} else {
			advertisementList = advertisementService.getLimitedAdvertisements(CommonUtils.ADS_PER_PAGE, pageNumber);
			totalAdCount = advertisementService.getTotalAdvertisementCount();
		}
		//int adCount = Math.min(totalAdCount, CommonUtils.ADS_PER_PAGE);
	%>
	<!-- <div class="container"> -->

	<% for(Advertisement ad: advertisementList) { %>
	<div class="col-sm-4">
		<div class="product-image-wrapper">
			<div class="single-products">
				<div class="productinfo text-center">
					<a href="advertisement?id=<%=ad.getId() %>">
						<img style="width:200px; height:250px;" src="UploadServlet?adid=<%=ad.getId()%>" alt="" />
					</a>
					<h2>$<%=String.format("%.2f", ad.getPrice()) %></h2>
					<a href="advertisement?id=<%=ad.getId() %>">
						<p><%=ad.getTitle()%></p>
					</a>
				</div>
			</div>
			<div class="choose">
				<ul class="nav nav-pills nav-justified">
					<li><a href="wishlist?action=add&id=<%=ad.getId()%>"><i class="fa fa-plus-square"></i>Add to wishlist</a></li>
					<li><a href="advertisement?id=<%=ad.getId()%>"><i class="fa fa-plus-square"></i>View Details</a></li>
				</ul>
			</div>
		</div>
	</div>
	<% } %>
</div>
<!--features_items-->

<div class="row text-center">
	<div class="col-sm-12">
		<div class="header-bottom">
			<ul class="pagination">
				<%
					int pageCount = (int) Math.ceil((float) totalAdCount / (float) CommonUtils.ADS_PER_PAGE);
					int pageNum = 1;
					while (pageNum <= pageCount) {
				%>
				<li><a
					href="advertisements?pageNumber=<%=pageNum%>&categoryId=<%=categoryId%>"><%=pageNum%></a></li>
				<% pageNum++; } %>

			</ul>
		</div>
	</div>
</div>

<!--/form-->
<%@ include file="pageFooter.jsp"%>
<!-- </Pavithra> -->
