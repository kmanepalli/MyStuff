package com.orbitz.hystrix;

public class RnRModBuilder {

	public void fillUpModule(){
		Request req = new Request();
		req.context = "context1";
		req.query = "query1";
		TbsCommand command = new TbsCommand(req);
		Response resp = command.execute();
		System.out.println(resp);
		
	}
}
