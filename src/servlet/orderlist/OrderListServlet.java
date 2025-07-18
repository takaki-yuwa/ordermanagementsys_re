package servlet.orderlist;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.order.OrderListDAO;
import model.order.OrderInfo;

@WebServlet("/OrderList")
public class OrderListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // キャッシュ制御ヘッダーを設定
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP/1.1
        response.setHeader("Pragma", "no-cache"); // HTTP/1.0
        response.setDateHeader("Expires", 0); // プロキシ／Expiresヘッダー用

        // 注文リストを取得
        OrderListDAO dao = new OrderListDAO();
        List<OrderInfo> orderList = dao.getAllOrderList();

        // リストの内容を出力
        System.out.println(orderList);  // Listの内容をそのまま表示

        // 注文リストをリクエスト属性にセット
        request.setAttribute("orderinfo", orderList);

        // 次のページ (OrderList.jsp) へフォワード
        request.getRequestDispatcher("/jsp/OrderList.jsp").forward(request, response);
    }
}


