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
<title>注文管理システム--トッピング一覧--</title>
<!-- .cssの呼び出し -->
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/popup.css">
<link rel="stylesheet" href="css/Topping/toppinglist.css">
<!-- ファビコン非表示 -->
<link rel="icon" href="data:," />
</head>
<body>
	<main>
		<div class="tab-wrapper">
			<div class="btn-row">
				<!--ホームボタン-->
				<form action="Home">
					<input type="image"
						src="<%=request.getContextPath()%>/image/homeButton.png"
						alt="ホームボタン" class="homebutton">
				</form>
				<!-- 新規作成ボタン -->
				<form action="ToppingCreateForm" method="post">
					<input type="hidden" name="form" value="ToppingCreate">
					<button type="submit" class="btn-create">新規作成</button>
				</form>
			</div>
		</div>
		<div class="tab-contents">
		<table>
			<thead>
				<tr>
					<th>トッピング名</th>
					<th>価格</th>
					<th>編集</th>
					<th>表示切替</th>
					<th>削除</th>
				</tr>
			</thead>
			<tbody>
				<!-- productInfo リストをループして表示 -->
				<c:forEach var="topping" items="${toppingInfo}">
				<div class="topping-row">
					<td class="topping-name"><c:out value="${topping.name}" /></td>
					<td style="border-left: 1px solid #e4e4e4;" class="topping-price"><c:out value="${topping.price}" />円</td>
					<td style="border-left: 1px solid #e4e4e4;">
					<!-- 編集ボタンで商品新規作成・編集画面へ遷移する -->
					<form action="ToppingEditForm" method="post">
						<input type="hidden" name="form" value="ToppingEdit"> 
						<input type="hidden" name="topping_id" value="${topping.id}"> 
						<input type="hidden" name="topping_name" value="<c:out value='${topping.name}' />"> 
						<input type="hidden" name="topping_price" value="<c:out value='${topping.price}' />">
						<button class="btn-edit" type="submit">編集</button>
					</form>
					</td>
					<td style="border-left: 1px solid #e4e4e4;">
						${topping.visible_flag == 1 ? '表示中：' : '非表示：'}
						<!-- 表示フラグが1のとき：非表示にするボタン -->
						<label class="switch" type="button"
						class="btn-toggle ${topping.visible_flag == 1 ? 'btn-hide' : 'btn-display'}"
						id="toggle-btn-${topping.id}"
						data-id="<c:out value='${topping.id}'/>"
						data-visible-flag="<c:out value='${topping.visible_flag}'/>"
						data-name="<c:out value='${topping.name}'/>"
						data-type="topping"
						data-label="トッピング"
						data-action="toggle"
						onclick="handleCommonToggle(this)">
						<input type="checkbox" ${topping.visible_flag == 1 ? 'checked' : ''} disabled>
						<span class="slider"></span>
						</label>
					</td>
					<td style="border-left: 1px solid #e4e4e4;">
						<button class="btn-trash"
							id="delete-btn-${topping.id}"
							data-id="<c:out value='${topping.id}'/>"
							data-name="<c:out value='${topping.name}'/>"
							data-type="topping"
							data-label="トッピング"
							data-action="delete"
							onclick="handleCommonToggle(this)">
							削除
						</button>
					</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
	</main>
	<!--ポップアップの背景-->
	<div class="popup-overlay" id="popup-overlay"></div>
	<!--ポップアップの内容-->
	<div class="popup-content" id="popup-content">
		<p id="popup-topping-name" class="text-bold"></p>
		<p id="popup-message"></p>
		<p>よろしいですか？</p>
		<!-- トッピングの表示変更 -->
		<button class="popup-close" id="close-popup">いいえ</button>
		<form action="ToppingList" method="post">
			<input type="hidden" name="topping_id" id="popup-topping-id">
			<input type="hidden" name="topping_visible_flag"
				id="popup-topping-visible-flag">
			<button type="submit" class="popup-proceed" id="confirm-button"
				data-action="" data-target-id="">
				は い
			</button>
		</form>
	</div>
	</main>
	<!-- .jsの呼び出し -->
	<script src="JavaScript/Popup.js"></script>
	<script src="JavaScript/setTabContent.js"></script>
</body>
</html>