var toDoApp=angular.module('toDoApp');

toDoApp.controller('registerController',function($scope,$state,registerService){
	$scope.registerUser=function(){
		var a=registerService.registerUser($scope.user,$scope.response,$scope.error);
		a.then(function(response){
			console.log("Register success");
			$state.go('user');
		},function(error){
			$scope.errorMessage=error.data.message;
			$state.go('/');
		});
	}
});