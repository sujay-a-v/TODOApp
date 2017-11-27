var toDoApp=angular.module('toDoApp');

toDoApp.controller('homeController',function($scope,homeService ,$state, $uibModal){
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

$scope.showSideBar = true;
/*$scope.toggleSideBar = function() {
	if($scope.showSideBar){
		$scope.showSideBar=false;
		document.getElementById("sideToggle").style.paddingLeft = "200px";
	}
	else{
		$scope.showSideBar = true;
		document.getElementById("sideToggle").style.paddingLeft = "70px";
	}
}*/


$scope.toggleSideBar = function() {
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
			note.deleteStatus = "false";/******* PopUp **********/
			$scope.popup=function(note){
				
			}
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
			modalInstance.close('resetmodel');
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
		
		
		/*********** Open a model *************/
		$scope.open = function (note) {
		$scope.note = note;
		modalInstance = $uibModal.open({
			templateUrl: 'template/UpdateNote.html',
			scope : $scope
			});
		};
		
		/***********  Update Note  **************/
		$scope.updateNote=function(note){
			console.log("inside update controller   " + note);
			console.log("inside update controller SSSSSSSSSS  " + note);
			var url = 'update/' + note.id;
			var method = 'POST';
			var token = localStorage.getItem('token');
			var notes=homeService.service(url,method,note,token);
			notes.then(function(respons){
				getNotes();
			},function(response){
				getNotes();
				$scope.error=response.data.message;
			});
		}
		
		/***********  Edit Note  **************/
		$scope.editNote=function(note){
			console.log("inside update controller   " + note);
			note.title=document.getElementById("title").innerHTML;
			note.description=document.getElementById("description").innerHTML;
			modalInstance.close('resetmodel');
			var url = 'update/' + note.id;
			var method = 'POST';
			var token = localStorage.getItem('token');
			var notes=homeService.service(url,method,note,token);
			notes.then(function(respons){
				getNotes();
			},function(response){
				getNotes();
				$scope.error=response.data.message;
			});
		}
		
		/****************  Note Color *************/
		$scope.AddNoteColor="#ffffff";
		
		$scope.addNoteColorChange=function(color){
			$scope.AddNoteColor=color;
		}
		
		$scope.colors=[
			
			{
				"color":'#ffffff',
				"path":'images/white.png'
			},
			{
				"color":'#e74c3c',
				"path":'images/Red.png'
			},
			{
				"color":'#ff8c1a',
				"path":'images/orange.png'
			},
			{
				"color":'#fcff77',
				"path":'images/yellow.png'
			},
			{
				"color":'#80ff80',
				"path":'images/green.png'
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
				"path":'images/darkblue.png'
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
		
		/**********  Copy Note  ***************/
		$scope.copy=function(note){
			note.pin = "true";
			note.noteStatus = "true";
			note.reminderStatus= "true";
			note.archiveStatus= "false";
			note.deleteStatus = "false";
			var url = 'notesCreate';
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
			$scope.note.noteColor=$scope.AddNoteColor;
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