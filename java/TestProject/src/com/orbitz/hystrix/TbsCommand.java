package com.orbitz.hystrix;


import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class TbsCommand extends HystrixCommand<Response>{

	 Request request;
	protected TbsCommand(Request request) {
		 super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
		 this.request = request;
	}

	@Override
	protected Response run() throws Exception {
		// TODO Auto-generated method stub
		Response resp = new Response();
		resp.response= request.query;
		Thread.sleep(500);
		return resp ;
	}
	 @Override
	protected String getCacheKey() {
	   return String.valueOf(request.query);
	}

}
