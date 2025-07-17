package servlet.login;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//セッションが存在しない場合はnullを返す
		HttpSession session=request.getSession(false);
		if(session != null) {
			//セッションを無効化
			session.invalidate();
		}
		RequestDispatcher dispacher = request.getRequestDispatcher("Login.jsp");
		dispacher.forward(request, response);
	}
}
