var toDoApp=angular.module('toDoApp');

toDoApp.factory('passwordService',function($http,$location){
	var x={}
	x.forgetPassword=function(user){
		console.log(user);
		return $http({
			method:"post",
			url:'forgetPassword',
			data:user
		});
	}
	return x;
});