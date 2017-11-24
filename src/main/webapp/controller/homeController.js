var toDoApp=angular.module('toDoApp');

toDoApp.controller('homeController',function($scope,homeService,$state){
	$scope.AddNoteBox=false;
	$scope.ShowAddNote=function(){
		$scope.AddNoteBox= true;
	}
  
	/*$scope.moreList=false;
	$scope.more=function(){
		$scope.moreList=true;
	}
*/
/**********	Getting All notes   ********/
var getNotes=function(){
	var token=localStorage.getItem('token');
	
	var notes=homeService.getNotes(token);
	notes.then(function(response){
		console.log(response.data);
		notes=response.data;
		$scope.notes=notes;
	},function(response){
		$scope.error=response.data;
	});
	
}

/************  Toggle side bar   ********/
$scope.toggleSideBar = function() {
	var width = $('#sideToggle').width();
	console.log(width);
	if (width == '250') {
		document.getElementById("sideToggle").style.width = "0px";
	} else {
		document.getElementById("sideToggle").style.width = "250px";
	}
}

/******************** Top Navigation bar heading        *******/
		if($state.current.name=="home"){
			$scope.navBarColor= "#ffbb33";
			$scope.topNavBarHeading="Fundoo Keep";
		}
		else if($state.current.name=="reminder"){
			$scope.navBarColor="#607D8B"
			$scope.topNavBarHeading="Reminder";
		}
		else if($state.current.name=="trash"){
			$scope.navBarHeading="Trash";
			$scope.topNavBarHeading="#636363"
		}
		else if($state.current.name=="archive"){
			$scope.navBarColor= "#607D8B";
			$scope.topNavBarHeading="Archive";
		}
		
		/**********  Delete Note  ***************/
		$scope.deleteNote=function(){
			$scope.note={};
			
		}

		/******  Adding Note  **************/
		$scope.addNote=function(){
			$scope.note={};
			var token=localStorage.getItem('token');
			$scope.note.title=document.getElementById("title").innerHTML;
			$scope.note.description=document.getElementById("description").innerHTML;
			
			var notes=homeService.addNote(token,$scope.note);
			notes.then(function(response){
				getNotes();
				document.getElementById("title").innerHTML="";
				document.getElementById("description").innerHTML="";
			},function(response){
				getNotes();
				$scope.error=response.data.message;
			});
		}
		getNotes();
		});