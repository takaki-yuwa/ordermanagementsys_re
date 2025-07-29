package servlet.orderedit;

import java.io.IOException;
import java.net.URLEncoder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
	        String orderPrice = request.getParameter("order_price");
			int productId = Integer.parseInt(request.getParameter("product_id"));
			String productQuantity = request.getParameter("product_quantity");
			String productStock = request.getParameter("product_stock");

//	        int orderPrice = Integer.parseInt(request.getParameter("order_price"));
//	        int productQuantity = Integer.parseInt(request.getParameter("product_quantity"));
//	        int productStock = Integer.parseInt(request.getParameter("product_stock"));

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

	        if (toppingIds != null && toppingStocks != null) {
	            for (int i = 0; i < toppingIds.length; i++) {
	                String id = toppingIds[i];
	                int quantity = Integer.parseInt(toppingQuantities[i]);
	                int stock = Integer.parseInt(toppingStocks[i]);

	                // トッピング情報を処理（例: DB更新や在庫加算など）
	                System.out.println("ID: " + id + ", 個数: " + quantity + ", 在庫: " + stock);
	            }
	        }

//	        // OrderUpdateInfo に詰める
//	        OrderUpdateInfo orderInfo = new OrderUpdateInfo();
//	        orderInfo.setOrderId(orderId);
//	        orderInfo.setOrderPrice(orderPrice);
//	        orderInfo.setProductId(productId);
//	        orderInfo.setProductQuantity(productQuantity);
//	        orderInfo.setProductStock(productStock);
//
//	        // トッピングをリストにしてセット
//	        if (toppingIds != null && toppingQuantities != null && toppingStocks != null) {
//	        	List<OrderUpdateToppingInfo> toppingList = new ArrayList<>();
//
//	        	for (int i = 0; i < toppingIds.length; i++) {
//	        		OrderUpdateToppingInfo topping = new OrderUpdateToppingInfo();
//	        	    topping.setToppingId(Integer.parseInt(toppingIds[i]));
//	        	    topping.setToppingQuantity(Integer.parseInt(toppingQuantities[i]));
//	        	    topping.setToppingStock(Integer.parseInt(toppingStocks[i]));
//	        	    toppingList.add(topping);
//	        	}
//
//	        }
//
//	        // DAOを呼び出す
//	        OrderDetailsDAO dao = new OrderDetailsDAO();
//	        dao.updateOrderDetails(orderInfo);

	        // 画面遷移
	        response.sendRedirect(request.getContextPath() + "/" + URLEncoder.encode(screen, "UTF-8"));

	    } catch (Exception e) {
	        e.printStackTrace();
	        request.getRequestDispatcher("/jsp/Error.jsp").forward(request, response);
	    }
	}

}
