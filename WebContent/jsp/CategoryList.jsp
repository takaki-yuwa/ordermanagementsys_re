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
<title>注文管理システム--カテゴリ一覧--</title>
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/Category/categorylist.css">
<!-- ファビコン非表示 -->
<link rel="icon" href="data:," />
</head>
<body>
	<main>
		<div class="wrapper">
			<div class="btn-row">
				<!--ホームボタン-->
				<form action="Home">
					<input type="image"
						src="<%=request.getContextPath()%>/image/homeButton.png"
						alt="ホームボタン" class="homebutton">
				</form>
			</div>
		</div>
		<div class="contents">
			<table>
				<thead>
					<tr>
						<th>カテゴリー名</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="category" items="${categoryList}">
						<tr class="category-row">
							<td class="category-name">
								<c:out value="${category}" />
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</main>
</body>
</html>