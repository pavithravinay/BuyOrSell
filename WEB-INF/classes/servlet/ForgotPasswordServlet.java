package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.UserService;

public class ForgotPasswordServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		UserService service = new UserService();
		
		if (email != null && !email.trim().isEmpty()) {
			boolean result = service.forgotPassword(email);
			if (result) {
				response.sendRedirect("forgot-password?error=0");
			} else {
				response.sendRedirect("forgot-password?error=0");
			}
		}
	}
}
