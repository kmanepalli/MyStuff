package com.orbitz.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NutchRelationGeneratorNew {

	public static void main(String ap[]) throws IOException{
		
		List<String> ignoredList = new ArrayList<String>();
		ignoredList.add("http://www.orbitz.com/");
		ignoredList.add("http://www.orbitz.com/car-rental/");
		ignoredList.add("http://www.orbitz.com/flights/");
		ignoredList.add("http://www.orbitz.com/vacations/");
		ignoredList.add("http://www.orbitz.com/travel-deals/");
		ignoredList.add("http://www.orbitz.com/travel-guide/");
		ignoredList.add("http://www.orbitz.com/things-to-do/");
		ignoredList.add("http://www.orbitz.com/hotels/");
		FileReader fileReader = new FileReader(
				"/Users/mkchakravarti/Documents/project/nutch/relation_tg.txt");
		
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		List<String> lines = new ArrayList<String>();
		String line = null;
		Map<String,Set<String>> inLink = new HashMap<String, Set<String>>();
		Set<String> fromLink = new HashSet<String>();
		List<Map<String,Set<String>>> relMaps = new ArrayList<Map<String,Set<String>>>();
		while ((line = bufferedReader.readLine()) != null) {
			lines.add(line);
			if(line.contains("Inlinks")){
				String lmod = line.substring(0,line.indexOf("Inlinks"));
				lmod = lmod.trim();
				inLink = new HashMap<String, Set<String>>();
				
				fromLink = new HashSet<String>();
				inLink.put(lmod, fromLink);
				//System.out.println(lmod);
				relMaps.add(inLink);
			}
			if(line.contains("fromUrl")){
				String lmod = line.substring(10,line.lastIndexOf("/")+1);
				//System.out.println(lmod);
				fromLink.add(lmod);
			}
		}
		
		//System.out.println(relMaps);
		int id=100;
		Map<String,Integer> masterMap = new HashMap<String, Integer>();
		
		for(Map<String,Set<String>> relMap:relMaps ){
			Iterator<String> itr =relMap.keySet().iterator();
			//System.out.println(relMap.size());
			while(itr.hasNext()){
				String url = itr.next();
				
				if(!ignoredList.contains(url)){
					Set<String> s = relMap.get(url);
					id=id+1;
					int to=id;
					//if(s.size()>1){
					if(masterMap.get(url)!=null){
						to = masterMap.get(url);
					
					}
					//System.out.println(to+"	"+url);
					masterMap.put(url,id);
						
					for(String str:s){
						id++;
						int from = id;
						if(masterMap.get(url)!=null){
							from = masterMap.get(url);
						
						}
						//System.out.println(from+"	"+str);
						//System.out.println(id+"	"+from+"	"+"LINK");
						}
					}
					id++;
				//}
				
			}
		}
		Iterator<String> itr2 = masterMap.keySet().iterator();
		while(itr2.hasNext()){
			String url = itr2.next();
			System.out.println(masterMap.get(url)+"	"+url);
		}
		
		for(Map<String,Set<String>> relMap:relMaps ){
			Iterator<String> itr =relMap.keySet().iterator();
			//System.out.println(relMap.size());
			while(itr.hasNext()){
				String url = itr.next();
				
				if(!ignoredList.contains(url)){
					Set<String> s = relMap.get(url);
					id=id+1;
					int to=id;
					//if(s.size()>1){
					if(masterMap.get(url)!=null){
						to = masterMap.get(url);
					
					}
					//System.out.println(to+"	"+url);
					//masterMap.put(url,id);
						
					for(String str:s){
						id++;
						int from = id;
						if(masterMap.get(url)!=null){
							from = masterMap.get(url);
						
						}
						//System.out.println(from+"	"+str);
						//System.out.println(id+"	"+from+"	"+"LINK");
						System.out.println(masterMap.get(str)+ "	"+ masterMap.get(url)+ "	"+"LINK" );
						}
					}
					id++;
				//}
				
			}
		}
		
		
		bufferedReader.close();
		
	}
}

