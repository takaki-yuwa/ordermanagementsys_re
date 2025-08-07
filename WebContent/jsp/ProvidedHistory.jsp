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
<!--.cssの呼び出し-->
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/Order/OrderList.css">
<link rel="stylesheet" href="css/Order/OrderListtab.css">
<title>注文管理システム--提供済み履歴--</title>
</head>
<script src="JavaScript/setTabContent.js"></script>
<body>
	<main>
		<!-- 選択されたカテゴリーを JavaScript に渡すための hidden input -->
		<input type="hidden" id="initial-selected-category"
			value="${selectedCategory}">
		<c:if test="${not empty orderinfo}">
			<div class="tab">
				<!-- ラジオボタン（表示制御のキーになる） -->
				<!-- 直前に押されていたボタンを呼び出す -->
				<c:forEach var="category" items="${categoryList}" varStatus="status">
					<input type="radio" name="tab" class="tab-item"
						id="tab${status.index}" ${status.index == 0 ? "checked" : ""}
						<c:if test="${category == selected_category}">checked</c:if>>
				</c:forEach>

				<div class="tab-wrapper">
					<!--ホームボタン-->
					<form action="Home" style="margin-top: 8px;">
						<input type="image" src="image/homeButton.png" alt="ホームボタン"
							class="homebutton">
					</form>
					<!-- ラベル（横スクロール） -->
					<div class="tab-labels">
						<c:forEach var="category" items="${categoryList}" varStatus="status">
							<label for="tab${status.index}">${category}</label>
						</c:forEach>
					</div>
				</div>
				<!-- タブの内容 -->
				<div class="tab-contents">
					<c:if test="${not empty orderinfo}">
						<table>
							<thead>
								<tr>
									<th>注文番号</th>
									<th>注文時間</th>
									<th>個数</th>
									<th>商品</th>
									<th>トッピング</th>
									<th>戻す</th>
								</tr>
							</thead>
							<!-- orderinfo リストをループして表示 -->
							<c:forEach var="order" items="${orderinfo}">
								<!-- data-tableに現在の卓番情報を保存 -->
								<tr class="order_row hidden-row"
									data-table="${order.tableNumber}卓">
									<td>
										<!--注文情報を注文編集へ送信-->
										<form action="OrderEditForm" method="post">
											<input type="hidden" name="screen" value="ProvidedHistory">
											<input type="hidden" name="order_id" value="${order.orderId}">
											<input type="hidden" name="table_number" value="${order.tableNumber}">
											<input type="hidden" name="product_id" value="${order.productId}">
											<input type="hidden" name="product_name" value="${order.productName}">
											<input type="hidden" name="product_price" value="${order.productPrice}">
											<input type="hidden" name="product_quantity" value="${order.productQuantity}">
											<input type="hidden" name="product_stock" value="${order.productStock}">
											<c:forEach var="topping" items="${order.orderTopping}">
											    <input type="hidden" name="topping_name[]" value="${topping.toppingName}">
											    <input type="hidden" name="topping_quantity[]" value="${topping.toppingQuantity}">
											    <input type="hidden" name="topping_stock[]" value="${topping.toppingStock}">
											</c:forEach>
											<button class="order_num">${order.orderId}</button>
										</form>
									</td>
									<td>${order.orderTime}</td>
									<td>${order.productQuantity}個</td>
									<td>${order.productName}</td>
									<td class="topping">
										<!-- トッピングが複数ある場合、カンマで区切ってリスト表示 -->
										<c:if test="${not empty order.orderTopping}">
											<ul class="list">
												<c:set var="toppingString" value="" />
												<!-- トッピングの文字列を格納する変数 -->

												<!-- トッピングのリストをループして処理 -->
												<c:forEach var="topping" items="${order.orderTopping}"
													varStatus="status">
													<!-- トッピングの数量が0でない場合にのみ処理 -->
													<c:if test="${topping.toppingQuantity > 0}">
														<li>${topping.toppingName}×${topping.toppingQuantity}</li>
														<!-- 各トッピングをリスト項目として表示 -->
													</c:if>
												</c:forEach>

												<!-- トッピングが1つ以上存在した場合に表示 -->
												<c:if test="${not empty toppingString}">
													<span>${toppingString}</span>
												</c:if>

												<!-- トッピングが全て数量0の場合は表示しない -->
												<c:if test="${empty toppingString}">
													<span></span>
												</c:if>
											</ul>
										</c:if> <!-- order.orderTopping が空の場合も表示しない -->
										<c:if test="${empty order.orderTopping}">
											<span></span>
										</c:if>

									</td>
									<td>
										<!--提供済みフラグの更新-->
										<form action="ProvidedHistory" method="post">
											<input type="hidden" name="order_id" value="${order.orderId}">
											<input type="hidden" name="order_flag" value="${order.orderFlag}">
											<input type="hidden" name="selected_category" class="selected-category">
											<button class="order_return">戻す</button>
										</form>
									</td>
								</tr>
							</c:forEach>
						</table>
					</c:if>
				</div>
			</div>
		</c:if>
		<!-- historyinfoが空のときはメッセージだけ表示 -->
		<c:if test="${empty orderinfo}">
			<!--ホームボタン-->
			<form action="Home" style="margin-left: 25px;">
				<input type="image" src="image/homeButton.png" alt="ホームボタン"
					class="homebutton">
			</form>
			<p class="no-orders">提供済みの商品はありません。</p>
		</c:if>
		<div class="fixed-row">
			<form action="OrderList" class="order_history-form" method="get">
				※注文番号を押下することで注文内容の変更・取り消しが可能です
				<button class="order_history">注文</button>
			</form>
		</div>
	</main>
	<!-- jsにjspのcategoryListを渡す -->
	<script>
		const categoryList=[
			<c:forEach items="${categoryList}" var="cat" varStatus="category_">
			'${cat}'<c:if test="${!category_.last}">,</c:if>
			</c:forEach>
			];
	</script>
	<script
		src="<%=request.getContextPath()%>/JavaScript/History/historyList.js"></script>
</body>
</html>