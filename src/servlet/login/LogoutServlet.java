package servlet.login;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// セッションが存在しない場合はnullを返す
			HttpSession session = request.getSession(false);
			if (session != null) {
				// セッションを無効化
				session.invalidate();
			}
			request.getRequestDispatcher("/Login.jsp").forward(request, response);

		} catch (ServletException e) {
			System.err.println("Servlet例外が発生しました: " + e.getMessage());
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "サーブレット処理中にエラーが発生しました");
		} catch (IOException e) {
			System.err.println("I/O例外が発生しました: " + e.getMessage());
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "I/O処理中にエラーが発生しました");
		} catch (Exception e) {
			System.err.println("予期しない例外が発生しました: " + e.getMessage());
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "予期しないエラーが発生しました");
		}

	}
}