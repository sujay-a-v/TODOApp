package com.bridgelabz.control;

import java.util.ArrayList;
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

import com.bridgelabz.model.Collaborator;
import com.bridgelabz.model.Label;
import com.bridgelabz.model.LinkScrapper;
import com.bridgelabz.model.Notes;
import com.bridgelabz.model.Response;
import com.bridgelabz.model.UrlData;
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
	/**
	 * @param notes
	 * @param request
	 * @return response message (String)
	 * @description creating the new note
	 */
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
	/**
	 * @param id
	 * @return note Object
	 * @description retrieve the note by id
	 */
	@RequestMapping(value = "/notesRetrieve/{id}", method = RequestMethod.GET)
	public ResponseEntity<Notes> retrieveNotesById(@PathVariable("id") int id) {
		Notes notes = noteService.fetchById(id);
		if (notes == null) {
			return new ResponseEntity<Notes>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Notes>(notes, HttpStatus.OK);
	}

	// ********** Retrieve All the Notes *******//
	/**
	 * @param request
	 * @return List note Object
	 * @description retrieve all the notes of the user
	 */
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
		List<Notes> collaboratedNotes=noteService.getCollaboratedNotes(user);
		List<Notes> allNotes=new ArrayList<Notes>();
		for (int i = 0; i < notes.size(); i++) {
			allNotes.add(notes.get(i));
		}
		for (int j = 0; j < collaboratedNotes.size(); j++) {
			allNotes.add(collaboratedNotes.get(j));
		}
		return new ResponseEntity<List<Notes>>(allNotes, HttpStatus.OK);
	}

	// ********* Delete the Notes By Id ********//
	/**
	 * @param id
	 * @param session
	 * @return String
	 * @description delete the particular note by note-id
	 */
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
	/**
	 * @param id
	 * @param notes
	 * @param session
	 * @return message (String)
	 * @description update a note by note-id
	 */
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

	/**
	 * @param id
	 * @param note
	 * @param session
	 * @param request
	 * @return String(message)
	 * @description update the user note by note-id
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	public ResponseEntity<Response> update(@PathVariable("id") int id, @RequestBody Notes note,
			HttpSession session, HttpServletRequest request) {
		System.out.println("inside update : " +note.getReminderStatus());
		
		Date date = new Date();
		note.setModifiedDate(date);

		int uid = tokens.validateToken(request.getHeader("token"));

		User user = userService.retrieveById(uid);
		
		Notes currentNote = noteService.fetchById(id);
		note.setUser(currentNote.getUser());
		if (currentNote == null) {
			response.setMessage("Notes not found for id ");
			return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
		}
		currentNote.setModifiedDate(note.getModifiedDate());
		noteService.modifiedNotes(id, note);
		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}

	/**
	 * @param id
	 * @param note
	 * @param session
	 * @param request
	 * @return String(message)
	 * @description change the note color by note-id 
	 */
	@RequestMapping(value = "/updateColor/{id}", method = RequestMethod.POST)
	public ResponseEntity<Response> updateColor(@PathVariable("id") int id, @RequestBody Notes note,
			HttpSession session, HttpServletRequest request) {
		int uid = tokens.validateToken(request.getHeader("token"));

		User user = userService.retrieveById(uid);
		Notes currentNote = noteService.fetchById(id);
		note.setUser(currentNote.getUser());
		if (currentNote == null) {
			response.setMessage("Notes not found for id ");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		noteService.modifiedNotes(id, note);
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}
	
	/**
	 * @param collaborate
	 * @param request
	 * @return List of collaborated Users 
	 * @throws Exception
	 * @description Add the new user of the note and returns all users of the note
	 */
	@RequestMapping(value="/collaborate",method = RequestMethod.POST)
	public ResponseEntity<List<User>> collaborateNote(@RequestBody Collaborator collaborate,HttpServletRequest request) throws Exception{
		
		Collaborator collaborator=new Collaborator();
		List<User> users=new ArrayList<User>();
		
		Notes note=(Notes)collaborate.getNoteId();
		User owner=(User)collaborate.getOwnerId();
		User sharedUser=(User)collaborate.getSharedId();
		
		sharedUser=userService.getByEmail(sharedUser.getUserEmail());
		
		int id = tokens.validateToken(request.getHeader("token"));
		User user=userService.retrieveById(id);
		
		users=noteService.getUserList(note);
		
		if(user!=null)
		{
			if(sharedUser!=null && sharedUser.getId()!=owner.getId())
			{
				int i=0;
				int flag=0;
				while(users.size()>i)
				{
					if(users.get(i).getId()==sharedUser.getId())
					{
						flag=1;
					}
					i++;
				}
				if(flag==0)
				{
					collaborator.setNoteId(note);
					collaborator.setOwnerId(owner);
					collaborator.setSharedId(sharedUser);
					noteService.addCollaborator(collaborator);
					users.add(sharedUser);
					return ResponseEntity.status(HttpStatus.OK).body(users);
				}
				else
				{
					return new ResponseEntity(HttpStatus.NOT_FOUND);
				}
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}
	
	
	/**
	 * @param collaborate
	 * @param request
	 * @return String(message)
	 * @throws Exception
	 * @description delete the user from the collaborated note
	 */
	@RequestMapping(value="removeCollaborate",method = RequestMethod.POST)
	public ResponseEntity<Response> removeCollaborate(@RequestBody Collaborator collaborate,HttpServletRequest request) throws Exception{
		
		User sharedId=collaborate.getSharedId();
		Notes noteId=collaborate.getNoteId();
		int id = tokens.validateToken(request.getHeader("token"));
		User owner=userService.retrieveById(id);
		
		if(owner!=null)
		{
			if(owner.getId()!=sharedId.getId())
			{
				System.out.println("line 2 ");
				int status=noteService.removeUser(sharedId, noteId);
				response.setMessage("User deleted");
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
		}
		response.setMessage("Token expired");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		
	}
	
	/**
	 * @param note
	 * @param request
	 * @return Owner of the note(Object)
	 * @description Get the owner of the note
	 */
	@RequestMapping(value="getOwner",method = RequestMethod.POST)
	public ResponseEntity<User> getOwner(@RequestBody Notes note,HttpServletRequest request)
	{
		Notes getNote=noteService.fetchById(note.getId());
		int id = tokens.validateToken(request.getHeader("token"));
		User user=userService.retrieveById(id);
		if(user!=null)
		{
			User owner=getNote.getUser();
			return new  ResponseEntity<User>(owner,HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}
	
	/**
	 * @param request
	 * @return List of user labels 
	 * @description get labels of the user
	 */
	@RequestMapping(value="getUserLabels",method = RequestMethod.GET)
	public ResponseEntity<List<Label>> getUserLabels(HttpServletRequest request){
		int id = tokens.validateToken(request.getHeader("token"));
		User user=userService.retrieveById(id);
		List<Label> labels=userService.getUserLabel(user);
		return ResponseEntity.status(HttpStatus.OK).body(labels);
		
	}
	
	/**
	 * @param label
	 * @param request
	 * @return String (message)
	 * @description adding the new label to the user
	 */
	@RequestMapping(value="addLabel",method = RequestMethod.POST)
	public ResponseEntity<Response> addLabel(@RequestBody Label label,HttpServletRequest request){
		int id = tokens.validateToken(request.getHeader("token"));
		User user=userService.retrieveById(id);
		Label label1=new Label();
		label1.setLabelName(label.getLabelName());
		label1.setUser(user);
		label1.setNote(null);
		noteService.addNewLabel(label1);
		response.setMessage("label added successfully");
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	/**
	 * @param label
	 * @param request
	 * @return String (message)
	 * @description delete the user label
	 */
	@RequestMapping(value="deleteUserLabel")
	public ResponseEntity<Response> deleteUserLabel(@RequestBody Label label,HttpServletRequest request){
		noteService.deleteUserLabel(label);
		response.setMessage("lable deleted successfully");
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	/**
	 * @param request
	 * @return url-data 
	 * @description receive url and return the url data(Title, Image & domain of the URL)
	 */
	@RequestMapping(value="getImageUrl",method = RequestMethod.POST)
	public ResponseEntity<UrlData> getURL(HttpServletRequest request){
		String url=request.getHeader("noteUrl");
		
		LinkScrapper link=new LinkScrapper();
		UrlData urlData=null;
		
		try{
			urlData=link.getUrlData(url);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity<UrlData>(urlData,HttpStatus.OK);
		
	}
}
