package com.bridgelabz.control;

import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bridgelabz.model.Notes;
import com.bridgelabz.model.User;
import com.bridgelabz.service.NotesService;
import com.bridgelabz.service.UserService;
import com.bridgelabz.tokens.Token;

@Controller
public class NotesController {
	
	@Autowired
	private NotesService noteService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Token tokens;
	
	//***** Adding the Notes ******///
	@RequestMapping(value="/notesCreate", method = RequestMethod.POST)
	public ResponseEntity<String> addNotes(@RequestBody Notes notes,HttpServletRequest request)
	{
		System.out.println("in side notes creation");
		String userToken=null;
		Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            if(key.equals("token"))
            {
            	userToken=request.getHeader(key);
            }
          }	
        int id=tokens.validateToken(userToken);
        if(id==0)
        {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Found");

        }
		System.out.println("in side notes creation");
		User user1=userService.retrieveById(id);
		Date date=new Date();
		notes.setCreateDate(date);
		notes.setModifiedDate(date);
		
		//User user=((User) session.getAttribute("User"));
		notes.setUser(user1);
		noteService.addUserNotes(notes);
		return ResponseEntity.status(HttpStatus.OK).body("Notes added successfully");
	}
	
	//*************** Retrieve the Notes By Id ********//
	@RequestMapping(value="/notesRetrieve/{id}", method = RequestMethod.GET)
	public ResponseEntity<Notes> retrieveNotesById(@PathVariable ("id") int id)
	{
		Notes notes=noteService.fetchById(id);
		if(notes==null)
		{
			return new ResponseEntity<Notes>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Notes>(notes,HttpStatus.OK);
	}
	
	//********** Retrieve All the Notes *******//
	@RequestMapping(value="/notesRetrieve", method = RequestMethod.GET )
	public ResponseEntity<List<Notes>> retrieveAllNotes(HttpServletRequest request)
	{
		String userToken=null;
		Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            if(key.equals("token"))
            {
            	userToken=request.getHeader(key);
            }
          }	
        int id=tokens.validateToken(userToken);
        if(id==0)
        {
			return new ResponseEntity(HttpStatus.NOT_FOUND);

        }
		System.out.println("in side notes creation");
		User user=userService.retrieveById(id);
		List<Notes> notes=noteService.fetchAllNotes(user);
		if(notes==null)
		{
			return new ResponseEntity<List<Notes>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Notes>>(notes,HttpStatus.OK);
	}
	
	
	//********* Delete the Notes By Id ********//
	@RequestMapping(value="/notesDelete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteNotesById(@PathVariable ("id") int id, HttpSession session)
	{
		Notes currentNotes=noteService.fetchById(id);
		if(currentNotes==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notes not found for id "+id);
		}
		noteService.dalateUserNotes(id);
		return ResponseEntity.status(HttpStatus.OK).body("Notes deleted");
	}
	
	
	//******* Update the Notes by Id ********//
	@RequestMapping(value="notesUpdate/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateNotes(@PathVariable ("id") int id,@RequestBody Notes notes, HttpSession session )
	{
		Date date=new Date();
		notes.setModifiedDate(date);
		Notes currentNotes=noteService.fetchById(id);
		if(currentNotes==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notes not found for id "+id);
		}
		currentNotes.setDescription(notes.getDescription());
		currentNotes.setModifiedDate(notes.getModifiedDate());
		currentNotes.setTitle(notes.getTitle());
		currentNotes.setUser((User) session.getAttribute("User"));
		noteService.modifiedNotes(id, currentNotes);
		return ResponseEntity.status(HttpStatus.OK).body("Note Updated");
	}
}