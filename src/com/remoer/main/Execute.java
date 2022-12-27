package com.remoer.main;

public class Execute {
	
	public static Object run(ServiceInterface service, Object obj) throws Exception {
		return service.service(obj);
	}

}
