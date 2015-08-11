package fremont.sandbox.wow.wsclient;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import fremont.sandbox.wow.wsclient.Constants.Host;
import fremont.sandbox.wow.wsclient.obj.ItemJsonObject;

public class ItemDataRetriever extends DataRetriever{
	
	public ItemJsonObject parseItemResp1(String result) throws ParseException{
		System.out.println("Parsing item response >>>");
		ItemJsonObject itemObj=new ItemJsonObject();
		try{
			JSONParser jp=new JSONParser();
			JSONObject map=(JSONObject)jp.parse(result);

			itemObj.name=(String) map.get("name");
			itemObj.description=(String) map.get("description");
			System.out.println("item name="+itemObj.name);
			System.out.println("Parsing item response <<<");
			return itemObj;
		}catch(ParseException e){
			System.out.println("String to parse: "+result);
			e.printStackTrace();
			throw e;
		}
	}
	public ItemJsonObject getItemData(int itemId) {
		String req1 = "http://" + Host.Taiwan + "/api/wow/item/" + itemId;
		try {
			String resp1;
			resp1 = request(req1);
			System.out.println("result="+resp1);

			// write auction house data to file
			DateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
			String timestamp = df.format(new Date());
			System.out.println("timestamp: " + timestamp);
		    writeStringToFile(resp1, "itemResult"+timestamp);
			
			return parseItemResp1(resp1);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args){
		ItemDataRetriever retriever= new ItemDataRetriever();
		retriever.getItemData(118472);
	}
}
