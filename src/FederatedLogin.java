

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.expressme.openid.OpenIdManager;
import org.expressme.openid.Association;
import org.expressme.openid.Authentication;
import org.expressme.openid.Endpoint;

public class FederatedLogin {
/**
	 * @param args
	 */
	public static String readLine(){
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		try{
		input = reader.readLine();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return input;
	}
	
	static HttpServletRequest createRequest(String url) throws UnsupportedEncodingException{
		int pos = url.indexOf('?');
		if(pos == -1){
			throw new IllegalArgumentException("Bad Url");
		}
		String query = url.substring(pos + 1);
        String[] params = query.split("[\\&]+");
        final Map<String, String> map = new HashMap<String, String>();
        for (String param : params) {
            pos = param.indexOf('=');
            if (pos==(-1))
                throw new IllegalArgumentException("Bad url.");
            String key = param.substring(0, pos);
            String value = param.substring(pos + 1);
            map.put(key, URLDecoder.decode(value, "UTF-8"));
        }
        return (HttpServletRequest) Proxy.newProxyInstance(
                FederatedLogin.class.getClassLoader(),
                new Class[] { HttpServletRequest.class },
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.getName().equals("getParameter"))
                            return map.get((String)args[0]);
                        throw new UnsupportedOperationException(method.getName());
                    }
                }
        );
	}
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stubs
		OpenIdManager manager = new OpenIdManager();
		manager.setReturnTo("http://www.riteshnayak.com/rowz");
		manager.setRealm("http://*.riteshnayak.com");
		manager.setTimeOut(10000);
		Endpoint endpoint = manager.lookupEndpoint("Google");
		Association association = manager.lookupAssociation(endpoint);
		String auth_url = manager.getAuthenticationUrl(endpoint, association);
		System.out.println(auth_url);
		String ret_url = readLine();
		HttpServletRequest request = createRequest(ret_url);
		Authentication oauth = manager.getAuthentication(request, association.getRawMacKey());
		System.out.println(oauth);
	}
}
