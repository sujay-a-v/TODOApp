package com.bridgelabz.sociallogin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("deprecation")
public class FacebookLogin {
	
	public static String FbAppId="155180495087574";
	public static String FbAppSecret="7488f15df970e979147b2b8fac5e3d81";
	public static String RedirectUri="http://localhost:8080/ToDoApp/getFacebookLogin";
	public static String USER_ACCESS_URL = "https://graph.facebook.com/v2.9/me?access_token=";
	public static String BINDING = "&fields=id,name,email,first_name,last_name,picture";
	
	public String getFBUrl()
	{
		String fbLoginUrl="";
		try
		{
			fbLoginUrl="http://www.facebook.com/dialog/oauth?" + "client_id="+
					FbAppId + "&redirect_uri="+ URLEncoder.encode(RedirectUri)+"&scope=public_profile,email";
			System.out.println("fbLoginUrl: "+fbLoginUrl);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return fbLoginUrl;
	}
	
	public String getFbAccessToken(String code)
	{
		
		String fburl="client_id="+FbAppId
				+ "&redirect_uri="+ URLEncoder.encode(RedirectUri)
				+"&client_secret="+FbAppSecret 
				+"&code="+code;
		
		try
		{
			URL url=new URL("https://graph.facebook.com/v2.10/oauth/access_token?"+fburl);
			URLConnection urlConnection=url.openConnection();
			urlConnection.setDoOutput(true);
			
			OutputStreamWriter writer= new OutputStreamWriter(urlConnection.getOutputStream());
			writer.write(fburl);
			System.out.println("fbUrl "+fburl);
			writer.flush();
			
			String fbResponse="";
			BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			
			String line;
			while((line=bufferedReader.readLine())!=null)
			{
				fbResponse=fbResponse+line;
			}
			
			ObjectMapper objectMapper=new ObjectMapper();
			String fbToken=objectMapper.readTree(fbResponse).get("access_token").asText();
			System.out.println("fbToken: "+fbToken);
			return fbToken;
		}
		catch (Exception e) {
			e.printStackTrace();
			}
		return fburl;
	}
	
	public String getFbProfileInfo(String fbAccessToken)
	{
		try
		{
			URL fbURL=new URL(USER_ACCESS_URL+fbAccessToken+BINDING);
			URLConnection urlConnection=fbURL.openConnection();
			BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String fbProfileInfo="";
			String info;
			while((info=bufferedReader.readLine())!=null)
			{
				fbProfileInfo=fbProfileInfo+info;
			}
			return fbProfileInfo;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
