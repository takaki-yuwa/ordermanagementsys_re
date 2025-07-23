/**
 * 
 */
/*タブのカテゴリーに合った商品を表示する*/
document.addEventListener('DOMContentLoaded', () => {
	//全てのラジオボタンを取得
	const tabs = document.querySelectorAll('input[name="tab"]');
	//全ての商品一覧が入っている親要素を取得
	const productBox = document.querySelector('.product-box');
	//サーバーから渡された以前選択されたカテゴリー名を取得
	const categoryFromServer = document.getElementById('initial-selected-category')?.value;
	//カテゴリー配列から該当するインデックスを取得(nullの場合「全て」をデフォルトにする)
	const selectedIndex = categoryList.indexOf(categoryFromServer || '全て');

	if (selectedIndex !== -1) {
		// 対応するタブを選択
		tabs[selectedIndex].checked = true;
	}

	// 初期フィルター実行
	const selectedCategory = categoryList[selectedIndex] || '全て';
	//data-categoryを使って該当カテゴリーのみ表示する、それ以外はdisplay:noneにする
	const products = productBox.querySelectorAll('.product-row');
	products.forEach(product => {
		const productCategory = product.getAttribute('data-category');
		product.classList.remove('hidden-row');
		product.style.display = (selectedCategory === '全て' || productCategory === selectedCategory)
			? ''
			: 'none';
	});

	// タブ変更時のイベント
	tabs.forEach((tab, index) => {
		tab.addEventListener('change', () => {
			const selectedCategory = categoryList[index];
			products.forEach(product => {
				const productCategory = product.getAttribute('data-category');
				product.style.display = (selectedCategory === '全て' || productCategory === selectedCategory)
					? ''
					: 'none';
			});
		});
	});
});