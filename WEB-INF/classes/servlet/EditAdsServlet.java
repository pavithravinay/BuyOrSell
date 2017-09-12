package servlet;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import bean.Address;
import bean.Advertisement;
import bean.Category;
import bean.Result;
import bean.User;
import service.AdvertisementService;

public class EditAdsServlet extends HttpServlet {
	static String REMOVE_AD = "removeAd";
	static String UPDATE_AD = "updateAd";
	static String REMOVE_ADDRESS = "removeAddress";
	static String FETCH_SUBCATEGORY = "fetchSubCategory";
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if(action.equals(REMOVE_AD)) {
			String idStr = request.getParameter("id");
			
			if(idStr.isEmpty() ) {
				String msg = "Please provide all details!!!";
				
				Result r = new Result(false, msg);
				response.setContentType("application/json");
				String rStr = new Gson().toJson(r);
				response.getWriter().write(rStr);
			} else {
			
				int advertisementId = Integer.parseInt(idStr);
				AdvertisementService service = new AdvertisementService();
		
				//String[] postArray = request.getParameterValues("data");
				boolean isSuccess = service.deleteAdvertisement(advertisementId);
				String msg = "Ad removed successfully.";
				if(!isSuccess) {
					 msg = "Error occured";
					 response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/myaccount?ERROR-OCCURED."));

				}
				
				 response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/myaccount?Ad-deleted-successfully."));

			}
			
		} else if(action.equals(UPDATE_AD)){
			String idStr = request.getParameter("id");
			
			String title = request.getParameter("title");
			String description = request.getParameter("description");
			String itemCondition = request.getParameter("itemCondition");
			String subCategoryStr = request.getParameter("subCategory");
			String addStr =request.getParameter("addressId");
			String priceStr =request.getParameter("price");
			if(idStr.isEmpty() || title.isEmpty() || description.isEmpty() || itemCondition.isEmpty() || subCategoryStr.isEmpty() || addStr.isEmpty() || priceStr.isEmpty() ) {
				String msg = "Please provide all details!!!";
				
				Result r = new Result(false, msg);
				response.setContentType("application/json");
				String rStr = new Gson().toJson(r);
				response.getWriter().write(rStr);
			} else {

				int advertisementId = Integer.parseInt(idStr);
				AdvertisementService service = new AdvertisementService();
				// Remove ad
			
				Boolean isNegotiable = Boolean.parseBoolean(request.getParameter("isNegotiable"));
//	    		NEWCODE--ROHAN
				Boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));
				Boolean isSoldOut = Boolean.parseBoolean(request.getParameter("isSoldOut"));
//	    		NEWCODE--ROHAN--END
				Boolean displayContactNumber = Boolean.parseBoolean(request.getParameter("displayContactNumber"));
				Float price = Float.parseFloat(priceStr);
				String contactNumber = request.getParameter("contactNumber");
				Integer addressId = Integer.parseInt(addStr);
				
				Category cat = new Category();
				Integer subCategory = Integer.parseInt(subCategoryStr);
				cat.setId(subCategory);
				
				Address addr = new Address();
				addr.setId(addressId);
				
				HttpSession session = request.getSession();
				User user = (User) session.getAttribute("user");
				int userId = user.getId();
				User postedBy = new User();
				postedBy.setId(userId);
				
				Random random = new Random();
				
				Advertisement ad = new Advertisement(advertisementId, title, description, itemCondition, cat, "", isNegotiable, price, addr, contactNumber, displayContactNumber, true);
//	    		NEWCODE--ROHAN
				ad.setActive(isActive);
				ad.setSoldOut(isSoldOut);
//	    		NEWCODE--ROHAN--END
				
				/*Advertisement ad = new Advertisement(advertisementId, title, description, itemCondition, cat, "", 
				isNegotiable, price, addr, contactNumber, displayContactNumber, postedBy, new Date());*/
				
				boolean isSuccess = service.updateAdvertisement(ad);
				String msg = "Ad updated successfully.";
				if(!isSuccess) {
					 msg = "Error occured";
					 response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/myaccount?ERROR-OCCURED."));

				}
				
				 response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/myaccount?Ad-updated-successfully."));

			}
		
		} 
	}
	
}
