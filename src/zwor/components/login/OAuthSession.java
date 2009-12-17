package zwor.components.login;

import org.expressme.openid.OpenIdManager;
import org.expressme.openid.Association;
import org.expressme.openid.Authentication;
import org.expressme.openid.Endpoint;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class OAuthSession {
	OpenIdManager manager;
	public Association association;
	public OAuthSession(){
		manager = new OpenIdManager(); 
	}
	
	public void setOpenIdParams(OpenIdParams parameters){
		manager.setReturnTo(parameters.returnUrl);
		manager.setRealm(parameters.realm);
		manager.setTimeOut(parameters.timeout);
	}
	
	public void setEndPoint(String endpoint,OpenIdParams parameters){
		parameters.endpoint = manager.lookupEndpoint(endpoint);
	}
	
	public void getAssociationKey(Endpoint endpoint){
		association = manager.lookupAssociation(endpoint);
	}
	
	public String getAuthenticationUrl(OpenIdParams parameters){
		return manager.getAuthenticationUrl(parameters.endpoint, manager.lookupAssociation(parameters.endpoint));
	}
	
	public HashMap<String,String> authenticateUser(String url,Endpoint e) throws Exception{
		HTTPRequestURL urlReq = new HTTPRequestURL();
		System.out.println(this.association.getSessionType());
		HttpServletRequest request = urlReq.createUrl(url);
		Authentication a = this.manager.getAuthentication(request, this.association.getRawMacKey());
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("id", a.getIdentity());
		if(e.toString() != "Yahoo"){
		map.put("email", a.getEmail());
		}
		UpdateUserProfile user = new UpdateUserProfile();
		user.addUser(map);
		return map;	
	}
}
