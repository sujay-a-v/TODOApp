var toDoApp=angular.module('toDoApp');

toDoApp.filter('labelFilter',function(){
	return function(notes,labelName){
		var filteredNotes=[];
		
		if(labelName===''){
			return notes;
		}
		else{
			for(var i=0; i<notes.length; i++){
				var note=notes[i];
				var label=note.labels;
				
				for(var j=0; j<label.length; j++){
					if(labelName===label[j].labelName){
						filteredNotes.push(note);
					}
				}
			}
			return filteredNotes;
		}
	}
});