var toDoApp=angular.module('toDoApp');

toDoApp.controller('passwordController',function($scope,passwordService,$state){
	$scope.forgetPassword=function(){
		var a=passwordService.forgetPassword($scope.user);
		a.then(function(response){
			console.log(response.data.message);
			localStorage.setItem('token',response.data.message)
			$state.go('setPassword');
			console.log("link sent")
		});
	}
});