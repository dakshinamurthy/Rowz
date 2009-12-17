package zwor.components.contacts;

import com.google.gdata.client.http.AuthSubUtil;

public class InitiateAuthSub {
	
	private static String scope = "http://www.google.com/m8/feeds/";
	public InitiateAuthSub(){
		
	}
	
	public String init( String returnUrl ){
		String url = AuthSubUtil.getRequestUrl(returnUrl, scope, false , true);
		System.out.println(url);
		return url;
	}
}
