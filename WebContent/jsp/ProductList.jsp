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
<title>ProductList</title>
<!-- .cssの呼び出し -->
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/popup.css">
<link rel="stylesheet" href="css/Product/productlist.css">
<link rel="stylesheet" href="css/Product/productlisttab.css">
<!-- ファビコン非表示 -->
<link rel="icon" href="data:," />
</head>
<body>
	<main>
		<!-- 選択されたカテゴリーを JavaScript に渡すための hidden input -->
		<input type="hidden" id="initial-selected-category"
			value="${selectedCategory}">

		<div class="tab">
			<!-- ラジオボタン（表示制御のキーになる） -->
			<!-- 直前に押されていたボタンを呼び出す -->
			<c:forEach var="category" items="${categoryList}"
				varStatus="category_">
				<input type="radio" name="tab" class="tab-item"
					id="tab${category_.index + 1}"
					${category_.index == 0 ? "checked" : ""}
					<c:if test="${category == selectedCategory}">checked</c:if>>
			</c:forEach>

			<div class="tab-wrapper">
				<div class="btn-row">
					<!--ホームボタン-->
					<form action="Home">
						<input type="image"
							src="<%=request.getContextPath()%>/image/homeButton.png"
							alt="ホームボタン" class="homebutton">
					</form>
					<!-- 新規作成ボタン -->
					<form action="ProductCreateForm" method="post">
						<input type="hidden" name="form" value="ProductCreate">
						<button type="submit" class="btn-create">新規作成</button>
					</form>
				</div>

				<!-- ラベル（横スクロール） -->
				<div class="tab-labels">
					<c:forEach var="category" items="${categoryList}"
						varStatus="category_">
						<label for="tab${category_.index + 1}">${category}</label>
					</c:forEach>
				</div>
			</div>
			<div class="product-box">
				<c:forEach var="product" items="${productInfo}">
					<!-- data-categoryに現在のカテゴリー情報を保存 -->
					<div class="product-row hidden-row"
						data-category="${product.category}">
						<div class="product-name">${product.name}</div>
						<div class="product-price">${product.price}円</div>

						<!-- 編集ボタンで商品新規作成・編集画面へ遷移する -->
						<form action="ProductEditForm" method="post">
							<input type="hidden" name="form" value="ProductEdit"> <input
								type="hidden" name="product_id" value="${product.id}"> <input
								type="hidden" name="product_name" value="${product.name}">
							<input type="hidden" name="category_name"
								value="${product.category}"> <input type="hidden"
								name="product_price" value="${product.price}">
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

		</div>
	</main>
	<!--ポップアップの背景-->
	<div class="popup-overlay" id="popup-overlay"></div>
	<!--ポップアップの内容-->
	<div class="popup-content" id="popup-content">
		<p id="popup-product-name" class="text-bold"></p>
		<p id="popup-message"></p>
		<p>よろしいですか？</p>
		<!-- 商品の表示変更 -->
		<form action="ProductList" method="post">
			<input type="hidden" name="product_id" id="popup-product-id">
			<input type="hidden" name="product_visible_flag"
				id="popup-product-visible-flag"> <input type="hidden"
				name="selected_category" id="popup-selected-category">
			<button type="submit" class="popup-proceed" id="confirm-button"
				data-action="" data-target-product-id="">は い</button>
		</form>

		<button class="popup-close" id="close-popup">いいえ</button>
	</div>

	<!-- jsにjspのcategoryListを渡す -->
	<script>
		const categoryList=[
			<c:forEach items="${categoryList}" var="cat" varStatus="category_">
			'${cat}'<c:if test="${!category_.last}">,</c:if>
			</c:forEach>
			];
	</script>
	
	<!-- .jsの呼び出し -->
	<script src="<%=request.getContextPath()%>/JavaScript/Product/productList.js"></script>
	<script src="JavaScript/Popup.js"></script>
	<script src="JavaScript/setTabContent.js"></script>
</body>
</html>