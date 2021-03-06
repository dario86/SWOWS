package org.swows.function;

import com.hp.hpl.jena.sparql.function.Function;
import com.hp.hpl.jena.sparql.function.FunctionFactory;
import com.hp.hpl.jena.sparql.function.FunctionRegistry;

public class Factory implements FunctionFactory {
	
	private static final String BASE_URI = "http://www.swows.org/function#";
	private static final int BASE_URI_LENGTH = BASE_URI.length();
	private static Factory singleton;
	
	//private static List<String uri>
	static  {
		singleton = new Factory();
	}

	private Factory() {
		FunctionRegistry.get().put(BASE_URI + "to", this);
	}
	
	public static Factory getInstance() {
		return singleton;
	}
	
	public static String getBaseURI() {
		return BASE_URI;
	}

	@Override
	public Function create(String uri) {
		if (uri.startsWith(BASE_URI)) {
			String pfunctionName = uri.substring(BASE_URI_LENGTH);
			if (pfunctionName.equals("to"))
				return new to();
		}
		return null;
	}

}
