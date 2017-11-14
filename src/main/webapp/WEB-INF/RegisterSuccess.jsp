<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>RegisterSuccess</title>
<style type="text/css">
#reg{
align:center;
}
</style>
</head>
<body>
<div id="reg" align="center">
	<h2>RegisterSuccess</h2>
	<h2>Welcome ${userForm.userName}! You have Registered successfully.</h2>
	<br/><br/>
	<%-- <table>
		 <tr><td>Name:</td><td>${studentForm.studentName}</td> </tr>
		 <tr><td>Email:</td><td>${studentForm.studentEmail}</td> </tr>
		 <tr><td>Hobby:</td><td>${studentForm.studentHobby}</td> </tr>
		 <tr><td>Mobile:</td><td>${studentForm.studentMobile}</td> </tr>
	</table> --%>
	
	Name:${userForm.userName}<br>
	Email:${userForm.userEmail}<br>
	<%-- DOB:${studentForm.studentDOB} --%>
</div>
</body>
</html>