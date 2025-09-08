/**
 * 
 */
/* -------------------- 共通ユーティリティ関数 -------------------- */

// HTMLエスケープ（XSS対策）
function escapeHtml(str) {
	if (!str) return '';
	return str.replace(/[&<>"']/g, match => ({
		'&': '&amp;',
		'<': '&lt;',
		'>': '&gt;',
		'"': '&quot;',
		"'": '&#39;',
	})[match]);
}

// 文字列の先頭を大文字に変換
function capitalize(str) {
	return str.charAt(0).toUpperCase() + str.slice(1);
}

// エラーメッセージ表示
function showError(id, message) {
	const span = document.getElementById(id);
	if (span) {
		span.textContent = message;
		span.classList.add('text-error');
	}
}

// エラー表示クリア
function clearErrors() {
	const errorIds = [
		'categoryNameError',
		'productNameError',
		'productPriceError',
		'toppingNameError',
		'toppingPriceError'
	];
	errorIds.forEach(id => {
		const el = document.getElementById(id);
		if (el) el.textContent = '';
	});
}

/* -------------------- ポップアップ全般制御 -------------------- */

// ポップアップ表示
function showDisplayHidePopup() {
	const popupOverlay = document.getElementById('popup-overlay');
	const popupContent = document.getElementById('popup-content');
	const closePopupButton = document.getElementById('close-popup');
	
	//ポップアップ表示
	popupOverlay.classList.add('show');
	popupContent.classList.add('show');
	
	//closeボタンが存在する場合のみイベント登録
	if (closePopupButton) {
		closePopupButton.onclick = () => hidePopup();
	}
}

// ポップアップ非表示
function hidePopup() {
	document.getElementById('popup-overlay')?.classList.remove('show');
	document.getElementById('popup-content')?.classList.remove('show');
}

// 実行ボタン処理
function confirmPopup() {
	const confirmButton = document.getElementById('confirm-button');
	if (!confirmButton) return;

	confirmButton.addEventListener('click', () => {
		const action = confirmButton.dataset.action;

		if (action === 'setupConfirmHidePopup') {
			setupConfirmHidePopup();
			return;
		}

		const idElement = document.querySelector('[id^="popup-"][id$="-id"]');
		if (!idElement) return;

		const type = idElement.id.includes('product') ? 'product' : 'topping';
		const id = document.getElementById(`popup-${type}-id`).value;

		if (action === 'delete') {
				const visibleFlagInput = document.getElementById(`popup-${type}-visible-flag`);
				if (visibleFlagInput) {
					visibleFlagInput.remove();
				}
			handleDeleteAction(type, id);

		} else if (action === 'toggle') {
			const visibleFlag = document.getElementById(`popup-${type}-visible-flag`).value;
			handleToggleAction(type, id, visibleFlag);
		}

		hidePopup();
	}, { once: true }); // 一度だけ実行されるようにする
}


document.addEventListener('DOMContentLoaded', () => {
	confirmPopup();
});

// ポップアップを非表示にするだけ
function setupConfirmHidePopup() {
	hidePopup();
}

/* -------------------- 商品・トッピング一覧画面向け -------------------- */

// ポップアップトリガー処理（削除・表示切り替え）
function handleCommonToggle(button) {
	const id = button.dataset.id;
	const visibleFlag = button.dataset.visibleFlag;
	const name = button.dataset.name;
	const type = button.dataset.type;
	const label = button.dataset.label;
	const action = button.dataset.action || 'toggle';

	openCommonDisplayTogglePopup(id, visibleFlag, name, type, label, action);
}

// //商品、トッピング一覧画面で使う共通ポップアップメッセージ処理（削除 or 表示切り替え）
function openCommonDisplayTogglePopup(id, visibleFlag, name, type, label, action) {
	const popupNameElement = document.getElementById(`popup-${type}-name`);
	const popupMessage = document.getElementById('popup-message');
	const confirmButton = document.getElementById('confirm-button');
	const popupIdInput = document.getElementById(`popup-${type}-id`);
	const popupFlagInput = document.getElementById(`popup-${type}-visible-flag`);
	
	// 商品の場合のみカテゴリをセット
	if (type === 'product') {
		const selectedRadio = document.querySelector('input[name="tab"]:checked');
		const selectedIndex = Array.from(document.querySelectorAll('input[name="tab"]')).indexOf(selectedRadio);
		document.getElementById('popup-selected-category').value = categoryList[selectedIndex];
	}
	
	// XSS対策：ユーザー入力をエスケープ
	if (popupNameElement && name != null) {
		popupNameElement.textContent = name;
	}

	// 表示・非表示か削除のメッセージ
	let messageHtml;
	if (action === 'delete') {
		messageHtml = `この${label}を<span style="color: red;">削除</span>します。`;
	} else {
		const actionColor = visibleFlag === '1' ? 'red' : 'blue';
		const actionText = visibleFlag === '1' ? '非表示' : '表示';
		messageHtml = `この${label}を<span style="color: ${actionColor};">${escapeHtml(actionText)}</span>にします。`;
	}
	popupMessage.innerHTML = messageHtml;

	// data属性の設定
	confirmButton.dataset.action = action;
	confirmButton.dataset[`target${type}Id`] = `${action === 'delete' ? 'delete-btn' : 'toggle-btn'}-${id}`;

	popupIdInput.value = id;
	if (popupFlagInput) popupFlagInput.value = visibleFlag || '';

	showDisplayHidePopup();
	confirmPopup(); // 新しいイベントを登録（再設定）
}

