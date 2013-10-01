package com.orbitz.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AirRankGenerator {

	public static void main(String a[]) throws Exception {
		FileReader fileReader1 = new FileReader(
				"/Users/mkchakravarti/flights/11-14/url_orb.txt");
		BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
		String code = null;
		Map<String,Set<String>> cdMap = new HashMap<String,Set<String>>();
		while ((code = bufferedReader1.readLine()) != null) {

			String v[] = code.split("\\|");
			String cd = v[0];
			String od[] = cd.split("_");
			String origin = od[0];
			String dest = od[1];
			if(cdMap.get(dest) ==null){
				Set<String> dList = new HashSet<String>();
				dList.add(origin);
				cdMap.put(dest, dList);
			}else{
				Set<String> dList = cdMap.get(dest);
				dList.add(origin);
				cdMap.put(dest, dList);
			}
			
		}
		System.out.println(cdMap.size());
		for (Iterator<String> iterator = cdMap.keySet().iterator(); iterator.hasNext();) {
			String c = iterator.next();
			System.out.println(c +"-" + cdMap.get(c));
			
		}
	}
}
