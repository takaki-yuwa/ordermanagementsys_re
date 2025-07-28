/**
 * 
 */
document.addEventListener('DOMContentLoaded', () => {
  // すべての増減ボタンを取得
  const increaseButtons = document.querySelectorAll('.increase-btn');
  const decreaseButtons = document.querySelectorAll('.decrease-btn');

  increaseButtons.forEach(button => {
    button.addEventListener('click', () => {
      const idPart = getIdPart(button.id); // "p123" か "t456"
      const counter = document.getElementById(`counter-${idPart}`);
      let count = parseInt(counter.textContent, 10);
	  //data-stock 取得
      const maxStock = parseInt(counter.dataset.stock, 10);
	  //カウントを1増やして表示に反映
      if (count < maxStock) {
        count++;
        counter.textContent = count;
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

	  //カウントが0以上の時カウントを1減らして表示に反映
      if (count > 0) {
        count--;
        counter.textContent = count;
      }
    });
  });

  //どのボタンが押されたかの判断
  function getIdPart(id) {
    return id.split('-')[1];
  }
});
