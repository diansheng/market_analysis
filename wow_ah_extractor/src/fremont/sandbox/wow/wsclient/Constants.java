package fremont.sandbox.wow.wsclient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import fremont.sandbox.util.AuctionDao;

public class Constants {

	public static String WOW_API_KEY="tsmm3wqk9wr3ba5gqkqh8pt75w5np93y";
	public static String SECRET="pTtHYuYR7ucfygYP2Ue9Js29f7ARVuSt";
	public static Realm realm;
	public static Host region;
	public static String OUT_DIR;
	
	public static Properties loadProperties() {
		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("config.properties");
			// load a properties file
			prop.load(input);

			for (Object key : prop.keySet().toArray()) {
				System.out.println(key + ": " + prop.getProperty((String) key));
			}
			
			realm=Realm.getEnum(prop.getProperty("realm"));
			region=Host.valueOf(prop.getProperty("region"));
			String key=prop.getProperty("apiKey");
			if (key!=""&&key!=null) WOW_API_KEY=key;
			AuctionDao.DB_USER=prop.getProperty("db_user");
			AuctionDao.DB_PASS=prop.getProperty("db_pass");
			OUT_DIR=prop.getProperty("out_dir");

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}
	
	public enum Host{
		Taiwan("https://tw.api.battle.net/"),
		US("https://us.api.battle.net/");
		
		private String label; 
	    
	    public static Host getEnum(String s){
	         if (s!= null) {
	              for (Host b : Host.values()) {
	                   if (s.equalsIgnoreCase(b.label)) {     return b;     }
	              }
	         }
	         return null;
	    }
	     
	    private Host(String label) // must be private 
	    {   this.label = label;   }
	    public String toString() // proper override 
	    {   return label;   }
	}
	
	public enum Realm{
		SilverwingHold("silverwing-hold");
		
		private String label; 
	    
	    public static Realm getEnum(String s){
	         if (s!= null) {
	              for (Realm b : Realm.values()) {
	                   if (s.equalsIgnoreCase(b.label)) {     return b;     }
	              }
	         }
	         return null;
	    }
	     
	    private Realm(String label) // must be private 
	    {   this.label = label;   }
	    public String toString() // proper override 
	    {   return label;   }
	}
}
