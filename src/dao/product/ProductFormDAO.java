package dao.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.producttopping.ProductToppingInfo;
import util.DBUtil;

public class ProductFormDAO {
	//情報取得
	public List<ProductToppingInfo> selectProductToppingList(int productId) {
		List<ProductToppingInfo> productToppingInfo = new ArrayList<>();
		String sql = "SELECT * FROM product_topping WHERE product_id = ?";
		try (Connection con = DBUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, productId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					int productToppingId = rs.getInt("product_topping_id");
					int toppingId = rs.getInt("topping_id");
					int fechedProductId = rs.getInt("product_id");

					// デバッグ用出力
					System.out.println("商品ID: " + fechedProductId);
					System.out.println("トッピングID:" + toppingId);

					productToppingInfo.add(new ProductToppingInfo(productToppingId, toppingId, fechedProductId));
				}
			}

		} catch (SQLException e) {
			System.err.println("データベースから商品トッピング情報の取得中にエラーが発生しました。");
			System.err.println("エラーメッセージ: " + e.getMessage());
			System.err.println("SQL状態コード: " + e.getSQLState());
			System.err.println("エラーコード: " + e.getErrorCode());
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("予期しないエラーが発生しました。");
			e.printStackTrace();
		}

		return productToppingInfo;
	}
}
