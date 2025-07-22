package servlet.toppinglist;

import java.io.IOException;
import java.util.List;

import dao.topping.ToppingListDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.topping.ToppingInfo;

@WebServlet("/ToppingList")
public class ToppingListServlet extends HttpServlet {
	//トッピング一覧画面へ遷移
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// キャッシュ制御ヘッダーを設定
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP/1.1
			response.setHeader("Pragma", "no-cache"); // HTTP/1.0
			response.setDateHeader("Expires", 0); // プロキシ／Expiresヘッダー用

			ToppingListDAO dao = new ToppingListDAO();

			//トッピング一覧の取得
			List<ToppingInfo> toppingInfo = dao.selectToppingList();

			//トッピングリストをリクエスト属性にセット
			request.setAttribute("toppingInfo", toppingInfo);

			//トッピング一覧画面に遷移
			request.getRequestDispatcher("/jsp/ToppingList.jsp").forward(request, response);

			//例外処理
		} catch (Exception e) {
			// ログに出力（開発時）
			e.printStackTrace();

			// エラー画面に戻す
			request.getRequestDispatcher("/jsp/Error.jsp").forward(request, response);
		}
	}

	//表示フラグ更新
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// キャッシュ制御ヘッダーを設定
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP/1.1
			response.setHeader("Pragma", "no-cache"); // HTTP/1.0
			response.setDateHeader("Expires", 0); // プロキシ／Expiresヘッダー用

			//情報を取得
			String toppingIdStr = request.getParameter("topping_id");
			String visibleFlagStr = request.getParameter("topping_visible_flag");

			//表示フラグ更新
			if (toppingIdStr != null && visibleFlagStr != null) {
				int toppingId = Integer.parseInt(toppingIdStr);
				int visibleFlag = Integer.parseInt(visibleFlagStr);

				visibleFlag = (visibleFlag == 0) ? 1 : 0;

				ToppingListDAO dao = new ToppingListDAO();
				dao.updateToppingList(toppingId, visibleFlag);
			}

			// 更新後は一覧画面にリダイレクト（PRGパターン推奨）
			response.sendRedirect(request.getContextPath() + "/ToppingList");

			//例外処理
		} catch (Exception e) {
			// ログに出力（開発時）
			e.printStackTrace();

			// エラー画面に戻す
			request.getRequestDispatcher("/jsp/Error.jsp").forward(request, response);
		}
	}
}
