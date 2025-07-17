package dao.product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.product.ProductInfo;
import util.DBUtil;

public class ProductListDAO {

	public List<ProductInfo> selectProductList(){
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
			e.printStackTrace();
		}

		return productInfo;
	}
}
