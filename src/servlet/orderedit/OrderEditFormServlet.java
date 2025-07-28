package servlet.orderedit;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.order.OrderEditDAO;
import model.order.OrderEditToppingInfo;

@WebServlet("/OrderEditForm")
public class OrderEditFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			
			String screen = request.getParameter("screen");
			String orderId = request.getParameter("order_id");
			String tableNumber = request.getParameter("table_number");
			String productId = request.getParameter("product_id");
			String productName = request.getParameter("product_name");
			String productQuantity = request.getParameter("product_quantity");
			String productStock = request.getParameter("product_stock");
			String[] toppingNames = request.getParameterValues("topping_name[]");
			String[] toppingQuantities = request.getParameterValues("topping_quantity[]");
			String[] toppingStocks = request.getParameterValues("topping_stock[]");
			
			System.out.println("screen: " + screen);
			System.out.println("order_id: " + orderId);
			System.out.println("table_number: " + tableNumber);
			System.out.println("product_id: " + productId);
			System.out.println("product_name: " + productName);
			System.out.println("product_quantity: " + productQuantity);
			System.out.println("product_stock: " + productStock);
			System.out.println("topping_names: " + Arrays.toString(toppingNames));
			System.out.println("topping_quantities: " + Arrays.toString(toppingQuantities));
			System.out.println("topping_stocks: " + Arrays.toString(toppingStocks));
			
			OrderEditDAO dao = new OrderEditDAO();
			int pId = Integer.parseInt(productId);

			// productId を使ってトッピング情報（IDと名前）を取得
			List<OrderEditToppingInfo> toppingList = dao.getToppingsByProductId(pId);

			// デバッグ出力
			for (OrderEditToppingInfo topping : toppingList) {
			    System.out.println("Topping ID: " + topping.getToppingId() +
			                       ", Name: " + topping.getToppingName() +
			                       ", Stock: " + topping.getToppingStock());
			}

			
			// JSPへ渡す
			request.setAttribute("screen", screen);
			request.setAttribute("orderId", orderId);
			request.setAttribute("tableNumber", tableNumber);
			request.setAttribute("productId", productId);
			request.setAttribute("productName", productName);
			request.setAttribute("productQuantity", productQuantity);
			request.setAttribute("productStock", productStock);
			request.setAttribute("toppingNames", toppingNames);
			request.setAttribute("toppingQuantities", toppingQuantities);
			request.setAttribute("toppingStocks", toppingStocks);
			request.setAttribute("toppingList", toppingList);

			request.getRequestDispatcher("/jsp/OrderEdit.jsp").forward(request, response);
		} catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("/jsp/Error.jsp").forward(request, response);
        }
    }
}
