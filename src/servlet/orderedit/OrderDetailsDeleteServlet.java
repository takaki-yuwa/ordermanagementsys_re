package servlet.orderedit;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import dao.order.OrderDetailsDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.orderditails.OrderDeleteInfo;
import model.orderditails.OrderDeleteToppingInfo;

@WebServlet("/OrderDetailsDelete")
public class OrderDetailsDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);

			String screen = request.getParameter("screen");
			int orderId = Integer.parseInt(request.getParameter("order_id"));
			int productId = Integer.parseInt(request.getParameter("product_id"));
			int productQuantity = Integer.parseInt(request.getParameter("product_quantity"));

			//	        int productStock = Integer.parseInt(request.getParameter("product_stock"));

			String[] toppingIds = request.getParameterValues("topping_id[]");
			String[] toppingQuantities = request.getParameterValues("topping_quantity[]");

			System.out.println("screen: " + screen);
			System.out.println("order_id: " + orderId);
			System.out.println("product_id: " + productId);
			System.out.println("product_quantity: " + productQuantity);

			if (toppingIds != null && toppingQuantities != null) {
				for (int i = 0; i < toppingIds.length; i++) {
					String id = toppingIds[i];
					int stock = Integer.parseInt(toppingQuantities[i]);

					// トッピング情報を処理（例: DB更新や在庫加算など）
					System.out.println("ID: " + id + ", 在庫: " + stock);
				}
			}

			// OrderDeleteInfo に詰める
			OrderDeleteInfo orderInfo = new OrderDeleteInfo();
			orderInfo.setOrderId(orderId);
			orderInfo.setProductId(productId);
			orderInfo.setProductQuantity(productQuantity);

			// トッピングをリストにしてセット
			if (toppingIds != null && toppingQuantities != null) {
				List<OrderDeleteToppingInfo> toppingList = new ArrayList<>();

				for (int i = 0; i < toppingIds.length; i++) {
					OrderDeleteToppingInfo topping = new OrderDeleteToppingInfo();
					topping.setToppingId(Integer.parseInt(toppingIds[i]));
					topping.setToppingQuantity(Integer.parseInt(toppingQuantities[i]));
					toppingList.add(topping);
				}

				orderInfo.setOrderTopping(toppingList);
			}

			// DAOを呼び出す
			OrderDetailsDAO dao = new OrderDetailsDAO();
			dao.deletOrderDetails(orderInfo);

			response.sendRedirect(request.getContextPath() + "/" + URLEncoder.encode(screen, "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
			request.getRequestDispatcher("/jsp/Error.jsp").forward(request, response);
		}
	}
}
