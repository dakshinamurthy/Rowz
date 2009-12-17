package zwor.components.examples;

import java.net.URL;
import java.io.*;

import zwor.components.contacts.InitiateAuthSub;
import zwor.components.contacts.ParseFeeds;
import zwor.components.contacts.Session;
import com.google.gdata.data.contacts.*;
import com.google.gdata.data.extensions.Email;


public class GetContacts extends ParseFeeds{

	/**
	 * @param args
	 */

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		InitiateAuthSub auth = new InitiateAuthSub();
		String auth_url = auth.init("http://www.riteshnayak.com/rowz");
		//This Authorization URL has to be passed to the browser
		//to obtain a token from the openid provider.
		Session session = new Session(new URL(auth_url));
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String input = reader.readLine();
		URL ret_url = new URL(input);
		ContactFeed results = session.setUpSessionAndGetContacts(ret_url);
		new GetContacts().parse(results);
	}
	
	public void parse(ContactFeed feed){
		System.out.println("Retrieved "+feed.getEntries().size()+" contacts");
		System.out.println(feed.getTitle().getPlainText());
		for(int i=0;i<feed.getEntries().size();i++){
			ContactEntry entry = feed.getEntries().get(i);
			System.out.println("----------------");
			if(entry.hasName()){
				System.out.println(entry.getTitle().getPlainText());
			}
			if(entry.hasEmailAddresses()){
				for(Email email : entry.getEmailAddresses()){
					System.out.println(email.getAddress());
				}
			}
		}
	}
}
