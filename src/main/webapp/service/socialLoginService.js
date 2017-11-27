var toDoApp=angular.module('toDoApp');

toDoApp.factory('socialLoginService',function($http,$location){
	var x={};
	
	x.getToken=function(){
		return $http({
			method:"get",
			url:'getToken'
		});
	}
	return x;
});