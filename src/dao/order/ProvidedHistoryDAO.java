package dao.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.order.ProvidedHistoryInfo;
import model.order.ProvidedHistoryToppingInfo;
import util.DBUtil;

public class ProvidedHistoryDAO {
    public List<ProvidedHistoryInfo> getAllProvidedHistoryList() {
        // 注文情報を保持するマップ
        Map<Integer, ProvidedHistoryInfo> orderMap = new HashMap<>();

        try (Connection con = util.DBUtil.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT\n"
                     + "o.order_id, o.product_quantity, o.table_number, o.order_time, o.order_flag,\n"
                     + "d.product_id,\n"
                     + "p.product_name, p.category_name, p.product_price, p.product_stock,\n"
                     + "m.topping_id, m.topping_quantity,\n"
                     + "t.topping_name, t.topping_price, t.topping_stock\n"
                     + "FROM\n"
                     + "order_details o\n"
                     + "JOIN\n"
                     + "product_details d ON o.order_id = d.order_id\n"
                     + "JOIN\n"
                     + "product p ON d.product_id = p.product_id\n"
                     + "LEFT JOIN\n"
                     + "multiple_toppings m ON o.order_id = m.order_id\n"
                     + "LEFT JOIN\n"
                     + "topping t ON m.topping_id = t.topping_id\n"
                     + "WHERE\n"
                     + "o.order_flag = 1 AND o.accounting_flag=0")) {

            while (rs.next()) {
                // 商品情報
                int order_id = rs.getInt("order_id");
                String order_time = rs.getString("order_time");
                int product_quantity = rs.getInt("product_quantity");
                int table_number = rs.getInt("table_number");
                int order_flag = rs.getInt("order_flag");
                String product_name = rs.getString("product_name");
                String category_name = rs.getString("category_name");
                int product_price = rs.getInt("product_price");
                int product_stock = rs.getInt("product_stock");

                // トッピング情報
                int topping_id = rs.getInt("topping_id");
                int topping_quantity = rs.getInt("topping_quantity");
                String topping_name = rs.getString("topping_name");
                int topping_price = rs.getInt("topping_price");
                int topping_stock = rs.getInt("topping_stock");

                // トッピング情報を保持するオブジェクト
                ProvidedHistoryToppingInfo toppingInfo = new ProvidedHistoryToppingInfo(
                    topping_id, topping_quantity, topping_name, topping_price, topping_stock
                );

                // 注文がすでに存在する場合はトッピングを追加、存在しない場合は新規に注文を作成
                ProvidedHistoryInfo orderInfo = orderMap.get(order_id);
                if (orderInfo != null) {
                    // 既存の注文にトッピングを追加
                    orderInfo.getHistoryTopping().add(toppingInfo);
                } else {
                    // 新しい注文を作成し、トッピング情報も追加
                    List<ProvidedHistoryToppingInfo> toppingList = new ArrayList<>();
                    toppingList.add(toppingInfo);

                    orderInfo = new ProvidedHistoryInfo(
                        order_id, order_time, product_quantity, table_number, order_flag, product_name, category_name, product_price, product_stock, toppingList
                    );

                    // マップに追加
                    orderMap.put(order_id, orderInfo);
                }
            }
        } catch (SQLException e) {
            // エラーハンドリングの強化
            e.printStackTrace();
            // 必要に応じて他の処理（ロギングなど）を追加
        }

        // マップの値をリストとして返す
        return new ArrayList<>(orderMap.values());
    }
    //表示・非表示フラグの更新
  	public void updateProvidedHistoryList(int orderId,int orderFlag){
  		String sql = "UPDATE order_details SET order_flag = ? WHERE order_id = ?";
  		try(Connection con = DBUtil.getConnection();
  				PreparedStatement ps = con.prepareStatement(sql)){
  			
  			ps.setInt(1, orderFlag);
  			ps.setInt(2, orderId);
  			
  			ps.executeUpdate();
  			
  		} catch (SQLException e) {
  			System.err.println("予期しないエラーが発生しました。");
  			e.printStackTrace();
  		}
  	}
}
