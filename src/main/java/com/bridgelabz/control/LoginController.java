package com.bridgelabz.control;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<String> doLogin(@RequestBody User user, HttpSession session) throws Exception {
		User userLogin = userService.checkUserData(user.getUserEmail(), user.getUserPassword());
		if (userLogin == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email or Password invalid.");
		} else {
			if (userLogin.isActive()) {
				session.setAttribute("User", userLogin);
				String token=tokens.generateToken(userLogin.getId());
				mailService.sendMail(user.getUserEmail(), "ToDoApp", token + " \ncopy this token for token crud operation");
				return ResponseEntity.status(HttpStatus.OK).body("User login successfully.");
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User didn't activated.");
		}
	}

	@RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
	public ResponseEntity<String> getPassword(@RequestBody User user, HttpSession session, HttpServletRequest request)
			throws Exception {
		User user1 = userService.getByEmail(user.getUserEmail());
		if (user1 == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User email is not registered");
		}
		session.setAttribute("Email", user.getUserEmail());
		String compactToken=tokens.generateToken(user1.getId());
		/*String url = request.getRequestURL().toString();
		url = url.substring(0, url.lastIndexOf("/")) + "/resetPassword/" + compactToken;*/
		mailService.sendMail(user.getUserEmail(), "ToDoApp", compactToken + " \ncopy this token for reset your password");
		return ResponseEntity.status(HttpStatus.OK).body("password reset link sent");
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public ResponseEntity<String> setNewPassword(@RequestBody User user,
			HttpSession session,HttpServletRequest request) throws Exception {
		
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
        /*if(id==0)
        {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Found");
        }
		System.out.println("User id for reset passwpord : "+id);*/
		if(id>0)
		{
			/*String email = (String) session.getAttribute("Email");
			User user1 = userService.getByEmail(email);
			if (user1 == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email not registered");
			}*/
	
			String isPasswordValid = userValidation.passwordValidate(user.getUserPassword());
			if (!isPasswordValid.equals("true")) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(isPasswordValid);
			}
			System.out.println("new password "+user.getUserPassword());
			String password = passwordEncrypt.encrypt(user.getUserPassword());
			user.setUserPassword(password);
			user.setId(id);
			String message = userService.passwordReset(user);
			return ResponseEntity.status(HttpStatus.OK).body(message);
		}
		else
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Id");
		}
	}
}