package fremont.sandbox.ws.client;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import fremont.sandbox.util.AuctionDao;
import fremont.sandbox.wow.test.AuctionTest;
import fremont.sandbox.ws.client.Constants.Host;
import fremont.sandbox.ws.client.Constants.Realm;

public class AhDataRetriever extends DataRetriever{

	private static final Logger logger=LogManager.getLogger(AhDataRetriever.class);
	
	public String parseAuctionResp1(String result) throws ParseException{
		logger.info("Parsing auction response 1 >>>");
		String url=null;
		try{
			JSONParser jp=new JSONParser();
			JSONObject respObj=(JSONObject)jp.parse(result);
			JSONArray fileArr=(JSONArray) respObj.get("files");
			
			JSONObject map=(JSONObject) fileArr.get(0);
			url=(String) map.get("url");
			long lastModified=(long) map.get("lastModified");
		
			logger.debug("url: "+url);
			logger.debug("lastModified: "+lastModified);
			
		}catch(ParseException e){
			logger.error("String to parse: "+result);
			throw e;
		}
		logger.info("Parsing auction response 1 <<<");
		return url;
	}
		
	public String getActionHouseData() throws Exception{
		logger.info("getActionHouseData >>>");
		DateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
	    String timestamp=df.format(new Date());
		String req1=Host.Taiwan+"wow/auction/data/"+Realm.SilverwingHold+"?apikey="+Constants.KEY;
		try {
			String resp1,req2,resp2;
			resp1 = request(req1);
			req2=parseAuctionResp1(resp1);
			resp2=request(req2);
			
			//write auction house data to file
		    logger.debug("timestamp: "+timestamp);
		    writeStringToFile(resp2, "actionResult"+timestamp);
		} catch (IOException | ParseException e) {
			throw e;
		}
		logger.info("getActionHouseData <<<");
	    return "actionResult"+timestamp;
	}
	
	public static void main(String [] args){
		String url; //rest webservice query urll
//		"http://rpc.geocoder.us/service/csv?address=1600+Pennsylvania+Ave,+Washington+DC";
//		url="http://"+Host.Taiwan+"/api/wow/quest/13132";
//		url="http://"+Host.Taiwan+"/api/wow/character/silverwing-hold/一群德魯伊";
//		url="http://"+Host.Taiwan+"/api/wow/challenge/silverwing-hold";
//		url="http://"+Host.Taiwan+"/api/wow/realm/status?locale=en_US";
		url="http://"+Host.Taiwan+"/api/wow/auction/data/"+Realm.SilverwingHold;
		AhDataRetriever retriever=new AhDataRetriever();
		
		try {
//			retriever.request(url);
			String outFileName=retriever.getActionHouseData();
			
			//store data to DB
			AuctionTest at=new AuctionTest();
			at.init();
			at.testInsertAH(outFileName);
			at.clear();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
