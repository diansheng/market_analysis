package fremont.sanbox.wow;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fremont.sandbox.util.AuctionDao;
import fremont.sandbox.wow.wsclient.AhDataRetriever;
import fremont.sandbox.wow.wsclient.Constants;
import fremont.sandbox.wow.wsclient.obj.AHJsonObject;

public class MainClass {
	
	private static final Logger logger=LogManager.getLogger(MainClass.class);
	
	static long startTime;
	static long stopTime;
	
	public static void main(String [] args){
		startTime = System.currentTimeMillis();

		AhDataRetriever retriever=new AhDataRetriever();
		try {
			Constants.loadProperties();

			retriever.getActionHouseData(Constants.OUT_DIR);
//			retriever.getActionHouseData(Constants.OUT_DIR);
			AHJsonObject ahObj=retriever.getAHJsonObj();
			//store data to DB

			ahObj.retrivedTime=new java.util.Date();
			try {
				AuctionDao dao=AuctionDao.getInstance();
				dao.insertAHObj(ahObj);
				dao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		stopTime= System.currentTimeMillis();
		logger.info("Execution cost in milliseconds: "+(stopTime - startTime));
		logger.info("Completed");
	}
}
