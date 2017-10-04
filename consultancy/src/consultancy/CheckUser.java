package consultancy;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.sql.Connection;

public class CheckUser extends LogFormatter implements CommonConsts
{
	private final static Logger log = Logger.getLogger(CheckUser.class.getName());
	private static FileHandler fileHandler = null;
	String phoneNumber = null;
	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;
	String query = SELECT_QUERY;

	public CheckUser(String phoneNumber) throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException, SecurityException, IOException
	{
		fileHandler = new FileHandler("consultancy.log", true);
		log.addHandler(fileHandler);
		fileHandler.setFormatter(new LogFormatter());
		log.log(Level.INFO, "fileHandler created!");
		log.log(Level.INFO, "CheckUser Constructor called!");
		this.phoneNumber = phoneNumber;
		log.log(Level.INFO, "Phone Number is " + phoneNumber);
	}

	public boolean isUserPresent() throws SQLException, InstantiationException,
			IllegalAccessException, ClassNotFoundException
	{
		log.log(Level.INFO, "isUserPresent called!");
		Class.forName("com.mysql.jdbc.Driver").newInstance();

		query = query.replaceAll("x", phoneNumber);
		connection = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
		statement = connection.createStatement();
		resultSet = statement.executeQuery(query);

		while (resultSet.next())
		{
			log.log(Level.INFO, "returning True");
			return true;
		}
		log.log(Level.INFO, "returning False");
		return false;
	}
	
}
