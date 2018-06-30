function login() {
	var username = document.getElementById('username').value;
	var password = document.getElementById('password').value;
	if (!username) {
		alert('请输入用户名');
		return;
	}
	if (!password) {
		alert('请输入密码');
		return;
	}
	var form = document.getElementById('login_form');
	form.submit();
}