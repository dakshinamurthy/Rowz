package zwor.components.contacts;

import java.lang.Runtime;
import java.net.URL;

import com.google.gdata.client.http.AuthSubUtil;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.client.Query;

public class Session {
	
	public Session(URL url) throws Exception{
		Runtime.getRuntime().exec("firefox "+url);
	}
	
	
	//Get a onetime token from google and exchange it for a session token.
	//Build 
	public ContactFeed setUpSessionAndGetContacts(URL url) throws Exception{
		String token = AuthSubUtil.getTokenFromReply(url);
		String sessionToken = AuthSubUtil.exchangeForSessionToken(token, null);
		ContactsService serviceBroker = new ContactsService("ZworApp");
		serviceBroker.setAuthSubToken(sessionToken);
		URL feedUrl = new URL("http://www.google.com/m8/feeds/contacts/default/full");
		Query query = new Query(feedUrl);
		query.setMaxResults(100);
		query.setStartIndex(1);
		ContactFeed yourContacts = serviceBroker.query(query, ContactFeed.class);
		return yourContacts;
	}
}
