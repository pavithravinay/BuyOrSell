package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import service.UserService;
import util.Errors;

public class LoginServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String action = request.getParameter("action");
		String id = request.getParameter("id");

		if (email != null && password != null) {
			UserService userService = new UserService();
			User user = userService.autheticate(email.toLowerCase(), password);
			if (user != null) {
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				if (action == null || id == null || !action.equalsIgnoreCase("contactseller")) {
					response.sendRedirect("home");
				} else {
					response.sendRedirect("contact-seller?id=" + id);
				}
			} else {
				response.sendRedirect("login?error=" + Errors.INCORRECT_CREDENTIALS.getCode());
			}
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action != null && action.equalsIgnoreCase("logout")) {
			HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect("login?action=logout");
		}
	}
}
