package servlet.productlist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import constants.Constants;
import dao.product.ProductListDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.product.ProductInfo;

@WebServlet("/ProductCreate")
public class ProductCreateServlet extends HttpServlet {

	//商品一覧画面へ遷移
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// キャッシュ制御ヘッダーを設定
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP/1.1
			response.setHeader("Pragma", "no-cache"); // HTTP/1.0
			response.setDateHeader("Expires", 0); // プロキシ／Expiresヘッダー用

			// セッションから選択カテゴリー取得（あればリクエスト属性にセット）
			String selectedCategory = (String) request.getSession().getAttribute("selectedCategory");
			if (selectedCategory != null) {
				request.setAttribute("selectedCategory", selectedCategory);
				request.getSession().removeAttribute("selectedCategory"); // 一度使ったら消す
			}

			ProductListDAO dao = new ProductListDAO();

			//商品一覧の取得
			List<ProductInfo> productInfo = dao.selectProductList();

			//商品リストをリクエスト属性にセット
			request.setAttribute("productInfo", productInfo);
			request.setAttribute("categoryList", Constants.PRODUCT_CATEGORY_LIST);

			//商品一覧画面に遷移
			request.getRequestDispatcher("/jsp/ProductList.jsp").forward(request, response);

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

			String productName = request.getParameter("product_name");
			String categoryName = request.getParameter("category_name");
			String productPriceStr = request.getParameter("product_price");
			String[] toppingIdStr = request.getParameterValues("topping_id");
			String selectedCategory = request.getParameter("selected_category");

			// デバッグ用出力
			System.out.println("商品名: " + productName);
			System.out.println("カテゴリー名: " + categoryName);
			System.out.println("金額: " + productPriceStr);
			if (toppingIdStr != null) {
				for (String id : toppingIdStr) {
					System.out.println("トッピングID: " + id);
				}
			}

			//int型に変換
			int productPrice = 0;
			productPrice = Integer.parseInt(productPriceStr);
			List<Integer> toppingIds = new ArrayList<>();
			if (toppingIdStr != null) {
				for (String id : toppingIdStr) {
					toppingIds.add(Integer.parseInt(id));

				}
			}

			ProductListDAO dao = new ProductListDAO();
			dao.insertProductList(productName, categoryName, productPrice, toppingIds);

			// POST内でカテゴリー保持（セッションに保存）
			if (selectedCategory != null && !selectedCategory.isEmpty()) {
				request.getSession().setAttribute("selectedCategory", selectedCategory);
			}

			// 更新後は一覧画面にリダイレクト（PRGパターン推奨）
			response.sendRedirect(request.getContextPath() + "/ProductCreate");

		} catch (Exception e) {
			// ログに出力（開発時）
			e.printStackTrace();

			// エラー画面に戻す
			request.getRequestDispatcher("/jsp/Error.jsp").forward(request, response);
		}
	}
}
