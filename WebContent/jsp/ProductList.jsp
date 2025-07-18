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
<title>商品一覧画面</title>
<!-- .cssの呼び出し -->
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/Product/productlist.css">
<!-- .jsの呼び出し -->
<script src="JavaScript/Popup.js"></script>
</head>
<body>
	<main>
		<div class="product-box">
			<c:forEach var="product" items="${productInfo}">
				<div class="product-row">
					<div class="product-name">${product.name}・${product.visible_flag}</div>
					<div class="product-price">${product.price}円</div>

					<form action="ProductEditForm" method="get">
						<input type="hidden" name="product_id" value="${product.id}">
						<input type="hidden" name="product_name" value="${product.name}">
						<input type="hidden" name="category_name"
							value="${product.category}"> <input type="hidden"
							name="product_price" value="${product.price}"> <input
							type="hidden" name="product_stock" value="${product.stock}">
						<button class="btn-edit" type="submit">編集</button>
					</form>

					<!-- 表示フラグが1のとき：非表示にするボタン -->
					<button type="button"
						class="btn-toggle ${product.visible_flag == 1 ? 'btn-hide' : 'btn-display'}"
						id="toggle-btn-${product.id}"
						data-visible-flag="${product.visible_flag}"
						onclick="openProductDisplayTogglePopup('${product.id}','${product.visible_flag}','${product.name}')">${product.visible_flag == 1 ? '非表示にする' : '表示にする'}</button>
				</div>
			</c:forEach>
		</div>

	</main>
	<!--ポップアップの背景-->
	<div class="popup-overlay" id="popup-overlay"></div>
	<!--ポップアップの内容-->
	<div class="popup-content" id="popup-content">
		<p id="popup-prodcut-name"></p>
		<p id="popup-message"></p>
		<p>よろしいですか？</p>
		<!-- 商品の表示変更 -->
		<form action="ProductList" method="post">
			<input type="hidden" name="product_id" id="popup-product-id">
			<input type="hidden" name="product_visible_flag" id="popup-product-visible-flag"> 
			<button type="submit" class="popup-proceed" id="confirm-button"
				data-action="" data-target-product-id="">は い</button>
		</form>

		<button class="popup-close" id="close-popup">いいえ</button>
	</div>
</body>
</html>