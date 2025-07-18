/**
 * 
 *///タブコンテンツ
document.addEventListener('DOMContentLoaded', function () {
  const sharedTable = `
  <!-- リストが空でないか確認 -->
   <c:if test="${not empty historyinfo}">
	   <table>
	      <tr>
	        <th>注文番号</th>
	        <th>個数</th>
	        <th>商品</th>
	        <th>トッピング</th>
	        <th>卓番/提供</th>
	      </tr>
		  <!-- historyinfo リストをループして表示 -->
          <c:forEach var="order" items="${historyinfo}">
		  <tr>
		    <td>
			  <form action="OrderEdit">
			  	<button class="order_num">${order.tableNumber}</button>
			  </form>
		    </td>
		    <td>${order.productQuantity}個</td>
		    <td>${order.productName}</td>
		    <td class="topping">
			<!-- トッピングが複数ある場合、カンマで区切ってリスト表示 -->
               <c:if test="${not empty order.historyTopping}">
                   <c:set var="toppingString" value="" /> <!-- トッピングの文字列を格納する変数 -->
                   <c:forEach var="topping" items="${order.historyTopping}" varStatus="status">
                       <c:set var="toppingString" value="${toppingString}${topping.toppingName}×${topping.toppingQuantity}" />
                       <!-- 最後の要素でない場合にカンマを追加 -->
                       <c:if test="${!status.last}">
                           <c:set var="toppingString" value="${toppingString}, " />
                       </c:if>
                   </c:forEach>
                   <span>${toppingString}</span>
               </c:if>
               <c:if test="${empty order.historyTopping}">
                   <span>トッピングなし</span>
               </c:if>
		     </td>
			 <td>
				 <form action="UpdateFlag">
				 	<button class="order_flag">${order.tableNumber}卓<br>提供</button>
				 </form>
			 </td>
		  </tr>
	    </table>
	</c:if>
  `;

  // すべての .tab-content に共通のテーブルを表示
  document.querySelectorAll('.tab-content').forEach(el => {
    el.innerHTML = sharedTable;
  });

  // .content2 の中の 1卓 → 2卓 に変更
  const content2 = document.querySelector('.content2');
  if (content2) {
	const table = content2.querySelector('table');
    const secondRow = table.querySelectorAll('tr')[1]; // 2行目
    const fifthCell = secondRow.querySelectorAll('td')[4]; // 5列目
    fifthCell.innerHTML = '<button class="order_flag">2卓<br>提供</button>';
  }
});