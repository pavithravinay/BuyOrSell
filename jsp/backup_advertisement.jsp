<%@page import="util.TwitterConnect"%>
<%@page import="dbutil.DataManager"%>
<%@page import="service.AdvertisementService"%>
<%@page import="bean.Advertisement"%>
<%@page import="bean.User"%>
<%@page import="bean.Address"%>
<%@ include file="htmlHead.jsp"%>
<%@ include file="pageHeader.jsp"%>
<%@ include file="leftSideBar.jsp"%>
<%
	String advertisementId = request.getParameter("id");
	
	if (advertisementId == null || advertisementId.isEmpty()) {
		response.sendRedirect("advertisements");
		return;
	}
	
	AdvertisementService advertisementService = new AdvertisementService();
	Advertisement advertisement = advertisementService.getAdvertisement(Integer.parseInt(advertisementId));
	Address location = advertisement.getAddress();
	User postedBy = advertisement.getPostedBy();
	
	List<String> tweets =  TwitterConnect.getTweets(Integer.parseInt(advertisementId));
	//get advertisement details
	//display advertisement details
	//fetch reviews
	//Call servlet for seller contact details
	//Call servlet for review submit
%>
<style>
#map {
	height: 400px;
	width: 100%;
	margin-bottom: 10px;
}

.tab-pane {
	padding: 10px;
}
</style>
<section>
	<div class="container">
		<div class="row">
			<div class="col-sm-9 padding-right">
				<div class="product-details">
					<!--product-details-->
					<div class="col-sm-5">
						<div class="view-product">
							<img src="UploadServlet?adid=<%=advertisement.getId()%>" alt="advertisement" />
						</div>
					</div>
					<div class="col-sm-7">
						<div class="product-information">
							<!--/product-information-->
							<h2><%=advertisement.getTitle()%></h2>
							<p>
								<b>Product Rating: </b><img
									src="images/product-details/rating.png" style="vertical-align: inherit;" alt="productRating" />
							</p>
							<p>
								<b>Sold By: </b>
								<%=String.format("%s %s", postedBy.getFirstName(), postedBy.getLastName())%>
							</p>
							<p>
								<b>Seller Rating: </b><img
									src="images/product-details/rating.png" style="vertical-align: inherit;" alt="sellerRating" />
							</p>
							<p>
								<b>Price: $</b><%=advertisement.getPrice()%>
							</p>
							<p>
								<b>Negotiable: </b>
								<%=advertisement.isNegotiable() ? "Yes" : "No"%></p>
							<p>
								<b>Condition:</b>
								<%=advertisement.getItemCondition().toUpperCase()%>
							</p>
							<p>
								<b>Category:</b>
								<%=advertisement.getCategory().getName()%>
							</p>
							<p><button type="button" class="btn btn-fefault cart" style="margin-left:0px;">Contact Seller</button></p>
						</div>
						<!--/product-information-->
					</div>
				</div>
				<!--/product-details-->

				<div class="category-tab shop-details-tab">
					<!--category-tab-->
					<div class="col-sm-12">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#details" data-toggle="tab">Details</a></li>
							<li><a href="#productreviews" data-toggle="tab">Product
									Reviews</a></li>
							<li><a href="#sellerreviews" data-toggle="tab">Seller
									Reviews</a></li>
							<li><a href="#twitter" data-toggle="tab">Twitter</a></li>
						</ul>
					</div>
					<div class="tab-content">
						<div class="tab-pane fade active in" id="details" style="padding:10px;">
							<p><%=advertisement.getDescription()%></p>
							<p>
								<b>Item Location: </b>
								<%=String.format("%s, %s, %s %s", location.getAddress(), location.getCity(), location.getState(), location.getZipcode())%>
							</p>
							<div id="map"></div>
							<p><button type="button" class="btn btn-fefault cart" style="margin-left:0px;">Contact Seller</button></p>
						</div>

						<div class="tab-pane fade" id="productreviews">
							<div class="col-sm-12">

								<p></p>
								<p>
									<b>Write Your Review</b>
								</p>

								<form action="#">
									<textarea name=""></textarea>
									<b>Rating: </b> <img src="images/product-details/rating.png"
										alt="" />
									<button type="button" class="btn btn-default pull-right">
										Submit</button>
								</form>
							</div>
						</div>

						<div class="tab-pane fade" id="sellerreviews">
							<div class="col-sm-12">
								<p></p>
								<p>
									<b>Write Your Review</b>
								</p>

								<form action="#">
									<textarea name=""></textarea>
									<b>Rating: </b> <img src="images/product-details/rating.png"
										alt="" />
									<button type="button" class="btn btn-default pull-right">
										Submit</button>
								</form>
							</div>
						</div>

						<div class="tab-pane fade" id="twitter">
							<div class="col-sm-12">
								<% if (tweets.isEmpty()) { %>
								<h4>Tweets for this product is not available</h4>
								<% } else {
										for (String x : tweets) {
								%>
								<p><%=x%></p>
								<% } } %>
							</div>
						</div>

					</div>
					<!--/category-tab-->
				</div>
			</div>
		</div>
	</div>
</section>
<%@ include file="pageFooter.jsp"%>
<script>
	function initMap() {
		var uluru = {
			lat : 41.840402,
			lng : -87.623315
		};
		var map = new google.maps.Map(document.getElementById('map'), {
			zoom : 15,
			center : uluru
		});
		var marker = new google.maps.Marker({
			position : uluru,
			map : map
		});
	}
</script>
<script async defer
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDFzDKVM9J06z1PE3mqRb55VpIJfd926v4&callback=initMap">
</script>