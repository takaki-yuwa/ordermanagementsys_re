package dao.topping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.topping.ToppingInfo;
import util.DBUtil;

public class ToppingListDAO {
	//情報取得
	public List<ToppingInfo> selectToppingList() {
		List<ToppingInfo> toppingInfo = new ArrayList<>();

		try (Connection con = DBUtil.getConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM topping")) {

			while (rs.next()) {
				int id = rs.getInt("topping_id");
				String name = rs.getString("topping_name");
				int price = rs.getInt("topping_price");
				int stock = rs.getInt("topping_stock");
				int display_flag = rs.getInt("topping_display_flag");

				toppingInfo.add(new ToppingInfo(id, name, price, stock, display_flag));
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

		return toppingInfo;

	}

	//表示・非表示フラグの更新
	public void updateToppingList(int toppingId, int visibleFlag) {
		String sql = "UPDATE topping SET topping_display_flag = ? WHERE topping_id = ?";
		try (Connection con = DBUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, visibleFlag);
			ps.setInt(2, toppingId);

			ps.executeUpdate();

		} catch (SQLException e) {
			System.err.println("予期しないエラーが発生しました。");
			e.printStackTrace();
		}
	}
}
