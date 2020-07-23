package com.cts.stockmarket.service;

import java.util.List;
import java.util.Map;

import com.cts.stockmarket.model.Sector;
import com.cts.stockmarket.model.Stock;

public interface StockMarketService {

	Map<Sector,List<Stock>> readData();
	void calculateProfit(Map<Sector,List<Stock>> map);
	List<Stock> getsStockSectorwise(Map<Sector,List<Stock>> map,Sector sectorType);//list returned should be inn ascending order of purchase date
	List<Stock> stockToSell(Map<Sector,List<Stock>> map);//stocks that are older than 50 weaks and are undergoing loss
	
}
