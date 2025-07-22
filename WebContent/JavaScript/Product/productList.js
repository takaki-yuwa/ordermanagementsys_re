/**
 * 
 */
/*タブのカテゴリーに合った商品を表示する*/
document.addEventListener('DOMContentLoaded', () => {
	const tabs = document.querySelectorAll('input[name="tab"]');
	const productBox = document.querySelector('.product-box');

	tabs.forEach((tab, index) => {
		tab.addEventListener('change', () => {
			const selectedCategory = categoryList[index];

			const products = productBox.querySelectorAll('.product-row');
			products.forEach(product => {
				const productCategory = product.getAttribute('data-category');
				if (selectedCategory === '全て' || productCategory === selectedCategory) {
					product.style.display = '';
				} else {
					product.style.display = 'none';
				}
			});
		});
	});
});