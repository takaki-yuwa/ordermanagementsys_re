package servlet.productform;

import java.io.IOException;
import java.util.List;

import constants.Constants;
import dao.topping.ToppingListDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.product.ProductFormInfo;
import model.topping.ToppingInfo;

@WebServlet("/ProductEditForm")
public class ProductEditFormServlet extends HttpServlet {
	//商品新規作成・編集画面へ遷移
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			// キャッシュ制御ヘッダーを設定
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP/1.1
			response.setHeader("Pragma", "no-cache"); // HTTP/1.0
			response.setDateHeader("Expires", 0); // プロキシ／Expiresヘッダー用

			//情報を取得
			String form = request.getParameter("form");
			String productIdStr = request.getParameter("product_id");
			String productName = request.getParameter("product_name");
			String productCategory = request.getParameter("category_name");
			String productPriceStr = request.getParameter("product_price");
			String productStockStr = request.getParameter("product_stock");
			String visibleFlagStr = request.getParameter("product_visible_flag");

			//変換先変数
			int productId = 0;
			int productPrice = 0;
			int productStock = 0;
			int visibleFlag = 0;

			//int型に変換
			if (productIdStr != null && productPriceStr != null && productStockStr != null && visibleFlagStr != null) {
				productId = Integer.parseInt(productIdStr);
				productPrice = Integer.parseInt(productPriceStr);
				productStock = Integer.parseInt(productStockStr);
				visibleFlag = Integer.parseInt(visibleFlagStr);

			}

			ProductFormInfo productFormInfo = new ProductFormInfo(productId, productName, productCategory, productPrice, productStock, visibleFlag);

			ToppingListDAO dao = new ToppingListDAO();

			//トッピング一覧の取得
			List<ToppingInfo> toppingInfo = dao.selectToppingList();

			//どのボタンから遷移してきたかの情報をリクエスト属性にセット
			request.setAttribute("formButton", form);

			//商品情報をリクエスト属性にセット
			request.setAttribute("productFormInfo", productFormInfo);

			//カテゴリー情報をリクエスト属性にセット
			request.setAttribute("categoryList", Constants.CATEGORY_LIST);

			//トッピング情報をリクエスト属性にセット
			request.setAttribute("toppingInfo", toppingInfo);

			//トッピング一覧画面に遷移
			request.getRequestDispatcher("/jsp/ProductForm.jsp").forward(request, response);

		} catch (Exception e) {
			// ログに出力（開発時）
			e.printStackTrace();

			// エラー画面に戻す
			request.getRequestDispatcher("/jsp/Error.jsp").forward(request, response);
		}
	}

}
