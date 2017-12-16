var toDoApp=angular.module('toDoApp');

toDoApp.controller('homeController',function($scope,$location,homeService ,$state, $uibModal, fileReader, $interval,$filter){
	$scope.AddNoteBox=false;
	$scope.ShowAddNote=function(){
		$scope.AddNoteBox= true;
	}
 
/**********	Getting All notes   ********/
var getNotes=function(){
	var token=localStorage.getItem('token');
	console.log("inside get User ")
	var notes=homeService.getNotes(token);
	notes.then(function(response){
		notes=response.data;
		$scope.ListViewToggle();
		$scope.notes=notes;
	},function(response){
		$scope.logout();
	});	
}


/****** Get current user *********/
getUser();
function getUser(){
	var token=localStorage.getItem('token');
	var url = 'currentUser';
	var method = 'GET';
		var user=homeService.getCurrentUser(url,method,token);
		user.then(function(response){
			$scope.User=response.data;
			$scope.ListView=response.data.listView;
		},function(response){
			$scope.logout();
		});
	}
	
/**** Getting User labels *******/
getUserLabels();
function getUserLabels(){
	var token=localStorage.getItem('token');
	var url = 'getUserLabels';
	var method = 'GET';
		var labels=homeService.getCurrentUser(url,method,token);
		labels.then(function(response){
			$scope.Listlabel=response.data;
		},function(response){
			
		});
}

	/****** List and Grid *******/
/*ListViewToggle();*/
	/*$scope.ListView = true;*/
$scope.ListView=localStorage.getItem('LISTGRID');
console.log($scope.ListView);
	$scope.ListViewToggle = function() {
		
		if ($scope.ListView == true) {
			$scope.ListView = false;
			localStorage.setItem('LISTGRID',$scope.ListView);
			$scope.card="col-md-12 col-sm-12 col-xs-12 col-lg-10";
			/*var notes = document.getElementsByClassName('card');
			for (var i = 0; i < notes.length; i++) {
				notes[i].style.width = "800px";
			}*/
		}
		else {
			$scope.ListView = true;
			localStorage.setItem('LISTGRID',$scope.ListView);
			$scope.card="col-md-6 col-sm-8 col-xs-12 col-lg-3";
			/*var notes = document.getElementsByClassName('card');
			for (var i = 0; i < notes.length; i++) {
				notes[i].style.width = "250px";
			}*/
		}
	}
/**--------------------------------------Image & User Profile Upload-----------------------------------------------------------------------------*/	
	/******** Image Upload **********/	
	$scope.imageSrc = "";

	$scope.$on("fileProgress", function(e, progress) {
		$scope.progress = progress.loaded / progress.total;
	});

	/*check from image upload type(add note, present note, user profile)*/
	$scope.openImageUploader = function(type) {
		$scope.type = type;
		$('#imageuploader').trigger('click');
	}
	
	$scope.type = {};
	$scope.type.image = '';

	$scope.$watch('imageSrc', function(newimg, oldimg) {
		if ($scope.imageSrc != '') {
			if ($scope.type === 'input') {
				$scope.adding = $scope.imageSrc;
			} 
			else if($scope.type === 'user'){
				$scope.ImageCrop=$scope.imageSrc;
				$scope.imageCrop();
				
				/*$scope.User.profile=$scope.imageSrc;
				$scope.changeProfile($scope.User);*/
			}
			else {
				$scope.type.image = $scope.imageSrc;
				$scope.updateNote($scope.type);
			}
		}

	});
	
	$scope.changeProfile=function(user){
		var token = localStorage.getItem('token');
		var method = 'PUT';
		var url = 'profileChange';
		var a = homeService.service(url,method,user,token);
		a.then(function(response) {
		
			},function(response){
			
		});
	}
	
	$scope.imageCrop=function(){
		modalInstance = $uibModal.open({
			templateUrl: 'template/ImageCropper.html',
			scope : $scope
			});
	}
	
	$scope.updateProfilePic=function(){
		modalInstance.close();
		$scope.User.profile=$scope.croppedImage;
		$scope.changeProfile($scope.User);
	}
	
	$scope.removeImage=function(note){
		note.image='';
		$scope.updateNote(note);
	}
	/**----------------------------Label Program---------------------------------------------------------*/
/******** Add Label***********/
	$scope.openAddNewLabel=function(){
		modalInstance = $uibModal.open({
			templateUrl: 'template/AddLabel.html',
			scope : $scope
			});
	}
	
	$scope.addLabel=function(){
		modalInstance.close();
		var newLabel=document.getElementById("newLabel").value;
		if(newLabel!=''){
			var obj={};
			obj.labelName=newLabel;
			var url = 'addLabel';
			var method = 'POST';
			var token = localStorage.getItem('token');
			var laBel=homeService.service(url,method,obj,token);
			laBel.then(function(response){
				console.log(response.data);
				getNotes();
				getUserLabels();
			},function(response){
				getNotes();
			});
		}	
	}
	
	$scope.addNoteLabel=function(note){
		var noteLabel=document.getElementById('noteLabel').value;
		if(noteLabel!=''){
			
			var obj={};
			obj.labelName=noteLabel;
			var url = 'addLabel';
			var method = 'POST';
			var token = localStorage.getItem('token');
			var laBel=homeService.service(url,method,obj,token);
			laBel.then(function(response){
				console.log(response.data);
				/*$scope.toggleLabel(note,obj);*/
				getNotes();
				getUserLabels();
			},function(response){
				getNotes();
			});
		}
	}
	
	/******* Add and Remove the labels from Note *******/
	$scope.toggleLabel=function(note,label){
		var index=-1;
		for(var i=0;i<note.labels.length;i++){
			if(note.labels[i].labelName == label.labelName){
				index=i;
			}
		}
		if(index == -1){
			note.labels.push(label);
		}
		else{
		note.labels.splice(index,1);
		}
		 $scope.updateNote(note); 
	}
	
	$scope.checkStatus=function(note,label){
		for(var i=0;i<note.labels.length;i++){
			if(note.labels[i].labelName===label.labelName){
				return true;
			}
		}
		return false;
	}
	
	/***** Delete Note Label ******/
	$scope.deleteNoteLabel=function(note,label){
		$scope.note = note;
        if (angular.isArray($scope.note.labels)) {
          for (var i = $scope.note.labels.length; i--;) {
            if (angular.equals($scope.note.labels[i],label)) {
            	$scope.note.labels.splice(i, 1);
              break;
            }
          }
        }
        $scope.updateNote(note);
	}
	
	/*** Delete User Label ****/
	$scope.deleteUserLabel=function(label){
		modalInstance.close();
		var url="deleteUserLabel";
		var method="POST";
		var token = localStorage.getItem('token');
		var abc=homeService.service(url,method,label,token);
		abc.then(function(response){
			console.log("return   "+response.data.message);
			getNotes();
			getUserLabels();
		},function(responsr){
			getNotes();
		});
	}
	
	/***** Label page***/
	$scope.goToLabelPage=function(label){
		$scope.displayLable=label.labelName;
		$scope.navBarHeading=$scope.displayLable;
		localStorage.setItem('LABEL',$scope.displayLable);
		$state.go('/LabelPage');
	}
	
	
	
	
	$(document).ready(function(){
		  $('.dropdown-submenu a.test').on("click", function(e){
		    $(this).next('ul').toggle();
		    e.stopPropagation();
		    e.preventDefault();
		  });
		});


/************  Toggle side bar   ********/
$scope.showSideBar = false;
$scope.toggleSideBar = function() {
	if($scope.showSideBar){
		$scope.showSideBar=false;
		document.getElementById("sideToggle").style.paddingLeft = "150px";
		document.getElementById("noteToggle").style.paddingLeft = "170px";
	}
	else{
		$scope.showSideBar = true;
		document.getElementById("sideToggle").style.paddingLeft = "0px";
		document.getElementById("noteToggle").style.paddingLeft = "15px";
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
			/*else if($state.current.name=="LabelPage"){
				$scope.navBarColor= "#607D8B";
				var LABEL = localStorage.getItem('LABEL');
				$scope.navBarHeading=LABEL;
			}*/
			
			else if($state.current.name=="Search"){
				$scope.navBarColor= "#0066ff";
				$scope.navBarHeading="Fundoo Keep";
				$scope.contentable = true;
				$scope.searching=true;
			}
			else {
				$scope.displayLable=$location.path().substr(1) ;
				$scope.navBarColor = "#607D8B";
				$scope.navBarHeading =$scope.displayLable;
			}
			
		/********  Search *************/
			$scope.goToSearchPage=function(){
				$state.go('Search');
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
			$scope.updateNote(note);
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
			note.pin="true";
			$scope.updateNote(note);
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
			modalInstance.close();
			$scope.updateNote(note);
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
		
		/**** Tommorow , Next Week & Later Today reminder *******/
		$scope.reminder=function(note,day){
			$scope.day=day;
			var date=new Date();
			var month=date.getMonth()+1;
			var year=date.getFullYear();
			if($scope.day==='tommorow'){
				var tommorow=date.getDate()+1;
				var tommorowDate=(tommorow+"/"+month+"/"+year);
				note.reminderStatus=tommorowDate+" 8:00AM";
			}
			else if($scope.day==='nextweek'){
				var nextWeek=date.getDate()+7;
				var nextWeekDate=(nextWeek+"/"+month+"/"+year);
				note.reminderStatus=nextWeekDate;
			}
			else{
				var time=date.getHours();
				if(time<8){
					note.reminderStatus="Today 8:00AM";
				}
				else if(time>8  &&  time<19){
					note.reminderStatus="Today 8:00PM";
				}
				
			}
			$scope.updateNote(note);
		}
		
		/******** Detele Reminder *********/
		$scope.deleteReminder=function(note){
			note.reminderStatus="";
			$scope.updateNote(note);
		}
		
		/*****  Collaborator  *******/
		$scope.openCollaborate=function(note,user){
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
			var object={};
			object.noteId=note;
			object.sharedId={};
			object.ownerId=user;
			
			var url='collaborate';
			var method='POST';
			var token = localStorage.getItem('token');
			var collaborateUser=homeService.service(url,method,object,token);
			collaborateUser.then(function(response){
				console.log(response.data);
				$scope.users = response.data;
				note.collabratorUsers = response.data;

			}, function(response) {
				$scope.users = {};
				collborators = response.data;

			});
			console.log(collborators);
			return collborators;
			
		}
		
		$scope.collborate=function(note,user){
			
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
				modalInstance.close();
				$scope.errorMsg="";
			}, function(response) {
				//$scope.users = {};
				$scope.errorMsg="User already exist";
			});
			
		}
		
		
		$scope.removeCollborator=function(note,user){
			modalInstance.close();
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
				
			});
		}
		
		$scope.getOwner = function(note) {
			var url = 'getOwner';
			var method='POST';
			var note=note;
			var token = localStorage.getItem('token');
			var collaborateUser=homeService.service(url,method,note,token);
			collaborateUser.then(function(response){
				$scope.owner = response.data;
			}, function(response) {
				$scope.users = {};
			});
		}
		
		$scope.getAllEmail=function(){
			var url = 'getAllEmail';
			var method='GET';
			var token = localStorage.getItem('token');
			var user=homeService.getCurrentUser(url,method,token);
			user.then(function(response){
				$scope.emailList=response.data;
				console.log(response.data);
			},function(response){
			});
		}

		
		/**********  Copy Note  ***************/
		$scope.copy=function(note){
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
		
		/*********  Add archive function  *************/
		$scope.archive="false";
		$scope.addArchiveNote=function(){
			$scope.archive="true";
		}
		
		$scope.adding="";
		

		/******  Adding Note  **************/
		
		$scope.addNote=function(){
			$scope.note={};
			var token=localStorage.getItem('token');
			$scope.note.title=document.getElementById("title").innerHTML;
			$scope.note.description=document.getElementById("description").innerHTML;
			$scope.note.pin = "true";
			$scope.note.noteStatus = "true";
			$scope.note.reminderStatus= $scope.AddReminder;
			$scope.note.image= $scope.adding;
			$scope.note.archiveStatus= $scope.archive;
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
		
		/******** URL Check *****************/
		$scope.checkURL=function(note){
			console.log(encodeURI(note.description));
			if(note.description!=''){
				
				var urlpattern=/[-a-zA-Z0-9@:%_\+.~#?&//=]{2,256}\.[a-z]{2,4}\b(\/[-a-zA-Z0-9@:%_\+.~#?&//=]*)?/gi;
				 note.arrayOfUrlData=[];
				var noteUrl=note.description;
				var count=0;
				
				if(noteUrl.match(urlpattern)){
					var urlArray=noteUrl.match(urlpattern);
					var singleUrl=[];
					for(var i=0; i<urlArray.length; i++){
						singleUrl[i]=urlArray[i];
						
						var url='getImageUrl';
						var method='POST';
						var token=localStorage.getItem('token');
						
						var urldata=homeService.getUrl(url,method,urlArray[i],token);
						urldata.then(function(response){
							note.arrayOfUrlData[count]={
									title:response.data.title,
									imageURL:response.data.imageURL,
									domain:response.data.domain,
									url:singleUrl[count]
							}
							count=count+1; 
							
							/*$scope.urlData=response.data;
							$scope.urlData.url=singleUrl[count];*/
							
						},function(error){
							console.log("Error");
						});
					}
				}	
			}
		}
		
		
		
		
		/*$scope.imagecrop=function(){
			$scope.cropper={};
			$scope.cropper.cropperImage=null;
			$scope.bounds={};
			$scope.bounds.left=0;
			$scope.bounds.right=0;
			$scope.bounds.top=0;
			$scope.bounds.bottom=0;
			
		}*/
		
		getNotes();
		});