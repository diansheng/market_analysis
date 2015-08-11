package fremont.sandbox.wow.wsclient;

import java.io.BufferedReader;
import java.io.FileReader;
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

import com.google.gson.Gson;

import fremont.sandbox.wow.wsclient.obj.AHJsonObject;

public class AhDataRetriever extends DataRetriever{

	protected String ahDataURL;
	private String jsonString;
	
	private static final Logger logger=LogManager.getLogger(AhDataRetriever.class);
		
	protected void parseAuctionResp1(String result) throws ParseException{
		logger.info("Parsing auction response 1 >>>");
		try{
			JSONParser jp=new JSONParser();
			JSONObject respObj=(JSONObject)jp.parse(result);
			JSONArray fileArr=(JSONArray) respObj.get("files");
			
			JSONObject map=(JSONObject) fileArr.get(0);
			ahDataURL=(String) map.get("url");
			long lastModified=(long) map.get("lastModified");
		
			logger.debug("url: "+ahDataURL);
			logger.debug("lastModified: "+lastModified);
			
		}catch(ParseException e){
			logger.error("String to parse: "+result);
			throw e;
		}
		logger.info("Parsing auction response 1 <<<");
	}
	
	/**
	 * 
	 * @param dataStoreDir
	 * @return data store file name if dataStoreDir is not empty; null otherwise 
	 * @throws Exception
	 */
	public String getActionHouseData(String dataStoreDir) throws Exception{
		logger.info("getActionHouseData >>>");
		
		String req1=region+"wow/auction/data/"+realm+"?apikey="+apiKey;
		try {
			String resp1;
			resp1 = request(req1);
			parseAuctionResp1(resp1);
			jsonString=request(ahDataURL);

			logger.info("getActionHouseData <<<");
		    if (dataStoreDir!=null&&dataStoreDir!=""){
				//write auction house data to file
				DateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
			    String timestamp=df.format(new Date());
			    logger.debug("timestamp: "+timestamp);
		    	writeStringToFile(jsonString, dataStoreDir+"/actionResult"+timestamp);
			    return "actionResult"+timestamp;
		    }else{
		    	return null;
		    }
		} catch (IOException | ParseException e) {
			throw e;
		}
	}
	
	public AHJsonObject getAHJsonObjFromFile(String ahObjFileName) throws IOException {
		logger.info("Read JSON from file, convert JSON string back to object");
		if (ahObjFileName==null)
			ahObjFileName="actionResult20150526_185344";

        try (BufferedReader reader = new BufferedReader(new FileReader(ahObjFileName))) {
        	Gson gson = new Gson();
        	AHJsonObject jsonObj=new AHJsonObject();
            jsonObj = gson.fromJson(reader, AHJsonObject.class);
            reader.close();
            return jsonObj;
        } catch (IOException e) {
        	logger.error("Fail to convert to AHJson object");
        	throw e;
        }
	}
	
	public AHJsonObject getAHJsonObj(){
		logger.info("Read JSON directly from response, convert JSON string back to object");

        Gson gson = new Gson();
		return gson.fromJson(jsonString, AHJsonObject.class);
	}
	
}
