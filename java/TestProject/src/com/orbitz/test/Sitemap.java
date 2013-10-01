package com.orbitz.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.json.JSONObject;

import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;

public class Sitemap {
	private static final String DEALS_URL = "http://deals-cmsref-001.cpeg.orbitz.net:10000/deals/module/hotelsCanonical/";
	private static final int INCREMENT = 100;
	static String SITEMAP_DIRECTORY = "/var/sitemap/";
	String SOLR_SERVER = "http://indexedsearch.prod.o.com:8980/hotelLP";

	public static void main(String p[]) throws Exception {

		String pos = "EBFR";
		String locale = "fr_FR";
		String domainUrl = "http://www.ebookers.fr";
		Sitemap map = new Sitemap();
		for (int i = 0; i < 1; i++) {
			List<String> ids = map.getIds(i, pos, locale);
			System.out.println("fetched Ids");
			List<String> canons = generateCanonicals(pos, locale, ids);
			System.out.println("fetched Urls");

			writeSitemap(canons,domainUrl);
		}
	}

	public List<String> getIds(int i, String pos, String locale)
			throws Exception {
		List<String> ids = new ArrayList<String>();

		SolrServer hotServer = new CommonsHttpSolrServer(SOLR_SERVER);
		SolrQuery solrLocrQuery = new SolrQuery();

		solrLocrQuery.setQueryType("standard");
		solrLocrQuery.addFilterQuery("pos:" + pos);
		solrLocrQuery.addFilterQuery("locale:" + locale);
		solrLocrQuery.setRows(INCREMENT);
		solrLocrQuery.setStart(i * INCREMENT);
		solrLocrQuery.setFields("hot.id");
		solrLocrQuery.setSortField("hot.city.sort", ORDER.asc);
		QueryResponse queryLocResponse = hotServer.query(solrLocrQuery);
		SolrDocumentList locList = queryLocResponse.getResults();

		for (SolrDocument doc : locList) {
			try {
				ids.add(doc.get("hot.id").toString());
			} catch (Exception e) {

			}
		}
		return ids;
	}

	private static void writeSitemap(List<String> canons,String domainUrl)
			throws MalformedURLException {
		WebSitemapGenerator wsg = WebSitemapGenerator
				.builder(domainUrl, new File(SITEMAP_DIRECTORY))
				.autoValidate(true).build();
		try {
			for (String canon : canons) {
				if (StringUtils.isNotEmpty(canon) && !canon.contains("hotels")) {
					WebSitemapUrl.Options op = new WebSitemapUrl.Options(canon);
					op.changeFreq(ChangeFreq.MONTHLY);
					WebSitemapUrl u = new WebSitemapUrl(op);
					wsg.addUrl(u);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		wsg.write();
	}

	private static List<String> generateCanonicals(String pos, String locale,
			List<String> ids) {
		List<String> canons = new ArrayList<String>();
		for (String id : ids) {
			String url = DEALS_URL + pos + "/" + locale + "/43/" + id;
			try {
				URL u = new URL(url);
				String line = null;
				HttpURLConnection connection = (HttpURLConnection) u
						.openConnection();
				connection.setRequestMethod("GET");
				connection.setDoOutput(true);
				connection.setReadTimeout(3000);

				connection.connect();
				BufferedReader rd = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
				StringBuilder sb = new StringBuilder();

				while ((line = rd.readLine()) != null) {
					sb.append(line + '\n');
				}

				JSONObject obj = new JSONObject(sb.toString());
				JSONObject o = obj.getJSONObject("onegToCanonical");
				String canUrl = o.getString(id);
				canUrl = canUrl.replace("cmsref.", "www.");

				canons.add(canUrl);
				connection.disconnect();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return canons;
	}
}
