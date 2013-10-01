package com.orbitz.test;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.request.SolrQueryResponse;
import org.apache.solr.request.SolrRequestHandler;
import org.apache.solr.servlet.SolrDispatchFilter;
import org.slf4j.Logger;

public class UpdateDispatchFilter  extends SolrDispatchFilter {
	//public static final Logger logger = Logger.getLogger("TopHotelReviewsHandler.class");

	
	@Override
    protected void execute(HttpServletRequest req,
                           SolrRequestHandler handler, SolrQueryRequest sreq,
                           SolrQueryResponse rsp) {
        
        try {
        	//Map<String,String[]> v = new HashMap<String, String[]>();
        	SolrParams sParams = sreq.getParams();
        	sreq.getOriginalParams().getParameterNamesIterator();
        	Iterator<String> sp = sParams.getParameterNamesIterator();
        	while(sp.hasNext()){
        		sParams.get(sp.next());
        	}
        	
        	ModifiableSolrParams newParams = new ModifiableSolrParams(sParams);
        	newParams.set("WT", "xml");
        	
        	Iterator<String> sp1 = newParams.getParameterNamesIterator();
        	while(sp1.hasNext()){
        		newParams.get(sp1.next());
        	}
        	sreq.setParams(newParams);
            super.execute(req, handler, sreq, rsp);
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
         
        }
    }
}
