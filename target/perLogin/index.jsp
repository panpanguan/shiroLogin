<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
    <title>Insert title here</title>
</head>
<script type="text/javascript">
    function kickout(){
        var href=location.href;
        if(href.indexOf("kickout")>0){
            alert("您的账号在另一台设备上登录，您被挤下线，若不是您本人操作，请立即修改密码！");
        }
    }
    kickout();
</script>
<body>
<a href="shiro/register">register</a>
<h4>Login Page</h4>
<form action="shiro/loginCheck" method="POST">
    username: <input type="text" name="username"/>
    <br><br>

    password: <input type="password" name="pwd"/>
    <br><br>

    <input type="submit" value="Submit"/>
</form>

</body>
</html>