package dbutil;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Properties;

import javax.sql.rowset.CachedRowSet;

import org.apache.commons.fileupload.FileItem;

import com.sun.rowset.CachedRowSetImpl;

import bean.Image;

public class MySQL {

	private final static String connectionURL = "jdbc:mysql://localhost:3306/EWA_Project";
	private static Properties dbProperties = null;
	private static Connection dbConnection = null;
	private static MySQL mySql = null;
	
	static {
		dbProperties = new Properties();
		dbProperties.setProperty("user", "root");
		dbProperties.setProperty("password", "root");
		dbProperties.setProperty("useSSL", "false");
		dbProperties.setProperty("autoReconnect", "true");
	}
	
	private MySQL() {
		
	}
	
	public static MySQL getInstance() {
		if(mySql == null)
			mySql = new MySQL();
		return mySql;
	}

	private Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			dbConnection = DriverManager.getConnection(connectionURL, dbProperties);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbConnection;
	}

	public void setParamaters(PreparedStatement statement, HashMap<Integer, Object> valueMap) {
		if (valueMap != null) {
			try {
				for (int i = 1; i <= valueMap.size(); i++) {
					Object value = valueMap.get(i);
					if (value instanceof Integer)
						statement.setInt(i, (int) value);
					else if (value instanceof Long)
						statement.setLong(i, (long) value);
					else if (value instanceof String)
						statement.setString(i, (String) value);
					else if (value instanceof Float)
						statement.setFloat(i, (float) value);
					else if (value instanceof java.util.Date) {
						SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						statement.setString(i, formatter.format(value));
						//statement.setDate(i, new Date(((java.util.Date) value).getTime()));
					} else if (value instanceof Boolean)
						statement.setBoolean(i, (Boolean) value);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void executeStatement(String query, HashMap<Integer, Object> valueMap) {
		System.out.println("EXECUTE => " + valueMap);
		try (Connection dbConnection = getConnection();
				PreparedStatement statement = dbConnection.prepareStatement(query);) {
			setParamaters(statement, valueMap);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public CachedRowSet executeSelect(String query, HashMap<Integer, Object> valueMap) {
		CachedRowSet rowset = null;
		try (Connection dbConnection = getConnection();
				PreparedStatement statement = dbConnection.prepareStatement(query);) {

			setParamaters(statement, valueMap);
			ResultSet rs = statement.executeQuery();
			if (rs != null) {
				rowset = new CachedRowSetImpl();
				rowset.populate(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowset;
	}
	
	/* Rohan */
	public int executeFileStatementWithId(String query, Image image, FileItem fileItem) {
		//System.out.println("EXECUTE => " + valueMap);
		int insertedId=0;
		try (Connection dbConnection = getConnection();
				PreparedStatement statement = dbConnection.prepareStatement(query,  Statement.RETURN_GENERATED_KEYS);) {
			
			/*FileInputStream fis = null;
			try {
				fis = new FileInputStream(image.getFile());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}*/
			System.out.println("image.getAdId(  " + image.getAdId());
			statement.setString(1, image.getName());
			statement.setInt(2, image.getAdId());
			statement.setBinaryStream(3, fileItem.getInputStream(), (int) fileItem.getSize());

			statement.executeUpdate();

			ResultSet generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				insertedId = (int) generatedKeys.getLong(1);
				System.out.println("insertedId "+insertedId);
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return insertedId;
	}
	
	public Blob getImageBlob(String query, HashMap<Integer, Object> valueMap) {
		Blob b = null;
		try (Connection dbConnection = getConnection();
				PreparedStatement statement = dbConnection.prepareStatement(query);) {

			setParamaters(statement, valueMap);
			ResultSet rs = statement.executeQuery();
			rs.next();
			b = rs.getBlob("photo");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}
	/* Rohan */
}