package com.orbitz.test;

import java.io.UnsupportedEncodingException;

public class JavaTester {

	public static void main(String s1[]) throws UnsupportedEncodingException{
		//String s = "%3D%7B%22hotelSearch%22%3A%7B%22pos%22%3A%22EBFR%22%2C%22locale%22%3A%22fr_FR%22%2C%22market%22%3A%22LONDRES%22%2C%22chainCodes%22%3A%5B%22%22%5D%2C%22hotelOnegIds%22%3A%5B%22%22%5D%2C%22arriveDate%22%3A20130422%2C%22departDate%22%3A20130423%2C%22rooms%22%3A1%2C%22adults%22%3A2%7D%7D%0A%3E%3Evalue%3D%7B%22hotelPriceResponse%22%3A%7B%22dateOfResponse%22%3A%2220130303-171315%22%2C%22lowestPrice%22%3A%7B%22value%22%3A74.53%2C%22currency%22%3A%22EUR%22%7D%2C%22hotelId%22%3A177126%7D%7D%0A";
		//String s ="%3D%7B%22hotelSearch%22%3A%7B%22pos%22%3A%22EBFR%22%2C%22locale%22%3A%22fr_FR%22%2C%22market%22%3A%22LONDRES%22%2C%22chainCodes%22%3A%5B%22%22%5D%2C%22hotelOnegIds%22%3A%5B%22%22%5D%2C%22arriveDate%22%3A20130422%2C%22departDate%22%3A20130423%2C%22rooms%22%3A1%2C%22adults%22%3A2%7D%7D%0A%3E%3Evalue%3D%7B%22hotelPriceResponse%22%3A%7B%22dateOfResponse%22%3A%2220130303-171315%22%2C%22lowestPrice%22%3A%7B%22value%22%3A74.53%2C%22currency%22%3A%22EUR%22%7D%2C%22hotelId%22%3A177126%7D%7D%0A";
		String s ="%3D%7B%22dpSearch%22%3A%7B%22pos%22%3A%22CTIX%22%2C%22locale%22%3A%22en_US%22%2C%22searches%22%3A%5B%7B%22airSearch%22%3A%7B%22pos%22%3A%22CTIX%22%2C%22locale%22%3A%22en_US%22%2C%22searchType%22%3A%22Basic+LowFare+Search%22%2C%22origin%22%3A%22MIA%22%2C%22dest%22%3A%22POP%22%2C%22cabinClass%22%3A%22C%22%2C%22adults%22%3A2%2C%22children%22%3A1%2C%22infants%22%3A0%2C%22departDate%22%3A20130511%2C%22returnDate%22%3A20130518%7D%2C%22hotelSearch%22%3A%7B%22pos%22%3A%22CTIX%22%2C%22locale%22%3A%22en_US%22%2C%22market%22%3A%22PUERTO+PLATA%22%2C%22chainCodes%22%3A%5B%22%22%5D%2C%22hotelOnegIds%22%3A%5B%22%22%5D%2C%22arriveDate%22%3A20130511%2C%22departDate%22%3A20130518%2C%22rooms%22%3A1%2C%22adults%22%3A2%7D%7D%5D%7D%7D%0A%3E%3Evalue%3D%7B%22basePriceResponse%22%3A%7B%22dateOfResponse%22%3A%2220130303-171710%22%2C%22lowestPrice%22%3A%7B%22value%22%3A1016.18%2C%22currency%22%3A%22USD%22%7D%7D%7D%0A";
		byte[] b = s.getBytes("UTF-8");
		System.out.println(b.length);
		int x =8 * (int) ((((b.length) * 2) + 45) / 8) ;
		System.out.println(x);
	}
}
