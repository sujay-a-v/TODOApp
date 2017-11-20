var toDoApp=angular.module('toDoApp');

toDoApp.controller('setPasswordController',function($scope,setPasswordService,$state){
	$scope.setPassword=function(){
		var a=setPasswordService.setPassword($scope.user,$scope.error);
		a.then(function(response){
			console.log(response.data.message);
			console.log("set password");
			$state.go('login');
		},function(error){
			$scope.errorMessage=error.data.message;
			$state.go('/');
		});
	}
});