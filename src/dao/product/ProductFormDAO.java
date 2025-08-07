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
		String selectProductToppingSql = "SELECT * FROM product_topping WHERE product_id = ?";
		List<ProductToppingInfo> productToppingInfoList = new ArrayList<>();

		try (Connection connection = DBUtil.getConnection();
				PreparedStatement selectStmt = connection.prepareStatement(selectProductToppingSql)) {

			selectStmt.setInt(1, productId);

			try (ResultSet resultSet = selectStmt.executeQuery()) {
				while (resultSet.next()) {
					int productToppingId = resultSet.getInt("product_topping_id");
					int toppingId = resultSet.getInt("topping_id");
					int fechedProductId = resultSet.getInt("product_id");

					// デバッグ用出力
					System.out.println("商品ID: " + fechedProductId);
					System.out.println("トッピングID:" + toppingId);

					productToppingInfoList.add(new ProductToppingInfo(productToppingId, toppingId, fechedProductId));
				}
			}

		} catch (SQLException e) {
			System.err.println("商品トッピング情報の取得中にSQLエラーが発生しました。");
			System.err.println("エラーメッセージ: " + e.getMessage());
			System.err.println("SQL状態コード: " + e.getSQLState());
			System.err.println("エラーコード: " + e.getErrorCode());
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("商品トッピング情報の取得中に予期しないエラーが発生しました。");
			e.printStackTrace();
		}

		return productToppingInfoList;
	}
}
