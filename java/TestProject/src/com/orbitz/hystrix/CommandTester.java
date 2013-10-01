package com.orbitz.hystrix;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

public class CommandTester {
	
	public static void main(String a[]){
		CommandTester tester = new CommandTester();
		tester.execute();
		
	}

	private void execute(){
		HystrixRequestContext context = HystrixRequestContext.initializeContext();
		
		RnRModBuilder rnrBuilder = new RnRModBuilder();
		PriceModBuilder priceBuilder = new PriceModBuilder();
		
		rnrBuilder.fillUpModule();
		priceBuilder.fillUpModule();
		
		context.shutdown();
		
		
	}
}
