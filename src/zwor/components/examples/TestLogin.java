package zwor.components.examples;

import zwor.components.login.OAuthSession;
import zwor.components.login.OpenIdParams;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.lang.Runtime;

public class TestLogin {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		OAuthSession session = new OAuthSession();
		OpenIdParams parameters = new OpenIdParams(10000);
		session.setOpenIdParams(parameters);
		session.setEndPoint(args[0], parameters);
		String auth_url = session.getAuthenticationUrl(parameters);
		session.getAssociationKey(parameters.endpoint);
		System.out.println(auth_url);
                Runtime rt = Runtime.getRuntime().exec("firefox "+auth_url);
		String ret_url = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try{
			ret_url = reader.readLine();
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
		System.out.println(ret_url);
		HashMap<String,String> map = session.authenticateUser(ret_url,parameters.endpoint);
		Set<String> keys = map.keySet();
		for(String str: keys){
			System.out.println(map.get(str).toString());
		}
	}

}
