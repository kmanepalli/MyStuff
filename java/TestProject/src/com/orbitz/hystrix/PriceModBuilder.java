package com.orbitz.hystrix;

public class PriceModBuilder {
	
	public void fillUpModule(){
		Request req = new Request();
		req.context = "context1";
		req.query = "query2";
		TbsCommand command = new TbsCommand(req);
		Response resp = command.execute();
		System.out.println(resp);
		
	}

}
