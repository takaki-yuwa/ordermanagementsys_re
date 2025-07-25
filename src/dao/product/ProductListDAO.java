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
	public void updateProductFlag(int productId, int visibleFlag) {
		String sql = "UPDATE product SET product_display_flag = ? WHERE product_id = ?";
		try (Connection con = DBUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, visibleFlag);
			ps.setInt(2, productId);

			ps.executeUpdate();

		} catch (SQLException e) {
			System.err.println("予期しないエラーが発生しました。");
			e.printStackTrace();
		}
	}

	//商品新規作成
	public void insertProductList(String productName, String categoryName, int productPrice, List<Integer> toppingIds) {
		Connection con = null;
		try {
			con = DBUtil.getConnection();
			// トランザクション開始（複数のSQL文を一括で実行し、途中で失敗したら巻き戻せるようにする）
			con.setAutoCommit(false);

			// productテーブルに商品情報をINSERTする
			String sqlInsertProduct = "INSERT INTO product (product_name, category_name, product_price, product_stock, product_display_flag) VALUES (?, ?, ?, 20, 1)";
			int generatedProductId = -1; // 新しく追加した商品のIDを格納する変数

			// PreparedStatementを生成し、自動採番されたキー（product_id）を取得可能にする
			try (PreparedStatement ps = con.prepareStatement(sqlInsertProduct, Statement.RETURN_GENERATED_KEYS)) {
				// SQLのパラメータに値をセット
				ps.setString(1, productName);
				ps.setString(2, categoryName);
				ps.setInt(3, productPrice);

				// SQLを実行（INSERT）
				ps.executeUpdate();

				// 自動採番されたproduct_idを取得
				try (ResultSet rs = ps.getGeneratedKeys()) {
					if (rs.next()) {
						generatedProductId = rs.getInt(1);
					}
				}
			}

			// 取得したproduct_idが有効かつトッピングリストが空でなければ処理を行う
			if (generatedProductId != -1 && toppingIds != null && !toppingIds.isEmpty()) {
				// product_toppingテーブルにINSERTするSQL文
				String sqlInsertTopping = "INSERT INTO product_topping (product_id, topping_id) VALUES (?, ?)";

				// PreparedStatementを準備し、複数トッピングを一括処理するためにバッチ処理を利用
				try (PreparedStatement ps = con.prepareStatement(sqlInsertTopping)) {
					for (Integer toppingId : toppingIds) {
						ps.setInt(1, generatedProductId); // 新規商品IDをセット
						ps.setInt(2, toppingId); // トッピングIDをセット
						ps.addBatch(); // バッチに追加
					}
					// バッチ実行でまとめてINSERT
					ps.executeBatch();
				}
			}

			// トランザクションをコミットして確定
			con.commit();
		} catch (Exception e) {
			System.err.println("商品登録中にエラーが発生しました。");
			e.printStackTrace();
			// 何かエラーが発生したらトランザクションをロールバックしてDBの状態を元に戻す
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} finally {
			// 最後に必ずコネクションのAutoCommitを元に戻して接続を閉じる
			if (con != null) {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	//商品変更
	public void updateProductList(int productId, String productName, String categoryName, int productPrice, List<Integer> validToppingIds) {
		Connection con = null;
		try {
			con = DBUtil.getConnection();
			// トランザクション開始（複数のSQL文を一括で実行し、途中で失敗したら巻き戻せるようにする）
			con.setAutoCommit(false);

			//商品トッピングテーブルでの処理
			// 商品トッピングの更新（不要な商品トッピング削除）
			String sqlDelete;
			boolean hasValidIds = validToppingIds != null && !validToppingIds.isEmpty();
			//validToppingIdsの中身が存在する場合
			if (hasValidIds) {
				StringBuilder sb = new StringBuilder("DELETE FROM product_topping WHERE product_id = ? AND topping_id NOT IN (");
				for (int i = 0; i < validToppingIds.size(); i++) {
					sb.append("?");
					if (i < validToppingIds.size() - 1)
						sb.append(",");
				}
				sb.append(")");
				sqlDelete = sb.toString();
				//存在しない場合
			} else {
				sqlDelete = "DELETE FROM product_topping WHERE product_id = ?";
			}

			try (PreparedStatement psDelete = con.prepareStatement(sqlDelete)) {
				psDelete.setInt(1, productId);
				//2番目以降にvalidToppingIdsをセット
				if (hasValidIds) {
					int index = 2;
					for (Integer toppingId : validToppingIds) {
						psDelete.setInt(index++, toppingId);
					}
				}
				// SQLを実行（DELETE）
				psDelete.executeUpdate();
			}

			//現在のテーブル上のトッピングIDを取得
			List<Integer> existingToppings = new ArrayList<>();
			String sqlSelect = "SELECT topping_id FROM product_topping WHERE product_id = ?";
			try (PreparedStatement psSelect = con.prepareStatement(sqlSelect)) {
				psSelect.setInt(1, productId);
				try (ResultSet rs = psSelect.executeQuery()) {
					while (rs.next()) {
						existingToppings.add(rs.getInt("topping_id"));
					}
				}
			}

			//validToppingIdsの中でまだテーブルにないものをINSERT
			String sqlInsert = "INSERT INTO product_topping (product_id, topping_id) VALUES (?, ?)";
			try (PreparedStatement psInsert = con.prepareStatement(sqlInsert)) {
				for (Integer toppingId : validToppingIds) {
					//テーブル上に存在しない場合追加
					if (!existingToppings.contains(toppingId)) {
						psInsert.setInt(1, productId);
						psInsert.setInt(2, toppingId);
						psInsert.addBatch();
					}
				}
				psInsert.executeBatch();
			}

			//商品テーブルでの処理
			//更新処理
			String sql = "UPDATE product SET product_name = ?, category_name = ?, product_price = ? WHERE product_id = ?";
			try (PreparedStatement ps = con.prepareStatement(sql)) {

				ps.setString(1, productName);
				ps.setString(2, categoryName);
				ps.setInt(3, productPrice);
				ps.setInt(4, productId);

				// SQLを実行（UPDATE）
				ps.executeUpdate();

			}

			con.commit(); // トランザクション終了

		} catch (Exception e) {
			System.err.println("予期しないエラーが発生しました。");
			e.printStackTrace();
			if (con != null) {
				try {
					con.rollback();
				} catch (Exception rollbackEx) {
					rollbackEx.printStackTrace();
				}
			}
		} finally {
			if (con != null) {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (Exception closeEx) {
					closeEx.printStackTrace();
				}
			}
		}
	}
}