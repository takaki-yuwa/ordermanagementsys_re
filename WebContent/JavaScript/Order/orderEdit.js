/**
 * 
 */
//totalをグローバルに設定
let total = 0;

//商品についてるトッピングの個数を反映
document.addEventListener('DOMContentLoaded', () => {
	// toppingNames と toppingQuantities はサーバーから受け取った配列

	// .topping-name の要素一覧を取得
	const toppingElements = document.querySelectorAll('.menu-item');

	toppingElements.forEach(el => {
		const id = el.dataset.toppingId;

		// サーバーから受け取った名前と一致するか検索
		const index = toppingIds.indexOf(id);
		if (index !== -1) {
			// 一致したら数量表示の要素を取得し数量をセット
			const quantityEl = el.querySelector('.quantity');
			if (quantityEl) {
				quantityEl.textContent = toppingQuantities[index];
			}
		}
	});
});

//ボタンの活性非活性
function changeButton(count, min, max, minusBtn, plusBtn) {
	if (minusBtn) {
		if (count == min) {
			minusBtn.setAttribute('disabled', 'true');
		} else {
			minusBtn.removeAttribute('disabled');
		}
	} else {
		console.warn("minusBtn が見つかりません");
	}

	if (plusBtn) {
		if (count == max) {
			plusBtn.classList.add('disabled');
			plusBtn.setAttribute('disabled', 'true');
		} else {
			plusBtn.classList.remove('disabled');
			plusBtn.removeAttribute('disabled');
		}
	} else {
		console.warn("plusBtn が見つかりません");
	}
}

document.addEventListener('DOMContentLoaded', () => {
	// すべての増減ボタンを取得
	const increaseButtons = document.querySelectorAll('.increase-btn');
	const decreaseButtons = document.querySelectorAll('.decrease-btn');

	increaseButtons.forEach(button => {
		button.addEventListener('click', function() {
			const idPart = getIdPart(this.id);
			const counter = document.getElementById(`counter-${idPart}`);
			let count = parseInt(counter.textContent, 10);
			//data-stock 取得
			const maxStock = parseInt(counter.dataset.stock, 10);
			//カウントを1増やして表示に反映
			if (0 < maxStock) {
				count++;
				counter.textContent = count;
				//data-stockを更新する
				counter.dataset.stock = maxStock - 1;
				// 在庫を1減らす
				const stockSpan = document.getElementById(`stock-${idPart}`);
				let stock = parseInt(stockSpan.textContent.replace('：', ''), 10);
				stockSpan.textContent = `：${stock - 1}`;

				updateTotalPrice();

				const plusBtn = document.getElementById(`increment-${idPart}`);
				const minusBtn = document.getElementById(`decrement-${idPart}`);
				const isProduct = idPart.startsWith('p');
				const min = isProduct ? 1 : 0;
				const max = parseInt(counter.dataset.stock, 10) + count;
				changeButton(count, min, max, minusBtn, plusBtn);

			}
		});
	});


	decreaseButtons.forEach(function(button) {
		button.addEventListener('click', function() {
			const idPart = getIdPart(this.id);
			const counter = document.getElementById(`counter-${idPart}`);
			let count = parseInt(counter.textContent, 10);
			//data-stock 取得
			const maxStock = parseInt(counter.dataset.stock, 10);
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
				//data-stockを更新する
				counter.dataset.stock = maxStock + 1;
				// 在庫を1増やす
				const stockSpan = document.getElementById(`stock-${idPart}`);
				let stock = parseInt(stockSpan.textContent.replace('：', ''), 10);
				stockSpan.textContent = `：${stock + 1}`;

				updateTotalPrice();

				const plusBtn = document.getElementById(`increment-${idPart}`);
				const minusBtn = document.getElementById(`decrement-${idPart}`);
				const max = parseInt(counter.dataset.stock, 10) + count;
				changeButton(count, min, max, minusBtn, plusBtn);

			}
		});
	});

	// ページの読み込み時にボタンの見た目を初期化
	document.querySelectorAll('.increase-btn').forEach(button => {
		const idPart = getIdPart(button.id);
		const counter = document.getElementById(`counter-${idPart}`);
		if (!counter) return;

		const count = parseInt(counter.textContent, 10) || 0;
		const stock = parseInt(counter.dataset.stock, 10) || 0;

		const plusBtn = document.getElementById(`increment-${idPart}`);
		const minusBtn = document.getElementById(`decrement-${idPart}`);

		const isProduct = idPart.startsWith('p');
		const min = isProduct ? 1 : 0;
		const max = count + stock;

		changeButton(count, min, max, minusBtn, plusBtn);
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

	}

	// 初期表示のときも実行
	updateTotalPrice();
});

