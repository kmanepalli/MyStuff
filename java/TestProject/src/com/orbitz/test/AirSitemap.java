package com.orbitz.test;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class AirSitemap {
	 public List <String> getIds(int i) throws Exception {
	        List <String> ids = new ArrayList <String>();

	        SolrServer hotServer = new CommonsHttpSolrServer("http://wmapp303p.prod.orbitz.net:8980/air");
	        SolrQuery solrLocrQuery = new SolrQuery();

	       
	        solrLocrQuery.addFilterQuery("pos:EBUK");
	       // solrLocrQuery.addFilterQuery("air.duration:[2 TO 10]");
	       // solrLocrQuery.addFilterQuery("air.recorded.at:[ 2012-10-19T00:00:00.000Z TO * ]");
	        solrLocrQuery.setRows(30000);
	        solrLocrQuery.setStart(i*30000);
	        solrLocrQuery.setFields("air.dest.city.marketid","air.origin.city.marketid");
	        solrLocrQuery.setQueryType("dismax");
	        QueryResponse queryLocResponse = hotServer.query(solrLocrQuery);
	        SolrDocumentList locList = queryLocResponse.getResults();

	        for (SolrDocument doc : locList) {
	            try {
	                ids.add(doc.get("air.origin.city.marketid").toString() + "_" + doc.get("air.dest.city.marketid").toString());
	            } catch (Exception e) {

	            }
	        }
	        System.out.println(i);
	        return ids;
	    }
	 
	 public static void main(String[] a) throws Exception{
		 AirSitemap sitemap = new AirSitemap();
		 Map<String,Integer> idMap = new Hashtable<String,Integer>();
		 
	        for(int i=0;i<62;i++){
	        	List<String> ids = sitemap.getIds(i);
	        	 sitemap.addIds(idMap,ids);
	        }
	        PrintWriter writer = new PrintWriter( "/users/mkchakravarti/flights/11-7/ebuk_air.txt" );     
	        
	        for (Iterator<String> iterator = idMap.keySet().iterator(); iterator.hasNext();) {
				  String key = iterator.next();
				 Integer val= idMap.get(key);
	            writer.println( key + "_" +val );     
	       
	        }
	        writer.close(); 
	        System.out.println("done");
	 }
	 
	 private void addIds(Map<String,Integer> idMap,List<String> ids){
		 for (String id : ids) {
			if(idMap.containsKey(id)){
				Integer val = idMap.get(id);
				val = val + 1;
				idMap.put(id,val);
			}else{
				idMap.put(id, 1);
			}
		}
	 }
}
