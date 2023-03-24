package com.remoer.main;

public class Execute {

	public static Object run(ServiceInterface service, Object obj) throws Exception {
		Object result = service.service(obj);
		return result;
	}

}
