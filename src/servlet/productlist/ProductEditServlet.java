package servlet.productlist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import constants.Constants;
import dao.product.ProductFormDAO;
import dao.product.ProductListDAO;
import dao.topping.ToppingListDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.product.ProductFormInfo;
import model.product.ProductInfo;
import model.producttopping.ProductToppingInfo;
import model.topping.ToppingInfo;

@WebServlet("/ProductEdit")
public class ProductEditServlet extends HttpServlet {

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

	//テーブルの内容を変更して商品一覧画面へ遷移
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// キャッシュ制御ヘッダーを設定
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP/1.1
			response.setHeader("Pragma", "no-cache"); // HTTP/1.0
			response.setDateHeader("Expires", 0); // プロキシ／Expiresヘッダー用

			String form = "ProductEdit";
			String productIdStr = request.getParameter("product_id");
			String productName = request.getParameter("product_name");
			String categoryName = request.getParameter("category_name");
			String productPriceStr = request.getParameter("product_price");
			String[] toppingIdStr = request.getParameterValues("topping_id");
			String selectedCategory = request.getParameter("selected_category");
			//エラーフラグ
			boolean hasError = false;

			// デバッグ用出力
			System.out.println("商品ID: " + productIdStr);
			System.out.println("商品名: " + productName);
			System.out.println("カテゴリー名: " + categoryName);
			System.out.println("金額: " + productPriceStr);
			if (toppingIdStr != null) {
				for (String id : toppingIdStr) {
					System.out.println("トッピングID: " + id);
				}
			}

			//int型に変換
			int productId = Integer.parseInt(productIdStr);
			int productPrice = 0;
			List<Integer> toppingIds = new ArrayList<>();
			if (toppingIdStr != null) {
				for (String id : toppingIdStr) {
					toppingIds.add(Integer.parseInt(id));

				}
			}

			//商品名が空の場合
			if (productName == null || productName.trim().isEmpty()) {
				request.setAttribute("productNameError", "※入力してください");
				hasError = true;
				//商品名が18文字を超えていた場合
			} else if (productName.length() > 18) {
				request.setAttribute("productNameError", "※18文字以内で入力してください");
				hasError = true;
			}

			//カテゴリーが空の場合
			if (categoryName == null || categoryName.trim().isEmpty()) {
				request.setAttribute("categoryNameError", "※選択してください");
				hasError = true;
			}

			//金額が空の場合
			if (productPriceStr == null || productPriceStr.trim().isEmpty()) {
				request.setAttribute("productPriceError", "※入力してください");
				hasError = true;
			} else {
				try {
					productPrice = Integer.parseInt(productPriceStr);
					//5桁より多い場合
					if (productPrice >= 100000) {
						request.setAttribute("productPriceError", "※5桁以内で入力してください");
						hasError = true;
					}

				} catch (NumberFormatException e) {
					request.setAttribute("productPriceError", "※半角数字で入力してください");
					hasError = true;
				}
			}

			//エラーフラグがtrueの場合ログイン画面に遷移
			if (hasError) {
				ProductFormInfo productFormInfo = new ProductFormInfo(productId, productName, categoryName, productPrice);
				ToppingListDAO toppingDao = new ToppingListDAO();
				ProductFormDAO productFormDao = new ProductFormDAO();
				//トッピング一覧の取得
				List<ToppingInfo> toppingInfo = toppingDao.selectToppingList();
				//商品トッピングの取得
				List<ProductToppingInfo> productToppingInfo = productFormDao.selectProductToppingList(productId);
				request.setAttribute("formButton", form);
				//商品情報をリクエスト属性にセット
				request.setAttribute("productFormInfo", productFormInfo);
				//カテゴリー情報をリクエスト属性にセット
				request.setAttribute("categoryList", Constants.CATEGORY_LIST);
				//トッピング情報をリクエスト属性にセット
				request.setAttribute("toppingInfo", toppingInfo);
				//商品トッピング情報を異rクエスト属性にセット
				request.setAttribute("productToppingInfo", productToppingInfo);
				request.getRequestDispatcher("/jsp/ProductForm.jsp").forward(request, response);
				return;
			}

			ProductListDAO dao = new ProductListDAO();
			dao.updateProductList(productId, productName, categoryName, productPrice, toppingIds);

			// POST内でカテゴリー保持（セッションに保存）
			if (selectedCategory != null && !selectedCategory.isEmpty()) {
				request.getSession().setAttribute("selectedCategory", selectedCategory);
			}

			// 更新後は一覧画面にリダイレクト（PRGパターン推奨）
			response.sendRedirect(request.getContextPath() + "/ProductEdit");

		} catch (Exception e) {
			// ログに出力（開発時）
			e.printStackTrace();

			// エラー画面に戻す
			request.getRequestDispatcher("/jsp/Error.jsp").forward(request, response);
		}
	}

}
