package dao.topping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.topping.ToppingInfo;
import util.DBUtil;

public class ToppingListDAO {
	//情報取得
	public List<ToppingInfo> selectToppingList() {
		List<ToppingInfo> toppingInfo = new ArrayList<>();
		String sql = "SELECT * FROM topping";
		try (Connection con = DBUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					int id = rs.getInt("topping_id");
					String name = rs.getString("topping_name");
					int price = rs.getInt("topping_price");
					int stock = rs.getInt("topping_stock");
					int display_flag = rs.getInt("topping_display_flag");

					toppingInfo.add(new ToppingInfo(id, name, price, stock, display_flag));
				}
			}

		} catch (SQLException e) {
			System.err.println("データベースからトッピング情報の取得中にエラーが発生しました。");
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
	public void updateToppingFlag(int toppingId, int visibleFlag) {
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

	//トッピング新規作成
	public void insertToppingList(String toppingName, int toppingPrice) {
		String sql = "INSERT INTO topping (topping_name, topping_price, topping_stock, topping_display_flag) VALUES (?, ?, 20, 1)";
		try (Connection con = DBUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, toppingName);
			ps.setInt(2, toppingPrice);

			ps.executeUpdate();

		} catch (SQLException e) {
			System.err.println("トッピング登録中にエラーが発生しました。");
			e.printStackTrace();
		}
	}

	//トッピング変更
	public void updateToppingList(int toppingId, String toppingName, int toppingPrice) {
		String sql = "UPDATE topping SET topping_name = ?, topping_price = ? WHERE topping_id = ?";
		try (Connection con = DBUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, toppingName);
			ps.setInt(2, toppingPrice);
			ps.setInt(3, toppingId);

			ps.executeUpdate();

		} catch (SQLException e) {
			System.err.println("トッピング変更中にエラーが発生しました。");
			e.printStackTrace();
		}
	}
}
