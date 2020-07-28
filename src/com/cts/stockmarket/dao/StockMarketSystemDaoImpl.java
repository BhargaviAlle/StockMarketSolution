package com.cts.stockmarket.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.cts.stockmarket.model.Sector;
import com.cts.stockmarket.model.Stock;
import com.cts.stockmarket.util.ConnectionManagerImpl;



public class StockMarketSystemDaoImpl implements StockMarketSystemDao{

	
	
	@Override
	public Map<Sector, List<Stock>> readData() {
		
		Map<Sector, List<Stock>> map=new HashMap<Sector, List<Stock>>();
		
		for (Sector sector:Sector.values()){
			map.put(sector,new ArrayList<Stock>());
		}
		
		List<Stock> listStock=new ArrayList<Stock>();
		
		Connection connection=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		connection=new ConnectionManagerImpl().getConnection();
//		if(connection!=null)System.out.println("Connected succefully !");
		
		try {
			stmt=connection.createStatement();
			rs=stmt.executeQuery("SELECT * FROM stockmarketdb.stock s;");
			while (rs.next()) {
				
				String stockName=rs.getString(2);
				Sector sectorType=Sector.valueOf(rs.getString(3).trim());
				double costPrice=rs.getDouble(4);
				double presentPrice=rs.getDouble(5);
				Date purchaseDate=rs.getDate(6);
				int numberOfShares=rs.getInt(7);
				double totalProfit=0;
				
				listStock.add(new Stock(stockName, sectorType, costPrice, presentPrice, purchaseDate, numberOfShares, totalProfit));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				rs.close();
				stmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
//		System.out.println(listStock.size());
//		for (Stock stock : listStock) {
//			System.out.println(stock);
//		}
		
		
		Set<Map.Entry<Sector, List<Stock>>> set=map.entrySet();
		for (Entry<Sector, List<Stock>> entry : set) {
			for (Stock stock:listStock) {
				if(stock.getSectorType().equals(entry.getKey())){
					map.get(entry.getKey()).add(stock);
				}
			}
		}
		
		return map;
	}

	@Override
	public void calculateProfit(Map<Sector, List<Stock>> map) {
		
		Set<Map.Entry<Sector, List<Stock>>> set=map.entrySet();
		for (Entry<Sector, List<Stock>> entry : set) {
			for(Stock stock:entry.getValue()){
			
				stock.setTotalProfit((stock.getPresentPrice()-stock.getCostPrice())*stock.getNumberOfShares());
			}
		}
		
		
	}

	@Override
	public List<Stock> getsStockSectorwise(Map<Sector, List<Stock>> map,Sector sectorType) {
		
		Collections.sort(map.get(sectorType),new Comparator<Stock>() {
			@Override
			public int compare(Stock s1, Stock s2) {
				return s1.getPurchaseDate().compareTo(s2.getPurchaseDate());
			}
		});
		
		
		return map.get(sectorType);
	}

	@Override
	public List<Stock> stockToSell(Map<Sector, List<Stock>> map) {
		
		
		long current=Calendar.getInstance().getTimeInMillis();
		List<Stock> returnList=new ArrayList<Stock>();
		Set<Map.Entry<Sector, List<Stock>>> set=map.entrySet();
		for (Entry<Sector, List<Stock>> entry : set) {
			for(Stock stock:entry.getValue()){
				if(stock.getTotalProfit()<1){
					
					long given=stock.getPurchaseDate().getTime();
					int numberOfDays=(int)((current-given)/(24*60*60*1000));
					int numberOfWeaks=(int)(numberOfDays/7);
					System.out.println(numberOfWeaks+"\t"+stock.getStockName());
					if(numberOfWeaks>50){
						returnList.add(stock);
					}
				}
			}
		}
		
		return returnList;
	}

	@Override
	public void readDataFromFile() {
	
		
		String fileName = "inputFeed.txt";
		String line = null;
		String[] splited = null;
		
	    PreparedStatement pstmt = null;
		Connection connection=null;
		
		connection=new ConnectionManagerImpl().getConnection();
	
			
		try {
			/*
			 * String filetext =
			 * "C://Users//bhanu//OneDrive//Desktop//Discussion_Board//StockMarketSolution//inputFeed.txt";
			 * 
			 * String query = "LOAD DATA LOCAL  INFILE '" + filetext +
			 * "' INTO TABLE stock FIELDS TERMINATED BY ',' IGNORE 1 LINES "; pstmt =
			 * connection.prepareStatement(query); pstmt.executeUpdate(query);
			 */
			
			
			File file = new File(fileName);
		    			
		    BufferedReader  bufferreader = new BufferedReader(new FileReader(file.getAbsoluteFile()));
			
		    line = bufferreader.readLine();
				
				while (line != null) {
										
					line = bufferreader.readLine();   // read next line
					splited = line.split("\\s+");  //   \\s+ - matches sequence of one or more whitespace characters.
					
				
			         String qry="insert into stockmarketdb.stock (stockName, sectorType, costPrice, presentPrice, purchaseDate, numberOfShares, totalProfit) values (?, ?, ?, ?, ?, ?, ?)";
					 pstmt = connection.prepareStatement(qry);
					  
					     pstmt.setString(1, splited[0]);
					     pstmt.setString(2, Sector.valueOf(splited[1]).toString());
					     pstmt.setDouble(3, Double.parseDouble(splited[2]));
					     pstmt.setDouble(4, Double.parseDouble(splited[3]));
					     pstmt.setDate(5, Date.valueOf(splited[4]));
					     pstmt.setInt(6, Integer.parseInt(splited[5]));
					     pstmt.setDouble(7, Integer.parseInt(splited[6]));		       		      
					     pstmt.executeUpdate();    
				
					
				}
				bufferreader.close();
						
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
	}
}
}
