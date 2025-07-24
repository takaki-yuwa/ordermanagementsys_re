/**
 * 
 */
//ポップアップ処理
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

//ポップアップ非表示処理
function hidePopup() {
	document.getElementById('popup-overlay')?.classList.remove('show');
	document.getElementById('popup-content')?.classList.remove('show');
}

//実行ボタン処理
function confirmPopup() {
	const confirmButton = document.getElementById('confirm-button');
	if (confirmButton) {
		confirmButton.onclick = () => {
			const action = confirmButton.dataset.action;

			switch (action) {
				case 'setupConfirmHidePopup':
					setupConfirmHidePopup();
					break;
				case 'productDisplayHide':
					const productId = confirmButton.dataset.targetProductId;
					productDisplayHidePopup(productId);
					break;
				case 'toppingDisplayHide':
					const toppingId = confirmButton.dataset.targetToppingId;
					toppingDisplayHidePopup(toppingId);
					break;
				default:
			}
			hidePopup();
		};
	}

}

document.addEventListener('DOMContentLoaded', () => {
	confirmPopup();
});

//ポップアップを非表示にするだけ
function setupConfirmHidePopup() {
	hidePopup();
}

//商品一覧画面で使うポップアップ処理
function productDisplayHidePopup(productId) {
	const button = document.getElementById(productId);
	if (!button) return;
	
	const currentFlag=button.dataset.visibleFlag;
	
	if(currentFlag === '1'){
		button.textContent="非表示にする";
		button.classList.remove("btn-display");
		button.classList.add("btn-hide");
		button.dataset.visibleFlag="0";
	}else{
		button.textContent="表示にする";
		button.classList.remove("btn-hide");
		button.classList.add("btn-display");
		button.dataset.visibleFlag="1";
	}
}

//商品一覧画面で使うポップアップメッセージ処理
function openProductDisplayTogglePopup(productId,visibleFlag,productName){
	const popupProductName=document.getElementById('popup-product-name');
	const popupMessage = document.getElementById('popup-message');
	const confirmButton = document.getElementById('confirm-button');
	const popupProductId=document.getElementById('popup-product-id');
	const popupVisibleFlag=document.getElementById('popup-product-visible-flag');
	
	const selectedRadio=document.querySelector('input[name="tab"]:checked');
	const selectedIndex=Array.from(document.querySelectorAll('input[name="tab"]')).indexOf(selectedRadio);
	document.getElementById('popup-selected-category').value = categoryList[selectedIndex];
	
	if(popupProductName && productName != null){
		popupProductName.textContent=productName;
	}
	
	if (visibleFlag === '1') {
		popupMessage.innerHTML = 'この商品を<span style="color: red;">非表示</span>にします。';
	} else {
		popupMessage.innerHTML = 'この商品を<span style="color: blue;">表示</span>にします。';
	}

	confirmButton.dataset.action = 'productDisplayHide';
	confirmButton.dataset.targetProductId = `toggle-btn-${productId}`;
	
	//商品IDと表示フラグをhidden inputにセット
	popupProductId.value=productId;
	popupVisibleFlag.value=visibleFlag;

	showDisplayHidePopup();
}

//商品新規作成・編集画面で使うポップアップ処理
function openProductFormDisplayTogglePopup(){
 // 入力値の取得
  const category = document.getElementById('category_name').value;
  const productName = document.getElementById('product_name').value;
  const productPrice = document.getElementById('product_price').value;

  // チェック済みトッピングの取得
  const toppingCheckboxes = document.querySelectorAll('input[name="topping_id"]:checked');
  const toppingNames = Array.from(toppingCheckboxes).map(cb => cb.parentElement.textContent.trim());

  // ポップアップに値をセット
  document.getElementById('popup-category').textContent = category || '(未選択)';
  document.getElementById('popup-name').textContent = productName;
  // トッピングを1行3つずつ「・」付きで表示
  let toppingHtml = '';
  toppingNames.forEach((name, index) => {
  if (index % 3 === 0) {
    toppingHtml += '<div>'; // 新しい行
  }
  toppingHtml += `<span style="margin-right: 20px;">・${name}</span>`;
  if (index % 3 === 2 || index === toppingNames.length - 1) {
    toppingHtml += '</div>'; // 行を閉じる
  }
  });
  document.getElementById('popup-toppings').innerHTML = toppingNames.length > 0 ? toppingHtml : '(なし)';
  document.getElementById('popup-price').textContent = productPrice;

  // メッセージの切り替え
  const formButton = document.querySelector('.create-btn') ? 'ProductCreate' : 'ProductEdit';
  const actionMessage = formButton === 'ProductCreate'
    ? 'この内容で作成しますか？'
    : 'この内容で変更しますか？';

  document.getElementById('popup-action-message').textContent = actionMessage;

  // 表示
  showDisplayHidePopup();

  // 「はい」ボタンで form を submit
  const confirmButton = document.getElementById('confirm-button');
  confirmButton.dataset.action = 'setupConfirmHidePopup';
  confirmButton.onclick = function () {
    document.getElementById('productForm').submit();
  };
}

//トッピング一覧画面で使うポップアップ処理
function toppingDisplayHidePopup(toppingId) {
	const button = document.getElementById(toppingId);
	if (!button) return;
	
	const currentFlag=button.dataset.visibleFlag;
	
	if(currentFlag === '1'){
		button.textContent="非表示にする";
		button.classList.remove("btn-display");
		button.classList.add("btn-hide");
		button.dataset.visibleFlag="0";
	}else{
		button.textContent="表示にする";
		button.classList.remove("btn-hide");
		button.classList.add("btn-display");
		button.dataset.visibleFlag="1";
	}
}

//トッピング一覧画面で使うポップアップメッセージ処理
function openToppingDisplayTogglePopup(toppingId,visibleFlag,toppingName){
	const popupToppingName=document.getElementById('popup-topping-name');
	const popupMessage = document.getElementById('popup-message');
	const confirmButton = document.getElementById('confirm-button');
	const popupToppingId=document.getElementById('popup-topping-id');
	const popupVisibleFlag=document.getElementById('popup-topping-visible-flag');
	
	if(popupToppingName && toppingName != null){
		popupToppingName.textContent=toppingName;
	}

	if (visibleFlag === '1') {
		popupMessage.innerHTML = 'このトッピングを<span style="color: red;">非表示</span>にします。';
	} else {
		popupMessage.innerHTML = 'このトッピングを<span style="color: blue;">表示</span>にします。';
	}

	confirmButton.dataset.action = 'toppingDisplayHide';
	confirmButton.dataset.targetToppingId = `toggle-btn-${toppingId}`;


	//トッピングIDと表示フラグをhidden inputにセット
	popupToppingId.value=toppingId;
	popupVisibleFlag.value=visibleFlag;
	
	showDisplayHidePopup();
}