<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!--サイトのサイズ自動調整-->
<meta name="viewport"
	content="width=device-width,height=device-height,initial-scale=1.0">
<title>注文一覧</title>
<!--.cssの呼び出し-->
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/OrderList/OrderList.css">

<title>OrderList</title>
</head>
<script src="JavaScript/setTabContent.js"></script>
<!--<script src="JavaScript/OrderList.js"></script>-->
<body>
	<main>
		<div class="tab">
		  <!-- ラジオボタン（表示制御のキーになる） -->
		  <input type="radio" name="tab" class="tab-item" id="tab1" checked>
		  <input type="radio" name="tab" class="tab-item" id="tab2">
		  <input type="radio" name="tab" class="tab-item" id="tab3">
		  <input type="radio" name="tab" class="tab-item" id="tab4">
		  <input type="radio" name="tab" class="tab-item" id="tab5">
		  <input type="radio" name="tab" class="tab-item" id="tab6">
		  <input type="radio" name="tab" class="tab-item" id="tab7">
		  <input type="radio" name="tab" class="tab-item" id="tab8">
		  <input type="radio" name="tab" class="tab-item" id="tab9">
		  <input type="radio" name="tab" class="tab-item" id="tab10">
		  <input type="radio" name="tab" class="tab-item" id="tab11">
		  
		  <div class="tab-wrapper">
		　<!--ホームボタン-->
		  <form action="Home" style="margin-top: 8px;">
		  	<input type="image" src="<%= request.getContextPath() %>/image/homeButton.png" alt="ホームボタン" class="homebutton">
		　</form>
		  <!-- ラベル（横スクロール） -->
			  <div class="tab-labels">
			    <label for="tab1">全て</label><span class="circle">1</span>
			    <label for="tab2">お好み焼き</label>
			    <label for="tab3">もんじゃ焼き</label>
			    <label for="tab4">鉄板焼き</label>
			    <label for="tab5">一品料理</label>
			    <label for="tab6">ソフトドリンク</label>
			    <label for="tab7">お酒</label>
			    <label for="tab8">ボトル</label>
			    <label for="tab9">1卓</label>
			    <label for="tab10">2卓</label>
			    <label for="tab11">3卓</label>
			  </div>
		  </div>
		  <!-- タブの内容 -->
		  <div class="tab-contents">
		    <div class="tab-content content1">
		    <c:if test="${not empty orderinfo}">
			   <table>
			      <tr>
			        <th>注文番号</th>
			        <th>個数</th>
			        <th>商品</th>
			        <th>トッピング</th>
			        <th>卓番/提供</th>
			      </tr>
				  <!-- orderinfo リストをループして表示 -->
		          <c:forEach var="order" items="${orderinfo}">
					  <tr class="tr">
					    <td>
						  <form action="OrderEdit">
						  	<button class="order_num">${order.orderId}</button>
						  </form>
					    </td>
					    <td>${order.productQuantity}個</td>
					    <td>${order.productName}</td>
					    <td class="topping">
						<!-- トッピングが複数ある場合、カンマで区切ってリスト表示 -->
						<c:if test="${not empty order.orderTopping}">
						<ul class="list">
						   <c:set var="toppingString" value="" /> <!-- トッピングの文字列を格納する変数 -->
						   
						   <!-- トッピングのリストをループして処理 -->
						   <c:forEach var="topping" items="${order.orderTopping}" varStatus="status">
						       <!-- トッピングの数量が0でない場合にのみ処理 -->
					            <c:if test="${topping.toppingQuantity > 0}">
					                <li>${topping.toppingName}×${topping.toppingQuantity}</li> <!-- 各トッピングをリスト項目として表示 -->
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
						</c:if>
						
						<!-- order.orderTopping が空の場合も表示しない -->
						<c:if test="${empty order.orderTopping}">
						   <span></span>
						</c:if>

					     </td>
						 <td>
							 <form action="UpdateFlag">
							 	<button class="order_flag">${order.tableNumber}卓<br>提供</button>
							 </form>
						 </td>
					  </tr>
				  </c:forEach>
			    </table>
			</c:if>
		    </div>
		    <div class="tab-content content2"></div>
		    <div class="tab-content content3"></div>
		    <div class="tab-content content4"></div>
		    <div class="tab-content content5"></div>
		    <div class="tab-content content6"></div>
		    <div class="tab-content content7"></div>
		    <div class="tab-content content8"></div>
		    <div class="tab-content content9"></div>
		    <div class="tab-content content10"></div>
		    <div class="tab-content content11"></div>
		  </div>
		</div>
		<div class="fixed-row">
		  
		  <form action="ProvidedHistory" class="history-form">
		  	※注文番号を押下することで注文内容の変更・取り消しが可能です<button class="history">履歴</button>
		  </form>
		</div>
	</main>
</body>
</html>