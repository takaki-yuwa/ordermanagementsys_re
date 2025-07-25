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
<title>履歴</title>
</head>
<script src="JavaScript/setTabContent.js"></script>
<body>
	<main>
		<!-- 選択されたカテゴリーを JavaScript に渡すための hidden input -->
		<input type="hidden" id="initial-selected-category"
			value="${selectedCategory}">
		<c:if test="${not empty historyinfo}">
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
						<c:forEach var="category" items="${categoryList}"
							varStatus="status">
							<label for="tab${status.index}">${category}</label>
						</c:forEach>
					</div>
				</div>
				<!-- タブの内容 -->
				<div class="tab-contents">
					<c:if test="${not empty historyinfo}">
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
							<!-- historyinfo リストをループして表示 -->
							<c:forEach var="order" items="${historyinfo}">
								<!-- data-tableに現在の卓番情報を保存 -->
								<tr class="order_row hidden-row"
									data-table="${order.tableNumber}卓">
									<td>
										<form action="OrderEditForm" method="post">
											<button class="order_num">${order.orderId}</button>
										</form>
									</td>
									<td>${order.orderTime}</td>
									<td>${order.productQuantity}個</td>
									<td>${order.productName}</td>
									<td class="topping">
										<!-- トッピングが複数ある場合、カンマで区切ってリスト表示 --> <c:if
											test="${not empty order.historyTopping}">
											<ul class="list">
												<c:set var="toppingString" value="" />
												<!-- トッピングの文字列を格納する変数 -->

												<!-- トッピングのリストをループして処理 -->
												<c:forEach var="topping" items="${order.historyTopping}"
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
										</c:if> <!-- order.orderTopping が空の場合も表示しない --> <c:if
											test="${empty order.historyTopping}">
											<span></span>
										</c:if>

									</td>
									<td>
										<!--提供済みフラグの更新-->
										<form action="ProvidedHistory" method="post">
											<input type="hidden" name="order_id" value="${order.orderId}">
											<input type="hidden" name="order_flag"
												value="${order.orderFlag}"> <input type="hidden"
												name="selected_category" class="selected-category">
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
		<c:if test="${empty historyinfo}">
			<!--ホームボタン-->
			<form action="Home" style="margin-left: 25px;">
				<input type="image" src="image/homeButton.png" alt="ホームボタン"
					class="homebutton">
			</form>
			<p class="no-orders">注文がありません。</p>
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