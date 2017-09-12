<!-- <Pavithra>  -->
<!-- Top viewed ads -->
<%@ page import="java.util.*, dbutil.*"%>
<%@page import="service.AdvertisementService"%>
<%@page import="bean.Advertisement"%>
<%
	MongoDBUtilities mongoUtils = new MongoDBUtilities();
	
	//Get list of top rated sellers
	LinkedHashMap<Integer, Float> topRatedSellers  = mongoUtils.getTopRatedSellers();
	
	//Get top rated ads from MongoDb;
	LinkedHashMap<Integer, Float> topRatedAds = mongoUtils.getTopRatedAds();	
	AdvertisementService advertisementService = new AdvertisementService();
%>

<div class="col-sm-9 padding-right">
	<div class="features_items"><!-- Top Rated Ads -->
			<h2 class="title text-center">Top Rated Ads</h2>
			<% for(Map.Entry<Integer, Float> myKey: topRatedAds.entrySet()){
			 	int advertisementId = myKey.getKey();
				Advertisement ad = advertisementService.getAdvertisement(advertisementId);%>
			
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
								<a href="advertisement?id=<%=ad.getId() %>">
									<p>Rating : <%=myKey.getValue()%></p>
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
			<%} %>			
		</div><!--Top Rated Ads-->
		
		
		
		<!-- Ads by top rated sellers -->
		<div class="features_items"><!--Ads by top rated sellers-->
			<h2 class="title text-center">Ads by top Rated sellers</h2>
			<% int adCount=0;
				for(Map.Entry<Integer, Float> myKey: topRatedSellers.entrySet()){
			 	int sellerId = myKey.getKey();
				List<Advertisement> adsList = advertisementService.getAdvertisementBySeller(sellerId);
				
				for(Advertisement ad:adsList) {
			%>
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
								<a href="advertisement?id=<%=ad.getId() %>">
									<p>Seller Rating : <%=String.format("%.2f", myKey.getValue())%></p>
								</a>
								<a href="advertisement?id=<%=ad.getId() %>">
									<p>Seller Id : <%=sellerId%></p>
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
			<%
					adCount++;
					if(adCount>=6){
						break;	
					}
				}
				if(adCount>=6){
					break;	
				}
			}
			%>			
		</div><!--Ads by top rated sellers-->
	</div>		
	
	<!-- Top Rated ads -->	