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
		String selectToppingSql = "SELECT * FROM topping";
		List<ToppingInfo> toppingInfoList = new ArrayList<>();
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement selectStmt = connection.prepareStatement(selectToppingSql)) {

			try (ResultSet resultSet = selectStmt.executeQuery()) {
				while (resultSet.next()) {
					int id = resultSet.getInt("topping_id");
					String name = resultSet.getString("topping_name");
					int price = resultSet.getInt("topping_price");
					int stock = resultSet.getInt("topping_stock");
					int display_flag = resultSet.getInt("topping_display_flag");

					toppingInfoList.add(new ToppingInfo(id, name, price, stock, display_flag));
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

		return toppingInfoList;

	}

	//表示・非表示フラグの更新
	public void updateToppingFlag(int toppingId, int visibleFlag) {
		String updateToppingFlagSql = "UPDATE topping SET topping_display_flag = ? WHERE topping_id = ?";
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement updateStmt = connection.prepareStatement(updateToppingFlagSql)) {

			updateStmt.setInt(1, visibleFlag);
			updateStmt.setInt(2, toppingId);

			updateStmt.executeUpdate();

		} catch (SQLException e) {
			System.err.println("トッピング表示フラグ更新中にSQLエラーが発生しました: " + e.getMessage());
			e.printStackTrace();
		}
	}

	//トッピング新規作成
	public void insertTopping(String toppingName, int toppingPrice) {
		String insertToppingSql = "INSERT INTO topping (topping_name, topping_price, topping_stock, topping_display_flag) VALUES (?, ?, 20, 1)";
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement insertStmt = connection.prepareStatement(insertToppingSql)) {

			insertStmt.setString(1, toppingName);
			insertStmt.setInt(2, toppingPrice);

			insertStmt.executeUpdate();

		} catch (SQLException e) {
			System.err.println("トッピング登録中にエラーが発生しました:" + e.getMessage());
			e.printStackTrace();
		}
	}

	//トッピング変更
	public void updateTopping(int toppingId, String toppingName, int toppingPrice) {
		String updateToppingSql = "UPDATE topping SET topping_name = ?, topping_price = ? WHERE topping_id = ?";
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement updateStmt = connection.prepareStatement(updateToppingSql)) {

			updateStmt.setString(1, toppingName);
			updateStmt.setInt(2, toppingPrice);
			updateStmt.setInt(3, toppingId);

			updateStmt.executeUpdate();

		} catch (SQLException e) {
			System.err.println("トッピング変更中にエラーが発生しました:" + e.getMessage());
			e.printStackTrace();
		}
	}
}
