package fremont.sandbox.wow.wsclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fremont.sandbox.wow.wsclient.Constants.Host;
import fremont.sandbox.wow.wsclient.Constants.Realm;

public class DataRetriever {
	Host region;
	Realm realm;
	String apiKey;
	
	private static final Logger logger=LogManager.getLogger(DataRetriever.class);
	
	public String request(String req_url) throws IOException {
		System.out.println("Request message: " + req_url);
		// send request
		URL url = new URL(req_url);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");
		// conn.setRequestProperty("Accept-Language", "zh-TW");
		// conn.setRequestProperty("Content-Type", "application/json");
		// OutputStream os = conn.getOutputStream();
		// os.write(json.getBytes());
		// os.flush();

		int responseCode = conn.getResponseCode();

		logger.debug("Response code: " + responseCode);

		String result = "";
		// get result if there is one
		if (responseCode == 200) // HTTP 200: Response OK
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String output;
			while ((output = br.readLine()) != null) {
				result += output;
			}
			logger.debug("Response message: " + result);
		}

		return result;
	}

	public void writeStringToFile(String s, String fileName){
		try {
			File f=new File(fileName);
			FileWriter fw=new FileWriter(f);
			fw.write(s);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public DataRetriever(){
		region=Host.Taiwan;
		realm=Realm.SilverwingHold;
		apiKey=Constants.WOW_API_KEY;
	}
	
	public DataRetriever(Host region, Realm realm, String apiKey){
		/* arguments default value setting */
	    if (region==null) region=Host.Taiwan;
	    else this.region=region;
	    
	    if (realm==null) realm=Realm.SilverwingHold;
	    else this.realm=realm;
	    
	    if (apiKey==null||apiKey=="") apiKey=Constants.WOW_API_KEY;
	    else this.apiKey=apiKey;	    
	}
}
