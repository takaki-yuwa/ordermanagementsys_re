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
<title>注文管理システム--商品編集--</title>
<!-- .cssの呼び出し -->
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/formpopup.css">
<link rel="stylesheet" href="css/Product/productform.css">
<!-- ファビコン非表示 -->
<link rel="icon" href="data:," />
</head>
<body>
	<main>
		<!--ホームボタン-->
		<form action="Home">
			<input type="image"
				src="<%=request.getContextPath()%>/image/homeButton.png"
				alt="ホームボタン" class="btn-row">
		</form>
		<div class="text-center text-bottom text-bold">
			<!-- 新規作成ボタンからの場合 -->
			<c:if test="${formButton == 'ProductCreate'}">
			新規商品作成
			</c:if>
			<!-- 編集ボタンからの場合 -->
			<c:if test="${formButton == 'ProductEdit'}">
			商品変更
			</c:if>
		</div>
		<!-- 元のフォーム全体を囲む form -->
		<c:set var="actionUrl" value="${formButton == 'ProductCreate' ? 'ProductCreate' : 'ProductEdit'}" />
		<form id="productForm" method="post" action="${actionUrl}">
			<div class="form-box">
				<div class="form-group">
					<!-- カテゴリー -->
					<label for="category_name" class="text-box-label">カテゴリー</label><br>
					<select name="category_name" id="category_name"
						class="category-box">
						<option value="">-選択してください-</option>
						<c:forEach var="category" items="${categoryList}">
							<!-- 新規作成ボタンからの場合 -->
							<c:if test="${formButton == 'ProductCreate'}">
								<!-- カテゴリー情報を格納する -->
								<option value="<c:out value='${category}'/>"
									<c:if test="${category == productFormInfo.category}">selected</c:if>>
									<c:out value="${category}" />
								</option>
							</c:if>
							<!-- 編集ボタンからの場合 -->
							<c:if test="${formButton == 'ProductEdit'}">
								<!-- 取得したカテゴリー情報を格納しておく -->
								<option value="<c:out value='${category}'/>"
									<c:if test="${category == productFormInfo.category}">selected</c:if>>
									<c:out value="${category}" />
								</option>
							</c:if>
						</c:forEach>
					</select> <br> <span class="text-error" id="categoryNameError">
						<c:if test="${not empty categoryNameError}">
							<c:out value="${categoryNameError}" />
						</c:if>
					</span>
				</div>

				<br>

				<div class="form-group">
					<!-- 商品名 -->
					<label for="product_name" class="text-box-label">商品名</label>
					<span class="note-text">※最大18文字</span><br>
					<!-- 新規作成ボタンからの場合 -->
					<c:if test="${formButton == 'ProductCreate'}">
						<input type="text" id="product_name" name="product_name"
							class="product-box"
							value="<c:out value='${productFormInfo.name}'/>" required>
					</c:if>
					<!-- 編集ボタンからの場合 -->
					<c:if test="${formButton == 'ProductEdit'}">
						<!-- 変更だけ商品名だけでなくIDも送る -->
						<input type="hidden" name="product_id"
							value="${productFormInfo.id}">
						<input type="text" id="product_name" name="product_name"
							class="product-box"
							value="<c:out value='${productFormInfo.name}'/>" required>
					</c:if>
					<br> 
					<span class="text-error" id="productNameError"> 
						<c:if test="${not empty productNameError}">
							<c:out value="${productNameError}" />
						</c:if>
					</span>
				</div>

				<br>

				<!-- トッピング -->
				<fieldset class="checkbox-group">
					<legend class="text-box-label">トッピング</legend>
					<br>
					<!-- 新規作成ボタンからの場合 -->
					<c:if test="${formButton == 'ProductCreate'}">
						<c:forEach var="topping" items="${toppingInfo}">
							<c:set var="checkboxId" value="topping_${topping.id}" />
							<label for="${checkboxId}"> 
								<input type="checkbox" name="topping_id" id="${checkboxId}" value="${topping.id}">
								<c:out value="${topping.name}" />
							</label>
						</c:forEach>
					</c:if>

					<!-- 編集ボタンからの場合 -->
					<c:if test="${formButton == 'ProductEdit'}">
						<c:forEach var="topping" items="${toppingInfo}">
							<c:set var="checkboxId" value="topping_${topping.id}" />
							<c:set var="checked" value="false" />
							<c:forEach var="pt" items="${productToppingInfo}">
								<c:if test="${pt.topping_id == topping.id}">
									<c:set var="checked" value="true" />
								</c:if>
							</c:forEach>
							<label for="${checkboxId}"> 
								<input type="checkbox" name="topping_id" id="${checkboxId}" value="${topping.id}"
								<c:if test="${checked}">checked</c:if>> 
								<c:out value="${topping.name}" />
							</label>
						</c:forEach>
					</c:if>
				</fieldset>
				<br> <br>

				<!-- 金額 -->
				<div class="form-group">
					<label for="product_price" class="text-box-label">金額</label>
					<span class="note-text">※最大5桁</span><br>
					<!-- 新規作成ボタンからの場合 -->
					<c:if test="${formButton == 'ProductCreate'}">
						<c:choose>
							<c:when test="${productFormInfo.price == 0 || empty productFormInfo.price}">
								<input type="text" id="product_price" name="product_price" class="price-box" value="" required>
							</c:when>
							<c:otherwise>
								<input type="text" id="product_price" name="product_price"
									class="price-box"
									value="<c:out value='${productFormInfo.price}'/>" required>
							</c:otherwise>
						</c:choose>
						<span class="text-bold">円</span>
					</c:if>
					<!-- 編集ボタンからの場合 -->
					<c:if test="${formButton == 'ProductEdit'}">
						<input type="text" id="product_price" name="product_price"
							class="price-box"
							value="<c:out value='${productFormInfo.price}'/>" required>
						<span class="text-bold">円</span>
					</c:if>
					<br> 
					<span class="text-error" id="productPriceError"> 
						<c:if test="${not empty productPriceError}">
							<c:out value="${productPriceError}" />
						</c:if>
					</span>
				</div>
				<br> <br>

				<!-- 横並びでボタンを配置 -->
				<div class="button-row">
					<!-- 左側：「商品一覧へ戻る」ボタン -->
					<!-- js側で遷移処理 -->
					<button type="button" onclick="goBackToList()" class="return-btn">商品一覧へ戻る</button>

					<!-- 右側：「作成」または「変更」ボタン -->
					<c:if test="${formButton == 'ProductCreate'}">
						<button type="button"
							data-type="product"
							onclick="handleCommonFormToggle(this)" class="create-btn">作成</button>
					</c:if>
					<c:if test="${formButton == 'ProductEdit'}">
						<button type="button"
							data-type="product"
							onclick="handleCommonFormToggle(this)" class="edit-btn">変更</button>
					</c:if>
				</div>
			</div>
		</form>
	</main>
	<!--ポップアップの背景-->
	<div class="popup-overlay" id="popup-overlay"></div>
	<!--ポップアップの内容-->
	<div class="popup-content" id="popup-content">
		<p id="popup-action-message" class="text-bold"></p>
		<!-- カテゴリー -->
		<div class="popup-info-row">
			<div class="popup-product-label popup-text">カテゴリー｜</div>
			<div class="popup-value" id="popup-category"></div>
		</div>

		<!-- 商品名 -->
		<div class="popup-info-row">
			<div class="popup-product-label popup-text">商品名｜</div>
			<div class="popup-value" id="popup-name"></div>
		</div>

		<!-- トッピング -->
		<div class="popup-info-row">
			<div class="popup-product-label popup-text">トッピング｜</div>
			<div class="popup-value" id="popup-toppings"></div>
		</div>

		<!-- 金額 -->
		<div class="popup-info-row">
			<div class="popup-product-label popup-text">金額｜</div>
			<div class="popup-value">
				<span id="popup-price"></span> 円
			</div>
		</div>
		<!-- 商品一覧へ遷移 -->
		<button class="popup-close" id="close-popup">いいえ</button>
		<button type="button" class="popup-proceed" id="confirm-button">はい</button>
	</div>
	<!-- .jsの呼び出し -->
	<script src="JavaScript/Popup.js"></script>
	<script src="JavaScript/Product/productform.js"></script>
</body>
</html>