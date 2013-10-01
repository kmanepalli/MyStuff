package com.orbitz.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;

import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class Hotel_Market_Map {

	/*public static void main(String ap[]) throws Exception{
		SolrServer solrLocServer = new HttpSolrServer(
			"http://egcs02.prod.o.com:8982/hotel");
			
			SolrQuery solrAnsQuery = new SolrQuery();
			solrAnsQuery.setQueryType("standard");
			solrAnsQuery.setRows(1);
			solrAnsQuery.addFilterQuery("pos:ORB");
			solrAnsQuery.addFilterQuery("locale:en_US");
			solrAnsQuery.setFacet(true);
			solrAnsQuery.setFacetLimit(-1);
			solrAnsQuery.setFacetMinCount(1);
			solrAnsQuery.setRows(1);
			solrAnsQuery.addFacetField("hot.city.facet");
			QueryResponse queryLocResponse = solrLocServer.query(solrAnsQuery);
			List<Count> cnts= queryLocResponse.getFacetField("hot.city.facet").getValues();
			for(Count cnt:cnts){
				long locCnt = cnt.getCount();
				String locVal = cnt.getName();
				System.out.println(locCnt +"|"+locVal);
			}
	}*/
	
	
	public static void main(String a[]) throws Exception{
		FileReader fileReader = new FileReader(
		"/Users/mkchakravarti/flights/11-14/url_orb.txt");
		System.out.println("its here");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;
		Map<String,String> map1 = new HashMap<String,String>();
		while ((line = bufferedReader.readLine()) != null) {
			String l[]=line.split("\\|");
			String c[]=l[0].split("_");
			map1.put(l[2]+"_"+l[3], "from-"+c[0]+"-to-"+c[1]);
		}
		Iterator<String> itr = map1.values().iterator();
		//while(itr.hasNext()){
			//System.out.println(itr.next());
		//}
		FileReader fileReader1 = new FileReader(
		"/Users/mkchakravarti/flights/11-14/final_url_orb.txt");
		System.out.println("its here");
		BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
		String line1 = null;
		Map<String,String> map2 = new HashMap<String,String>();
		while ((line1 = bufferedReader1.readLine()) != null) {
			
			String l[]=line1.split("/");
			
			map2.put(l[2], line1);
		}
		//Iterator<String> itr1 = map2.keySet().iterator();
		//while(itr1.hasNext()){
		//	System.out.println(itr1.next());
		//}
		
		FileReader fileReader2 = new FileReader(
		"/Users/mkchakravarti/flights/content_location.txt");
		System.out.println("its here");
		BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
		String line2 = null;
		PrintWriter writer = new PrintWriter( "/users/mkchakravarti/flights/content_location_final.txt" ); 
		while ((line2 = bufferedReader2.readLine()) != null) {
			
			String l[]=line2.split("_");
			String line3 = line2.replace("_", "|");
			String key = l[0]+"_"+l[1];
			String val=map1.get(key);
			String uVal=map2.get(val);
			writer.println(line3+"|"+uVal);
		}
		
	}
	public  void main1(String ap[]) throws Exception{
		FileReader fileReader = new FileReader(
		"/Users/mkchakravarti/flights/11-14/url_orb_mini.txt");
		System.out.println("its here");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		List<String> lines = new ArrayList<String>();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			//System.out.println(line);
			lines.add(line);
		}       
		SolrServer solrLocServer = new CommonsHttpSolrServer(
			"http://indexedsearch.prod.o.com:8980/cds/");
		PrintWriter writer = new PrintWriter( "/users/mkchakravarti/flights/11-14/content_location_mini.txt" ); 
			SolrQuery solrAnsQuery = new SolrQuery();
			solrAnsQuery.setQueryType("standard");
			solrAnsQuery.addFilterQuery("pos:ORB","contentType:TitleBodyMedialist","urlType:flights","status:publish", "namedAttribute:\"pageType:ond\"");
			solrAnsQuery.setRows(1);
			for (String string : lines) {
				String[] l= string.split("\\|");
				//System.out.println(l[2]+"_"+l[3]);
				String unique="namedAttribute:\"originLocationId:"+l[2]+"\" AND namedAttribute:\"destinationLocationId:"+l[3]+"\"";
				String mash_orig="namedAttribute:\"originLocationId:"+l[2]+"\" AND namedAttribute:\"DEFAULT-CITY\"";
				String mash_dest="namedAttribute:\"originLocationId:DEFAULT-CITY\" AND namedAttribute:\"destinationLocationId:"+l[3]+"\"";
				String def="namedAttribute:\"originLocationId:DEFAULT-CITY\" AND namedAttribute:\"destinationLocationId:DEFAULT-CITY\"";
				
				solrAnsQuery.addFilterQuery(unique);
				//System.out.println(solrAnsQuery);
				QueryResponse queryLocResponse = solrLocServer.query(solrAnsQuery);
				SolrDocumentList locList = queryLocResponse.getResults();
				boolean unq= locList.getNumFound()>0?true:false;
				boolean m_orig=false;
				boolean m_dest=false;
				boolean de=false;
				if(locList.getNumFound() ==0){
					solrAnsQuery.clear();
					solrAnsQuery.setQueryType("standard");
					solrAnsQuery.addFilterQuery("pos:ORB","contentType:TitleBodyMedialist","urlType:flights","status:publish", "namedAttribute:\"pageType:ond\"");
					solrAnsQuery.setRows(1);
					
					solrAnsQuery.addFilterQuery(mash_orig);
					//System.out.println(solrAnsQuery);
					queryLocResponse = solrLocServer.query(solrAnsQuery);
					locList = queryLocResponse.getResults();
					 m_orig= locList.getNumFound()>0?true:false;
						solrAnsQuery.clear();
						solrAnsQuery.setQueryType("standard");
						solrAnsQuery.addFilterQuery("pos:ORB","contentType:TitleBodyMedialist","urlType:flights","status:publish", "namedAttribute:\"pageType:ond\"");
						solrAnsQuery.setRows(1);
					solrAnsQuery.addFilterQuery(mash_dest);
					//System.out.println(solrAnsQuery);
					queryLocResponse = solrLocServer.query(solrAnsQuery);
					locList = queryLocResponse.getResults();
					m_dest= locList.getNumFound()>0?true:false;
					solrAnsQuery.clear();
					solrAnsQuery.setQueryType("standard");
					solrAnsQuery.addFilterQuery("pos:ORB","contentType:TitleBodyMedialist","urlType:flights","status:publish", "namedAttribute:\"pageType:ond\"");
					solrAnsQuery.setRows(1);
					solrAnsQuery.addFilterQuery(def);
					//System.out.println(solrAnsQuery);
					queryLocResponse = solrLocServer.query(solrAnsQuery);
					locList = queryLocResponse.getResults();
					de= locList.getNumFound()>0?true:false;
					solrAnsQuery.removeFilterQuery(def);
				}
				System.out.println(l[2]+"_"+l[3]+"_"+unq+"_"+m_orig+"_"+m_dest+"_"+de);
				writer.println(l[2]+"_"+l[3]+"_"+unq+"_"+m_orig+"_"+m_dest+"_"+de);
				
				//Thread.sleep(10);
				
			}
			
			
			/*for (SolrDocument doc : locList) {
					String destId=null,origId=null,locId=null,destCode=null,origCode=null;
						ArrayList<String> obj = (ArrayList<String>)doc.get("namedAttribute");
						for(String str : obj){
							if(str.contains("destinationLocationId")){
								destId = str.substring(22,str.length());
							}	
							if(str.contains("originLocationId")){
								origId = str.substring(17,str.length());
							}
						}
						if((destId !=null && !destId.equals("DEFAULT-CITY")) && (origId !=null && !origId.equals("DEFAULT-CITY"))){
							System.out.println(destId +"|" + origId);
						}
						
						if((destId !=null && destId.equals("DEFAULT-CITY")) && (origId !=null && !origId.equals("DEFAULT-CITY"))){
							System.out.println("mas_orig"+destId +"|" + origId);
						}
						if((destId !=null && destId.equals("DEFAULT-CITY")) && (origId !=null && origId.equals("DEFAULT-CITY"))){
							System.out.println("def"+destId +"|" + origId);
						}
						if((destId !=null && !destId.equals("DEFAULT-CITY")) && (origId !=null && origId.equals("DEFAULT-CITY"))){
							System.out.println("mas_dest"+destId +"|" + origId);
						}
			}*/
						
			
	}
}
