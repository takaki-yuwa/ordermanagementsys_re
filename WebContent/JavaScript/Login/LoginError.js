/**
 * 
 */
document.getElementById('loginForm').addEventListener('submit',function(event){
	let valid=true;
	
	const userid=document.getElementById('userid');
	const password=document.getElementById('password');
	const useridError=document.getElementById('useridError');
	const passwordError=document.getElementById('passwordError');
	
	//初期化
	useridError.textContent='';
	passwordError.textContent='';
	
	//IDが空の場合
	if(userid.value.trim()===''){
		useridError.textContent='IDを入力してください';
		valid=false;
	}
	
	//パスワードが空の場合
	if(password.value.trim()===''){
		passwordError.textContent='パスワードを入力してください';
		valid=false;
	}
	
	//valid=falseの場合フォーム送信を無効にする
	if(!valid){
		event.preventDefault();
	}
});