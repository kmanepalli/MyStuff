package com.orbitz.test;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlParserKeyword {

	public static void main(String argv[]) {
		try {

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder
					.parse(new File(
							"/Users/mkchakravarti/Documents/project/deals/keywordText.xml"));

			// normalize text representation
			doc.getDocumentElement().normalize();
			System.out.println("Root element of the doc is "
					+ doc.getDocumentElement().getNodeName());

			NodeList listOfPersons = doc.getElementsByTagName("text");
			int totalPersons = listOfPersons.getLength();
			System.out.println("Total no of people : " + totalPersons);

			for (int s = 0; s < totalPersons; s++) {

				Node firstPersonNode = listOfPersons.item(s);
				//System.out.println(firstPersonNode.getNodeName());
				String textType = firstPersonNode.getAttributes()
						.getNamedItem("texttype").getNodeValue();
				String product = firstPersonNode.getAttributes()
						.getNamedItem("product").getNodeValue();

				if (textType.equals("marketingtext") && product.equals("HOTEL")) {
					String marketId = firstPersonNode.getChildNodes().item(1)
							.getTextContent();
					//System.out.println(textType + " =" + product);
					String val = firstPersonNode.getChildNodes().item(3)
							.getTextContent();
					System.out.println(marketId +"|"+val);
				}
				
				/*
				 * String val =
				 * firstPersonNode.getChildNodes().item(3).getTextContent();
				 * if(val.contains("Assurance")){ //System.out.println(val);
				 * System.out.println(val.substring(0,50)); }
				 */

			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}