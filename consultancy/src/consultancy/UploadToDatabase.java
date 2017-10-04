package consultancy;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UploadToDatabase implements CommonConsts
{
	private final static Logger log = Logger.getLogger(UploadToDatabase.class.getName());
	static FileHandler fileHandler;
	Object document = null;
	String phoneNumber = null;
	String id = null;
	String pin = INDIAN_PIN;
	String query = INSERT_QUERY;
	Connection connection = null;
	PreparedStatement preparedStatement = null;

	public UploadToDatabase(Object document, String phoneNumber)
	{
		try
		{
			fileHandler = new FileHandler("consultancy.log", true);
			LogFormatter formatter = new LogFormatter();
			log.addHandler(fileHandler);
			fileHandler.setFormatter(formatter);
		} catch (SecurityException | IOException e)
		{
			e.printStackTrace();
		}
		log.log(Level.INFO, "UploadToDatabase constructor called!");
		this.document = document;
		this.phoneNumber = phoneNumber;

	}

	public boolean uploadNow() throws NoSuchAlgorithmException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException
	{
		log.log(Level.INFO, "UploadNow called!");
		setId(phoneNumber);
		log.log(Level.INFO, "Connecting to the database");
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
		preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, id);
		preparedStatement.setInt(2, Integer.parseInt(pin));
		preparedStatement.setInt(3, Integer.parseInt(phoneNumber));
		preparedStatement.setBlob(4, (Blob) document);
		if (preparedStatement.executeUpdate() != 0)
		{
			log.log(Level.INFO, "Insertion Successful");
			closeConnection();
			return true;
		}
		else
		{
			log.log(Level.INFO, "Insertion Failed");
			closeConnection();
			return false;
		}
	}

	private void closeConnection()
	{
		try
		{
			connection.close();
			// closing connection
		} catch (SQLException e)
		{
			log.log(Level.INFO, "Connection unclosed!" + e);
			e.printStackTrace();
		}
	}

	public void setId(String phoneNumber) throws NoSuchAlgorithmException
	{
		log.log(Level.INFO, "SetId called!");
		MessageDigest messageDigest = MessageDigest.getInstance(DIGEST_ALGORITHM);
		// throws no_such_algorithm_exception

		messageDigest.update(phoneNumber.getBytes());
		byte[] digest = messageDigest.digest();
		StringBuffer stringBuffer = new StringBuffer();
		for (byte bytes : digest)
		{
			stringBuffer.append(String.format("%02x", bytes & 0xff));
			// generating hashId
		}
		id = stringBuffer.toString();
		log.log(Level.INFO,
				"phoneNumber:" + phoneNumber + "\tid:***************************************");
	}

}
