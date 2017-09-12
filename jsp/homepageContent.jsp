<%@page import="service.AdvertisementService"%>
<%@page import="bean.Advertisement"%>
<%@page import="java.util.List"%>
<%
AdvertisementService advertisementService = new AdvertisementService();
//Get most viewed ads from SQLDb;
List<Advertisement> mostViewedAds = advertisementService.getMostViewedAds();
%>
<div class="col-sm-9 padding-right">
	<!-- Top Viewed Ads -->
		<div class="features_items"><!--Top Viewed Ads-->
			<h2 class="title text-center">Top Viewed Ads</h2>
			<% int viewCounter=0; 
			for(Advertisement ad:mostViewedAds){%>
			
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
								<%-- <a href="advertisement?id=<%=ad.getId() %>">
									<p>ViewCount : <%=ad.getViewCount()%></p>
								</a> --%>
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
			<%
			viewCounter++;
			if(viewCounter>=6){
				break;
			}
			} %>	
		</div><!--Top Viewed Ads-->
	</div>		