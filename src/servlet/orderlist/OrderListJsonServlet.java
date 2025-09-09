package servlet.orderlist;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.order.OrderListDAO;
import model.order.OrderInfo;

@WebServlet("/api/orderlist")
public class OrderListJsonServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            response.setContentType("application/json; charset=UTF-8");

            OrderListDAO dao = new OrderListDAO();
            List<OrderInfo> orderList = dao.getAllOrderList();

            String json = new Gson().toJson(orderList);

            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();

        } catch (Exception e) {
        	e.printStackTrace();
			request.getRequestDispatcher("/jsp/Error.jsp").forward(request, response);
        }
    }
}
