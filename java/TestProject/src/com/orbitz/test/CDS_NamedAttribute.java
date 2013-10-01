package com.orbitz.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.json.JSONObject;

public class CDS_NamedAttribute {

	@SuppressWarnings("rawtypes")
	public static void main(String a[]) throws Exception {
		SolrServer solrLocServer = new CommonsHttpSolrServer(
				"http://egcs02.prod.o.com:8982/cds");
		//HashMap<String,String> locMap = getLocMap();
		SolrQuery solrQuery = new SolrQuery();
		// solrAnsQuery.setQueryType("standard");
		solrQuery.setRows(1000);
		solrQuery.addFilterQuery("status:preview");
		solrQuery.addFilterQuery("urlType:hotels");
		solrQuery.addFilterQuery("namedAttribute:locationId*");
		solrQuery.setFields("namedAttribute,id,pos,locale,urlType");
		solrQuery.addFilterQuery("contentPurpose:Introduction");

		QueryResponse queryLocResponse = solrLocServer.query(solrQuery);
		SolrDocumentList locList = queryLocResponse.getResults();
		PrintWriter writer = new PrintWriter( "/users/mkchakravarti/flights/cds_named_attr_1" );     
		for (SolrDocument doc : locList) {
			try {
				String destId=null,origId=null,locId=null,destCode=null,origCode=null;
					ArrayList<String> obj = (ArrayList<String>)doc.get("namedAttribute");
					for(String str : obj){
						if(str.contains("locationId") && !str.contains("DEFAULT")){
							System.out.println(str);
							destId = str.substring(11,str.length());
						}
						if(str.contains("originLocationId")){
							origId = str.substring(17,str.length());
						}
						
					}
					if(destId !=null){
						String pos = ((ArrayList)doc.get("pos")).get(0).toString();
						String locale = ((ArrayList)doc.get("locale")).get(0).toString();
						String urlType = ((ArrayList)doc.get("urlType")).get(0).toString();
						
						String contentId = (String)doc.get("id");
						//destCode=locMap.get(destId);
						//origCode=locMap.get(origId);
						//String url = fetchUrl(origCode,destCode);
						String url = fetchUrl(destId,pos,locale,urlType);
						//System.out.println(contentId +"|"+url);
						String str = contentId +"|"+url +"|"+pos+"|"+ locale +"|"+urlType+"|"+destId ;
						//System.out.println(str);
						writer.println(contentId +"|"+url +"|"+pos+"|"+ locale +"|"+urlType+"|"+destId ); 
						Thread.sleep(20);
					}
			} catch (Exception e) {

			}
		}
		writer.close(); 
	}
	
	public static String fetchUrl(String orig,String pos,String locale,String urlType){
		 //String url = "http://egcmsref01.prod.o.com:10000/deals/module/redirect/ORB/en_US/301/flights/from-"+orig+"-to-"+dest;
		String url = "http://egcmsref01.prod.o.com:10000/deals/module/redirect/"+pos+"/"+locale+"/301//"+urlType+"/.hd"+orig;
		 String urlStr ="";
         try {
        	 System.out.println(url);
             URL u = new URL(url);
             String line = null;
             HttpURLConnection connection = (HttpURLConnection) u.openConnection();
             connection.setRequestMethod("GET");
             connection.setDoOutput(true);
             connection.setReadTimeout(3000);

             connection.connect();
             BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
             StringBuilder sb = new StringBuilder();

             while ((line = rd.readLine()) != null) {
                 sb.append(line + '\n');
             }

             JSONObject obj = new JSONObject(sb.toString());
             
             //JSONObject o = obj.getJSONObject("redirectUrl");
             //System.out.println();
             //String canUrl = o.getString(id);
             //canUrl = canUrl.replace("cmsref.", "");
              urlStr = (String)obj.get("redirectUrl");
             if(urlStr !=null){
             	urlStr = urlStr.replace("cmsref.", "");
             	
             }
            
             connection.disconnect();
         } catch (MalformedURLException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         } catch (Exception e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
         return urlStr;
	}
	
	public static HashMap <String,String> getLocMap() throws Exception{
		SolrServer solrLocServer = new CommonsHttpSolrServer(
		"http://egcs02.prod.o.com:8982/location");
		
		SolrQuery solrLocrQuery = new SolrQuery();
		solrLocrQuery.setQueryType("dismax");
		solrLocrQuery.setStart(0);
		solrLocrQuery.setRows(50000);
		solrLocrQuery.addFilterQuery("loc.level:7");
		solrLocrQuery.addFilterQuery("loc.city.code:[* TO *]");
		solrLocrQuery.addField("loc.id,loc.city.code");
		QueryResponse queryLocResponse = solrLocServer.query(solrLocrQuery);
		System.out.println(solrLocrQuery);
		SolrDocumentList locList = queryLocResponse.getResults();
		HashMap <String,String> locMap = new HashMap<String,String>();
		for (SolrDocument doc : locList) {
			String locId =  (String)doc.getFieldValue("loc.id");
			ArrayList<String> locationName = (ArrayList<String>) doc.getFieldValue("loc.city.code");
			locMap.put(locId, locationName.get(0));
		}
		//HashMap <String,String> locMap = new HashMap<String,String>();
		return locMap;
	}
}
