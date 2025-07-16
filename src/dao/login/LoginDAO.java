package dao.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.login.LoginInfo;
import util.DBUtil;

public class LoginDAO {
	public LoginInfo getLogin(String userid) {
		LoginInfo loginInfo=null;
		
		String sql="SELECT login_password FROM user_login WHERE login_id = ?";
		
		try(Connection con=DBUtil.getConnection();
				PreparedStatement ps  = con.prepareStatement(sql)){
			
			ps.setString(1, userid);
			
			try(ResultSet rs=ps.executeQuery()){
				if(rs.next()) {
					String password=rs.getString("login_password");
					
					loginInfo=new LoginInfo();
					loginInfo.setId(userid);
					loginInfo.setPassword(password);
				}
			}
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		return loginInfo;
		
	}
}
