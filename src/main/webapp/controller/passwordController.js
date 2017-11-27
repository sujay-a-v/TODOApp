var toDoApp=angular.module('toDoApp');

toDoApp.controller('passwordController',function($scope,passwordService,$state){
	$scope.forgetPassword=function(){
		var a=passwordService.forgetPassword($scope.user,$scope.error);
		a.then(function(response){
			localStorage.setItem('token',response.data.message);
			$state.go('user');
		},function(error){
			$scope.errorMessage=error.data.message;
			$state.go('/');
		});
	}
});