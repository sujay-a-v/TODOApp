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
	
	/*******  Delete Note ********/
	notes.deleteNote=function(note){
		return $http({
			method:'DELETE',
			url:'notesDelete/' + note.id
		});
	}
	
	/*******  Update Note ********/
	notes.updateNote=function(note){
		return $http({
			method:'PUT',
			url:'notesUpdate/' + note.id ,
			data:note
		});
	}
	
	
	/*******  Add Note ********/
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