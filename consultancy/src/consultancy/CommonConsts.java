package consultancy;

public interface CommonConsts
{
	
	final static String SELECT_QUERY = "SELECT x FROM users"; 
	
	final static String CONNECTION_URL = "jdbc:mysql://localhost:3306/consultancy";
	
	final static String USERNAME = "mohan";
	
	final static String PASSWORD = "manimohan251";
	
	final static String INSERT_QUERY = "INSERT INTO users ('id', 'pin', 'number', 'resume') VALUES (?,?,?,?)";
	
	final static String DIGEST_ALGORITHM = "SHA-256";
	
	final static String INDIAN_PIN = "91";

}
