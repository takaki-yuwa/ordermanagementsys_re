package servlet.providedhistory;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import constants.Constants;
import dao.order.ProvidedHistoryDAO;
import model.order.ProvidedHistoryInfo;

@WebServlet("/ProvidedHistory")
public class ProvidedHistoryServlet extends HttpServlet {

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
			String selectedCategory = request.getParameter("selected_category");
			// 選択カテゴリーをセッションにも保持
			if (selectedCategory != null && !selectedCategory.isEmpty()) {
				request.getSession().setAttribute("selectedCategory", selectedCategory);
				request.setAttribute("selectedCategory", selectedCategory);
			}

			ProvidedHistoryDAO dao = new ProvidedHistoryDAO();

			// 注文情報の更新処理がある場合
			if (orderIdStr != null && orderFlagStr != null) {
				int orderId = Integer.parseInt(orderIdStr);
				int orderFlag = Integer.parseInt(orderFlagStr);
				orderFlag = (orderFlag == 0) ? 1 : 0;

				dao.updateProvidedHistoryList(orderId, orderFlag);
			}

			// 注文リストを取得
			List<ProvidedHistoryInfo> historyList = dao.getAllProvidedHistoryList();
			System.out.println(historyList); // デバッグ表示

			// 注文リストをセットしてJSPにフォワード
			request.setAttribute("historyinfo", historyList);
			request.setAttribute("categoryList", Constants.HISTORY_TABLE_LIST);
			request.getRequestDispatcher("/jsp/ProvidedHistory.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.getRequestDispatcher("/jsp/Error.jsp").forward(request, response);
		}
	}
}
