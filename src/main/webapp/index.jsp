<html>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	
	<link rel="stylesheet" href="styleCSS/TopNavBar.css">
	<link rel="stylesheet" href="styleCSS/SideNavBar.css">
	<link rel="stylesheet" href="styleCSS/homePage.css">
	<link rel="stylesheet" href="styleCSS/AddNote.css">
	
	<!-- <link rel="stylesheet" href="styleCSS/login.css"> -->

<body ng-app="toDoApp">
	<div ui-view></div>
</body>


<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.6/angular.min.js"
	type="text/javascript"></script>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-router/1.0.3/angular-ui-router.min.js"
	type="text/javascript" type="text/javascript"></script>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js" type="text/javascript"></script>

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" type="text/javascript"></script>

<script
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.8/angular-sanitize.js" type="text/javascript"></script>

<!-- <script  src="bower_components/angular/angular.js" type="text/javascript"></script>
<script  src="bower_components/angular-animate/angular-animate.js" type="text/javascript"></script>
<script  src="bower_components/angular-ui-router/release/angular-ui-router.js" type="text/javascript"></script>
<script  src="bower_components/angular-aria/angular-aria.js" type="text/javascript"></script> -->

<script src="script/app.js" type="text/javascript"></script>

<script src="controller/loginController.js" type="text/javascript"></script>
<script src="controller/passwordController.js" type="text/javascript"></script>
<script src="controller/setPasswordController.js" type="text/javascript"></script>
<script src="controller/registerController.js" type="text/javascript"></script>
<script src="controller/homeController.js" type="text/javascript"></script>
<script src="directives/homeDirective.js" type="text/javascript"></script>

<script src="service/loginService.js" type="text/javascript"></script>
<script src="service/passwordService.js" type="text/javascript"></script>
<script src="service/setPasswordService.js" type="text/javascript"></script>
<script src="service/registerService.js" type="text/javascript"></script>
<script src="service/homeService.js" type="text/javascript"></script>
</html>
