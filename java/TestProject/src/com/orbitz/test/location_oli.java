package com.orbitz.test;

import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class location_oli {
	public static void main(String are[]) throws Throwable {
		SolrServer solrLocServer = new CommonsHttpSolrServer(
				"http://egcs02.prod.o.com:8982/location");
		PrintWriter writer = new PrintWriter( "/users/mkchakravarti/flights/loc.txt" ); 
		List<String> flList = new ArrayList<String>();
		int i = 0;
		for(int k =0 ; k<2;k++){
		SolrQuery solrLocrQuery = new SolrQuery();
		solrLocrQuery.setQueryType("dismax");
		solrLocrQuery.setStart(0);
		solrLocrQuery.setRows(1);
		solrLocrQuery.addFilterQuery("loc.level:7");
		QueryResponse queryLocResponse = solrLocServer.query(solrLocrQuery);
		System.out.println(solrLocrQuery);
		SolrDocumentList locList = queryLocResponse.getResults();
		Set<String> li = new HashSet<String>();
		
		for (SolrDocument doc : locList) {
			if (i == 0) {
				Collection<String> fls = doc.getFieldNames();
				for (String fl : fls) {
					if (fl.contains("loc.city.name")) {
						flList.add(fl);
					}
				}
			}
			String loc ="";
			for(String fl: flList){
			List<String> cityList =  (ArrayList)doc.getFieldValue(fl);
			
			loc = loc +cityList.get(0)+ "|";
			}
			 writer.println( loc); 
			System.out.println(loc);
			i++;
		}
		}
		String locName= "";
		
		for(String fl: flList){
			locName = locName + fl + "|";
		}
		System.out.println( locName);
	}
}
