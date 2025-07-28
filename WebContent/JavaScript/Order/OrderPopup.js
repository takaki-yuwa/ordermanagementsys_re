/**
 * 
 */
//ポップアップ処理
function showhidePopup(actionType, orderId, options = {}) {
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
    document.getElementById("popup-order-id").value = orderId;

  } else if (actionType === "OrderDetailsEdit") {
    popupText[0].textContent = "この商品の内容を変更します";
    popupText[1].textContent = "よろしいですか？";

    popupForm.action = actionType;
    document.getElementById("popup-order-price").value = options.order_price || '';
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
