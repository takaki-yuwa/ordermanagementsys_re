/**
 * 
 */
/*タブのカテゴリーに合った商品を表示する*/
document.addEventListener('DOMContentLoaded', () => {

	//全てのラジオボタンを取得
	const tabs = document.querySelectorAll('input[name="tab"]');

	//全ての注文一覧が入っている親要素を取得
	const orderBox = document.querySelector('.tab-contents');
	if (!orderBox) return;

	//サーバーから渡された以前選択されたカテゴリー名を取得
	const categoryFromServer = document.getElementById('initial-selected-category')?.value;

	//カテゴリー配列から該当するインデックスを取得(nullの場合「全て」をデフォルトにする)
	let selectedIndex = categoryList.indexOf(categoryFromServer || '全て');

	// 現在チェックされているラジオボタンのインデックスがあれば、そちらを優先する
	const selectedRadio = document.querySelector('input[name="tab"]:checked');
	if (selectedRadio) {
		selectedIndex = Array.from(tabs).indexOf(selectedRadio);
	}

	// 選択中のカテゴリ名を取得
	const selectedCategory = categoryList[selectedIndex] || '全て';

	//全てのフォームの hidden input に現在のカテゴリを設定
	document.querySelectorAll('form').forEach(form => {
		const hidden = form.querySelector('.selected-category');
		if (hidden) hidden.value = selectedCategory;
	});

	//「戻す」ボタン押下時にも、現在のカテゴリをセット
	document.querySelectorAll('.order_return').forEach(button => {
		button.addEventListener('click', function() {
			let selectedIndex = Array.from(tabs).findIndex(tab => tab.checked);
			const category = categoryList[selectedIndex] || '全て';
			// 対象のフォームにカテゴリをセット
			const form = this.closest('form');
			const hidden = form.querySelector('.selected-category');
			if (hidden) hidden.value = category;
		});
	});

	//初期フィルター表示
	const orders = orderBox.querySelectorAll('.order_row');
	orders.forEach(order => {
		const category = order.getAttribute('data-category');
		const table = order.getAttribute('data-table');
		order.classList.remove('hidden-row');
		const shouldShow =
			selectedCategory === '全て' ||
			category === selectedCategory ||
			table === selectedCategory;

		order.style.display = shouldShow ? '' : 'none';
	});

	updateCounts(orders);

	//タブ変更時のフィルター表示
	tabs.forEach((tab, index) => {
		tab.addEventListener('change', () => {
			const selectedCategory = categoryList[index];
			orders.forEach(order => {
				const category = order.getAttribute('data-category');
				const table = order.getAttribute('data-table');
				const shouldShow =
					selectedCategory === '全て' ||
					category === selectedCategory ||
					table === selectedCategory;

				order.style.display = shouldShow ? '' : 'none';
			});

			//タブ変更時にも hidden を更新
			document.querySelectorAll('.selected-category').forEach(input => {
				input.value = selectedCategory;
			});
			updateCounts(orders);
		});
	});
});


//カテゴリー別に件数をカウント
//カウント要素を取得
function updateCounts(orders) {
	const countSpans = document.querySelectorAll('.count');

	countSpans.forEach(span => {
		const targetLabel = span.getAttribute('data-for');
		let count = 0;

		orders.forEach(order => {
			const category = order.getAttribute('data-category');
			const table = order.getAttribute('data-table');

			if (
				targetLabel === '全て' ||
				category === targetLabel ||
				table === targetLabel
			) {
				count++;
			}
		});

		if (count === 0) {
			span.style.display = 'none';
		} else {
			span.textContent = `${count}`;
			span.style.display = '';
		}
	});
}

//経過時間
function updateButtonColors() {
	const buttons = document.querySelectorAll(".provide-btn");

	buttons.forEach(button => {
		const orderTimeStr = button.dataset.orderTime;
		const orderTime = new Date(orderTimeStr);
		const now = new Date();

		const elapsedMs = now - orderTime;
		const elapsedMin = Math.floor(elapsedMs / 60000); // ミリ秒→分

		// 一度クラスをリセット
		button.classList.remove("green", "orange", "red");

		// 経過時間に応じて色を設定
		if (elapsedMin < 5) {
			button.classList.add("green");
		} else if (elapsedMin < 10) {
			button.classList.add("orange");
		} else {
			button.classList.add("red");
		}

	});
}

// 初回実行
updateButtonColors();

// 1秒ごとに更新
setInterval(updateButtonColors, 1000);


// 新しい注文時間が追加されているかどうかを判定する関数
function getLatestOrderTime(orderList) {
	return orderList.reduce((latest, order) => {
		// 文字列としての比較が可能（"YYYY-MM-DD HH:mm:ss"形式）
		return order.order_time > latest ? order.order_time : latest;
	}, "");
}


function isOrderTimeIncreased(oldList, newList) {
	const oldLatest = getLatestOrderTime(oldList);
	const newLatest = getLatestOrderTime(newList);

	// 最新の注文時間が更新されていれば true（＝新しい注文が来た）
	return newLatest > oldLatest;
}

// ページ読み込み時に前回の注文リストを復元（なければ空配列）
let previousOrderList = JSON.parse(localStorage.getItem('previousOrderList') || '[]');

async function fetchOrderList() {
	try {
		const response = await fetch(contextPath + '/api/orderlist');
		if (!response.ok) throw new Error('ネットワークエラー');

		const newOrderList = await response.json();

		if (isOrderTimeIncreased(previousOrderList, newOrderList)) {
			console.log("新しい注文が追加されました → リロード");
			// localStorageに新しい注文リストを保存してからリロード
			localStorage.setItem('previousOrderList', JSON.stringify(newOrderList));
			location.reload();
			return;
		}

		// 変化がなければlocalStorageも更新
		previousOrderList = newOrderList;
		localStorage.setItem('previousOrderList', JSON.stringify(newOrderList));

	} catch (err) {
		console.error("注文データ取得に失敗", err);
	}
}

window.addEventListener('load', fetchOrderList);
setInterval(fetchOrderList, 1000);

