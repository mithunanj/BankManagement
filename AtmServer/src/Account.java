import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Account {
	
	private double balance;
	private int pin; 
	private final Lock lock = new ReentrantLock();
	private final int accNum;
	private User owner;
	
	
	public Account(int accNum,int pin) {
		balance =0;
		this.pin = pin;
		this.accNum=accNum;
			
	}
	
	public abstract boolean withdraw(int amount);
	
	public abstract String getAccountType();
	
	public synchronized String deposit(double amount) {
		
		setBalance(getBalance() + amount);
		System.out.println("thank you");
		return "Balance: "+ balance; 
		
	}
	public void setOwner(User user) {
		owner = user;
	}
	public  User getOwner() {
		return owner;
	}
	
	public synchronized double getBalance() {
		
		return balance;
	}
	public int getAccNum() {
		return accNum;
	}
	
	public synchronized void setBalance(double balance) {
		this.balance=balance;
		
	}
	
	
	public int getPin() {
		return pin;
	}
	
	public synchronized boolean transferTo(Account to, int amount) {
		boolean fromLock= false;
		boolean toLock= false;
		
		try {
			fromLock = lock.tryLock();
			toLock = to.lock.tryLock();
			
			if(fromLock && toLock && this.withdraw(amount)) {
				to.deposit(amount);
				return true;
			}
			else {
			
				return false;		
			}		
		}
		finally {
			if(!(fromLock && toLock )) {
				if(fromLock) {
					lock.unlock();
				}
				if(toLock) {
					to.lock.unlock();
				}
				
			}
		}
		
	}

}
	
	

