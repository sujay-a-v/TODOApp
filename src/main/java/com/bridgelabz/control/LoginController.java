package com.bridgelabz.control;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Response doLogin(@RequestBody User user, HttpSession session) throws Exception {
		Response response=new Response();
		User userLogin = userService.checkUserData(user.getUserEmail(), user.getUserPassword());
		if (userLogin == null) {
			response.setMessage("Email or Password invalid.");
			response.setStatus(-1);
			return response;
		} else {
			if (userLogin.isActive()) {
				session.setAttribute("User", userLogin);
				String token=tokens.generateToken(userLogin.getId());
				mailService.sendMail(user.getUserEmail(), "ToDoApp", token + " \ncopy this token for token crud operation");
				response.setMessage(token);
				response.setStatus(1);
				return response;
			}
			response.setMessage("User didn't activated.");
			response.setStatus(-1);
			return response;
		}
	}

	@RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
	public Response getPassword(@RequestBody User user, HttpSession session, HttpServletRequest request)
			throws Exception {
		Response response=new Response();
		User user1 = userService.getByEmail(user.getUserEmail());
		if (user1 == null) {
			response.setMessage("User email is not registered");
			response.setStatus(-1);
			return response;
			//return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User email is not registered");
		}
		session.setAttribute("Email", user.getUserEmail());
		String compactToken=tokens.generateToken(user1.getId());
		/*String url = request.getRequestURL().toString();
		url = url.substring(0, url.lastIndexOf("/")) + "/resetPassword/" + compactToken;*/
		mailService.sendMail(user.getUserEmail(), "ToDoApp", compactToken + " \ncopy this token for reset your password");
		response.setMessage(compactToken);
		response.setStatus(1);
		return response;
		//return ResponseEntity.status(HttpStatus.OK).body("password reset link sent");
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public ResponseEntity<Response> setNewPassword(@RequestBody User user,
			HttpSession session,HttpServletRequest request) throws Exception {
		Response response=new Response();
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
  		if(id>0)
		{
			String isPasswordValid = userValidation.passwordValidate(user.getUserPassword());
			if (!isPasswordValid.equals("true")) {
				response.setMessage(isPasswordValid);
				response.setStatus(-1);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			System.out.println("new password "+user.getUserPassword());
			String password = passwordEncrypt.encrypt(user.getUserPassword());
			user.setUserPassword(password);
			user.setId(id);
			String message = userService.passwordReset(user);
			response.setMessage(message);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		else
		{
			response.setMessage("Invalid Id");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
}