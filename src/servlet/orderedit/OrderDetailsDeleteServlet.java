package servlet.orderedit;

import java.io.IOException;
import java.net.URLEncoder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/OrderDetailsDelete")
public class OrderDetailsDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			
			String screen = request.getParameter("screen");
			String orderId = request.getParameter("order_id");
			
			System.out.println("screen: " + screen);
			System.out.println("order_id: " + orderId);
			

			response.sendRedirect(request.getContextPath() + "/" + URLEncoder.encode(screen, "UTF-8"));
		} catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("/jsp/Error.jsp").forward(request, response);
        }
    }
}
