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
		List<ProductInfo> productInfoList = new ArrayList<>();
		String selectProductSql = "SELECT * FROM product";
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement selectStmt = connection.prepareStatement(selectProductSql)) {

			try (ResultSet resultSet = selectStmt.executeQuery()) {
				while (resultSet.next()) {
					int id = resultSet.getInt("product_id");
					String name = resultSet.getString("product_name");
					String category = resultSet.getString("category_name");
					int price = resultSet.getInt("product_price");
					int stock = resultSet.getInt("product_stock");
					int display_flag = resultSet.getInt("product_display_flag");

					productInfoList.add(new ProductInfo(id, name, category, price, stock, display_flag));
				}
			}

		} catch (SQLException e) {
			System.err.println("データベースから商品情報の取得中にエラーが発生しました。");
			System.err.println("商品一覧取得中にSQLエラーが発生しました: " + e.getMessage());
			System.err.println("SQL状態コード: " + e.getSQLState());
			System.err.println("エラーコード: " + e.getErrorCode());
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("商品一覧取得中に予期しないエラーが発生しました。");
			e.printStackTrace();
		}

		return productInfoList;
	}

	//表示・非表示フラグの更新
	public void updateProductFlag(int productId, int visibleFlag) {
		String updateProductFlagSql = "UPDATE product SET product_display_flag = ? WHERE product_id = ?";
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement updateStmt = connection.prepareStatement(updateProductFlagSql)) {

			updateStmt.setInt(1, visibleFlag);
			updateStmt.setInt(2, productId);

			updateStmt.executeUpdate();

		} catch (SQLException e) {
			System.err.println("商品表示フラグ更新中にSQLエラーが発生しました: " + e.getMessage());
			e.printStackTrace();
		}
	}

	//商品新規作成
	public void insertProduct(String productName, String categoryName, int productPrice, List<Integer> toppingIds) {
		// productテーブルに商品情報をINSERTする
		String insertProductSql = "INSERT INTO product (product_name, category_name, product_price, product_stock, product_display_flag) VALUES (?, ?, ?, 20, 1)";
		// product_toppingテーブルにINSERTするSQL文
		String insertToppingSql = "INSERT INTO product_topping (product_id, topping_id) VALUES (?, ?)";
		Connection connection = null;
		try {
			connection = DBUtil.getConnection();
			// トランザクション開始（複数のSQL文を一括で実行し、途中で失敗したら巻き戻せるようにする）
			connection.setAutoCommit(false);

			int generatedProductId = -1; // 新しく追加した商品のIDを格納する変数

			// PreparedStatementを生成し、自動採番されたキー（product_id）を取得可能にする
			try (PreparedStatement insertStmt = connection.prepareStatement(insertProductSql, Statement.RETURN_GENERATED_KEYS)) {
				// SQLのパラメータに値をセット
				insertStmt.setString(1, productName);
				insertStmt.setString(2, categoryName);
				insertStmt.setInt(3, productPrice);

				// SQLを実行（INSERT）
				insertStmt.executeUpdate();

				// 自動採番されたproduct_idを取得
				try (ResultSet resultSet = insertStmt.getGeneratedKeys()) {
					if (resultSet.next()) {
						generatedProductId = resultSet.getInt(1);
					} else {
						throw new SQLException("商品IDの取得に失敗しました。");
					}
				}
			}

			// 取得したproduct_idが有効かつトッピングリストが空でなければ処理を行う
			if (generatedProductId != -1 && toppingIds != null && !toppingIds.isEmpty()) {

				// PreparedStatementを準備し、複数トッピングを一括処理するためにバッチ処理を利用
				try (PreparedStatement insetStmt = connection.prepareStatement(insertToppingSql)) {
					for (Integer toppingId : toppingIds) {
						insetStmt.setInt(1, generatedProductId); // 新規商品IDをセット
						insetStmt.setInt(2, toppingId); // トッピングIDをセット
						insetStmt.addBatch(); // バッチに追加
					}
					// バッチ実行でまとめてINSERT
					insetStmt.executeBatch();
				}
			}

			// トランザクションをコミットして確定
			connection.commit();
		} catch (Exception e) {
			System.err.println("商品登録中にSQLエラーが発生しました: " + e.getMessage());
			e.printStackTrace();
			// 何かエラーが発生したらトランザクションをロールバックしてDBの状態を元に戻す
			try {
				if (connection != null)
					connection.rollback();
			} catch (SQLException rollbackEx) {
				System.err.println("ロールバック失敗: " + rollbackEx.getMessage());
			}
			e.printStackTrace();
		} finally {
			// 最後に必ずコネクションのAutoCommitを元に戻して接続を閉じる
			try {
				if (connection != null)
					connection.setAutoCommit(true);
				if (connection != null)
					connection.close();
			} catch (SQLException closeEx) {
				System.err.println("接続終了中にエラーが発生しました。");
				closeEx.printStackTrace();
			}
		}
	}

	//商品変更
	public void updateProduct(int productId, String productName, String categoryName, int productPrice,
			List<Integer> validToppingIds) {
		// 商品トッピングの更新（不要な商品トッピング削除）
		String deleteProductToppingSql;
		String selectToppingSql = "SELECT topping_id FROM product_topping WHERE product_id = ?";
		String insertProductToppingSql = "INSERT INTO product_topping (product_id, topping_id) VALUES (?, ?)";
		String updateProductSql = "UPDATE product SET product_name = ?, category_name = ?, product_price = ? WHERE product_id = ?";
		Connection connection = null;
		try {
			connection = DBUtil.getConnection();
			// トランザクション開始（複数のSQL文を一括で実行し、途中で失敗したら巻き戻せるようにする）
			connection.setAutoCommit(false);

			//商品トッピングテーブルでの処理
			boolean hasValidIds = validToppingIds != null && !validToppingIds.isEmpty();
			//validToppingIdsの中身が存在する場合
			if (hasValidIds) {
				StringBuilder deleteSqlBuilder = new StringBuilder(
						"DELETE FROM product_topping WHERE product_id = ? AND topping_id NOT IN (");
				for (int i = 0; i < validToppingIds.size(); i++) {
					deleteSqlBuilder.append("?");
					if (i < validToppingIds.size() - 1)
						deleteSqlBuilder.append(",");
				}
				deleteSqlBuilder.append(")");
				deleteProductToppingSql = deleteSqlBuilder.toString();
				//存在しない場合
			} else {
				deleteProductToppingSql = "DELETE FROM product_topping WHERE product_id = ?";
			}

			try (PreparedStatement deleteStmt = connection.prepareStatement(deleteProductToppingSql)) {
				deleteStmt.setInt(1, productId);
				//2番目以降にvalidToppingIdsをセット
				if (hasValidIds) {
					int index = 2;
					for (Integer toppingId : validToppingIds) {
						deleteStmt.setInt(index++, toppingId);
					}
				}
				// SQLを実行（DELETE）
				deleteStmt.executeUpdate();
			}

			//現在のテーブル上のトッピングIDを取得
			List<Integer> existingToppingList = new ArrayList<>();
			try (PreparedStatement selectStmt = connection.prepareStatement(selectToppingSql)) {
				selectStmt.setInt(1, productId);
				try (ResultSet resultSet = selectStmt.executeQuery()) {
					while (resultSet.next()) {
						existingToppingList.add(resultSet.getInt("topping_id"));
					}
				}
			}

			//validToppingIdsの中でまだテーブルにないものをINSERT
			try (PreparedStatement insertStmt = connection.prepareStatement(insertProductToppingSql)) {
				for (Integer toppingId : validToppingIds) {
					//テーブル上に存在しない場合追加
					if (!existingToppingList.contains(toppingId)) {
						insertStmt.setInt(1, productId);
						insertStmt.setInt(2, toppingId);
						insertStmt.addBatch();
					}
				}
				insertStmt.executeBatch();
			}

			//商品テーブルでの処理
			//更新処理
			try (PreparedStatement updateStmt = connection.prepareStatement(updateProductSql)) {

				updateStmt.setString(1, productName);
				updateStmt.setString(2, categoryName);
				updateStmt.setInt(3, productPrice);
				updateStmt.setInt(4, productId);

				// SQLを実行（UPDATE）
				updateStmt.executeUpdate();

			}

			connection.commit(); // トランザクション終了

		} catch (Exception e) {
			System.err.println("商品更新中にSQLエラーが発生しました: " + e.getMessage());
			try {
				if (connection != null)
					connection.rollback();
			} catch (SQLException rollbackEx) {
				System.err.println("ロールバック失敗: " + rollbackEx.getMessage());
			}
			e.printStackTrace();

		} finally {
			try {
				if (connection != null)
					connection.setAutoCommit(true);
				if (connection != null)
					connection.close();
			} catch (SQLException closeEx) {
				System.err.println("接続終了中にエラーが発生しました。");
				closeEx.printStackTrace();
			}
		}
	}
}