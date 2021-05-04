package ya.dev.crm.dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

import ya.dev.crm.dao.CustomerDao;
import ya.dev.crm.dao.OrderDao;
import ya.dev.crm.dao.UserDao;
import ya.dev.crm.exception.DaoConfigurationException;

public class DaoFactory {
	
	private static final String PROPERTIES_FILE="ya/dev/crm/dao/dao.properties";
	private static final String PROPERTY_URL="url";
	private static final String PROPERTY_DRIVER="driver";
	private static final String PROPERTY_USERNAME="username";
	private static final String PROPERTY_PASSWORD="password";
	
	/*
	 * Basic implementation of javax.sql.DataSource.
	 * Commons-dbcp2 and commons-pool2 packages
	 * To create a pool of connexion
	 * To replace DriverManager. 
	 */
	private static BasicDataSource basicDS;
	
	public DaoFactory(BasicDataSource basicDataSource) {
		this.basicDS = basicDataSource;
	}
	
	// Retrieving DaoFactory Instance
	
	public static DaoFactory getInstance() {
		Properties properties = new Properties();
		String driver;
		String url;
		String username;
		String password;
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);
		
		if(propertiesFile == null)
			throw new DaoConfigurationException("Properties file " + PROPERTIES_FILE + " not found!");
		try {
			properties.load(propertiesFile);
			driver = properties.getProperty(PROPERTY_DRIVER);
			url = properties.getProperty(PROPERTY_URL);
			username = properties.getProperty(PROPERTY_USERNAME);
			password = properties.getProperty(PROPERTY_PASSWORD);
			
		} catch (IOException e) {
			throw new DaoConfigurationException("Impossible to load properties file " + PROPERTIES_FILE, e );
		}
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new DaoConfigurationException("Driver is not found in the classpath!");
		}
		basicDS = new BasicDataSource();
		basicDS.setDriverClassName(driver);
		basicDS.setUrl(url);
		basicDS.setUsername(username);
		basicDS.setPassword(password);
		basicDS.setInitialSize(10);
		basicDS.setMaxTotal(20);
		
		return new DaoFactory(basicDS);
	}
	
	public Connection getConnexion() throws SQLException {
		return basicDS.getConnection();
	}
	
	public CustomerDao getCustomerDao() {
		return new CustomerDaoImpl(this);
	}
	
	public OrderDao getOrderDao() {
		return new OrderDaoImpl(this);
	}

	public UserDao getUserDao() {
		return new UserDaoImpl(this);
	}
}
