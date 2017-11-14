package com.bridgelabz.sociallogin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GoogleLogin {
	//AIzaSyAGKpuKwUV79bUxLqsQoAFwyLXz8gIeabo
	public static String googleId="81834731882-3d7rrr8gjv1qeqv376smphhakelsdssu.apps.googleusercontent.com";
	public static String secretKey="hAzZmpNUhCpMyh9ijgCphvSZ";
	public static String redirectUri="http://localhost:8080/ToDoApp/getGoogleLogin";
	static String googleLoginURL="";

	
	static{
		try
		{
			googleLoginURL="https://accounts.google.com/o/oauth2/auth?client_id="+googleId
					+"&redirect_uri="+URLEncoder.encode(redirectUri, "UTF-8")
					+"&response_type=code"+"&scope=profile email"
					+"&approval_prompt=force" + "&access_type=offline";
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String generateURL()
	{
		
		return googleLoginURL;
	}

	public static String googleAcceessToken(String code)
	{
		String googleUrlParameters="";
		try {
			googleUrlParameters = "code="+code
			+"&client_id="+googleId
			+"&redirect_uri="+URLEncoder.encode(redirectUri, "UTF-8")
			+"&client_secret="+secretKey
			+"&grant_type=authorization_code";
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		try
		{
			URL url=new URL("https://accounts.google.com/o/oauth2/token");
			URLConnection urlConnection=url.openConnection();
			urlConnection.setDoOutput(true);
			OutputStreamWriter writer=new OutputStreamWriter(urlConnection.getOutputStream());
			writer.write(googleUrlParameters);
			writer.flush();
			
			BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			
			String response="";
			String line;
			while((line=bufferedReader.readLine())!=null)
			{
				response=response+line;
			}
			ObjectMapper mapper=new ObjectMapper();
			String googleToken;
			try
			{
				googleToken=mapper.readTree(response).get("access_token").asText();
				System.out.println("google Token : "+ googleToken);
				return googleToken;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getProfileInfo(String accessToken)
	{
		try
		{
			URL googleProfileUrl=new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token="+accessToken);
			URLConnection urlConnection=googleProfileUrl.openConnection();
			BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			
			String googleProfileinfo="";
			String line;
			while((line=bufferedReader.readLine())!=null)
			{
				googleProfileinfo=googleProfileinfo+line;
			}
			return googleProfileinfo;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
