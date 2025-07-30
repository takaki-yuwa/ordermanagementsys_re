package dao.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.order.OrderEditToppingInfo;

public class OrderEditDAO {

    public OrderEditDAO() {}

    // 商品IDからトッピングIDと名前を取得
    public List<OrderEditToppingInfo> getToppingsByProductId(int productId) throws SQLException {
        List<OrderEditToppingInfo> toppingList = new ArrayList<>();

        String sql = "SELECT t.topping_id, t.topping_name, t.topping_price, t.topping_stock " +
                     "FROM product_topping pt " +
                     "JOIN topping t ON pt.topping_id = t.topping_id " +
                     "WHERE pt.product_id = ?";

        try (Connection con = util.DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("topping_id");
                    String name = rs.getString("topping_name");
                    int price = rs.getInt("topping_price");
                    int stock = rs.getInt("topping_stock");
                    toppingList.add(new OrderEditToppingInfo(id, name, price, stock));
                }
            }
        }

        return toppingList;
    }
}
