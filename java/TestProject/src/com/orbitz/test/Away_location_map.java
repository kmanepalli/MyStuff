package com.orbitz.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Away_location_map {

	public static void main(String s[]) throws Throwable{
		Away_location_map away = new Away_location_map();
		FileReader fileReader = new FileReader(
				"/Users/mkchakravarti/Google Drive/away-non-orb-ltlong.txt");
		System.out.println("its here");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;
		PrintWriter writer = new PrintWriter( "/users/mkchakravarti/Google Drive/away-orb-map.txt");
		int i=0;
		while ((line = bufferedReader.readLine()) != null) {
			
			String[] l=line.split("\\|");
			//lines.add(l[0]);
			//System.out.println(l[1]);
			String key=l[1];
			key = key.replace(" ", "_");
			String keyword = away.fetchKeywordUrl(key,line); 
			String name = away.fetchExactUrl(key, line);
			//String latlong = away.fetchlatLongurl(l[7],l[8], line);
			//System.out.println(line+"|"+name);
			writer.println(line+"|"+keyword+"|"+name);
			i++;
			System.out.println(i);
		}
		//away.fetchUrl(); 
		bufferedReader.close();
	}
	
	public  String fetchKeywordUrl(String input, String origLine){
		 //String url = "http://egcmsref01.prod.o.com:10000/deals/module/redirect/ORB/en_US/301/flights/from-"+orig+"-to-"+dest;
		//String url = "http://teakettle.qa1.o.com/location/3223778/en_US";
		//	String url = "http://teakettle.qa1.o.com//en_US/coordinate/41.9066/12.4602/10/?locationType=POI";
		String url = "http://egburner01.prod.o.com:1156/location/keyword/"+input+"/en_US";
		 String output="";
        try {
       	// System.out.println(url);
            URL u = new URL(url);
            String line = null;
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setReadTimeout(3000);

            connection.connect();
            int respCode = connection.getResponseCode();
            
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();

            while ((line = rd.readLine()) != null) {
                sb.append(line + '\n');
            }
           
            if(respCode == 200){
            	String str = sb.toString();
            	str = str.replaceAll("\\<[^>]*>","");
            	String id = str.substring(str.indexOf("id"),str.indexOf("location_type"));
            	String name = str.substring(str.indexOf("name:"),str.indexOf("code:"));
            	String lat =  str.substring(str.indexOf("latitude:"),str.indexOf("longitude:"));
            	String lon =  str.substring(str.indexOf("longitude:"),str.indexOf("city_code:"));
            	String state = str.substring(str.indexOf("state_province_name:"),str.indexOf("country_name:"));
            	String country = str.substring(str.indexOf("country_name:"),str.length()-1);
            	output = id+"|"+name+"|"+state+"|"+country+"|"+lat+"|"+lon;

            	//System.out.println(origLine+"|"+output);
            }
           
            connection.disconnect();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return output;
	}
	public  String fetchlatLongurl(String lati,String longi, String origLine){
		 //String url = "http://egcmsref01.prod.o.com:10000/deals/module/redirect/ORB/en_US/301/flights/from-"+orig+"-to-"+dest;
		//String url = "http://teakettle.qa1.o.com/location/3223778/en_US";
			String url = "http://egburner01.prod.o.com:1156/location/en_US/coordinate/"+lati+"/"+longi+"/10/?locationType=POI&locationType=CITY";
		//String url = "http://egburner01.prod.o.com:1156/location/keyword/"+input+"/en_US";
		 String output="";
       try {
      	// System.out.println(url);
           URL u = new URL(url);
           String line = null;
           HttpURLConnection connection = (HttpURLConnection) u.openConnection();
           connection.setRequestMethod("GET");
           connection.setDoOutput(true);
           connection.setReadTimeout(3000);

           connection.connect();
           int respCode = connection.getResponseCode();
           
           BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
           StringBuilder sb = new StringBuilder();

           while ((line = rd.readLine()) != null) {
               sb.append(line + '\n');
           }
          
           if(respCode == 200){
           	String str = sb.toString();
           	str = str.replaceAll("\\<[^>]*>","");
           	String id = str.substring(str.indexOf("id"),str.indexOf("location_type"));
           	String name = str.substring(str.indexOf("name:"),str.indexOf("code:"));
           	String lat =  str.substring(str.indexOf("latitude:"),str.indexOf("longitude:"));
           	String lon =  str.substring(str.indexOf("longitude:"),str.indexOf("city_code:"));
           	String state = str.substring(str.indexOf("state_province_name:"),str.indexOf("country_name:"));
           	String country = str.substring(str.indexOf("country_name:"),str.length()-1);
           	output = id+"|"+name+"|"+state+"|"+country+"|"+lat+"|"+lon;

           	//System.out.println(origLine+"|"+output);
           }
          
           connection.disconnect();
       } catch (MalformedURLException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       } catch (Exception e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
       return output;
	}
	public  String fetchExactUrl(String input, String origLine){
		 //String url = "http://egcmsref01.prod.o.com:10000/deals/module/redirect/ORB/en_US/301/flights/from-"+orig+"-to-"+dest;
		//String url = "http://teakettle.qa1.o.com//en_US/coordinate/41.9066/12.4602/10/?locationType=POI";
		String url = "http://egburner01.prod.o.com:1156/location/name/"+input+"/en_US";
		 String output="";
       try {
      	// System.out.println(url);
           URL u = new URL(url);
           String line = null;
           HttpURLConnection connection = (HttpURLConnection) u.openConnection();
           connection.setRequestMethod("GET");
           connection.setDoOutput(true);
           connection.setReadTimeout(3000);

           connection.connect();
           int respCode = connection.getResponseCode();
           
           BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
           StringBuilder sb = new StringBuilder();

           while ((line = rd.readLine()) != null) {
               sb.append(line + '\n');
           }
          
           if(respCode == 200){
           	String str = sb.toString();
           	str = str.replaceAll("\\<[^>]*>","");
           	String id = str.substring(str.indexOf("id"),str.indexOf("location_type"));
           	String name = str.substring(str.indexOf("name:"),str.indexOf("code:"));
           	String lat =  str.substring(str.indexOf("latitude:"),str.indexOf("longitude:"));
           	String lon =  str.substring(str.indexOf("longitude:"),str.indexOf("city_code:"));
           	String state = str.substring(str.indexOf("state_province_name:"),str.indexOf("country_name:"));
           	String country = str.substring(str.indexOf("country_name:"),str.length()-1);
           	output = id+"|"+name+"|"+state+"|"+country+"|"+lat+"|"+lon;

           	//System.out.println(origLine+"|"+output);
           }
          
           connection.disconnect();
       } catch (MalformedURLException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       } catch (Exception e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
       return output;
	}
}
