var toDoApp=angular.module('toDoApp');

toDoApp.factory('homeService',function($http){
	var notes={};
	
	/*******  Retrieve All Notes ********/
	notes.getNotes=function(token){
		return $http({
			method:'GET',
			url:'notesRetrieve',
			headers:{
				'token':token
			}
		});
	}
	
	/*******  Common Service ********/
	notes.service=function(url,method,note,token){
		console.log("inside delete Service common service");
		return $http({
			method:method,
			url:url,
			data:note,
			headers:{
				'token':token
			}
		});
	}
	
	/*******  Update Note ********/
	/*notes.updateNote=function(note){
		return $http({
			method:'PUT',
			url:'notesUpdate/' + note.id ,
			data:note
		});
	}*/
	
	
	/******** Home Service ********/
	/*notes.service=function(url,method,note,token){
		
		return $http({
			url:url,
			method:method,
			data:note,
			headers:{
				'token':token
			}
		});
	}*/
	
	/*******  Add Note ********//*
	notes.addNote=function(token,note){
		console.log(note);
		return $http({
			method:'POST',
			url:'notesCreate',
			data:note,
			headers:{
				'token':token
			}
		});
	}*/
	return notes;
});