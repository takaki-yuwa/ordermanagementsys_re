package dao.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import model.orderditails.OrderDeleteInfo;
import model.orderditails.OrderDeleteToppingInfo;
import model.orderditails.OrderUpdateInfo;
import model.orderditails.OrderUpdateToppingInfo;

public class OrderDetailsDAO {

	public void updateOrderDetails(OrderUpdateInfo orderUpdateInfo) throws SQLException {

		try (Connection con = util.DBUtil.getConnection()) {

			// 1. order_details テーブルの更新
			String sql1 = "UPDATE order_details SET product_quantity = ?, order_price = ? WHERE order_id = ?";
			try (PreparedStatement ps1 = con.prepareStatement(sql1)) {
				ps1.setInt(1, orderUpdateInfo.getProductQuantity());
				ps1.setInt(2, orderUpdateInfo.getOrderPrice());
				ps1.setInt(3, orderUpdateInfo.getOrderId());
				ps1.executeUpdate();
			}

			// 2. product テーブルの更新
			String sql2 = "UPDATE product SET product_stock = ? WHERE product_id = ?";
			try (PreparedStatement ps2 = con.prepareStatement(sql2)) {
				ps2.setInt(1, orderUpdateInfo.getProductStock());
				ps2.setInt(2, orderUpdateInfo.getProductId());
				ps2.executeUpdate();
			}

			// 3. multiple_topping テーブルの更新（複数対応）
			String sql3 = "INSERT INTO multiple_topping (order_id, topping_id, topping_quantity) " +
					"VALUES (?, ?, ?) " +
					"ON DUPLICATE KEY UPDATE topping_quantity = VALUES(topping_quantity)";
			try (PreparedStatement ps3 = con.prepareStatement(sql3)) {
				List<OrderUpdateToppingInfo> toppingList = orderUpdateInfo.getOrderTopping();
				if (toppingList != null) {
					for (OrderUpdateToppingInfo topping : toppingList) {
						ps3.setInt(1, orderUpdateInfo.getOrderId());
						ps3.setInt(2, topping.getToppingId());
						ps3.setInt(3, topping.getToppingQuantity());
						ps3.addBatch(); // バッチで実行する
					}
					ps3.executeBatch();
				}
			}

			// 4. topping テーブルの更新（複数対応）
			String sql4 = "UPDATE topping SET topping_stock = ? WHERE topping_id = ?";
			try (PreparedStatement ps4 = con.prepareStatement(sql4)) {
				List<OrderUpdateToppingInfo> toppingList = orderUpdateInfo.getOrderTopping();
				if (toppingList != null) {
					for (OrderUpdateToppingInfo topping : toppingList) {
						ps4.setInt(1, topping.getToppingStock());
						ps4.setInt(2, topping.getToppingId());
						ps4.addBatch();
					}
					ps4.executeBatch();
				}
			}

		}
	}

	public void deletOrderDetails(OrderDeleteInfo orderDeleteInfo) throws SQLException {

		try (Connection con = util.DBUtil.getConnection()) {

			// 1. order_details テーブルの削除
			String sql1 = "DELETE FROM order_details WHERE order_id= ?";
			try (PreparedStatement ps1 = con.prepareStatement(sql1)) {
				ps1.setInt(1, orderDeleteInfo.getOrderId());
				ps1.executeUpdate();
			}

			// 2. multiple_topping テーブルの削除
			String sql2 = "DELETE FROM multiple_topping WHERE order_id= ?";
			try (PreparedStatement ps2 = con.prepareStatement(sql2)) {
				ps2.setInt(1, orderDeleteInfo.getOrderId());
				ps2.executeUpdate();
			}

			// 3. product_details テーブルの削除
			String sql3 = "DELETE FROM product_details WHERE order_id= ?";
			try (PreparedStatement ps3 = con.prepareStatement(sql3)) {
				ps3.setInt(1, orderDeleteInfo.getOrderId());
				ps3.executeUpdate();
			}

			// 4. product テーブルの更新
			String sql4 = "UPDATE product SET product_stock = ? WHERE product_id = ?";
			try (PreparedStatement ps4 = con.prepareStatement(sql4)) {
				ps4.setInt(1, orderDeleteInfo.getProductStock());
				ps4.setInt(2, orderDeleteInfo.getProductId());
				ps4.executeUpdate();
			}

			// 5. topping テーブルの更新
			String sql5 = "UPDATE topping SET topping_stock = ? WHERE topping_id = ?";
			try (PreparedStatement ps5 = con.prepareStatement(sql5)) {
				List<OrderDeleteToppingInfo> toppingList = orderDeleteInfo.getOrderTopping();
				if (toppingList != null) {
					for (OrderDeleteToppingInfo topping : toppingList) {
						ps5.setInt(1, topping.getToppingStock());
						ps5.setInt(2, topping.getToppingId());
						ps5.addBatch();
					}
					ps5.executeBatch();
				}

			}
		}
	}
}
