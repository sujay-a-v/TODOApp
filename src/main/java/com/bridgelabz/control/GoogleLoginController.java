package com.bridgelabz.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bridgelabz.model.User;
import com.bridgelabz.service.UserService;
import com.bridgelabz.sociallogin.GoogleLogin;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class GoogleLoginController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private GoogleLogin googleConnection;
	
	@RequestMapping(value="/googleLogin")
	public void googleLogin(HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		String googleUrl=GoogleLogin.generateURL();
		try
		{
			response.sendRedirect(googleUrl);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value="/getGoogleLogin", method = RequestMethod.GET)
	public ResponseEntity<String> getGoogleToken(HttpServletRequest request, HttpServletResponse response, HttpSession session)
	{
		String googleCode=(String)request.getParameter("code");
		System.out.println("googleCode : "+googleCode);
		String googleToken=GoogleLogin.googleAcceessToken(googleCode);
		System.out.println("googleToken : "+googleToken);
		String googleProfile=googleConnection.getProfileInfo(googleToken);
		System.out.println("googleProfile : "+googleProfile);
		
		ObjectMapper mapper=new ObjectMapper();
		try
		{
			String email=mapper.readTree(googleProfile).get("email").asText();
			System.out.println("Email ; "+email);
			User user=userService.getByEmail(email);
			if(user==null)
			{
				User googleUser=new User();
				googleUser.setUserEmail(email);
				
				String name=mapper.readTree(googleProfile).get("given_name").asText();
				googleUser.setUserName(name);
				System.out.println("Google User name : "+name);
				googleUser.setActive(true);
				userService.saveUserData(googleUser);
				int id=googleUser.getId();
				System.out.println("Google user id : "+id);
				if(id==0)
				{
					response.sendRedirect("http://localhost:8080/ToDoApp/login");
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
