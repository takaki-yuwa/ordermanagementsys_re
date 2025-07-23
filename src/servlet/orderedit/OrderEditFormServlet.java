package servlet.orderedit;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/OrderEditForm")
public class OrderEditFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			request.getRequestDispatcher("/jsp/OrderEdit.jsp").forward(request, response);
		} catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("/jsp/Error.jsp").forward(request, response);
        }
    }
}
