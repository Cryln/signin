<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>首页</title>
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
	<%
		if(request.getSession().getAttribute("isLogin")==null){
			request.getSession().setAttribute("isLogin","no");	
		}		
	%>
	<c:if test="${sessionScope.isLogin=='no' }">
		<a href="/signin/register.jsp">注册</a>&nbsp;
	</c:if>
		<c:choose>
			<c:when test="${sessionScope.isLogin=='yes' }">
					<a href="/signin/LogoutServlet">${sessionScope.uid } 注销</a>&nbsp;
			</c:when>
			<c:otherwise>
					<a href="/signin/login.jsp">登录</a>&nbsp;
			</c:otherwise>
		</c:choose>
		
	</div>
		<c:if test="${sessionScope.isLogin=='yes' }">
			<br><form action="/signin/RecordServlet" method="post" >
			体&emsp;&emsp;温:<input type="text" name="temper" id="temper"/>
	    	<br><br>
	    	<c:if test="${sessionScope.last_temper!=null }">
	    	上次体温：${sessionScope.last_temper }
	    	<br>
	    	</c:if>
	    	<c:if test="${sessionScope.last_time!=null }">
	    	上报时间：
	    	<br>${sessionScope.last_time }
	    	<br>
	    	</c:if>
	    	<br>
	    	<input type="submit" value="submit" onclick="return myfunction()" />
   		</form>
   		</c:if>
	</div>
</body> 
</html>