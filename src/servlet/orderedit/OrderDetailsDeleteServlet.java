package servlet.orderedit;

import java.io.IOException;
import java.net.URLEncoder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
			String productStock = request.getParameter("product_stock");

//	        int productStock = Integer.parseInt(request.getParameter("product_stock"));

			String[] toppingIds = request.getParameterValues("topping_id[]");
			String[] toppingStocks = request.getParameterValues("topping_stock[]");
			
			
			System.out.println("screen: " + screen);
			System.out.println("order_id: " + orderId);
			System.out.println("product_id: " + productId);
			System.out.println("product_stock: " + productStock);
			
			if (toppingIds != null && toppingStocks != null) {
		        for (int i = 0; i < toppingIds.length; i++) {
		            String id = toppingIds[i];
		            int stock = Integer.parseInt(toppingStocks[i]);

		            // トッピング情報を処理（例: DB更新や在庫加算など）
		            System.out.println("ID: " + id + ", 在庫: " + stock);
		        }
		    }
			
//			 // OrderDeleteInfo に詰める
//	        OrderDeleteInfo orderInfo = new OrderDeleteInfo();
//	        orderInfo.setOrderId(orderId);
//	        orderInfo.setProductId(productId);
//	        orderInfo.setProductStock(productStock);
//
//	        // トッピングをリストにしてセット
//	        if (toppingIds != null && toppingStocks != null) {
//	        	List<OrderDeleteToppingInfo> toppingList = new ArrayList<>();
//
//	        	for (int i = 0; i < toppingIds.length; i++) {
//	        		OrderDeleteToppingInfo topping = new OrderDeleteToppingInfo();
//	        	    topping.setToppingId(Integer.parseInt(toppingIds[i]));
//	        	    topping.setToppingStock(Integer.parseInt(toppingStocks[i]));
//	        	    toppingList.add(topping);
//	        	}
//
//	        }
//
//	        // DAOを呼び出す
//	        OrderDetailsDAO dao = new OrderDetailsDAO();
//	        dao.deletOrderDetails(orderInfo);

			response.sendRedirect(request.getContextPath() + "/" + URLEncoder.encode(screen, "UTF-8"));
		} catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("/jsp/Error.jsp").forward(request, response);
        }
    }
}
