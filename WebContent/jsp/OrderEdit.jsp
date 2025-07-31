<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!--サイトのサイズ自動調整-->
<meta name="viewport"
	content="width=device-width,height=device-height,initial-scale=1.0">
<!--.cssの呼び出し-->
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/popup.css">
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
			${orderId}番<span class="table">${tableNumber}卓</span>
		</div>
		<div class="underline">
			<span class="item-name">${productName}</span>
			<!--確認用-->
			<span style="display: none; class="stock" id="stock-p${productId}">：${productStock}</span>
			<!-- 増減ボタンを追加 -->
			<div class="quantity-buttons">
				<button type="button" name="quantity" class="decrease-btn"
					id="decrement-p${productId}">-</button>
				<!-- 数量を表示する要素、変数にバインド -->
				<span class="quantity" id="counter-p${productId}"
					data-stock="${productStock}" data-price="${productPrice}"
					data-quantity="${productQuantity}"> ${productQuantity} </span>
				<button type="button" name="quantity" class="increase-btn"
					id="increment-p${productId}">+</button>
			</div>
			<div class="form-delet">
				<button onclick="handleChangeButton('delete',${productId})"
					class="form-button delete">注文取り消し</button>
			</div>
		</div>
		<div class="list-padding">
			<!-- トッピングが1つ以上ある場合だけ表示 -->
			<c:if test="${not empty toppingList}">
				<ul class="list">
					<!-- トッピングのリストをループして表示 -->
					<c:forEach var="topping" items="${toppingList}" varStatus="status">
						<li class="menu-item"><span class="topping-name">${topping.toppingName}</span>
							<!--確認用--> 
							<span style="display: none; class="stock" id="stock-t${topping.toppingId}">：${topping.toppingStock}</span>
							<!-- 増減ボタンを追加 -->
							<div class="quantity-buttons">
								<button type="button" name="quantity" class="decrease-btn"
									id="decrement-t${topping.toppingId}">-</button>
								<span class="quantity" id="counter-t${topping.toppingId}"
									data-stock="${topping.toppingStock}"
									data-price="${topping.toppingPrice}"> ${empty toppingQuantity ? 0 : toppingQuantity}
								</span>
								<button type="button" name="quantity" class="increase-btn"
									id="increment-t${topping.toppingId}">+</button>
							</div></li>
					</c:forEach>
				</ul>
			</c:if>


			<!-- トッピングが空のときは空要素（または非表示） -->
			<c:if test="${empty toppingList}">
				<span></span>
			</c:if>
		</div>
		<div class="form">
			<form action="${screen}" method="get">
				<button class="form-button back">一覧へ戻る</button>
			</form>
			<button onclick="handleChangeButton('edit',${productId})"
				class="form-button change">変更</button>
		</div>
	</div>
	<!--ポップアップの背景-->
	<div class="popup-overlay" id="popup-overlay"></div>
	<!--ポップアップの内容-->
	<div class="popup-content" id="popup-content">
		<p>この商品を削除します</p>
		<p>よろしいですか？</p>
		<button class="popup-close" id="close-popup">いいえ</button>
		<!-- 注文を変更、削除 -->
		<form id="popup-form" method="post">
			<input type="hidden" name="order_id" value="${orderId}"> 
			<input type="hidden" name="order_price" id="popup-order-price"> 
			<input type="hidden" name="product_id" value="${productId}"> 
			<input type="hidden" name="product_quantity" id="popup-product-quantity">
			<input type="hidden" name="product_stock" id="popup-product-stock" value="${productStock}">
			<!-- JavaScript(showhidePopup)側でトッピング関連はinputにセットする -->
			<input type="hidden" name="screen" value="${screen}">
			<button type="submit" class="popup-proceed" id="confirm-button">は
				い</button>
		</form>
	</div>
	<script>
  // サーバーの配列をJSに渡す
  const toppingNames = [
    <c:forEach var="name" items="${toppingNames}">
      "${name}"<c:if test="${!status.last}">,</c:if>
    </c:forEach>
  ];

  const toppingQuantities = [
    <c:forEach var="qty" items="${toppingQuantities}">
      ${qty}<c:if test="${!status.last}">,</c:if>
    </c:forEach>
  ];
</script>
	<script
		src="<%=request.getContextPath()%>/JavaScript/Order/orderEdit.js"
		defer></script>
	<script
		src="<%=request.getContextPath()%>/JavaScript/Order/quantityControl.js"
		defer></script>
</body>
</html>