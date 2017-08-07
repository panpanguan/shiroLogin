<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<div id="demo"></div>
<script>
   /* var sessionTime=500;
    var intvalue=1;
    function start(){
        intvalue ++;
        document.getElementById("demo").innerHTML="&nbsp;" + Math.floor(((sessionTime-intvalue)/60)).toString()+"分"+((sessionTime-intvalue)%60).toString()+"秒";
        if(intvalue>=sessionTime){
            end();
        }
    }
    function end(){
        alert("时间到了！！");
        window.location.href="logout";
    }

    window.setInterval("start()",1000);*/
</script>
<head>
<title>Insert title here</title>
</head>
<body>
	<h4>List Page</h4>
	
	Welcome: <shiro:principal></shiro:principal>
	
	<shiro:hasRole name="admin">
	<br><br>
	<a href="loginAdmin">Admin Page</a>
	</shiro:hasRole>
	
	<shiro:hasRole name="user">
	<br><br>
	<a href="loginUser">User Page</a>
	</shiro:hasRole>
	
	<%--<br><br>
	<a href="shiro/testShiroAnnotation">Test ShiroAnnotation</a>--%>
	
	<br><br>
	<a href="logout">Logout</a>
	
</body>
</html>