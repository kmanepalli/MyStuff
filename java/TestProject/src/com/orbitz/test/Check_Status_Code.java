package com.orbitz.test;

import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;

import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class Check_Status_Code {
	public static void main(String a[]) throws Exception {
		String fls[] = { "timestamp", "id", "status", "media", "text_s",
				"title_s", "contentType", "publish", "lang",
				"publishTimeStamp", "effectiveEndDate", "effectiveStartDate",
				"partner", "posSource", "author", "source", "namedAttribute",
				"steerModuleKey", "urlMapping", "urlType", "contentPurpose",
				"pos", "locale" };

		for (int i = 0; i < 20; i++) {
			List<Map<String, Object>> ids = getData(i);
			PrintWriter writer = new PrintWriter(
					"/users/mkchakravarti/cds_content/cds_" + i + ".txt");
			for (Iterator<Map<String, Object>> iterator = ids.iterator(); iterator
					.hasNext();) {
				
				Map<String, Object> key = iterator.next();
				String str = "";
				for (String fl : fls) {
					Object obj = key.get(fl);
					str = str + "|" + obj;
				}
				//System.out.println(str);
				writer.println(str);

			}
			writer.close();
		}

	}

	private static List<Map<String, Object>> getData(int i)
			throws MalformedURLException, SolrServerException {
		String fls[] = { "timestamp", "id", "status", "media", "text_s",
				"title_s", "contentType", "publish", "lang",
				"publishTimeStamp", "effectiveEndDate", "effectiveStartDate",
				"partner", "posSource", "author", "source", "namedAttribute",
				"steerModuleKey", "urlMapping", "urlType", "contentPurpose",
				"pos", "locale" };
		List<Map<String, Object>> ids = new ArrayList<Map<String, Object>>();
		CommonsHttpSolrServer hotServer = new CommonsHttpSolrServer(
				"http://indexedsearch.prod.o.com:8980/cds");
		hotServer.setParser(new XMLResponseParser());
		SolrQuery solrLocrQuery = new SolrQuery();
		solrLocrQuery.setFields("timestamp", "id", "status", "media", "text_s",
				"title_s", "contentType", "publish", "lang",
				"publishTimeStamp", "effectiveEndDate", "effectiveStartDate",
				"partner", "posSource", "author", "source", "namedAttribute",
				"steerModuleKey", "urlMapping", "urlType", "contentPurpose",
				"pos", "locale");
		solrLocrQuery.addFilterQuery("pos:ORB");
		solrLocrQuery.addFilterQuery("contentType:TitleBodyMedialist");
		solrLocrQuery.addFilterQuery("contentPurpose:Introduction");
		// solrLocrQuery.addFilterQuery("air.duration:[2 TO 10]");
		solrLocrQuery.setRows(5);
		solrLocrQuery.setStart(i * 5);
		// solrLocrQuery.setQueryType("dismax");
		System.out.println(solrLocrQuery);
		QueryResponse queryLocResponse = hotServer.query(solrLocrQuery);
		SolrDocumentList locList = queryLocResponse.getResults();

		for (SolrDocument doc : locList) {
			Map<String, Object> objMap = doc.getFieldValueMap();
			Map<String, Object> flValMap = new HashMap<String, Object>();
			String str = "";
			for (String fl : fls) {
				Object t = doc.get(fl);
				if (t != null && t instanceof String) {
					t = ((String) t).replaceAll("\n", "");
					t = ((String) t).replaceAll("\t", "");

				}
				flValMap.put(fl, t);
				// str = t +"|"+str;
			}

			ids.add(flValMap);
		}
		return ids;
	}
}
