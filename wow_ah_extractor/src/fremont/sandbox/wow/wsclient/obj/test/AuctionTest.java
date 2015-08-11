package fremont.sandbox.wow.wsclient.obj.test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import fremont.sandbox.util.AuctionDao;
import fremont.sandbox.wow.wsclient.obj.AHJsonObject;
import fremont.sandbox.wow.wsclient.obj.AuctionItem;

public class AuctionTest {
	private static final Logger logger=LogManager.getLogger(AuctionTest.class);

	AuctionDao dao;
	long startTime,stopTime,elapsedTime;
	
	@Before
	public void init(){
		startTime = System.currentTimeMillis();
		dao=AuctionDao.getInstance();
	}
	
//	@Test
	public AuctionItem testAuctionItem(String autionItemFileName) {
		logger.info("Read JSON from file, convert JSON string back to object");
		if (autionItemFileName==null)
			autionItemFileName="auctionItemSample.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(autionItemFileName))) {
        	Gson gson = new Gson();
        	AuctionItem jsonObj=new AuctionItem();
            jsonObj = gson.fromJson(reader, AuctionItem.class);
            logger.debug(gson.toJson(jsonObj));
            reader.close();

            BufferedReader reader1 = new BufferedReader(new FileReader(autionItemFileName));
        	String testJsonStr="";
        	String line="";
        	while((line=reader1.readLine())!=null){
        		testJsonStr+=line;
        		logger.debug(line);
        	}
            AuctionItem jsonObj2=new AuctionItem();
            jsonObj2 = gson.fromJson(testJsonStr, AuctionItem.class);
            logger.debug(gson.toJson(jsonObj2));
            reader1.close();
            assertEquals(gson.toJson(jsonObj), gson.toJson(jsonObj2));
            return jsonObj;
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
		return null;
	}

//	@Test
	public AHJsonObject testAHInventory(String ahObjFileName) {
		logger.info("Read JSON from file, convert JSON string back to object");
		if (ahObjFileName==null)
			ahObjFileName="actionResult20150526_185344";
//        try (BufferedReader reader = new BufferedReader(new FileReader(ahObjFileName))) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ahObjFileName))) {
        	Gson gson = new Gson();
        	AHJsonObject jsonObj=new AHJsonObject();
            jsonObj = gson.fromJson(reader, AHJsonObject.class);
//            logger.info("object:"+gson.toJson(jsonObj));
            reader.close();
//        	BufferedReader reader1 = new BufferedReader(new FileReader("actionResult20150526_185344"));
//        	String content="";
//        	String line="";
//        	while((line=reader1.readLine())!=null){
//        		logger.info(line);
//        		content+=line;
//        	}
//            AHJsonObject jsonObj2=new AHJsonObject();
//            jsonObj2 = gson.fromJson(content, AHJsonObject.class);
//            logger.info("object 2:"+gson.toJson(jsonObj2));
//            reader1.close();
//            
//            assertEquals(gson.toJson(jsonObj), gson.toJson(jsonObj2));
            return jsonObj;
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
		return null;
	}

//	@Test
	public void testinsertAuctionItem(String autionItemFileName){
		AuctionItem item=testAuctionItem(null);
		item.timestamp=new Date();
		try {
			dao.insertAuctionItem(item);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInsertAH(String ahObjFileName){
		AHJsonObject ahObj=testAHInventory(null);
		ahObj.retrivedTime=new Date();
		try {
			dao.insertAHObj(ahObj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void clear(){
		dao.close();
		dao=null;

		stopTime= System.currentTimeMillis();
		elapsedTime = stopTime - startTime;
		logger.info("Execution cost in milliseconds: "+elapsedTime);
	}

}
