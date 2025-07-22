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
		popupMessage.innerHTML = 'この商品を<span style="color: red;">非表示</span>にしますか？';
	} else {
		popupMessage.innerHTML = 'この商品を<span style="color: blue;">表示</span>にしますか？';
	}

	confirmButton.dataset.action = 'productDisplayHide';
	confirmButton.dataset.targetProductId = `toggle-btn-${productId}`;
	
	//商品IDと表示フラグをhidden inputにセット
	popupProductId.value=productId;
	popupVisibleFlag.value=visibleFlag;

	showDisplayHidePopup();
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
		popupMessage.innerHTML = 'このトッピングを<span style="color: red;">非表示</span>にしますか？';
	} else {
		popupMessage.innerHTML = 'このトッピングを<span style="color: blue;">表示</span>にしますか？';
	}

	confirmButton.dataset.action = 'toppingDisplayHide';
	confirmButton.dataset.targetToppingId = `toggle-btn-${toppingId}`;


	//トッピングIDと表示フラグをhidden inputにセット
	popupToppingId.value=toppingId;
	popupVisibleFlag.value=visibleFlag;
	
	showDisplayHidePopup();
}