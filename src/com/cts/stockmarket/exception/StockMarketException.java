package com.cts.stockmarket.exception;

public class StockMarketException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void printStackTrace() {
		System.out.println("Stock Market Exception !");
		super.printStackTrace();
	}

	
}
