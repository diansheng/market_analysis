package fremont.sandbox.util;

import java.sql.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fremont.sandbox.wow.wsclient.obj.AHJsonObject;
import fremont.sandbox.wow.wsclient.obj.AuctionItem;

public class AuctionDao {
	private static final Logger logger=LogManager.getLogger(AuctionDao.class);
	private static AuctionDao dao=null;
	
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306";

	// Database credentials
	public static String DB_USER = "fremont";
	public static String DB_PASS = "300991";

	private static Connection conn = null;

	public static AuctionDao getInstance() {
		if (dao == null) {
			dao=new AuctionDao();
		}
		if(conn==null){
			try {
				// STEP 2: Register JDBC driver
				Class.forName("com.mysql.jdbc.Driver");
				// STEP 3: Open a connection
				System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
				conn.setAutoCommit(false);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				System.err.println("ERROR: Unable to Connect to Database.");
				e.printStackTrace();
			}
		}
		return dao;
	}

	protected AuctionDao() {
	}

	public void close() {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
		conn = null;
	}

	public void insertAuctionItem(AuctionItem item) throws SQLException {
		// PreparedStatements can use variables and are more efficient
		PreparedStatement preStmt = null;
		String sql = "insert into fremont_db.auctionhall (auc, item, bid, buyout, timeStamp) values (?, ?, ?, ?, ?)";
		logger.info("SQL statment: "+sql);
		
		preStmt = conn.prepareStatement(sql);
		insertAuctionItem(preStmt, item);
		conn.commit();
		if(preStmt!=null)
			preStmt.close();
	}
	
	public void insertAHObj(AHJsonObject ahObj) throws SQLException {
		logger.info("insert AH data into DB >>>");
		// PreparedStatements can use variables and are more efficient
		PreparedStatement preStmt = null;
		String sql = "insert into fremont_db.auctionhall (auc, item, bid, buyout, timeStamp) values (?, ?, ?, ?, ?)";
		preStmt = conn.prepareStatement(sql);
		logger.info("SQL statment: "+sql);
		logger.info("Number of entries="+ahObj.getItemList().size());
		for(AuctionItem item:ahObj.getItemList()){
			insertAuctionItem(preStmt, item, ahObj.retrivedTime);
		}
		conn.commit();
		if(preStmt!=null)
			preStmt.close();
		logger.info("insert AH data into DB <<<");
	}
	
	private void insertAuctionItem(PreparedStatement preStmt, AuctionItem item, java.util.Date date)
			throws SQLException {
		preStmt.setLong(1, item.auc);
		preStmt.setLong(2, item.item);
		preStmt.setLong(3, item.bid);
		preStmt.setLong(4, item.buyout);
		preStmt.setTimestamp(5, new Timestamp(date.getTime()));
		logger.debug("auc="+item.auc);
		logger.debug("item="+item.item);
		logger.debug("bid="+item.bid);
		logger.debug("buyout="+item.buyout);
		logger.debug("timestamp="+item.timestamp);
		preStmt.executeUpdate();
	}

	private void insertAuctionItem(PreparedStatement preStmt, AuctionItem item)
			throws SQLException {
		insertAuctionItem(preStmt,item,item.timestamp);
	}
}
