<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!--サイトのサイズ自動調整-->
<meta name="viewport"
	content="width=device-width,height=device-height,initial-scale=1.0">
<title>Home</title>
<!--.cssの呼び出し-->
<link rel="stylesheet" href="../css/main.css">
<link rel="stylesheet" href="../css/Home/Home.css">

</head>
<body>
	<main>
		<form action="Logoutt" style="text-align: right;">
			<input type="image" src="../image/logoutButton.png" alt="ログアウトボタン"
				class="logout right-image">
		</form>
		<div class=textcenter>ホームメニュー</div>
		<hr>
		<h5>注文管理</h5>
		<form action="OrderList" method="post">
			<button class="btn--ordermanagemant">注文一覧</button>
		</form>
		<hr>
		<h5>メニュー管理</h5>
		<table>
			<tr>
				<td>
					<form action="Product" method="post">
						<button class="btn--menumanagemant">商品</button>
					</form>
				</td>
				<td>
					<form action="Topping" method="post">
						<button class="btn--menumanagemant">トッピング</button>
					</form>
				</td>
				<td>
					<form action="Category" method="post">
						<button class="btn--menumanagemant">カテゴリ</button>
					</form>
				</td>
			</tr>
		</table>
	</main>
</body>
</html>