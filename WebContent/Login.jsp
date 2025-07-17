<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!--サイトのサイズ自動調整-->
<meta name="viewport"
	content="width=device-width,height=device-height,initial-scale=1.0">
<title>ログイン画面</title>
<!-- .cssの呼び出し -->
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/Login/login.css">
<!-- ファビコン非表示 -->
<link rel="icon" href="data:," />
</head>
<body>
	<div class="text-center text-bottom">ログイン</div>
	<main>
		<div class="login-box">
			<form id="loginForm" action="Login" method="post" novalidate>
				<!-- ID入力 -->
				<label for="userid">ID</label><br> 
				<input type="text" id="userid" name="userid" required><br>
				<!-- IDが空の場合JavaScriptでエラーテキスト表示 -->
				<span class="text-error"><%=request.getAttribute("useridError") != null ? request.getAttribute("useridError") : ""%></span><br>
				<br>
				<!-- パスワード入力 -->
				<label for="password" method="post">パスワード</label><br> 
				<input type="password" id="password" name="password" required><br>
				<!-- パスワードが空の場合JavaScriptでエラーテキスト表示 -->
				<span class="text-error"><%=request.getAttribute("passwordError") != null ? request.getAttribute("passwordError") : ""%></span><br>
				<br>
				<button type="submit">ログイン</button>
				<br>
				<!-- IDまたはパスワードを間違えている場合エラーテキスト表示 -->
				<span class="text-error"><%=request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : ""%></span><br>
				<span class="text-error"><%=request.getAttribute("noLoginMessage") != null ? request.getAttribute("noLoginMessage") : ""%></span><br>
				<br>
			</form>
		</div>
	</main>
</body>
</html>