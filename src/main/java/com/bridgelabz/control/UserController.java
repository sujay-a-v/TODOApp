package com.bridgelabz.control;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.model.Collaborator;
import com.bridgelabz.model.ErrorMessage;
import com.bridgelabz.model.Response;
import com.bridgelabz.model.User;
import com.bridgelabz.passwordencrypt.PasswordEncrypt;
import com.bridgelabz.service.MailService;
import com.bridgelabz.service.NotesService;
import com.bridgelabz.service.Producer;
import com.bridgelabz.service.UserService;
import com.bridgelabz.tokens.Token;
import com.bridgelabz.validation.UserValidation;

@Controller
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserValidation userValidation;

	/*@Autowired
	private ErrorMessage errorMassage;*/

	@Autowired
	private MailService mailService;
	
	@Autowired
	private PasswordEncrypt passwordEncrypt;
	
	@Autowired
	private Token tokens;
	
	@Autowired
	private Response response;
	
	@Autowired
	private Producer producer;
	
	@Autowired
	private NotesService noteService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Response> doRegister(@RequestBody User user,
			HttpServletRequest request) throws Exception {

		String isValid = userValidation.userValidate(user);
		if (!isValid.equals("true")) {
			response.setMessage(isValid);
			response.setStatus(-1);
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);

		} else {
			String isPasswordValid=userValidation.passwordValidate(user.getUserPassword());
			if(!isPasswordValid.equals("true"))
			{
				response.setMessage(isPasswordValid);
				response.setStatus(-1);
				return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
			}
			boolean status = userService.isUserExist(user.getUserEmail());
			if (status) {
				response.setMessage("User email already exist");
				response.setStatus(-1);
				return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
			}

			String password=passwordEncrypt.encrypt(user.getUserPassword());
			user.setUserPassword(password);
			
			userService.saveUserData(user);
			String url = request.getRequestURL().toString();
			
			String compactToken=tokens.generateToken(user.getId());
			url = url.substring(0, url.lastIndexOf("/")) + "/active/" + compactToken;
			
			HashMap<String, String> map=new HashMap<>();
			map.put("to", user.getUserEmail());
			map.put("message", url);
			producer.send(map);

			//mailService.sendMail(user.getUserEmail(), "User Validate", url + " \nclick on the url to activate");

			response.setMessage("RegisterSuccess and activation link sent to User mail");
			response.setStatus(1);
			return new ResponseEntity<Response>(response, HttpStatus.CREATED);
		}
	}

	@RequestMapping(value = "/active/{Token:.+}", method = RequestMethod.GET)
	public ResponseEntity<String> activeUser(@PathVariable("Token") String token, HttpServletResponse response)throws Exception {
		int id=tokens.validateToken(token);
		System.out.println("Token verified id "+id);
		if(id>0)
		{
			User user = userService.retrieveById(id);
			if (user == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not found");
			}
			user.setActive(true);
			userService.activateUser(id, user);
			response.sendRedirect("http://localhost:8080/ToDoApp/#!/login");
			return ResponseEntity.status(HttpStatus.OK).body("User activated");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Id");
	}
	
	@RequestMapping(value="/listAndGrid", method = RequestMethod.POST)
	public ResponseEntity<Boolean> listGrid(@RequestBody User user,HttpServletRequest request) throws Exception
	{
		System.out.println("\n \n Inside list and Grid view \n\n");
		int id = tokens.validateToken(request.getHeader("token"));
		User user1 = userService.retrieveById(id);
		if (user1 == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
		}
		user1.setListView(user.isListView());
		userService.activateUser(id, user1);
		return ResponseEntity.status(HttpStatus.OK).body(user1.isListView());
	}
	
	@RequestMapping(value="/collaborate",method = RequestMethod.POST)
	public ResponseEntity<Response> collaborate(@RequestBody Collaborator collaborate,HttpServletRequest request) throws Exception{
		System.out.println("\n\n\n\n ##$#$   Success  \n\n");
		System.out.println("Owner ----> "+collaborate.getOwnerId());
		System.out.println("Owner ----> "+collaborate.getSharedId());
		System.out.println("Owner ----> "+collaborate.getNoteId());
		
		noteService.addCollaborator(collaborate);
		
		response.setMessage("Added");
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
}
