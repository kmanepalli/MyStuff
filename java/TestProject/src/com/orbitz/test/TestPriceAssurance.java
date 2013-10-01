package com.orbitz.test;

import java.net.MalformedURLException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class TestPriceAssurance {

	
	public static void main(String a[]) throws MalformedURLException, SolrServerException{
		SolrServer hotServer = new CommonsHttpSolrServer("http://indexedsearch.prod.o.com:8980/cds");
        SolrQuery solrLocrQuery = new SolrQuery();
        /*solrLocrQuery.setParam("pos", "ORB");
        solrLocrQuery.setParam("locale", "en_US");*/
        solrLocrQuery.setRows(300);
        QueryResponse queryLocResponse = hotServer.query(solrLocrQuery);
        SolrDocumentList locList = queryLocResponse.getResults();

        for (SolrDocument doc : locList) {
            try {
                String str = doc.get("text_s").toString();
                if(str.toLowerCase().contains("price")){
                	if(str.toLowerCase().contains("assurance")){
                		String id = doc.get("id").toString();
                		String url = doc.get("title_s").toString();
                		
                		 System.out.println(id +"|"+ url);
                	}
               
                }
            } catch (Exception e) {

            }
        }
        
	}
}
