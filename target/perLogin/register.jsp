<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<h4>Register Page</h4>
	<form action="shiro/addRegister" method="post">
		<table>
			<tr>
				<td>
					&nbsp;用户名:<input id="username" name="username">
				</td>
			</tr>
			<tr>
				<td>
					密&nbsp;&nbsp;&nbsp;&nbsp;码:<input id="pwd" name="pwd">
				</td>
			</tr>
			<tr>
				<td>
					昵&nbsp;&nbsp;&nbsp;&nbsp;称:<input id="nickName" name="nickName">
				</td>
			</tr>
			<tr>
				<td>
					角&nbsp;&nbsp;&nbsp;&nbsp;色:<input id="role" name="role">
				</td>
			</tr>

		</table>
		<input type="submit" value="Submit"/>
	</form>
</body>
</html>