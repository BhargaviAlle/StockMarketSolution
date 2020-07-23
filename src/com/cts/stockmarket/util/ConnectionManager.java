package com.cts.stockmarket.util;

import java.sql.Connection;

public interface ConnectionManager {

	void closeConnection();
	Connection getConnection();
}
