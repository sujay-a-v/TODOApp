var toDoApp=angular.module('toDoApp');

toDoApp.directive('topNavBar',function(){
	return{
		restrict:'E',
		templateUrl:'./template/TopNavBar.html'
	}
})
toDoApp.directive('sideNavBar',function(){
	return{
		restrict:'E',
		templateUrl:'./template/SideNavBar.html'
	}
})
toDoApp.directive('addNote',function(){
	return{
		restrict:'E',
		templateUrl:'./template/AddNote.html'
	}
});
toDoApp.directive('moreOption',function(){
	return{
		restrict:'E',
		templateUrl:'./template/MoreOptions.html'
	}
})
toDoApp.directive('noteColor',function(){
	return{
		restrict:'E',
		templateUrl:'./template/NoteColor.html'
	}
})
toDoApp.directive('urlProgram',function(){
	return{
		restrict:'E',
		templateUrl:'./template/urlProgram.html'
	}
})
toDoApp.directive('reminderIcon',function(){
	return{
		restrict:'E',
		templateUrl:'./template/ReminderIcon.html'
	}
})

toDoApp.directive("ngFileSelect", function(fileReader, $timeout) {
    return {
      scope: {
        ngModel: '='
      },
      link: function($scope, el) {
        function getFile(file) {
          fileReader.readAsDataUrl(file, $scope)
            .then(function(result) {
              $timeout(function() {
                $scope.ngModel = result;
              });
            });
        }

        el.bind("change", function(e) {
          var file = (e.srcElement || e.target).files[0];
          getFile(file);
        });
      }
    };
  });

toDoApp.factory("fileReader", function($q, $log) {
  var onLoad = function(reader, deferred, scope) {
    return function() {
      scope.$apply(function() {
        deferred.resolve(reader.result);
      });
    };
  };

  var onError = function(reader, deferred, scope) {
    return function() {
      scope.$apply(function() {
        deferred.reject(reader.result);
      });
    };
  };

  var onProgress = function(reader, scope) {
    return function(event) {
      scope.$broadcast("fileProgress", {
        total: event.total,
        loaded: event.loaded
      });
    };
  };

  var getReader = function(deferred, scope) {
    var reader = new FileReader();
    reader.onload = onLoad(reader, deferred, scope);
    reader.onerror = onError(reader, deferred, scope);
    reader.onprogress = onProgress(reader, scope);
    return reader;
  };

  var readAsDataURL = function(file, scope) {
    var deferred = $q.defer();

    var reader = getReader(deferred, scope);
    reader.readAsDataURL(file);

    return deferred.promise;
  };

  return {
    readAsDataUrl: readAsDataURL
  };
});