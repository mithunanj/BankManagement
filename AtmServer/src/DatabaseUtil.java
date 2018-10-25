import java.sql.*;

public class DatabaseUtil {
	 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	 static final String DB_URL = "jdbc:mysql://localhost/bank";

	   //  Database credentials
	 static final String USER = "user";
	 static final String PASS = "pass";
	 private Connection conn = null;
	
	
	public void connectToData() {
		 
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      System.out.println("Creating statement...");
		      
		   
		      
			
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
		
	}
	public void addAccount(Account acc) {
		try {
			PreparedStatement stmt = conn.prepareStatement("insert into accounts(account_number,account_type,balance,user_id,pin) values(?,?,?,?,?)");
			stmt.setInt(1, acc.getAccNum());
			stmt.setString(2, acc.getAccountType());
			stmt.setDouble(3,acc.getBalance());
			stmt.setLong(4, acc.getOwner().getPhoneNum());
			stmt.setInt(5, acc.getPin());
			addUser(acc.getOwner());
			
			stmt.executeUpdate();
		}
		catch(Exception e ) {
			e.printStackTrace();
			
		}
	}
	public void updateAccount(Account acc) {
		try {
			PreparedStatement stmt = conn.prepareStatement("update accounts set balance = ? where account_number=?");
			stmt.setDouble(1, acc.getBalance());
			stmt.setInt(2,acc.getAccNum());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void addUser(User user  ) {
		
		try {
			PreparedStatement stmt = conn.prepareStatement("insert into users(phone,name) values(?,?)");
			stmt.setLong(1, user.getPhoneNum());
			stmt.setString(2, user.getName());
			
			stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Account getAccount(int accNum) {
		Account acc; 
		try {
			PreparedStatement stmt = conn.prepareStatement("select * from accounts where account_number=?");
			stmt.setInt(1, accNum);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getString("account_type").equals("savings")) {
					acc= new SavingsAccount(rs.getInt("account_number"),rs.getInt("pin"));
					
				}else {
					acc = new CheckingAccount(rs.getInt("account_number"),rs.getInt("pin"));
				}
				acc.setBalance(rs.getDouble("balance"));
			}else {
				System.out.println("sorr");
				acc= null;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			acc= null;
			e.printStackTrace();
		}
		
		return acc;
		
		
	}


	
	
    


}
