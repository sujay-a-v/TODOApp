<html>
<script  src="bower_components/angular/angular.js" type="text/javascript"></script>
<script  src="bower_components/angular-animate/angular-animate.js" type="text/javascript"></script>
<script  src="bower_components/angular-ui-router/release/angular-ui-router.js" type="text/javascript"></script>
<script  src="bower_components/angular-aria/angular-aria.js" type="text/javascript"></script>

<script src="script/app.js" type="text/javascript"></script>

<script src="controller/loginController.js" type="text/javascript"></script>
<script src="controller/passwordController.js" type="text/javascript"></script>

<script src="service/loginService.js" type="text/javascript"></script>
<script src="service/passwordService.js" type="text/javascript"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!-- <link rel="stylesheet" type="text/css" href="styleCSS/login.css" /> -->
<body ng-app="toDoApp">
	<div layout="row" ui-view></div>
</body>
</html>
