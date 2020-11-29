<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>登录</title>
<style type="text/css">
		.divForm{
			position: absolute;
			width: 200px;
			height: 200px;
			border: 1px solid black;
			text-align: center;
			top: 50%;
			left: 50%;
			margin-top: -200px;
			margin-left: -150px;
			}
		.divLink{
			float:right
		}
	</style>
    	
</head>
<body>
	<div class="divForm">
	<div class="divLink">
	登录 <br>    	
    	
    	<script type="text/javascript">
    		function myfunction(){
    			if(document.getElementById("uid").value==''){
    				alert("id不能为空");
    				return false;    				
    			}
    			if(document.getElementById("password").value==''){
    				alert("密码不能为空");
    				return false;    				
    			}		
    			return true;
    		}
    	</script>

    	<form action="/signin/LoginServlet" method="post" >
	    	用户名:<input type="text" name="uid"  id="uid"/><br>
	    	密&nbsp;&nbsp;&nbsp;码:<input type="password" name="password" id="password"/><br><br>
	    	<input type="submit" value="登录" onclick="return myfunction()" />
   		</form>
    </div></div>
</body>
</html>