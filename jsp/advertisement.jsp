<%@page import="dbutil.DataManager"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="dbutil.MongoDBUtilities"%>
<%@page import="service.AdvertisementService"%>
<%@page import="bean.Advertisement"%>
<%@page import="bean.Review"%>
<%@page import="bean.User"%>
<%@page import="bean.Address"%>
<%@ include file="htmlHead.jsp"%>
<%@ include file="pageHeader.jsp"%>
<%@ include file="leftSideBar.jsp"%>
<%@page import="service.UserService"%>
<%
	String advertisementId = request.getParameter("id");

	if (advertisementId == null || advertisementId.isEmpty()) {
		response.sendRedirect("advertisments");
		return;
	}

	AdvertisementService advertisementService = new AdvertisementService();
	Advertisement advertisement = advertisementService.getAdvertisement(Integer.parseInt(advertisementId));
	Address location = advertisement.getAddress();
	User postedBy = advertisement.getPostedBy();
	MongoDBUtilities mongoDB = new MongoDBUtilities();

	List<Review> sellerReview = mongoDB.getSellerReviews(advertisement.getPostedBy().getId());
	List<Review> advertisementReview = mongoDB.getAdvertisementReviews(advertisement.getId());

	int averageSellerRating = mongoDB.getAverageSellerRating(advertisement.getPostedBy().getId());
	int averageAdvertisementRating = mongoDB.getAverageAdvertismentRating(advertisement.getId());
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
							<img src="UploadServlet?adid=<%=advertisement.getId()%>" alt="" />
						</div>
					</div>
					<div class="col-sm-7">
						<div class="product-information">
							<!--/product-information-->
							<h2><%=advertisement.getTitle()%></h2>
							<p>
								<b>Product Rating: </b>
								<%
								if (averageAdvertisementRating > 0) {
									for(int i = 1; i<= averageAdvertisementRating; i++) {
										out.print("<img style='margin:-2px 2px 0px 0px;' src='images/star.png'/>");
									}
								} else {
									out.print("No reviews available.");
								}
								%>
								<!-- <img
									src="images/product-details/rating.png"
									style="vertical-align: inherit;" alt="productRating" /> -->
							</p>
							<p>
								<b>Sold By: </b>
								<%=String.format("%s %s", postedBy.getFirstName(), postedBy.getLastName())%>
							</p>
							<p>
								<b>Seller Rating: </b>
								<%
								if (averageSellerRating > 0) {
									for(int i = 1; i<= averageSellerRating; i++) {
										out.print("<img style='margin:-2px 2px 0px 0px;' src='images/star.png'/>");
									}
								} else {
									out.print("No reviews available.");
								}
								%>
								<!-- <img
									src="images/product-details/rating.png"
									style="vertical-align: inherit;" alt="sellerRating" /> -->
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
							<form method="post" action="AdvertisementServlet">
								<p>
									<input type="hidden" name="id"
										value="<%=advertisement.getId()%>" /> <input type="submit"
										class="btn btn-fefault cart" name="action"
										value="Contact Seller">
								</p>
							</form>
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
						<div class="tab-pane fade active in" id="details"
							style="padding: 10px;">
							<p><%=advertisement.getDescription()%></p>
							<p>
								<b>Item Location: </b>
								<%=String.format("%s, %s, %s %s", location.getAddress(), location.getCity(), location.getState(),
					location.getZipcode())%>
								<input id="address" type="hidden"
									value="<%=String.format(location.getAddress() + " " + location.getCity() + " " + location.getState() + " "
					+ location.getZipcode())%>">
							</p>
							<div id="map"></div>
							<form method="post" action="AdvertisementServlet">
								<p>
									<input type="hidden" name="id"
										value="<%=advertisement.getId()%>" /> <input type="submit"
										class="btn btn-fefault cart" name="action"
										value="Contact Seller">
								</p>
							</form>
						</div>

						<div class="tab-pane fade reviews" id="productreviews">
							<div class="col-sm-12">

								<%
									UserService userService = new UserService();
									SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm a");
									SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy");

									if (advertisementReview.size() == 0) {
								%>
								<p>No Review found.</p>
								<%
									} else {
										for (Review rev : advertisementReview) {
											User from = userService.getUser(rev.getUserId());
								%>
								<ul style="margin: 0 0 10px;">
									<li><a href="#"><i class="fa fa-clock-o"></i><%=timeFormatter.format(rev.getReviewDate())%></a></li>
									<li><a href="#"><i class="fa fa-calendar-o"></i><%=dateFormatter.format(rev.getReviewDate())%></a></li>
									<li><a href="#"><i class="fa fa-user"></i>From: <%=from.getFirstName() + " " + from.getLastName()%></a></li>
									<li>Rating:
									<%
									for(int i = 1; i <= (int)rev.getRating(); i++) {
										out.print("<img style='margin:-2px 2px 0px 0px;' src='images/star.png'/>");
									}
									%>
									</li>
								</ul>
								<p style="margin: 0 0 15px;">
									<%=rev.getReview()%></p>
								<%
									}
									}
								%>
								<p></p>
								<%
									if (request.getSession().getAttribute("user") == null) {
								%>
								<p>
									<b>Login to submit review</b>
								</p>
								<%
									} else {
								%>

								<p>
									<b>Write Your Review</b>
								</p>

								<form method="post" action="AdvertisementServlet">
									<textarea name="review"></textarea>
									<table>
										<tr>
											<td><b>Rating: </b></td>
											<td><input type="radio" name="rating" value="1"
												class="star"></td>
											<td>1</td>
											<td><input type="radio" name="rating" value="2"
												class="star"></td>
											<td>2</td>
											<td><input type="radio" name="rating" value="3"
												class="star"></td>
											<td>3</td>
											<td><input type="radio" name="rating" value="4"
												class="star"></td>
											<td>4</td>
											<td><input type="radio" name="rating" value="5"
												class="star"></td>
											<td>5</td>
										</tr>
									</table>
									<input type="hidden" name="id"
										value="<%=advertisement.getId()%>" /> <input type="submit"
										class="btn btn-default pull-right" name="action"
										value="Submit Advertisement Review">
								</form>
								<%
									}
								%>
							</div>
						</div>

						<div class="tab-pane fade reviews" id="sellerreviews">
							<div class="col-sm-12">
								<%
									if (sellerReview.size() == 0) {
								%>
								<p>No Review found.</p>
								<%
									} else {
										for (Review rev : sellerReview) {
											User from = userService.getUser(rev.getUserId());
								%>
								<ul style="margin: 0 0 10px;">
									<li><a href="#"><i class="fa fa-clock-o"></i><%=timeFormatter.format(rev.getReviewDate())%></a></li>
									<li><a href="#"><i class="fa fa-calendar-o"></i><%=dateFormatter.format(rev.getReviewDate())%></a></li>
									<li><a href="#"><i class="fa fa-user"></i>From: <%=from.getFirstName() + " " + from.getLastName()%></a></li>
									<li>Rating:
									<%
									for(int i = 1; i <= (int)rev.getRating(); i++) {
										out.print("<img style='margin:-2px 2px 0px 0px;' src='images/star.png'/>");
									}
									%>
									</li>
								</ul>
								<p style="margin: 0 0 15px;">
									<%=rev.getReview()%></p>
								<%
									}
									}
								%>
								<p></p>
								<%
									if (request.getSession().getAttribute("user") == null) {
								%>
								<p>
									<b>Login to submit review</b>
								</p>
								<%
									} else {
								%>

								<p>
									<b>Write Your Review</b>
								</p>

								<form method="post" action="AdvertisementServlet">
									<textarea name="review"></textarea>
									<table>
										<tr>
											<td><b>Rating: </b></td>
											<td><input type="radio" name="rating" value="1"
												class="star"></td>
											<td>1</td>
											<td><input type="radio" name="rating" value="2"
												class="star"></td>
											<td>2</td>
											<td><input type="radio" name="rating" value="3"
												class="star"></td>
											<td>3</td>
											<td><input type="radio" name="rating" value="4"
												class="star"></td>
											<td>4</td>
											<td><input type="radio" name="rating" value="5"
												class="star"></td>
											<td>5</td>
										</tr>
									</table>
									<input type="hidden" name="id"
										value="<%=advertisement.getId()%>" /> <input type="submit"
										class="btn btn-default pull-right" name="action"
										value="Submit Seller Review">
								</form>
								<%
									}
								%>
							</div>
						</div>

						<div class="tab-pane fade" id="twitter">
							<div class="col-sm-12">
								<p>Twitter feeds coming soon...</p>
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
		var map = new google.maps.Map(document.getElementById('map'), {
			zoom : 15,
			center : {
				lat : -34.397,
				lng : 150.644
			}
		});
		var geocoder = new google.maps.Geocoder();
		geocodeAddress(geocoder, map);
	}

	function geocodeAddress(geocoder, resultsMap) {

		var address = document.getElementById('address').value;
		geocoder.geocode({
			'address' : address
		}, function(results, status) {
			if (status === 'OK') {
				resultsMap.setCenter(results[0].geometry.location);
				var marker = new google.maps.Marker({
					map : resultsMap,
					position : results[0].geometry.location
				});
			} else {
				alert('Geocode was not successful for the following reason: '
						+ status);
			}
		});
	}
</script>
<script async defer
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBMK-J_IYru6jH3FD25dWCczn4klVin0iA&callback=initMap">
	
</script>