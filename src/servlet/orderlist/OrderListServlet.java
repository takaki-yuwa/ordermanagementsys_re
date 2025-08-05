package servlet.orderlist;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import constants.Constants;
import dao.order.OrderListDAO;
import model.order.OrderInfo;

@WebServlet("/OrderList")
public class OrderListServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// キャッシュ制御ヘッダーを設定
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);

			//情報を取得
			String selectedCategory = (String) request.getSession().getAttribute("selected_category");
			// 選択カテゴリーをセッションにも保持
			if (selectedCategory != null && !selectedCategory.isEmpty()) {
				request.setAttribute("selected_category", selectedCategory);
				request.getSession().removeAttribute("selected_category"); // 一度使ったら消す
			}
			OrderListDAO dao = new OrderListDAO();

			// 注文リストを取得
			List<OrderInfo> orderList = dao.getAllOrderList();
			System.out.println(orderList); // デバッグ表示

			request.setAttribute("orderinfo", orderList);
			request.setAttribute("categoryList", Constants.ORDER_CATEGORY_LIST);

			request.getRequestDispatcher("/jsp/OrderList.jsp").forward(request, response);

			//例外処理
		} catch (Exception e) {
			e.printStackTrace();
			request.getRequestDispatcher("/jsp/Error.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//情報を取得
		try {
			// キャッシュ制御ヘッダーを設定
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);

			String orderIdStr = request.getParameter("order_id");
			String orderFlagStr = request.getParameter("order_flag");
			String selectedCategory = request.getParameter("selected_category");

			// 注文情報の更新処理がある場合
			if (orderIdStr != null && orderFlagStr != null) {
				int orderId = Integer.parseInt(orderIdStr);

				OrderListDAO dao = new OrderListDAO();
				dao.updateOrderFlag(orderId);
			}

			// POST内でカテゴリー保持（セッションに保存）
			if (selectedCategory != null && !selectedCategory.isEmpty()) {
				request.getSession().setAttribute("selected_category", selectedCategory);
			}

			// 更新後は注文管理画面にリダイレクト（PRGパターン推奨）
			response.sendRedirect(request.getContextPath() + "/OrderList");

		} catch (Exception e) {
			e.printStackTrace();
			request.getRequestDispatcher("/jsp/Error.jsp").forward(request, response);
		}
	}
}
