package fremont.sandbox.wow.wsclient.obj;

import java.util.Date;
import java.util.List;

public class AHJsonObject{
	private Realm realm;
	private AHInventory auctions;
	public Date retrivedTime;
	
	public class AHInventory {
		private List<AuctionItem> auctions;
		
		public List<AuctionItem> getList(){
			return auctions;
		}
	}
	
	public class Realm{
		String name,slug;
	}
	
	public List<AuctionItem> getItemList(){
		return auctions.getList();
	}
}
