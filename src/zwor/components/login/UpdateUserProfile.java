/**
 * 
 */
package zwor.components.login;
import java.util.HashMap;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author dakshinam
 *
 */
public class UpdateUserProfile {

	public void addUser(HashMap<String,String> map){
		Properties properties = new Properties();
		try{
			properties.load(new FileInputStream("profiles.properties"));
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
		properties.put(map.get("id"), map.get("email"));
		try{
			properties.store(new FileOutputStream("profiles.properties"),"");
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
}
