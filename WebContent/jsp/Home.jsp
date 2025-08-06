<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!--サイトのサイズ自動調整-->
<meta name="viewport"
	content="width=device-width,height=device-height,initial-scale=1.0">
<title>注文管理システム--ホーム画面--</title>
<!--.cssの呼び出し-->
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/popup.css">
<link rel="stylesheet" href="css/Home/home.css">
<!-- ファビコン非表示 -->
<link rel="icon" href="data:," />
</head>
<body>
	<main>
		<div style="text-align: right;">
			<input type="image" src="image/logoutButton.png" alt="ログアウトボタン"
				class="logout right-image" onclick="showDisplayHidePopup()">
		</div>
		<div class="text-center">ホームメニュー</div>
		<hr>
		<h5>注文管理</h5>
		<form action="OrderList" method="get">
			<button class="btn--ordermanagemant button">注文一覧</button>
		</form>
		<hr>
		<h5>メニュー管理</h5>
		<table>
			<tr>
				<td>
					<form action="ProductList" method="get">
						<button class="btn--menumanagemant button">商品</button>
					</form>
				</td>
				<td>
					<form action="ToppingList" method="get">
						<button class="btn--menumanagemant button">トッピング</button>
					</form>
				</td>
				<td>
					<form action="CategoryList" method="get">
						<button class="btn--menumanagemant button">カテゴリ</button>
					</form>
				</td>
			</tr>
		</table>
	</main>
	<!--ポップアップの背景-->
	<div class="popup-overlay" id="popup-overlay"></div>
	<!--ポップアップの内容-->
	<div class="popup-content" id="popup-content">
		<p class="text-bold">ログアウトします</p>
		<p>よろしいですか？</p>
		<!-- ログアウト -->
		<form action="Logout" method="post">
			<button type="submit" class="popup-proceed" id="confirm-button"
				data-action="setupConfirmHidePopup">は い</button>
		</form>

		<button class="popup-close" id="close-popup">いいえ</button>
	</div>
	<!-- .jsの呼び出し -->
	<script src="JavaScript/Popup.js"></script>
</body>
</html>