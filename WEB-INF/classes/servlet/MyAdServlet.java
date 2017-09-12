package servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;
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
import service.UserService;

public class MyAdServlet extends HttpServlet {
	static String ADD_NEW_ADDRESS = "addAddress";
	static String ADD_NEW_AD = "addAd";
	static String REMOVE_ADDRESS = "removeAddress";
	static String FETCH_SUBCATEGORY = "fetchSubCategory";
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if(action.equals(ADD_NEW_ADDRESS)) {
			String address = request.getParameter("address");
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			String country = request.getParameter("country");
			String zipcode = request.getParameter("zipcode");
			
			if(address.isEmpty() || city.isEmpty() || state.isEmpty() || country.isEmpty() || zipcode.isEmpty() ) {
				String msg = "Please provide all details!!!";
				
				Result r = new Result(false, msg);
				response.setContentType("application/json");
				String rStr = new Gson().toJson(r);
				response.getWriter().write(rStr);
			} else {
			
				UserService service = new UserService();
				
				HttpSession session = request.getSession();
				User user = (User) session.getAttribute("user");
				Random random = new Random();
				
				
				Address addressObj = new Address(random.nextInt(Integer.MAX_VALUE), user.getId(), address, city, state, country, zipcode); 
		
				//String[] postArray = request.getParameterValues("data");
				boolean isSuccess = service.addAddress(addressObj);
				String msg = "Address updated added successfully.";
				if(!isSuccess) {
					 msg = "Error occured";
				}
				Result r = new Result(isSuccess, msg);
				r.setObj(addressObj);
				response.setContentType("application/json");
				String rStr = new Gson().toJson(r);
				response.getWriter().write(rStr);
			}
			
		} else if(action.equals(REMOVE_ADDRESS)){
			Integer addressId = Integer.parseInt(request.getParameter("addressId"));
			UserService service = new UserService();
			boolean isSuccess = false;
			String msg;
			if(service.doesAddressAlreadyBind(addressId)) {
				 msg = "This address already attached with other advertisements. Please remove these advertisements before removing this address.";
			} else {
			   isSuccess = service.removeAddress(addressId);
			   msg = "Address removed successfully.";
				if(!isSuccess) {
					 msg = "Error occured";
				}
			}
			
			Result r = new Result(isSuccess, msg);
			response.setContentType("application/json");
			String rStr = new Gson().toJson(r);
			response.getWriter().write(rStr);
		} else if(action.equals(ADD_NEW_AD)){
			
			String title = request.getParameter("title");
			String description = request.getParameter("description");
			String itemCondition = request.getParameter("itemCondition");
			String subCategoryStr = request.getParameter("subCategory");
			String addStr =request.getParameter("addressId");
			String priceStr =request.getParameter("price");
			System.out.println(title + " " + description+ "  " +itemCondition+"  "+subCategoryStr+"   "+addStr+"  "+priceStr);
			if(title.isEmpty() || description.isEmpty() || itemCondition.isEmpty() || subCategoryStr.isEmpty() || addStr.isEmpty() || priceStr.isEmpty() ) {
				String msg = "Please provide all details!!!";
				
				Result r = new Result(false, msg);
				response.setContentType("application/json");
				String rStr = new Gson().toJson(r);
				response.getWriter().write(rStr);
			} else {
			
				Boolean isNegotiable = Boolean.parseBoolean(request.getParameter("isNegotiable"));
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
				// int userId = user.getId();
				User postedBy = new User();
				postedBy.setId(user.getId());
				
				Random random = new Random();
				
				AdvertisementService service = new AdvertisementService();
				int adId = random.nextInt(Integer.MAX_VALUE);
				
				Advertisement ad = new Advertisement(adId, title, description, itemCondition, cat, "", isNegotiable, price, addr, 
						contactNumber, displayContactNumber, postedBy, true);
				/*
				 * 
						Advertisement ad = new Advertisement(adId, title, description, itemCondition, cat, "", 
						isNegotiable, price, addr, contactNumber, displayContactNumber, postedBy, new Date());*/
				
				boolean isSuccess = service.postAdvertisement(ad);
				String msg = "Address removed successfully.";
				if(!isSuccess) {
					 msg = "Error occured";
				}
				Result r = new Result(isSuccess, msg);
				r.setObj(adId);
				response.setContentType("application/json");
				String rStr = new Gson().toJson(r);
				response.getWriter().write(rStr);
			}
		} 
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if(action.equals(FETCH_SUBCATEGORY)){
			Integer categoryPID = Integer.parseInt(request.getParameter("categoryPID"));
			
			AdvertisementService service = new AdvertisementService();
			
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			Random random = new Random();
			
			//String[] postArray = request.getParameterValues("data");
			List<Category> subcategories = service.getSubCategories(categoryPID);
			String msg = "success.";
			
			Result r = new Result(true, msg);
			r.setObj(subcategories);
			response.setContentType("application/json");
			String rStr = new Gson().toJson(r);
			response.getWriter().write(rStr);
			
		}
	}
}
