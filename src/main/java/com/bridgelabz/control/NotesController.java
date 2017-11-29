package com.bridgelabz.control;

import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bridgelabz.model.Notes;
import com.bridgelabz.model.Response;
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

	@Autowired
	private Response response;

	// ***** Adding the Notes ******///
	@RequestMapping(value = "/notesCreate", method = RequestMethod.POST)
	public ResponseEntity<Response> addNotes(@RequestBody Notes notes, HttpServletRequest request) {
		System.out.println(notes);
		System.out.println("line 1");

		if ((notes.getDescription() == "" || notes.getDescription() == null)
				&& (notes.getTitle() == "" || notes.getTitle() == null)) {
			response.setMessage("Notes adding failed");
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		String userToken = null;
		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			if (key.equals("token")) {
				userToken = request.getHeader(key);
			}
		}
		int id = tokens.validateToken(userToken);
		if (id == 0) {
			response.setMessage("User not Found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		User user1 = userService.retrieveById(id);
		Date date = new Date();
		notes.setCreateDate(date);
		notes.setModifiedDate(date);

		// User user=((User) session.getAttribute("User"));
		notes.setUser(user1);
		noteService.addUserNotes(notes);
		response.setMessage("Notes added successfully");
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	// *************** Retrieve the Notes By Id ********//
	@RequestMapping(value = "/notesRetrieve/{id}", method = RequestMethod.GET)
	public ResponseEntity<Notes> retrieveNotesById(@PathVariable("id") int id) {
		Notes notes = noteService.fetchById(id);
		if (notes == null) {
			return new ResponseEntity<Notes>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Notes>(notes, HttpStatus.OK);
	}

	// ********** Retrieve All the Notes *******//
	@RequestMapping(value = "/notesRetrieve", method = RequestMethod.GET)
	public ResponseEntity<List<Notes>> retrieveAllNotes(HttpServletRequest request) {
		String userToken = null;
		userToken = request.getHeader("token");
		int id = tokens.validateToken(userToken);
		if (id == 0) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		User user = userService.retrieveById(id);
		List<Notes> notes = noteService.fetchAllNotes(user);
		if (notes == null) {
			return new ResponseEntity<List<Notes>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Notes>>(notes, HttpStatus.OK);
	}

	// ********* Delete the Notes By Id ********//
	@RequestMapping(value = "/noteDelete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Response> deleteNotesById(@PathVariable("id") int id, HttpSession session) {
		Notes currentNote = noteService.fetchById(id);
		if (currentNote == null) {
			response.setMessage("Notes not found for id ");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		noteService.dalateUserNotes(id);
		response.setMessage("Note deleted");
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	// ******* Update the Notes by Id ********//
	@RequestMapping(value = "/noteUpdate/{id}", method = RequestMethod.POST)
	public ResponseEntity<Response> updateNotes(@PathVariable("id") int id, @RequestBody Notes notes,
			HttpSession session) {
		Date date = new Date();
		notes.setModifiedDate(date);
		Notes currentNote = noteService.fetchById(id);
		if (currentNote == null) {
			response.setMessage("Notes not found for id ");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		currentNote.setDescription(notes.getDescription());
		currentNote.setModifiedDate(notes.getModifiedDate());
		currentNote.setTitle(notes.getTitle());
		currentNote.setUser((User) session.getAttribute("User"));
		noteService.modifiedNotes(id, currentNote);
		response.setMessage("Note Updated");
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	public ResponseEntity<Response> update(@PathVariable("id") int id, @RequestBody Notes note,
			HttpSession session, HttpServletRequest request) {
		System.out.println("inside update : " +note.getReminderStatus());
		
		Date date = new Date();
		note.setModifiedDate(date);

		int uid = tokens.validateToken(request.getHeader("token"));

		User user = userService.retrieveById(uid);
		note.setUser(user);
		Notes currentNote = noteService.fetchById(id);
		if (currentNote == null) {
			response.setMessage("Notes not found for id ");
			return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
		}
		currentNote.setModifiedDate(note.getModifiedDate());
		noteService.modifiedNotes(id, note);
		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/updateColor/{id}", method = RequestMethod.POST)
	public ResponseEntity<Response> updateColor(@PathVariable("id") int id, @RequestBody Notes note,
			HttpSession session, HttpServletRequest request) {
		int uid = tokens.validateToken(request.getHeader("token"));

		User user = userService.retrieveById(uid);
		note.setUser(user);
		Notes currentNote = noteService.fetchById(id);
		if (currentNote == null) {
			response.setMessage("Notes not found for id ");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		noteService.modifiedNotes(id, note);
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}
}
