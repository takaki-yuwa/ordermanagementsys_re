package model.login;

/*
 * ログイン情報を保持するモデルクラス
 * ログインID、パスワード
 */
public class LoginInfo {
	private String id;
	private String password;

	public LoginInfo(String id, String password) {
		this.setId(id);
		this.setPassword(password);
	}

	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
