package servlet.login;

import java.io.IOException;

import dao.login.LoginDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.login.LoginInfo;
import util.PasswordUtil;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("userid");
		String password = request.getParameter("password");
		//エラーフラグ
		boolean hasError = false;

		// デバッグ用出力
		System.out.println("入力されたユーザーID: " + userId);
		System.out.println("入力されたパスワード: " + password);

		//IDが空の場合
		if (userId == null || userId.trim().isEmpty()) {
			request.setAttribute("useridError", "IDを入力してください");
			hasError = true;

		}
		//パスワードが空の場合
		if (password == null || password.trim().isEmpty()) {
			request.setAttribute("passwordError", "パスワードを入力してください");
			hasError = true;
		}

		//エラーフラグがtrueの場合ログイン画面に遷移
		if (hasError) {
			RequestDispatcher dispacher = request.getRequestDispatcher("Login.jsp");
			dispacher.forward(request, response);
			return;
		}

		// 認証処理
		LoginDAO loginDao = new LoginDAO();
		LoginInfo info = loginDao.getLogin(userId);

		// ユーザーが見つからない場合
		if (info == null) {
			request.setAttribute("errorMessage", "IDが存在しません。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
			dispatcher.forward(request, response);
			return; // 処理をここで終了
		}

		// デバッグ出力（DBから取得したハッシュ値を確認）
		System.out.println("DBのハッシュパスワード: " + info.getPassword());

		//PasswordUtil.verify()でハッシュ化
		if (PasswordUtil.verify(password, info.getPassword())) {
			// 認証成功
			HttpSession session = request.getSession();
			session.setAttribute("userId", userId);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/Home");
			dispatcher.forward(request, response);
		} else {
			// 認証失敗
			request.setAttribute("errorMessage", "IDまたはパスワードが間違っています。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
			dispatcher.forward(request, response);
		}

	}
}
