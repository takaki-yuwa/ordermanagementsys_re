package servlet.productlist;

import java.io.IOException;
import java.util.List;

import dao.product.ProductListDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.product.ProductInfo;

@WebServlet("/ProductList")
public class ProductListServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// キャッシュ制御ヘッダーを設定
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP/1.1
			response.setHeader("Pragma", "no-cache"); // HTTP/1.0
			response.setDateHeader("Expires", 0); // プロキシ／Expiresヘッダー用

			ProductListDAO dao = new ProductListDAO();

			//商品一覧の取得
			List<ProductInfo> productInfo = dao.selectProductList();

			//商品リストをリクエスト属性にセット
			request.setAttribute("productInfo", productInfo);

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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// キャッシュ制御ヘッダーを設定
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP/1.1
			response.setHeader("Pragma", "no-cache"); // HTTP/1.0
			response.setDateHeader("Expires", 0); // プロキシ／Expiresヘッダー用

			//情報を取得
			String productIdStr = request.getParameter("product_id");
			String visibleFlagStr = request.getParameter("product_visible_flag");

			

			//表示フラグ更新
			if (productIdStr != null && visibleFlagStr != null) {
				int productId = Integer.parseInt(productIdStr);
				int visibleFlag = Integer.parseInt(visibleFlagStr);
				
				visibleFlag=(visibleFlag == 0) ? 1 :0;
				
				
				ProductListDAO dao = new ProductListDAO();
				dao.updateProductList(productId,visibleFlag);
			}

			// 更新後は一覧画面にリダイレクト（PRGパターン推奨）
            response.sendRedirect(request.getContextPath() + "/ProductList");


			//例外処理
		} catch (Exception e) {
			// ログに出力（開発時）
			e.printStackTrace();

			// エラー画面に戻す
			request.getRequestDispatcher("/jsp/Error.jsp").forward(request, response);
		}
	}
}
