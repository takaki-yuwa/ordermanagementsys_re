package servlet.toppingform;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.topping.ToppingFormInfo;

@WebServlet("/ToppingEditForm")
public class ToppingEditFormServlet extends HttpServlet {
	//トッピング新規作成・編集画面へ遷移
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// キャッシュ制御ヘッダーを設定
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP/1.1
			response.setHeader("Pragma", "no-cache"); // HTTP/1.0
			response.setDateHeader("Expires", 0); // プロキシ／Expiresヘッダー用

			//情報を取得
			String form = request.getParameter("form");
			String toppingIdStr = request.getParameter("topping_id");
			String toppingName = request.getParameter("topping_name");
			String toppingPriceStr = request.getParameter("topping_price");

			//変換先変数
			int toppingId = 0;
			int toppingPrice = 0;

			//int型に変換
			if (toppingIdStr != null && toppingPriceStr != null) {
				toppingId = Integer.parseInt(toppingIdStr);
				toppingPrice = Integer.parseInt(toppingPriceStr);

			}

			//空の情報を格納
			ToppingFormInfo toppingFormInfo = new ToppingFormInfo(toppingId, toppingName, toppingPrice);

			//どのボタンから遷移してきたかの情報をリクエスト属性にセット
			request.setAttribute("formButton", form);

			//トッピング情報をリクエスト属性にセット
			request.setAttribute("toppingFormInfo", toppingFormInfo);

			//トッピング新規作成・編集画面に遷移
			request.getRequestDispatcher("/jsp/ToppingForm.jsp").forward(request, response);

		} catch (Exception e) {
			// ログに出力（開発時）
			e.printStackTrace();

			// エラー画面に戻す
			request.getRequestDispatcher("/jsp/Error.jsp").forward(request, response);
		}
	}
}
