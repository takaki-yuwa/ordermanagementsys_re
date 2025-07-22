/**
 * 
 */
/*タブのカテゴリーに合った商品を表示する*/
document.addEventListener('DOMContentLoaded', () => {
	const tabs = document.querySelectorAll('input[name="tab"]');
	const orderBox = document.querySelector('.tab-contents');

	tabs.forEach((tab, index) => {
		tab.addEventListener('change', () => {
			const selectedCategory = categoryList[index];

			const orders = orderBox.querySelectorAll('.order_row');
			orders.forEach(order => {
				const orderCategory = order.getAttribute('data-category');
				if (selectedCategory === '全て' || orderCategory === selectedCategory) {
					order.style.display = '';
				} else {
					order.style.display = 'none';
				}
			});
		});
	});
});