var toDoApp=angular.module('toDoApp');

toDoApp.controller('homeController',function($scope,homeService,$state){
  

 console.log("inside home controller");

var getNotes=function(){
	var token=localStorage.getItem('token');
	
	var notes=homeService.getNotes(token);
	console.log(notes);
	notes.then(function(response){
		console.log(response.data);
		notes=response.data;
		$scope.notes=notes;
	},function(response){
		$scope.error=response.data.message;
	});
	
}

$scope.cresteNote=function(){
	$scope.note={};
	var token=localStorage.getItem('token');
	$scope.note.title=document.getElementById("title").innerHTML;
	$scope.note.description=document.getElementById("description").innerHTML;
	
	var notes=homeService.addNote(token,$scope.note);
	notes.then(function(response){
		getNotes();
	},function(response){
		getNotes();
		$scope.error=response.data.message;
	});
}
getNotes();
});