package dao.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.product.ProductInfo;
import util.DBUtil;

public class ProductListDAO {
	//情報取得
	public List<ProductInfo> selectProductList() {
		List<ProductInfo> productInfo = new ArrayList<>();

		try (Connection con = DBUtil.getConnection();
			 Statement st = con.createStatement();
			 ResultSet rs = st.executeQuery("SELECT * FROM product")) {

			while (rs.next()) {
				int id = rs.getInt("product_id");
				String name = rs.getString("product_name");
				String category = rs.getString("category_name");
				int price = rs.getInt("product_price");
				int stock = rs.getInt("product_stock");
				int display_flag = rs.getInt("product_display_flag");

				productInfo.add(new ProductInfo(id, name, category, price, stock, display_flag));
			}

		} catch (SQLException e) {
			System.err.println("データベースから商品情報の取得中にエラーが発生しました。");
			System.err.println("エラーメッセージ: " + e.getMessage());
			System.err.println("SQL状態コード: " + e.getSQLState());
			System.err.println("エラーコード: " + e.getErrorCode());
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("予期しないエラーが発生しました。");
			e.printStackTrace();
		}

		return productInfo;
	}
	
	//表示・非表示フラグの更新
	public void updateProductList(int productId,int visibleFlag){
		String sql = "UPDATE product SET product_display_flag = ? WHERE product_id = ?";
		try(Connection con = DBUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)){
			
			ps.setInt(1, visibleFlag);
			ps.setInt(2, productId);
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			System.err.println("予期しないエラーが発生しました。");
			e.printStackTrace();
		}
	}
}