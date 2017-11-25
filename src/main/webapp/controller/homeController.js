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
	console.log('hello');
	var width = $('#sideToggle').width();
	if (width == '250') {
		document.getElementById("sideToggle").style.width = "0px";
	} else {
		document.getElementById("sideToggle").style.width = "250px";
	}
}

/******************** Top Navigation bar heading        *******/
$scope.showSideBar=true;
			if($state.current.name=="home"){
				$scope.navBarColor= "#ffbb33";
				$scope.navBarHeading="Fundoo Keep";
			}
			else if($state.current.name=="reminder"){
				$scope.navBarColor="#607D8B"
				$scope.navBarHeading="Reminder";
			}
			else if($state.current.name=="Trash"){
				$scope.navBarHeading="Trash";
				$scope.navBarColor="#636363"
			}
			else if($state.current.name=="Archive"){
				$scope.navBarColor= "#607D8B";
				$scope.navBarHeading="Archive";
			}
		
		/**********  Delete Note  ***************/
		$scope.deleteNote=function(note){
			note.deleteStatus = "true";
			note.pin="false";
			note.reminderStatus="false";
			var url = 'update/' + note.id;
			var method = 'POST';
			var token = localStorage.getItem('token');
			var notes=homeService.service(url,method,note,token);
			notes.then(function(response){
				getNotes();
			},function(response){
				getNotes();
				$scope.error=response.data;
			});
		}
		
		/**********  Delete forever Note  ***************/
		$scope.deleteNoteForever=function(note){
			var url = 'noteDelete/'+ note.id;
			var method = 'DELETE';
			var token = localStorage.getItem('token');
			var notes=homeService.service(url,method,note,token);
			notes.then(function(response){
				getNotes();
			},function(response){
				getNotes();
				$scope.error=response.data;
			});
		}
		
		/**********  Restore Note  ***************/
		$scope.restoreNote=function(note){
			note.deleteStatus = "false";
			note.pin="false";
			var url = 'update/' + note.id;
			var method = 'POST';
			var token = localStorage.getItem('token');
			var notes=homeService.service(url,method,note,token);
			notes.then(function(response){
				getNotes();
			},function(response){
				getNotes();
				$scope.error=response.data;
			});
		}
		
		/**********  Archive Note  ***************/
		$scope.archiveNote=function(note){
			note.archiveStatus= "true";
			$scope.note.pin = "false";
			$scope.note.noteStatus = "false";
			var url = 'update/' + note.id;
			var method = 'POST';
			var token = localStorage.getItem('token');
			var notes=homeService.service(url,method,note,token);
			notes.then(function(response){
				getNotes();
			},function(response){
				getNotes();
				$scope.error=response.data;
			});
		}
		
		
		/**********  Unarchive Note  ***************/
		$scope.unarchiveNote=function(note){
			note.archiveStatus = "false";
			note.pin="false";
			var url = 'update/' + note.id;
			var method = 'POST';
			var token = localStorage.getItem('token');
			var notes=homeService.service(url,method,note,token);
			notes.then(function(response){
				getNotes();
			},function(response){
				getNotes();
				$scope.error=response.data;
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
		
		
		/******* Pin  *****************/ 
		$scope.pinStatus =false;
		
		$scope.pinUnpin = function() {
				if($scope.pinStatus == false){
				$scope.pinStatus = true;
			}
			else {
				$scope.pinStatus=false;
			}
		}
		
		
		/*******  Add notes to trash  *****************/ 
		$scope.deleteNote1=function(note){
			note.pin="false";
			note.deleteStatus="true";
			note.reminderStatus="false";
			console.log("new Delete");
			var url = 'deleteTrash/'+ note.id;
			var method = 'POST';
			var token = localStorage.getItem('token');
			
			var a = homeService.service(url,method,token,note);
			a.then(function(response) {
				getAllNotes();
			}, function(response) {
			});
		}
		

		/******  Adding Note  **************/
		
		$scope.addNote=function(){
			$scope.note={};
			var token=localStorage.getItem('token');
			$scope.note.title=document.getElementById("title").innerHTML;
			$scope.note.description=document.getElementById("description").innerHTML;
			$scope.note.pin = "true";
			$scope.note.noteStatus = "true";
			$scope.note.reminderStatus= "true";
			$scope.note.archiveStatus= "false";
			$scope.note.deleteStatus = "false";
			/*$scope.note.noteColor=$scope.AddNoteColor;*/
			var note=$scope.note;
			
			var url = 'notesCreate';
			var method = 'POST';
			
			var notes=homeService.service(url,method,note,token);
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