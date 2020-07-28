package com.cts.stockmarket.testcases;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.sql.Date;
import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import com.cts.stockmarket.model.Sector;
import com.cts.stockmarket.model.Stock;
import com.cts.stockmarket.util.ConnectionManagerImpl;
import com.mysql.jdbc.Connection;

public class TestCase {

	ConnectionManagerImpl connimpl;

	Connection connection = null;

	java.util.Date javaDate = new java.util.Date();
	long javaTime = javaDate.getTime();
	java.sql.Date sqlDate = new java.sql.Date(javaTime);

	Stock stock = new Stock("Finance", Sector.Infotech, 5.0, 5.0, sqlDate, 5, 5.0);

	@Before
	public void setUp() {
		System.out.println("Started Testing");
	}

	@Test
	public void testReadFile() {
		File file = new File("C://Users//bhanu//Downloads//inputFeed.txt");

		String filename = file.getName();

		assertEquals("inputFeed.txt", filename);
	}

	@Test
	public void testConvertStringToDate() throws ParseException {	 
		 String str="2017-07-28";  
		 Date date2=Date.valueOf(str);//converting string into sql date  
		 System.out.println(date2);  
		 assertEquals("2017-07-28", date2.toString());
	}

	@Test
	public void testreadData() {
		System.out.println("The SQL DATE is: " + sqlDate.toString());

		assertEquals("Finance", stock.getStockName());
		assertEquals(Sector.Infotech, stock.getSectorType());
		assertEquals(5.0, stock.getCostPrice());
		assertEquals(5.0, stock.getPresentPrice());
		assertEquals("2020-07-28", sqlDate.toString());
		assertEquals(5, stock.getNumberOfShares());
		assertEquals(5.0, stock.getTotalProfit());
	}

	@Test
	public void testCalculateProfit() {
		assertEquals(5.0, stock.getTotalProfit());
	}

	@Test
	public void testGetConnection() {

		connimpl = new ConnectionManagerImpl();
		connection = (Connection) connimpl.getConnection();
		assertTrue(connection != null);

		System.out.println(connection);
	}

	@Test
	public void testConvertUtilToSqlDate() {
		assertEquals("2020-07-28", sqlDate.toString());
	}

	public void shutDown() {
		System.out.println("End of TestCase");
	}
}
