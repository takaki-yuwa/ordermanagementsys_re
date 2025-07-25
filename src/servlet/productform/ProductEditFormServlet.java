package servlet.productform;

import java.io.IOException;
import java.util.List;

import constants.Constants;
import dao.product.ProductFormDAO;
import dao.topping.ToppingListDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.product.ProductFormInfo;
import model.producttopping.ProductToppingInfo;
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

			//変換先変数
			int productId = 0;
			int productPrice = 0;


			//int型に変換
			if (productIdStr != null && productPriceStr != null) {
				productId = Integer.parseInt(productIdStr);
				productPrice = Integer.parseInt(productPriceStr);

			}

			ProductFormInfo productFormInfo = new ProductFormInfo(productId, productName, productCategory, productPrice);
			ToppingListDAO toppingDao = new ToppingListDAO();
			ProductFormDAO productFormDao = new ProductFormDAO();

			//トッピング一覧の取得
			List<ToppingInfo> toppingInfo = toppingDao.selectToppingList();
			//商品トッピングの取得
			List<ProductToppingInfo> productToppingInfo = productFormDao.selectProductToppingList(productId);
			

			//どのボタンから遷移してきたかの情報をリクエスト属性にセット
			request.setAttribute("formButton", form);
			//商品情報をリクエスト属性にセット
			request.setAttribute("productFormInfo", productFormInfo);
			//カテゴリー情報をリクエスト属性にセット
			request.setAttribute("categoryList", Constants.CATEGORY_LIST);
			//トッピング情報をリクエスト属性にセット
			request.setAttribute("toppingInfo", toppingInfo);
			//商品トッピング情報を異rクエスト属性にセット
			request.setAttribute("productToppingInfo", productToppingInfo);

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
