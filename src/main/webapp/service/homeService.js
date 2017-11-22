var toDoApp=angular.module('toDoApp');

toDoApp.factory('homeService',function($http){
	var notes={};
	notes.getNotes=function(token){
		console.log("inside service   token is "+token);
		return $http({
			method:'GET',
			url:'notesRetrieve',
			headers:{
				'token':token
			}
		});
	}
	
	
	notes.addNote=function(token,note){
		return $http({
			method:'POST',
			url:'notesCreate',
			data:note,
			headers:{
				'token':token
			}
		});
	}
	return notes;
});