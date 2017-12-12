package HelperClasses;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class PostParamGiver {
	
	 public static Map<String, String> splitQuery(String query) throws UnsupportedEncodingException {
		    Map<String, String> query_pairs = new HashMap<>();
		    String[] pairs = query.split("&");
		    for (String pair : pairs) {
		      int idx = pair.indexOf("=");
		      query_pairs.put(
		          URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
		          URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
		    }
		    return query_pairs;
		  }
}