// 商品、トッピング一覧画面で使う共通ボタン切り替え処理
function commonDisplayHidePopup(buttonId) {
	const button = document.getElementById(buttonId);
	if (!button) return;

	const currentFlag = button.dataset.visibleFlag;
	const isVisible = currentFlag === '1';

	button.textContent = isVisible ? "非表示にする" : "表示にする";
	button.classList.toggle("btn-display", !isVisible);
	button.classList.toggle("btn-hide", isVisible);
	button.dataset.visibleFlag = isVisible ? "0" : "1";
}

/* -------------------- 商品・トッピング作成／編集画面向け -------------------- */

// 商品、トッピング新規作成・編集画面で使う共通トリガー処理
function handleCommonFormToggle(button) {
	const type = button.dataset.type;
	openCommonFormDisplayTogglePopup(type);
}

// 商品新規作成・編集画面で使うカテゴリーとトッピング表示用
function handleProductExtras(category, toppingNames) {
	document.getElementById('popup-category').textContent = category || '(未選択)';

	let toppingHtml = '';
	toppingNames.forEach((name, index) => {
		if (index % 3 === 0) toppingHtml += '<div>';
		toppingHtml += `<span style="margin-right: 20px;">・${escapeHtml(name)}</span>`;
		if (index % 3 === 2 || index === toppingNames.length - 1) toppingHtml += '</div>';
	});
	document.getElementById('popup-toppings').innerHTML = toppingNames.length > 0 ? toppingHtml : '(なし)';
}

// 商品・トッピング新規作成・編集画面で使う共通ポップアップ処理
function openCommonFormDisplayTogglePopup(type) {
	// エラー表示の初期化
	clearErrors();

	// 入力値の取得
	const name = document.getElementById(`${type}_name`).value.trim();
	const price = document.getElementById(`${type}_price`).value.trim();
	let hasError = false;

	// バリデーション（名称）
	if (!name) {
		showError(`${type}NameError`, '※入力してください');
		hasError = true;
	} else if (name.length > 18) {
		showError(`${type}NameError`, '※18文字以内で入力してください');
		hasError = true;
	}

	// バリデーション（金額）
	if (!price) {
		showError(`${type}PriceError`, '※入力してください');
		hasError = true;
	} else if (!/^[0-9]+$/.test(price)) {
		showError(`${type}PriceError`, '※半角数字で入力してください');
		hasError = true;
	} else if (price.length > 5) {
		showError(`${type}PriceError`, '※5桁以内で入力してください');
		hasError = true;
	}

	// 商品特有の処理（カテゴリとトッピング）
	if (type === 'product') {
		const category = document.getElementById('category_name').value.trim();
		if (!category) {
			showError('categoryNameError', '※選択してください');
			hasError = true;
		}

		const toppingCheckboxes = document.querySelectorAll('input[name="topping_id"]:checked');
		const toppingNames = Array.from(toppingCheckboxes).map(cb => cb.parentElement.textContent.trim());
		handleProductExtras(category, toppingNames);// → カテゴリ・トッピング名をポップアップにセット
	}

	// エラーがある場合はポップアップを表示しない
	if (hasError) return;

	// ポップアップに値をセット（XSS対策済み）
	document.getElementById('popup-name').textContent = name;
	document.getElementById('popup-price').textContent = price;

	// ポップアップのメッセージ切り替え（作成 or 編集）
	const formButton = document.querySelector('.create-btn') ? `${capitalize(type)}Create` : `${capitalize(type)}Edit`;
	const actionMessage = formButton === `${capitalize(type)}Create`
		? 'この内容で作成しますか？'
		: 'この内容で変更しますか？';

	document.getElementById('popup-action-message').textContent = actionMessage;

	// ポップアップを表示
	showDisplayHidePopup();

	// 「はい」ボタンの処理を設定（フォームを送信）
	const confirmButton = document.getElementById('confirm-button');
	confirmButton.dataset.action = 'setupConfirmHidePopup';
	confirmButton.onclick = function () {
		document.getElementById(`${type}Form`).submit();
	};
}
