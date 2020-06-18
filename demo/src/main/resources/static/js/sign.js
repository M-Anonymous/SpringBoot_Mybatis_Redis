var flag = true
function load(){
	var name_c = document.getElementById("title")
	name = name_c.innerHTML.split("")
	name_c.innerHTML = ""
	for (i = 0; i < name.length; i++)
	    if (name[i] != ",")
	        name_c.innerHTML += "<i>" + name[i] + "</i>"
	}

function sendcode() {
	var phone = document.getElementById("phone")
	$.ajax({
		url: "/sendsms",
		type: "post",
		data: {"phone":phone.value}
	})
}

function hint_msg(){
	var hint = document.getElementById("hint")
	setTimeout(function(){hint.style.display = "block"},0)
    setTimeout(function(){hint.style.opacity = 1},0)
    setTimeout(function(){hint.style.opacity = 0},1000)
    setTimeout(function(){hint.style.display = "none"},2000)
}

function signin() {
	var hint = document.getElementById("hint")
	var form = document.getElementById("sign")
	var sign_div = document.getElementById("sign_div")
	var username = document.getElementById("username")
	var password = document.getElementById("password")
	var status = document.getElementById("status").getElementsByTagName("i")
    if (flag) {
    	if(!/^[A-Za-z0-9]+$/.test(username.value)){
    		hint.innerHTML = "账号只能为英文和数字"
    	}else if(username.value.length < 6){
    		hint.innerHTML = "账号长度必须不小于6位"
    	}else if(password.value.length < 6){
    		hint.innerHTML = "密码长度必须不小于6位"
    	}else{
    		$.ajax({
    			type:'post',
    			url:'/signin',
    			data:{
    				username:username.value,
    				password:password.value
    			},
    			success:function(response){
    				hint.innerHTML = response.result
    				if(response.result == "登录成功"){
    					window.location.href='/info'
    				}
    			}
    		})
    	}
    	hint_msg()
    }else{
    	sign_div.style.height = 0
	    status[1].style.top = 0
	    status[2].style.top = 35 + "px"
	    flag = !flag
    	}
    }

function signup() {
	var hint = document.getElementById("hint")
	var sign_div = document.getElementById("sign_div")
	var username = document.getElementById("username")
	var password = document.getElementById("password")
	var confirm_pwd = document.getElementById("confirm_pwd")
	var phone = document.getElementById("phone")
	var verifycode = document.getElementById("verifycode")
	var status = document.getElementById("status").getElementsByTagName("i")

	if(flag){
		sign_div.style.height = 150 + "px"
	    sign_div.style.width = 300 + "px"
	    status[1].style.top = 35 + "px"
	    status[2].style.top = 0
	    flag = !flag;
		}else{
			if(!/^[A-Za-z0-9]+$/.test(username.value)){
				hint.innerHTML = "账号只能为英文和数字"
			}else if(username.value.length < 6){
				hint.innerHTML = "账号长度必须不小于6位"
			}else if(password.value.length < 6){
				hint.innerHTML = "密码长度必须不小于6位"
			}else if(password.value != confirm_pwd.value){
				hint.innerHTML = "两次密码不相等"
			}else {
				$.ajax({
	    			type:'post',
	    			url:'/signup',
	    			data:{
	    				verifycode:verifycode.value,
	    				username:username.value,
	    				password:password.value,
	    				phone:phone.value
	    			},
	    			success:function(response){
	    				hint.innerHTML = response.result
	    			}
	    		})
			}
			hint_msg()
		}
    }
