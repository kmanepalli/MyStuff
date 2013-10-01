package com.orbitz.test;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class CDS_Greg {
	@SuppressWarnings("unchecked")
	public static void main(String a[]) throws Exception {
		//PrintWriter writer = new PrintWriter( "/users/mkchakravarti/flights/cds_greg" );
		SolrServer solrLocServer = new CommonsHttpSolrServer(
				"http://indexedsearch.prod.o.com:8980/cds");
		// HashMap<String,String> locMap = getLocMap();
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.addFilterQuery("status:publish","contentType:TitleBodyMedialist");
		solrQuery.setQueryType("standard");
		for(int i=0;i<7;i++){
			PrintWriter writer = new PrintWriter( "/users/mkchakravarti/flights/cds_greg_ttl_"+i );
		solrQuery.setStart(i*20000);
		solrQuery.setRows(20000);
		solrQuery.setFields("urlType", "urlMapping", "contentPurpose",
				"contentType", "namedAttribute", "locale", "pos","steerModuleKey");
		QueryResponse queryLocResponse = solrLocServer.query(solrQuery);
		System.out.println(solrQuery);
		SolrDocumentList locList = queryLocResponse.getResults();
		for (SolrDocument doc : locList) {
			try {
				String id = (String) doc.get("id");
				String contentPurpose = (String) doc.get("contentPurpose");
				
				ArrayList<String> steerModuleKey = (ArrayList<String>) doc.get("steerModuleKey");
				String contentType = (String) doc.get("contentType");
				ArrayList<String> obj = (ArrayList<String>) doc
						.get("namedAttribute");
				ArrayList<String> ut = (ArrayList<String>) doc.get("urlType");
				ArrayList<String> um = (ArrayList<String>) doc.get("urlMapping");
				ArrayList<String> pos = (ArrayList<String>) doc.get("pos");
				ArrayList<String> locale = (ArrayList<String>) doc
						.get("locale");
				String finalStr = id + "_" + contentPurpose + "_" + contentType
						+ "_" + app(obj) + "_" + app(ut)+"_"+app(um) + "_" + app(pos) + "_"
						+ app(locale)+"_"+app(steerModuleKey);
				writer.println(finalStr);

			} catch (Exception e) {
				System.out.println(e);
			}
		}
		}
		
	}

	public static String app(List<String> obj) {

		StringBuilder sb = new StringBuilder();
		if (obj != null) {
			for (String s : obj) {
				sb.append(s).append('_');
			}

			sb.deleteCharAt(sb.length() - 1); // delete last comma
		}
		String newString = sb.toString();
		return newString;

	}
}
