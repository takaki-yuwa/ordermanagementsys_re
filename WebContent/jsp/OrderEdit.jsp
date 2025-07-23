<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!--サイトのサイズ自動調整-->
<meta name="viewport"
	content="width=device-width,height=device-height,initial-scale=1.0">
<!--.cssの呼び出し-->
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/Order/OrderEdit.css">
<title>注文編集</title>
</head>
<body>
	<!--ホームボタン-->
	<form action="Home">
		<input type="image" src="image/homeButton.png" alt="ホームボタン"
			class="homebutton">
	</form>
	<div class="box">
		<div class="underline">
			95番<span>1卓</span>
		</div>
		<div class="underline">
			豚玉お好み焼き
			<button>注文取り消し</button>
		</div>
		<div>
			<ul>
				<li>コーン</li>
				<li>チーズ</li>
				<li>もち</li>
				<li>ベビースター</li>
				<li>ツナ</li>
				<li>カレー</li>
			</ul>
			<button>一覧へ戻る</button>
			<button>変更</button>
		</div>
	</div>
</body>
</html>