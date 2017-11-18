var toDoApp=angular.module('toDoApp');

toDoApp.factory('loginService',function($http,$location){
	var x={};
	x.loginUser=function(user){
		console.log(user);
		return $http({
			method:"post",
			url:'login',
			data:user
		});
	}
	return x;
});