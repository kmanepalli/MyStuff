package com.orbitz.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.solr.common.SolrInputDocument;

import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;

public class AirsitemapGenerator {

	public static void main(String ap[]) throws Exception {
		AirsitemapGenerator mapgen = new AirsitemapGenerator();
		FileReader fileReader = new FileReader(
				"/users/mkchakravarti/flights/10-31/orb_air.txt");
		System.out.println("its here");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		List<String> lines = new ArrayList<String>();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			lines.add(line);
		}

		FileReader fileReader1 = new FileReader(
				"/users/mkchakravarti/city_id_map_new.txt");
		System.out.println("its here"+lines.size());
		BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
		Map<String, String> codes = new HashMap<String, String>();
		String code = null;
		while ((code = bufferedReader1.readLine()) != null) {
			
			String v[] = code.split(",");
			//System.out.println(v[1]);
			codes.put(v[0], v[2] + "_" + v[1]);
			//System.out.println(codes.get(v[2]));
		}
		
		int cnt =0;
		
		Map<String,Integer> cnt_map = new HashMap<String, Integer>();
		PrintWriter writer = new PrintWriter( "/users/mkchakravarti/flights/11-14/url_orb.txt" );  
		for (String id : lines) {
			String v[] = id.split("_");
			
			if (Integer.parseInt(v[2]) > 0) {
				//System.out.println(v[1]);
				String or = codes.get(v[0]);
				String de = codes.get(v[1]);
				if (or != null && de != null) {
					//System.out.println(codes.get(v[0]));
					cnt++;
					String[] origs = codes.get(v[0]).split("_");
					String[] dests = codes.get(v[1]).split("_");
					String url =  origs[0] + "_" + dests[0];
					
					String finalUrl = "from-" + origs[0] + "-to-" + dests[0]
							+ "/from-" + origs[1].replace(" ", "_") + "-to-"
							+ dests[1].replace(" ", "_") + "/";
				//System.out.println(url + "|" +v[2]);
				writer.println(url + "|" +v[2]  + "|" + v[0] + "|" + v[1]);
				cnt_map.put(finalUrl, Integer.parseInt	(v[2]));
				}
			}
		}
		System.out.println(cnt_map.size());
		Map s = sortByValue(cnt_map);
		
		writer.close();
		//System.out.println(s.size());
		PrintWriter writer1 = new PrintWriter( "/users/mkchakravarti/flights/11-14/final_url_orb.txt" );  
		
		List<String> urls = new ArrayList<String>();
		for (Iterator<String> iterator = s.keySet().iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			String va = s.get(string).toString();
			writer1.println ("/flights/"+string+"|INDEX_FOLLOW");
			
			
			urls.add(string);
			//System.out.println(string);
		}
		writer1.close();
		for (int i = 0; i < 6; i++) {
			List<String> url1 = urls.subList(i*28900, (i+1)*28900);
			//mapgen.generateSitemap(url1,(i+1));
		}
		
	}
	
	
	
	public  void generateSitemap(List<String> urls,int i) throws MalformedURLException{
		File dir = new File("/Users/mkchakravarti/Documents/project/sitemap/flights/11-14/"+i+"/");
		dir.mkdirs();
		WebSitemapGenerator web = new WebSitemapGenerator("http://www.orbitz.com",dir) ;
	        
	        try {
	            for(String url:urls){
	            	    url = "http://www.orbitz.com/flights/"+url ; 
	                    WebSitemapUrl.Options op =new WebSitemapUrl.Options(url);
	                    op.changeFreq(ChangeFreq.MONTHLY);
	                    WebSitemapUrl u = new WebSitemapUrl(op) ;
	                    web.addUrl(u);
	                
	            }
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        web.write();
	}
	
	static <K,V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
            new Comparator<Map.Entry<K,V>>() {
                @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
                    int res = e1.getValue().compareTo(e2.getValue());
                    return res != 0 ? res : 1; // Special fix to preserve items with equal values
                }
            }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static Map sortByValue(Map map) {
	     List list = new LinkedList(map.entrySet());
	     Collections.sort(list, new Comparator() {
	          public int compare(Object o1, Object o2) {
	               return ((Comparable) ((Map.Entry) (o1)).getValue())
	              .compareTo(((Map.Entry) (o2)).getValue());
	          }
	     });

	    Map result = new LinkedHashMap();
	    for (Iterator it = list.iterator(); it.hasNext();) {
	        Map.Entry entry = (Map.Entry)it.next();
	        result.put(entry.getKey(), entry.getValue());
	    }
	    return result;
	} 

}
