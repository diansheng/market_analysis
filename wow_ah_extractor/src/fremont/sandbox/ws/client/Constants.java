package fremont.sandbox.ws.client;

public class Constants {

	public static final String KEY="tsmm3wqk9wr3ba5gqkqh8pt75w5np93y";
	public static final String SECRET="pTtHYuYR7ucfygYP2Ue9Js29f7ARVuSt";

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
