package zwor.components.login;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;

public class HTTPRequestURL {
	public HttpServletRequest createUrl(String url) throws UnsupportedEncodingException{
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
            try{
            map.put(key, URLDecoder.decode(value, "UTF-8"));
            }catch(UnsupportedEncodingException e){
            	
            }
        }
        return (HttpServletRequest) Proxy.newProxyInstance(
        		HTTPRequestURL.class.getClassLoader(),
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
}
