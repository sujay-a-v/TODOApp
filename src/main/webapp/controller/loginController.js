var toDoApp=angular.module('toDoApp');

toDoApp.controller('loginController',function($scope,loginService,$state){
	$scope.loginUser=function(){
		var a=loginService.loginUser($scope.user);
		a.then(function(response){
			console.log(response.data.message);
			localStorage.setItem('token',response.data.message);
			$state.go('home');
			console.log("login Success")
		});
	}
});