// 変更ボタンを押した時に呼び出される関数
function handleChangeButton(type, productId) {
	const productCounter = document.getElementById(`counter-p${productId}`);
	const productQuantity = parseInt(productCounter.textContent, 10);
	const productStock = parseInt(productCounter.dataset.stock, 10);

	const toppingIds = [];
	const toppingQuantities = [];
	const toppingStocks = [];

	// トッピング数量と在庫を取得
	const toppingSpans = document.querySelectorAll('[id^="counter-t"]');
	toppingSpans.forEach(span => {
		const toppingId = span.id.replace("counter-t", "");
		toppingIds.push(toppingId);
		toppingQuantities.push(parseInt(span.textContent, 10));
		toppingStocks.push(parseInt(span.dataset.stock, 10));
	});

	if (type === 'edit') {
		// 編集用ポップアップを表示
		showhidePopup("OrderDetailsEdit", {
			product_quantity: productQuantity,
			topping_quantity: toppingQuantities,
			product_stock: productStock,
			topping_stock: toppingStocks,
			topping_id: toppingIds,
		});
	} else if (type === 'delete') {
		// 削除用ポップアップを表示
		showhidePopup("OrderDetailsDelete", {
			product_quantity: productQuantity,
			topping_quantity: toppingQuantities,
			product_stock: productStock,
			topping_stock: toppingStocks,
			topping_id: toppingIds,
		});
	}
}


//ポップアップ処理
function showhidePopup(actionType, options = {}) {

	// ポップアップ表示
	document.getElementById("popup-overlay").style.display = "block";
	document.getElementById("popup-content").style.display = "block";

	// メッセージの切り替え
	const popupText = document.querySelectorAll("#popup-content p");

	const popupForm = document.getElementById("popup-form");
	popupForm.reset(); // 前回の値をクリア

	// 既存のtopping系のhidden inputをすべて削除(重複送信を防ぐため)
	popupForm.querySelectorAll('input[name="topping_id[]"], input[name="topping_quantity[]"], input[name="topping_stock[]"]').forEach(el => el.remove());

	// topping_id[]を追加
	if (Array.isArray(options.topping_id)) {
		options.topping_id.forEach(id => {
			const input = document.createElement('input');
			input.type = 'hidden';
			input.name = 'topping_id[]';
			input.value = id;
			popupForm.appendChild(input);
		});
	}

	// topping_quantity[]を追加
	if (Array.isArray(options.topping_quantity)) {
		options.topping_quantity.forEach(quantity => {
			const input = document.createElement('input');
			input.type = 'hidden';
			input.name = 'topping_quantity[]';
			input.value = quantity;
			popupForm.appendChild(input);
		});
	}

	// topping_stock[]を追加
	if (Array.isArray(options.topping_stock)) {
		options.topping_stock.forEach(stock => {
			const input = document.createElement('input');
			input.type = 'hidden';
			input.name = 'topping_stock[]';
			input.value = stock;
			popupForm.appendChild(input);
		});
	}

	if (actionType === "OrderDetailsDelete") {
		popupText[0].textContent = "この商品を削除します";
		popupText[1].textContent = "よろしいですか？";

		popupForm.action = actionType;
		document.getElementById("popup-order-price").value = total;
		document.getElementById("popup-product-quantity").value = options.product_quantity || '';
		document.getElementById("popup-product-stock").value = options.product_stock || '';



	} else if (actionType === "OrderDetailsEdit") {
		popupText[0].textContent = "この商品の内容を変更します";
		popupText[1].textContent = "よろしいですか？";

		popupForm.action = actionType;
		document.getElementById("popup-order-price").value = total;
		document.getElementById("popup-product-quantity").value = options.product_quantity || '';
		document.getElementById("popup-product-stock").value = options.product_stock || '';
	}
}

document.addEventListener("DOMContentLoaded", () => {
	document.getElementById("close-popup").addEventListener("click", () => {
		document.getElementById("popup-overlay").style.display = "none";
		document.getElementById("popup-content").style.display = "none";
	});
});

