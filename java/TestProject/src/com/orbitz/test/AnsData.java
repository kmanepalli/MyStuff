package com.orbitz.test;

import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class AnsData {
	
	public static void main(String a[]) throws Exception{
		SolrServer solrLocServer = new CommonsHttpSolrServer(
		"http://egcs02.prod.o.com:8982/location");
		
		SolrQuery solrLocrQuery = new SolrQuery();
		solrLocrQuery.setQueryType("dismax");
		solrLocrQuery.setStart(0);
		solrLocrQuery.setRows(50000);
		//solrLocrQuery.addFilterQuery("loc.level:7");
		solrLocrQuery.addField("loc.id,loc.locationName");
		QueryResponse queryLocResponse = solrLocServer.query(solrLocrQuery);
		System.out.println(solrLocrQuery);
		SolrDocumentList locList = queryLocResponse.getResults();
		HashMap <String,String> locMap = new HashMap<String,String>();
		for (SolrDocument doc : locList) {
			String locId =  (String)doc.getFieldValue("loc.id");
			String locationName = (String) doc.getFieldValue("loc.locationName");
			locMap.put(locId, locationName);
		}
		 solrLocServer = new CommonsHttpSolrServer(
		"http://egcs02.prod.o.com:8982/ans");
		
		SolrQuery solrAnsQuery = new SolrQuery();
		solrAnsQuery.setQueryType("dismax");
		solrAnsQuery.setRows(1);
		solrAnsQuery.setFacet(true);
		solrAnsQuery.setFacetLimit(-1);
		solrAnsQuery.addFacetField("ans.market.id");
		queryLocResponse = solrLocServer.query(solrAnsQuery);
		List<Count> cnts= queryLocResponse.getFacetField("ans.market.id").getValues();
		for(Count cnt:cnts){
			String locName = cnt.getName();
			System.out.println(locName +"|"+locMap.get(locName) +"|"+cnt.getCount());
		}
	}
	

}
