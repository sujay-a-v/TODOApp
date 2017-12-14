package com.bridgelabz.control;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bridgelabz.model.Response;
import com.bridgelabz.model.User;
import com.bridgelabz.service.UserService;
import com.bridgelabz.sociallogin.GoogleLogin;
import com.bridgelabz.tokens.Token;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class GoogleLoginController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private GoogleLogin googleConnection;
	
	@Autowired
	private Token tokens;
	@Autowired
	private Response response;
	
	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 * @description first get the login page URL and redirect to google login page
	 */
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

	/**
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws IOException
	 * 
	 * @description  get token by using code,
	 * 				get the user profile using token (Profile=Email,name,Profile-picture) 
	 */
	@RequestMapping(value="/getGoogleLogin", method = RequestMethod.GET)
	public ResponseEntity<String> getGoogleToken(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException
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
				String picture=mapper.readTree(googleProfile).get("picture").asText();
				googleUser.setProfile(picture);
				googleUser.setActive(true);
				userService.saveUserData(googleUser);
				int id=googleUser.getId();
				System.out.println("Google user id : "+id);
				if(id==0)
				{
					response.sendRedirect("http://localhost:8080/ToDoApp/#!/socialLogin");
				}
				else
				{
					String accessToken=tokens.generateToken(id);
					session.setAttribute("ToDoAccessToken", accessToken);
				}
			}
			else
			{
				String accessToken=tokens.generateToken(user.getId());
				session.setAttribute("ToDoAccessToken", accessToken);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("http://localhost:8080/ToDoApp/#!/socialLogin");
		return ResponseEntity.status(HttpStatus.OK).body("new Registered");
		
	}
	
	/**
	 * @param session
	 * @return String (message)
	 * @description getting social login token
	 */
	@RequestMapping(value="/getToken",method = RequestMethod.GET )
	public ResponseEntity<Response> getSocialLoginToken(HttpSession session)
	{
		String token=(String) session.getAttribute("ToDoAccessToken");
		response.setMessage(token);
		System.out.println("social token "+ token);
		if(token!=null)
		{
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
}
