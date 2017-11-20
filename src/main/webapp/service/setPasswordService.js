var toDoApp=angular.module('toDoApp');

toDoApp.factory('setPasswordService',function($http){
	var password={};
	password.setPassword=function(user){
		console.log(user);
		return $http({
			method:'POST',
			url:'resetPassword',
			data:user
		});
	}
	return password;
});