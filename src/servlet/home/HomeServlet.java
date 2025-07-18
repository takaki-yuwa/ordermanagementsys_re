package servlet.home;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			request.getRequestDispatcher("/jsp/Home.jsp").forward(request, response);
		} catch (ServletException e) {
			System.err.println("サーブレットエラーが発生しました: " + e.getMessage());
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "サーバー内部エラー（サーブレット）");
		} catch (IOException e) {
			System.err.println("I/Oエラーが発生しました: " + e.getMessage());
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "サーバー内部エラー（I/O）");
		} catch (Exception e) {
			System.err.println("予期しないエラーが発生しました: " + e.getMessage());
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "予期しないエラーが発生しました");
		}
	}
}
