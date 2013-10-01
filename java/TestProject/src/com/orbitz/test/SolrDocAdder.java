package com.orbitz.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public class SolrDocAdder {

	public static void main(String a[]) throws Exception{
		SolrServer solrServer = new CommonsHttpSolrServer(
		"http://kmanepalli.duncllc.com:8984/solr/flex/");
		SolrInputDocument inDoc = new SolrInputDocument();
		inDoc.addField("flex.id", "id_x22z");
		UpdateResponse resp;
		try {
			resp = solrServer.add(inDoc);
		
		System.out.println(resp);

		solrServer.commit();
		solrServer.optimize();
		
		SolrQuery solrLocrQuery = new SolrQuery();
        solrLocrQuery.setQueryType("standard");
        solrLocrQuery.setStart(0);
        solrLocrQuery.setRows(50000);
        QueryResponse queryLocResponse = solrServer.query(solrLocrQuery);
        System.out.println(solrLocrQuery);
        SolrDocumentList locList = queryLocResponse.getResults();
        System.out.println(locList.size());
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
