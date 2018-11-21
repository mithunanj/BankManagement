import java.sql.*;

public class BankDatabase {
	
	 private PreparedStatement stmt;
	 private  Connection conn;
	 
	
	  public BankDatabase() {
		
		try {
			conn = DatabaseCP.getDataSource().getConnection();
			
			conn.setAutoCommit(false);
			
		   
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
	
		
		
	}
	 
	
	// create account table if it doesn't already exist. Set auto_increment at 10000  
	public boolean createAccountTable() {
		try {
			stmt = conn.prepareStatement("create table if not exists account(account_number int not null primary key auto_increment,"
					+ " account_type enum ('savings','checking'), balance double, user_id bigint,"
					+ " foreign key(user_id) references user(phone_number),pin int)auto_increment=10000");
			stmt.executeUpdate();
			return true;
		}
		catch(Exception e) {
			System.out.println(e.toString());
			return false;
		}
	}
	
	public boolean createUserTable() {
		
		try {
			stmt = conn.prepareStatement("create table if not exists user(phone_number bigint not null primary key, name varchar(255))");
			stmt.executeUpdate();
			return true;
		}
		catch( Exception e ) {
			e.printStackTrace();
			return false;
			
			
		}
	}
	
	public boolean addAccountAndUser(Account acc) throws SQLException {
		
		if(addUser(acc.getOwner())) {
			addAccount(acc);
			return true;
		}
		else {
			conn.rollback();
			return false;
		}
	}
	  
	  
	  
	public boolean addAccount(Account acc) throws SQLException {
		try {
			

			stmt = conn.prepareStatement("insert into account(account_type,balance,user_id,pin) values(?,?,?,?)");
			stmt.setString(1, acc.getAccountType());
			stmt.setDouble(2,acc.getBalance());
			stmt.setLong(3, acc.getOwner().getPhoneNum());
			stmt.setInt(4, acc.getPin());
			
			stmt.executeUpdate();
			printAccNum(acc.getOwner());
			return true;
		}
		catch(Exception e ) {
			e.printStackTrace();
			return false;
			
		}
	}
	
	
	
	private void printAccNum(User user) {
		
		try {
			stmt = conn.prepareStatement("select account_number from account where user_id=?");
			stmt.setLong(1, user.getPhoneNum());
			ResultSet rs = stmt.executeQuery();
			rs.last();
			System.out.println("account added for " + user.getName() + " with account number " + rs.getInt("account_number"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		
	}


	public void updateAccount(Account acc) {
		try {
			stmt = conn.prepareStatement("update account set balance = ? where account_number=?");
			stmt.setDouble(1, acc.getBalance());
			stmt.setInt(2,acc.getAccNum());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean addUser(User user  ) {
		
		try {
		    stmt = conn.prepareStatement("insert into user(phone_number,name) values(?,?)");
			stmt.setLong(1, user.getPhoneNum());
			stmt.setString(2, user.getName());
			
			stmt.executeUpdate();
			return true;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public Account getAccount(int accNum) {
		Account acc; 
		try {
			stmt = conn.prepareStatement("select * from user inner join account on user.phone_number=account.user_id where account_number=? for update");
			stmt.setInt(1, accNum);
			ResultSet rs = stmt.executeQuery();
			// creates the appropriate account type
			if(rs.next()) {
				if(rs.getString("account_type").equals("savings")) {
					acc= new SavingsAccount(rs.getInt("account_number"),rs.getInt("pin"), new User(rs.getString("name"),rs.getLong("phone_number")));
					
				}else {
					acc = new CheckingAccount(rs.getInt("account_number"),rs.getInt("pin"),new User(rs.getString("name"),rs.getLong("phone_number")));
				}
				acc.setBalance(rs.getDouble("balance"));
			}else {
				System.out.println("sorry");
				acc= null;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			acc= null;
			e.printStackTrace();
		}
		
		return acc;
		
		
	}
	
	public void saveChanges() {
		try {
			conn.commit();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	
	
    


}
