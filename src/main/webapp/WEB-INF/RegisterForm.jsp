<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register Form</title>
	<style>
	    .error {
	        color: red; font-weight: bold;
	    }
	    #register{
	    align:center;
	    }
	</style>
</head>
<body>
	<div id="register" align="center">
		<table>
		<form:form action="Abc" commandName="userForm" method="post">
			<tr><td>Name : </td>
				<td><form:input path="userName" size="30"/> </td>
				<td><form:errors path="userName" cssClass="error"/></td>
			</tr>
			
			<tr><td>Mobile : </td>
				<td><form:input path="userMobile" size="30"/> </td>
				<td><form:errors path="userMobile" cssClass="error"/></td>
			</tr>
			<tr><td>Email : </td>
				<td><form:input path="userEmail" size="30"/> </td>
				<td><form:errors path="userEmail" cssClass="error"/></td>
			</tr>
			<tr><td>Password : </td>
				<td><form:input path="userPassword" type="password" size="30"/> </td>
				<td><form:errors path="userPassword" cssClass="error"/></td>
			</tr>
			<%-- <tr><td>DOB : </td>
				<td><form:input path="studentDOB" size="30"/> </td>
				<td><form:errors path="studentDOB" cssClass="error"/></td>
			</tr>
			<tr><td>Age : </td>
				<td><form:input path="studentAge" size="30"/> </td>
				<td><form:errors path="studentAge" cssClass="error"/></td>
			</tr> --%>
			<tr>
				<td><input type="submit" value="Register"/></td>
			</tr>
			
		</form:form>
		</table>
	</div>
</body>
</html>