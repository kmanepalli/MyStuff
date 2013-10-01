package com.orbitz.rx;

public class TBSLapsangCall {

	public String getHotelData(){
		try {
			Thread.sleep(5000);
		
		System.out.println("I am invoked....");
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "invoked "+System.currentTimeMillis();
	}
}
