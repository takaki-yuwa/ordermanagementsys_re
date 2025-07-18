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
		String sql = "SELECT login_password FROM user_login WHERE login_id = ?";

		try (Connection con = DBUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, userid);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String password = rs.getString("login_password");

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
