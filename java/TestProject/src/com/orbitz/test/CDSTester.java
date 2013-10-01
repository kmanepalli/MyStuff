package com.orbitz.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public class CDSTester {

	public static void main(String ar[]) throws SolrServerException,
			IOException {
		try {
			SolrServer server = new CommonsHttpSolrServer(
					"http://egcs02.qa1.o.com:8982/cds");
			SolrQuery solrQuery = new SolrQuery();
			solrQuery.addFilterQuery("contentType:TitleBodyMedialist");
			solrQuery.addFilterQuery("author:kkalyan");
			solrQuery.setStart(0);
			solrQuery.setRows(10000);
			QueryResponse queryLocResponse = server.query(solrQuery);
			SolrDocumentList locList = queryLocResponse.getResults();
			int i = 0;
			int min = 1;
			int hr = 1;
			List<SolrInputDocument> docList = new ArrayList<SolrInputDocument>();
			for (SolrDocument doc : locList) {

				min = (i % 100) + 1;
				hr = (i % 1000) + 1;
				GregorianCalendar cal = new GregorianCalendar(2012, 7, 13);
				GregorianCalendar cal1 = new GregorianCalendar(2022, 7, 13);
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");

				SolrInputDocument inputDoc = ClientUtils
						.toSolrInputDocument(doc);
				inputDoc.setField("author", "kkalyan");
				inputDoc.setField("effectiveStartDate",
						df.format(cal.getTime()));
				inputDoc.setField("effectiveEndDate", df.format(cal1.getTime()));
				System.out.println(df.format(cal.getTime()));
				i++;
				docList.add(inputDoc);

			}
			server.add(docList);
			server.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
