package com.oatey.scale.onbase.preprocessor.domain.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.oatey.scale.onbase.preprocessor.domain.Logger;

public class ScaleRepository {
	private String driverClass;
	private String url;
	private String userName;
	private String password;
	
	private Connection connection;
	
	public String getCustomerFromShipment(String shipmentId) {
		String sql = "select CUSTOMER from ils.dbo.SHIPMENT_HEADER where SHIPMENT_ID = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = getConnection().prepareStatement(sql);
			ps.setString(1, shipmentId);
			ps.execute();
			
			rs = ps.getResultSet();
			if(rs.next())
				return rs.getString(1);
		} catch (Exception e) {
			Logger.logException(null, e);
		} finally {
			try { rs.close(); rs = null; } catch (Exception e) {}
			try { ps.close(); ps = null; } catch (Exception e) {}
		}
		
		return null;
	}
	
	private Connection getConnection() throws Exception {
		if(connection == null)
			connection = getNewConnection();
		
		if(connection.isClosed()) {
			connection = null;
			connection = getNewConnection();
		}
			
		return connection;
	}
	
	private Connection getNewConnection() throws Exception {
		Class.forName(driverClass);
		return DriverManager.getConnection(url,userName,password);
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
