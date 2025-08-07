package dao.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.login.LoginInfo;
import util.DBUtil;

public class LoginDAO {
	public LoginInfo getLogin(String userid) {
		LoginInfo loginInfo = null;
		//login_idを参照してlogin_passwordを取得する
		String selectLoginSql = "SELECT login_password FROM user_login WHERE login_id = ?";
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement selectStmt = connection.prepareStatement(selectLoginSql)) {

			selectStmt.setString(1, userid);

			try (ResultSet resultSet = selectStmt.executeQuery()) {
				if (resultSet.next()) {
					String password = resultSet.getString("login_password");

					loginInfo = new LoginInfo();
					loginInfo.setId(userid);
					loginInfo.setPassword(password);
				}
			} catch (Exception e) {
				System.err.println("ResultSet処理中にエラーが発生しました");
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.err.println("SQL例外が発生しました：データベース接続またはSQL実行に失敗しました");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("予期しない例外が発生しました");
			e.printStackTrace();
		}

		return loginInfo;

	}
}
