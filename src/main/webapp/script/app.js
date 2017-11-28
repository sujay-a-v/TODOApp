var toDoApp=angular.module('toDoApp',['ui.router','ngSanitize','ui.bootstrap','ui.bootstrap.datepicker']);

/*,'toastr'*/

toDoApp.config(['$stateProvider','$urlRouterProvider',function($stateProvider,$urlRouterProvider){
	$stateProvider.state('login',{
		url:'/login',
		templateUrl:'template/login.html',
		controller:'loginController'
	})
	.state('socialLogin',{
		url:'/socialLogin',
		templateUrl:'template/socialLogin.html',
		controller:'socialLoginController'
	})
	.state('register',{
		url:'/register',
		templateUrl:'template/register.html',
		controller:'registerController'
	})
	.state('user',{
		url:'/user',
		templateUrl:'template/user.html',
	})
	.state('home',{
		url:'/home',
		templateUrl:'template/home.html',
		controller:'homeController'
	})
	.state('forgetPassword',{
		url:'/forgetPassword',
		templateUrl:'template/forgetPassword.html',
		controller:'passwordController'
	})
	.state('setPassword',{
		url:'/setPassword',
		templateUrl:'template/setPassword.html',
		controller:'setPasswordController'
	})
	.state('Trash',{
		url:'/Trash',
		templateUrl:'template/Trash.html',
		controller:'homeController'
	})
	.state('Archive',{
		url:'/Archive',
		templateUrl:'template/Archive.html',
		controller:'homeController'
	});
	
	
	$urlRouterProvider.otherwise('login');
	}
	]);