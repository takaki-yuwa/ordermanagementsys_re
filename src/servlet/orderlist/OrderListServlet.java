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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// キャッシュ制御ヘッダーを設定
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);

			//情報を取得
			String orderIdStr = request.getParameter("order_id");
			String orderFlagStr = request.getParameter("order_flag");
			// セッションから選択カテゴリー取得（あればリクエスト属性にセット）
			String selectedCategory = (String) request.getSession().getAttribute("selectedCategory");
			if (selectedCategory != null) {
				request.setAttribute("selectedCategory", selectedCategory);
				request.getSession().removeAttribute("selectedCategory"); // 一度使ったら消す
			}

			OrderListDAO dao = new OrderListDAO();

			// 注文情報の更新処理がある場合
			if (orderIdStr != null && orderFlagStr != null) {
				int orderId = Integer.parseInt(orderIdStr);
				int orderFlag = Integer.parseInt(orderFlagStr);
				orderFlag = (orderFlag == 0) ? 1 : 0;

				dao.updateOrderList(orderId, orderFlag);
			}
			// POST内でカテゴリー保持（セッションに保存）
			if (selectedCategory != null && !selectedCategory.isEmpty()) {
				request.getSession().setAttribute("selectedCategory", selectedCategory);
			}

			// 注文リストを取得
			List<OrderInfo> orderList = dao.getAllOrderList();
			System.out.println(orderList); // デバッグ表示

			// 注文リストをセットしてJSPにフォワード
			request.setAttribute("orderinfo", orderList);
			request.setAttribute("categoryList", Constants.ORDER_CATEGORY_LIST);
			request.getRequestDispatcher("/jsp/OrderList.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.getRequestDispatcher("/jsp/Error.jsp").forward(request, response);
		}
	}
}
