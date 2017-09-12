package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Address;
import bean.User;
import service.UserService;
import util.Errors;


public class SignUpServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		UserService service = new UserService();
		Random random = new Random();
		
		System.out.println("SIGNUP => " + action);
		if(action != null) {
			if(action.equalsIgnoreCase("register")) {
				String firstName = request.getParameter("firstname");
				String lastName = request.getParameter("lastname");
				String email = request.getParameter("email");
				String password = request.getParameter("password");
				//String confirmPassword = request.getParameter("confirmpassword");
				
				User user = new User(random.nextInt(Integer.MAX_VALUE), firstName, lastName, "-", new Date(), "-", email, "customer", null, password);
				boolean result = service.register(user);
				
				if(result) {
					session.setAttribute("user", user);
					response.sendRedirect("profile");
				}
			} else if(action.equalsIgnoreCase("updateProfile")) {
				User user = (User) session.getAttribute("user");
				System.out.println(user);
				
				Address userAddress = null;
				
				String gender = request.getParameter("gender");
				String dateOfBirth = request.getParameter("dateOfBirth");
				String contact = request.getParameter("contact");
				String addressLine1 = request.getParameter("address1");
				String addressLine2 = request.getParameter("address2");
				String city = request.getParameter("city");
				String state = request.getParameter("state");
				String zipcode = request.getParameter("zipcode");
				
				if(gender != null && !gender.trim().isEmpty()) {
					user.setGender(gender);
				}
				
				if(dateOfBirth != null && !dateOfBirth.trim().isEmpty()) {
					SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
					try {
						user.setDateOfBirth(formatter.parse(dateOfBirth));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				
				if(contact != null && !contact.trim().isEmpty()) {
					user.setContactNumber(contact);
				}
				
				if(addressLine1 != null && !addressLine1.trim().isEmpty()) {
					userAddress = new Address();
					userAddress.setId(random.nextInt(Integer.MAX_VALUE));
					userAddress.setUserId(user.getId());
					String address = addressLine1;
					
					if(addressLine2 != null) {
						address += addressLine2;
					}
					userAddress.setAddress(address);
					
					if(city != null && !city.trim().isEmpty()) {
						userAddress.setCity(city);
					}
					
					if(state != null && !state.trim().isEmpty()) {
						userAddress.setState(state);
					}
					
					if(zipcode != null && !zipcode.trim().isEmpty()) {
						userAddress.setZipcode(zipcode);
					}
					userAddress.setCountry("USA");
					user.setAddress(userAddress);
				}
				service.updateProfile(user);
				response.sendRedirect("profile?error=" + Errors.PROFILE_UPDATED.getCode());
			} else if(action.equalsIgnoreCase("updatePassword")) {
				String currentPassword = request.getParameter("currentpwd");
				String newPassword = request.getParameter("newpwd");
				
				User user = (User) session.getAttribute("user");
				if (user.getCredential().getPassword().equals(currentPassword)) {
					user.getCredential().setPassword(newPassword);
					service.updatePassword(user);
					response.sendRedirect("profile?error=" + Errors.PASSWORD_UPDATED.getCode());
				} else {
					response.sendRedirect("profile?error=" + Errors.INCORRECT_PASSWORD.getCode());
				}
			}
		}
	}

}
