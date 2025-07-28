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

@WebServlet("/ToppingEdit")
public class ToppingEditServlet extends HttpServlet {

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

	//テーブルに内容を追加してdoGetに遷移する
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// キャッシュ制御ヘッダーを設定
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP/1.1
			response.setHeader("Pragma", "no-cache"); // HTTP/1.0
			response.setDateHeader("Expires", 0); // プロキシ／Expiresヘッダー用

			String toppingIdStr = request.getParameter("topping_id");
			String toppingName = request.getParameter("topping_name");
			String toppingPriceStr = request.getParameter("topping_price");

			// デバッグ用出力
			System.out.println("トッピングID: " + toppingIdStr);
			System.out.println("トッピング名: " + toppingName);
			System.out.println("金額: " + toppingPriceStr);

			//int型に変換
			int toppingId = 0;
			int toppingPrice = 0;

			toppingId = Integer.parseInt(toppingIdStr);
			toppingPrice = Integer.parseInt(toppingPriceStr);

			ToppingListDAO dao = new ToppingListDAO();
			dao.updateToppingList(toppingId, toppingName, toppingPrice);

			// 更新後は一覧画面にリダイレクト（PRGパターン推奨）
			response.sendRedirect(request.getContextPath() + "/ToppingEdit");

		} catch (Exception e) {
			// ログに出力（開発時）
			e.printStackTrace();

			// エラー画面に戻す
			request.getRequestDispatcher("/jsp/Error.jsp").forward(request, response);
		}
	}
}
