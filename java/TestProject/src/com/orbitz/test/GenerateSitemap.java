package com.orbitz.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;

public class GenerateSitemap {

	public static void main(String are[]) throws Exception {
		FileReader fileReader = new FileReader(
				"/users/mkchakravarti/flights/hotel_dynamo_country.txt");
		System.out.println("its here");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		List<String> lines = new ArrayList<String>();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			lines.add(line);
		}
		File dir = new File(
				"/Users/mkchakravarti/Documents/project/sitemap/hotels/");
		dir.mkdirs();
		WebSitemapGenerator web = new WebSitemapGenerator(
				"http://www.orbitz.com", dir);

		try {
			for (String url : lines) {
				String urlcv = url.replace("www.orbitz.com", "cmsref.www.orbitz.com");
				URL urlc = new URL(urlcv);
				URLConnection connection = urlc.openConnection();
				System.out.println(url);
				try {
					connection.connect();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// Cast to a HttpURLConnection
				if (connection instanceof HttpURLConnection) {
					HttpURLConnection httpConnection = (HttpURLConnection) connection;
					int code = 200;
					try {
						code = httpConnection.getResponseCode();
					} catch (Exception e) {
						System.out.println("e=="+url);
						e.printStackTrace();
					}finally{
						httpConnection.disconnect();
					}
					
					if (code == 200 || code==301) {
						WebSitemapUrl.Options op = new WebSitemapUrl.Options(
								url);
						op.changeFreq(ChangeFreq.MONTHLY);
						WebSitemapUrl u = new WebSitemapUrl(op);
						web.addUrl(u);
					}else{
						System.out.println("f=="+url);
					}
				}
				Thread.sleep(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		web.write();
	}
}
