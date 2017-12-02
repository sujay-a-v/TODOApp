var toDoApp=angular.module('toDoApp');

toDoApp.controller('homeController',function($scope,homeService ,$state, $uibModal, $interval,$filter){
	$scope.AddNoteBox=false;
	$scope.ShowAddNote=function(){
		$scope.AddNoteBox= true;
	}
  
	/*, fileReader*/
	
	/*,toastr*/
	
	/*$scope.moreList=false;
	$scope.more=function(){
		$scope.moreList=true;
	}
*/
/**********	Getting All notes   ********/
var getNotes=function(){
	var token=localStorage.getItem('token');
	console.log("inside get User ")
	var notes=homeService.getNotes(token);
	notes.then(function(response){
		console.log(response.data);
		notes=response.data;
		$scope.notes=notes;
	},function(response){
		$scope.logout();
	});	
}


/****** Get current user ********/
getUser();
function getUser(){
	var token=localStorage.getItem('token');
		var user=homeService.getCurrentUser(token);
		user.then(function(response){
			$scope.User=response.data;
			$scope.ListView=response.data.listView;
		},function(response){
			$scope.logout();
		});
	}
	
	/****** List and Grid *******/

	/*$scope.ListView = true;*/
$scope.ListView=localStorage.getItem('LISTGRID');
	$scope.ListViewToggle = function() {
		
		if ($scope.ListView == true) {
			$scope.ListView = false;
			localStorage.setItem('LISTGRID',$scope.ListView);
			var notes = document.getElementsByClassName('card');
			for (var i = 0; i < notes.length; i++) {
				notes[i].style.width = "800px";
			}
		}
		else {
			$scope.ListView = true;
			localStorage.setItem('LISTGRID',$scope.ListView);
			var notes = document.getElementsByClassName('card');
			for (var i = 0; i < notes.length; i++) {
				notes[i].style.width = "250px";
			}
		}
	}
	
	
	/*console.log("Grid list ");
	$scope.user.listView=ListView;
	var url = 'listAndGrid';
	var method = 'POST';
	var token = localStorage.getItem('token');
	var user=$scope.user;
	console.log(user);
	var notes=homeService.service(url,method,user,token);
	notes.then(function(response){
		getNotes();
	},function(response){
		getNotes();
		$scope.error=response.data;
	});*/

	
	/******** Image Upload **********/
	
	/*$scope.uploadFile=function(noteOrUser){
		$scope.noteOrUser=noteOrUser;
		$('#imageuploader').trigger('click');
	}
	
	$scope.$on("fileProgress", function(e, progress) {
		$scope.progress = progress.loaded / progress.total;
	});
	
	$scope.noteOrUser={};
	$scope.noteOrUser.image='';
	$scope.$watch('imageSrc',function(newImage,oldImage){
		if($scope.imageSrc!='')
			{
				if($scope.noteOrUser==='input'){
					$scope.addimg=$scope.imageSrc;
				}
				else if($scope.noteOrUser==='user'){
					console.log("inside User Profile");
					
				}
				else{
					console.log("inside note image add");
					$scope.noteOrUser.image = $scope.imageSrc;
					console.log("inside note image added");
					$scope.updateNote($scope.type);
				}
			}
	})*/

/************  Toggle side bar   ********/
$scope.showSideBar = false;
$scope.toggleSideBar = function() {
	if($scope.showSideBar){
		$scope.showSideBar=false;
		document.getElementById("sideToggle").style.paddingLeft = "150px";
	}
	else{
		$scope.showSideBar = true;
		document.getElementById("sideToggle").style.paddingLeft = "01px";
	}
}


/*$scope.toggleSideBar = function() {
	var width = $('#sideToggle').width();
	if (width == '250') {
		document.getElementById("sideToggle").style.width = "0px";
	} else {
		document.getElementById("sideToggle").style.width = "250px";
	}
}*/

/******************** Top Navigation bar heading        *******/
//$scope.showSideBar=true;
			if($state.current.name=="home"){
				$scope.navBarColor= "#ffbb33";
				$scope.navBarHeading="Fundoo Keep";
			}
			else if($state.current.name=="Reminder"){
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
		
		/**********  Delete & Restore Note  ***************/
		$scope.deleteNote=function(note){
			if(note.deleteStatus == "true")
				{
				note.deleteStatus = "false";
				}
			else
				{
				note.deleteStatus = "true";
				}
			//note.deleteStatus = "true";
			$scope.updateNote(note);
			/*var url = 'update/' + note.id;
			var method = 'POST';
			var token = localStorage.getItem('token');
			var notes=homeService.service(url,method,note,token);
			notes.then(function(response){
				getNotes();
			},function(response){
				getNotes();
				$scope.error=response.data;
			});*/
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
		/*$scope.restoreNote=function(note){
			note.deleteStatus = "false";
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
		}*/
		
		/**********  Archive &  Unarchive Note  ***************/
		$scope.archiveNote=function(note){
			if(note.archiveStatus== "true")
				{
				note.archiveStatus= "false";
				$scope.Archive=false;
				}
			else
				{
				note.archiveStatus= "true";
				$scope.Archive=true;
				}
			//note.archiveStatus= "true";
			note.pin="true";
			$scope.updateNote(note);
			//modalInstance.close('resetmodel');
			/*var url = 'update/' + note.id;
			var method = 'POST';
			var token = localStorage.getItem('token');
			var notes=homeService.service(url,method,note,token);
			notes.then(function(response){
				getNotes();
			},function(response){
				getNotes();
				$scope.error=response.data;
			});*/
		}
		
		/**********  Unarchive Note  ***************/
		/*$scope.unarchiveNote=function(note){
			note.archiveStatus = "false";
			note.pin="true";
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
		}*/
		
		
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
			console.log("inside Update image updated");
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
		
		/*********** LogOut *******/
		$scope.logout = function() {
			localStorage.removeItem('token');
			$state.go('login');
		}
		
		
		/***********  Edit Note  **************/
		$scope.editNote=function(note){
			note.title=document.getElementById("title").innerHTML;
			note.description=document.getElementById("description").innerHTML;
			modalInstance.close('resetmodel');
			$scope.updateNote(note);
			/*var url = 'update/' + note.id;
			var method = 'POST';
			var token = localStorage.getItem('token');
			var notes=homeService.service(url,method,note,token);
			notes.then(function(respons){
				getNotes();
			},function(response){
				getNotes();
				$scope.error=response.data.message;
			});*/
		}
		
		/****************  Note Color *************/
		//$scope.AddNoteColor="#ffffff";
		
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
		
		/***********  Change Note Color **************/
		$scope.changeColor=function(note){
			var url = 'updateColor/' + note.id;
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
		
		
		/******* Pin  *****************//* 
		$scope.pinStatus =false;
		
		$scope.pinUnpin = function() {
				if($scope.pinStatus == false){
				$scope.pinStatus = true;
			}
			else {
				$scope.pinStatus=false;
			}
		}*/
		
		
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
		
		/****** Social Share ***********/
		$scope.socialShare = function(note) {
			FB.init({
				appId : '155180495087574',
				status : true,
				cookie : true,
				xfbml : true,
				version : 'v2.4'
			});

			FB.ui({
				method : 'share_open_graph',
				action_type : 'og.likes',
				action_properties : JSON.stringify({
					object : {
						'og:title' : note.title,
						'og:description' :note.description
					}
				})
			}, function(response) {
				alert('Posting completed.');
			},function(error){
				alert('Somthing Wrong.');
			});
		};

		
		/******** Remainder ********/
		/*$scope.openReminder=function(note){
			
			$('#datepicker').datetimepicker();
			$scope.timecheck=$("#datepicker").val();
			console.log($scope.timecheck);
		}*/
		
		
		$scope.AddReminder='';
		$scope.openAddReminder=function(){
		   	$('#datepicker').datetimepicker();
		   	$scope.AddReminder= $('#datepicker').val();
	}
		
		$scope.reminder ="";
		$scope.openReminder=function(note){
			   	$('.reminder').datetimepicker();
			   	 var id = '#datepicker' + note.id;
			   	$scope.reminder = $(id).val();
			   	//note.reminderStatus=$scope.reminder;
			   	if($scope.reminder === "" || $scope.reminder === undefined){
			   		console.log(note);
			   		console.log($scope.reminder);
			   	}
			   	else{
			   		console.log($scope.reminder);
			   		note.reminderStatus=$scope.reminder;
			   		console.log(note.reminderStatus);
			   		console.log(note);
			   		$scope.updateNote(note);
			   		$scope.reminder="";
			   }
		}
		
		/******** Detele Reminder *********/
		$scope.deleteReminder=function(note){
			note.reminderStatus="";
			$scope.updateNote(note);
		}
		
		/*****  Collaborator  *******/
		$scope.openCollaborate=function(note,user){
			console.log(" $$$  Collaborate  @@@@");
			$scope.note=note;
			$scope.user=user;
			/*$scope.indexOfNote=index;*/
			modalInstance = $uibModal.open({
				templateUrl: 'template/Collborate.html',
				scope : $scope
				});	
		}
		
		var collborators = [];
		$scope.getUserlist=function(note,user){
			
			console.log("Collll @@@@   note");
			console.log("note in collaborator    _------- "+note);
			var object={};
			object.noteId=note;
			object.sharedId={};
			object.ownerId=user;
			
			var url='collaborate';
			var method='POST';
			var token = localStorage.getItem('token');
			var collaborateUser=homeService.service(url,method,object,token);
			collaborateUser.then(function(response){
				console.log("Inside collborator");
				console.log(response.data);
				$scope.users = response.data;
				note.collabratorUsers = response.data;

			}, function(response) {
				$scope.users = {};
				collborators = response.data;

			});
			console.log("Returned");
			console.log(collborators);
			return collborators;
			
		}
		
		$scope.collborate=function(note,user){
			modalInstance.close('resetmodel');
			var object={};
			object.noteId=note;
			object.sharedId=$scope.shareWith;
			object.ownerId=user;
			
			var url='collaborate';
			var method='POST';
			var token = localStorage.getItem('token');
			var collaborateUser=homeService.service(url,method,object,token);
			collaborateUser.then(function(response){
				$scope.users = response.data;
				$scope.note.collabratorUsers = response.data;
			}, function(response) {
				$scope.users = {};
			});
		}
		
		
		$scope.removeCollborator=function(note,user){
			
			var object={};
			object.noteId=note;
			object.sharedId=user;
			object.ownerId={
					'userEmail':''
			};
			
			var url='removeCollaborate';
			var method='POST';
			var token = localStorage.getItem('token');
			var collaborateUser=homeService.service(url,method,object,token);
			collaborateUser.then(function(response){
				console.log("Deleted  @#345353");
				$scope.collborate(note,$scope.User);
			},function(response){
				console.log("@@  Leader  @@@@");
			});
			
		}
		
		
		
		
		/**********  Copy Note  ***************/
		$scope.copy=function(note){
			note.pin = "true";
			note.noteStatus = "true";
			note.reminderStatus= "";
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
			$scope.note.reminderStatus= "";
			$scope.note.archiveStatus= "false";
			$scope.note.deleteStatus = "false";
			$scope.note.noteColor="#ffffff";
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