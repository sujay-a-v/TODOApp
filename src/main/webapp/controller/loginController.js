var toDoApp=angular.module('toDoApp');

toDoApp.controller('loginController',function($scope,loginService,$state){
	$scope.loginUser=function(){
		var a=loginService.loginUser($scope.user,$scope.response,$scope.error);
		a.then(function(response){
			console.log(response.data.message);
			localStorage.setItem('token',response.data.message);
			console.log("login Success");
			$state.go('home');
		},function(error){
			$scope.errorMessage=error.data.message;
			$state.go('/');
		});
	}
	
});