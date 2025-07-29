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
<title>ToppingForm</title>
<!-- .cssの呼び出し -->
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/formpopup.css">
<link rel="stylesheet" href="css/Topping/toppingform.css">
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
			<c:if test="${formButton == 'ToppingCreate'}">
			新規トッピング作成
			</c:if>
			<!-- 編集ボタンからの場合 -->
			<c:if test="${formButton == 'ToppingEdit'}">
			トッピング変更
			</c:if>
		</div>
		<!-- 元のフォーム全体を囲む form -->
		<c:set var="actionUrl" value="${formButton == 'ToppingCreate' ? 'ToppingCreate' : 'ToppingEdit'}" />
		<form id="toppingForm" method="post" action="${actionUrl}">
			<div class="form-box">
				<div class="form-group">
					<!-- トッピング名 -->
					<label for="topping_name" class="text-box-label">トッピング名</label>
					<span class="note-text">※最大18文字</span><br>
					<!-- 新規作成ボタンからの場合 -->
					<c:if test="${formButton == 'ToppingCreate'}">
						<input type="text" id="topping_name" name="topping_name"
							class="topping-box"
							value="<c:out value='${toppingFormInfo.name}'/>" required>
					</c:if>
					<!-- 編集ボタンからの場合 -->
					<c:if test="${formButton == 'ToppingEdit'}">
						<!-- 変更だけ商品名だけでなくIDも送る -->
						<input type="hidden" name="topping_id" value="${toppingFormInfo.id}">
						<input type="text" id="topping_name" name="topping_name"
							class="topping-box"
							value="<c:out value='${toppingFormInfo.name}'/>" required>
					</c:if>
					<br> 
					<span class="text-error" id="toppingNameError"> 
						<c:if test="${not empty toppingNameError}">
							<c:out value="${toppingNameError}" />
						</c:if>
					</span>
				</div>
				<br>

				<!-- 金額 -->
				<div class="form-group">
					<label for="topping_price" class="text-box-label">金額</label> 
					<span id="topping_price_note" class="note-text">※最大5桁</span><br>
					<!-- 新規作成ボタンからの場合 -->
					<c:if test="${formButton == 'ToppingCreate'}">
						<c:choose>
							<c:when
								test="${toppingFormInfo.price == 0 || empty toppingFormInfo.price}">
								<input type="text" id="topping_price" name="topping_price"
									aria-describedby="topping_price_note" class="price-box"
									value="" required>
							</c:when>
							<c:otherwise>
								<input type="text" id="topping_price" name="topping_price"
									aria-describedby="topping_price_note" class="price-box"
									value="<c:out value='${toppingFormInfo.price}'/>" required>
							</c:otherwise>
						</c:choose>
						<span class="text-bold">円</span>
					</c:if>
					<!-- 編集ボタンからの場合 -->
					<c:if test="${formButton == 'ToppingEdit'}">
						<input type="text" id="topping_price" name="topping_price"
							aria-describedby="topping_price_note" class="price-box"
							value="<c:out value='${toppingFormInfo.price}'/>" required>
						<span class="text-bold">円</span>
					</c:if>
					<br> 
					<span class="text-error" id="toppingPriceError"> 
						<c:if
							test="${not empty toppingPriceError}">
							<c:out value="${toppingPriceError}" />
						</c:if>
					</span>
				</div>
				<br> <br>

				<!-- 横並びでボタンを配置 -->
				<div class="button-row">
					<!-- 左側：「トッピング一覧へ戻る」ボタン -->
					<!-- js側で遷移処理 -->
					<button type="button" onclick="goBackToList()" class="return-btn">トッピング一覧へ戻る</button>

					<!-- 右側：「作成」または「変更」ボタン -->
					<c:if test="${formButton == 'ToppingCreate'}">
						<button type="button" data-type="topping"
							onclick="handleCommonFormToggle(this)" class="create-btn">作成</button>
					</c:if>
					<c:if test="${formButton == 'ToppingEdit'}">
						<button type="button" data-type="topping"
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
		<!-- トッピング名 -->
		<div class="popup-info-row">
			<div class="popup-topping-label popup-text">トッピング名｜</div>
			<div class="popup-value" id="popup-name"></div>
		</div>

		<!-- 金額 -->
		<div class="popup-info-row">
			<div class="popup-topping-label popup-text">金額｜</div>
			<div class="popup-value">
				<span id="popup-price"></span> 円
			</div>
		</div>
		<!-- 商品一覧へ遷移 -->
		<button type="button" class="popup-proceed" id="confirm-button">はい</button>
		<button class="popup-close" id="close-popup">いいえ</button>
	</div>
	<!-- .jsの呼び出し -->
	<script src="JavaScript/Popup.js"></script>
	<script src="JavaScript/Topping/toppingform.js"></script>
</body>
</html>