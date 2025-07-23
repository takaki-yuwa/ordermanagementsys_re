/**
 * 
 */
/*タブのカテゴリーに合った商品を表示する*/
document.addEventListener('DOMContentLoaded', () => {
	//全ての注文一覧が入っている親要素を取得
	const orderBox = document.querySelector('.tab-contents');
	//data-categoryを使って該当カテゴリーのみ表示する、それ以外はdisplay:noneにする
	const orders = orderBox.querySelectorAll('.order_row');
	//件数のカウント
	const countSpans = document.querySelectorAll('.count');
	//全てのラジオボタンを取得
	const tabs = document.querySelectorAll('input[name="tab"]');

	//カテゴリー別に件数をカウント
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


	//タブ変更で表示内容を切り替える
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
		});
	});
});
