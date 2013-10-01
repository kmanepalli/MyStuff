package com.orbitz.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;

public class FlightsitemapGenerator {
	
	public static void main(String p[]) throws Exception{
		FileReader fileReader = new FileReader(
		"/users/mkchakravarti/flight_urls.csv");
		System.out.println("its here");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		List<String> lines = new ArrayList<String>();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			lines.add(line);
		}
		FlightsitemapGenerator mapgen = new FlightsitemapGenerator();
		for (int i = 0; i < 4; i++) {
			List<String> url1 = lines.subList(i*27215, (i+1)*27215);
			mapgen.generateSitemap(url1,(i+1));
		}
		
	}
	
	public  void generateSitemap(List<String> urls,int i) throws MalformedURLException{
		File dir = new File("/Users/mkchakravarti/Documents/project/sitemap/flights/"+i+"/");
		dir.mkdirs();
		WebSitemapGenerator web = new WebSitemapGenerator("http://www.orbitz.com",dir) ;
	        
	        try {
	            for(String url:urls){
	                    WebSitemapUrl.Options op =new WebSitemapUrl.Options(url);
	                    op.changeFreq(ChangeFreq.MONTHLY);
	                    WebSitemapUrl u = new WebSitemapUrl(op) ;
	                    web.addUrl(u);
	                
	            }
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        web.write();
	}

}
