var toDoApp=angular.module('toDoApp');

toDoApp.controller('socialLoginController',function($scope,loginService,$state){
	
	var socialToken=loginService.getToken();
	socialToken.then(function(response){
		console.log(response.data.message);
		localStorage.setItem('token',response.data.message);
		console.log("login Success");
		$state.go('home');
	},function(error){
		$scope.errorMessage=error.data.message;
		$state.go('/');
	});
});