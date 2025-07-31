package servlet.orderedit;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.order.OrderDetailsDAO;
import model.orderditails.OrderUpdateInfo;
import model.orderditails.OrderUpdateToppingInfo;

@WebServlet("/OrderDetailsEdit")
public class OrderDetailsEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);

			// パラメータ取得
			String screen = request.getParameter("screen");
			int orderId = Integer.parseInt(request.getParameter("order_id"));
			String orderPriceStr = request.getParameter("order_price");
			int productId = Integer.parseInt(request.getParameter("product_id"));
			String productQuantityStr = request.getParameter("product_quantity");
			String productStockStr = request.getParameter("product_stock");

			//空文字・nullチェック付きで整数変換
			int orderPrice = 0;
			int productQuantity = 0;
			int productStock = 0;

			if (orderPriceStr != null && !orderPriceStr.isEmpty()) {
				orderPrice = Integer.parseInt(orderPriceStr);
			}

			if (productQuantityStr != null && !productQuantityStr.isEmpty()) {
				productQuantity = Integer.parseInt(productQuantityStr);
			}

			if (productStockStr != null && !productStockStr.isEmpty()) {
				productStock = Integer.parseInt(productStockStr);
			}

			String[] toppingIds = request.getParameterValues("topping_id[]");
			String[] toppingQuantities = request.getParameterValues("topping_quantity[]");
			String[] toppingStocks = request.getParameterValues("topping_stock[]");

			//確認用
			System.out.println("screen: " + screen);
			System.out.println("order_id: " + orderId);
			System.out.println("order_price: " + orderPrice);
			System.out.println("product_id: " + productId);
			System.out.println("product_quantity: " + productQuantity);
			System.out.println("product_stock: " + productStock);

			if (toppingIds != null && toppingQuantities != null && toppingStocks != null) {
				for (int i = 0; i < toppingIds.length; i++) {
					String id = toppingIds[i];
					int quantity = 0;
					int stock = 0;

					if (toppingQuantities[i] != null && !toppingQuantities[i].isEmpty()) {
						quantity = Integer.parseInt(toppingQuantities[i]);
					}

					if (toppingStocks[i] != null && !toppingStocks[i].isEmpty()) {
						stock = Integer.parseInt(toppingStocks[i]);
					}

					// トッピング情報を処理（例: DB更新や在庫加算など）
					System.out.println("ID: " + id + ", 個数: " + quantity + ", 在庫: " + stock);
				}
			}

			// OrderUpdateInfo に詰める
			OrderUpdateInfo orderInfo = new OrderUpdateInfo();
			orderInfo.setOrderId(orderId);
			orderInfo.setOrderPrice(orderPrice);
			orderInfo.setProductId(productId);
			orderInfo.setProductQuantity(productQuantity);
			orderInfo.setProductStock(productStock);

			// トッピングをリストにしてセット
			if (toppingIds != null && toppingQuantities != null && toppingStocks != null) {
				List<OrderUpdateToppingInfo> toppingList = new ArrayList<>();

				for (int i = 0; i < toppingIds.length; i++) {
					OrderUpdateToppingInfo topping = new OrderUpdateToppingInfo();
					topping.setToppingId(Integer.parseInt(toppingIds[i]));
					topping.setToppingQuantity(Integer.parseInt(toppingQuantities[i]));
					topping.setToppingStock(Integer.parseInt(toppingStocks[i]));
					toppingList.add(topping);
				}
				
				orderInfo.setOrderTopping(toppingList);

			}

			// DAOを呼び出す
			OrderDetailsDAO dao = new OrderDetailsDAO();
			dao.updateOrderDetails(orderInfo);

			// 画面遷移
			response.sendRedirect(request.getContextPath() + "/" + URLEncoder.encode(screen, "UTF-8"));

		} catch (Exception e) {
			e.printStackTrace();
			request.getRequestDispatcher("/jsp/Error.jsp").forward(request, response);
		}
	}

}
