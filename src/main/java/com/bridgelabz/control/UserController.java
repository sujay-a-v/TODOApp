package com.bridgelabz.control;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.bridgelabz.model.ErrorMessage;
import com.bridgelabz.model.User;
import com.bridgelabz.passwordencrypt.PasswordEncrypt;
import com.bridgelabz.service.MailService;
import com.bridgelabz.service.UserService;
import com.bridgelabz.tokens.Token;
import com.bridgelabz.validation.UserValidation;

@Controller
@RestController
public class UserController {

	/*
	 * @InitBinder public void InitBinder(WebDataBinder binder) {
	 * //binder.setDisallowedFields(new String[] {"studentMobile"});
	 * //SimpleDateFormat dateFormat=new SimpleDateFormat("dd**MM**yyyy");
	 * //binder.registerCustomEditor(Date.class, "studentDOB", new
	 * CustomDateEditor(dateFormat, false));
	 * //binder.registerCustomEditor(String.class, "studentName",new
	 * StudentNameEditor()); }
	 */

	@Autowired
	private UserService userService;

	@Autowired
	private UserValidation userValidation;

	@Autowired
	private ErrorMessage errorMassage;

	@Autowired
	private MailService mailService;
	
	@Autowired
	private PasswordEncrypt passwordEncrypt;
	
	@Autowired
	private Token tokens;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<ErrorMessage> doRegister(@RequestBody User user,
			HttpServletRequest request) throws Exception {

		String isValid = userValidation.userValidate(user);
		if (!isValid.equals("true")) {
			errorMassage.setResponseMessage(isValid);
			return new ResponseEntity<ErrorMessage>(errorMassage, HttpStatus.OK);

		} else {
			String isPasswordValid=userValidation.passwordValidate(user.getUserPassword());
			if(!isPasswordValid.equals("true"))
			{
				errorMassage.setResponseMessage(isPasswordValid);
				return new ResponseEntity<ErrorMessage>(errorMassage, HttpStatus.OK);
			}
			boolean status = userService.isUserExist(user.getUserEmail());
			if (status) {
				errorMassage.setResponseMessage("User email already exist");
				return new ResponseEntity<ErrorMessage>(errorMassage, HttpStatus.BAD_REQUEST);
			}

			String password=passwordEncrypt.encrypt(user.getUserPassword());
			user.setUserPassword(password);
			
			userService.saveUserData(user);
			String url = request.getRequestURL().toString();
			
			String compactToken=tokens.generateToken(user.getId());
			url = url.substring(0, url.lastIndexOf("/")) + "/active/" + compactToken;

			mailService.sendMail(user.getUserEmail(), "User Validate", url + " \nclick on the url to activate");

			errorMassage.setResponseMessage("RegisterSuccess");
			return new ResponseEntity<ErrorMessage>(errorMassage, HttpStatus.CREATED);
		}
	}

	@RequestMapping(value = "/active/{Token:.+}", method = RequestMethod.GET)
	public ResponseEntity<String> activeUser(@PathVariable("Token") String token)throws Exception {
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
			return ResponseEntity.status(HttpStatus.OK).body("User activated");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Id");
	}
}
