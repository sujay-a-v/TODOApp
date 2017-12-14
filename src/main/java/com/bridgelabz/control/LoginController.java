package com.bridgelabz.control;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.model.Response;
import com.bridgelabz.model.User;
import com.bridgelabz.passwordencrypt.PasswordEncrypt;
import com.bridgelabz.service.MailService;
import com.bridgelabz.service.UserService;
import com.bridgelabz.tokens.Token;
import com.bridgelabz.validation.UserValidation;

@RestController
public class LoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserValidation userValidation;

	@Autowired
	private MailService mailService;

	@Autowired
	private PasswordEncrypt passwordEncrypt;
	
	@Autowired
	private Token tokens;
	
	public static int id;

	/**
	 * @param user
	 * @param session
	 * @return String(message)
	 * @throws Exception
	 * @description login by using user-Email and User-Password
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Response> doLogin(@RequestBody User user, HttpSession session) throws Exception {
		Response response=new Response();
		User userLogin = userService.checkUserData(user.getUserEmail(), user.getUserPassword());
		if (userLogin == null) {
			response.setMessage("Email or Password invalid.");
			response.setStatus(-1);
			return new ResponseEntity<Response>(response,HttpStatus.BAD_REQUEST);
		} else {
			if (userLogin.isActive()) {
				session.setAttribute("User", userLogin);
				String token=tokens.generateToken(userLogin.getId());
				mailService.sendMail(user.getUserEmail(), "ToDoApp", token + " \ncopy this token for token crud operation");
				response.setMessage(token);
				response.setStatus(1);
				return new ResponseEntity<Response>(response,HttpStatus.OK);
			}
			response.setMessage("User didn't activated.");
			response.setStatus(-1);
			return new ResponseEntity<Response>(response,HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @param user
	 * @param session
	 * @param request
	 * @return String
	 * @throws Exception
	 * @description send the url with token to the Email.
	 * if the user email is registered. else return not registered message 
	 */
	@RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
	public ResponseEntity<Response> getPassword(@RequestBody User user, HttpSession session, HttpServletRequest request)
			throws Exception {
		Response response=new Response();
		User user1 = userService.getByEmail(user.getUserEmail());
		if (user1 == null) {
			response.setMessage("User email is not registered");
			response.setStatus(-1);
			return new ResponseEntity<Response>(response,HttpStatus.BAD_REQUEST);
		}
		session.setAttribute("Email", user.getUserEmail());
		String compactToken=tokens.generateToken(user1.getId());
		String url = request.getRequestURL().toString();
		url = url.substring(0, url.lastIndexOf("/")) + "/setPassword/" + compactToken;
		mailService.sendMail(user.getUserEmail(), "ToDoApp", url + " \ncopy this token for reset your password");
		response.setMessage(compactToken);
		response.setStatus(1);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	/**
	 * @param token
	 * @param response
	 * @return
	 * @throws Exception
	 * @description checks the token.
	 * if the token is valid then redirect to ResetPassword page, else send invalid token message to the user
	 */
	@RequestMapping(value="/setPassword/{Token:.+}", method = RequestMethod.GET)
	public ResponseEntity<String> setPassword(@PathVariable("Token") String token, HttpServletResponse response) throws Exception
	{
		id=tokens.validateToken(token);
		if(id>0)
		{
			response.sendRedirect("http://localhost:8080/ToDoApp/#!/setPassword");
			return ResponseEntity.status(HttpStatus.OK).body("Set new Password");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token");
	}
	
	/**
	 * @param user
	 * @param session
	 * @param request
	 * @return String(message)
	 * @throws Exception
	 * @description set the new password of the user
	 */
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public ResponseEntity<Response> setNewPassword(@RequestBody User user,
			HttpSession session,HttpServletRequest request) throws Exception {
		Response response=new Response();
			String isPasswordValid = userValidation.passwordValidate(user.getUserPassword());
			if (!isPasswordValid.equals("true")) {
				response.setMessage(isPasswordValid);
				response.setStatus(-1);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			String password = passwordEncrypt.encrypt(user.getUserPassword());
			user.setUserPassword(password);
			user.setId(id);
			String message = userService.passwordReset(user);
			response.setMessage(message);
			return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	/**
	 * @param request
	 * @return User Object
	 * @throws IOException
	 * @description return the details of the current login user
	 */
	@RequestMapping(value = "/currentUser", method = RequestMethod.GET)
	public ResponseEntity<User> currrentUser(HttpServletRequest request) throws IOException {
		int id = tokens.validateToken(request.getHeader("token"));
		User user = userService.retrieveById(id);
		if (user == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
}