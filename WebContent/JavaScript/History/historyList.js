/**
 * 
 */
/*タブのカテゴリーに合った商品を表示する*/
document.addEventListener('DOMContentLoaded', () => {
	//全てのラジオボタンを取得
	const tabs = document.querySelectorAll('input[name="tab"]');
	//全ての商品一覧が入っている親要素を取得
	const orderBox = document.querySelector('.tab-contents');
	//サーバーから渡された以前選択されたカテゴリー名を取得
	const categoryFromServer = document.getElementById('initial-selected-category')?.value;
	//カテゴリー配列から該当するインデックスを取得(nullの場合「全て」をデフォルトにする)
	const selectedIndex = categoryList.indexOf(categoryFromServer || '全て');
	
	const selectedRadio=document.querySelector('input[name="tab"]:checked');
	selectedIndex=Array.from(document.querySelectorAll('input[name="tab"]')).indexOf(selectedRadio);
	document.getElementById('popup-selected-category').value = categoryList[selectedIndex];

	if (selectedIndex !== -1) {
		// 対応するタブを選択
		tabs[selectedIndex].checked = true;
	}

	// 初期フィルター実行
	const selectedCategory = categoryList[selectedIndex] || '全て';
	//data-categoryを使って該当カテゴリーのみ表示する、それ以外はdisplay:noneにする
	const orders = orderBox.querySelectorAll('.order_row');
	orders.forEach(order => {
		const orderCategory = order.getAttribute('data-table');
		order.classList.remove('hidden-row');
		order.style.display = (selectedCategory === '全て' || orderCategory === selectedCategory)
			? ''
			: 'none';
	});

	// タブ変更時のイベント
	tabs.forEach((tab, index) => {
		tab.addEventListener('change', () => {
			const selectedCategory = categoryList[index];
			orders.forEach(order => {
				const orderCategory = order.getAttribute('data-table');
				order.style.display = (selectedCategory === '全て' || orderCategory === selectedCategory)
					? ''
					: 'none';
			});
		});
	});
});