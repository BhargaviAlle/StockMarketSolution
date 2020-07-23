package com.cts.stockmarket.service;

import java.util.List;
import java.util.Map;

import com.cts.stockmarket.model.Sector;
import com.cts.stockmarket.model.Stock;

public class StockMarketServiceImpl implements StockMarketService{

	@Override
	public Map<Sector, List<Stock>> readData() {
		
		return null;
	}

	@Override
	public void calculateProfit(Map<Sector, List<Stock>> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Stock> getsStockSectorwise(Map<Sector, List<Stock>> map, Sector sectorType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Stock> stockToSell(Map<Sector, List<Stock>> map) {
		// TODO Auto-generated method stub
		return null;
	}

}
