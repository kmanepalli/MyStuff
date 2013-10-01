package com.orbitz.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;

public class GenerateSitemapXml {

	public static void main(String ap[]) throws Exception{
		String pos="EBUK";
		GenerateSitemapXml sitemap = new GenerateSitemapXml();
		List<String> urls = readData(pos);
		
		sitemap.generate(urls,pos,"http://www.ebookers.com");
		
		
	}
	private static List<String> readData(String pos) throws IOException{
		FileReader fileReader = new FileReader(
				"/users/mkchakravarti/Documents/hotelsFromLPCore."+pos+".csv");
		
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		List<String> lines = new ArrayList<String>();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			lines.add(line);
		}
		bufferedReader.close();
		return lines;
	}
	private  void generate(List<String> urls, String pos,String domain)
			throws MalformedURLException {
		
		File dir = new File(
				"/Users/mkchakravarti/Documents/project/sitemap/hotels/"+pos);
		dir.mkdirs();
		WebSitemapGenerator web = new WebSitemapGenerator(
				domain, dir);
		for (String url : urls) {
			
		 
		WebSitemapUrl.Options op = new WebSitemapUrl.Options(
				url);
		op.changeFreq(ChangeFreq.MONTHLY);
		WebSitemapUrl u = new WebSitemapUrl(op);
		web.addUrl(u);
		
		}
		web.write();
	}
}
