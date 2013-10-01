package com.orbitz.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NutchRelationGenerator {

	public static void main(String ap[]) throws IOException{
		
		List<String> ignoredList = new ArrayList<String>();
		ignoredList.add("http://www.orbitz.com/");
		ignoredList.add("http://www.orbitz.com/car-rental/");
		ignoredList.add("http://www.orbitz.com/flights/");
		ignoredList.add("http://www.orbitz.com/vacations/");
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
		for(Map<String,Set<String>> relMap:relMaps ){
			Iterator<String> itr =relMap.keySet().iterator();
			//System.out.println(relMap.size());
			while(itr.hasNext()){
				String url = itr.next();
				
				if(!ignoredList.contains(url)){
					//System.out.println(id +"	"+url);
					String relation = ""+id;
					Set<String> s = relMap.get(url);
					id=id+1;
					relation = relation+"	"+id+"	"+"LINK";
					//System.out.println(id +"	"+ s.toArray()[0]);
					System.out.println(relation);
					id=id+10;
				}
				
			}
		}
		bufferedReader.close();
		
	}
}
