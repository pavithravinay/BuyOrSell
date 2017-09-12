package servlet;

import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bean.Advertisement;
import bean.Review;
import bean.User;
import dbutil.MongoDBUtilities;
import service.AdvertisementService;

public class AdvertisementServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int advertisementId = Integer.parseInt(request.getParameter("id"));

		if (request.getSession().getAttribute("user") == null) {
			response.sendRedirect("login?action=contactseller&id=" + advertisementId);
		} else {
			if (request.getParameter("action").equalsIgnoreCase("Contact Seller")) {
				response.sendRedirect("contact-seller?id=" + advertisementId);
			} else {
				MongoDBUtilities mongoDB = new MongoDBUtilities();
				AdvertisementService adService = new AdvertisementService();
				Advertisement ad = new Advertisement();
				Review objReview = new Review();

				ad = adService.getAdvertisement(advertisementId);
				float rating = Float.parseFloat(request.getParameter("rating"));
				
				String review = request.getParameter("review");
				
				int userId = ((User) request.getSession().getAttribute("user")).getId();
				int sellerId = ad.getPostedBy().getId();
				Date reviewDate = new Date();
								
				objReview.setRating(rating);
				objReview.setReview(review);
				objReview.setReviewDate(reviewDate);
				objReview.setUserId(userId);

				if (request.getParameter("action").equalsIgnoreCase("Submit Advertisement Review")) {
					objReview.setAdvertisementId(advertisementId);
					mongoDB.addAdvertisementReview(objReview);
					response.sendRedirect("advertisement?id="+advertisementId);
				} else if (request.getParameter("action").equalsIgnoreCase("Submit Seller Review")) {
					objReview.setSellerId(sellerId);
					mongoDB.addSellerReview(objReview);
					response.sendRedirect("advertisement?id="+advertisementId);
				}
			}
		}
	}

}