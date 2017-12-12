package com.bridgelabz.model;

import java.io.IOException;
import java.net.URI;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class LinkScrapper {
	
	public UrlData getUrlData(String url) throws IOException{
		String title=null;
		String imageURL=null;
		String domain=null;
		
		try{
			URI uri=new URI(url);
			domain=uri.getHost();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		Document document=Jsoup.connect(url).get();
		
		Elements meteOgTitle=document.select("meta[property=og:title]");
		title=document.title();
		System.out.println(title);
		
		if(meteOgTitle!=null){
			title=meteOgTitle.attr("content");
			if(title==null){
				title=document.title();
			}
		}
		
		Elements metaOgImage=document.select("meta[property=og:image]");
		if(metaOgImage!=null){
			imageURL=metaOgImage.attr("content");
		}
		return new UrlData(title,imageURL,domain);
		
	}
	

}
