
import org.apache.commons.dbcp2.BasicDataSource;

public class BankDataSource {

	private static BasicDataSource dataSource;
	static final String DB_URL = "jdbc:mysql://localhost/bank";
	static final String USER = "user";
	static final String PASS = "pass";
	
	public static synchronized BasicDataSource getDataSource() {
		
		if(dataSource ==null) {
			dataSource = new BasicDataSource();
		
			dataSource.setUrl(DB_URL);
			dataSource.setUsername(USER);
			dataSource.setPassword(PASS);
			dataSource.setInitialSize(4);
			
		
			
			dataSource.setMinIdle(1);
			dataSource.setMaxIdle(10);
		
		}
		return dataSource;
		
	}
}
