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
<title>注文管理システム--注文編集--</title>
</head>
<body>
	<!--ホームボタン-->
	<form action="Home">
		<input type="image" src="image/homeButton.png" alt="ホームボタン"
			class="homebutton">
	</form>
	<div class="box">
		<div class="underline">
			<c:out value="${orderId}" />番<span class="table"><c:out value="${tableNumber}" />卓</span>
		</div>
		<div class="underline">
			<span class="item-name"><c:out value="${productName}" /></span>
			<!--在庫確認用-->
			<span style="display: none;" class="stock" id="stock-p<c:out value='${productId}' />">：<c:out value="${productStock}" /></span>
			<!-- 増減ボタンを追加 -->
			<div class="quantity-buttons">
				<button type="button" name="quantity" class="decrease-btn" id="decrement-p${productId}">-</button>
				<!-- 数量を表示する要素、変数にバインド -->
				<span class="quantity" id="counter-p<c:out value='${productId}' />"
					data-stock="<c:out value='${productStock}' />"
					data-price="<c:out value='${productPrice}' />"
					data-quantity="<c:out value='${productQuantity}' />">
					<c:out value="${productQuantity}" />
				</span>
				<button type="button" name="quantity" class="increase-btn" id="increment-p<c:out value='${productId}' />">+</button>
			</div>
			<div class="form-delet">
				<button onclick="handleChangeButton('delete','<c:out value='${productId}' />')" class="form-button delete">注文取り消し</button>
			</div>
		</div>
		<div class="list-padding">
			<!-- トッピングが1つ以上ある場合だけ表示 -->
			<c:if test="${not empty toppingList}">
				<ul class="list">
					<!-- トッピングのリストをループして表示 -->
					<c:forEach var="topping" items="${toppingList}" varStatus="status">
						<li class="menu-item"
						data-topping-id="${topping.toppingId}">
						<span class="topping-name"><c:out value="${topping.toppingName}" /></span>
						<span style="display: none; class="stock" id="stock-t<c:out value='${topping.toppingId}' />">：<c:out value="${topping.toppingStock}" /></span>
							<!-- 増減ボタンを追加 -->
							<div class="quantity-buttons">
								<button type="button" name="quantity" class="decrease-btn" id="decrement-t<c:out value='${topping.toppingId}' />">-</button>
								<span class="quantity" id="counter-t<c:out value='${topping.toppingId}' />"
									data-stock="<c:out value='${topping.toppingStock}' />"
									data-price="<c:out value='${topping.toppingPrice}' />">
									<c:out value="${empty toppingQuantity ? 0 : toppingQuantity}" />
								</span>
								<button type="button" name="quantity" class="increase-btn" id="increment-t<c:out value='${topping.toppingId}' />">+</button>
							</div>
						</li>
					</c:forEach>
				</ul>
			</c:if>
		</div>
			<!-- トッピングが空のときは空要素（または非表示） -->
			<c:if test="${empty toppingList}">
				<span style="margin-left: 8px;">選択可能なトッピングがありません。</span>
			</c:if>
		<div class="form">
			<form action="<c:out value='${screen}' />" method="get">
				<button class="form-button back">一覧へ戻る</button>
			</form>
			<button onclick="handleChangeButton('edit','<c:out value='${productId}' />')" class="form-button change">変更</button>
		</div>
	</div>
	<!--ポップアップの背景-->
	<div class="popup-overlay" id="popup-overlay"></div>
	<!--ポップアップの内容-->
	<div class="popup-content" id="popup-content">
		<p>この注文を削除します</p>
		<p>よろしいですか？</p>
		<button class="popup-close" id="close-popup">いいえ</button>
		<!-- 注文を変更、削除 -->
		<form id="popup-form" method="post">
			<input type="hidden" name="order_id" value="<c:out value='${orderId}' />">
			<input type="hidden" name="order_price" id="popup-order-price"> 
			<input type="hidden" name="product_id" value="<c:out value='${productId}' />">
			<input type="hidden" name="product_quantity" id="popup-product-quantity">
			<input type="hidden" name="product_stock" id="popup-product-stock" value="<c:out value='${productStock}' />">
			<!-- JavaScript(showhidePopup)側でトッピング関連はinputにセットする -->
			<input type="hidden" name="screen" value="<c:out value='${screen}' />">
			<button type="submit" class="popup-proceed" id="confirm-button">はい</button>
		</form>
	</div>
	<script>
  // サーバーの配列をJSに渡す
  const toppingIds = [
    <c:forEach var="id" items="${toppingIds}">
      "<c:out value='${id}' />"<c:if test="${!status.last}">,</c:if>
    </c:forEach>
  ];
  const toppingQuantities = [
    <c:forEach var="qty" items="${toppingQuantities}">
    	<c:out value='${qty}' /><c:if test="${!status.last}">,</c:if>
    </c:forEach>
  ];
</script>
	<script
		src="<%=request.getContextPath()%>/JavaScript/Order/orderEdit.js"
		defer></script>
</body>
</html>