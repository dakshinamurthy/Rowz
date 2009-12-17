package zwor.components.login;

import org.expressme.openid.Endpoint;

public class OpenIdParams{
	public Endpoint endpoint;
	public final String returnUrl = "http://www.riteshnayak.com/rowz";
	public final String realm = "http://*.riteshnayak.com";
	public int timeout;
	
	public OpenIdParams(int timeout){
		this.timeout = timeout;
	}
	
	public String getEndPoint(){
		return endpoint.getUrl();
	}
	public String getReturnUrl(){
		return returnUrl;
	}
	
	public String realm(){
		return realm;
	}
	
	public int timeout(){
		return timeout;
	}
}
