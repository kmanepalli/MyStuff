package com.orbitz.test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.request.JSONResponseWriter;


public class LocationDataGen {
	
	public static void main(String a[]) throws Exception{ 
		HttpSolrServer solrLocServer = new HttpSolrServer("http://indexedsearch.prod.o.com:8980/location");   
		//solrLocServer.setParser(new JsonP)
		//solrLocServer.setParser(new JsonP)
		//solrLocServer.setParser(new XMLResponseParser());
		//solrLocServer.setRequestWriter(new JSONResponseWriter());
        SolrQuery solrLocQuery = new SolrQuery();
        String[] fls = new String[]{"loc.id","loc.popularity_EBAT_HOT","loc.popularity_HCRG_HOT","loc.popularity_HCL_HOT","loc.popularity_MJSE_HOT","loc.popularity_EBUK_HOT","loc.popularity_MJDK_HOT","loc.popularity_EBNL_HOT","loc.popularity_EBIE_HOT","loc.popularity_EBFR_HOT","loc.popularity_EBFI_HOT","loc.popularity_EBDE_HOT","loc.popularity_EBCH_HOT","loc.popularity_EBBE_HOT","loc.popularity_ORB_HOT","loc.popularity_CTIX_HOT"};
        //solrLocQuery.addSortField("loc.id", ORDER.asc);
       
        solrLocQuery.setQueryType("dismax");
        solrLocQuery.addFilterQuery("loc.id:(25960)");
       // solrLocQuery.setFields("loc.id,loc.popularity_EBAT_HOT,loc.popularity_HCRG_HOT,loc.popularity_HCL_HOT,loc.popularity_MJSE_HOT,loc.popularity_EBUK_HOT,loc.popularity_MJDK_HOT,loc.popularity_EBNL_HOT,loc.popularity_EBIE_HOT,loc.popularity_EBFR_HOT,loc.popularity_EBFI_HOT,loc.popularity_EBDE_HOT,loc.popularity_EBCH_HOT,loc.popularity_EBBE_HOT,loc.popularity_ORB_HOT,loc.popularity_CTIX_HOT");
        QueryResponse queryLocResponse = solrLocServer.query(solrLocQuery);
        System.out.println(solrLocQuery);
        SolrDocumentList locList = queryLocResponse.getResults();
        for(String fl:fls){
    		
    		System.out.print(fl+",");
    	}
        System.out.println("");
        for (SolrDocument doc : locList) {
        	for(String fl:fls){
        		Object obj=doc.getFieldValue(fl);
        		System.out.print(obj+",");
        	}
        	System.out.println("");
        }
	}

    public static void maina(String are[]) { 

        try {
            
            
           
            SolrServer solrLocServer = new CommonsHttpSolrServer("http://indexedsearch.prod.o.com:8980/location");
            
            FileReader fileReader = new FileReader("/Users/mkchakravarti/Documents/ans_mkt.txt");
            System.out.println("its here");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<String> lines = new ArrayList<String>();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }

                    
            SolrQuery solrLocrQuery = new SolrQuery();
            solrLocrQuery.setQueryType("dismax");
            solrLocrQuery.addFilterQuery("loc.city.code:[* TO *]","loc.level:10");
            solrLocrQuery.setStart(0);
            solrLocrQuery.setRows(8000);
            solrLocrQuery.setFields("loc.id","loc.airport.code,loc.city.code");
            QueryResponse queryLocResponse = solrLocServer.query(solrLocrQuery);
            System.out.println(solrLocrQuery);
            SolrDocumentList locList = queryLocResponse.getResults();
            Set<String>  li = new HashSet<String> ();
            for (SolrDocument doc : locList) {
                
                    String loc = (String) doc.getFieldValue("loc.id");
                    String cityCode = ((List) doc.getFieldValue("loc.city.code")).get(0).toString();
                    //String val = (String) doc.getFieldValue("loc.locationName");
                    String airport = (String) doc.getFieldValue("loc.airport.code");
                    //String country = (String) doc.getFieldValue("loc.country_en_us");
                    
                    Integer level = (Integer) doc.getFieldValue("loc.level");
                    System.out.println(loc+"|"+cityCode+"|"+airport);
                     }
            
              
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
