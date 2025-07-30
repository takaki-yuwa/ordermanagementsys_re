/**
 * 
 */
//totalをグローバルに設定
let total = 0;

document.addEventListener('DOMContentLoaded', () => {
  // すべての増減ボタンを取得
  const increaseButtons = document.querySelectorAll('.increase-btn');
  const decreaseButtons = document.querySelectorAll('.decrease-btn');

  increaseButtons.forEach(button => {
    button.addEventListener('click', () => {
      const idPart = getIdPart(button.id);
      const counter = document.getElementById(`counter-${idPart}`);
      let count = parseInt(counter.textContent, 10);
	  //data-stock 取得
      const maxStock = parseInt(counter.dataset.stock, 10);
	  //カウントを1増やして表示に反映
      if (count < maxStock) {
        count++;
        counter.textContent = count;
		
		// 在庫を1減らす
        const stockSpan = document.getElementById(`stock-${idPart}`);
        let stock = parseInt(stockSpan.textContent.replace('：', ''), 10);
        stockSpan.textContent = `：${stock - 1}`;
		
		updateTotalPrice();
      }
    });
  });

  decreaseButtons.forEach(button => {
    button.addEventListener('click', () => {
      const idPart = getIdPart(button.id);
	  const counter = document.getElementById(`counter-${idPart}`);
	  let count = parseInt(counter.textContent, 10);

	  // null や "" や NaN 対策：0に初期化
	  if (isNaN(count)) {
	    count = 0;
	  }
	  
	  const isProduct = idPart.startsWith('p');

      // product は最小1、topping は最小0
      const min = isProduct ? 1 : 0;

	  //カウントが0以上の時カウントを1減らして表示に反映
      if (count > min) {
        count--;
        counter.textContent = count;
		
		// 在庫を1増やす
        const stockSpan = document.getElementById(`stock-${idPart}`);
        let stock = parseInt(stockSpan.textContent.replace('：', ''), 10);
        stockSpan.textContent = `：${stock + 1}`;

		updateTotalPrice();
      }
    });
  });

  //どのボタンが押されたかの判断
  function getIdPart(id) {
    return id.split('-')[1];
  }
  

//合計の算出
 function updateTotalPrice() {
    // 商品の価格と数量
    const productCounter = document.querySelector('[id^="counter-p"]');
    const productQuantity = parseInt(productCounter.textContent, 10);
    const productPrice = parseInt(productCounter.dataset.price, 10);

    // トッピングの価格と数量
    const toppingCounters = document.querySelectorAll('[id^="counter-t"]');
    let toppingTotal = 0;

    toppingCounters.forEach(counter => {
      const quantity = parseInt(counter.textContent, 10) || 0;
      const price = parseInt(counter.dataset.price, 10) || 0;
      toppingTotal += price * quantity;
    });

    total = (productPrice + toppingTotal) * productQuantity;

    // 合計表示を更新
    const totalElement = document.getElementById('total-price');
    totalElement.textContent = `${total.toLocaleString()}`;
	
  }

  // 初期表示のときも実行
  updateTotalPrice();
});


//ポップアップ処理
function showhidePopup(actionType, options = {}) {
	
  // ポップアップ表示
  document.getElementById("popup-overlay").style.display = "block";
  document.getElementById("popup-content").style.display = "block";

  // メッセージの切り替え
  const popupText = document.querySelectorAll("#popup-content p");

  const popupForm = document.getElementById("popup-form");
  popupForm.reset(); // 前回の値をクリア

  if (actionType === "OrderDetailsDelete") {
    popupText[0].textContent = "この商品を削除します";
    popupText[1].textContent = "よろしいですか？";

	popupForm.action = actionType;
	document.getElementById("popup-product-stock").value = options.product_stock || '';
	document.getElementById("popup-topping-stock").value = options.topping_stock || '';

	  
	} else if (actionType === "OrderDetailsEdit") {
    popupText[0].textContent = "この商品の内容を変更します";
    popupText[1].textContent = "よろしいですか？";

    popupForm.action = actionType;
	document.getElementById("popup-order-price").value = total;
    document.getElementById("popup-product-quantity").value = options.product_quantity || '';
    document.getElementById("popup-topping-quantity").value = options.topping_quantity || '';
    document.getElementById("popup-product-stock").value = options.product_stock || '';
    document.getElementById("popup-topping-stock").value = options.topping_stock || '';
  }
}

document.addEventListener("DOMContentLoaded", () => {
  document.getElementById("close-popup").addEventListener("click", () => {
    document.getElementById("popup-overlay").style.display = "none";
    document.getElementById("popup-content").style.display = "none";
  });
});

