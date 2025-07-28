package servlet.toppinglist;

import java.io.IOException;
import java.util.List;

import dao.topping.ToppingListDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.topping.ToppingFormInfo;
import model.topping.ToppingInfo;

@WebServlet("/ToppingEdit")
public class ToppingEditServlet extends HttpServlet {

	// 全角数字チェック関数
	private boolean containsFullWidthDigit(String input) {
		return input != null && input.matches(".*[０-９]+.*");
	}

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

			String form = "ToppingEdit";
			String toppingIdStr = request.getParameter("topping_id");
			String toppingName = request.getParameter("topping_name");
			String toppingPriceStr = request.getParameter("topping_price");
			//エラーフラグ
			boolean hasError = false;

			// デバッグ用出力
			System.out.println("トッピングID: "+toppingIdStr);
			System.out.println("トッピング名: " + toppingName);
			System.out.println("金額: " + toppingPriceStr);

			//int型に変換
			int toppingId = 0;
			int toppingPrice = 0;

			toppingId = Integer.parseInt(toppingIdStr);

			//商品名が空の場合
			if (toppingName == null || toppingName.trim().isEmpty()) {
				request.setAttribute("toppingNameError", "※入力してください");
				hasError = true;
				//商品名が18文字を超えていた場合
			} else if (toppingName.length() > 18) {
				request.setAttribute("toppingNameError", "※18文字以内で入力してください");
				hasError = true;
			}

			// 金額が空の場合
			if (toppingPriceStr == null || toppingPriceStr.trim().isEmpty()) {
				request.setAttribute("toppingPriceError", "※入力してください");
				hasError = true;

				// 全角数字が含まれているかをチェック
			} else if (containsFullWidthDigit(toppingPriceStr)) {
				request.setAttribute("toppingPriceError", "※半角数字で入力してください");
				hasError = true;

				// 半角数字で構成されているかチェック（英字や記号混入を防止）
			} else if (!toppingPriceStr.matches("^[0-9]+$")) {
				request.setAttribute("toppingPriceError", "※半角数字で入力してください");
				hasError = true;

				// 正常な整数かつ5桁以内かのチェック
			} else {
				try {
					toppingPrice = Integer.parseInt(toppingPriceStr);
					if (toppingPrice >= 100000) {
						request.setAttribute("toppingPriceError", "※5桁以内で入力してください");
						hasError = true;
					}
				} catch (NumberFormatException e) {
					// 理論上この時点では起こらないが、保険として例外処理
					request.setAttribute("toppingPriceError", "※半角数字で入力してください");
					hasError = true;
				}
			}

			//エラーフラグがtrueの場合商品新規作成・編集画面に遷移
			if (hasError) {
				//入力された情報を格納する
				ToppingFormInfo toppingFormInfo = new ToppingFormInfo(toppingId, toppingName, toppingPrice);
				request.setAttribute("formButton", form);
				//商品情報をリクエスト属性にセット
				request.setAttribute("toppingFormInfo", toppingFormInfo);
				request.getRequestDispatcher("/jsp/ToppingForm.jsp").forward(request, response);
				return;
			}

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
