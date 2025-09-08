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
<title>注文管理システム--商品一覧--</title>
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
					<c:if test="${category_.index == 0 || category == selectedCategory}">checked</c:if>>
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
					<c:forEach var="category" items="${categoryList}" varStatus="category_">
						<label for="tab${category_.index + 1}">
							<c:out value="${category}" />
						</label>
					</c:forEach>
				</div>
			</div>
			<div class="tab-contents">
				<table>
					<thead>
						<tr>
							<th>商品名</th>
							<th>価格</th>
							<th>編集</th>
							<th>表示切替</th>
							<th>削除</th>
						</tr>
					</thead>
					<tbody>
						<!-- productInfo リストをループして表示 -->
						<c:forEach var="product" items="${productInfo}">
							<!-- data-categoryに現在のカテゴリー情報を保存 -->
							<tr class="product-row hidden-row"
								data-category="${product.category}">
							<td class="product-name"><c:out value="${product.name}" /></td>
							<td class="product-price" style="border-left: 1px solid #e4e4e4;"><c:out value="${product.price}" />円</td>
							<td style="border-left: 1px solid #e4e4e4;">
							<!-- 編集ボタンで商品新規作成・編集画面へ遷移する -->
								<form action="ProductEditForm" method="post">
									<input type="hidden" name="form" value="ProductEdit">
									<input type="hidden" name="product_id"
										value="<c:out value='${product.id}'/>"> <input
										type="hidden" name="product_name"
										value="<c:out value='${product.name}' />"> <input
										type="hidden" name="category_name"
										value="<c:out value='${product.category}'/>"> <input
										type="hidden" name="product_price"
										value="<c:out value='${product.price}'/>">
									<button class="btn-edit">編集</button>
								</form>
							</td>
							<td style="border-left: 1px solid #e4e4e4;">
								${product.visible_flag == 1 ? '表示中：' : '非表示：'}
								<!-- 表示フラグが1のとき：非表示にするボタン -->
								<label class="switch" type="button"
								class="btn-toggle ${product.visible_flag == 1 ? 'btn-hide' : 'btn-display'}"
								id="toggle-btn-${product.id}"
								data-id="<c:out value='${product.id}'/>"
								data-visible-flag="<c:out value='${product.visible_flag}'/>"
								data-name="<c:out value='${product.name}'/>"
								data-type="product"
								data-label="商品"
								data-action="toggle"
								onclick="handleCommonToggle(this)">
								<input type="checkbox" ${product.visible_flag == 1 ? 'checked' : ''} disabled>
								<span class="slider"></span>
								</label>
							</td>
							<td style="border-left: 1px solid #e4e4e4;">
								<button class="btn-trash"
								id="toggle-btn-${product.id}"
								data-id="<c:out value='${product.id}'/>"
								data-name="<c:out value='${product.name}'/>"
								data-type="product"
								data-label="商品"
								data-action="delete"
								onclick="handleCommonToggle(this)">
								削除</button>
							</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
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
		<button class="popup-close" id="close-popup">いいえ</button>
		<form action="ProductList" method="post">
			<input type="hidden" name="product_id" id="popup-product-id">
			<input type="hidden" name="product_visible_flag"
				id="popup-product-visible-flag"> <input type="hidden"
				name="selected_category" id="popup-selected-category">
			<button type="submit" class="popup-proceed" id="confirm-button"
				data-action="" data-target-id="">は い</button>
		</form>
	</div>

	<!-- jsにjspのcategoryListを渡す -->
	<script>
		const categoryList=[
			<c:forEach items="${categoryList}" var="cat" varStatus="category_">
			'<c:out value="${cat}"/>'<c:if test="${!category_.last}">,</c:if>
			</c:forEach>
			];
	</script>

	<!-- .jsの呼び出し -->
	<script
		src="<%=request.getContextPath()%>/JavaScript/Product/productList.js"></script>
	<script src="JavaScript/Popup.js"></script>
	<script src="JavaScript/setTabContent.js"></script>
</body>
</html>