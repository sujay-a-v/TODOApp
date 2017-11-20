var toDoApp=angular.module('toDoApp');

toDoApp.factory('registerService',function($http){
	var register={};
	register.registerUser=function(user){
		return $http({
			method:"post",
			url:'register',
			data:user
		});
	}
	return register;
});