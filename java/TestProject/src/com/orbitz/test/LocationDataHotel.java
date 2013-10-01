package com.orbitz.test;

import java.io.PrintWriter;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class LocationDataHotel {

	public static void main(String are[]) {

		try {
			PrintWriter writer = new PrintWriter( "/users/mkchakravarti/loc_hot_nbr.txt" ); 
			SolrServer solrLocServer = new CommonsHttpSolrServer(
					"http://indexedsearch.prod.o.com:8980/location");
			
			SolrQuery solrHotQuery = new SolrQuery();
			solrHotQuery.setQueryType("hotelSearchForDynamo");
			solrHotQuery.set("pos", "ORB");
			solrHotQuery.set("locale", "en_US");
			
			//solrHotQuery.set("locId, val)
			solrHotQuery.setRows(1);
			SolrQuery solrLocrQuery = new SolrQuery();
			solrLocrQuery.setQueryType("dismax");
			solrLocrQuery.addFilterQuery("loc.level:[8 TO 10]");// OR loc.level:8");
			solrLocrQuery.setStart(0);
			solrLocrQuery.setRows(50000);
			solrLocrQuery.setFields("loc.id" , "loc.city_en_us","loc.state_en_us","loc.country_en_us","loc.locationName");
			QueryResponse queryLocResponse = solrLocServer.query(solrLocrQuery);
			System.out.println(solrLocrQuery);
			SolrDocumentList locList = queryLocResponse.getResults();
			
			for (SolrDocument doc : locList) {
				String loc = (String) doc.getFieldValue("loc.id");
				String city = (String) doc.getFieldValue("loc.city_en_us");
				String state = (String) doc.getFieldValue("loc.state_en_us");
				String country = (String) doc.getFieldValue("loc.country_en_us");
				String locName = (String) doc.getFieldValue("loc.locationName");
				
				solrHotQuery.set("locId", loc);
				
				//QueryResponse queryHotResponse = solrHotServer.query(solrHotQuery);
				//long num = queryHotResponse.getResults().getNumFound();
				 writer.println(loc +"|"+locName+ "|"+ city +"|"+state +"|"+country);
				//Thread.sleep(10);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
