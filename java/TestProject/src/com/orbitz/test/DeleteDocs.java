package com.orbitz.test;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public class DeleteDocs {

   public static final void main(String[] args) throws SolrServerException, TimeoutException, ExecutionException, InterruptedException, IOException{
       CommonsHttpSolrServer cds = new CommonsHttpSolrServer("http://indexedsearch.prod.o.com:8980/cds");
       System.out.println("print me " + cds);

       SolrQuery query = new SolrQuery();
       /*query.addFilterQuery("status:publish");
       //query.addFilterQuery("author:pmarzal");
       //query.addFilterQuery("id:62718_preview");
       //query.addFilterQuery("pos:ORB");
       query.addFilterQuery("contentType:Editorial");
       //query.addFilterQuery("contentPurpose:Editorial");
       //query.addFilterQuery("contentPurpose:Introduction");
       //query.addFilterQuery("id:3453*");
       query.addFilterQuery("source:CMS");
       //query.addFilterQuery("urlType:flights");*/
       query.addFilterQuery("contentPurpose:Introduction");
       query.addFilterQuery("urlType:flights");
       query.addFilterQuery("status:preview_deleted");
       query.addFilterQuery("pos:ORB");
       query.setRows(6000);
       System.out.println("QUERY : " + query);

       QueryResponse resp = cds.query(query);
       SolrDocumentList docList = (SolrDocumentList) resp.getResults();

       System.out.println(" docs to be moved ********** size "  + docList.size() + " ");

       for(SolrDocument doc : docList){
           System.out.println("to update " + doc.getFieldValue("id"));

           SolrInputDocument sdi = new SolrInputDocument();
           Collection <String> coll = doc.getFieldNames();
           for (String key : coll) {
               sdi.addField(key, doc.getFieldValue(key));
           }
           sdi.setField("status", "publish");//doc.getFieldValue("status") + "_deleted");
           cds.add(sdi);
       }
       cds.commit();

       cds.optimize();
         System.out.println("DONE");
     }

 }