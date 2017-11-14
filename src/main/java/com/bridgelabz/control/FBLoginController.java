package com.bridgelabz.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bridgelabz.model.User;
import com.bridgelabz.service.UserService;
import com.bridgelabz.sociallogin.FacebookLogin;
import com.bridgelabz.tokens.Token;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class FBLoginController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FacebookLogin fbConnection;
	
	@Autowired
	private Token tokens;
	
	@RequestMapping(value="/facebookLogin")
	public ResponseEntity<Void> fbLogin(HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		String fbUrl=fbConnection.getFBUrl();
		try
		{
			response.sendRedirect(fbUrl);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/getFacebookLogin", method = RequestMethod.GET)
	public ResponseEntity<String> getFacebookToken(HttpServletRequest request, HttpServletResponse response, HttpSession session)throws Exception
	{
		String fbCode=request.getParameter("code");
		System.out.println("code: "+fbCode);
		String fbtoken=fbConnection.getFbAccessToken(fbCode);
		System.out.println("token is: "+fbtoken);
		String fbProfileInfo=fbConnection.getFbProfileInfo(fbtoken);
		System.out.println("fbProfileInfo: "+fbProfileInfo);
		ObjectMapper mapper=new ObjectMapper();
		try
		{
			String email=mapper.readTree(fbProfileInfo).get("email").asText();
			User user=userService.getByEmail(email);
			if(user==null)
			{
				User fbUser=new User();
				fbUser.setUserEmail(email);
				String firstName=mapper.readTree(fbProfileInfo).get("first_name").asText();
				fbUser.setUserName(firstName);
	
				fbUser.setActive(true);
				userService.saveUserData(fbUser);
				int id=fbUser.getId();
				
				if(id==-1)
				{
					response.sendRedirect("http://localhost:8080/ToDoApp/login");
				}
				else
				{
					/*String accessToken=tokens.generateToken(id);
					session.setAttribute("ToDoAccessToken", accessToken);*/
				}
			}
			else
			{
				/*String accessToken=tokens.generateToken(user.getId());
				session.setAttribute("ToDoAccessToken", accessToken);*/
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
