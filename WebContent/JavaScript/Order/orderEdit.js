/**
 * 
 */
document.addEventListener('DOMContentLoaded', () => {
  // toppingNames と toppingQuantities はサーバーから受け取った配列

  // .topping-name の要素一覧を取得
  const toppingElements = document.querySelectorAll('.topping-name');

  toppingElements.forEach(el => {
    const name = el.textContent.trim();

    // サーバーから受け取った名前と一致するか検索
    const index = toppingNames.indexOf(name);
    if (index !== -1) {
      // 一致したら数量表示の要素を取得し数量をセット
      const quantityEl = el.parentElement.querySelector('.quantity');
      if (quantityEl) {
        quantityEl.textContent = toppingQuantities[index];
      }
    }
  });
});
