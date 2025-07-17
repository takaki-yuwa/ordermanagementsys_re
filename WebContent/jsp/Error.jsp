<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!--サイトのサイズ自動調整-->
<meta name="viewport"
	content="width=device-width,height=device-height,initial-scale=1.0">
<title>エラーが発生しました</title>
<!-- .cssの呼び出し -->
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/Error/error.css">
</head>
<body>
	<main>
		<div class="error-box">
			<form action="Login.jsp" method="post" novalidate>
				<!-- エラーメッセージ表示 -->
				<h1>問題が発生しました</h1>
				<br>
				<div>お手数ですが、ログイン画面に</div>
				<div>再度アクセスしてください</div>
				<br>
				<button type="submit">ログインへ</button>
				<br>
			</form>
		</div>
	</main>
</body>
</html>