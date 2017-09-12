package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.AdvertisementService;

public class AutoCompleteServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StringBuilder builder = new StringBuilder();
		AdvertisementService service = new AdvertisementService();
		String prefix = request.getParameter("search");
		HashMap<Integer, String> products = service.getSuggestions(prefix);

		builder.append("[");
		for (Map.Entry<Integer, String> e : products.entrySet()) {
			builder.append("{\"id\"").append(":").append(e.getKey()).append(",");
			builder.append("\"value\"").append(":\"").append(e.getValue()).append("\"}");
			builder.append(",");
		}
		if(builder.charAt(builder.length() - 1) == ',')
			builder.deleteCharAt(builder.length() -1);
		builder.append("]");
		
		
		response.setContentType("application/json");
		response.getWriter().print(builder.toString());
	}

}
