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
		$scope.deleteNote=function(note){
			var notes=homeService.deleteNote(note);
			notes.then(function(response){
				getNotes();
			},function(response){
				getNotes();
				$scope.error=response.data.messege;
			});
		}
		
		
		/******* PopUp **********/
		$scope.popup=function(note){
			
		}
		
		/***********  Update Note  **************/
		$scope.updateNote=function(note){
			console.log("inside update controller");
			var notes=homeService.updateNote(note);
			notes.then(function(respons){
				getNotes();
			},function(response){
				getNotes();
				$scope.error=response.data.message;
			});
		}
		
		
		
		
		$scope.colors=[/*"#fff","#f1c40f","#280275"*/
			
			{
				"color":'#ffffff',
				"path":'images/white.png'
			},
			{
				"color":'#e74c3c',
				"path":'images/red.png'
			},
			{
				"color":'#ff8c1a',
				"path":'images/orange.png'
			},
			{
				"color":'#fcff77',
				"path":'images/lightYellow.jpg'
			},
			{
				"color":'#80ff80',
				"path":'images/green.jpg'
			},
			{
				"color":'#99ffff',
				"path":'images/skyblue.png'
			},
			{
				"color":'#0099ff',
				"path":'images/blue.png'
			},
			{
				"color":'#1a53ff',
				"path":'images/darkBlue.png'
			},
			{
				"color":'#9966ff',
				"path":'images/purple.png'
			},
			{
				"color":'#ff99cc',
				"path":'images/pink.png'
			},
			{
				"color":'#d9b38c',
				"path":'images/brown.png'
			},
			{
				"color":'#bfbfbf',
				"path":'images/grey.png'
			}
		];
		
		
		if($state.current.name=="home"){
			$scope.navBarColor= "#ffbb33";
			$scope.navBarHeading="Google Keep";
		}
		else if($state.current.name=="reminder"){
			$scope.navBarColor="#607D8B"
			$scope.navBarHeading="Reminder";
		}
		else if($state.current.name=="trash"){
			$scope.navBarHeading="Trash";
			$scope.navBarColor="#636363"
		}
		else if($state.current.name=="archive"){
			$scope.navBarColor= "#607D8B";
			$scope.navBarHeading="Archive";
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